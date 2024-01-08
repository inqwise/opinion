package com.inqwise.opinion.managers;

import java.util.List;

import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.common.IControl;
import com.inqwise.opinion.common.IControlRequest;
import com.inqwise.opinion.common.IModifyControlDetailsRequest;
import com.inqwise.opinion.common.ParentType;
import com.inqwise.opinion.entities.controls.ControlEntity;

public final class ControlsManager {
	private static ApplicationLog logger = ApplicationLog.getLogger(ControlsManager.class);
	
	public static OperationResult<Long> copyTo(Long controlId, Long opinionId, Long parentId, Integer parentTypeId,
			Long translationId, Integer orderId, String content, long userId, long accountId) throws Exception{
		
		OperationResult<Long> result = null;
		OperationResult<IControl> getControlResult = ControlEntity.getControlById(controlId, accountId);
		if(getControlResult.hasError()){
			result = getControlResult.toErrorResult();
		}
		
		if (null == result){
			ControlEntity control;
			control = (ControlEntity)getControlResult.getValue();
			result = control.copyTo(opinionId, parentId, parentTypeId,
									translationId, orderId, content, null , userId, accountId);
		}
		
		return result;
	}

	public static OperationResult<IControl> getControlById(Long controlId, Long accountId) {
		return ControlEntity.getControlById(controlId, accountId);
	}

	public static BaseOperationResult orderControls(String controlIds, Long accountId, long userId){
		
		return ControlEntity.order(controlIds, accountId, userId);
	}

	public static OperationResult<List<IControl>> getControls(Long opinionId, Integer pageNumber, Long translationId, Long accountId, String answerSessionId){
		return ControlEntity.getControls(null, null, opinionId, pageNumber, translationId, accountId, answerSessionId);
	}

	public static BaseOperationResult deleteControl(Long controlId,Long accountId, long userId) {
		return ControlEntity.delete(controlId, accountId, userId);
	}

	public static OperationResult<Long> updateControlDetails(IModifyControlDetailsRequest request){
		return ControlEntity.updateDetails(request);
	}

	public static BaseOperationResult reorderControl(Long controlId, Long accountId, Long parentId, Integer parentTypeId, Integer orderId, long userId){
		return ControlEntity.reorder(controlId, accountId, parentId, parentTypeId, orderId, userId);
	}

	public static OperationResult<Long> createControl(IControlRequest request) {
		return ControlEntity.createControl(request);
	}

	public static OperationResult<List<IControl>> getControls(Long parentId, ParentType parentType, Long translationId, Long accountId, String answerSessionId){
		return ControlEntity.getControls(parentId, parentType.getValue(), null, null, translationId, accountId, answerSessionId);
	}
}
