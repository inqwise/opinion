package com.inqwise.opinion.actions.opinions;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.actions.ActionFlow;
import com.inqwise.opinion.actions.opinions.ICreateOpinionRequest.IRequestExtension;
import com.inqwise.opinion.dao.IInsertOpinionParams;

public class ImportOpinionFlow/*<TRequest extends IImportOpinionRequest> extends ActionFlow<TRequest, OperationResult<Long>> implements IInsertOpinionParams*/ {

	protected static ApplicationLog logger = ApplicationLog.getLogger(ImportOpinionFlow.class);
	private OperationResult<Long> insertOpinionResult;
	private Long opinionId;
	//private TRequest request;
	private IRequestExtension requestExtension;
	private IAccountView account;
	
	public ImportOpinionFlow() {
		// TODO Auto-generated constructor stub
	}

}
