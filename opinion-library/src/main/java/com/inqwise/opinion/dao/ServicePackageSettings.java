package com.inqwise.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.common.servicePackage.IOpinionServicePackageSettingsUpdateRequest;
import com.inqwise.opinion.common.servicePackage.IServicePackageSettings;

public class ServicePackageSettings extends DAOBase{
	private static final String SERVICE_PACKAGE_SUPPLY_DAYS_INTERVAL_PARAM = "$sps_supply_days_interval";
	private static final String SERVICE_PACKAGE_SUPPLIED_COUNT_SESSIONS_PARAM = "$sps_supplied_count_sessions";
	private static final String SERVICE_PACKAGE_INIT_COUNT_SESSIONS_PARAM = "$sps_init_count_sessions";
	private static final String SERVICE_PACKAGE_MAX_RESPONSES_PER_OPINION_PARAM = "$sps_max_responses_per_opinion";
	private static final String SERVICE_PACKAGE_CONTROLS_PER_OPINION_PARAM = "$sps_max_controls_per_opinion";
	private static final String SERVICE_PACKAGE_MAX_COLLECTORS_PARAM = "$sps_max_collectors";
	private static final String SERVICE_PACKAGE_MAX_OPINIONS_PARAM = "$sps_max_opinions";
	private static final String SERVICE_PACKAGE_ID_PARAM = "$service_package_id";
	static ApplicationLog logger = ApplicationLog.getLogger(ServicePackageSettings.class);
	
	private static OperationResult<IServicePackageSettings> fillServicePackageSettings(Integer servicePackageId, IDataFillable<IServicePackageSettings> data, List<IServicePackageSettings> settingsList) throws DAOException{
		CallableStatement call = null;
		ResultSet resultSet = null;
		OperationResult<IServicePackageSettings> result = null;
		Connection connection = null;
		IServicePackageSettings settings = null;
		
		SqlParam[] params = {
				new SqlParam(SERVICE_PACKAGE_ID_PARAM, servicePackageId),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("getServicePackageSetting", params);
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            while (resultSet.next()) {
            	settings = data.fill(resultSet);
            	if(null != settingsList){
            		settingsList.add(settings);
            	}
            } 
            
            if(null == settings) {
            	result = new OperationResult<IServicePackageSettings>(ErrorCode.NoResults);
            } else 
            {
            	result = new OperationResult<IServicePackageSettings>(settings);
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
	
	public static OperationResult<IServicePackageSettings> getServicePackageSettings(Integer servicePackageId, IDataFillable<IServicePackageSettings> data) throws DAOException{
		OperationResult<IServicePackageSettings> result = fillServicePackageSettings(servicePackageId, data, null);
		if(result.hasError()){
			return result.toErrorResult();
		} else {
			try {
				return new OperationResult<IServicePackageSettings>(result.getValue());
			} catch (Exception e) {
				throw new DAOException(e);
			}
		}
	}
	
	public static void updateServicePackageSettings(IOpinionServicePackageSettingsUpdateRequest request) throws DAOException{
		CallableStatement call = null;
		Connection connection = null;
		
		SqlParam[] params = {
				new SqlParam(SERVICE_PACKAGE_ID_PARAM, request.getServicePackageId()),
				new SqlParam(SERVICE_PACKAGE_MAX_OPINIONS_PARAM, request.getMaxOpinions()),
				new SqlParam(SERVICE_PACKAGE_MAX_COLLECTORS_PARAM, request.getMaxCollectors()),
				new SqlParam(SERVICE_PACKAGE_CONTROLS_PER_OPINION_PARAM, request.getMaxControlsPerOpinion()),
				new SqlParam(SERVICE_PACKAGE_MAX_RESPONSES_PER_OPINION_PARAM, request.getMaxResponsesPerOpinion()),
				new SqlParam(SERVICE_PACKAGE_INIT_COUNT_SESSIONS_PARAM, request.getInitCountSessions()),
				new SqlParam(SERVICE_PACKAGE_SUPPLIED_COUNT_SESSIONS_PARAM, request.getSuppliedCountSessions()),
				new SqlParam(SERVICE_PACKAGE_SUPPLY_DAYS_INTERVAL_PARAM, request.getSupplyIntervalInDays()),
        };
		
		try {
			
			Database factory = DAOFactory.getInstance(Databases.Opinion);
        	call = factory.GetProcedureCall("setServicePackageSettings", params);
        	connection = call.getConnection();
            call.execute();
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}

