package com.inqwise.opinion.api.common;

import java.io.Serializable;
import java.util.Date;

public class AnswererSessionArgs implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3393194440722033807L;

	public AnswererSessionArgs(String sessionId, Integer lastViewedSheetIndex,
			Boolean isClientInfoReceived, Boolean isPasswordRequired,
			Date finishDate) {
		super();
		this.sessionId = sessionId;
		this.lastViewedSheetIndex = lastViewedSheetIndex;
		this.isClientInfoReceived = isClientInfoReceived;
		this.isPasswordRequired = isPasswordRequired;
		this.finishDate = finishDate;
	}

	private String sessionId;
	private Integer lastViewedSheetIndex;
	private Boolean isClientInfoReceived;
	private Boolean isPasswordRequired;
	private Date finishDate;
	
	public AnswererSessionArgs() {
	}
	
	public Integer getLastViewedSheetIndex() {
		return lastViewedSheetIndex;
	}
	public void setLastViewedSheetIndex(Integer lastViewedSheetIndex) {
		this.lastViewedSheetIndex = lastViewedSheetIndex;
	}
	public Boolean getIsClientInfoReceived() {
		return isClientInfoReceived;
	}
	public void setIsClientInfoReceived(Boolean isClientInfoReceived) {
		this.isClientInfoReceived = isClientInfoReceived;
	}
	public Boolean getIsPasswordRequired() {
		return isPasswordRequired;
	}
	public void setIsPasswordRequired(Boolean isPasswordRequired) {
		this.isPasswordRequired = isPasswordRequired;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public AnswererSessionArgs clone(){
		return new AnswererSessionArgs(sessionId, lastViewedSheetIndex, isClientInfoReceived, isPasswordRequired, finishDate);
	}
}