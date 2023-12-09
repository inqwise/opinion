package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inqwise.opinion.cms.CmsSystem;
import com.inqwise.opinion.cms.common.IPage;
import com.inqwise.opinion.cms.common.ITemplatePage;
import com.inqwise.opinion.cms.managers.TemplatePagesManager;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public class Cms  extends HttpServlet {
	private static final CmsSystem cms = new CmsSystem();
	private static final String PAGE_URI_KEY = "page";


	private static final String CULTURE_CODE_KEY = "lang";
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2322693509245920343L;
	static ApplicationLog logger = ApplicationLog.getLogger(Cms.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		BaseOperationResult result = null;
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		ServletContext context = getServletContext();	
		
		try{
			IPage page = null;
		  	String cultureCode = request.getParameter(CULTURE_CODE_KEY);
		  	String pageUri = request.getParameter(PAGE_URI_KEY);
		  	
		  	OperationResult<IPage> pageResult = cms.getPage(request, response, cultureCode, pageUri);
		  	if(pageResult.hasError()){
		  		result = pageResult;
		  	} else {
		  		page = pageResult.getValue();
		  		//Compile page
		  		page.compile();
		  	}
		  	
		  	ITemplatePage template = null;
		  	if(null == result){
		  		
		  		//Params
		  		request.setAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME, page);
		  		
		  		OperationResult<ITemplatePage> templateResult = TemplatePagesManager.getInstance().get(page.getTemplateId());
		  		if(templateResult.hasError()){
		  			logger.error("Unable get find templatePage #'%s' for page #'%s'", page.getTemplateId(), page.getId());
		  			result = templateResult;
		  		} else {
		  			template = templateResult.getValue();
		  		}
		  	}
		  	
		  	if(null == result){
		  		//Include
		  		logger.debug("Dispatch template page: " + template.getInclude());
		  		RequestDispatcher rd = request.getRequestDispatcher(template.getInclude());
		  		rd.include(request, response);
		  		
		  	} else {
		  		if(result.getError() == ErrorCode.NoResults) {
			  		response.sendRedirect(request.getContextPath() + "/404.html");
			  	} else {
			  		response.sendRedirect(request.getContextPath() + "/500.html");
			  	} 
		  	}
		  	
		} catch (Throwable t){
			UUID errorId = logger.error(t, "doGet : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
			response.sendRedirect(request.getContextPath() + "/503.html");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		
	}
}