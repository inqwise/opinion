package com.inqwise.opinion.library.managers;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.casper.data.model.CDataCacheContainer;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.accounts.IAccountOperation;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.dao.AccountsOperationsDataAccess;
import com.inqwise.opinion.library.dao.ChargesDataAccess;
import com.inqwise.opinion.library.dao.InvoicesDataAccess;
import com.inqwise.opinion.library.entities.accounts.AccountOperationEntity;
import com.inqwise.opinion.library.entities.pay.ChargeEntity;

public class AccountsOperationsManager {
	static ApplicationLog logger = ApplicationLog.getLogger(AccountsOperationsManager.class);
	
	public static CDataCacheContainer getAccountOperations(int top, long accountId,
			List<Integer> accountsOperationsTypeIds, Long referenceId, Integer referenceTypeId, Date fromDate, Date toDate, Boolean monetary) {
		
		try {
			return AccountsOperationsDataAccess.getAccountOperations(top, accountId,
					accountsOperationsTypeIds, referenceId, referenceTypeId, fromDate, toDate, monetary);
		} catch (DAOException e) {
			throw new Error(e);
		}
	}
	
public static OperationResult<IAccountOperation> get(long accountOperationId){
		
		final OperationResult<IAccountOperation> result = new OperationResult<IAccountOperation>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							IAccountOperation opearation = new AccountOperationEntity(reader);
							result.setValue(opearation);							
						}
					}
				}
			};
			
			AccountsOperationsDataAccess.getResultSet(accountOperationId, callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "get() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
		
	}
}
