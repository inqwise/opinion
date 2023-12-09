package com.inqwise.opinion.opinion.entities.analizeResults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.entities.BaseEntity;
import com.inqwise.opinion.opinion.common.ControlType;
import com.inqwise.opinion.opinion.common.IControl;
import com.inqwise.opinion.opinion.common.OptionKind;
import com.inqwise.opinion.opinion.common.ParentType;
import com.inqwise.opinion.opinion.common.analizeResults.IAnalizeControl;
import com.inqwise.opinion.opinion.common.analizeResults.IAnalizeOption;
import com.inqwise.opinion.opinion.dao.Results;

public abstract class AnalizeControlEntity extends BaseEntity implements IAnalizeControl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ApplicationLog logger = ApplicationLog
			.getLogger(AnalizeControlEntity.class);
	
	private Long controlId;
	private Long parentId;
	private ParentType parentType;
	private ControlType controlType;
	private String content;
	private Integer countOfAnswers;
	private Integer countOfSkip;
	private boolean hidden;
	private String key;
	private boolean numerable;
	private double completionRate;
	
	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	private static IAnalizeControl createControlByType(ControlType controlType,
			ResultSet reader) throws Exception {
		AnalizeControlEntity returnValue;
		switch (controlType) {
		case FreeText:
			returnValue = new AnalizeFreeTextControlEntity();
			break;
		case OptionList:
			returnValue = new AnalizeOptionListControlEntity();
			break;
		case Matrix:
			returnValue = new AnalizeMatrixControlEntity();
			break;
		case Date:
			returnValue = new AnalizeDateTimeControlEntity();
			break;
		case MatrixQuestion:
			returnValue = new AnalizeMatrixSubControlEntity();
			break;
		case Scale:
			returnValue = new AnalizeScaleControlEntity();
			break;
		case Attribute:
			returnValue = new AnalizeAttributeControlEntity();
			break;
		default:
			throw new Exception(
					"createControlByType() : controlType not supported. Name: "
							+ controlType);
		}

		if (null != reader) {
			returnValue.fill(reader, controlType);
		}

		return returnValue;
	}

	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		setControlId(Long.valueOf(reader.getLong("control_id")));
		setControlType(controlType);
		setContent(reader.getString("content"));
		setParentId(ResultSetHelper.optLong(reader, "parent_id"));
		setParentType(ParentType.fromInt(reader.getInt("parent_type_id")));
		setCountOfAnswers(ResultSetHelper.optInt(reader, "count_answers", 0));
		setCountOfSkip(ResultSetHelper.optInt(reader, "count_skip", 0));
		setHidden(reader.getBoolean("is_hidden"));
		setKey(ResultSetHelper.optString(reader, "control_key"));
		setNumerable(reader.getBoolean("numerable"));
		
		long cntStarted = getCountOfAnswers() + getCountOfSkip();
		completionRate = (cntStarted > 0 ? Math.round((getCountOfAnswers() * 1d / cntStarted * 1d) * 100.0) : 0.0);
	}
	
	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.analizeResults.IAnalizeControl#toJson()
	 */
	@Override
	public JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException {
		JSONObject jo = new JSONObject()
		.put(IControl.JsonNames.CONTROL_ID, getControlId())
		.put(IControl.JsonNames.PARENT_ID, getParentId())
		.put(IControl.JsonNames.PARENT_TYPE_ID, getParentType().getValue())
		.put(IControl.JsonNames.CONTROL_TYPE_ID, getControlType().getValue())
		.put(IControl.JsonNames.COUNT_OF_ANSWERS, getCountOfAnswers())
		.put(IControl.JsonNames.COUNT_OF_SKIP, getCountOfSkip())
		.put(IControl.JsonNames.COMPLETION_RATE, getCompletionRate());
		if(isHidden()){
			jo.put(IControl.JsonNames.IS_HIDDEN, isHidden());
		}
		
		jo.put(IControl.JsonNames.CONTENT, getContent());
		jo.put(IControl.JsonNames.KEY, getKey());
		
		if(!isNumerable()){
			jo.put(IControl.JsonNames.INCALCULABLE, true);
		}
		
		return jo;
	}

	private double getCompletionRate() {
		return completionRate;
	}

	public void setControlId(Long controlId) {
		this.controlId = controlId;
	}

	public Long getControlId() {
		return controlId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentType(ParentType parentType) {
		this.parentType = parentType;
	}

	public ParentType getParentType() {
		return parentType;
	}

	public void setControlType(ControlType controlType) {
		this.controlType = controlType;
	}

	public ControlType getControlType() {
		return controlType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	public static OperationResult<List<IAnalizeControl>> getAnalizeControls(Long opinionId, Long accountId, Long translationId, String answererSessionId, Long collectorId, boolean includePartialAnswers, boolean includePartialStatistics, boolean includeAttributeControl){
		OperationResult<List<IAnalizeControl>> result;
		try {
			IDataFillable<IAnalizeControl> controlHandler = new IDataFillable<IAnalizeControl>() {
	
				@Override
				public IAnalizeControl fill(ResultSet reader) throws Exception {
					ControlType controlType = ControlType.fromInt(reader.getInt("control_type_id"));
					IAnalizeControl control = createControlByType(controlType, reader);
					return control;
				}
			};
			IDataFillable<IAnalizeOption> optionHandler = new IDataFillable<IAnalizeOption>() {
	
				@Override
				public IAnalizeOption fill(ResultSet reader) throws Exception {
					OptionKind optionKind = OptionKind.fromInt(reader.getInt("option_kind_id"));
					return AnalizeOptionEntity.createOptionByKind(optionKind, reader);
				}
			};		
		
			List<IAnalizeControl> list = Results.getControlsResults(opinionId, accountId, translationId, controlHandler, optionHandler, answererSessionId, collectorId, includePartialAnswers, includePartialStatistics, includeAttributeControl);
			result = new OperationResult<List<IAnalizeControl>>(list);
		} catch (Exception e) {
			UUID errorId = logger.error(e, "getAnalizeControls() : Unexpected eror occured");
			result = new OperationResult<List<IAnalizeControl>>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public Integer getCountOfAnswers() {
		return countOfAnswers;
	}

	public void setCountOfAnswers(Integer countOfAnswers) {
		this.countOfAnswers = countOfAnswers;
	}

	public Integer getCountOfSkip() {
		return countOfSkip;
	}

	public void setCountOfSkip(Integer countOfSkip) {
		this.countOfSkip = countOfSkip;
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
