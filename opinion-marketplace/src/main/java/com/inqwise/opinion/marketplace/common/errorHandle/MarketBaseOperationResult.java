package com.inqwise.opinion.marketplace.common.errorHandle;

import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;

public class MarketBaseOperationResult implements IOperationResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6917661930571123821L;
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
	
	public MarketErrorCode getError() {
		return error;
	}

	public UUID getErrorId() {
		return errorId;
	}
	
	public String getErrorDescription(){
		return errorDescription;
	}
	
	protected MarketErrorCode error = MarketErrorCode.NoError;
	protected UUID errorId = null;
	
	protected String errorDescription;
	
	private Long transactionId = null;
	public Long getTransactionId(){
		return transactionId;
	}
	
	public boolean hasError(){
		return error != MarketErrorCode.NoError;
	}
	
	public boolean hasErrorExcept(MarketErrorCode... notErrors){
		return error != MarketErrorCode.NoError && !ArrayUtils.contains(notErrors, error);
	}
	
	public MarketBaseOperationResult(){
		this(MarketErrorCode.NoError);
	}
	
	public MarketBaseOperationResult(Long transactionId){
		this.transactionId = transactionId;
	}
	
	public MarketBaseOperationResult(MarketErrorCode error){
		this(error, UUID.randomUUID());
	}
	
	public MarketBaseOperationResult(MarketErrorCode error, UUID errorId){
		this(error, errorId, null);
	}
	
	public MarketBaseOperationResult(MarketErrorCode error, String description){
		this(error, UUID.randomUUID(), description);
	}
	
	public MarketBaseOperationResult(MarketErrorCode error, UUID errorId, String description){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = description;
	}
	
	public MarketBaseOperationResult(MarketErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = String.format(descriptionFormat, args);
	}
	
	public void setError(MarketErrorCode error, UUID errorId){
		setError(error, errorId, null);
	}
	
	public void setError(MarketErrorCode error, UUID errorId, String description){
		if(!hasError()){
			this.error = error;
			this.errorId = errorId;
			this.errorDescription = description;
		}
	}
	
	public void setError(MarketBaseOperationResult result){
		setError(result, false);
	}
	
	public void setError(MarketBaseOperationResult result, boolean isOverwrite){
		if(isOverwrite || !hasError()){
			this.error = result.getError();
			this.errorId = result.getErrorId();
			this.errorDescription = result.getErrorDescription();
		}
	}
	
	public <W> MarketOperationResult<W> toErrorResult() {
		return new MarketOperationResult<W>(error, errorId, errorDescription);
	}
	
	public String toString(){
		return hasError() ? error.toString() : transactionId.toString();
	}
	
	public final static MarketBaseOperationResult Ok = new MarketBaseOperationResult(MarketErrorCode.NoError);
	
	public JSONObject toJson() throws JSONException {
		JSONObject result = null;
		
		result = new JSONObject().put("error", getError()).put("errorId", getErrorId());
		if(null != getErrorDescription()){
			result.put("errorDescription", getErrorDescription());
		}
		return result;
	}

	public static <T> MarketOperationResult<T> validateWithoutLog(boolean validCondition,
			MarketErrorCode error, String errorMessage) {
		
		if(!validCondition){
			return new MarketOperationResult<T>(error, UUID.randomUUID(), errorMessage);
		}
		return null;
	}
	
	public boolean isGeneralError() {
		return error == MarketErrorCode.GeneralError;
	}
}
