package com.inqwise.opinion.library.managers;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.stringtemplate.v4.DateRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.EmailProvider;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.emails.EmailType;
import com.inqwise.opinion.library.common.emails.IContactUsRequestEmailData;
import com.inqwise.opinion.library.common.emails.ICustomPlanRequestEmailData;
import com.inqwise.opinion.library.common.emails.IFollowUpEmailData;
import com.inqwise.opinion.library.common.emails.IInviteEmailData;
import com.inqwise.opinion.library.common.emails.IPackageBeforeExpirationEmailData;
import com.inqwise.opinion.library.common.emails.IPackageExpiredEmailData;
import com.inqwise.opinion.library.common.emails.IPasswordChangedEmailData;
import com.inqwise.opinion.library.common.emails.IRegisterEmailData;
import com.inqwise.opinion.library.common.emails.IReportBugEmailData;
import com.inqwise.opinion.library.common.emails.IResetPasswordEmailData;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.EmailsAuditDataAccess;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class EmailsManager {
	
	static ApplicationLog logger = ApplicationLog.getLogger(EmailsManager.class);
	public static BaseOperationResult sendRegisterEmail(IRegisterEmailData data){
		try{
			com.inqwise.opinion.infrastructure.systemFramework.EmailProvider emailProvider = EmailProvider.getInstance();	
			
			STGroup group = new STGroupFile(getOfficeTemplateFile("Registration/RegistrationEmail.stg"));
			ST subjectSt = group.getInstanceOf("subject");
			ST bodySt = group.getInstanceOf("body");
			subjectSt.add("data", data);
			bodySt.add("data", data);
			
			emailProvider.sendText(data.getNoreplyEmail(), data.getEmail(), subjectSt.render(), bodySt.render());
			logger.info("Sent Register notification to email '%s'", data.getEmail());	
			return BaseOperationResult.Ok;
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendRegisterEmail() : Unexpected error occured.");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
	}
	
	private static String getOfficeTemplateFile(String fileName){
		File file1 = new File(ApplicationConfiguration.CustomerZone.getTemplatesFolder());
		File file2 = new File(file1, fileName);
		return file2.getPath();
	}
	
	public static BaseOperationResult sendResetPasswordEmail(IResetPasswordEmailData data){
		try{
			EmailProvider emailProvider = EmailProvider.getInstance();	
			
			STGroup group = new STGroupFile(getOfficeTemplateFile("ResetPassword/ResetPasswordEmail.stg"));
			ST subjectSt = group.getInstanceOf("subject");
			ST bodySt = group.getInstanceOf("body");
			subjectSt.add("data", data);
			bodySt.add("data", data);
			
			emailProvider.sendText(data.getNoreplyEmail(), data.getEmail(), subjectSt.render(), bodySt.render());
			logger.info("Sent Reset password confirmation to email '%s'", data.getEmail());	
			return BaseOperationResult.Ok;
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendResetPasswordEmail() : Unexpected error occured.");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
	}
	
	public static BaseOperationResult sendPasswordChangedEmail(IPasswordChangedEmailData data){
		try{
			EmailProvider emailProvider = EmailProvider.getInstance();	
			
			STGroup group = new STGroupFile(getOfficeTemplateFile("PasswordChanged/PasswordChangedEmail.stg"));
			ST subjectSt = group.getInstanceOf("subject");
			ST bodySt = group.getInstanceOf("body");
			subjectSt.add("data", data);
			bodySt.add("data", data);
			
			emailProvider.sendText(data.getNoreplyEmail(), data.getEmail(), subjectSt.render(), bodySt.render());
			logger.info("Sent Password was changed message to email '%s'", data.getEmail());	
			return BaseOperationResult.Ok;
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendPasswordChangedEmail() : Unexpected error occured.");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
	}
	
	public static BaseOperationResult sendCustomPlanRequestEmail(ICustomPlanRequestEmailData data){
		BaseOperationResult result = null;
		try{
			EmailProvider emailProvider = EmailProvider.getInstance();	
			
			STGroup group = new STGroupFile(getOfficeTemplateFile("ClientRequests/CustomPlanRequestEmail.stg"));
			ST subjectSt = group.getInstanceOf("subject");
			ST bodySt = group.getInstanceOf("body");
			subjectSt.add("data", data);
			bodySt.add("data", data);
			
			IProduct product = ProductsManager.getCurrentProduct();
			if(null == product){
				result = new BaseOperationResult(ErrorCode.NoResults);
			} 
			
			if(null == result){
				emailProvider.sendText(data.getEmail(), product.getSalesEmail(), subjectSt.render(), bodySt.render());
				logger.info("Sent Custom Plan request from email '%s' to sales", data.getEmail());
				result = BaseOperationResult.Ok;
			}
			
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendCustomPlanRequestEmail() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
		
		return result;
	}
	
	public static BaseOperationResult sendContactUsRequestEmail(IContactUsRequestEmailData data){
		BaseOperationResult result = null;
		try{
			EmailProvider emailProvider = EmailProvider.getInstance();	
			
			STGroup group = new STGroupFile(getOfficeTemplateFile("ClientRequests/ContactUsRequestEmail.stg"));
			ST subjectSt = group.getInstanceOf("subject");
			ST bodySt = group.getInstanceOf("body");
			subjectSt.add("data", data);
			bodySt.add("data", data);
			
			IProduct product = ProductsManager.getCurrentProduct();
			if(null == product){
				result = new BaseOperationResult(ErrorCode.NoResults);
			}
			
			if(null == result){
				emailProvider.sendText(data.getEmail(), product.getContactUsEmail(), subjectSt.render(), bodySt.render());
				logger.info("Sent Contact Us request from email '%s' to sales", data.getEmail());	
				result = BaseOperationResult.Ok;
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendContactUsRequestEmail() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
		
		return result;
	}
	
	public static BaseOperationResult sendReportBug(IReportBugEmailData data){
		try{
			EmailProvider emailProvider = EmailProvider.getInstance();	
			
			STGroup group = new STGroupFile(getOfficeTemplateFile("ClientRequests/BugReportEmail.stg"));
			ST subjectSt = group.getInstanceOf("subject");
			ST bodySt = group.getInstanceOf("body");
			subjectSt.add("data", data);
			bodySt.add("data", data);
			
			emailProvider.sendText(data.getNoreplyEmail(), data.getBugsEmail(), subjectSt.render(), bodySt.render());
			logger.info("Sent Bug report to email '%s'", data.getBugsEmail());	
			return BaseOperationResult.Ok;
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendReportBug() : Unexpected error occured.");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
	}

	public static BaseOperationResult sendUsEmail(String title,
			String description, Long userId, String email) {
		try{
			IProduct currentProduct = ProductsManager.getCurrentProduct();
			String subject = currentProduct.getFeedbackCaption() + " - " + title;
			StringBuilder sb = new StringBuilder();
			sb.append("\n");
			sb.append("UserId: ").append(userId).append("\n\n");
			sb.append("Contact Email: ").append(email).append("\n\n");
			sb.append("Description:\n").append(description);
			
			String toAddress = currentProduct.getSupportEmail();
			EmailProvider emailProvider = EmailProvider.getInstance();
			emailProvider.sendText(currentProduct.getNoreplyEmail(), toAddress, subject, sb.toString());
			logger.info("Sent email to address '%s'", toAddress);	
			return BaseOperationResult.Ok;
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendUsEmail() : Unexpected error occured.");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
	}
	
	public static void insertEmailAudit(String key, String content, EmailType emailType){
		try{
			EmailsAuditDataAccess.set(key, content, emailType.getValue());
		} catch (Throwable ex){
			logger.error(ex, "insertEmailAudit() : Unexpected error occured.");
		}
	}
	
	public static OperationResult<Boolean> isEmailAuditExist(String key){
		OperationResult<Boolean> result = new OperationResult<Boolean>();
		try{
			result.setValue(EmailsAuditDataAccess.isKeyExist(key));
		} catch (Throwable ex){
			UUID errorId = logger.error(ex, "isEmailAuditExist() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	private static String createAuditKey(EmailType emailType, String entityId){
		return String.format("e%si%s", emailType.getValue(), entityId);
	}
	
	private static String createAuditKeyDaily(EmailType emailType, String entityId, Date date){
		return String.format("e%si%sd%tY%tm%td", emailType.getValue(), entityId, date, date, date);
	}
	
	private static String createAuditContent(String to, String from, String subject, String body) throws JSONException{
		return new JSONObject().put("to", to).put("from", from).put("subject", subject).put("body", body).toString();
	}
	
	public static IOperationResult sendFollowUpEmail(IFollowUpEmailData data) {
		try{
			String auditKey = createAuditKey(EmailType.FollowUp, Long.toString(data.getUserId()));
			if(isEmailAuditExist(auditKey).getValue()){
				logger.info("Follow up email already sent to user #" + data.getUserId());
			} else {
				EmailProvider emailProvider = EmailProvider.getInstance();	
				
				STGroup group = new STGroupFile(getOfficeTemplateFile("FollowUp/FollowUp1DayEmail.stg"));
				ST subjectSt = group.getInstanceOf("subject");
				ST bodySt = group.getInstanceOf("body");
				subjectSt.add("data", data);
				bodySt.add("data", data);
				String renderedBody = bodySt.render();
				String renderedSubject = subjectSt.render();
				String from = data.getSupportEmail();
				String to = data.getEmail();
						
				emailProvider.sendText(from, to, renderedSubject, renderedBody);
				logger.info("Sent Follow up message to email '%s'", data.getEmail());
				
				// Insert email to audit
				insertEmailAudit(auditKey, createAuditContent(to, from, renderedSubject, renderedBody), EmailType.FollowUp);
			}
			return BaseOperationResult.Ok;
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendFollowUpEmail() : Unexpected error occured.");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
	}

	public static IOperationResult sendPackageExpiredEmail(IPackageExpiredEmailData data) {
		try{
			String auditKey = createAuditKeyDaily(EmailType.PackageExpiry, Long.toString(data.getUserId()), new Date());
			if(isEmailAuditExist(auditKey).getValue()){
				logger.info("Package expired email already sent to user #" + data.getUserId());
			} else {
				EmailProvider emailProvider = EmailProvider.getInstance();	
				
				STGroup group = new STGroupFile(getOfficeTemplateFile("ServicePackages/PackageExpiredEmail.stg"));
				ST subjectSt = group.getInstanceOf("subject");
				ST bodySt = group.getInstanceOf("body");
				subjectSt.add("data", data);
				bodySt.add("data", data);
				String renderedBody = bodySt.render();
				String renderedSubject = subjectSt.render();
				String from = data.getSupportEmail();
				String to = data.getEmail();
						
				emailProvider.sendText(from, to, renderedSubject, renderedBody);
				logger.info("Sent Package Expiry message to email '%s'", data.getEmail());
				
				// Insert email to audit
				insertEmailAudit(auditKey, createAuditContent(from, to, renderedSubject, renderedBody), EmailType.PackageExpiry);
			}
			return BaseOperationResult.Ok;
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendPackageExpiredEmail() : Unexpected error occured.");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
	}

	public static IOperationResult sendPackageOneDayBeforeExpirationEmail(IPackageBeforeExpirationEmailData data) {
		try{
			String auditKey = createAuditKeyDaily(EmailType.PackageBeforeExpiration, Long.toString(data.getUserId()), data.getExpirationDate());
			if(isEmailAuditExist(auditKey).getValue()){
				logger.info("Package before expiration email already sent to user #" + data.getUserId());
			} else {
				EmailProvider emailProvider = EmailProvider.getInstance();	
				
				STGroup group = new STGroupFile(getOfficeTemplateFile("ServicePackages/PackageWillExpiryEmail.stg"));
				group.registerRenderer(Date.class, new DateRenderer());
				ST subjectSt = group.getInstanceOf("subject");
				ST bodySt = group.getInstanceOf("body");
				subjectSt.add("data", data);
				bodySt.add("data", data);
				String renderedBody = bodySt.render();
				String renderedSubject = subjectSt.render();
				String from = data.getSupportEmail();
				String to = data.getEmail();
						
				emailProvider.sendText(from, to, renderedSubject, renderedBody);
				logger.info("Sent Package Before Expiration message to email '%s'", data.getEmail());
				
				// Insert email to audit
				insertEmailAudit(auditKey, createAuditContent(to, from, renderedSubject, renderedBody), EmailType.PackageBeforeExpiration);
			}
			return BaseOperationResult.Ok;
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendPackageExpiredEmail() : Unexpected error occured.");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
	}

	public static BaseOperationResult sendInviteEmail(IInviteEmailData data) {
		try{
			String auditKey = null;
			EmailProvider emailProvider = EmailProvider.getInstance();	
			
			STGroup group = new STGroupFile(getOfficeTemplateFile("Invite/InviteEmail.stg"));
			group.registerRenderer(Date.class, new DateRenderer());
			ST subjectSt = group.getInstanceOf("subject");
			ST bodySt = group.getInstanceOf("body");
			subjectSt.add("data", data);
			bodySt.add("data", data);
			String renderedBody = bodySt.render();
			String renderedSubject = subjectSt.render();
			String from = data.getSupportEmail();
			String to = data.getEmail();
					
			emailProvider.sendText(from, to, renderedSubject, renderedBody);
			logger.info("Sent Invite message to email '%s'", data.getEmail());
			
			// Insert email to audit
			insertEmailAudit(auditKey, createAuditContent(to, from, renderedSubject, renderedBody), EmailType.Invite);
			
			return BaseOperationResult.Ok;
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendInviteEmail() : Unexpected error occured.");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
	}
	
	
}
