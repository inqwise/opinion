package com.inqwise.opinion.cms;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inqwise.opinion.cms.common.ILanguage;
import com.inqwise.opinion.cms.common.IPage;
import com.inqwise.opinion.cms.common.ITemplatePage;
import com.inqwise.opinion.cms.entities.PagesEnvironmentEntity;
import com.inqwise.opinion.cms.managers.LanguagesManager;
import com.inqwise.opinion.cms.managers.PagesFactory;
import com.inqwise.opinion.cms.managers.TemplatePagesManager;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class CmsSystem {
	static ApplicationLog logger = ApplicationLog.getLogger(CmsSystem.class);
	public static final String DEFAULT_CULTURE_CODE = "en_US";
	
	public OperationResult<IPage> getPage(HttpServletRequest request, HttpServletResponse response, String cultureCode, String pageUri) throws MalformedURLException{
		OperationResult<IPage> result = null;
		
		if(null == pageUri){
			result = new OperationResult<IPage>(ErrorCode.NoResults);
			logger.warn("getPage : PageUri wasn't supplied");
		}
		
	  	ILanguage language = null;
	  	if(null == result){
		  	OperationResult<ILanguage> languageResult = LanguagesManager.getLanguageByCultureCode(cultureCode, DEFAULT_CULTURE_CODE);
			if(languageResult.hasError()){
				result = languageResult.toErrorResult();
			} else {
				language = languageResult.getValue();
			}
	  	}
	  	
		IPage page = null;
	  	if(null == result){
	  		
	  	  	 
	    	OperationResult<IPage> pageResult = PagesFactory.getPagesManager().getPage(pageUri, language.getId(), request, response);
	    	if(pageResult.hasError()){
	    		result = pageResult;
	    	} else {
	    		page = pageResult.getValue();
	    	}
	  	}
	  	
	  	if(null == result){
	  		result = new OperationResult<IPage>(page);
	  	}
	  	
	  	return result;
	}
}
