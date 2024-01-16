package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.handlers.descriptors.DataPostmasterDescryptorBase;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.managers.OpinionsManager;

public class OpinionsHandler extends HttpServlet {
	enum OpinionAction 
	{
		Create("create");
		private String value;
		
		private OpinionAction(String value){
			this.value = value;
		}
		
		public static OpinionAction fromString(String value){
			for (OpinionAction a : OpinionAction.values()) { 
				if (null != value && value.toLowerCase().trim() == a.value.toLowerCase()) { 
		          return a; 
		        }
	        } 
			
			return Create;
		}
		
	};
	/**
	 * 
	 */
	private static final long serialVersionUID = -5516430249642370616L;

	protected static ApplicationLog logger = ApplicationLog.getLogger(ImportHandler.class);

	//@SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain");
        try {
        	DataPostmasterDescryptorBase context = new DataPostmasterDescryptorBase(request, response, getServletContext());
        	process(request, context);
		} catch (Exception e) {
    		logger.error(e, "doPost: Unexpected error occured");
            throw new RuntimeException(e);
        } 
    }
	
	private void process(HttpServletRequest request, IPostmasterContext context)
			throws Exception {
		
		create(request, context);
	}

	public void create(HttpServletRequest request, IPostmasterContext context) throws ExecutionException, IOException,
			MalformedURLException, JSONException {
		
		IOperationResult result = null;
		String guid = StringUtils.trimToNull(request.getParameter(IOpinion.JsonNames.GUID));
		String translationName = StringUtils.trimToNull(request.getParameter(IOpinion.JsonNames.TRANSLATION_NAME));
		String strTransactionId = StringUtils.trimToNull(request.getParameter(IOpinion.JsonNames.TRANSLATION_ID));
		Long translationId = null == strTransactionId ? IOpinion.DEFAULT_TRANSLATION_ID : Long.valueOf(strTransactionId);
		String name = StringUtils.trimToNull(request.getParameter(IOpinion.JsonNames.NAME));
		
		Long userId = null;
		
		if(context.IsSignedIn()){
			userId = context.getUserId().getValue();
		} else {
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = context.getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		IOpinion originOpinion = null;
		if(null == result){
			OperationResult<IOpinion> opinionResult = OpinionsManager.getOpinion(guid);
			if(opinionResult.hasError()){
				result = new BaseOperationResult(ErrorCode.NotExist);
			} else {
				originOpinion = opinionResult.getValue();
			}
		}
		
		Long newOpinionId = null;
		if(null == result){
			OperationResult<Long> copyResult = OpinionsManager.copyOpinion(guid, translationId, translationName, name, userId, account.getId());
			if(copyResult.hasError()){
				result = copyResult;
			} else {
				newOpinionId = copyResult.getValue();
			}
		}
		
		StringBuilder urlBuilder = null;
		if(null == result){
			urlBuilder = new StringBuilder(context.getAbsoluteURL().toString()).append("/").append(originOpinion.getOpinionType().toString().toLowerCase()).append("s/").append(newOpinionId).append("/edit");
		} else {
			if(result.getErrorCode() == ErrorCode.NotSignedIn.ordinal()){
				String requestUrl = context.getRequestUrl();
				if(null != requestUrl){
					requestUrl = URLEncoder.encode(context.getServerUrl().toString() + requestUrl, "utf-8");
				}
				urlBuilder = new StringBuilder(context.getAbsoluteURL().toString()).append("/").append("login?ret=").append(requestUrl);
			} else {
				logger.warn("create: result: {%s}. Redirect to dashboard", result.toJson());
				urlBuilder = new StringBuilder(context.getAbsoluteURL().toString()).append("/").append("dashboard");
			}
		}
		
		context.sendRedirect(urlBuilder.toString());
	}
}
