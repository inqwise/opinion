package com.inqwise.opinion.automation.actions;

import java.util.Date;

import com.inqwise.opinion.automation.common.ChargePostPayActionArgs;
import com.inqwise.opinion.automation.common.IEventAction;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.accounts.IAccountDetails;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ChargeReferenceType;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.ChargesManager;
import com.inqwise.opinion.library.managers.ServicePackagesManager;

public class UpgradePlanAction implements IEventAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2864173651490620512L;

	private static final ApplicationLog logger = ApplicationLog.getLogger(ActivateCintCollectorAction.class);
	
	private ChargePostPayActionArgs args;
	public UpgradePlanAction(ChargePostPayActionArgs args) {
		this.args = args;
	}
	
	@Override
	public IOperationResult run() {
		logger.info("UpgradePlanAction started");
		IOperationResult result = null;
		ICharge charge = null;
		
		OperationResult<ICharge> chargeResult = ChargesManager.getCharge(args.getChargeId(), null);
		if(chargeResult.hasError()){
			result = chargeResult;
		} else {
			charge = chargeResult.getValue();
		}
		
		IAccountDetails accountDetails = null;
		if(null == result){
			OperationResult<IAccountDetails> accountDetailsResult = AccountsManager.getAccountDetails(charge.getAccountId());
			if(accountDetailsResult.hasError()){
				result = accountDetailsResult;
			} else {
				accountDetails = accountDetailsResult.getValue();
			}
		}
		
		if(null == result && null != charge.getReferenceId() && charge.getReferenceType() == ChargeReferenceType.ServicePackage){
			int countOfMonths = args.getCountOf();
			result = updateAccountServicePackage(accountDetails, charge.getReferenceId(), countOfMonths);
		}
		
		return result;
		
		
	}

	private static IOperationResult updateAccountServicePackage(IAccountDetails accountDetails,
			Long referenceId, int countOfMonths) {
		
		IOperationResult result = null;
		int servicePackageId = Integer.parseInt(referenceId.toString());
		IServicePackage servicePackage = null;
		OperationResult<IServicePackage> servicePackageResult = ServicePackagesManager.getServicePackage(servicePackageId, null);
		if(servicePackageResult.hasError()){
			result = servicePackageResult;
		} else {
			servicePackage = servicePackageResult.getValue();
		}
		
		Date expiryDate = null;
		if(null == result){
			if(accountDetails.getServicePackageId() == servicePackageId){
				expiryDate = servicePackage.calculateExpiryDate(accountDetails.getServicePackageExpiryDate(), countOfMonths);
			} else {
				expiryDate = servicePackage.calculateExpiryDate(countOfMonths);
			}
			result = updateAccountDetails(accountDetails.getId(), servicePackageId, expiryDate, servicePackage.getMaxAccountUsers());
		}
		
		if(!result.hasError()){
			logger.info("UpgradePlanAction : Successfuly updated account #%s to servicePackage #%s until '%s'", accountDetails.getId(), servicePackageId, expiryDate);
		}
		
		return result;
	}

	private static IOperationResult updateAccountDetails(final long accountId,
			final int servicePackageId, final Date expiryDate, final Integer maxUsers) {
		
		return AccountsManager.updateServicePackage(accountId, servicePackageId, expiryDate, maxUsers);
	}

}
