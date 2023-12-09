package com.inqwise.opinion.cms.common.errorHandle;

import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;

public class BaseCmsOperationResult implements IOperationResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4951888820110652301L;
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
	
	public CmsErrorCode getError() {
		return error;
	}

	public UUID getErrorId() {
		return errorId;
	}
	
	public String getErrorDescription(){
		return errorDescription;
	}
	
	protected CmsErrorCode error = CmsErrorCode.NoError;
	protected UUID errorId = null;
	
	protected String errorDescription;
	
	private Long transactionId = null;
	public Long getTransactionId(){
		return transactionId;
	}
	
	public boolean hasError(){
		return error != CmsErrorCode.NoError;
	}
	
	public boolean hasErrorExcept(CmsErrorCode... notErrors){
		return error != CmsErrorCode.NoError && !ArrayUtils.contains(notErrors, error);
	}
	
	public BaseCmsOperationResult(){
		this(CmsErrorCode.NoError);
	}
	
	public BaseCmsOperationResult(Long transactionId){
		this.transactionId = transactionId;
	}
	
	public BaseCmsOperationResult(CmsErrorCode error){
		this(error, UUID.randomUUID());
	}
	
	public BaseCmsOperationResult(CmsErrorCode error, UUID errorId){
		this(error, errorId, null);
	}
	
	public BaseCmsOperationResult(CmsErrorCode error, String description){
		this(error, UUID.randomUUID(), description);
	}
	
	public BaseCmsOperationResult(CmsErrorCode error, UUID errorId, String description){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = description;
	}
	
	public BaseCmsOperationResult(CmsErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = String.format(descriptionFormat, args);
	}
	
	public void setError(CmsErrorCode error, UUID errorId){
		setError(error, errorId, null);
	}
	
	public void setError(CmsErrorCode error, UUID errorId, String description){
		if(!hasError()){
			this.error = error;
			this.errorId = errorId;
			this.errorDescription = description;
		}
	}
	
	public void setError(BaseCmsOperationResult result){
		setError(result, false);
	}
	
	public void setError(BaseCmsOperationResult result, boolean isOverwrite){
		if(isOverwrite || !hasError()){
			this.error = result.getError();
			this.errorId = result.getErrorId();
			this.errorDescription = result.getErrorDescription();
		}
	}
	
	public <W> CmsOperationResult<W> toErrorResult() {
		return new CmsOperationResult<W>(error, errorId, errorDescription);
	}
	
	public String toString(){
		return hasError() ? error.toString() : transactionId.toString();
	}
	
	public final static BaseCmsOperationResult Ok = new BaseCmsOperationResult(CmsErrorCode.NoError);
	
	public JSONObject toJson() throws JSONException {
		JSONObject result = null;
		
		result = new JSONObject().put("error", getError()).put("errorId", getErrorId());
		if(null != getErrorDescription()){
			result.put("errorDescription", getErrorDescription());
		}
		return result;
	}

	public static <T> CmsOperationResult<T> validateWithoutLog(boolean validCondition,
			CmsErrorCode error, String errorMessage) {
		
		if(!validCondition){
			return new CmsOperationResult<T>(error, UUID.randomUUID() , errorMessage);
		}
		return null;
	}
	
	public boolean isGeneralError() {
		return error == CmsErrorCode.GeneralError;
	}
}
