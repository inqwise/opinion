package com.inqwise.opinion.cms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.cms.common.ICategory;

public class CategoryEntity implements ICategory {

	Integer id;
	String name;
	
	public CategoryEntity(ResultSet reader) throws SQLException {
		id = ResultSetHelper.optInt(reader, "category_id");
		name = ResultSetHelper.optString(reader, "category_name");
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
}
