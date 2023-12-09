package com.inqwise.opinion.api.dao.cache;

import java.util.concurrent.Callable;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import com.inqwise.opinion.api.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.infrastructure.systemFramework.ObjectsManager;

public class CacheManager {
	private static EmbeddedCacheManager manager;  
    
	public static EmbeddedCacheManager getCacheManager() {  
        if (null == manager) {
        	synchronized (EmbeddedCacheManager.class) {
        		if(null == manager){
		        	try {
	        			manager = ObjectsManager.getOrAdd(EmbeddedCacheManager.class, new Callable<EmbeddedCacheManager>() {
			
							@Override
							public EmbeddedCacheManager call() throws Exception {
								if(null != ApplicationConfiguration.Infinispan.getBindIp()){
									System.setProperty("jgroups.bind_addr", ApplicationConfiguration.Infinispan.getBindIp());
								}
								//System.setProperty("java.net.preferIPv4Stack", "true");
								DefaultCacheManager result = new DefaultCacheManager(ApplicationConfiguration.Infinispan.getConfigPath());
								
								Runtime.getRuntime().addShutdownHook(new Thread() {
					    		    public void run() { 
					    		    	if(null != manager && null != ObjectsManager.remove(EmbeddedCacheManager.class)){
					    		    		manager.stop();}
					    		    	}
					    			});
								
								return result;
							}
						});
		        	} catch (Exception ex){
		        		throw new Error(ex);
		        	}
        		}
        	}
        }  
        return manager;  
    } 
}
