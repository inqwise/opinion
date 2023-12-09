package com.inqwise.opinion.cms.managers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.cms.common.ILanguage;
import com.inqwise.opinion.cms.common.IPage;
import com.inqwise.opinion.cms.common.IPageGeneratorCallback;
import com.inqwise.opinion.cms.common.IPagesManager;
import com.inqwise.opinion.cms.common.IPagesEnvironment;
import com.inqwise.opinion.cms.common.IPortal;
import com.inqwise.opinion.cms.dao.PagesDataAccess;
import com.inqwise.opinion.cms.entities.PageEntity;
import com.inqwise.opinion.cms.entities.PagesEnvironmentEntity;
import com.inqwise.opinion.cms.entities.ResourceEntity;
import com.inqwise.opinion.library.common.basicTypes.EntityBox;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class PagesFactory implements IPagesManager {
	
	private static ApplicationLog logger = ApplicationLog.getLogger(PagesFactory.class);
	private static IPagesManager instance;
	public static IPagesManager getPagesManager(){
		if (null == instance) {
			synchronized (logger) {
				if (null == instance) {
					instance = new PagesFactory();
				}
			}
		}
		return instance;
	}
	
	private PagesFactory(){}
	
	protected IPage createPage(ResultSet reader, IPagesEnvironment environment) throws SQLException, ServletException, IOException {
		return new PageEntity(reader, environment);
	}
		
	@Override
	public OperationResult<IPage> getPage(int pageId, final int languageId, final HttpServletRequest request, final HttpServletResponse response){
		return getPage(pageId, null, languageId, request, response);
	}
	
	@Override
	public OperationResult<IPage> getPage(String pageUri, final int languageId, final HttpServletRequest request, final HttpServletResponse response){
		return getPage(null, pageUri, languageId, request, response);
	}
	
	@Override
	public OperationResult<IPage> getPage(int pageId, IPagesEnvironment environment){
		return getPage(pageId, null, (PagesEnvironmentEntity)environment);
	}
	
	@Override
	public OperationResult<IPage> getPage(String pageUri, IPagesEnvironment environment){
		return getPage(null, pageUri, (PagesEnvironmentEntity)environment);
	}
	
	private OperationResult<IPage> getPage(Integer pageId, String pageUri, final int languageId,
			final HttpServletRequest request, final HttpServletResponse response) {
		OperationResult<IPage> result = null;
		
		ILanguage language = LanguagesManager.findLanguageById(languageId);
		
		if(null == language){
			result = new OperationResult<IPage>(ErrorCode.ArgumentWrong);
			logger.warn("getPage : no language found with id: '%s', ticketId: '%s'", languageId, result.getErrorId());
		}
		
		IPortal portal = null;
		if(null == result){
			portal = PortalsManager.getCurrentPortal();
			if(null == portal){
				result = new OperationResult<IPage>(ErrorCode.GeneralError, UUID.randomUUID(), "Undefinded portal");
			}
		}
		
		if(null == result){
			PagesEnvironmentEntity environment;
			try {
				environment = new PagesEnvironmentEntity(request, response, language, portal, this);
				result = getPage(pageId, pageUri, environment);
			} catch (MalformedURLException e) {
				UUID errorId = logger.error(e, "getPage : failed to create PagesEnvironmentEntity");
				result = new OperationResult<IPage>(ErrorCode.GeneralError, errorId);
			}
		}
		 
		return result;
	}

	private OperationResult<IPage> getPage(final Integer pageId,
			final String pageUri, final PagesEnvironmentEntity environment){
		
		final EntityBox<IPage> pageBox = new EntityBox<IPage>();
		final String adaptedPageUri = (null == pageUri ? null : StringUtils.left(pageUri.replace(".jsp", ""), 45));
		OperationResult<IPage> result;
		try {
			IPageGeneratorCallback callback = new IPageGeneratorCallback() {
				public void create(ResultSet reader) throws SQLException, ServletException, IOException {
					while(reader.next()){
						IPage page = createPage(reader, environment);
						environment.addPage(page);
						if(!pageBox.hasValue()
							&& (null != adaptedPageUri && adaptedPageUri.equalsIgnoreCase(page.getUri())
								|| null != pageId && pageId == Integer.valueOf(page.getId()))){
							pageBox.setValue(page);
						}
					}
				}
	
				public void fillResources(ResultSet reader) throws SQLException {
					while(reader.next()){
						int pageId = reader.getInt("page_id");
						ResourceEntity resource = ResourceEntity.Create(reader);
						resource.addToPage(environment.getPage(pageId));
					}
				}	
			}; 
			
			BaseOperationResult pagesResult = PagesDataAccess.getPages(callback, pageId, pageUri, null, environment.getLanguage().getId(), environment.getPortal().getId());
			if(pagesResult.hasError()){
				result = pagesResult.toErrorResult();
			} else {
				IPage page = pageBox.getValue();
				if(null == page){
					logger.info("No page found. pageUri: '%s', pageId: '%s'", pageUri, pageId);
					result = new OperationResult<IPage>(ErrorCode.NoResults, UUID.randomUUID());
				} else {
					result = new OperationResult<IPage>(page);
				}
			}
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "GetPage : Unexpected error occured.");
			result = new OperationResult<IPage>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
}
