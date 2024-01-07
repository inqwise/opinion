package com.inqwise.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.common.SurveyStatistics;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.library.dao.Databases;

public class Surveys extends OpinionsDataAccess {

	// getSurveyShortStatistics
	public static OperationResult<SurveyStatistics> getSurveyShortStatistics(
			Long opinionId) throws DAOException {
		CallableStatement call = null;
		ResultSet resultSet = null;
		Connection connection = null;

		SqlParam[] params = 
			{ 
				new SqlParam(OPINION_ID_PARAM, opinionId),
			};

		try {

			Database factory = DAOFactory.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("getSurveyShortStatistics", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();

			int countOfControls = 0;
			int countOfSessions = 0;
			int countOfFinishedSessions = 0;

			if (resultSet.next()) {
				countOfControls = ResultSetHelper.optInt(resultSet,
						"cnt_of_controls", 0);
				countOfSessions = ResultSetHelper.optInt(resultSet,
						"cnt_of_sessions", 0);
				countOfFinishedSessions = ResultSetHelper.optInt(resultSet,
						"cnt_of_finished_sessions", 0);
			}

			return new OperationResult<SurveyStatistics>(new SurveyStatistics(
					countOfControls, countOfSessions, countOfFinishedSessions));
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call
					,e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	
}
