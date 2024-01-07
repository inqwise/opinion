package com.inqwise.opinion.library.dao;

import java.sql.Date;
import java.sql.DriverManager;
import java.time.LocalDate;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.inqwise.opinion.infrastructure.dao.DAOConfigurationException;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.BaseApplicationConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.cj.xdevapi.DatabaseObjectDescription;

@Testcontainers
public class AccountsDataAccessTest {
	private static final ApplicationLog logger = ApplicationLog.getLogger();
	
	@Container
	private final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest");
	
	@Test
	void testGetAccountsWithExpiredServicePackages() throws Exception {
		logger.debug("testGetAccountsWithExpiredServicePackages");
		
		final String sqlCreateProcedure = 
			    "CREATE PROCEDURE getAccountsWithExpiredServicePackages(IN $expiry_date DATE) " +
			    "BEGIN " +
			    "SELECT $expiry_date; " +
			    "END ";
		
		try (var conn = DriverManager.getConnection(MY_SQL_CONTAINER.getJdbcUrl(), "root",  MY_SQL_CONTAINER.getPassword())) {
			try(var statement = conn.createStatement()){
				logger.debug("connected to db");
				statement.execute("CREATE DATABASE office;");
				statement.execute("use office;");
				statement.execute(sqlCreateProcedure);
			}
        }
		
    	var dataSource = new ComboPooledDataSource("office"); 
    	dataSource.setDriverClass("com.mysql.cj.jdbc.Driver"); 
    	dataSource.setJdbcUrl(String.format("jdbc:mysql://localhost:%s/office", MY_SQL_CONTAINER.getFirstMappedPort())); 
    	dataSource.setUser("root"); 
    	dataSource.setPassword(MY_SQL_CONTAINER.getPassword());
    	Database.setInstance("office", new Database(dataSource));
    	
		LocalDate expectedExpiryDate = LocalDate.now();
		var array = AccountsDataAccess.getAccountsWithExpiredServicePackages(Date.valueOf(expectedExpiryDate));
	
		Assertions.assertNotNull(array);
		logger.debug("array:%s", array.toString());
		
		JSONObject json = (JSONObject) array.get(0);
		LocalDate actualExpiryDate = ((java.sql.Date)json.get("$expiry_date")).toLocalDate();
		Assertions.assertTrue(expectedExpiryDate.equals(actualExpiryDate), String.format("%s not equal to %s", expectedExpiryDate, actualExpiryDate));
	}
}
