package com.inqwise.opinion.payments.systemFramework;

import com.inqwise.opinion.infrastructure.systemFramework.BaseApplicationConfiguration;

public class PayConfiguration extends BaseApplicationConfiguration {
	
	public final static class Paypal{
		private static final String CONFIG_PATH_KEY = "paypal.config-path";
		private static final String SERVICE_URL_KEY = "paypal.service-url";
	
		private static String configPath = null;
		public static String getConfigPath(){
			if (null == configPath) {
				configPath = getValue(CONFIG_PATH_KEY, true);
			}
			return configPath;
		}
		
		private static String serviceUrl = null;
		public static String getServiceUrl() {
			if (null == serviceUrl) {
				serviceUrl = getValue(SERVICE_URL_KEY, true);
			}
			return serviceUrl;
		}
	}
}
