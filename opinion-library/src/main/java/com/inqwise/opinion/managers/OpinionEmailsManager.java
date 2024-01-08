package com.inqwise.opinion.managers;

import java.io.File;
import java.util.UUID;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.EmailProvider;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.managers.EmailsManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.common.emails.IAnswererFinishResponseEmailData;
import com.inqwise.opinion.common.emails.ICollectLinkEmailData;

public class OpinionEmailsManager extends EmailsManager {

	private static ApplicationLog logger = ApplicationLog
	.getLogger(OpinionEmailsManager.class);
	
	private static String getOpinionTemplateFile(String fileName){
		File file1 = new File(ApplicationConfiguration.Opinion.getTemplatesFolder());
		File file2 = new File(file1, fileName);
		return file2.getPath();
	}
	public static BaseOperationResult sendAnswererFinishResponseEmail(IAnswererFinishResponseEmailData data){
		try {
			STGroup group = new STGroupFile(getOpinionTemplateFile("SessionFinished/SessionFinishedEmail.stg"));
			ST subjectSt = group.getInstanceOf("subject");
			ST bodySt = group.getInstanceOf("body");
			subjectSt.add("data", data);
			bodySt.add("data", data);
			EmailProvider emailProvider = EmailProvider.getInstance();
			emailProvider.sendText(data.getNoreplyEmail(), data.getEmail(), subjectSt.render(), bodySt.render());
			logger.info("AnswererFinishResponseEmail notification was sent to '%s'", data.getEmail());
			return BaseOperationResult.Ok;
		} catch (Exception e) {
			UUID errorId = logger.error(e, "sendAnswererFinishResponseEmail() : Unexpected eror occured");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
	}
	public static BaseOperationResult sendCollectLinkEmail(ICollectLinkEmailData data) {
		try {
			STGroup group = new STGroupFile(getOpinionTemplateFile("Collect/CollectLinkEmail.stg"));
			ST subjectSt = group.getInstanceOf("subject");
			ST bodySt = group.getInstanceOf("body");
			subjectSt.add("data", data);
			bodySt.add("data", data);
			EmailProvider emailProvider = EmailProvider.getInstance();
			emailProvider.sendText(data.getNoreplyEmail(), data.getEmail(), subjectSt.render(), bodySt.render());
			logger.info("CollectLinkEmail was sent to '%s'", data.getEmail());
			return BaseOperationResult.Ok;
		} catch (Exception e) {
			UUID errorId = logger.error(e, "sendCollectLinkEmail() : Unexpected eror occured");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
	}
}
