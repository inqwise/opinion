package com.inqwise.opinion.library.common.errorHandle;

import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.javatuples.KeyValue;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class BaseOperationResult implements IOperationResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4542704316085947141L;
	static ApplicationLog logger = ApplicationLog.getLogger(BaseOperationResult.class);
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
	
	public ErrorCode getError() {
		return error;
	}

	public UUID getErrorId() {
		return errorId;
	}
	
	public String getErrorDescription(){
		return errorDescription;
	}
	
	protected ErrorCode error = ErrorCode.NoError;
	protected UUID errorId = null;
	
	protected String errorDescription;
	
	private Long transactionId = null;
	public Long getTransactionId(){
		return transactionId;
	}
	
	public boolean hasError(){
		return error != ErrorCode.NoError;
	}
	
	public boolean hasErrorExcept(ErrorCode... notErrors){
		return error != ErrorCode.NoError && !ArrayUtils.contains(notErrors, error);
	}
	
	public boolean hasError(ErrorCode... errors){
		return null != errors && ArrayUtils.contains(errors, error);
	}
	
	public BaseOperationResult(){
		this(ErrorCode.NoError);
	}
	
	public BaseOperationResult(Long transactionId){
		this.transactionId = transactionId;
	}
	
	public BaseOperationResult(ErrorCode error){
		this(error, UUID.randomUUID());
	}
	
	public BaseOperationResult(ErrorCode error, UUID errorId){
		this(error, errorId, null);
	}
	
	public BaseOperationResult(ErrorCode error, String description){
		this(error, UUID.randomUUID(), description);
	}
	
	public BaseOperationResult(ErrorCode error, UUID errorId, String description){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = description;
	}
	
	public BaseOperationResult(ErrorCode error, UUID errorId, String descriptionFormat, Object... args){
		this.error = error;
		this.errorId = errorId;
		this.errorDescription = String.format(descriptionFormat, args);
	}
	
	public void setError(ErrorCode error, UUID errorId){
		setError(error, errorId, null);
	}
	
	public void setError(ErrorCode error, String description){
		setError(error, UUID.randomUUID(), description);
	}
	
	public void setError(ErrorCode error, UUID errorId, String description){
		if(!hasError()){
			this.error = error;
			this.errorId = errorId;
			this.errorDescription = description;
		}
	}
	
	@SafeVarargs
	public final void setError(BaseOperationResult result, KeyValue<ErrorCode, ErrorCode>... errorsMap){
		setError(result, false, errorsMap);
	}
	
	public void setError(BaseOperationResult result){
		setError(result, false);
	}
	
	public void setError(BaseOperationResult result, boolean isOverwrite){
		setError(result, isOverwrite, (KeyValue<ErrorCode, ErrorCode>[])null);
	}
	
	public void setError(BaseOperationResult result, boolean isOverwrite, KeyValue<ErrorCode, ErrorCode>... errorsMap){
		
		if(isOverwrite || !hasError()){
			this.error = result.getError();
			if(null != errorsMap){
				for (KeyValue<ErrorCode, ErrorCode> entity : errorsMap) {
					if(entity.getKey() == this.error){
						this.error = entity.getValue();
						break;
					}
				}
			}
			this.errorId = result.getErrorId();
			this.errorDescription = result.getErrorDescription();
		}
	}
	
	public <W> OperationResult<W> toErrorResult() {
		return new OperationResult<W>(error, errorId, errorDescription);
	}
	
	public String toString(){
		return hasError() ? error.toString() : transactionId.toString();
	}
	
	public final static BaseOperationResult Ok = new BaseOperationResult(ErrorCode.NoError);
	public static final BaseOperationResult NotImplemented = new BaseOperationResult(ErrorCode.NotImplemented);
	
	public JSONObject toJson() throws JSONException {
		JSONObject result = null;
		
		result = new JSONObject().put("error", getError()).put("errorId", getErrorId());
		if(null != getErrorDescription()){
			result.put("errorDescription", getErrorDescription());
		}
		return result;
	}

	public static <T> OperationResult<T> validateWithoutLog(boolean validCondition,
			ErrorCode error, String errorMessage) {
		
		if(!validCondition){
			return new OperationResult<T>(error, UUID.randomUUID() , errorMessage);
		}
		return null;
	}
	
	public boolean isGeneralError() {
		return error == ErrorCode.GeneralError;
	}
	
	public boolean isNoResultsError() {
		return error == ErrorCode.NoResults;
	}

	public static JSONObject toJsonGeneralError(Throwable t, UUID errorId) throws JSONException {
		JSONObject output = new JSONObject();
		output.put("error", ErrorCode.GeneralError).put("errorId",
				errorId).put("description", t.getMessage());
		return output;
	}
	
	public void setTransactionId(long transactionId){
		this.transactionId = transactionId;
	}

	public void setError(ErrorCode errorCode) {
		setError(errorCode , UUID.randomUUID());
	}
	
	public BaseOperationResult validate(Boolean condition, ErrorCode errorCode, String messageFormat,
			Object... args) {
				return validate(condition, errorCode, String.format(messageFormat, args));
			}

	public BaseOperationResult validate(Boolean condition, ErrorCode errorCode, String message) {
		if(!hasError() && !condition){
			if(errorCode == ErrorCode.GeneralError){
				this.errorId = logger.error(message);
			} else {
				logger.warn(message);
				this.errorId = UUID.randomUUID();
			}
			this.error = errorCode;
			this.errorDescription = message;
		}
		
		return this;
	}

	public static JSONObject toJsonError(ErrorCode errorCode) throws JSONException {
		return new JSONObject().put("error", errorCode);
	}
}
