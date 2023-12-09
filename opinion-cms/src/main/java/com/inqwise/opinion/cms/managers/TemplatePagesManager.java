package com.inqwise.opinion.cms.managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.cms.common.ITemplatePage;
import com.inqwise.opinion.cms.common.ITemplatePagesContainer;
import com.inqwise.opinion.cms.dao.TemplatePagesDataAccess;
import com.inqwise.opinion.cms.entities.TemplatePageEntity;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class TemplatePagesManager implements ITemplatePagesContainer {
	private static ApplicationLog logger = ApplicationLog.getLogger(TemplatePagesManager.class);
	private List<ITemplatePage> templates;
	private static TemplatePagesManager instance;
	private BaseOperationResult fillTempatesToCache(int portalId){
		BaseOperationResult result;
		try{
			templates =  new ArrayList<ITemplatePage>();
			IDataFillable<ITemplatePage> callback = new IDataFillable<ITemplatePage>() {
				public ITemplatePage fill(ResultSet reader) throws Exception {
					while(reader.next()){
						ITemplatePage template = new TemplatePageEntity(reader); 
						templates.add(template);
					}
					return null;
				}
			}; 
			
			TemplatePagesDataAccess.getTemplatePages(callback, portalId);
			if(templates.size() == 0){
				templates = null;
				UUID errorId = logger.error("fillTempatesToCache : NO Results");
				result = new BaseOperationResult(ErrorCode.NoResults, errorId);
			} else {
				result = BaseOperationResult.Ok;
			}
		} catch (DAOException ex){
			templates = null;
			UUID errorId = logger.error(ex, "fillTempatesToCache : Unexpected error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	private TemplatePagesManager(){
		fillTempatesToCache(PortalsManager.getCurrentPortal().getId());
	}
	
	public static TemplatePagesManager getInstance(){
		if(null == instance){
			synchronized (logger) {
				if(null == instance){
					instance = new TemplatePagesManager();
				}
			}
		}
		
		return instance;
	}
	
	public OperationResult<ITemplatePage> get(int templateId){
		
		OperationResult<ITemplatePage> result = null;
		if(null == templates){
			result = new OperationResult<ITemplatePage>(ErrorCode.InitializationError);
		}
		
		ITemplatePage requestedTemplate = null;
		if(null == result){
			for(ITemplatePage template : templates){
				if(template.getId() == templateId){
					requestedTemplate = template;
					break;
				}
			}
		
			if(null == requestedTemplate){
				result = new OperationResult<ITemplatePage>(ErrorCode.NoResults);
			}
		}
		
		if(null == result){
			result = new OperationResult<ITemplatePage>(requestedTemplate);
		}
		
		return result;
	}
}
