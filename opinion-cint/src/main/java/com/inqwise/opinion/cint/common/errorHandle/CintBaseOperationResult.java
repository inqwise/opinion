package com.cint.common.errorHandle;

import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;

public class CintBaseOperationResult implements IOperationResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2922767482175705478L;
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
	
	public CintErrorCode getError() {
		return error;
	}

	public UUID getErrorId() {
		return errorId;
	}
	
	public String getErrorDescription(){
		return errorDescription;
	}
	
	protected CintErrorCode error = CintErrorCode.NoError;
	protected UUID errorId = null;
	
	protected String errorDescription;
	
	private Long transactionId = null;
	public Long getTransactionId(){
		return transactionId;
	}
	
	public boolean hasError(){
		return error != CintErrorCode.NoError;
	}
	
	public boolean hasErrorExcept(CintErrorCode... notErrors){
		return error != CintErrorCode.NoError && !ArrayUtils.contains(notErrors, error);
	}
	
	public CintBaseOperationResult(){
		this(CintErrorCode.NoError);
	}
	
	public CintBaseOperationResult(Long transactionId){
		this.transactionId = transactionId;
	}
	
	public CintBaseOperationResult(CintErrorCode error){
		this(error, UUID.randomUUID());
	}
	
	public CintBaseOperationResult(CintErrorCode error, UUID errorId){
		this(error, errorId, null);
	}
	
	public CintBaseOperationResult(CintErrorCode error, String description){
		this(error, UUID.randomUUID(), description);
	}
	
	public CintBaseOperationResult(CintErrorCode error, UUID errorId, String description){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = description;
	}
	
	public CintBaseOperationResult(CintErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = String.format(descriptionFormat, args);
	}
	
	public void setError(CintErrorCode error, UUID errorId){
		setError(error, errorId, null);
	}
	
	public void setError(CintErrorCode error, UUID errorId, String description){
		if(!hasError()){
			this.error = error;
			this.errorId = errorId;
			this.errorDescription = description;
		}
	}
	
	public void setError(CintBaseOperationResult result){
		setError(result, false);
	}
	
	public void setError(CintBaseOperationResult result, boolean isOverwrite){
		if(isOverwrite || !hasError()){
			this.error = result.getError();
			this.errorId = result.getErrorId();
			this.errorDescription = result.getErrorDescription();
		}
	}
	
	public <W> CintOperationResult<W> toErrorResult() {
		return new CintOperationResult<W>(error, errorId, errorDescription);
	}
	
	public String toString(){
		return hasError() ? error.toString() : transactionId.toString();
	}
	
	public final static CintBaseOperationResult Ok = new CintBaseOperationResult(CintErrorCode.NoError);
	
	public JSONObject toJson() throws JSONException {
		JSONObject result = null;
		
		result = new JSONObject().put("error", getError()).put("errorId", getErrorId());
		if(null != getErrorDescription()){
			result.put("errorDescription", getErrorDescription());
		}
		return result;
	}

	public static <T> CintOperationResult<T> validateWithoutLog(boolean validCondition,
			CintErrorCode error, String errorMessage) {
		
		if(!validCondition){
			return new CintOperationResult<T>(error, UUID.randomUUID(), errorMessage);
		}
		return null;
	}
	
	public boolean isGeneralError() {
		return error == CintErrorCode.GeneralError;
	}
}
