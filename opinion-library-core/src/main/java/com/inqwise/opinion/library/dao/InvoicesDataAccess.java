package com.inqwise.opinion.library.dao;

import java.security.InvalidParameterException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import net.casper.data.model.CDataCacheContainer;
import net.casper.data.model.CDataCacheDBAdapter;

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
import com.inqwise.opinion.library.common.pay.IInvoiceCreateRequest;
import com.inqwise.opinion.library.common.pay.IOpenInvoiceRequest;
import com.inqwise.opinion.library.common.pay.IUpdateInvoiceRequest;
import com.inqwise.opinion.library.common.pay.InvoiceItemType;
import com.inqwise.opinion.library.common.pay.InvoiceStatus;

public class InvoicesDataAccess {

	private static final String INVOICE_ID_PARAM = "$invoice_id";
	private static final String FOR_ACCOUNT_ID_PARAM = "$for_account_id";
	private static final String NOTES_PARAM = "$notes";
	private static final String INVOICE_STATUS_ID_PARAM = "$invoice_status_id";
	private static final String INSERT_USER_ID_PARAM = "$insert_user_id";
	private static final String TOP_PARAM = "$top";
	private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String USER_ID_PARAM = "$user_id";
	private static final String SOURCE_GUID_PARAM = "$source_guid";
	private static final String BACKOFFICE_USER_ID = "$backoffice_user_id";
	private static final String INVOICE_FROM_DATE_PARAM = "$invoice_from_date";
	private static final String INVOICE_TO_DATE_PARAM = "$invoice_to_date";
	private static final String COMPANY_NAME_PARAM = "$company_name";
	private static final String FIRST_NAME_PARAM = "$first_name";
	private static final String LAST_NAME_PARAM = "$last_name";
	private static final String BADDRESS1_PARAM = "$address1";
	private static final String ADDRESS2_PARAM = "$address2";
	private static final String CITY_PARAM = "$city";
	private static final String STATE_ID_PARAM = "$state_id";
	private static final String POSTAL_CODE_PARAM = "$postal_code";
	private static final String COUNTRY_ID_PARAM = "$country_id";
	private static final String PHONE1_PARAM = "$phone1";
	private static final String GEO_COUNTRY_CODE_PARAM = "$geo_country_code";
	private static final String CLIENT_IP_PARAM = "$client_ip";
	private static final String ACCOUNT_OPERATIONS_IDS_PARAM = "$account_operations_ids";
	private static final String CHARGES_IDS_PARAM = "$charges_ids";
	private static final String BILL_ID_PARAM = "$bill_id";
	private static final String BILL_TYPE_ID_PARAM = "$bill_type_id";
	private static final String TOTAL_CREDIT_PARAM = "$total_credit";
	private static final String TOTAL_DEBIT_PARAM = "$total_debit";

	public static void getInvoiceResultSet(long invoiceId,
			IResultSetCallback callback, Long accountId, Integer statusId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(INVOICE_ID_PARAM, invoiceId),
					new SqlParam(FOR_ACCOUNT_ID_PARAM, accountId),
					new SqlParam(INVOICE_STATUS_ID_PARAM, statusId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_getInvoice", params);
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

	public static void insertInvoice(IInvoiceCreateRequest request,
			IResultSetCallback callback) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(FOR_ACCOUNT_ID_PARAM, request.getForAccountId()),
					new SqlParam(INSERT_USER_ID_PARAM, request.getUserId()),
					new SqlParam(INVOICE_FROM_DATE_PARAM, request.getInvoiceFromDate()),
					new SqlParam(INVOICE_TO_DATE_PARAM, request.getInvoiceToDate()),
					new SqlParam(COMPANY_NAME_PARAM, request.getCompanyName()),
					new SqlParam(FIRST_NAME_PARAM, request.getFirstName()),
					new SqlParam(LAST_NAME_PARAM, request.getLastName()),
					new SqlParam(BADDRESS1_PARAM, request.getAddress1()),
					new SqlParam(ADDRESS2_PARAM, request.getAddress2()),
					new SqlParam(CITY_PARAM, request.getCity()),
					new SqlParam(STATE_ID_PARAM, request.getStateId()),
					new SqlParam(POSTAL_CODE_PARAM, request.getPostalCode()),
					new SqlParam(COUNTRY_ID_PARAM, request.getCountryId()),
					new SqlParam(PHONE1_PARAM, request.getPhone1()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_setInvoice", params);
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

	public static boolean deleteInvoice(long invoiceId, long userId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(INVOICE_ID_PARAM, invoiceId),
					new SqlParam(USER_ID_PARAM, userId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_deleteInvoice", params);
        	connection = call.getConnection();
        	call.execute();
            return true;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static CDataCacheContainer getInvoices(int top, Long accountId,
			Integer invoiceStatusId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(INVOICE_STATUS_ID_PARAM, invoiceStatusId),
					new SqlParam(TOP_PARAM, top),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_getInvoices", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            String[] primaryKeys = new String[]{"invoice_id"};
            return CDataCacheDBAdapter.loadData(resultSet, null, primaryKeys, new LinkedHashMap());
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static boolean updateInvoice(
			IUpdateInvoiceRequest request) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(INVOICE_ID_PARAM, request.getInvoiceId()),
					new SqlParam(USER_ID_PARAM, request.getModifyUserId()),
					new SqlParam(INVOICE_FROM_DATE_PARAM, request.getInvoiceFromDate()),
					new SqlParam(INVOICE_TO_DATE_PARAM, request.getInvoiceToDate()),
					new SqlParam(COMPANY_NAME_PARAM, request.getCompanyName()),
					new SqlParam(FIRST_NAME_PARAM, request.getFirstName()),
					new SqlParam(LAST_NAME_PARAM, request.getLastName()),
					new SqlParam(BADDRESS1_PARAM, request.getAddress1()),
					new SqlParam(ADDRESS2_PARAM, request.getAddress2()),
					new SqlParam(CITY_PARAM, request.getCity()),
					new SqlParam(STATE_ID_PARAM, request.getStateId()),
					new SqlParam(POSTAL_CODE_PARAM, request.getPostalCode()),
					new SqlParam(COUNTRY_ID_PARAM, request.getCountryId()),
					new SqlParam(PHONE1_PARAM, request.getPhone1()),
					new SqlParam(NOTES_PARAM, request.getNotes()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_updateInvoice", params);
        	connection = call.getConnection();
        	call.execute();
            return true;
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static BaseOperationResult openInvoice(IOpenInvoiceRequest request) throws DAOException {
		BaseOperationResult result = null;
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(INVOICE_ID_PARAM, request.getInvoiceId()),
					new SqlParam(BACKOFFICE_USER_ID, request.getBackOfficeUserId()),
					new SqlParam(GEO_COUNTRY_CODE_PARAM, request.getGeoCountryCode()),
					new SqlParam(CLIENT_IP_PARAM, request.getClientIp()),
					new SqlParam(SOURCE_GUID_PARAM, request.getSourceGuid()),
					new SqlParam(ACCOUNT_ID_PARAM, request.getAccountId()),
					new SqlParam(ACCOUNT_OPERATIONS_IDS_PARAM, StringUtils.join(request.getAccountOperationsIds(), ",")),
					new SqlParam(CHARGES_IDS_PARAM, StringUtils.join(request.getChargesIds(), ",")),
					new SqlParam(TOTAL_CREDIT_PARAM, request.getTotalCredit()),
					new SqlParam(TOTAL_DEBIT_PARAM, request.getTotalDebit()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_openInvoice", params);     
        	connection = call.getConnection();
            resultSet = call.executeQuery();
            if(resultSet.next()){
	            int errorCode = resultSet.getInt("error_code");
	            switch(errorCode){
	            case 0:
	            	result = new BaseOperationResult();
	            	break;
	            case 2:
	            	result = new BaseOperationResult(ErrorCode.NoEnoughFunds);
	            	break;
	            case 3:
	            	result = new BaseOperationResult(ErrorCode.InvalidOperation);
	            	break;
	            default:
	            	throw new InvalidParameterException("openInvoice : errorCode not supported " + errorCode);
	            }
            } else {
            	UUID errorId = ApplicationLog.getLogger(InvoicesDataAccess.class).error("pay_openInvoice : No results returned");
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

	public static Map<InvoiceItemType, CDataCacheContainer> getInvoiceItems(long billId, int billTypeId) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(BILL_ID_PARAM, billId),
					new SqlParam(BILL_TYPE_ID_PARAM, billTypeId),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_getBillItems", params);     
        	connection = call.getConnection();
        	
        	Map<InvoiceItemType, CDataCacheContainer> result = new HashMap<InvoiceItemType, CDataCacheContainer>();
        	
            resultSet = call.executeQuery();
            String[] primaryKeys = null;
            result.put(InvoiceItemType.Charge, CDataCacheDBAdapter.loadData(resultSet, null, primaryKeys, new LinkedHashMap()));
            
            if(call.getMoreResults()){
            	resultSet.close();
            	resultSet = call.getResultSet(); 
            	result.put(InvoiceItemType.AccountOperation, CDataCacheDBAdapter.loadData(resultSet, null, primaryKeys, new LinkedHashMap()));
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
