package com.inqwise.opinion.library.systemFramework;

import java.util.UUID;

import com.inqwise.opinion.infrastructure.systemFramework.BaseApplicationConfiguration;

public class ApplicationConfiguration extends BaseApplicationConfiguration {
	 
	public static class General extends BaseApplicationConfiguration.General{
		private static final String ENABLE_GOOGLE_ANALYTICS_KEY = "general.enableGoogleAnalytics";
		
		public static boolean isEnableGoogleAnalytics(){
			return getValue(ENABLE_GOOGLE_ANALYTICS_KEY, "true").equalsIgnoreCase("true");
		}
	}
	
	public final static class Static{
		private static final String URL_KEY = "static.url";
		
		public static String getUrl(){ 	
			return getValue(URL_KEY, false);
		}
	}
	
	public final static class WebSite{
		private static final String URL_KEY = "website.url";
		
		public static String getUrl(){ 	
			return getValue(URL_KEY, true);
		}
	}
	
	public final static class HtmlTo{
		private static final String URL_KEY = "htmlto.url";
		
		public static String getUrl(){ 	
			return getValue(URL_KEY, true);
		}
	}
	
	public final static class BackOffice{
		private static final String PRODUCT_ID_KEY = "backoffice.id";
		private static final String SESSION_DOMAIN_KEY = "backoffice.sessionDomain";
		
		public static UUID getProductGuid(){
			return UUID.fromString(getValue(PRODUCT_ID_KEY, true));
		}
		
		public static String getSessionDomain() {
			return getValue(SESSION_DOMAIN_KEY, null);
		}
	}
	
	public final static class Opinion{
		private static final String URL_KEY = "opinion.url";
		private static final String PRODUCT_ID_KEY = "opinion.id";
		private static final String SYSTEM_TEMPLATES_FOLDER_KEY = "opinion.systemTemplatesFolder";
		private static final String DEFAULT_COLLECTOR_NAME_KEY = "opinion.defaultCollectorName";
		private static final String DEFAULT_TRANSLATION_NAME_KEY = "opinion.defaultTranslationName";
		private static final String SESSION_DOMAIN_KEY = "opinion.sessionDomain";
		private static final String SECURE_URL_KEY = "opinion.secureUrl";
		
		public final static class Collector{
			private static final String URL_KEY = "opinion.collector.url";
			private static final String SECURE_URL_KEY = "opinion.collector.secureUrl";
			public static String getUrl(){ 	
				return getValue(URL_KEY, true);
			}
			public static String getSecureUrl(){ 	
				return getValue(SECURE_URL_KEY, true);
			}
		}
		
		public static String getUrl(){ 	
			return getValue(URL_KEY, true);
		}
		
		public static UUID getProductGuid(){
			return UUID.fromString(getValue(PRODUCT_ID_KEY, true));
		}
		
		public static String getTemplatesFolder(){
			return getValue(SYSTEM_TEMPLATES_FOLDER_KEY, true);
		}

		public static String getDefaultCollectorName() {
			return getValue(DEFAULT_COLLECTOR_NAME_KEY, "Default collector");
		}

		public static String getSessionDomain() {
			return getValue(SESSION_DOMAIN_KEY, null);
		}

		public static String GetDefaultTranslationName() {
			return getValue(DEFAULT_TRANSLATION_NAME_KEY, "Default");
		}
	}
	
	public final static class CustomerZone{
		private static final String URL_KEY = "customerZone.url";
		private static final String SYSTEM_TEMPLATES_FOLDER_KEY = "office.systemTemplatesFolder";
				
		public static String getUrl(){ 	
			return getValue(URL_KEY, true);
		}
		
		public static String getTemplatesFolder(){
			return getValue(SYSTEM_TEMPLATES_FOLDER_KEY, true);
		}
	}
	
	public final static class SocialAuth{
		private static final String SOCIAL_AUTH_PROPERTIES_PATH_KEY = "socialauth.config-path";
		
		private static String socialAuthPath = null;
		public static String getConfigPath(){
			if(null == socialAuthPath){
				socialAuthPath = getValue(SOCIAL_AUTH_PROPERTIES_PATH_KEY, true);
			}
			return socialAuthPath;
		}
	}
	
	public final static class Api{
		private static final String URL_KEY = "api.url";
		
		public static String getUrl(){ 	
			return getValue(URL_KEY, true);
		}
	}
}
