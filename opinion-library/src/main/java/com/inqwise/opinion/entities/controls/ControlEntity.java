package com.inqwise.opinion.entities.controls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.entities.BaseEntity;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.common.ControlType;
import com.inqwise.opinion.common.IControl;
import com.inqwise.opinion.common.IControlRequest;
import com.inqwise.opinion.common.ICreateResult;
import com.inqwise.opinion.common.IModifyControlDetailsRequest;
import com.inqwise.opinion.common.IOption;
import com.inqwise.opinion.common.IOptionRequest;
import com.inqwise.opinion.common.ParentType;
import com.inqwise.opinion.common.SurveyStatistics;
import com.inqwise.opinion.common.servicePackage.IServicePackageSettings;
import com.inqwise.opinion.dao.ControlsDataAccess;
import com.inqwise.opinion.entities.OptionEntity;
import com.inqwise.opinion.entities.ServicePackageSettingsEntity;
import com.inqwise.opinion.entities.SurveyEntity;

public abstract class ControlEntity extends BaseEntity implements IControl{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5172840615935494154L;
	
	private static ApplicationLog logger = ApplicationLog.getLogger(ControlEntity.class);
	
	
	public static OperationResult<Long> createControl(IControlRequest request){
		OperationResult<Long> result = null;
		
		ControlEntity control = null;
		
		try{
			control = createControlByType(ControlType.fromInt(request.getControlTypeId()), null);
		} catch (Exception e){
			UUID errorId = logger.error(e, "createControl() : Unexpected error occured");
			result = new OperationResult<Long>(ErrorCode.GeneralError, errorId, e.getMessage());
		}
		
		
		// Validations
		//
		// Basic validation
		if(null == result){
			BaseOperationResult importResult = control.importRequest(request);
			if(importResult.hasError()){
				result = importResult.toErrorResult();
			}
		}
		
		// Get survey statistics
		SurveyStatistics surveyStatistics = null;
		if(null == result){
			OperationResult<SurveyStatistics> surveyStatisticsResult = SurveyEntity.getSurveyShortStatistics(request.getOpinionId());
			if(surveyStatisticsResult.hasError()){
				result = surveyStatisticsResult.toErrorResult();
			} else {
				try {
					surveyStatistics = surveyStatisticsResult.getValue();
				} catch (Exception e) {
					UUID errorId = logger.error(e, "createControl() : Failed to get value from surveyStatisticsResult");
					result = new OperationResult<Long>(ErrorCode.GeneralError, errorId, e.getMessage());
				}
			}
		}
		
		IAccountView account = null;
		if (null == result){
			OperationResult<IAccountView> accountResult = AccountsManager.getAccount(request.getAccountId());
			if(accountResult.hasError()){
				result = accountResult.toErrorResult();
			} else {
				account = accountResult.getValue();
			}
		}
		
		// Get settings
		IServicePackageSettings settings = null;
		if (null == result) {
			OperationResult<IServicePackageSettings> settingsResult = ServicePackageSettingsEntity
					.getServicePackageSettings(account.getServicePackageId());
			if(settingsResult.hasError()){
				result = settingsResult.toErrorResult();
			} else {
				try {
					settings = settingsResult.getValue();
				} catch (Exception e) {
					UUID errorId = logger.error(e, "createControl() : Failed to get  value from getServicePackageType");
					result = new OperationResult<Long>(ErrorCode.GeneralError, errorId);
				}
			}
		}
		
		// Check MaxControls per opinion limit
		if(null == result && null != settings.getMaxControlsPerOpinion()){
			if(surveyStatistics.getCountOfControls() >= settings.getMaxControlsPerOpinion()){
				logger.info("User reach his Max controls per opinion limit. accountType: '%s', maxControls: '%s', opinionId: '%s'",account.getServicePackageId() , surveyStatistics.getCountOfControls(), request.getOpinionId());
				result = new OperationResult<Long>(ErrorCode.MaxControlsReached);
			}
		}
		//
		// End Validations
		
		Long controlId = null;
		if(null == result){
			OperationResult<ICreateResult> setControlResult;
			try {
				setControlResult = ControlsDataAccess.setControl(request);
				if(setControlResult.hasError()){
					result = setControlResult.toErrorResult();
				} else {
					controlId = setControlResult.getValue().getId();
					control.setId(controlId);
					control.setOrderId(setControlResult.getValue().getOrderId());
					
					result = new OperationResult<Long>(controlId);
				}
			
			} catch (Exception e) {
				UUID errorId = logger.error(e, "createControl() : Unexpected error occured");
				result = new OperationResult<Long>(ErrorCode.GeneralError, errorId, e.getMessage());
			}
		}
		
		return result;
	}
	private static ControlEntity createControlByType(ControlType controlType, ResultSet reader) throws Exception{
		ControlEntity returnValue;
		switch(controlType){
			case FreeText:
				returnValue = new FreeTextControlEntity();
				break;
			case OptionList:
				returnValue = new OptionListControlEntity();
				break;
			case Matrix:
				returnValue = new MatrixControlEntity();
				break;
			case Date:
				returnValue = new DateTimeControlEntity();
				break;
			case MatrixQuestion:
				returnValue = new MatrixSubControlEntity();
				break;
			case Scale:
				returnValue = new ScaleControlEntity();
				break;
			case Attribute:
				returnValue = new AttributeControlEntity();
				break;
			case Label:
				returnValue = new LabelControlEntity();
				break;
			default:
				throw new Exception("createControlByType() : controlType not supported. Name: " + controlType);
		}
		
		if(null != reader){
			returnValue.fill(reader, controlType);
		}
		
		return returnValue;
	}
	public static BaseOperationResult delete(Long id, Long accountId, long userId) {
		
		try {
			return ControlsDataAccess.deleteControl(id, accountId, userId);
		} catch (Exception e) {
			UUID errorId = logger.error(e, "delete() : Unexpected error occured");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, e.toString());
		}
	}
	
	/*
	public static OperationResult<IControl> getControlById(Long controlId, Long accountId){
		OperationResult<IControl> result;
		try{
			IDataFillable<IControl> data = new IDataFillable<IControl>()
			{
				public ControlEntity fill(ResultSet reader) throws Exception
				{
					ControlType controlType = ControlType.fromInt(reader.getInt("control_type_id"));
					return createControlByType(controlType, reader);
				}
			};
			
			result = Controls.getControl(controlId, data, accountId, null);
			
		}catch(Exception ex){
			UUID errorId = logger.error(ex, "getControlById() : Unexpected error occured.");
			result = new OperationResult<IControl>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	*/
	
	public static OperationResult<IControl> getControlById(Long controlId, Long accountId){
		OperationResult<IControl> result;
		try {
			IDataFillable<IControl> controlHandler = new IDataFillable<IControl>() {
	
				@Override
				public IControl fill(ResultSet reader) throws Exception {
					ControlType controlType = ControlType.fromInt(reader.getInt("control_type_id"));
					IControl control = createControlByType(controlType, reader);
					return control;
				}
			};
			IDataFillable<IOption> optionHandler = new IDataFillable<IOption>() {
	
				@Override
				public IOption fill(ResultSet reader) throws Exception {
					return new OptionEntity(reader);
				}
			};		
		
			List<IControl> list = ControlsDataAccess.getControls(controlId, null, null, null, null, null, accountId, null, controlHandler, optionHandler);
			if(list.size() == 1){
				result = new OperationResult<IControl>(list.get(0));
			} else {
				result = new OperationResult<IControl>(ErrorCode.NoResults);
			}
			
		} catch (Exception e) {
			UUID errorId = logger.error(e, "getAnalizeControls() : Unexpected eror occured");
			result = new OperationResult<IControl>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<List<IControl>> getControls(Long parentId, Integer parentTypeId, Long opinionId, Integer pageNumber, Long translationId, Long accountId, String answerSessionId){
		OperationResult<List<IControl>> result;
		try {
			IDataFillable<IControl> controlHandler = new IDataFillable<IControl>() {
	
				@Override
				public IControl fill(ResultSet reader) throws Exception {
					ControlType controlType = ControlType.fromInt(reader.getInt("control_type_id"));
					IControl control = createControlByType(controlType, reader);
					return control;
				}
			};
			IDataFillable<IOption> optionHandler = new IDataFillable<IOption>() {
	
				@Override
				public IOption fill(ResultSet reader) throws Exception {
					return new OptionEntity(reader);
				}
			};		
		
			List<IControl> list = ControlsDataAccess.getControls(null, parentId, parentTypeId, opinionId, pageNumber, translationId, accountId, answerSessionId, controlHandler, optionHandler);
			if(list.size() > 0){
				result = new OperationResult<List<IControl>>(list);
			} else {
				result = new OperationResult<List<IControl>>(ErrorCode.NoResults);
			}
		} catch (Exception e) {
			UUID errorId = logger.error(e, "getAnalizeControls() : Unexpected eror occured");
			result = new OperationResult<List<IControl>>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult order(String optionIds, Long accountId, long userId) {
		
		BaseOperationResult result;
		try {
			result = ControlsDataAccess.orderControls(optionIds, accountId, userId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "order() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, e.toString());
		}
		
		return result;
	}
	public static BaseOperationResult reorder(Long controlId, Long accountId,
												Long parentId, Integer parentTypeId, Integer orderId, long userId) {
		BaseOperationResult result;
		try {
			result = ControlsDataAccess.reorderControl(controlId, accountId,  parentId, parentTypeId, orderId, userId);
		} catch (DAOException e) {
			UUID errorId = logger.error(e, "reorder() : Unexpected error occured");
			return new BaseOperationResult(ErrorCode.GeneralError, errorId, e.toString());
		}
		return result;
	}
	public static OperationResult<Long> updateDetails(IModifyControlDetailsRequest request){
		OperationResult<Long> result = null;
		try {
			
			ControlEntity control = null;
			OperationResult<IControl> controlResult = getControlById(request.getControlId(), request.getAccountId());
			if(controlResult.hasError()){
				result = controlResult.toErrorResult();
			} else {
				control = (ControlEntity) controlResult.getValue();
			}
			
			if(null == result){
				BaseOperationResult updateResult = control.update(request);
				if(updateResult.hasError()){
					result = updateResult.toErrorResult();
				} else {
					result = new OperationResult<Long>(control.opinionId);
				}
			}
			
			
		} catch (Exception e) {
			UUID errorId = logger.error(e, "updateDetails() : Unexpected error occured.");
			result = new OperationResult<Long>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	private Long id;

	private Long opinionId;
	
	private ControlType controlType;
	
	private Long translationId;
	
	private String content;
	
	private Integer orderId;

	private Long parentId;

	private ParentType parentType;
	
	private boolean hidden;

	private String key;
	
	private boolean numerable;
	
	public ControlEntity() {}

	public ControlEntity(ResultSet reader, ControlType controlType) throws Exception {
		fill(reader, controlType);
	}

	public abstract OperationResult<Long> copyTo(Long opinionId, Long parentId, Integer parentTypeId,
														Long translationId, Integer orderId, String content,
														String actionGuid, long userId, long accountId);

	public BaseOperationResult createOptions(
			List<IOptionRequest> requests) {
		
		return OptionEntity.createOptions(requests);
	}

	public BaseOperationResult delete(Long accountId, long userId) {
		return delete(getId(), accountId, userId);
	}

	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		setId(Long.valueOf(reader.getLong("control_id")));
		setOpinionId(Long.valueOf(reader.getLong("opinion_id")));
		setControlType(controlType);
		
		setOrderId(Integer.valueOf(reader.getInt("order_id")));
		setTranslationId(Long.valueOf(reader.getLong("translation_id")));
		setContent(reader.getString("content"));
		setParentId(ResultSetHelper.optLong(reader, "parent_id"));
		setParentType(ParentType.fromInt(reader.getInt("parent_type_id")));
		setHidden(reader.getBoolean("is_hidden"));
		setKey(ResultSetHelper.optString(reader, "control_key"));
		setNumerable(reader.getBoolean("numerable"));
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public ControlType getControlType() {
		return controlType;
	}

	

	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public Long getOpinionId() {
		return opinionId;
	}

	public Integer getOrderId() {
		return orderId;
	}
	
	@Override
	public Long getParentId() {
		return parentId;
	}
	
	@Override
	public ParentType getParentType() {
		return parentType;
	}
	
	@Override
	public Long getTranslationId() {
		return translationId;
	}

	public BaseOperationResult importRequest(IControlRequest request) {
				
		BaseOperationResult result = BaseOperationResult.Ok;
		
		// Validate
		//
		// content
		//result = validate(null != request.getContent(), ErrorCode.ArgumentNull, "argument 'content' is mandatory.");
		if(!result.hasError()){
			setContent(request.getContent());
		}
		
		if(!result.hasError()){
			// parentId
			result = validate(null != request.getParentId(), ErrorCode.ArgumentNull, "argument 'parentId' is mandatory.");
			if(!result.hasError()){
				setParentId(request.getParentId());
			}
		}
		
		if(!result.hasError()){
			// parentTypeId
			result = validate(null != request.getParentTypeId(), ErrorCode.ArgumentNull, "argument 'parentTypeId' is mandatory.");
			if(!result.hasError()){
				setParentType(ParentType.fromInt(request.getParentTypeId()));
			}
		}
		
		if(!result.hasError()){
			// opinionId
			result = validate(null != request.getOpinionId(), ErrorCode.ArgumentNull, "argument 'opinionId' is mandatory.");
			if(!result.hasError()){
				setOpinionId(request.getOpinionId());
			}
		}
		
		if(!result.hasError()){
			// controlType
			result = validate(null != request.getControlTypeId(), ErrorCode.ArgumentNull, "argument 'controlType' is mandatory.");
			if(!result.hasError()){
				setControlType(ControlType.fromInt(request.getControlTypeId()));
			}
		}
		
		if(!result.hasError()){
			setTranslationId(request.getTranslationId());
			setHidden(request.isHidden());
		}
		
		
		
		return result;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public void setControlType(ControlType controlType) {
		this.controlType = controlType;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setOpinionId(Long opinionId) {
		this.opinionId = opinionId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	
	public void setParentType(ParentType parentType) {
		this.parentType = parentType;
	}
	
	public void setTranslationId(Long translationId) {
		this.translationId = translationId;
	}

	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jo = new JSONObject()
		.put(IControl.JsonNames.CONTROL_ID, getId())
		.put(IControl.JsonNames.PARENT_ID, getParentId())
		.put(IControl.JsonNames.PARENT_TYPE_ID, getParentType().getValue())
		.put(IControl.JsonNames.CONTROL_TYPE_ID, getControlType().getValue())
		.put(IControl.JsonNames.CONTENT, getContent());
		
		if(null != getTransactionId() && getTransactionId() != 0){
			jo.put(IControl.JsonNames.TRANSLATION_ID, getTranslationId());
		}
		
		if(isHidden()){
			jo.put(IControl.JsonNames.IS_HIDDEN, isHidden());
		}
		
		jo.put(IControl.JsonNames.KEY, getKey());
		if(!isNumerable()){
			jo.put(IControl.JsonNames.INCALCULABLE, true);
		}
		return jo;
	}

	private BaseOperationResult update(
			IModifyControlDetailsRequest request) throws DAOException {
		BaseOperationResult result = validateModifyRequest(request);
		if(!result.hasError()){
			result = ControlsDataAccess.updateControlDetails(request);
		}
		
		return result;
	}
	
	protected BaseOperationResult validateModifyRequest(
			IModifyControlDetailsRequest request) {
		
		validate(null != request.getAccountId(), ErrorCode.ArgumentNull, "`accountId` is manatory");
		validate(null != request.getControlId(), ErrorCode.ArgumentNull, "`controlId` is mandatory");
		validate(null != request.getContent(), ErrorCode.ArgumentNull, "`content` is mandatory");
		return this;
	}
	
	@Override
	public JSONObject toJson(boolean withAnswer) throws JSONException {
		return toJson();
	}
	
	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = new JSONObject();
		
		output.put(IControl.JsonNames.CONTROL_TYPE_ID, getControlType().getValue());
		output.put(IControl.JsonNames.CONTENT, getContent());
		output.put(IControl.JsonNames.KEY, getKey());
		if(isHidden()){
			output.put(IControl.JsonNames.IS_HIDDEN, isHidden());
		}
		
		return output;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isNumerable() {
		return numerable;
	}
	public void setNumerable(boolean numerable) {
		this.numerable = numerable;
	}
}
