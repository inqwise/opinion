package com.inqwise.opinion.library.common.errorHandle;

public class NotAllowedOperationResult<T> extends OperationResult<T> {

	Long accountId;
	
	public NotAllowedOperationResult(ErrorCode error, Long accountId) {
		super(error);
		this.accountId = accountId;
	}
	
	public long getAccountId(){
		return accountId;
	}
}
