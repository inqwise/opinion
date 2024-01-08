package com.inqwise.opinion.common.emails;

import com.inqwise.opinion.library.common.emails.IEmailData;
import com.inqwise.opinion.library.common.emails.ISingleEmailSignature;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.common.opinions.IOpinion;

public interface ICollectLinkEmailData extends ISingleEmailSignature, IEmailData {

	public abstract ICollector getCollector();
	public abstract IOpinion  getOpinion();
}
