package com.inqwise.opinion.cms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.cms.common.IPage;
import com.inqwise.opinion.cms.common.ResourceType;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;

public abstract class ResourceEntity {

	static ApplicationLog logger = ApplicationLog.getLogger(ResourceEntity.class);
	private int id;
	private String path;
	private String key;
	private boolean isReleativePath;
	
	public static ResourceEntity Create(ResultSet reader) throws SQLException {
		Integer resourceTypeId = ResultSetHelper.optInt(reader, "resource_type_id");
		ResourceType resourceType = ResourceType.fromInt(resourceTypeId);
		ResourceEntity resource;
		switch (resourceType) {
		case Css:
			resource = new StylesheetEntity(reader);
			break;
		case Script:
			resource = new ScryptEntity(reader);
			break;
		default:
			throw new IllegalArgumentException(String.format("ResourceType '%s' not supported", resourceType));
		}
		return resource;
	}

	public ResourceEntity(ResultSet reader) throws SQLException{
		setId(reader.getInt("resource_id"));
		setPath(ResultSetHelper.optString(reader, "path"));
		setKey(ResultSetHelper.optString(reader, "resource_key"));
		setReleativePath(ResultSetHelper.optBool(reader, "is_releative_path", true));
	}

	public abstract ResourceType getResourceType();

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public abstract void addToPage(IPage page);

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setReleativePath(boolean isReleativePath) {
		this.isReleativePath = isReleativePath;
	}

	public boolean isReleativePath() {
		return isReleativePath;
	}
}
