package com.inqwise.opinion.opinion.managers;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import net.casper.data.model.CDataCacheContainer;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.opinion.common.ITheme;
import com.inqwise.opinion.opinion.common.OutputMode;
import com.inqwise.opinion.opinion.common.collectors.ICollector;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.dao.OpinionsDataAccess;
import com.inqwise.opinion.opinion.dao.ThemesDataAccess;
import com.inqwise.opinion.opinion.entities.OpinionEntity;
import com.inqwise.opinion.opinion.entities.ThemeEntity;


public class ThemesManager {
	public static ApplicationLog logger = ApplicationLog.getLogger(ThemesManager.class);
	
	public static CDataCacheContainer getMeny(long accountId, Integer top, int opinionTypeId) {
		try {
			return ThemesDataAccess.getThemes(accountId, top, opinionTypeId);
		} catch (DAOException e) {
			throw new Error(e);
		}
	}

	public static OperationResult<ITheme> get(Integer themeId, Long accountId) {
		return get(themeId, accountId, null, null, null);
	}
	
	private static OperationResult<ITheme> get(Integer themeId, Long accountId, String opinionGuid, String collectorGuid, Long translationId) {
		
		final OperationResult<ITheme> result = new OperationResult<ITheme>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				@Override
				public void call(ResultSet reader, int generationId) throws Exception {
					if(reader.next()){
						result.setValue(new ThemeEntity(reader));
					}
				}
			};
			
			ThemesDataAccess.getTheme(callback, themeId, accountId, opinionGuid, collectorGuid, translationId);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getTheme() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static OperationResult<ITheme> get(String guid, OutputMode mode) {
		
		OperationResult<ITheme> result = null;
		
		String opinionGuid = null;
		String collectorGuid = null;
		
		if(OutputMode.Answer == mode){
			collectorGuid = guid;
		} else if (OutputMode.View == mode){
			opinionGuid = guid;
		} else {
			logger.warn("getTheme : Unimplemented mode detected: '%s'", mode);
			result = new OperationResult<ITheme>(ErrorCode.ArgumentWrong, UUID.randomUUID(), "Invalid Mode");
		}
		
		if(null == result){
			result = get(null, null, opinionGuid, collectorGuid, IOpinion.DEFAULT_TRANSLATION_ID);
		}
		
		return result;
	}

	public static OperationResult<Integer> createTheme(String name, int opinionTypeId,
			String cssContent, Long accountId, boolean isTemplate) {
		
		final OperationResult<Integer> result = new OperationResult<Integer>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							int errorCode = ResultSetHelper.optInt(reader, DAOBase.ERROR_CODE, 0);
							switch(errorCode){
							case 0:
								result.setValue(reader.getInt(ITheme.ResultSetNames.THEME_ID));
								break;
							default:
								UUID errorId = logger.error("createTheme : errorCode '%s' received", errorCode);
								result.setError(ErrorCode.GeneralError, errorId);
								break;
							}
														
						}
					}
				}
			};
			
			ThemesDataAccess.insertTheme(callback, name, opinionTypeId, cssContent, accountId, isTemplate);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "createTheme() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static BaseOperationResult updateThemeCssContent(final Integer themeId,
			String cssContent, Long accountId) {
		final BaseOperationResult result = new BaseOperationResult();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							int errorCode = ResultSetHelper.optInt(reader, DAOBase.ERROR_CODE, 0);
							switch(errorCode){
							case 4:
								logger.warn("updateThemeCssContent : not permitted to update theme #%s", themeId);
								result.setError(ErrorCode.NotPermitted);
								break;
							}
														
						}
					}
				}
			};
			
			ThemesDataAccess.updateTheme(callback, themeId, cssContent, accountId);
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "updateThemeCssContent() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult deleteTheme(int themeId, long accountId){
		final BaseOperationResult result = new BaseOperationResult();
		try{
			ThemesDataAccess.deleteTheme(themeId, accountId);
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "deleteTheme() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
}
