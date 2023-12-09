package com.inqwise.opinion.cms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.inqwise.opinion.cms.common.kb.ITopic;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;

public class TopicEntity implements ITopic {

	private Integer id;
	private String name;
	private String uri;
	private Date createDate;
	private Date modifyDate;
	
	public TopicEntity(ResultSet reader) throws SQLException {
		setId(ResultSetHelper.optInt(reader, "topic_id"));
		setName(ResultSetHelper.optString(reader, "topic_name"));
		setUri(ResultSetHelper.optString(reader, "topic_uri"));
		setCreateDate(ResultSetHelper.optDate(reader, "create_date"));
		setModifyDate(ResultSetHelper.optDate(reader, "modify_date"));
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String getUri() {
		return uri;
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

}
