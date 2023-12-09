package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public class CountriesDataAccess {

	private static final String COUNTRY_ID_PARAM = "$country_id";

	public static void getCountries(IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					// new SqlParam(COUNTRY_ID_PARAM, countryId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getCountries", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            callback.call(resultSet, 0);
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void getStatesProvinces(IResultSetCallback callback, Integer countryId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(COUNTRY_ID_PARAM, countryId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getStatesProvinces", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            callback.call(resultSet, 0);
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void getTimeZones(IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					// new SqlParam(COUNTRY_ID_PARAM, countryId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("getTimezones", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            callback.call(resultSet, 0);
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
