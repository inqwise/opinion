package com.inqwise.opinion.common;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.json.JSONException;

public interface IHttpAnswererSession {
	public abstract void setLastViewedSheetIndex(Integer lastViewedSheetIndex)
	throws JSONException;

	public abstract Integer getLastViewedSheetIndex();
	
	public abstract void setFinishDate(Date finishDate) throws JSONException;
	
	public abstract Date getFinishDate();
	
	public abstract boolean isOpinionFinished();
	
	public abstract boolean getIsClientInfoReceived();
	
	public abstract void save() throws UnsupportedEncodingException;

	public abstract void setIsClientInfoReceived(boolean isClientInfoReceived) throws JSONException;

	public abstract String getSessionId();
	
	public abstract void setPasswordRestrictionMarker() throws JSONException;
	
	public abstract boolean havePasswordRestrictionMarker();
	
	public abstract String getKey();
}
