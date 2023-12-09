package com.inqwise.opinion.opinion.actions.opinions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.actions.ActionFlow;
import com.inqwise.opinion.opinion.dao.IUpdateOpinionParams;
import com.inqwise.opinion.opinion.dao.OpinionsDataAccess;

abstract class ModifyOpinionFlow<TRequest extends IModifyOpinionRequest> extends ActionFlow<TRequest, BaseOperationResult> implements IUpdateOpinionParams {

	protected static ApplicationLog logger = ApplicationLog.getLogger(ModifyOpinionFlow.class);
	
	private IModifyOpinionRequest request;
	private BaseOperationResult updateOpinionResult;
	
	protected IModifyOpinionRequest getRequest() {
		return request;
	}

	@Override
	public BaseOperationResult process(IModifyOpinionRequest request) {
		this.request = request;
		
		BaseOperationResult result = null;
		
		if(null == result){
			try {
				result = Collect();
				if(null == result){
					OpinionsDataAccess.updateOpinion(this); // callback: updateOpinionCompleted
				}
			} catch (DAOException ex) {
				UUID errorId = logger.error(ex, "ModifyOpinionFlow : unexpected error occured");
				result = new OperationResult<Long>(ErrorCode.GeneralError, errorId);
			}
		}
		
		if(null == result){
			result = updateOpinionResult;
		}
		
		return result;
	}	
	
	protected BaseOperationResult Collect() {
		return null;
	}

	@Override
	public Long getAccountId() {
		return getRequest().getAccountId();
	}
	
	@Override
	public boolean isHighlightRequiredQuestions() {
		return getRequest().isHighlightRequiredQuestions();
	}
	
	@Override
	public long getOpinionId() {
		return getRequest().getOpinionId();
	}
	
	@Override
	public Long getTranslationId() {
		return getRequest().getTranslationId();
	}
	
	@Override
	public long getUserId() {
		return getRequest().getUserId();
	}
	
	@Override
	public boolean isRtl() {
		return getRequest().isRtl();
	}
	
	@Override
	public Boolean isHidePoweredBy() {
		return getRequest().isHidePoweredBy();
	}
	
	@Override
	public void updateOpinionCompleted(ResultSet reader) throws SQLException {
		if (reader.next()) {
			// UUID errorId;
			int errorCode = (null == reader.getObject("error_code")) ? 0
					: reader.getInt("error_code");

			switch (errorCode) {
			case 0:
				updateOpinionResult = new BaseOperationResult(ErrorCode.NoError);
				break;
			case 4: // no have permissions
				updateOpinionResult = new BaseOperationResult(ErrorCode.NotPermitted);
				break;
			default: // Unsupported Error
				throw new UnsupportedOperationException(
						"updateOpinionCompleted() : errorCode not supported. Value: "
								+ errorCode);
			}

		} else {
			updateOpinionResult = new OperationResult<String>(ErrorCode.NoResults);
		}
	}
}
