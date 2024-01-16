package com.inqwise.opinion.api.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.exception.BadRequestException;

import com.inqwise.opinion.api.context.Cookies;
import com.inqwise.opinion.api.context.FrontContext;
import com.inqwise.opinion.http.HttpClientSession;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.Convert;
import com.inqwise.opinion.infrastructure.systemFramework.NetworkHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;

public class FrontController {
	static ApplicationLog logger = ApplicationLog.getLogger(FrontController.class);
	private static final String RQ_PARAM_NAME = "rq";
	
	private static FrontController instance;
	public static FrontController getInstance() {
		if(null == instance){
			synchronized (FrontController.class) {
				if(null == instance){
					instance = new FrontController();
				}
			}
		}
		return instance;
	}
	
	// Post
	public String create(Request request, Response response) throws Throwable {
		String origin = request.getHeader("Origin");
		if(null == origin){
			origin = request.getHeader("origin");
		}
		
		if(null == origin){
			origin = "*";
		}
		
		response.addHeader( "Access-Control-Allow-Origin", origin);
		response.addHeader( "Access-Control-Allow-Methods", "POST");
		response.addHeader( "Access-Control-Max-Age", "1000");
		response.addHeader( "Access-Control-Allow-Credentials", "true");
		response.addHeader( "Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept, Proxy-Connection");
		
		return process(request, response, true, true);
	}
	
	private String processRq(Request request, Response response,
			String result, String rq, String callbackName, Long accountId) {
		try(Cookies cookies = new Cookies(request, response)){
			if (rq == null) {
				logger.warn("processRq() : Not provided the mandatory parameter 'rq'");
			} else {
				FrontContext descryptor = null;
				HttpClientSession session = null;
				try {
					JSONObject source = new JSONObject(rq);
					JSONArray methodNames = source.names();
					JSONObject output = new JSONObject();
					descryptor = new FrontContext(request, response, cookies, accountId);
					session = descryptor.getSession();
					descryptor.invokeMethods(source, methodNames, null, output);
					if(output.length() > 0){
						// Output
						if(null == callbackName){
							result = output.toString();
						} else {
							result = (callbackName + "(" + output + ")");
						}
					} else {
						UUID errorId = logger.error("processRq() : Output json is empty for methodNames:'%s', session:'%s'", methodNames, rq, (null == session ? null : session.toJson()));
					}
				} catch (JSONException ex){
					UUID errorId = logger.error(ex, "processRq() : Unexpected error occured. path:'%s', rq:'%s', session:'%s'", request.getPath(), rq, (null == session ? null : session.toJson()));
					throw new BadRequestException(errorId.toString());
				} catch (Throwable t) {
					UUID errorId = logger.error(t, "processRq() : Unexpected error occured. path:'%s', rq:'%s', session:'%s'", request.getPath(), rq, (null == session ? null : session.toJson()));
					throw new BadRequestException(errorId.toString());
				}
			}
		}
		return result;
	}
	
	// Get
	public String read(Request request, Response response) throws Throwable {
		return process(request, response, false, false);
	}
	
	private String process(Request request, Response response,
			boolean withoutCallbackWrapper, boolean isPost) throws Throwable {
		String result = null;
		String rq = null;
		Long accountId = null;
		
		response.addHeader("srv", NetworkHelper.getLocalHostName());
		try {
			if(isPost){
				Map<String, List<String>> params = request.getBodyFromUrlFormEncoded();
				List<String> listOfValues = params.get(RQ_PARAM_NAME);
				if(null != listOfValues){
					rq = StringUtils.trimToNull(listOfValues.get(0));
				}
				
				listOfValues = params.get(IAccount.JsonNames.ACCOUNT_ID);
				if(null != listOfValues){
					accountId = Convert.toOptLong(StringUtils.trimToNull(listOfValues.get(0)));
				}
			} else {
				Map<String, String> queryString = request.getQueryStringMap();
				rq = queryString.get(RQ_PARAM_NAME);
				if(null != rq){
					rq = StringUtils.trimToNull(URLDecoder.decode(rq, "UTF-8"));
				}
				
				String strAccountId = queryString.get(IAccount.JsonNames.ACCOUNT_ID);
				if(null != strAccountId){
					strAccountId = URLDecoder.decode(strAccountId, "UTF-8");
					if(null != strAccountId){
						accountId = Convert.toOptLong(StringUtils.trimToNull(strAccountId));
					}
				} 
				
			}
		} catch(Throwable t) {
			logger.error(t, "process(): Failed to get properties");
			throw t;
		}
		
		if(null == rq){
			throw new BadRequestException("rq is mandatory");
		}
		
		try {
			
			String callbackName = null;
			if(!withoutCallbackWrapper){
				callbackName = request.getQueryStringMap().get("callback");
				if(null != callbackName){
					callbackName = StringUtils.trimToNull(URLDecoder.decode(callbackName, "UTF-8"));
				}
			}
			result = processRq(request, response, result, rq, callbackName, accountId);
		} catch (Throwable t){
			logger.error(t, "process(): Unexpected error occured");
			throw t;
		}
		return result;
	}
}
