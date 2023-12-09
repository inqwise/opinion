package com.inqwise.opinion.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;

public final class ControlTypes {
	public static <T> List<T> getControlTypes(IDataFillable<T> data) throws DAOException {
		Connection connection = null;	
    	CallableStatement call = null;
		ResultSet resultSet = null;
		List<T> controlTypes = new ArrayList<T>();
		SqlParam[] params = {};
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("getControlTypes", params);
            connection = call.getConnection();
            resultSet = call.executeQuery();
            while (resultSet.next()) {
            	T controlType = data.fill(resultSet);
            	controlTypes.add(controlType);
            }
            
			return controlTypes;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
    }
}
