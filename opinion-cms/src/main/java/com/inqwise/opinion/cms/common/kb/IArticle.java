package com.inqwise.opinion.cms.common.kb;

import java.util.Date;

public interface IArticle {
	public abstract Integer getId();
	public abstract String getTitle();
	public abstract String getUri();
	public abstract String getContent();
	public abstract Integer getTopicId();
	public abstract String getTopicName();
	public abstract Date getCreateDate();
	public abstract Date getModifyDate();
	public abstract Boolean getPopular();
	public abstract Boolean getActive();
}
