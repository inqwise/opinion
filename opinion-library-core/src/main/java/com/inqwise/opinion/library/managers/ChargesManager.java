package com.inqwise.opinion.library.managers;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.collect.Lists;
import com.inqwise.opinion.automation.EventsServiceClient;
import com.inqwise.opinion.automation.common.events.ChargeStatusChangedEventArgs;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.AccessValue;
import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IAccess;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.common.parameters.PermissionsKeys;
import com.inqwise.opinion.library.common.parameters.VariablesCategories;
import com.inqwise.opinion.library.common.pay.BillType;
import com.inqwise.opinion.library.common.pay.ChargeModel;
import com.inqwise.opinion.library.common.pay.ChargeRepositoryParser;
import com.inqwise.opinion.library.common.pay.ChargeStatus;
import com.inqwise.opinion.library.common.pay.ICancelChargeRequest;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.pay.IChargeCreateRequest;
import com.inqwise.opinion.library.common.pay.IChargePayRequest;
import com.inqwise.opinion.library.common.pay.IChargeUpdateRequest;
import com.inqwise.opinion.library.dao.ChargesDataAccess;
import com.inqwise.opinion.library.entities.pay.ChargeEntity;

public class ChargesManager {
	static ApplicationLog logger = ApplicationLog.getLogger(ChargesManager.class);
	
	public static List<ChargeModel> getCharges(int top, Long billId, Integer billTypeId, Long accountId, Integer statusId, boolean includeExpired, Boolean billed){
		try {
			var arr = ChargesDataAccess.getCharges(top, billId, billTypeId, accountId, statusId, includeExpired, billed);
			return arr.toList().stream().map(i -> (JSONObject)i).map(new ChargeRepositoryParser()::parse).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new Error(e);
		}
	}
	
	public static OperationResult<ICharge> getCharge(long chargeId, Long accountId){
		
		final OperationResult<ICharge> result = new OperationResult<ICharge>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							ICharge charge = new ChargeEntity(reader);
							result.setValue(charge);							
						}
					}
				}
			};
			
			ChargesDataAccess.getChargeResultSet(chargeId, callback, accountId);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getCharge() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
		
	}
	
	public static OperationResult<ICharge> createCharge(IChargeCreateRequest request){
		
		final OperationResult<ICharge> result = new OperationResult<ICharge>();
		try{
			IAccountView account = null;
			OperationResult<IAccountView> accountResult = AccountsManager.getAccount(request.getAccountId());
			if(accountResult.hasError()){
				result.setError(accountResult);
			} else {
				account = accountResult.getValue();
			}
			
			boolean additionalFundsRequired = true;
			if(!result.hasError()){
				additionalFundsRequired = (AccountsManager.getFreeBalance(request.getAccountId(), null).getValue() - request.getAmount()) < 0;
			}
			
			// Check Permission
			HashMap<String, IVariableSet> permissions = null;
			if(!result.hasError() && additionalFundsRequired){
				String servicePackageKey = ParametersManager.getReferenceKey(EntityType.ServicePackage, account.getServicePackageId());
				OperationResult<HashMap<String, IVariableSet>> variablesResult = ParametersManager.getVariables(account.getId(), EntityType.Account, new Integer[] {VariablesCategories.Permissions.getValue()}, new String[] {servicePackageKey});
				if(variablesResult.hasError()){
					result.setError(variablesResult);
				} else {
					permissions = variablesResult.getValue();
				}
			}
			
			if(!result.hasError() && additionalFundsRequired){
				IAccess makePaymentAccess = permissions.get(PermissionsKeys.MakePayment.getValue()).getActual();
				if(makePaymentAccess.getValue() == AccessValue.Denied){
					result.setError(ErrorCode.NotPermitted);
				}
			}
			//END Check Permission
			
			if(!result.hasError()){
				IResultSetCallback callback = new IResultSetCallback() {
					
					public void call(ResultSet reader, int generationId) throws Exception {
						while(reader.next()){
							if(0 == generationId){
								ICharge charge = new ChargeEntity(reader);
								result.setValue(charge);							
							}
						}
					}
				};
				
				ChargesDataAccess.insertCharge(request, callback);
				
				if(!result.hasError() && !result.hasValue()){
					result.setError(ErrorCode.NoResults);
				}
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "createCharge() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
		
	}
	
	public static OperationResult<ICharge> updateCharge(IChargeUpdateRequest request){
		
		final OperationResult<ICharge> result = new OperationResult<ICharge>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							ICharge charge = new ChargeEntity(reader);
							result.setValue(charge);							
						}
					}
				}
			};
			
			ChargesDataAccess.updateCharge(request, callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "updateCharge() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult deleteCharge(long chargeId, Long userId, Long accountId){
		BaseOperationResult result;
		try{
			if(ChargesDataAccess.deleteCharge(chargeId, userId, accountId)){
				result = BaseOperationResult.Ok;
			} else {
				logger.warn("deleteCharge() : ChargeId '%s' not deleted. Check condition.", chargeId);
				result = new BaseOperationResult(ErrorCode.NoResults);
			}
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "deleteCharge() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static void deleteCharges(long[] chargeIds, Long userId, Long accountId){
		
		for (int i = 0; i < chargeIds.length; i++) {
			long chargeId = chargeIds[i];
			deleteCharge(chargeId, userId, accountId);
		}
	}
	
	public static JSONArray getPostPayActions(Long chargeId){
		try {
			return ChargesDataAccess.getPostPayActions(chargeId, null, null);
		} catch (DAOException e) {
			throw new Error(e);
		}
	}

	public static OperationResult<ChargeStatus> payCharge(
			IChargePayRequest request, boolean fireEvent) {
		OperationResult<ChargeStatus> result;
		try{
			result = ChargesDataAccess.pay(request);
			if(!result.hasError()){
				if(fireEvent){
				//fire event
					ChargeStatusChangedEventArgs args = new ChargeStatusChangedEventArgs(request.getChargeId(), result.getValue().getValue(), request.getSourceId());
					EventsServiceClient.getInstance().fire(args);
				}
			} else {
				//TODO: charge partially paid
			}
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "payCharge() : Unexpected error occured.");
			result = new OperationResult<ChargeStatus>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static JSONArray getChargesByReferenceId(Long accountId,
			long referenceId, int referenceTypeId) {
		
		try {
			return ChargesDataAccess.getChargesByReferenceId(accountId, referenceId, referenceTypeId);
		} catch (DAOException e) {
			throw new Error(e);
		}
	}

	public static BaseOperationResult cancelOrder(long collectorId, int referenceTypeId, Long accountId, long userId) {
		BaseOperationResult result = new BaseOperationResult();
		try{
			JSONArray arr = getChargesByReferenceId(accountId, collectorId, referenceTypeId);
			for (int i = 0; i < arr.length(); i++) {
				var json = arr.getJSONObject(i);
				long chargeId = json.getLong("charge_id");
				ChargeStatus status = ChargeStatus.fromInt(json.getInt("status_id"));
				if(status == ChargeStatus.Unpaid){
					BaseOperationResult deleteResult = deleteCharge(chargeId, userId, accountId);
					if(deleteResult.hasError() && !result.hasError()){
						result.setError(deleteResult);
					}
				}
			}
		} catch (Throwable t){
			UUID errorId = logger.error(t, "cancelOrder : Unexpected error occured. collectorId:'%s', serviceId:'%s', accountId:'%s'", collectorId, referenceTypeId, accountId);
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult addChargesToBill(List<Long> chargesIds, long billId, BillType billType, long accountId, Long modifyUserId){
		BaseOperationResult result;
		try{
			ChargesDataAccess.addChargesToBill(chargesIds, modifyUserId, accountId, billId, billType);
			result = BaseOperationResult.Ok;
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "addChargesToBill() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult removeChargeFromBill(long chargeId, Long modifyUserId){
		BaseOperationResult result;
		try{
			ChargesDataAccess.removeChargeFromBill(chargeId, modifyUserId);
			result = BaseOperationResult.Ok;
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "removeChargeFromBill() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static Date generateDefaultExpirationDate() {
		return DateUtils.addDays(DateConverter.trim(Calendar.getInstance().getTime()), 2);
	}

	public static BaseOperationResult cancelCharge(ICancelChargeRequest request) {
		BaseOperationResult result;
		try{
			result = ChargesDataAccess.cancelCharge(request);
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "cancelCharge() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static List<ChargeModel> getCharges(int top, Long accountId, Long billId,
			Integer billTypeId, Integer statusId, List<Integer> statusIds,
			boolean includeExpired, Boolean invoiced, List<Long> chargesIds) {
		List<ChargeModel> list = getCharges(top, billId, billTypeId, accountId, statusId, includeExpired, invoiced);
		
		Stream<ChargeModel> stream = list.stream();
		
//		CDataFilterClause filterClause = new CDataFilterClause();
		if(null != chargesIds){
			stream = stream.filter(itm -> chargesIds.contains(itm.getId()));
			
//			CDataFilter chargeIdFilter = new EqualsFilter("charge_id", chargesIds.toArray());
//			filterClause.addFilter(chargeIdFilter);
		}
		
		if(null != statusIds){
			stream = stream.filter(itm -> statusIds.contains(itm.getStatus().getValue()));
			
//			CDataFilter statusIdFilter = new EqualsFilter("charge_status_id", statusIds.toArray());
//			filterClause.addFilter(statusIdFilter);
		}
		
		return stream.collect(Collectors.toList());
	}
}
