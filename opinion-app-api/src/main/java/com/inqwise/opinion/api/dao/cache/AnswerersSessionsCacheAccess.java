package com.inqwise.opinion.api.dao.cache;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;

import com.inqwise.opinion.api.common.AnswererSessionArgs;

public class AnswerersSessionsCacheAccess {
	private static Cache<String, AnswererSessionArgs> c = CacheManager.getCacheManager().getCache("answerersSessions");
	
	public static void put(String key, AnswererSessionArgs args){
		c.put(args.getSessionId(), args, 2, TimeUnit.HOURS);
	}
	
	public static boolean update(String key, AnswererSessionArgs oldArgs, AnswererSessionArgs newArgs){
		if(null == oldArgs){
			c.put(key, newArgs, 30, TimeUnit.DAYS);
			return true;
		} else {
			return c.replace(key, oldArgs, newArgs, 30, TimeUnit.DAYS);
		}
		
	}
	
	public static AnswererSessionArgs get(String token){
		return c.get(token);
	}
	
	public static AnswererSessionArgs remove(String token){
		return c.remove(token);
	}
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { c.stop(); }
		});
	}
}
