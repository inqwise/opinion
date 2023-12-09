package com.inqwise.opinion.opinion.entities.controls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.opinion.common.ControlType;
import com.inqwise.opinion.opinion.common.IControl;
import com.inqwise.opinion.opinion.common.IControlRequest;
import com.inqwise.opinion.opinion.common.IModifyControlDetailsRequest;
import com.inqwise.opinion.opinion.common.IOption;
import com.inqwise.opinion.opinion.common.OptionsType;
import com.inqwise.opinion.opinion.common.analizeResults.IControlsContainer;
import com.inqwise.opinion.opinion.common.analizeResults.IOptionsContainer;
import com.inqwise.opinion.opinion.dao.ControlsDataAccess;
import com.inqwise.opinion.opinion.dao.Options;
import com.inqwise.opinion.opinion.entities.OptionEntity;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;

public class OptionListControlEntity extends AdvancedControlEntity implements IOptionsContainer<IOption>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<IOption> options;
	private static ApplicationLog logger = ApplicationLog.getLogger(OptionListControlEntity.class);
	
	protected OptionListControlEntity(ResultSet reader, ControlType controlType) throws Exception {
		super(reader, controlType);
	}
	
	public OptionListControlEntity(ResultSet reader) throws Exception {
		super(reader, ControlType.OptionList);
	}

	public OptionListControlEntity() {
	}

	@Override
	protected void fill(ResultSet reader, ControlType controlType) throws SQLException, Exception{
		super.fill(reader, controlType);
	}

	@Override
	public JSONObject toJson() throws JSONException {
		
		// fill base details
		JSONObject jsonControl = super.toJson();
		
		// add Options
		jsonControl.put(JsonNames.OPTIONS, new JSONObject().put(JsonNames.LIST, optionsToJson()));
		return jsonControl;
	}

	@Override
	protected BaseOperationResult validateModifyRequest(
			IModifyControlDetailsRequest request) {
		return  hasError() ? this : super.validateModifyRequest(request);
	}
	
	private JSONArray optionsToJson() throws JSONException {

		JSONArray jsonOptions = new JSONArray();
		if(null != options){
			for(IOption option : options){
				jsonOptions.put(((OptionEntity)option).toJson());
			}
		}
		return jsonOptions;
	}
	
	@Override
	public BaseOperationResult importRequest(IControlRequest request) {
		
		BaseOperationResult result = validate(null != request.getContent(), ErrorCode.ArgumentNull, "argument 'content' is mandatory.");
		
		if(!result.hasError()){
			result = super.importRequest(request);
		}
		
		return result;
	}

	@Override
	public OperationResult<Long> copyTo(final Long opinionId, final Long parentId,
			final Integer parentTypeId, final Long translationId, final Integer orderId,
			final String content, final String actionGuid, final long userId, final long accountId) {
		
		OperationResult<Long> result;
		final OptionListControlEntity c = this;
		
		IControlRequest request = new IControlRequest() {
			
			@Override
			public Long getTranslationId() {
				return null == translationId ? c.getTranslationId() : translationId;
			}
			
			@Override
			public Long getParentId() {
				return null == parentId ? c.getParentId() : parentId;
			}
			
			@Override
			public Integer getOrderId() {
				return null == orderId ? c.getOrderId() : orderId;
			}
			
			@Override
			public Long getOpinionId() {
				return null == opinionId ? c.getOpinionId() : opinionId;
			}
			
			@Override
			public String getNote() {
				return c.getNote();
			}
			
			@Override
			public Boolean getIsMandatory() {
				return c.getIsMandatory();
			}
			
			@Override
			public Integer getInputTypeId() {
				return c.getInputTypeId();
			}
			
			@Override
			public Integer getInputSizeTypeId() {
				return null;
			}
			
			@Override
			public Integer getParentTypeId() {
				return null == parentTypeId ? c.getParentType().getValue() : parentTypeId;
			}
			
			@Override
			public Integer getControlTypeId() {
				return c.getControlType().getValue();
			}
			
			@Override
			public String getContent() {
				return null == content ? c.getContent() : content;
			}
			
			@Override
			public String getComment() {
				return c.getComment();
			}
			
			@Override
			public Integer getArrangeId() {
				return c.getArrange().getValue();
			}
			
			@Override
			public Long getAccountId() {
				return accountId;
			}

			@Override
			public String getDayTitle() {
				return null;
			}

			@Override
			public String getHourTitle() {
				return null;
			}

			@Override
			public String getMinuteTitle() {
				return null;
			}

			@Override
			public String getMonthTitle() {
				return null;
			}

			@Override
			public String getTimezoneTitle() {
				return null;
			}

			@Override
			public String getYearTitle() {
				return null;
			}

			@Override
			public Integer getFromScale() {
				return null;
			}

			@Override
			public String getFromScaleTitle() {
				return null;
			}

			@Override
			public Integer getToScale() {
				return null;
			}

			@Override
			public String getToScaleTitle() {
				return null;
			}

			@Override
			public String getLink() {
				return c.getLink();
			}

			@Override
			public Integer getLinkTypeId() {
				return c.getLinkType().getValueOrNullWhenUndefined();
			}

			@Override
			public String getActionGuid() {
				return actionGuid;
			}

			@Override
			public long getUserId() {
				return userId;
			}
			
			@Override
			public boolean isHidden() {
				return c.isHidden();
			}
			
			@Override
			public String getKey() {
				return c.getKey();
			}

			@Override
			public boolean isNumerable() {
				return true;
			}
		};
		
		Long controlId = null;
		result = ControlEntity.createControl(request);
		if(!result.hasError()){
			controlId = result.getValue();
		}
		
		// copy Options
		if(!result.hasError() && null != options){
			for(IOption optionInterface : options){
				OptionEntity option = (OptionEntity) optionInterface;
				OperationResult<IOption> optionResult = option.copyTo(controlId, translationId, null, accountId, opinionId, userId);
				if(optionResult.hasError()){
					result = optionResult.toErrorResult();
					break;
				}
			}
		}
		
		return result;
	}

	@Override
	public void addOption(IOption option) {
		if(null == options){
			options = new ArrayList<IOption>();
		}
		options.add(option);
	}

	@Override
	public List<IOption> getOptions() {
		return options;
	}
	
	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = super.getExportJson();
		output.put(JsonNames.OPTIONS, getOptionsExportJson());
		return output;
	}
	
	private JSONArray getOptionsExportJson() throws JSONException {
		JSONArray jsonOptions = new JSONArray();
		if(null != options){
			for(IOption option : options){
				jsonOptions.put(option.getExportJson());
			}
		}
		return jsonOptions;
	}
}
