package com.inqwise.opinion.cms.entities.blog;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.cms.common.blog.IBlog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;

public class BlogEntity implements IBlog {

	private int id;
	private String name;
	private Long ownerId;
	
	public BlogEntity(ResultSet reader) throws SQLException {
		setId(reader.getInt("blog_id"));
		setName(reader.getString("blog_name"));
		setOwnerId(ResultSetHelper.optInt(reader, "owner_id"));
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public Long getOwnerId() {
		return ownerId;
	}
	
}
