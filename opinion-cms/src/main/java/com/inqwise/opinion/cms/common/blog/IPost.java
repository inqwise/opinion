package com.inqwise.opinion.cms.common.blog;

import java.util.Date;
import java.util.List;

public interface IPost {

	public abstract long getId();

	public abstract String getTitle();

	public abstract String getUrlTitle();

	public abstract String getContent();

	public abstract Date getPostDate();
	
	public abstract List<String> getTags();
	public abstract List<String> getCategories();

	public abstract int getCountOfComments();
	public abstract Date getModifyDate();

}