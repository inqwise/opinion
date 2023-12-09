package com.inqwise.opinion.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.restexpress.Request;
import org.restexpress.Response;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.NetworkHelper;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CounterController {

	private static Object _countersCacheLocker = new Object(); 
	private static LoadingCache<String, AtomicInteger> _countersCache;
	private static LoadingCache<String, AtomicInteger> getCountersCache(){
		if(null == _countersCache){
			synchronized (_countersCacheLocker) {
				if(null == _countersCache){
					_countersCache = CacheBuilder.newBuilder().
							maximumSize(100).
							expireAfterWrite(10, TimeUnit.MINUTES).
							build( new CacheLoader<String, AtomicInteger>() {
								public AtomicInteger load(String key) throws Exception {
									return new AtomicInteger();
								}
							});
				}
			}
		}
		
		return _countersCache;
	}
	
	static ApplicationLog logger = ApplicationLog.getLogger(CounterController.class);

	private static CounterController instance;
	public static CounterController getInstance() {
		if(null == instance){
			synchronized (CounterController.class) {
				if(null == instance){
					instance = new CounterController();
				}
			}
		}
		return instance;
	}
	
	public String read(Request request, Response response) throws UnsupportedEncodingException, ExecutionException{
		response.addHeader("srv", NetworkHelper.getLocalHostName());
		String result = null;
		Map<String, String> queryString = request.getQueryStringMap();
		String key = StringUtils.trimToNull(URLDecoder.decode(queryString.get("id"), "UTF-8"));
		String callbackName = queryString.get("callback");
		if(null != callbackName){
			callbackName = StringUtils.trimToNull(URLDecoder.decode(callbackName, "UTF-8"));
		}
		
		int cnt = getCountersCache().get(key).incrementAndGet();
		
		if(null == callbackName) {
			result = "popup.pop("+ cnt +");";
		} else {
			result = (callbackName + "(" + cnt + ");");
		}
		
		response.setContentType("application/javascript;charset=UTF-8");
		
		return result;
	}
}
