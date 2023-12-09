package com.inqwise.opinion.cms.dao.kb;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.dao.DAOFactory;

public class Topics {

	private static final String TOPIC_ID_PARAM = "$topic_id";
	private static final String TOPIC_NAME_PARAM = "$topic_name";
	private static final String TOPIC_URI_PARAM = "$topic_uri";

	public static void get(IResultSetCallback callback, Integer id, String uri) throws DAOException {
		
		CallableStatement call = null;
		Connection connection = null;
		ResultSet resultSet = null;

		SqlParam[] params = {
			new SqlParam(TOPIC_ID_PARAM, id),
			new SqlParam(TOPIC_URI_PARAM, uri)
		};

		Database factory = DAOFactory.getInstance(CmsConfiguration
				.getDatabaseName());
		try {
			call = factory.GetProcedureCall("getTopics", params);
			connection = call.getConnection();
			ResultSet reader = call.executeQuery();

			// Topics
			callback.call(reader, 1);
			reader.close();

			/*
			 * // Tags if(call.getMoreResults()){
			 * callback.call(call.getResultSet(), 2); }
			 */

		} catch (Exception e) {
			throw null == call ? new DAOException(e)
					: new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static int set(Integer id, String name, String uri)
			throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;

		SqlParam[] params = { 
				new SqlParam(TOPIC_ID_PARAM, id),
				new SqlParam(TOPIC_NAME_PARAM, name),
				new SqlParam(TOPIC_URI_PARAM, uri)
		};

		try {

			Database factory = DAOFactory.getInstance(CmsConfiguration
					.getDatabaseName());
			call = factory.GetProcedureCall("setTopic", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();

			if (resultSet.next()) {
				id = resultSet.getInt("topic_id");
			} else {
				throw new Exception("No Results");
			}

			resultSet.close();
			return id;

		} catch (Exception e) {
			throw null == call ? new DAOException(e)
					: new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}

	}

	public static void delete(Integer id) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;

		SqlParam[] params = { new SqlParam(TOPIC_ID_PARAM, id) };

		try {
			Database factory = DAOFactory.getInstance(CmsConfiguration
					.getDatabaseName());
			call = factory.GetProcedureCall("deleteTopic", params);
			connection = call.getConnection();
		} catch (Exception e) {
			throw null == call ? new DAOException(e)
					: new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

}
