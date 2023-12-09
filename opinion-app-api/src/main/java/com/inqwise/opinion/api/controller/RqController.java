package com.inqwise.opinion.api.controller;

import java.net.URLDecoder;
import java.util.Map;

import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import org.restexpress.Request;
import org.restexpress.Response;

public class RqController {
	static ApplicationLog logger = ApplicationLog.getLogger(RqController.class);
	
	/*
	public Object create(Request request, Response response) {
		//response.setResponseCreated();
		//return request.getBody();
		return null;
	}
	
	public Object update(Request request, Response response) {
		//response.setResponseCreated();
		//return request.getBody();
		return null;
	}
	
	public Object delete(Request request, Response response) {
		return null;
	}
	*/
	
	public JSONObject read(Request request, Response response) throws Throwable {
		
		Map<String, String> queryString = request.getQueryStringMap();
		
		if(!queryString.containsKey("rq")){
			throw new org.restexpress.exception.BadRequestException("rq is mandatory");
		}
		
		try {
	
			String rq = URLDecoder.decode(request.getQueryStringMap().get("rq"), "UTF-8");
			JSONObject rqJson = new JSONObject(rq);
			String lang = request.getHeader("lang");
			logger.info("rq: '%s'", rqJson);
			
			return new JSONObject().putOpt("result", "done").put("lang", lang);
		} catch (Throwable t){
			logger.error(t, "read: Unexpected error ocuured");
			throw t;
		}
	}

	private static RqController instance;
	public static RqController getInstance() {
		if(null == instance){
			synchronized (RqController.class) {
				if(null == instance){
					instance = new RqController();
				}
			}
		}
		return instance;
	}
}
