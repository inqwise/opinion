package com.inqwise.opinion.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.casper.data.model.CDataCacheContainer;
import net.casper.data.model.CDataCacheDBAdapter;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.opinion.common.ParentType;
import com.inqwise.opinion.opinion.common.analizeResults.IAnalizeControl;
import com.inqwise.opinion.opinion.common.analizeResults.IAnalizeOption;
import com.inqwise.opinion.opinion.common.analizeResults.IControlsContainer;
import com.inqwise.opinion.opinion.common.analizeResults.IOptionsContainer;

public class Results extends DAOBase {
	private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String OPINION_ID_PARAM = "$opinion_id";
	private static final String TRANSLATION_ID_PARAM = "$translation_id";
	private static final String ANSWERER_SESSION_ID_PARAM = "$answer_session_id";
	private static final String SESSION_ID_PARAM = "$session_id";
	private static final String INCLUDE_PARTIAL_PARAM = "$include_partial_answers";
	private static final String INCLUDE_PARTIAL_ANSWERS_PARAM = "$include_partial_answers";
	private static final String INCLUDE_PARTIAL_STATISTICS_PARAM = "$include_partial_statistics";
	private static final String CONTROL_ID_PARAM = "$control_id";
	private static final String OPTION_ID_PARAM = "$option_id";
	private static final String COLLECTOR_ID_PARAM = "$collector_id";
	private static final String INCLUDE_ATTRIBUTES_PARAM = "$include_attributes";
	private static final String FROM_DATE_PARAM = "$from_date";
	private static final String TO_DATE_PARAM = "$to_date";
	static ApplicationLog logger = ApplicationLog.getLogger(Results.class);

	public static List<IAnalizeControl> getControlsResults(
			Long opinionId, Long accountId, Long translationId,
			IDataFillable<IAnalizeControl> controlHandler,
			IDataFillable<IAnalizeOption> optionHandler, String answererSessionId, Long collectorId, boolean includePartialAnswers, boolean includePartialStatistics, boolean includeAttributeContol) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		Dictionary<Long, IAnalizeControl> controlsDictionary = new Hashtable<Long, IAnalizeControl>();
		List<IAnalizeControl> controls = new ArrayList<IAnalizeControl>();

		SqlParam[] params = 
				{
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(OPINION_ID_PARAM, opinionId),
					new SqlParam(TRANSLATION_ID_PARAM, translationId),
					new SqlParam(ANSWERER_SESSION_ID_PARAM, answererSessionId),
					new SqlParam(INCLUDE_PARTIAL_ANSWERS_PARAM, includePartialAnswers),
					new SqlParam(INCLUDE_PARTIAL_STATISTICS_PARAM, includePartialStatistics),
					new SqlParam(COLLECTOR_ID_PARAM, collectorId),
					new SqlParam(INCLUDE_ATTRIBUTES_PARAM, includeAttributeContol),
				};

		try {

			Database factory = DAOFactory
					.getInstance(Databases.OpinionReplication);
			call = factory.GetProcedureCall("getResults", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			// controls
			while (resultSet.next()) {
				IAnalizeControl controlResult = controlHandler.fill(resultSet);
				if (controlResult.getParentType() == ParentType.Control) {
					IControlsContainer controlsContainer = (IControlsContainer) controlsDictionary.get(controlResult.getParentId());
					controlsContainer.addControl(controlResult);
				} else {
					controls.add(controlResult);
				}
				controlsDictionary.put(controlResult.getControlId(),
						controlResult);
			}
			resultSet.close();

			// options
			if (call.getMoreResults()) {
				resultSet = call.getResultSet();
				while (resultSet.next()) {
					IAnalizeOption optionResult = optionHandler.fill(resultSet);
					IOptionsContainer optionsContainer = (IOptionsContainer) controlsDictionary.get(optionResult.getControlId());
					optionsContainer.addOption(optionResult);
				}
			}

			return controls;

		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static CDataCacheContainer getAllResults(long opinionId, Long accountId, Long[] sessionIds, boolean includePartial, TreeMap<Long, Integer> headerIdsMap) throws DAOException{
		CDataCacheContainer result;
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(OPINION_ID_PARAM, opinionId),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(SESSION_ID_PARAM, StringUtils.join(sessionIds, ",")),
					new SqlParam(INCLUDE_PARTIAL_PARAM, includePartial),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getAllResults", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            String[] primaryKeys = null;
            result = CDataCacheDBAdapter.loadData(resultSet, null, primaryKeys, new LinkedHashMap());
            
            if(call.getMoreResults()){
            	resultSet = call.getResultSet();
            	int cnt = 0;
            	while(resultSet.next()){
            		headerIdsMap.put(resultSet.getLong("control_id"), cnt++);
            	}
            }
            
            return result;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static Map<String, String> getResultsFreeText(long opinionId, Long controlId, Long optionId, boolean includePartial, Long accountId) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		HashMap<String, String> result = new HashMap<String, String>();
		try{
			SqlParam[] params = {
					new SqlParam(OPINION_ID_PARAM, opinionId),
					new SqlParam(CONTROL_ID_PARAM, controlId),
					new SqlParam(OPTION_ID_PARAM, optionId),
					new SqlParam(INCLUDE_PARTIAL_PARAM, includePartial),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getResultsFreeText", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            
            while(resultSet.next()){
            	String comment = ResultSetHelper.optString(resultSet, "comment");
            	String answerText = ResultSetHelper.optString(resultSet, "answer_value");
            	result.put(resultSet.getString("answer_session_id"), (null == comment ? answerText : comment));
            }
            
            return result;
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static CDataCacheContainer getCountriesStatistics(long opinionId, Long accountId, Date from, Date to, Long collectorId, boolean includePartial) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(OPINION_ID_PARAM, opinionId),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(COLLECTOR_ID_PARAM, collectorId),
					new SqlParam(FROM_DATE_PARAM, from),
					new SqlParam(TO_DATE_PARAM, to),
					new SqlParam(INCLUDE_PARTIAL_PARAM, includePartial),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getResultsCountriesStatistics", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            String[] primaryKeys = null;
            return CDataCacheDBAdapter.loadData(resultSet, null, primaryKeys, new LinkedHashMap());
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
