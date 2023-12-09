package com.inqwise.opinion.opinion.common.emails;

import com.inqwise.opinion.library.common.emails.IEmailData;
import com.inqwise.opinion.library.common.emails.ISingleEmailSignature;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;

public interface IAnswererFinishResponseEmailData extends ISingleEmailSignature, IEmailData {

	public abstract String getAnswererSessionId();
	public abstract String getUrl();
	public abstract OpinionType getOpinionType();
	public abstract IOpinion  getOpinion();
}
