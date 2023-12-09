package com.inqwise.opinion.infrastructure.systemFramework;

import java.util.HashMap;
import java.util.concurrent.Callable;

public class ObjectsManager {
	private static HashMap<Object, Object> objectsSet = new HashMap<Object, Object>();
	public static <T> T get(Class<T> key){
		return (T) objectsSet.get(key);
	}
	
	public static <T> void put(Class<T> key, T value){
		objectsSet.put(key, value);
	}
	
	public static <T> T remove(Class<T> key){
		return (T) objectsSet.remove(key);
	}
	
	public static synchronized <T> T getOrAdd(Class<T> key, Callable<T> callback) throws Exception{
		Object result = objectsSet.get(key);
		if(null == result){
			result = callback.call();
			objectsSet.put(key, result);
		}
		
		return (T) result;
	} 
}
