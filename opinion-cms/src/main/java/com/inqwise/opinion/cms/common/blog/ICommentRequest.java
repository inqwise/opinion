package com.inqwise.opinion.cms.common.blog;

public interface ICommentRequest {

	Long getPostId();

	Long getParentId();

	Long getAutorId();

	String getAutorName();

	String getContent();

	String getAutorEmail();

	String getAutorUrl();

	Integer getBlogId();

}
