package com.inqwise.opinion.infrastructure.systemFramework;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public final class Convert {
	public static Date toDate(Timestamp timestamp){
		if(null == timestamp){
			return null;
		} else {
			long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
		    return new Date(milliseconds);
		}
	}
	
	public static java.util.Date toDate(java.sql.Date date) {
		if(null == date){
			return null;
		} else{
			return new java.util.Date(date.getTime());
		}
	}

	public static Map<String, List<String>> fromQueryString(String query) 
	        throws UnsupportedEncodingException { 
	    Map<String, List<String>> params = new HashMap<String, List<String>>(); 
	     
	    if (null != query) { 
	        for (String param : query.split("&")) { 
	            String pair[] = param.split("="); 
	            String key = URLDecoder.decode(pair[0], "UTF-8"); 
	            String value = ""; 
	            if (pair.length > 1) { 
	                value = URLDecoder.decode(pair[1], "UTF-8"); 
	            } 
	            List<String> values = params.get(key); 
	            if (values == null) { 
	                values = new ArrayList<String>(); 
	                params.put(key, values); 
	            } 
	            values.add(value); 
	        } 
	    } 
	    return params; 
	}
	
	public static Object nullIfSame(Object obj1, Object obj2){
		Object result = null;
		if(null != obj1 && !obj1.equals(obj2)){
			result = obj1;
		}
		
		return result;
	}
	
	public static String getUniqueKey(){
		return StringUtils.remove(UUID.randomUUID().toString(), '-');
	}
	
	public static Long toOptLong(String s){
		Long result = null;
		if(null != s && StringUtils.isNumeric(s)){
			result = Long.valueOf(s);
		}
		
		return result;
	}
}
