package com.inqwise.opinion.payments.dao.cache;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import com.inqwise.opinion.payments.dao.interfaces.IBeginPaymentArgs;

public class PayCacheAccess {
	
	private static Cache<String, IBeginPaymentArgs> c = CacheManager.getCacheManager().getCache("payment");
	
	public static void putBeginPaymentArgs(IBeginPaymentArgs args){
		c.put(args.getToken(), args, 1, TimeUnit.DAYS);
	}
	
	public static IBeginPaymentArgs getBeginPaymentArgs(String token){
		return c.get(token);
	}
	
	public static IBeginPaymentArgs removeBeginPaymentArgs(String token){
		return c.remove(token);
	}
}
