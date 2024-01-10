package com.inqwise.opinion.infrastructure.systemFramework;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Lists;

public final class JSONHelper {
	
	public static final Long optLong(JSONObject input, String key){
		return optLong(input, key, null);
	}
	
	public static final Long optLong(JSONObject input, String key, Long defaultValue){
		return optLong(input, key, defaultValue, null);
	}
	
	public static final Long optLong(JSONObject input, String key, Long defaultValue, Long nullValue){
		return null == input || !input.has(key) || Long.valueOf(input.optLong(key)).equals(nullValue) || input.isNull(key) ? defaultValue : Long.valueOf(input.optLong(key));
	}
	
	public static final Boolean optBoolean(JSONObject input, String key){
		return optBoolean(input, key, null);
	}
	
	public static final Boolean optBoolean(JSONObject input, String key, Boolean defaultValue){
		return null != input && input.has(key) ? Boolean.valueOf(input.optBoolean(key)) : defaultValue;
	}
	
	public static Integer optInt(JSONObject input, String key){
		return optInt(input, key, null);
	}

	public static Integer optInt(JSONObject input, String key, Integer defaultValue){
		return optInt(input, key, defaultValue, null);
	}
	
	public static Integer optInt(JSONObject input, String key, Integer defaultValue,
			Integer nullValue) {
		
		return null == input || !input.has(key) || Integer.valueOf(input.optInt(key)).equals(nullValue) || input.isNull(key) ? defaultValue : Integer.valueOf(input.optInt(key));
	}

	public static String optStringTrim(JSONObject input, String key){
		return optStringTrim(input, key, null);
	}
	
	public static String optStringTrim(JSONObject input, String key,
			String defaultValue){
		return StringUtils.trimToNull(optString(input, key, defaultValue, null));
	}
	
	public static String optString(JSONObject input, String key){
		return optString(input, key, null);
	}
	
	public static String optString(JSONObject input, String key,
			String defaultValue){
		return optString(input, key, defaultValue, null);
	}
	
	public static String optString(JSONObject input, String key,
			String defaultValue, String nullValue) {
		return null == input || !input.has(key) || input.optString(key).equals(nullValue) || input.isNull(key) ? defaultValue : input.optString(key);
	}

	public static Object getNullable(Object value) {
		return null == value ? JSONObject.NULL : value;
	}

	public static Date optDate(JSONObject input, String key) {
		return optDate(input, key, null);
	}
	
	public static Date optDate(JSONObject input, String key, Date defaultValue) {
		return optDate(input, key, defaultValue, null);
	}
	
	public static Date optDate(JSONObject input, String key, Date defaultValue, Date nullValue) {
		return optDate(input, key, "yyyy-MM-dd HH:mm", defaultValue, nullValue);
	}
	
	public static Date optDate(JSONObject input, String key, String format, Date defaultValue) {
		return optDate(input, key, format, defaultValue, null);
	}
	
	public static Date optDate(JSONObject input, String key, String format, Date defaultValue, Date nullValue) {
		DateFormat formatter = new SimpleDateFormat(format);
		try {
			return null == input || !input.has(key) || formatter.parse(input.optString(key)).equals(nullValue) || input.isNull(key) ? defaultValue : formatter.parse(input.optString(key));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getDateFormat(Date date, String format) {
		if(null == date){
			return JSONObject.NULL;
		} else {
			return DateConverter.getDateFormat(date, format);
		}
	}
	
	public static JSONObject getTimeSpan(Date start, Date end) throws JSONException{
		long diffInSeconds = (end.getTime() - start.getTime()) / 1000;
		return getTimeSpanSec(diffInSeconds);
	}

	public static JSONArray optJsonArray(JSONObject input, String key){
		return optJsonArray(input, key, null);
	}
	
	public static JSONArray optJsonArray(JSONObject input, String key, JSONArray defaultValue) {
		JSONArray result;
		if(null == input || !input.has(key) || input.isNull(key)){
			result = defaultValue;
		} else {
			JSONArray arr = input.optJSONArray(key);
			if(null == arr){
				result = defaultValue;
			} else {
				result = arr;
			}
		}
		return result;
	}

	public static List<Long> toListOfLong(JSONArray jsonArray) {
		if(null == jsonArray) return null;
		
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < jsonArray.length(); i++) {
			
			list.add(Long.valueOf(jsonArray.optLong(i)));
		}
		
		return list;
	}

	public static Integer[] toArrayOfInt(JSONArray jsonArray) {
		if(null == jsonArray) return null;
		
		Integer[] list = new Integer[jsonArray.length()];
		for (int i = 0; i < jsonArray.length(); i++) {
			list[i] = Integer.valueOf(jsonArray.optInt(i));
		}
		
		return list;
	}
	
	public static List<Integer> toListOfInt(JSONArray jsonArray) {
		if(null == jsonArray) return null;
		
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < jsonArray.length(); i++) {
			
			list.add(Integer.valueOf(jsonArray.optInt(i)));
		}
		
		return list;
	}
	
	public static <T> List<T> toListOfModel(JSONArray jsonArray, Function<JSONObject, T> parser){
		List<T> list = Lists.newArrayList();
		jsonArray.forEach(itm -> list.add(parser.apply((JSONObject) itm)));
		return list;
	}
	
	public static Double optDouble(JSONObject input, String key, Double defaultValue) {
		return null != input && input.has(key) ? Double.valueOf(input.optDouble(key)) : defaultValue;
	}
	
	public static Double optDouble(JSONObject input, String key){
		return optDouble(input, key, null);
	}

	public static JSONObject optJson(JSONObject input, String key) {
		return optJson(input, key, null);
	}
	
	public static JSONObject optJson(JSONObject input, String key, JSONObject defaultValue) {
		JSONObject result;
		if(null == input || !input.has(key) || input.isNull(key)){
			result = defaultValue;
		} else {
			JSONObject obj = input.optJSONObject(key);
			if(null == obj){
				result = defaultValue;
			} else {
				result = obj;
			}
		}
		return result;
	}

	public static JSONObject getTimeSpanSec(int diffInSeconds) throws JSONException {
		return getTimeSpanSec(new Long(diffInSeconds));
	}
	
	public static JSONObject getTimeSpanSec(long diffInSeconds) throws JSONException {
		
		JSONObject jo = new JSONObject();
		jo.put("seconds", diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
		jo.put("minutes", (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds);
		jo.put("hours", (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds);
		jo.put("days", (diffInSeconds = (diffInSeconds / 24)));
	    return jo;
	}
}
