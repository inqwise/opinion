package com.inqwise.opinion.infrastructure.systemFramework;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.common.EmailProviderException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;


public final class EmailProvider {

	static final ApplicationLog logger = ApplicationLog.getLogger(EmailProvider.class);
	
	private final String MAIL_SMTP_HOST = "mail.smtp.host";
	private final String MAIL_SMTP_PORT = "mail.smtp.port";
	private final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	private final String MAIL_SMTP_SUBMITTER = "mail.smtp.submitter";
	private boolean done = false;
	private BlockingQueue<Message> queue = null;
	private static EmailProvider instance = null;
	
	public enum ContentType {

		PlainText("text/plain; charset=utf-8"), Html("text/html; charset=utf-8");

		private final String value;

		public String toString() {
			return value;
		}

		private ContentType(String value) {
			this.value = value;
		}
	}

	protected void finalize() throws Throwable
	{
		finish();
		super.finalize();
	} 

	public void finish(){
		if(!done){
			done = true;
			if(null != queue){
				try{
					queue = null;
				} catch (Throwable t){
					logger.error(t, "finishThread() : put to queue null failed.");
				}
			}
		}
	}
	
	private EmailProvider(){
		try{
			queue = new LinkedBlockingQueue<Message>();
			Thread t = new Thread(new MailSender());
			t.setPriority(Thread.MIN_PRIORITY);
			t.start();
		} catch(Throwable t){
			logger.error(t, "ctor() : start executeSend thread failed.");
		}
	}
	
	public static EmailProvider getInstance() {
		if(instance == null) {
			instance = new EmailProvider();
		} else if(instance.done){
			try {
				instance.finalize();
			} catch (Throwable t) {
				logger.error(t, "getInstance() : finilize failed.");
			}
			instance = new EmailProvider();
		}
		return instance;
	}

	private Session getSession() {

		Authenticator authenticator = new Authenticator();

		Properties props = new Properties();
		props.setProperty(MAIL_SMTP_SUBMITTER, authenticator.getPasswordAuthentication().getUserName());
		props.setProperty(MAIL_SMTP_AUTH, BaseApplicationConfiguration.Email.getIsAuthenticationRequired());
		props.setProperty(MAIL_SMTP_HOST, BaseApplicationConfiguration.Email.getSmtpServer());
		props.setProperty(MAIL_SMTP_PORT, BaseApplicationConfiguration.Email.getSmtpPort());

		return Session.getInstance(props, authenticator);
	}

	private class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator() {
			String username = BaseApplicationConfiguration.Email.getSmtpUsername();
			String password = BaseApplicationConfiguration.Email.getSmtpPassword();
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}

	private String[] splitRecipients(String recipients){
		return StringUtils.split(recipients, ";, :|");
	}
	
	public void send(String from, String to, String subject, String content, ContentType contentType) throws EmailProviderException{
		send(from, splitRecipients(to), subject, content, contentType);
	}
	
	public void send(String from, String[] toArr, String subject, String content, ContentType contentType) throws EmailProviderException{
		// Send Email
		
		try{
			if(done){
				throw new Exception("Unable to send mail when service is done.");
			}
			
			if(null == from || from.trim() == ""){
				throw new UnsupportedOperationException("send() : 'from' is mandatory.");
			}
			
			Message message = new MimeMessage(getSession());
			
			InternetAddress addressFrom = new InternetAddress(from);
			message.setFrom(addressFrom);
			
			
			String auditEmail = BaseApplicationConfiguration.Email.getAuditEmail();
			if(null != auditEmail){
				InternetAddress auditAddressBcc = new InternetAddress(auditEmail);
				message.addRecipient(Message.RecipientType.BCC, auditAddressBcc);
			}
			
			for(String to : toArr){
				
				if((StringUtils.isEmpty(content) || StringUtils.isBlank(content))
						&& (StringUtils.isEmpty(subject) || StringUtils.isBlank(subject))){
					throw new MessagingException("send() : Unable to send mail without subject and content.");
				}
	
				if(StringUtils.isEmpty(to) || StringUtils.isBlank(to)){
					throw new MessagingException("send() : Unable to send mail without recipient(s).");
				}
				
				if(null == content){
					content = StringUtils.EMPTY;
				}
				
				InternetAddress addressTo = new InternetAddress(to);
				message.addRecipient(Message.RecipientType.TO, addressTo);
			}
			
			// Setting the Subject and Content Type
			message.setSubject(subject);
			
			message.setContent(content, contentType.toString());
			
			synchronized (queue) {
				queue.put(message);
				queue.notify();
			}
		} catch (Exception ex){
			throw new EmailProviderException(ex); 
		}
	}

	public void sendHtml(String from, String to, String subject, String content)
			throws EmailProviderException {
		// Send Email

		send(from, to, subject, content, ContentType.Html);
	}

	public void sendText(String from, String to, String subject, String content)
			throws EmailProviderException {
		// Send Email
		send(from, to, subject, content, ContentType.PlainText);
	}
	
	public class MailSender implements Runnable {
		public void run(){
			
			try {
				while (!done) {
					Message message = queue.take();
					if(null == message) {
						synchronized (queue) {
							while(queue.isEmpty()){
								queue.wait();
							}
						}
					} else {
						try{
							Transport.send(message);
						} catch(Throwable t){
							logger.error(t, "executeSend() : Failed to send email message.");
							if(null != message){
								logger.error("Message - recipients: '%s'. subject: '%s'. content: '%s'", InternetAddress.toString(message.getAllRecipients()), message.getSubject(), message.getContent());
							}
						}
					}
				}
			} catch (InterruptedException ex) {
				logger.error(ex, "executeSend() : INTERRUPTED");
			} catch (Throwable t){
				logger.error(t, "executeSend() : Fatal error occured.");
			}
		}
	}
}

