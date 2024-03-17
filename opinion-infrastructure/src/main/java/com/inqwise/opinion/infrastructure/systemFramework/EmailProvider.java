package com.inqwise.opinion.infrastructure.systemFramework;

import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.common.EmailProviderException;


public final class EmailProvider {

	static final ApplicationLog logger = ApplicationLog.getLogger(EmailProvider.class);
	
	private final String MAIL_SMTP_HOST = "mail.smtp.host";
	private final String MAIL_SMTP_PORT = "mail.smtp.port";
	private final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	private final String MAIL_SMTP_SUBMITTER = "mail.smtp.submitter";
	private boolean done = false;
	private BlockingQueue<Message> queue = null;
	private static EmailProvider instance = null;

	private EmailProviderOptions options;

	private ExecutorService service;
	
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

	public void close(){
		logger.info("close");
		if(!done){
			done = true;
			if(null != queue){
				try{
					logger.debug("notify queue");
					synchronized (queue) {
						queue.notify();
						try {
							logger.debug("wait queue");
							queue.wait(100000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					logger.debug("Shutdown service");
					service.shutdown();
					try {
						service.awaitTermination(100000, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (Throwable t){
					logger.error(t, "finishThread() : put to queue null failed.");
				}
			}
		}
		
	}
	
	private EmailProvider() {
		this(EmailProviderOptions.builder()
				.withUsername(BaseApplicationConfiguration.Email.getSmtpUsername())
				.withPassword(BaseApplicationConfiguration.Email.getSmtpPassword())
				.withIsAuthRequired(BaseApplicationConfiguration.Email.getIsAuthenticationRequired())
				.withHost(BaseApplicationConfiguration.Email.getSmtpServer())
				.withPort(BaseApplicationConfiguration.Email.getSmtpPort())
				.withAuditEmail(BaseApplicationConfiguration.Email.getAuditEmail())
				.build()
				);
	}
	
	protected EmailProvider(EmailProviderOptions options){
		this.options = options;
		try{
			queue = new LinkedBlockingQueue<Message>();
			service = Executors.newSingleThreadExecutor();
			service.execute(new MailSender());
		} catch(Throwable t){
			logger.error(t, "ctor() : start executeSend thread failed.");
		}
	}
	
	public synchronized static EmailProvider getInstance() {
		if(instance == null) {
			instance = new EmailProvider();
		}
		return instance;
	}

	private Session getSession() {

		Authenticator authenticator = new Authenticator();

		Properties props = new Properties();
		props.setProperty(MAIL_SMTP_SUBMITTER, authenticator.getPasswordAuthentication().getUserName());
		props.setProperty(MAIL_SMTP_AUTH, options.getIsAuthenticationRequired());
		props.setProperty(MAIL_SMTP_HOST, options.getSmtpServer());
		props.setProperty(MAIL_SMTP_PORT, options.getSmtpPort());
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
		props.put("mail.smtp.socketFactory.port", options.getSmtpPort()); //SSL Port

		return Session.getInstance(props, authenticator);
	}

	private class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator() {
			authentication = new PasswordAuthentication(options.getUsername(), options.getPassword());
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
		logger.debug("send(%s,%s,%s,%s,%s)" , from, Arrays.toString(toArr),subject,content,contentType);
		try{
			Objects.requireNonNull(toArr, "toArr");
			if(done){
				throw new Exception("Unable to send mail when service is done.");
			}
			
			if(null == from || from.trim() == ""){
				throw new UnsupportedOperationException("send() : 'from' is mandatory.");
			}
			
			Message message = new MimeMessage(getSession());
			
			InternetAddress addressFrom = new InternetAddress(from);
			message.setFrom(addressFrom);
			
			String auditEmail = options.getAuditEmail();
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
			logger.debug("MailSender started");
			try {
				do {
					Message message = queue.take();
					if(null == message) {
						synchronized (queue) {
							queue.wait();
						}
					} else {
						logger.debug("message received");
						try{
							Transport.send(message);
							logger.debug("message has been sent");
						} catch(Throwable t){
							logger.error(t, "executeSend() : Failed to send email message.");
							if(null != message){
								logger.error("Message - recipients: '%s'. subject: '%s'. content: '%s'", InternetAddress.toString(message.getAllRecipients()), message.getSubject(), message.getContent());
							}
						}
					}
				} while (!(done && (null == queue || queue.isEmpty())));
				
				synchronized (queue) {
					queue.notify();
				}
				
				logger.info("MailSender execution completed");
				
			} catch (InterruptedException ex) {
				logger.error(ex, "executeSend() : INTERRUPTED");
			} catch (Throwable t){
				logger.error(t, "executeSend() : Fatal error occured.");
			}
		}
	}
}

