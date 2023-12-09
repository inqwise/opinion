package com.inqwise.opinion.opinion.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.opinion.common.collectors.CollectorSourceType;
import com.inqwise.opinion.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.opinion.common.collectors.ICollectorSource;

public class CollectorSourceEntity implements ICollectorSource {

	private int id;
	private String name;
	private CollectorSourceType type;
	private String description;
	private Date modifyDate;
	private CollectorStatus initialStatus;
	private String returnUrl;
	private String screenOutUrl;
	private String closedUrl;
	private String referer;
	
	public CollectorSourceEntity(ResultSet reader) throws SQLException {
		setId(reader.getInt("collector_source_id"));
		setName(reader.getString("collector_source_name"));
		setType(CollectorSourceType.fromInt(reader.getInt("collector_source_type_id")));
		setDescription(ResultSetHelper.optString(reader, "colector_source_description"));
		setModifyDate(ResultSetHelper.optDate(reader, "modify_date"));
		setInitialStatus(CollectorStatus.fromInt(reader.getInt("initial_status_id")));
		setReturnUrl(ResultSetHelper.optString(reader, "return_url"));
		setScreenOutUrl(ResultSetHelper.optString(reader, "screen_out_url"));
		setClosedUrl(ResultSetHelper.optString(reader, "survey_closed_url"));
		setReferer(ResultSetHelper.optString(reader, "referer"));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CollectorSourceType getType() {
		return type;
	}

	public void setType(CollectorSourceType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public CollectorStatus getInitialStatus() {
		return initialStatus;
	}

	public void setInitialStatus(CollectorStatus initialStatus) {
		this.initialStatus = initialStatus;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getScreenOutUrl() {
		return screenOutUrl;
	}

	public void setScreenOutUrl(String screenOutUrl) {
		this.screenOutUrl = screenOutUrl;
	}

	public String getClosedUrl() {
		return closedUrl;
	}

	public void setClosedUrl(String closedUrl) {
		this.closedUrl = closedUrl;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

}
