package com.inqwise.opinion.payments.common.errorHandle;

import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;

public class PayBaseOperationResult implements IOperationResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8221960054663345853L;
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
	
	public PayErrorCode getError() {
		return error;
	}

	public UUID getErrorId() {
		return errorId;
	}
	
	public String getErrorDescription(){
		return errorDescription;
	}
	
	protected PayErrorCode error = PayErrorCode.NoError;
	protected UUID errorId = null;
	
	protected String errorDescription;
	
	private Long transactionId = null;
	public Long getTransactionId(){
		return transactionId;
	}
	
	public boolean hasError(){
		return error != PayErrorCode.NoError;
	}
	
	public boolean hasErrorExcept(PayErrorCode... notErrors){
		return error != PayErrorCode.NoError && !ArrayUtils.contains(notErrors, error);
	}
	
	public PayBaseOperationResult(){
		this(PayErrorCode.NoError);
	}
	
	public PayBaseOperationResult(Long transactionId){
		this.transactionId = transactionId;
	}
	
	public PayBaseOperationResult(PayErrorCode error){
		this(error, UUID.randomUUID());
	}
	
	public PayBaseOperationResult(PayErrorCode error, UUID errorId){
		this(error, errorId, null);
	}
	
	public PayBaseOperationResult(PayErrorCode error, String description){
		this(error, UUID.randomUUID(), description);
	}
	
	public PayBaseOperationResult(PayErrorCode error, UUID errorId, String description){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = description;
	}
	
	public PayBaseOperationResult(PayErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = String.format(descriptionFormat, args);
	}
	
	public void setError(PayErrorCode error, UUID errorId){
		setError(error, errorId, null);
	}
	
	public void setError(PayErrorCode error, UUID errorId, String description){
		if(!hasError()){
			this.error = error;
			this.errorId = errorId;
			this.errorDescription = description;
		}
	}
	
	public void setError(PayBaseOperationResult result){
		setError(result, false);
	}
	
	public void setError(PayBaseOperationResult result, boolean isOverwrite){
		if(isOverwrite || !hasError()){
			this.error = result.getError();
			this.errorId = result.getErrorId();
			this.errorDescription = result.getErrorDescription();
		}
	}
	
	public <W> PayOperationResult<W> toErrorResult() {
		return new PayOperationResult<W>(error, errorId, errorDescription);
	}
	
	public String toString(){
		return hasError() ? error.toString() : transactionId.toString();
	}
	
	public final static PayBaseOperationResult Ok = new PayBaseOperationResult(PayErrorCode.NoError);
	
	public JSONObject toJson() throws JSONException {
		JSONObject result = null;
		
		result = new JSONObject().put("error", getError()).put("errorId", getErrorId());
		if(null != getErrorDescription()){
			result.put("errorDescription", getErrorDescription());
		}
		return result;
	}

	public static <T> PayOperationResult<T> validateWithoutLog(boolean validCondition,
			PayErrorCode error, String errorMessage) {
		
		if(!validCondition){
			return new PayOperationResult<T>(error, UUID.randomUUID() , errorMessage);
		}
		return null;
	}
	
	public boolean isGeneralError() {
		return error == PayErrorCode.GeneralError;
	}
}
