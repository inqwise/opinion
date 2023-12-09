package com.inqwise.opinion.cms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.cms.common.ITemplatePage;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;

public class TemplatePageEntity implements ITemplatePage  {
	private int id;
	private String include;
	public TemplatePageEntity(ResultSet reader) throws SQLException{
		setId(reader.getInt("template_id"));
		setInclude(ResultSetHelper.optString(reader, "include"));
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setInclude(String include) {
		this.include = include;
	}
	public String getInclude() {
		return include;
	}
}
