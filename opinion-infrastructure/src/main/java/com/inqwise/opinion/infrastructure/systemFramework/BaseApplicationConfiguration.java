package com.inqwise.opinion.infrastructure.systemFramework;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.UUID;

public class BaseApplicationConfiguration {
	 
	// Constants
	private static final String PROPERTIES_FILE = "application.properties";
	private static final String PROPERTY_LOCATION_PREFIX = "[";
	private static final String PROPERTY_LOCATION_SUFFIX = "]";
	
	private static Properties CONFIGURATION = new Properties();
	private static Hashtable<String, Properties> cachedExternalConfigs = new Hashtable<String, Properties>();
	
	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
		
		if(propertiesFile == null) {
			throw new ApplicationConfigurationException(
					"Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
		}
		
		try {
			CONFIGURATION.load(propertiesFile);
		} catch (IOException e) {
			throw new ApplicationConfigurationException(
					"Cannot load properties file '" + PROPERTIES_FILE + "'.", e);
		}
		
		try{
			// check for externalConfigs
			for (Enumeration<?> en = CONFIGURATION.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				String property = CONFIGURATION.getProperty(key);
				if(isPropertyExternalConfigLocation(property)){
					String path = getExternalConfigPath(property);
					Properties config = getExternalConfig(path, false); // return null when actual config is already requested 
					CONFIGURATION.remove(key);
					if(null != config){
						copyProperties(config, CONFIGURATION);
					}
				}
			}
		} catch(Throwable t){
			throw new RuntimeException ("static ctor() : Unexpected error occured.", t);
		}
	}
	
	public static boolean isPropertyExternalConfigLocation(String property){
		return (property.startsWith(PROPERTY_LOCATION_PREFIX) && property.endsWith(PROPERTY_LOCATION_SUFFIX));
	}
	
	private static void copyProperties(Properties from,
										Properties to) {
		for (Enumeration<?> en = from.propertyNames(); en.hasMoreElements();) {
			String key = (String) en.nextElement();
			if(to.containsKey(key)){
				throw new ApplicationConfigurationException("The key already contains in destination Config. Actual key: '"+ key +"'");
			} else {
				String value = from.getProperty(key);
				to.setProperty(key, value);
			}
		}
		
	}

	protected static String getValue(String key, boolean isMandatory){
		return getValue(key, isMandatory, null);
	}
	
	protected static String getValue(String key, String defaultValue){
		return getValue(key, false, defaultValue);
	}
	
	private static String getValue(String key, boolean isMandatory, String defaultValue){
		return getValue(key, isMandatory, defaultValue, CONFIGURATION);
	}
	
	private static String getExternalConfigPath(String property){
		return property.substring(1, property.length()-1);
	}
	
	private static String getValue(String key, boolean isMandatory, String defaultValue, Properties config){
		if(config.containsKey(key)){
			
			String property = config.getProperty(key);
			if(isPropertyExternalConfigLocation(key)){
				if(config != CONFIGURATION){
					throw new ApplicationConfigurationException("ExternalConfig location avalable only in root config file.");
				}
				
				String externalConfigPath = getExternalConfigPath(property);
				property = getPropertyFromExternalConfigurationFile(externalConfigPath, key, isMandatory, defaultValue);
			} else {
				property = config.getProperty(key);
			}
			return property;
			
		} else if (!isMandatory) {
			return defaultValue;
		} else {
			throw new ApplicationConfigurationException("Configuration key is missing. Key: " + key);
		}
	}

	private static Properties getExternalConfig(String path, boolean duplicationsAllowed){
		
		Properties config = null;
		String key = path.toLowerCase();
		if(cachedExternalConfigs.containsKey(key)){
			config = duplicationsAllowed ? cachedExternalConfigs.get(key) : null;
		} else {
			FileReader reader;
			try {
				reader = new FileReader(path);
				config = new Properties();
				config.load(reader);
				cachedExternalConfigs.put(path.toLowerCase(), config);
			
			} catch (FileNotFoundException e) {
				throw new ApplicationConfigurationException(
						"Not found properties file '" + path + "'.", e);
			} catch (IOException e) {
				throw new ApplicationConfigurationException(
						"Cannot load properties file '" + path + "'.", e);
			} catch (Throwable t){
				throw new ApplicationConfigurationException(
						"Cannot load properties file '" + path + "'.", t);
			}
			
		}
		
		return config;
	}
	
	private static String getPropertyFromExternalConfigurationFile(
			String externalConfigPath, String key, boolean isMandatory, String defaultValue) {

		Properties externalConfig = getExternalConfig(externalConfigPath, true);
		return getValue(key, isMandatory, defaultValue, externalConfig);
	}
	
	public static String getResourcePath(String name){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource(name);
		if(null == url){  
			return null;
		} else {
			return url.getPath();
		}
	}
	/*
	public final static class Validation{
		private static final String IS_PASS_CAPCHA_VALIDATION_KEY = "validation.isPassCapchaValidation";
		
		private static Boolean isPassCapchaValidation = null;
		public static boolean getIsPassCapchaValidation(){
			if(null == isPassCapchaValidation){
				isPassCapchaValidation = Boolean.valueOf(getValue(IS_PASS_CAPCHA_VALIDATION_KEY, "true"));
			}
			return isPassCapchaValidation;
		}
	}
	*/
	public final static class DAOConfiguration{
		
		private static final String URL_KEY = "dao.url";
		private static final String DRIVER_KEY = "dao.driver";
		private static final String USERNAME_KEY = "dao.username";
		private static final String PASSWORD_KEY = "dao.password";
		private static final String DEFAULT_SECTION_KEY = "dao.defaultSection";
		private static Hashtable<String, DAOConfiguration> cachedSections = new Hashtable<String, DAOConfiguration>();
		
		public static String getDefaultSectionName(){
			return getValue(DEFAULT_SECTION_KEY, true);
		}
		
		public static DAOConfiguration getSectionConfig(String sectionName){
			DAOConfiguration section;
			String key = (null == sectionName ? getValue(DEFAULT_SECTION_KEY, true) : sectionName.toLowerCase());
			if(cachedSections.containsKey(key)){
				section = cachedSections.get(key);
			} else{
				section = new DAOConfiguration(sectionName);
				cachedSections.put(key, section);
			}
			return section;
		}
		
		private String sectionName;
		private DAOConfiguration(String sectionName){
			this.sectionName = sectionName;
		}
		
		private String buildSectionKey(String key){
			return sectionName + "." + key;
		}
		
		public String getUrl(){
			return getValue(buildSectionKey(URL_KEY), true);
		}
		
		public String getDriver(){
			return getValue(buildSectionKey(DRIVER_KEY), false);
		}
		
		public String getUsername(){
			return getValue(buildSectionKey(USERNAME_KEY), null != getPassword());
		}
		
		public String getPassword(){
			return getValue(buildSectionKey(PASSWORD_KEY), false);
		}
		
	}
	
	public final static class Email{
		
		private static final String AUDIT_EMAIL_KEY = "email.auditEmail";
		private static final String SMTP_SERVER_KEY = "email.smtpServer";
		private static final String SMTP_PORT_KEY = "email.smtpPort";
		private static final String SMTP_USERNAME_KEY = "email.smtpUsername";
		private static final String SMTP_PASSWORD_KEY = "email.smtpPassword";
		private static final String SMTP_IS_ATHENTICATION_REQUIRED_KEY = "email.smtpIsAuthenticationRequired";
		
		public static String getAuditEmail(){ 	
			return getValue(AUDIT_EMAIL_KEY, false);
		}
		
		public static String getSmtpServer(){
			return getValue(SMTP_SERVER_KEY, true);
		}

		public static String getSmtpUsername() {
			return getValue(SMTP_USERNAME_KEY, getIsAuthenticationRequired().toLowerCase() == "true");
		}

		public static String getSmtpPassword() {
			return getValue(SMTP_PASSWORD_KEY, getIsAuthenticationRequired().toLowerCase() == "true");
		}

		public static String getSmtpPort() {
			return getValue(SMTP_PORT_KEY, "25");
		}
		
		public static String getIsAuthenticationRequired() {
			return getValue(SMTP_IS_ATHENTICATION_REQUIRED_KEY, false);
		}
	}
	
	public final static class Fonts{
		private static final String FONTS_PATH_KEY = "fonts.path";
		private static final String DEFAULT_FONT_FAMILY_NAME_KEY = "fonts.defaultFamilyName";
		
		private static String fontsPath = null;
		public static String getFontsPath(){
			if (null == fontsPath) {
				fontsPath = getValue(FONTS_PATH_KEY, true);
			}
			
			return fontsPath;
		}
		public static String getDefaultFontFamilyName() {
			return getValue(DEFAULT_FONT_FAMILY_NAME_KEY, "Arial");
		}
	}
	
	public final static class GeoIp{
		private static final String GEO_IP_PATH_KEY = "geoip.path";
		
		private static String geoIpPath = null;
		public static String getGeoIpPath(){
			if(null == geoIpPath){
				geoIpPath = getValue(GEO_IP_PATH_KEY, true);
			}
			return geoIpPath;
		}
	}
	
	public static class General{
		private static final String PRODUCT_ID_KEY = "general.productId";
		
		private static UUID productGuid = null;
		public static UUID getProductGuid(){
			if(null == productGuid){
				productGuid = UUID.fromString(getValue(PRODUCT_ID_KEY, true));
			}
			return productGuid;
		}
	}
	
	public static class Infinispan{
		private static final String CONFIG_PATH_KEY = "infinispan.config-path";
		
		private static String configPath = null;
		public static String getConfigPath(){
			if(null == configPath){
				configPath = getValue(CONFIG_PATH_KEY, true);
			}
			return configPath;
		}
		
		private static final String BIND_IP_KEY = "infinispan.bind.ip";
		public static String getBindIp() {
			return getValue(BIND_IP_KEY, false);
		}
	}
}
