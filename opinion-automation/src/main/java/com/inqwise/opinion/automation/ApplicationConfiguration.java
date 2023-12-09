package com.inqwise.opinion.automation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.UUID;

public class ApplicationConfiguration {
	 
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
			throw new Error(
					"Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
		}
		
		try {
			CONFIGURATION.load(propertiesFile);
		} catch (IOException e) {
			throw new Error(
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
				throw new Error("The key already contains in destination Config. Actual key: '"+ key +"'");
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
					throw new Error("ExternalConfig location avalable only in root config file.");
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
			throw new Error("Configuration key is missing. Key: " + key);
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
				throw new Error(
						"Not found properties file '" + path + "'.", e);
			} catch (IOException e) {
				throw new Error(
						"Cannot load properties file '" + path + "'.", e);
			} catch (Throwable t){
				throw new Error(
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
	
	private static final String ENDPOINT_KEY = "automation.endpoint";
	
	private static String endpoint = null;
	public static String getEndpoint(){
		if(null == endpoint){
			endpoint = getValue(ENDPOINT_KEY, "127.0.0.1");
		}
		return endpoint;
	}

}
