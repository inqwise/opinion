package com.inqwise.opinion.opinion.actions.opinions;

import java.util.HashMap;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.dao.SqlProcParameter;
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
import com.inqwise.opinion.library.managers.ParametersManager;
//import com.inqwise.opinion.opinion.managers.AccountsManager;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.opinion.common.opinions.FinishBehaviourType;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;


class ModifySurveyFlow extends ModifyOpinionFlow<IModifySurveyRequest> {

	private Integer finishBehaviourTypeId;
	
	@Override
	protected IModifySurveyRequest getRequest() {
		return (IModifySurveyRequest) super.getRequest();
	}
	
	@Override
	public String getStartMessage() {
		return getRequest().getStartMessage();
	}

	@Override
	public String getFinishMessage() {
		return getRequest().getFinishMessage();
	}

	@Override
	public String getRedirectUrl() {
		return getRequest().getRedirectUrl();
	}

	@Override
	public Boolean isShowProgressBar() {
		return getRequest().isShowProgressBar();
	}

	@Override
	public String getStartButtonTitle() {
		return getRequest().getStartButtonTitle();
	}

	@Override
	public String getFinishButtonTitle() {
		return getRequest().getFinishButtonTitle();
	}

	@Override
	public String getPreviousButtonTitle() {
		return getRequest().getPreviousButtonTitle();
	}

	@Override
	public String getNextButtonTitle() {
		return getRequest().getNextButtonTitle();
	}

	@Override
	public Boolean isUsePageNumbering() {
		return getRequest().isUsePageNumbering();
	}

	@Override
	public Boolean isUseQuestionNumbering() {
		return getRequest().isUseQuestionNumbering();
	}

	@Override
	public String GetLogoUrl() {
		return getRequest().getLogoUrl();
	}
	
	@Override
	public Integer getFinishBehaviourTypeId() {
		return finishBehaviourTypeId;
	}
	
	@Override
	protected BaseOperationResult Collect() {
		BaseOperationResult result = super.Collect();
		
		setFinishBehaviourTypeId(getRequest().getFinishBehaviourTypeId());
		boolean checkPermissions = getFinishBehaviourTypeId() != FinishBehaviourType.Void.getValue();
		
		// Start Check Permissions
		IAccountView account = null;
		if(null == result && checkPermissions){
			OperationResult<IAccountView> accountResult = AccountsManager.getAccount(getRequest().getAccountId());
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		HashMap<String, IVariableSet> permissions = null;
		if(null == result && checkPermissions){
			String servicePackageKey = ParametersManager.getReferenceKey(EntityType.ServicePackage, account.getServicePackageId());
			OperationResult<HashMap<String, IVariableSet>> variablesResult = ParametersManager.getVariables(account.getId(), EntityType.Account, new Integer[] {VariablesCategories.Permissions.getValue()}, new String[] {servicePackageKey});
			if(variablesResult.hasError()){
				result = variablesResult.toErrorResult();
			} else {
				permissions = variablesResult.getValue();
			}
		}
		
		if(null == result && checkPermissions){
			IAccess finishBehaviourAccess = permissions.get(PermissionsKeys.CustomFinishBehaviour.getValue()).getActual();
			if(finishBehaviourAccess.getValue() == AccessValue.Denied){
				//setFinishBehaviourTypeId(FinishBehaviourType.Void.getValue());
				result = new BaseOperationResult(ErrorCode.NotPermitted, IOpinion.JsonNames.FINISH_BEHAVIOUR_TYPE_ID);
			}
		}
		// End Check permissions
		
		return result;
	}

	public void setFinishBehaviourTypeId(Integer finishBehaviourTypeId) {
		this.finishBehaviourTypeId = finishBehaviourTypeId;
	}

	@Override
	public Integer getLabelPlacementId() {
		return getRequest().getLabelPlacementId();
	}

	@Override
	public Boolean isShowPaging() {
		return getRequest().isShowPaging();
	}

	@Override
	public BaseOperationResult process(IModifySurveyRequest request) {
		return null;
	}

	@Override
	public String getAlreadyCompletedMessage() {
		return getRequest().getAlreadyCompletedMessage();
	}
	
	@Override
	public String getRequiredQuestionErrorMessage() {
		return getRequest().getRequiredQuestionErrorMessage();
	}
	
}
