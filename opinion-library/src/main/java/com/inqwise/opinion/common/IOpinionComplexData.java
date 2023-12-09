package com.inqwise.opinion.opinion.common;

import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.opinion.common.collectors.ICollector;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;

public interface IOpinionComplexData<T extends IOpinion> {
	public GuidType getGuidType();
	public ICollector getCollector();
	public T getOpinion();
	public String getGuid();
	public IHttpAnswererSession getSession();
	public boolean isOpen();
	public OutputMode getMode();
	public Long getResponsesBallance(Long defaultValue);
	public IOpinionAccount getAccountServicePackage();
	public boolean isClientInfoReceived();
	public boolean isPasswordRequired();
	public IAccount getAccount();
	public IProduct getProduct();
	boolean isFinished();
	public String getAnswererSessionKey();
	String getRespondentExternalId();
}
