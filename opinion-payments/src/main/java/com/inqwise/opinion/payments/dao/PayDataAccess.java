package com.inqwise.opinion.payments.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.payments.common.IDirectPaymentRequest;
import com.inqwise.opinion.payments.common.IDirectPaymentResponse;
import com.inqwise.opinion.payments.common.PayProcessorTypes;
import com.inqwise.opinion.payments.dao.interfaces.ICreditCardPaymentArgs;
import com.inqwise.opinion.payments.dao.interfaces.IPaymentArgs;

public class PayDataAccess {
	private static final String USER_ID_PARAM = "$user_id";
	private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String PROCESSOR_TYPE_ID_PARAM = "$processor_type_id";
	private static final String LAST_4_DIGITS_PARAM = "$credit_card_number";
	private static final String REQUEST_DATE_PARAM = "$request_date";
	private static final String REQUESTED_AMOUNT_PARAM = "$requested_amount";
	private static final String AMOUNT_PARAM = "$amount";
	private static final String AMOUNT_CURRENCY_PARAM = "$amount_currency";
	private static final String TRANSACTION_DATE_PARAM = "$transaction_date";
	private static final String TRANSACTION_STATUS_ID_PARAM = "$transaction_status_id";
	private static final String PROCESSOR_TRANSACTION_ID_PARAM = "$processor_transaction_id";
	private static final String GEO_COUNTRY_CODE_PARAM = "$geo_country_code";
	private static final String SOURCE_ID_PARAM = "$source_id";
	private static final String CLIENT_IP_PARAM = "$client_ip";
	private static final String SESSION_ID_PARAM = "$session_id";
	private static final String DETAILS_PARAM = "$details";
	private static final String BACKOFFICE_USER_ID_PARAM = "$backoffice_user_id";
	private static final String PARENT_ID_PARAM = "$parent_id";
	private static final String PAY_TRANSACTION_TYPE_ID_PARAM = "$pay_transaction_type_id";
	private static final String CREDIT_CARD_TYPE_ID_PARAM = "$credit_card_type_id";
	private static final String PAY_TRANSACTION_ID_PARAM = "$id";
	
	public static long setPaymentTransaction(IPaymentArgs args) throws DAOException {
		
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		ICreditCardPaymentArgs creditCardArgs = null;
		if(args instanceof ICreditCardPaymentArgs) {
			creditCardArgs = (ICreditCardPaymentArgs)args;
		}
		try{
			SqlParam[] params = {
					new SqlParam(USER_ID_PARAM, args.getUserId()),
					new SqlParam(ACCOUNT_ID_PARAM, args.getAccountId()),
					new SqlParam(PROCESSOR_TYPE_ID_PARAM, args.getProcessorTypeId()),
					new SqlParam(LAST_4_DIGITS_PARAM, (null == creditCardArgs ? null : creditCardArgs.getLast4DigitsOfCreditCardNumber())),
					new SqlParam(REQUEST_DATE_PARAM, args.getRequestDate()),
					new SqlParam(REQUESTED_AMOUNT_PARAM, args.getAmount()),
					new SqlParam(AMOUNT_PARAM, args.getAmount()),
					new SqlParam(AMOUNT_CURRENCY_PARAM, args.getAmountCurrency()),
					new SqlParam(TRANSACTION_DATE_PARAM, args.getPaymentDate()),
					new SqlParam(TRANSACTION_STATUS_ID_PARAM, args.getPaymentStatusId()),
					new SqlParam(PROCESSOR_TRANSACTION_ID_PARAM, args.getProcessorTransactionId()),
					new SqlParam(GEO_COUNTRY_CODE_PARAM, args.getGeoCountryCode()),
					new SqlParam(CLIENT_IP_PARAM, args.getClientIp()),
					new SqlParam(SOURCE_ID_PARAM, args.getSourceId()),
					new SqlParam(SESSION_ID_PARAM, args.getSessionId()),
					new SqlParam(DETAILS_PARAM, args.getDetails()),
					new SqlParam(BACKOFFICE_USER_ID_PARAM, args.getBackofficeUserId()),
					new SqlParam(PARENT_ID_PARAM, args.getParentId()),
					new SqlParam(PAY_TRANSACTION_TYPE_ID_PARAM, args.getTransactionTypeId()),
					new SqlParam(CREDIT_CARD_TYPE_ID_PARAM, (null == creditCardArgs ? null : creditCardArgs.getCreditCardTypeId())),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_setPayment", params);
        	connection = call.getConnection();
        	resultSet = call.executeQuery();
        	resultSet.next();
        	return resultSet.getLong("fund_account_operation_id");
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static void fillTransaction(long id, IResultSetCallback callback) throws DAOException {
		Connection connection = null;	
    	CallableStatement call = null;
		ResultSet resultSet = null;
				
		try {
			SqlParam[] params = {
					new SqlParam(PAY_TRANSACTION_ID_PARAM, id),
			};
			Database factory = DAOFactory.getInstance(Databases.Office);
			call = factory.GetProcedureCall("pay_getTransaction");
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

	public static long setRefundTransaction(IRefundArgs args) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		
		try{
			SqlParam[] params = {
					new SqlParam(USER_ID_PARAM, args.getUserId()),
					new SqlParam(ACCOUNT_ID_PARAM, args.getAccountId()),
					new SqlParam(PROCESSOR_TYPE_ID_PARAM, args.getProcessorTypeId()),
					new SqlParam(REQUEST_DATE_PARAM, args.getRequestDate()),
					new SqlParam(REQUESTED_AMOUNT_PARAM, args.getAmount()),
					new SqlParam(AMOUNT_PARAM, args.getAmount()),
					new SqlParam(AMOUNT_CURRENCY_PARAM, args.getAmountCurrency()),
					new SqlParam(TRANSACTION_DATE_PARAM, args.getRefundDate()),
					new SqlParam(TRANSACTION_STATUS_ID_PARAM, args.getPaymentStatusId()),
					new SqlParam(PROCESSOR_TRANSACTION_ID_PARAM, args.getProcessorTransactionId()),
					new SqlParam(SOURCE_ID_PARAM, args.getSourceId()),
					new SqlParam(DETAILS_PARAM, args.getDetails()),
					new SqlParam(BACKOFFICE_USER_ID_PARAM, args.getBackofficeUserId()),
					new SqlParam(PARENT_ID_PARAM, args.getParentId()),
					new SqlParam(PAY_TRANSACTION_TYPE_ID_PARAM, args.getTransactionTypeId()),
	        };
			
			Database factory = DAOFactory.getInstance(Databases.Office);
        	call = factory.GetProcedureCall("pay_setRefund", params);
        	connection = call.getConnection();
        	resultSet = call.executeQuery();
        	resultSet.next();
        	return resultSet.getLong("refund_account_operation_id");
		
		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
