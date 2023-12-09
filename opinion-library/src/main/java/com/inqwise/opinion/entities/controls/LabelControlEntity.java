package com.inqwise.opinion.opinion.entities.controls;

import java.sql.ResultSet;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.common.ControlType;
import com.inqwise.opinion.opinion.common.IControlRequest;
import com.inqwise.opinion.opinion.common.IModifyControlDetailsRequest;

public class LabelControlEntity extends AdvancedControlEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LabelControlEntity(ResultSet reader) throws Exception {
		super(reader, ControlType.Label);
	}
	
	public LabelControlEntity() {
	}

	public LabelControlEntity(ResultSet reader, ControlType controlType)
			throws Exception {
		super(reader, controlType);
	}

	@Override
	public OperationResult<Long> copyTo(final Long opinionId, final Long parentId,
			final Integer parentTypeId, final Long translationId, final Integer orderId,
			final String content, final String actionGuid, final long userId, final long accountId) {
		final LabelControlEntity c = this;
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
				return null;
			}
			
			@Override
			public Integer getInputTypeId() {
				return null;
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
				return null;
			}
			
			@Override
			public Integer getArrangeId() {
				return null;
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
				return null;
			}

			@Override
			public Integer getLinkTypeId() {
				return null;
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
				return null;
			}

			@Override
			public boolean isNumerable() {
				return false;
			}
		};
		return createControl(request);
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jo = getBaseJson()
		.put(JsonNames.NOTE, getNote());
		return jo;
	}
	
	@Override
	protected BaseOperationResult validateModifyRequest(
			IModifyControlDetailsRequest request) {
		
		validate(null != request.getAccountId(), ErrorCode.ArgumentNull, "`accountId` is manatory");
		validate(null != request.getControlId(), ErrorCode.ArgumentNull, "`controlId` is mandatory");
		
		return this;
	}
}
