package com.inqwise.opinion.automation.common.errorHandle;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;

public class AutomationBaseOperationResult implements IOperationResult, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8194420564047053315L;
	public static JSONObject JsonOk;
	static {
		try {
			JsonOk = new JSONObject().putOpt("result", "done");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public int getErrorCode() {
		return error.ordinal();
	}
	
	public AutomationErrorCode getError() {
		return error;
	}

	public UUID getErrorId() {
		return errorId;
	}
	
	public String getErrorDescription(){
		return errorDescription;
	}
	
	protected AutomationErrorCode error = AutomationErrorCode.NoError;
	protected UUID errorId = null;
	
	protected String errorDescription;
	
	private Long transactionId = null;
	public Long getTransactionId(){
		return transactionId;
	}
	
	public boolean hasError(){
		return error != AutomationErrorCode.NoError;
	}
	
	public boolean hasErrorExcept(AutomationErrorCode... notErrors){
		return error != AutomationErrorCode.NoError && !ArrayUtils.contains(notErrors, error);
	}
	
	public AutomationBaseOperationResult(){
		this(AutomationErrorCode.NoError);
	}
	
	public AutomationBaseOperationResult(Long transactionId){
		this.transactionId = transactionId;
	}
	
	public AutomationBaseOperationResult(AutomationErrorCode error){
		this(error, UUID.randomUUID());
	}
	
	public AutomationBaseOperationResult(AutomationErrorCode error, UUID errorId){
		this(error, errorId, null);
	}
	
	public AutomationBaseOperationResult(AutomationErrorCode error, String description){
		this(error, UUID.randomUUID(), description);
	}
	
	public AutomationBaseOperationResult(AutomationErrorCode error, UUID errorId, String description){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = description;
	}
	
	public AutomationBaseOperationResult(AutomationErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = String.format(descriptionFormat, args);
	}
	
	public void setError(AutomationErrorCode error, UUID errorId){
		setError(error, errorId, null);
	}
	
	public void setError(AutomationErrorCode error, UUID errorId, String description){
		if(!hasError()){
			this.error = error;
			this.errorId = errorId;
			this.errorDescription = description;
		}
	}
	
	public void setError(AutomationBaseOperationResult result){
		setError(result, false);
	}
	
	public void setError(AutomationBaseOperationResult result, boolean isOverwrite){
		if(isOverwrite || !hasError()){
			this.error = result.getError();
			this.errorId = result.getErrorId();
			this.errorDescription = result.getErrorDescription();
		}
	}
	
	public <W> AutomationOperationResult<W> toErrorResult() {
		return new AutomationOperationResult<W>(error, errorId, errorDescription);
	}
	
	public String toString(){
		return hasError() ? error.toString() : transactionId.toString();
	}
	
	public final static AutomationBaseOperationResult Ok = new AutomationBaseOperationResult(AutomationErrorCode.NoError);
	
	public JSONObject toJson() throws JSONException {
		JSONObject result = null;
		
		result = new JSONObject().put("error", getError()).put("errorId", getErrorId());
		if(null != getErrorDescription()){
			result.put("errorDescription", getErrorDescription());
		}
		return result;
	}

	public static <T> AutomationOperationResult<T> validateWithoutLog(boolean validCondition,
			AutomationErrorCode error, String errorMessage) {
		
		if(!validCondition){
			return new AutomationOperationResult<T>(error, UUID.randomUUID(), errorMessage);
		}
		return null;
	}
	
	public boolean isGeneralError() {
		return error == AutomationErrorCode.GeneralError;
	}
}
