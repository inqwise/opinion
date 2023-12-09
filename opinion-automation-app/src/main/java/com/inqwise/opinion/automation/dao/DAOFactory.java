
package com.inqwise.opinion.automation.dao;


import com.inqwise.opinion.infrastructure.dao.DAOConfigurationException;
import com.inqwise.opinion.infrastructure.dao.Database;

public class DAOFactory {
	
    protected DAOFactory(){}
    
    public static Database getOfficeInstance() throws DAOConfigurationException {
    	return getInstance(Databases.Office.toString());
    }
    
    public static Database getInstance() throws DAOConfigurationException {
    	return getInstance(Databases.Default);
    }
    
    public static Database getInstance(Databases database) throws DAOConfigurationException {
    	return getInstance(database.toString());
    }
    
    public static Database getInstance(String name) throws DAOConfigurationException {
    	return Database.getInstance(name);
    }
}
