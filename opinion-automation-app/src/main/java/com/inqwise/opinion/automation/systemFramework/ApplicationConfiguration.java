package com.inqwise.opinion.automation.systemFramework;

import com.inqwise.opinion.infrastructure.systemFramework.BaseApplicationConfiguration;

public class ApplicationConfiguration extends BaseApplicationConfiguration {
	
	public final static class RMI{
		private static final String REGISTRY_PORT_KEY = "rmi.registry.port";
		private static final String REGISTRY_IP_KEY = "rmi.registry.ip";
		
		private static Integer port = null;
		public static Integer getRegistryPort(){
			if(null == port){
				port = Integer.valueOf(getValue(REGISTRY_PORT_KEY, "1099"));
			}
			return port;
		}
		
		private static String ip = null;
		public static String getIP() {
			if(null == ip){
				ip = getValue(REGISTRY_IP_KEY, "127.0.0.1");
			}
			return ip;
		}
	}
	
	public final static class Quartz{
		private static final String PROPERTIES_FILE_PATH_KEY = "org.quartz.properties";
		
		private static String quartzPropertiesFilePath = null;
		public static String getPropertiesFilePath(){
			if(null == quartzPropertiesFilePath){
				quartzPropertiesFilePath = getValue(PROPERTIES_FILE_PATH_KEY, true);
			}
			return quartzPropertiesFilePath;
		}
	}
}
