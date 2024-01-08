package com.inqwise.opinion.jobs;

import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.automation.common.jobs.IJobExecutorCallback;
import com.inqwise.opinion.automation.common.jobs.Job;
import com.inqwise.opinion.automation.common.jobs.JobSettings;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.common.SessionCreditInfo;
import com.inqwise.opinion.dao.OpinionAccountDataAccess;

public class SessionsAmountCreditor extends Job {
	
	public SessionsAmountCreditor(JobSettings settings, IJobExecutorCallback callback) {
		super(settings, callback);
	}

	@Override
	public IOperationResult process() {
		BaseOperationResult result = null;
		try{
			result = creditAmounts();
		}catch(Exception e){
			UUID errorId = logger.error(e, "SessionsAmountCreditor.process() : Unexpected error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, e.getMessage());
		}
		return result;
	}

	private BaseOperationResult creditAmounts() throws Exception {
		OperationResult<List<SessionCreditInfo>> creditResult = OpinionAccountDataAccess.setSessionsCredit(getId());
		
		if(!creditResult.hasError()){
			for(SessionCreditInfo info : creditResult.getValue()){
				//TODO: send actual Email
				logger.info("Sessions balance was align to '%s' for account #%s, servicePackageId: '%s'", info.getSessionAmountBalance(), info.getAccountId(), info.getServicePackageId());
			}
		}
		
		if(creditResult.hasErrorExcept(ErrorCode.NoResults)){
			return creditResult;
		} else {
			return BaseOperationResult.Ok;
		}
	}

	private int getId() {
		return getSettings().getId();
	}
}
