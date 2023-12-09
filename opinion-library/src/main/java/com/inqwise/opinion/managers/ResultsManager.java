package com.inqwise.opinion.opinion.managers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.javatuples.Septet;

import net.casper.data.model.CDataCacheContainer;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.common.analizeResults.IAnalizeControl;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.dao.Results;
import com.inqwise.opinion.opinion.entities.analizeResults.AnalizeControlEntity;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class ResultsManager {
	private static ApplicationLog logger = ApplicationLog.getLogger(ResultsManager.class);
	
	/*
	private static Object _analizeControlsLocker = new Object(); 
	private static LoadingCache<Septet<Long, Long, String, Long, Boolean, Boolean, Boolean>, OperationResult<List<IAnalizeControl>>> _analizeControlsCache;
	private static LoadingCache<Septet<Long, Long, String, Long, Boolean, Boolean, Boolean>, OperationResult<List<IAnalizeControl>>> getAnalizeControlsCache(){
		if(null == _analizeControlsCache){
			synchronized (_analizeControlsLocker) {
				if(null == _analizeControlsCache){
					_analizeControlsCache = CacheBuilder.newBuilder().
							maximumSize(1000).
							expireAfterWrite(5, TimeUnit.MINUTES).
							build( new CacheLoader<Septet<Long, Long, String, Long, Boolean, Boolean, Boolean>, OperationResult<List<IAnalizeControl>>>() {

								@Override
								public OperationResult<List<IAnalizeControl>> load(
										Septet<Long, Long, String, Long, Boolean, Boolean, Boolean> args)
										throws Exception {
									return getAnalizeControlsInternal(args.getValue0(), args.getValue1(), args.getValue2(), args.getValue3(), args.getValue4(), args.getValue5(), args.getValue6());
								}
								
							});
				}
			}
		}
		
		return _analizeControlsCache;
	}
	*/
	
	public static OperationResult<List<IAnalizeControl>> getAnalizeControls(Long opinionId, Long accountId, String answererSessionId, Long collectorId, boolean includePartialAnswers, boolean includePartialStatistics, boolean includeAttributeControl){
		OperationResult<List<IAnalizeControl>> result = getAnalizeControlsInternal(opinionId, accountId, answererSessionId, collectorId, includePartialAnswers, includePartialStatistics, includeAttributeControl);
		/*
		OperationResult<List<IAnalizeControl>> result;
		try {
			result = getAnalizeControlsCache().get(new Septet<Long, Long, String, Long, Boolean, Boolean, Boolean>(opinionId, accountId, answererSessionId, collectorId, includePartialAnswers, includePartialStatistics, includeAttributeControl));
		} catch (ExecutionException ex) {
			UUID errorTicket = logger.error(ex, "getAnalizeControls : Unexpected error occured");
			result = new OperationResult<List<IAnalizeControl>>(ErrorCode.GeneralError, errorTicket);
		}
		*/
		return result;
	}
	
	private static OperationResult<List<IAnalizeControl>> getAnalizeControlsInternal(Long opinionId, Long accountId, String answererSessionId, Long collectorId, boolean includePartialAnswers, boolean includePartialStatistics, boolean includeAttributeControl){
		return AnalizeControlEntity.getAnalizeControls(opinionId, accountId, null, answererSessionId, collectorId, includePartialAnswers, includePartialStatistics, includeAttributeControl);
	}
	
	public static CDataCacheContainer getAllResults(long opinionId, Long accountId, Long[] sessionIds, boolean includePartial, TreeMap<Long, Integer> headerIdsMap) {
		try{
			return Results.getAllResults(opinionId, accountId, sessionIds, includePartial, headerIdsMap);
		} catch (DAOException ex){
			throw new Error(ex);
		}
	}
	
	public static OperationResult<Map<String, String>> getResultsFreeText(long opinionId, Long controlId, Long optionId, boolean includePartial, Long accountId) {
		OperationResult<Map<String, String>> result = new OperationResult<Map<String,String>>();
		try{
			result.setValue(Results.getResultsFreeText(opinionId, controlId, optionId, includePartial, accountId));
		} catch (DAOException ex){
			UUID errorTicket = logger.error(ex, "getResultsFreeText : Unexpected error occured");
			result.setError(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}
	
	public static CDataCacheContainer getCountriesStatistics(long opinionId, Long accountId, Date from, Date to, Long collectorId, boolean includePartial){
		try{
			return Results.getCountriesStatistics(opinionId, accountId, from, to, collectorId, includePartial);
		} catch (DAOException ex){
			throw new Error(ex);
		}
	}
}
