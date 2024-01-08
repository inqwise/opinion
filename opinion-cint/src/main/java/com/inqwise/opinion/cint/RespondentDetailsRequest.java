package com.inqwise.opinion.cint;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IRequest;
import com.inqwise.opinion.cint.common.IRespondent;
import com.inqwise.opinion.cint.common.errorHandle.CintBaseOperationResult;
import com.inqwise.opinion.cint.common.errorHandle.CintErrorCode;
import com.inqwise.opinion.cint.core.RequestType;
import com.inqwise.opinion.cint.entities.RespondentEntity;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class RespondentDetailsRequest implements IRequest<IRespondent> {
	static ApplicationLog logger = ApplicationLog.getLogger(RespondentDetailsRequest.class);
	private String respondentGuid;
	
	@Override
	public String getUrl() {
		return "/respondents/" + respondentGuid;
	}

	@Override
	public CintBaseOperationResult validate() {
		CintBaseOperationResult result = null;
		if(null == respondentGuid){
			logger.warn("validate : respondentGuid is mandatory.");
			result = new CintBaseOperationResult(CintErrorCode.RespondentGuidIsMandatory);
		}
		return result;
	}

	@Override
	public RequestType getRequestType() {
		return RequestType.Get;
	}

	@Override
	public String toXml() {
		return null;
	}

	@Override
	public IRespondent parseResponse(Document doc) {
		return new RespondentEntity((Element)doc.getFirstChild());
	}

	@Override
	public IRespondent parseResponse(String raw) {
		throw new Error("Not implemented");
	}

	public String getRespondentGuid() {
		return respondentGuid;
	}

	public void setRespondentGuid(String respondentGuid) {
		this.respondentGuid = respondentGuid;
	}

	@Override
	public boolean isRequiredSignature() {
		return false;
	}

}
