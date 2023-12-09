package com.inqwise.opinion.infrastructure.common;

import java.io.Serializable;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public interface IOperationResult extends Serializable{
	
	public int getErrorCode();
	public JSONObject toJson() throws JSONException;
	public boolean hasError();
	public UUID getErrorId();
	public String getErrorDescription();
}
