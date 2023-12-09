package com.inqwise.opinion.payments.managers;

import java.sql.ResultSet;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.payments.common.errorHandle.PayErrorCode;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;
import com.inqwise.opinion.payments.common.transactions.IPayTransaction;
import com.inqwise.opinion.payments.dao.PayDataAccess;
import com.inqwise.opinion.payments.entities.TransactionEntity;

public class TransactionsManager {

	static final ApplicationLog Logger = ApplicationLog.getLogger(TransactionsManager.class);
			
	public static PayOperationResult<IPayTransaction> get(long id){
		final PayOperationResult<IPayTransaction> result = new PayOperationResult<IPayTransaction>();
		
		try{
		IResultSetCallback callback = new IResultSetCallback() {
			
			@Override
			public void call(ResultSet reader, int generationId) throws Exception {
				while(reader.next()){
					// Current transaction
					if(1 == generationId){
						if(result.hasError() || result.hasValue()){
							throw new UnsupportedOperationException("More than one result receaved from DB");
						}
						
						IPayTransaction transaction = new TransactionEntity(reader);
						result.setValue(transaction);
					}
				}
			}
		};
		
		PayDataAccess.fillTransaction(id, callback);
		
		if(!result.hasError() && !result.hasValue()){
			Logger.warn("get : no results found for payTransaction #" + id);
			result.setError(PayErrorCode.NoResults);
		}
		} catch(DAOException ex){
			UUID errorId = Logger.error("get : Unexpected error occured, payTransaction #" + id);
			result.setError(PayErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
}
