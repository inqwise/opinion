package com.inqwise.opinion.cms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.cms.common.IPage;
import com.inqwise.opinion.cms.common.IScript;
import com.inqwise.opinion.cms.common.ResourceType;

public class ScryptEntity extends ResourceEntity implements IScript {

	public ScryptEntity(ResultSet reader) throws SQLException {
		super(reader);
	}

	@Override
	public ResourceType getResourceType() {
		return ResourceType.Script;
	}

	@Override
	public void addToPage(IPage page) {
		page.addScript(this);
	}

}
