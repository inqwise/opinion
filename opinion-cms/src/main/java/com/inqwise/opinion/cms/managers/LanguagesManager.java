package com.inqwise.opinion.cms.managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.cms.common.ILanguage;
import com.inqwise.opinion.cms.dao.LanguagesDataAccess;
import com.inqwise.opinion.cms.entities.LanguageEntity;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class LanguagesManager {
	private static ApplicationLog logger = ApplicationLog.getLogger(LanguagesManager.class);
	private static Dictionary<String, ILanguage> languagesByCultureCode;
	private static Dictionary<Integer, ILanguage> languagesById;
	private static Object lockObj = new Object();

	public static OperationResult<ILanguage> getLanguageByCultureCode(String cultureCode, String defaultCultureCode){
		OperationResult<ILanguage> result = null;
		if(null == languagesByCultureCode){
			synchronized (lockObj) {
				if(null == languagesByCultureCode){
					BaseOperationResult fillResult = fillLanguagesToCache();
					if(fillResult.hasError()){
						result = fillResult.toErrorResult();
					}
				}
			}
		}
		
		String adaptedCultureCode;
		if (cultureCode != null){
			adaptedCultureCode = cultureCode.replace("-", "_");
	  	} else {
	  		adaptedCultureCode = cultureCode;
	  	}
		
		if(null == result && null == adaptedCultureCode && null == defaultCultureCode){
			result = new OperationResult<ILanguage>(ErrorCode.NoResults);
		}
		
		if(null == result){
			ILanguage language = null;
			if(null != adaptedCultureCode){
				language = languagesByCultureCode.get(adaptedCultureCode.toLowerCase());
			}
			
			if(null == language && null != defaultCultureCode){
				language = languagesByCultureCode.get(defaultCultureCode.toLowerCase());
			}
				
			if(null == language){
				result = new OperationResult<ILanguage>(ErrorCode.NoResults);
			} else {
				result = new OperationResult<ILanguage>(language);
			}
		}
		
		return result;
	}

	private static BaseOperationResult fillLanguagesToCache() {
		BaseOperationResult result;
		try{
			languagesByCultureCode = new Hashtable<String, ILanguage>();
			languagesById = new Hashtable<Integer, ILanguage>();
			
			IDataFillable<ILanguage> callback = new IDataFillable<ILanguage>() {
				
				public ILanguage fill(ResultSet reader) throws Exception {
					while(reader.next()){
						ILanguage language = new LanguageEntity(reader);
						languagesByCultureCode.put(language.getCultureCode().toLowerCase(), language);
						languagesById.put(language.getId(), language);
						
					}
					return null;
				}
			};
			LanguagesDataAccess.getLanguages(callback);
			
			if(0 == languagesByCultureCode.size()){
				logger.warn("fillLanguagesToCache : languagesByCultureCode without items");
			}
			
			result = BaseOperationResult.Ok;
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "fillLanguagesToCache : Unexpected error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
			languagesByCultureCode = null;
			languagesById = null;
			
		}
		
		return result;
	}

	public static ILanguage findLanguageById(int languageId) {
		
		if(null == languagesByCultureCode){
			BaseOperationResult fillResult = fillLanguagesToCache();
		}
		return languagesById.get(languageId);
	}
}
