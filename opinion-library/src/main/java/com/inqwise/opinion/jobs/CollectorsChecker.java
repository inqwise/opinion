package com.inqwise.opinion.jobs;

import java.util.UUID;

import com.inqwise.opinion.automation.common.jobs.IJobExecutorCallback;
import com.inqwise.opinion.automation.common.jobs.Job;
import com.inqwise.opinion.automation.common.jobs.JobSettings;
import com.inqwise.opinion.cint.CintApiService;
import com.inqwise.opinion.cint.OrderDetailsRequest;
import com.inqwise.opinion.cint.common.IOrder;
import com.inqwise.opinion.cint.common.errorHandle.CintErrorCode;
import com.inqwise.opinion.cint.common.errorHandle.CintOperationResult;
import com.inqwise.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.common.collectors.IPanelSurveysCollector;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.managers.CollectorsManager;

public class CollectorsChecker extends Job {
	private static ApplicationLog logger = ApplicationLog.getLogger(CollectorsChecker.class);
	
	public CollectorsChecker(JobSettings settings, IJobExecutorCallback callback) {
		super(settings, callback);
	}

	@Override
	protected IOperationResult process() {
		IOperationResult result = null;
		
		try {
			result = checkVerifiedCollectors();
		} catch (Exception e) {
			UUID errorId = logger.error(e, "process: Unexpected error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

	private IOperationResult checkVerifiedCollectors() throws NullPointerException {
		IOperationResult result = null;
		var list = CollectorsManager.getMeny(null, null, false, 100, null, null, new Integer[] { CollectorStatus.Verify.getValue() }, null);
		
		for (var collectorModel : list) {
			IOperationResult checkResult = checkVerifiedCollector(CollectorsManager.get(collectorModel.getId(), null).getValue());
			if(checkResult.hasError()){
				result = checkResult;
			}
		}
		
		if(null == result){
			result = BaseOperationResult.Ok;
		}
		return result;
	}

	public IOperationResult checkVerifiedCollector(ICollector collector) {
		IOperationResult result = null;
		IOrder order = null;
		
		if(collector instanceof IPanelSurveysCollector) {//CINT
			IPanelSurveysCollector panelCollector = (IPanelSurveysCollector)collector;
			if(logger.IsDebugEnabled()){
				logger.debug("Starting to check collector #%s", collector.getId());
			}
			CintApiService service = new CintApiService();
			OrderDetailsRequest request = new OrderDetailsRequest();
			request.setLocationId(panelCollector.getExternalId());
			CintOperationResult<IOrder> orderResult = service.call(request);
			if(orderResult.hasError()){
				if(orderResult.getError() == CintErrorCode.NoResults){
					// panel deleted
					UUID errorId = logger.error("checkVerifiedCollector: In some reason Cint panel has been deleted. collector #'%s'", collector.getId());
					result = new BaseOperationResult(ErrorCode.NoResults, errorId);
				} 
			} else {
				order = orderResult.getValue();
			}
		} else {
			UUID errorTicket = logger.error("checkVerifiedCollector: collectorStatus not supported. collector #%s", collector.getId());
			result = new BaseOperationResult(ErrorCode.GeneralError, errorTicket);
		}
		
		if( null == result){
			// check status
			switch (order.getState()) {
				case Failed:
				case Closed:
					if(collector.getCountOfFinishedOpinions() > 0){
						logger.error("checkVerifiedCollector: collector #'%s' has been '%s'", collector.getId(), order.getState());
					}
				
				case Cancelled:
					logger.warn("checkVerifiedCollector: Found order #%s with status '%s' referenced to collector #%s. Staring to cancel collector and referenced invoices", order.getOrderNumber(), order.getState(), collector.getId());
				/*	
					IOperationResult cancelResult = cancelInvoices(collector);
					if(cancelResult.hasError()){
						result = cancelResult;
					} else {
						result = collector.changeStatus(CollectorStatus.Canceled, null);
					}
				*/
					result = BaseOperationResult.Ok;
					break;
				
				case Completed:
					if(logger.IsInfoEnabled()){
						logger.info("checkVerifiedCollector: Found order #%s with status '%s' referenced to collector #%s. Collector will be closed", order.getOrderNumber(), order.getState(), collector.getId());
					}
					collector.changeStatus(CollectorStatus.Closed, null);
					result = BaseOperationResult.Ok;
					break;
				
				case Approved:
				case Denied:
				case Hold:
				case New:
				case Pending:
					result = BaseOperationResult.Ok;
					break;
				
				case Live:
					if(logger.IsInfoEnabled()){
						logger.info("checkVerifiedCollector: Order #%s is Live. changing status for collector #%s.", order.getOrderNumber(), collector.getId());
					}
					collector.changeStatus(CollectorStatus.Open, null);
					result = BaseOperationResult.Ok;
					break;
				
				default:
					UUID errorId = logger.error("Unimplemented orderState received. state: '%s', collectorId: '%s'", order.getState(), collector.getId());
					result = new BaseOperationResult(ErrorCode.ArgumentOutOfRange, errorId);
					break;
			}
		}
		return result;
	}

	/*
	private IOperationResult cancelInvoices(ICollector collector) {
		IOperationResult result = null;
		if(null != collector.getServiceId()){
			try {
				CDataCacheContainer chargesSet = ChargesManager.getChargesByReferenceId(null, collector.getId(), collector.getServiceId());
				CDataRowSet rowSet = chargesSet.getAll();
				while(rowSet.next()){
					ChargeStatus chargeStatus = ChargeStatus.fromInt(rowSet.getInt("status_id"));
					logger.info("cancelInvoices: Found invoice #%s with status '%s' referenced to collector #%s", rowSet.getLong("invoice_id"), chargeStatus, collector.getId());
					if(chargeStatus == ChargeStatus.Paid || chargeStatus == ChargeStatus.Unpaid){
						result = InvoicesManager.cancelInvoice(rowSet.getLong("invoice_id"), ProductsManager.getCurrentProduct().getGuid(), rowSet.getLong("for_account_id"), null, "order Canceled");
					} 
					
					if(null != result){
						break;
					}
				}
				
			} catch (Throwable e) {
				UUID errorId = logger.error(e, "cancelInvoice : Unexpected error occured. collector #'%s'", collector.getId());  
				result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
			}
		}
		
		if(null == result){
			result = BaseOperationResult.Ok;
		}
		
		return result;
	}
	*/
}
