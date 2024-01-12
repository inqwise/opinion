package com.inqwise.opinion.managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.inqwise.opinion.actions.collectors.CollectorsActionsFactory;
import com.inqwise.opinion.actions.collectors.ICreatePollsCollectorRequest;
import com.inqwise.opinion.actions.collectors.ICreateSurveysCollectorRequest;
import com.inqwise.opinion.common.ResultsPermissionType;
import com.inqwise.opinion.common.collectors.CollectorModel;
import com.inqwise.opinion.common.collectors.CollectorRepositoryParser;
import com.inqwise.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.common.collectors.ICollector.JsonNames;
import com.inqwise.opinion.common.collectors.IDeletedCollectorDetails;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.dao.CollectorsDataAccess;
import com.inqwise.opinion.entities.collectors.CollectorEntity;
import com.inqwise.opinion.entities.collectors.SurveysCollectorEntity;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ChargeReferenceType;
import com.inqwise.opinion.library.managers.ChargesManager;

public final class CollectorsManager {

	public static ApplicationLog logger = ApplicationLog.getLogger(CollectorsManager.class);

	public static BaseOperationResult delete(Long collectorId,
			Long accountId, long userId) {
		OperationResult<IDeletedCollectorDetails> result = SurveysCollectorEntity.delete(collectorId, accountId, userId);
		
		if(!result.hasError()){
			IDeletedCollectorDetails collectorDetails = result.getValue();
			if(collectorDetails.getStatus() == CollectorStatus.PendingPayment){
				ChargesManager.cancelOrder(collectorDetails.getCollectorId(), ChargeReferenceType.Collector.getValue(), accountId, userId);
			}
		}
		return result;
	}
	
	private static OperationResult<ICollector> get(Long collectorId, String collectorUuid, Long accountId){
		final OperationResult<ICollector> result = new OperationResult<ICollector>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							CollectorEntity collector = CollectorEntity.identifyCollector(reader);
							result.setValue(collector);
						}
					}
				}
			};
			
			CollectorsDataAccess.getCollector(callback, collectorId, collectorUuid, accountId);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getCollector() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<ICollector> get(long collectorId, Long accountId){
		return get(collectorId, null, accountId);
	}
	
	private static Object _collectorsByGuidCacheLocker = new Object(); 
	private static LoadingCache<String, OperationResult<ICollector>> _collectorsByGuidCache;
	private static LoadingCache<String, OperationResult<ICollector>> getCollectorsByGuidCache(){
		if(null == _collectorsByGuidCache){
			synchronized (_collectorsByGuidCacheLocker) {
				if(null == _collectorsByGuidCache){
					_collectorsByGuidCache = CacheBuilder.newBuilder().
							maximumSize(1000).
							expireAfterWrite(20, TimeUnit.SECONDS).
							build( new CacheLoader<String, OperationResult<ICollector>>() {
								public OperationResult<ICollector> load(String guid) throws Exception {
									return internalGet(guid);
								}
							});
				}
			}
		}
		
		return _collectorsByGuidCache;
	}
	
	private static OperationResult<ICollector> internalGet(String collectorUuid){
		return get(null, collectorUuid, null);
	}
	
	public static OperationResult<ICollector> get(String collectorUuid){
		OperationResult<ICollector> result;
		try {
			result = getCollectorsByGuidCache().get(collectorUuid);
		} catch (ExecutionException e) {
			UUID errorTicket = logger.error(e, "get: Unexpected error occured");
			result = new OperationResult<ICollector>(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}
	
	public static BaseOperationResult modifyStatus(Long collectorId, int statusId,
			Long accountId, String closeMessage, UUID collectorUuid, Long userId) {
		
		BaseOperationResult result = null;
		if(null == result){
			result = BaseOperationResult.validateWithoutLog(CollectorStatus.contains(statusId), ErrorCode.ArgumentOutOfRange, "Undefined collector status");
		}
		
		if(null == result){
			result = BaseOperationResult.validateWithoutLog(null != collectorId, ErrorCode.ArgumentNull, "'collectorId' is mandatory");
		}
		
		if(null == result){
			result = SurveysCollectorEntity.updateStatus(collectorId, statusId, accountId, closeMessage, collectorUuid, userId);
		}
		
		if(result.hasError() && !result.isGeneralError()) {
			logger.warn("updateCollectorStatus() : Failed in validation. collectorId: '%s', statusId: '%s', accountId: '%s'. ValidationDetails: %s", collectorId, statusId, accountId, result);
		}
		
		return result;
	}

	public static BaseOperationResult modifyName(Long collectorId,
			Long accountId, String name, String actionGuid, long userId) {
		return SurveysCollectorEntity.changeOpinionName(collectorId, accountId, name,
				actionGuid, userId);
	}
		
	public static OperationResult<List<BaseOperationResult>> deleteMeny(
			List<Long> collectorIds, Long accountId, long userId) {
		List<BaseOperationResult> resultsList = new ArrayList<BaseOperationResult>();
		
		for (Long collectorId : collectorIds) {
			BaseOperationResult deleteResult = delete(collectorId, accountId, userId);
			if(deleteResult.hasError()){
				resultsList.add(deleteResult);
			} else {
				resultsList.add(new BaseOperationResult(collectorId));
			}
		}
		
		return new OperationResult<List<BaseOperationResult>>(resultsList);
	}

	public static BaseOperationResult setExternalId(long collectorId,
			String externalId, CollectorStatus status, Long accountId,
			Date expirationDate, Long userId, Long responseQuota) {
		
		BaseOperationResult result = null;
		
		if(null == result){
			result = BaseOperationResult.validateWithoutLog(collectorId > 0, ErrorCode.ArgumentMandatory, "collectorId is mandatory");
		}
		
		if(null == result){
			result = BaseOperationResult.validateWithoutLog(CollectorStatus.PendingPayment == status, ErrorCode.ArgumentOutOfRange, "Only 'PandingPayment' status supported");
		}
		
		if(null == result){
			try{
				result = CollectorsDataAccess.setCollectorExternalId(collectorId, externalId, status.getValue(), accountId, expirationDate, userId, responseQuota);
			} catch (Throwable t){
				UUID ticket = logger.error(t, "setExternalId : Unexpected error occured");
				result = new BaseOperationResult(ErrorCode.GeneralError, ticket);
			}
		}
		return result;
	}
	
	public static List<CollectorModel> getMeny(Long opinionId, Long accountId, boolean includeExpired, int top, Date from, Date to, Integer[] collectorsStatusIds, String orderBy){
		try {
			List<CollectorModel> list = new ArrayList<>();
			JSONArray arr = CollectorsDataAccess.getCollectors(opinionId, accountId, includeExpired, top, from, to, collectorsStatusIds, orderBy);

			arr.forEach(itm -> {
				list.add(new CollectorRepositoryParser().parse((JSONObject)itm));						
			});
			return list;
			
		} catch (DAOException e) {
			throw new Error(e);
		}
	}

	public static OperationResult<ICollector> create(final Long opinionId,
			long accountId, final String name, final Integer collectorSourceId, final Long userId) {
		OperationResult<ICollector> result = null;
		
		IOpinion opinion = null;
		OperationResult<IOpinion> opinionResult = OpinionsManager.getOpinion(opinionId, accountId);
		if(opinionResult.hasError()){
			result = opinionResult.toErrorResult();
		} else {
			opinion = opinionResult.getValue();
		}
		
		Long collectorId = null;
		if(null == result){
			OperationResult<Long> createResult;
			switch (opinion.getOpinionType()) {
			case Poll:
				createResult = CollectorsActionsFactory.getInstance().create(new ICreatePollsCollectorRequest() {
					
					@Override
					public long getUserId() {
						return userId;
					}
					
					@Override
					public Long getOpinionId() {
						return opinionId;
					}
					
					@Override
					public String getName() {
						return name;
					}
					
					@Override
					public Integer getCollectorSourceId() {
						return collectorSourceId;
					}
					
					@Override
					public String getActionGuid() {
						return null;
					}

					@Override
					public Integer getResultsTypeId() {
						return ResultsPermissionType.All.getValue();
					}
				});
				break;
			case Survey:
				createResult = CollectorsActionsFactory.getInstance().create(new ICreateSurveysCollectorRequest() {
					
					@Override
					public long getUserId() {
						return userId;
					}
					
					@Override
					public Long getOpinionId() {
						return opinionId;
					}
					
					@Override
					public String getName() {
						return name;
					}
					
					@Override
					public Integer getCollectorSourceId() {
						return collectorSourceId;
					}
					
					@Override
					public String getActionGuid() {
						return null;
					}
				});
				break;
			default:
				throw new UnsupportedOperationException("Unsupported opinion type " + opinion.getOpinionType());
			}
			
			if(createResult.hasError()){
				result = createResult.toErrorResult();
			} else {
				collectorId = createResult.getValue();
			}
		}
		
		if(null == result){
			result = get(collectorId, accountId);
		}
		
		return result;
	}

	public static BaseOperationResult modify(JSONObject input,
			long userId, Long accountId) throws JSONException {
		long collectorId = input.getLong(JsonNames.COLLECTOR_ID);
		
		BaseOperationResult result = null;
		ICollector collector = null;
		OperationResult<ICollector> collectorResult = get(collectorId, accountId);
		if(collectorResult.hasError()){
			result = collectorResult;
		} else {
			collector = collectorResult.getValue();
		}
		
		if(null == result){
			result = collector.modify(input, userId, accountId);
		}
		
		return result;
	}
}
