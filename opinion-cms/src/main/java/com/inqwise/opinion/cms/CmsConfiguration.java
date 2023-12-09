package com.inqwise.opinion.cms;

import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class CmsConfiguration extends ApplicationConfiguration {
	private static final String SYSTEM_TEMPLATES_FOLDER_KEY = "cms.systemTemplatesFolder";
	private static final String CMS_DATABASE_NAME = "cms.databaseName";
	private static final String CMS_PORTAL_NAME = "cms.portal_name";
	
	public static String getTemplatesFolder(){
		return getValue(SYSTEM_TEMPLATES_FOLDER_KEY, true);
	}
	
	public static String getDatabaseName(){
		return getValue(CMS_DATABASE_NAME, "cms");
	}
	
	public static String getCurrentPortalName(){
		return getValue(CMS_PORTAL_NAME, true);
	}
	
}
