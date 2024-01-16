package com.inqwise.opinion.library.managers;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.InvoiceItemModel;
import com.inqwise.opinion.library.common.InvoiceItemRepositoryParser;
import com.inqwise.opinion.library.common.InvoiceModel;
import com.inqwise.opinion.library.common.InvoicesRepositoryParser;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.BillType;
import com.inqwise.opinion.library.common.pay.IInvoice;
import com.inqwise.opinion.library.common.pay.IInvoiceCreateRequest;
import com.inqwise.opinion.library.common.pay.IOpenInvoiceRequest;
import com.inqwise.opinion.library.common.pay.IUpdateInvoiceRequest;
import com.inqwise.opinion.library.common.pay.InvoiceItemType;
import com.inqwise.opinion.library.dao.InvoicesDataAccess;
import com.inqwise.opinion.library.entities.pay.InvoiceEntity;

public class InvoicesManager {
	public static final int DEFAULT_EXPIRATION_PERIOD_IN_DAYS = 30;
	static ApplicationLog logger = ApplicationLog.getLogger(InvoicesManager.class);
	
	public static OperationResult<IInvoice> getInvoice(long invoiceId, Long accountId, Integer statusId){
		final OperationResult<IInvoice> result = new OperationResult<IInvoice>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							IInvoice invoice = new InvoiceEntity(reader);
							result.setValue(invoice);							
						}
					}
				}
			};
			
			InvoicesDataAccess.getInvoiceResultSet(invoiceId, callback, accountId, statusId);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getInvoice() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<IInvoice> createInvoice(IInvoiceCreateRequest request){
		final OperationResult<IInvoice> result = new OperationResult<IInvoice>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							IInvoice invoice = new InvoiceEntity(reader);
							result.setValue(invoice);							
						}
					}
				}
			};
			
			InvoicesDataAccess.insertInvoice(request, callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "createInvoice() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult deleteInvoice(long invoiceId, long userId){
		BaseOperationResult result;
		try{
			if(InvoicesDataAccess.deleteInvoice(invoiceId, userId)){
				if(logger.IsDebugEnabled()){
					logger.debug("deleteInvoice() : Invoice #%s deleted.", invoiceId);
				}
				result = BaseOperationResult.Ok;
			} else {
				logger.warn("deleteInvoice() : InvoiceId '%s' not deleted. Check condition.", invoiceId);
				result = new BaseOperationResult(ErrorCode.NoResults);
			}
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "deleteInvoice() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static List<InvoiceModel> getInvoices(int top, Long accountId,Integer invoiceStatusId, boolean includeDue) {
		try {
			var arr = InvoicesDataAccess.getInvoices(top, accountId, invoiceStatusId);
			var toList = JSONHelper.toListOfModel(arr, new InvoicesRepositoryParser()::parse);
			return toList;
		} catch (DAOException e) {
			throw new Error(e);
		}
	}
	
	public static BaseOperationResult updateInvoice(
			IUpdateInvoiceRequest request) {
		BaseOperationResult result = null;
		try {
			InvoicesDataAccess.updateInvoice(request);
			return BaseOperationResult.Ok;
		} catch (Exception e) {
			UUID errorId = logger.error(e, "updateInvoice() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static BaseOperationResult openInvoice(
			IOpenInvoiceRequest request) {
		BaseOperationResult result = null;
		try {
			return InvoicesDataAccess.openInvoice(request);
		} catch (Exception e) {
			UUID errorId = logger.error(e, "openInvoice() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static Map<InvoiceItemType, List<InvoiceItemModel>> getInvoiceItems(long invoiceId){
		try {
			Map<InvoiceItemType, List<InvoiceItemModel>> resultMap = Maps.newHashMap();
			
			var map = InvoicesDataAccess.getInvoiceItems(invoiceId, BillType.Invoice.getValue());
			for (var mapItem : map.entrySet()) {
				var toList = JSONHelper.toListOfModel(mapItem.getValue(), new InvoiceItemRepositoryParser()::parse);
				resultMap.put(mapItem.getKey(), toList);
				
			}
			return resultMap;
		} catch (DAOException e) {
			throw new Error(e);
		}
	}
}
