package com.inqwise.opinion.cms.managers;

import java.io.File;
import java.util.UUID;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.cms.common.blog.INewCommentNotificationEmailData;
import com.inqwise.opinion.cms.common.faq.INewQuestionEmailData;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.EmailProvider;

public class EmailsManager {

	private static ApplicationLog logger = ApplicationLog.getLogger(EmailsManager.class);
	
	private static String getTemplatePath(String fileName){
		File file1 = new File(CmsConfiguration.getTemplatesFolder());
		File file2 = new File(file1, fileName);
		return file2.getPath();
	}

	public static BaseOperationResult sendNewCommentNotificationEmail(final INewCommentNotificationEmailData data){
		try{
			EmailProvider emailProvider = EmailProvider.getInstance();	
			
			STGroup group = new STGroupFile(getTemplatePath("Blog/NewCommentNotificationEmail.stg"));
			ST subjectSt = group.getInstanceOf("subject");
			ST bodySt = group.getInstanceOf("body");
			subjectSt.add("data", data);
			bodySt.add("data", data);
			
			emailProvider.sendText(data.getNoreplyEmail(), data.getEmail(), subjectSt.render(), bodySt.render());
			logger.info("Sent New comment notification to email '%s'", data.getEmail());	
			return BaseOperationResult.Ok;
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendNewCommentNotificationEmail() : Unexpected error occured.");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
	}

	public static BaseOperationResult sendNewQuestionEmail(
			INewQuestionEmailData data) {
		try{
			EmailProvider emailProvider = EmailProvider.getInstance();	
			
			STGroup group = new STGroupFile(getTemplatePath("Faq/NewQuestionEmail.stg"));
			ST subjectSt = group.getInstanceOf("subject");
			ST bodySt = group.getInstanceOf("body");
			subjectSt.add("data", data);
			bodySt.add("data", data);
			
			emailProvider.sendText(data.getNoreplyEmail(), data.getEmail(), subjectSt.render(), bodySt.render());
			logger.info("Sent New Question to email '%s'", data.getEmail());	
			return BaseOperationResult.Ok;
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "sendNewQuestionEmail() : Unexpected error occured.");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, t.toString());
		}
	}

}
