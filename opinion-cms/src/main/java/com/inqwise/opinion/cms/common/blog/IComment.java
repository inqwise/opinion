package com.inqwise.opinion.cms.common.blog;

import java.util.Date;
import java.util.List;

public interface IComment {

	public abstract String getContent();

	public abstract Date getCommentDate();

	public abstract Long getAutorId();

	public abstract String getAutorName();

	public abstract long getPostId();

	public abstract long getId();

	public abstract Long getParentId();
	
	public abstract String getAutorEmail();

	public abstract String getAutorUrl();

	public abstract List<IComment> getComments();

	public abstract boolean HasComments();

}
