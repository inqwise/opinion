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

import com.inqwise.opinion.api.context.CollectorContext;
import com.inqwise.opinion.api.context.Cookies;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.NetworkHelper;

public class CollectorController {
	private static final String RQ_PARAM_NAME = "rq";
	private static final String SESSION_ID_PARAM_NAME = "sid";
	private static final String SESSION_KEY_PARAM_NAME = "sk";

	static ApplicationLog logger = ApplicationLog.getLogger(CollectorController.class);
	
	private static CollectorController instance;
	public static CollectorController getInstance() {
		if(null == instance){
			synchronized (CollectorController.class) {
				if(null == instance){
					instance = new CollectorController();
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
		response.addHeader( "Access-Control-Allow-Credentials", "true");
		response.addHeader( "Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept, Proxy-Connection");
		response.addHeader( "Access-Control-Max-Age", "1000");
		
		return process(request, response, true, true);
	}
	
	// Get
	public String read(Request request, Response response) throws Throwable {
		return process(request, response, false, false);
	}

	private String process(Request request, Response response,
			boolean withoutCallbackWrapper, boolean isPost) throws Throwable {
		String result = null;
		String rq = null;
		String sessionId = null;
		String sessionKey = null;
		
		response.addHeader("srv", NetworkHelper.getLocalHostName());
		try {
			if(isPost){
				Map<String, List<String>> params = request.getBodyFromUrlFormEncoded();
				//rq
				List<String> listOfValues = params.get(RQ_PARAM_NAME);
				if(null != listOfValues){
					rq = StringUtils.trimToNull(listOfValues.get(0));
				}
				//sessionId
				listOfValues = params.get(SESSION_ID_PARAM_NAME);
				if(null != listOfValues){
					sessionId = StringUtils.trimToNull(listOfValues.get(0));
				}
				//sessionKey
				listOfValues = params.get(SESSION_KEY_PARAM_NAME);
				if(null != listOfValues){
					sessionKey = StringUtils.trimToNull(listOfValues.get(0));
				}
				
			} else {
				Map<String, String> queryString = request.getQueryStringMap();
				if(null != queryString) {
					rq = queryString.get(RQ_PARAM_NAME);
					if(null != rq){
						rq = StringUtils.trimToNull(URLDecoder.decode(rq, "UTF-8"));
					}
					
					sessionKey = queryString.get(SESSION_KEY_PARAM_NAME);
					if(null != sessionKey){
						sessionKey = StringUtils.trimToNull(URLDecoder.decode(sessionKey, "UTF-8"));
					}
					
					sessionId = queryString.get(SESSION_ID_PARAM_NAME);
					if(null != sessionId){
						sessionId = StringUtils.trimToNull(URLDecoder.decode(sessionId, "UTF-8"));
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
				callbackName = StringUtils.trimToNull(URLDecoder.decode(request.getQueryStringMap().get("callback"), "UTF-8"));
			}
			result = processRq(request, response, result, rq, callbackName, sessionId, sessionKey);
		} catch (Throwable t){
			logger.error(t, "process(): Unexpected error occured");
			throw t;
		}
		return result;
	}

	private String processRq(Request request, Response response,
			String result, String rq, String callbackName, String sessionId, String sessionKey) {
		try(Cookies cookies = new Cookies(request, response)){
			if (rq == null) {
				logger.warn("processRq() : Not provided the mandatory parameter 'rq'");
			} else {
				try {
					JSONObject source = new JSONObject(rq);
					JSONArray methodNames = source.names();
					JSONObject output = new JSONObject();
					CollectorContext descryptor = new CollectorContext(request, response, cookies, sessionId, sessionKey);
					descryptor.invokeMethods(source, methodNames, null, output);
					if(output.length() > 0){
						// Output
						if(null == callbackName){
							result = output.toString();
						} else {
							result = (callbackName + "(" + output + ")");
						}
					} else {
						UUID errorId = logger.error("processRq() : Output json is empty for methodNames: '%s'", methodNames);
					}
				} catch (JSONException ex){
					UUID errorId = logger.error(ex, "processRq() : Unexpected error occured. path:'%s', rq:'%s'", request.getPath(), rq);
					throw new BadRequestException(errorId.toString());
				} catch (Throwable t) {
					UUID errorId = logger.error(t, "processRq() : Unexpected error occured. path:'%s'", request.getPath());
					throw new BadRequestException(errorId.toString());
				}
			}
		}
		return result;
	}
}
