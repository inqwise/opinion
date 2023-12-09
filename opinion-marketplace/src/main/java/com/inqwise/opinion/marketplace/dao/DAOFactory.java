/**
 * 
 */
package com.inqwise.opinion.marketplace.dao;


import com.inqwise.opinion.infrastructure.dao.DAOConfigurationException;
import com.inqwise.opinion.infrastructure.dao.Database;


public class DAOFactory {
	
    public static Database getMarketInstance() throws DAOConfigurationException {
    	return Database.getInstance(Databases.Market.toString());
    }
    
    public static Database getInstance() throws DAOConfigurationException {
    	return getInstance(Databases.Default);
    }
    
    public static Database getInstance(Databases database) throws DAOConfigurationException {
    	return Database.getInstance(database.toString());
    }
    
    
    
}
