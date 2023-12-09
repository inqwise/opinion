package com.inqwise.opinion.library.dao;

import java.security.InvalidParameterException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import net.casper.data.model.CDataCacheContainer;
import net.casper.data.model.CDataCacheDBAdapter;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.BillType;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.pay.ICancelChargeRequest;
import com.inqwise.opinion.library.common.pay.IChargeCreateRequest;
import com.inqwise.opinion.library.common.pay.IChargePayRequest;
import com.inqwise.opinion.library.common.pay.IChargeUpdateRequest;

public class ChargesDataAccess {
	private static final String TOP_PARAM = "$top";
	private static final String BILL_ID_PARAM = "$bill_id";
	private static final String BILL_TYPE_ID_PARAM = "$bill_type_id";
	private static final String CHARGE_ID_PARAM = "$charge_id";
         
	private static final String CHARGE_NAME_PARAM = "$charge_name";      
	private static final String CHARGE_DESCRIPTION_PARAM = "$charge_description";
	private static final String AMOUNT_PARAM = "$amount";
	private static final String USER_ID_PARAM = "$user_id";
	private static final String REFERENCE_TYPE_ID_PARAM = "$reference_type_id";
	private static final String REFERENCE_ID_PARAM = "$reference_id";
	private static final String FOR_ACCOUNT_ID_PARAM = "$for_account_id";
	private static final String POST_PAY_ACTION_PARAM = "$post_pay_action";
	private static final String EXPIRY_DATE_PARAM = "$expiry_date";
	private static final String CHARGE_STATUS_ID_PARAM = "$charge_status_id";
	private static final String AMOUNT_CURRENCY_PARAM = "$amount_currency";
	private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String SOURCE_ID_PARAM = "$source_id";
	private static final String SESSION_ID_PARAM = "$session_id";
	private static final String GEO_COUNTRY_CODE_PARAM = "$geo_country_code";
	private static final String CLIENT_IP_PARAM = "$client_ip";
	private static final String INCLUDE_EXPIRED_PARAM = "$include_expired";
	private static final String BILLED_PARAM = "$billed";
	private static final String CHARGES_IDS_PARAM = "$charges_ids";
	private static final String BACKOFFICE_USER_ID = "$backoffice_user_id";
	private static final String COMMENTS_PARAM = "$comments";
	private static final String POST_PAY_ACTION_DATA_PARAM = "$post_pay_action_data";

	
	public static CDataCacheContainer getCharges(int top, Long billId, Integer billTypeId, Long accountId, Integer statusId, boolean includeExpired, Boolean billed) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(BILL_ID_PARAM, billId),
					new SqlParam(BILL_TYPE_ID_PARAM, billTypeId),
					new SqlParam(TOP_PARAM, top),
					new SqlParam(FOR_ACCOUNT_ID_PARAM, accountId),
					new SqlParam(CHARGE_STATUS_ID_PARAM, statusId),
					new SqlParam(INCLUDE_EXPIRED_PARAM, includeExpired),
					new SqlParam(BILLED_PARAM, billed),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_getCharges", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            String[] primaryKeys = new String[]{"charge_id"};
            return CDataCacheDBAdapter.loadData(resultSet, null, primaryKeys, new Hashtable());
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void getChargeResultSet(long chargeId,
			IResultSetCallback callback, Long accountId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(CHARGE_ID_PARAM, chargeId),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_getCharge", params);
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

	public static boolean deleteCharge(long chargeId, Long userId, Long accountId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(CHARGE_ID_PARAM, chargeId),
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_deleteCharge", params);
        	connection = call.getConnection();
            return call.execute();
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void updateCharge(IChargeUpdateRequest request,
			IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(CHARGE_ID_PARAM, request.getChargeId()),
					new SqlParam(BILL_ID_PARAM, request.getBillId()),
					new SqlParam(BILL_TYPE_ID_PARAM, request.getBillTypeId()),
					new SqlParam(CHARGE_NAME_PARAM, request.getName()),
					new SqlParam(CHARGE_DESCRIPTION_PARAM, request.getDescription()),
					new SqlParam(USER_ID_PARAM, request.getUserId()),
					new SqlParam(ACCOUNT_ID_PARAM, request.getAccountId()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_updateCharge", params);
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

	public static void insertCharge(IChargeCreateRequest request,
			IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(BILL_ID_PARAM, request.getBillId()),
					new SqlParam(BILL_TYPE_ID_PARAM, request.getBillTypeId()),
					new SqlParam(CHARGE_NAME_PARAM, request.getName()),
					new SqlParam(CHARGE_DESCRIPTION_PARAM, request.getDescription()),
					new SqlParam(AMOUNT_PARAM, request.getAmount()),
					new SqlParam(AMOUNT_CURRENCY_PARAM, request.getAmountCurrency()),
					new SqlParam(USER_ID_PARAM, request.getUserId()),
					new SqlParam(REFERENCE_TYPE_ID_PARAM, request.getReferenceTypeId()),
					new SqlParam(REFERENCE_ID_PARAM, request.getReferenceId()),
					new SqlParam(FOR_ACCOUNT_ID_PARAM, request.getAccountId()),
					new SqlParam(POST_PAY_ACTION_PARAM, request.getPostPayAction()),
					new SqlParam(EXPIRY_DATE_PARAM, request.getExpiryDate()),
					new SqlParam(CHARGE_STATUS_ID_PARAM, request.getStatusId()),
					new SqlParam(POST_PAY_ACTION_DATA_PARAM, (null == request.getPostPayActionData() ? null : request.getPostPayActionData().toString())),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_setCharge", params);
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
	
	public static CDataCacheContainer getPostPayActions(Long chargeId, Long billId, Integer billTypeId) throws DAOException{
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(CHARGE_ID_PARAM, chargeId),
					new SqlParam(BILL_ID_PARAM, billId),
					new SqlParam(BILL_TYPE_ID_PARAM, billTypeId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_getBillPostPayActions", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            String[] primaryKeys = new String[] {"charge_id"};
            return CDataCacheDBAdapter.loadData(resultSet, null, primaryKeys, new LinkedHashMap());
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static OperationResult<ChargeStatus> pay(IChargePayRequest request) throws DAOException {
		OperationResult<ChargeStatus> result = null;
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, request.getAccountId()),
					new SqlParam(CHARGE_ID_PARAM, request.getChargeId()),
					new SqlParam(USER_ID_PARAM, request.getUserId()),
					new SqlParam(AMOUNT_PARAM, request.getAmount()),
					new SqlParam(SOURCE_ID_PARAM, request.getSourceId()),
					new SqlParam(SESSION_ID_PARAM, request.getSessionId()),
					new SqlParam(GEO_COUNTRY_CODE_PARAM, request.getGeoCountryCode()),
					new SqlParam(CLIENT_IP_PARAM, request.getClientIp()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_setChargePayment", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()){
	            int errorCode = resultSet.getInt("error_code");
	            switch(errorCode){
	            case 0:
	            	ChargeStatus status = ChargeStatus.fromInt(resultSet.getInt("charge_status_id"));
	            	result = new OperationResult<ChargeStatus>(status, resultSet.getLong("account_operation_id"));
	            	break;
	            case 2:
	            	result = new OperationResult<ChargeStatus>(ErrorCode.NoEnoughFunds);
	            	break;
	            default:
	            	throw new InvalidParameterException("pay : errorCode not supported " + errorCode);
	            }
            } else {
            	UUID errorId = ApplicationLog.getLogger(InvoicesDataAccess.class).error("pay_setChargePayment : No results returned");
            	result = new OperationResult<ChargeStatus>(ErrorCode.GeneralError, errorId);
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
	
	public static CDataCacheContainer getChargesByReferenceId(Long accountId,
			long referenceId, int referenceTypeId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(REFERENCE_ID_PARAM, referenceId),
					new SqlParam(REFERENCE_TYPE_ID_PARAM, referenceTypeId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_getChargeByReferenceId", params);     
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
	
	public static boolean addChargesToBill(List<Long> chargesIds, Long userId, long accountId, long billId, BillType billType) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(CHARGES_IDS_PARAM, StringUtils.join(chargesIds, ",")),
					new SqlParam(USER_ID_PARAM, userId),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(BILL_ID_PARAM, billId),
					new SqlParam(BILL_TYPE_ID_PARAM, billType.getValue()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_addChargesToBill", params);
        	connection = call.getConnection();
            return call.execute();
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static boolean removeChargeFromBill(long chargeId, Long userId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(CHARGE_ID_PARAM, chargeId),
					new SqlParam(USER_ID_PARAM, userId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_removeChargeFromBill", params);
        	connection = call.getConnection();
            return call.execute();
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static BaseOperationResult cancelCharge(ICancelChargeRequest request) throws DAOException {
		BaseOperationResult result = null;
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(COMMENTS_PARAM, request.getComments()),
					new SqlParam(CHARGE_ID_PARAM, request.getChargeId()),
					new SqlParam(BACKOFFICE_USER_ID, request.getBackOfficeUserId()),
					new SqlParam(GEO_COUNTRY_CODE_PARAM, request.getGeoCountryCode()),
					new SqlParam(CLIENT_IP_PARAM, request.getClientIp()),
					new SqlParam(SOURCE_ID_PARAM, request.getSourceId()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_cancelCharge", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()){
	            int errorCode = resultSet.getInt("error_code");
	            switch(errorCode){
	            case 0:
	            	result = new BaseOperationResult(resultSet.getLong("account_operation_id"));
	            	break;
	            case 2:
	            	result = new BaseOperationResult(ErrorCode.NoEnoughFunds);
	            	break;
	            case 3:
	            	result = new BaseOperationResult(ErrorCode.NotExist);
	            	break;
	            default:
	            	throw new InvalidParameterException("cancelCharge : errorCode not supported " + errorCode);
	            }
            } else {
            	UUID errorId = ApplicationLog.getLogger(InvoicesDataAccess.class).error("pay_cancelCharge : No results returned");
            	result = new OperationResult<ChargeStatus>(ErrorCode.GeneralError, errorId);
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
}
