/**
 * 
 */
package com.inqwise.opinion.payments.dao;


import com.inqwise.opinion.infrastructure.dao.DAOConfigurationException;
import com.inqwise.opinion.infrastructure.dao.Database;


public class DAOFactory {
	
    public static Database getPayInstance() throws DAOConfigurationException {
    	return Database.getInstance(Databases.Payments.toString());
    }
    
    public static Database getInstance() throws DAOConfigurationException {
    	return getInstance(Databases.Default);
    }
    
    public static Database getInstance(Databases database) throws DAOConfigurationException {
    	return Database.getInstance(database.toString());
    }
    
    
    
}
