package com.inqwise.opinion.cms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.cms.common.IPage;
import com.inqwise.opinion.cms.common.IStylesheet;
import com.inqwise.opinion.cms.common.ResourceType;

public class StylesheetEntity extends ResourceEntity implements IStylesheet{

	public StylesheetEntity(ResultSet reader) throws SQLException {
		super(reader);
	}

	@Override
	public ResourceType getResourceType() {
		return ResourceType.Css;
	}

	@Override
	public void addToPage(IPage page) {
		page.addCss(this);
	}

}
