package com.inqwise.opinion.cms.common.kb;

import java.util.Date;

public interface ITopic {
	public abstract Integer getId();
	public abstract String getName();
	public abstract String getUri();
	public abstract Date getCreateDate();
	public abstract Date getModifyDate();
}
