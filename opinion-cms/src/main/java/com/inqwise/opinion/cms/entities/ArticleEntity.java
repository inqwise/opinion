package com.inqwise.opinion.cms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.inqwise.opinion.cms.common.kb.IArticle;
import com.inqwise.opinion.cms.common.kb.ITopic;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;

public class ArticleEntity implements IArticle {

	private Integer id;
	private String title;
	private String uri;
	private String content;
	private Integer topicId;
	private String topicName;
	private Date createDate;
	private Date modifyDate;
	private Boolean popular;
	private Boolean active;

	public ArticleEntity(ResultSet reader) throws SQLException {

		setId(ResultSetHelper.optInt(reader, "article_id"));
		setTitle(ResultSetHelper.optString(reader, "article_title"));
		setUri(ResultSetHelper.optString(reader, "article_uri"));
		setContent(ResultSetHelper.optString(reader, "article_content"));
		setTopicId(ResultSetHelper.optInt(reader, "topic_id"));
		setTopicName(ResultSetHelper.optString(reader, "topic_name"));
		setCreateDate(ResultSetHelper.optDate(reader, "create_date"));
		setModifyDate(ResultSetHelper.optDate(reader, "modify_date"));
		setPopular(ResultSetHelper.optBool(reader, "is_popular"));
		setActive(ResultSetHelper.optBool(reader, "is_active"));

	}

	private void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public Boolean getActive() {
		return active;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getId() {
		return id;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return title;
	}

	private void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String getUri() {
		return uri;
	}

	private void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getContent() {
		return content;
	}
	
	private void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	
	@Override
	public Integer getTopicId() {
		return topicId;
	}
	
	private void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	@Override
	public String getTopicName() {
		return topicName;
	}
	
	private void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	private void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public Date getModifyDate() {
		return modifyDate;
	}
	
	private void setPopular(Boolean popular) {
		this.popular = popular;
	}

	@Override
	public Boolean getPopular() {
		return popular;
	}

}
