package com.inqwise.opinion.automation.common.eventTypes;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;

@XmlType
public class EventType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7404393977908918875L;
	/* EventTypes */
	public static final int AutomationActionFailed = 1;
	public static final int RegistrationOccured = 2;
	public static final int PaymentOccured = 3;
	public static final int ChargeStatusChanged = 4;
	public static final int OpinionCreated = 5;
	public static final int ParticipantCompleted = 6;
	public static final int Login = 7;
	
	private Integer id;
	private String name;
	private Date modifyDate;
	private String description;
	private String recipients;
	
	public EventType(ResultSet reader) throws SQLException {
		setId(reader.getInt("event_type_id"));
		setName(reader.getString("event_type_name"));
		setDescription(ResultSetHelper.optString(reader, "event_type_description"));
		setModifyDate(ResultSetHelper.optDate(reader, "modify_date"));
		setRecipients(ResultSetHelper.optString(reader, "event_type_recipients"));
	}
	public EventType() {
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

}
