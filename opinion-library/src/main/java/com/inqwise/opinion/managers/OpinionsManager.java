package com.inqwise.opinion.opinion.managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import net.casper.data.model.CDataCacheContainer;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.common.parameters.VariablesCategories;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.ParametersManager;
import com.inqwise.opinion.opinion.actions.opinions.ICreatePollRequest;
import com.inqwise.opinion.opinion.actions.opinions.ICreateSurveyRequest;
import com.inqwise.opinion.opinion.actions.opinions.OpinionsActionsFactory;
import com.inqwise.opinion.opinion.common.IControlType;
import com.inqwise.opinion.opinion.common.IOptionRequest;
import com.inqwise.opinion.opinion.common.ITheme;
import com.inqwise.opinion.opinion.common.SurveyStatistics;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.opinions.IOpinionTemplate;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.opinion.common.opinions.OpinionsOrderBy;
import com.inqwise.opinion.opinion.dao.OpinionsDataAccess;
import com.inqwise.opinion.opinion.entities.ControlTypeEntity;
import com.inqwise.opinion.opinion.entities.OpinionEntity;
import com.inqwise.opinion.opinion.entities.OptionEntity;
import com.inqwise.opinion.opinion.entities.SurveyEntity;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class OpinionsManager {
	
	private static ApplicationLog logger = ApplicationLog
			.getLogger(OpinionsManager.class);

	public static OperationResult<List<IControlType>> getControlTypes() {
		return ControlTypeEntity.getControlTypes();
	}

	public static BaseOperationResult updateOption(Long optionId, String text,
			String value, Long translationId, Long accountId,
			Boolean isEnableAdditionalDetails, String additionalDetailsTitle, long userId, String link, Integer linkTypeId) {
		return OptionEntity.update(optionId, text, value, translationId,
				accountId, isEnableAdditionalDetails, additionalDetailsTitle, userId, link, linkTypeId);
	}

	public static BaseOperationResult createOption(IOptionRequest request) {
		return OptionEntity.create(request);
	}

	// Not in Use
	public static BaseOperationResult createOptions(
			List<IOptionRequest> requests) {
		return OptionEntity.createOptions(requests);
	}

	public static BaseOperationResult orderOptions(String optionIds,
			Long accountId, long userId) {

		return OptionEntity.order(optionIds, accountId, userId);
	}

	public static BaseOperationResult deleteOption(Long optionId, Long accountId, long userId) {
		return OptionEntity.delete(optionId, accountId, userId);
	}

	public static OperationResult<Long> copyOpinion(String opinionGuid,
			Long translationId, String translationName, String name,
			Long userId, long toAccountId) {
		OperationResult<Long> result = null;
		OperationResult<IOpinion> surveyResult = getOpinion(opinionGuid);
		IOpinion opinion = null;
		if (surveyResult.hasError()) {
			result = surveyResult.toErrorResult();
		} else {
			opinion = surveyResult.getValue();
		}

		UUID actionGuid = null;
		if (null == result) {
			actionGuid = UUID.randomUUID();
			logger.info("Create opinion from exist. ActionGuid: " + actionGuid.toString());
			result = opinion.copyTo(translationId, translationName, toAccountId,
					name, actionGuid.toString(), userId);
		}
		
		return result;
	}
	
	public static OperationResult<Long> copyOpinion(Long opinionId,
			Long translationId, String translationName, String name, long userId, Long accountId, long toAccountId) {

		OperationResult<Long> result = null;
		OperationResult<IOpinion> surveyResult = getOpinion(opinionId, accountId);
		IOpinion opinion = null;
		if (surveyResult.hasError()) {
			result = surveyResult.toErrorResult();
		} else {
			opinion = surveyResult.getValue();
		}

		UUID actionGuid = null;
		if (null == result) {
			actionGuid = UUID.randomUUID();
			logger.info("Create opinion from exist. ActionGuid: " + actionGuid.toString());
			result = opinion.copyTo(translationId, translationName, toAccountId,
					name, actionGuid.toString(), userId);
		}
		
		return result;
	}
	
	public static BaseOperationResult deleteOpinion(Long opinionId,
			Long accountId, long userId) {
		return OpinionEntity.delete(opinionId, accountId, userId);
	}

	public static BaseOperationResult changeOpinionName(Long opinionId,
			Long accountId, String name, String title, String description, String actionGuid, Long translationId, long userId) {
		return OpinionEntity.changeOpinionName(opinionId, accountId, name,
				title, description, actionGuid, translationId, userId);
	}

	public static BaseOperationResult changeTheme(Long opinionId, int themeId,
			Long accountId, String actionGuid, long userId) {
		
		BaseOperationResult result = null;
		
		// check if theme exist
		OperationResult<ITheme> themeResult = ThemesManager.get(themeId, accountId);
		if(themeResult.hasError()){
			result = themeResult;
		} 
		
		if(null == result){
			result = OpinionEntity.changeTheme(opinionId, themeId, accountId, actionGuid, userId);
		}
		return result;
	}

	public static OperationResult<List<BaseOperationResult>> deleteOpinions(
			List<Long> opinionIds, Long accountId, long userId) {
		
		List<BaseOperationResult> resultsList = new ArrayList<BaseOperationResult>();
		
		for (Long opinionId : opinionIds) {
			BaseOperationResult deleteResult = deleteOpinion(opinionId, accountId, userId);
			if(deleteResult.hasError()){
				resultsList.add(deleteResult);
			} else {
				resultsList.add(new BaseOperationResult(opinionId));
			}
		}
		
		return new OperationResult<List<BaseOperationResult>>(resultsList);
	}
	
	public static OperationResult<SurveyStatistics> getSurveyShortStatistics(Long opinionId){
		return SurveyEntity.getSurveyShortStatistics(opinionId);
	}
	
	public static CDataCacheContainer getOpinions(Long accountId, int top, Date from, Date to, Integer opinionTypeId, long translationId, OpinionsOrderBy orderBy){
		try {
			return OpinionsDataAccess.getOpinions(accountId, top, from, to, opinionTypeId, translationId, (null == orderBy ? null : orderBy.getValue()));
		} catch (DAOException e) {
			throw new Error(e);
		}
	}

	public static OperationResult<IOpinion> getOpinion(Long opinionId, Long translationId, Long accountId, String opinionGuid ){
		final OperationResult<IOpinion> result = new OperationResult<IOpinion>();
		try{
			
			IAccountView account = null;
			if(null != accountId && !result.hasError()){
				OperationResult<IAccountView> accountResult = AccountsManager.getAccount(accountId);
				if(accountResult.hasError()){
					result.setError(accountResult);
				} else {
					account = accountResult.getValue();
				}
			}
			
			HashMap<String, IVariableSet> permissions = null;
			if(null != accountId && !result.hasError()){
				String servicePackageKey = ParametersManager.getReferenceKey(EntityType.ServicePackage, account.getServicePackageId());
				OperationResult<HashMap<String, IVariableSet>> variablesResult = ParametersManager.getVariables(account.getId(), EntityType.Account, new Integer[] {VariablesCategories.Permissions.getValue()}, new String[] {servicePackageKey});
				if(variablesResult.hasError()){
					result.setError(variablesResult);
				} else {
					permissions = variablesResult.getValue();
				}
			}
			
			if(!result.hasError()){
				final HashMap<String, IVariableSet> finalPermissions = permissions;
				IResultSetCallback callback = new IResultSetCallback() {
					
					public void call(ResultSet reader, int generationId) throws Exception {
						while(reader.next()){
							if(0 == generationId){
								OpinionEntity opinion = OpinionEntity.identifyOpinion(reader, finalPermissions);
								result.setValue(opinion);							
							}
						}
					}
				};
				
				OpinionsDataAccess.getOpinion(callback, opinionId, opinionGuid, accountId, translationId);
			}
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getOpinion() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<IOpinion> getOpinion(Long opinionId, Long accountId){
		return getOpinion(opinionId, IOpinion.DEFAULT_TRANSLATION_ID, accountId, null);
	}
	
	public static OperationResult<Long> createOpinion(final String name, final String description,
			final long accountId, final String actionGuid, final Long userId, OpinionType opinionType, final String title) {
		OperationResult<Long> result = null;
		switch (opinionType) {
		case Survey:
			result = OpinionsActionsFactory.getInstance().create(new ICreateSurveyRequest() {
				
				@Override
				public long getUserId() {
					return userId;
				}
				
				@Override
				public String getName() {
					return name;
				}
				
				@Override
				public String getDescription() {
					return description;
				}
				
				@Override
				public String getActionGuid() {
					return actionGuid;
				}
				
				@Override
				public long getAccountId() {
					return accountId;
				}

				@Override
				public String getTitle() {
					return title;
				}

				@Override
				public boolean isCreateEmptySheet() {
					return true;
				}
			});
			break;
		case Poll:
			result = OpinionsActionsFactory.getInstance().create(new ICreatePollRequest() {
				
				@Override
				public long getUserId() {
					return userId;
				}
				
				@Override
				public String getName() {
					return name;
				}
				
				@Override
				public String getDescription() {
					return description;
				}
				
				@Override
				public String getActionGuid() {
					return actionGuid;
				}
				
				@Override
				public long getAccountId() {
					return accountId;
				}

				@Override
				public String getTitle() {
					return null;
				}
			});
			break;
		default:
			throw new UnsupportedOperationException("Unsupported opinion type " + opinionType);
		}
		return result;
	}

	public static BaseOperationResult modifyOpinion(JSONObject input,
			Long accountId, Long userId) throws JSONException {
		
		BaseOperationResult result = null;
		IOpinion opinion = null;
		
		long opinionId = input.getLong(IOpinion.JsonNames.OPINION_ID);
		OperationResult<IOpinion> opinionResult = OpinionsManager.getOpinion(opinionId, accountId);
		if(opinionResult.hasError()){
			result = opinionResult;
		} else {
			opinion = opinionResult.getValue();
		}
		
		if(null == result){
			result = opinion.modify(input, userId, accountId);
		}
		
		return result;
	}

	public static OperationResult<Long> importOpinion(JSONObject input, Long accountId, Long userId) {
		
		OperationResult<Long> result = new OperationResult<Long>();
		try {
			final OpinionType opinionType = OpinionType.fromInt(input.getInt(IOpinion.JsonNames.OPINION_TYPE_ID));
			switch (opinionType) {
			case Poll:
				break;
			case Survey:
				break;
			default:
				result.setError(ErrorCode.InvalidAccount, UUID.randomUUID(), "opinionType");
			}
			
			//if(!result.hasError()){
				
			//}
			/*
			String name = input.getString(IOpinion.JsonNames.NAME);
			String description = JSONHelper.optStringTrim(input, IOpinion.JsonNames.DESCRIPTION);
			String actionGuid = UUID.randomUUID().toString();
			String title = JSONHelper.optStringTrim(input, IOpinion.JsonNames.TITLE);
			*/
			
		} catch (JSONException e) {
			result.setError(ErrorCode.InvalidSchema, UUID.randomUUID(), e.getMessage());
		}
		
		return null;
		
	}
	
	private static Object _opinionsByGuidCacheLocker = new Object(); 
	private static LoadingCache<String, OperationResult<IOpinion>> _opinionsByGuidCache;
	private static LoadingCache<String, OperationResult<IOpinion>> getOpinionsByGuidCache(){
		if(null == _opinionsByGuidCache){
			synchronized (_opinionsByGuidCacheLocker) {
				if(null == _opinionsByGuidCache){
					_opinionsByGuidCache = CacheBuilder.newBuilder().
							maximumSize(1000).
							expireAfterWrite(20, TimeUnit.SECONDS).
							build( new CacheLoader<String, OperationResult<IOpinion>>() {
								public OperationResult<IOpinion> load(String guid) throws Exception {
									return getOpinionInternal(guid);
								}
							});
				}
			}
		}
		
		return _opinionsByGuidCache;
	}

	private static OperationResult<IOpinion> getOpinionInternal(String opinionGuid ){
		return getOpinion(null, null, null, opinionGuid);
	}
	
	public static OperationResult<IOpinion> getOpinion(String opinionGuid ){
		OperationResult<IOpinion> result;
		try {
			result = getOpinionsByGuidCache().get(opinionGuid);
		} catch (ExecutionException e) {
			UUID errorTicket = logger.error(e, "getOpinion : Unexpected error occured");
			result = new OperationResult<IOpinion>(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}
	
	public static CDataCacheContainer getTemplates(){
		try {
			return OpinionsDataAccess.getTemplatesDataSet();
		} catch (DAOException e) {
			throw new Error(e);
		}
	}
	
	public static BaseOperationResult updateTemplate(long opinionId, Long userId, String name, String description, String urlName, HashSet<String> keywords, HashSet<Integer> categories){
		return BaseOperationResult.NotImplemented;
	}
	
	public static BaseOperationResult activateTemplate(long opinionId, Long userId, boolean isActive){
		return BaseOperationResult.NotImplemented;
	}
	
	public static BaseOperationResult deleteTemplate(long opinionId, Long userId){
		return BaseOperationResult.NotImplemented;
	}
	
	public static OperationResult<IOpinionTemplate> getTemplate(Long opinionId, Long userId){
		return null;
	}
}
