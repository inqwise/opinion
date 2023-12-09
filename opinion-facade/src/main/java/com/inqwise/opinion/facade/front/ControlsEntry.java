package com.inqwise.opinion.opinion.facade.front;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.common.ControlType;
import com.inqwise.opinion.opinion.common.IControl;
import com.inqwise.opinion.opinion.common.IControl.JsonNames;
import com.inqwise.opinion.opinion.common.IControlRequest;
import com.inqwise.opinion.opinion.common.IControlType;
import com.inqwise.opinion.opinion.common.IModifyControlDetailsRequest;
import com.inqwise.opinion.opinion.common.IOptionRequest;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.IPostmasterObject;
import com.inqwise.opinion.opinion.common.OptionKind;
import com.inqwise.opinion.opinion.common.ParentType;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.sheet.ISheet;
import com.inqwise.opinion.opinion.common.sheet.ISheetRequest;
import com.inqwise.opinion.opinion.common.sheet.StartSheetIndexData;
import com.inqwise.opinion.opinion.http.HttpClientSession;
import com.inqwise.opinion.opinion.managers.ControlsManager;
import com.inqwise.opinion.opinion.managers.OpinionsManager;
import com.inqwise.opinion.opinion.managers.SheetsManager;

public class ControlsEntry extends Entry implements IPostmasterObject {
	static ApplicationLog logger = ApplicationLog.getLogger(ControlsEntry.class);
	public ControlsEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject createControl(JSONObject input) throws Exception {
		JSONObject output = null;

		Long userId = null;
		IAccount account = null;
		IOperationResult result = null;
		IControl control = null;
		
		OperationResult<Long> userIdResult = getUserId();
		if(userIdResult.hasError()){
			result = userIdResult;
		} else {
			userId = userIdResult.getValue();
		}
		
		Long controlId = null;
		Long opinionId = null;
		Long translationId = null;
		
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			opinionId = input.getLong(IControl.JsonNames.OPINION_ID);
			translationId = JSONHelper.optLong(input, IControl.JsonNames.TRANSLATION_ID,
					IOpinion.DEFAULT_TRANSLATION_ID);
			OperationResult<Long> createResult = createControl(input,
				account.getId(), null, userId, translationId, opinionId, null, null);
			if (createResult.hasError()) {
				result = createResult;
			} else {
				controlId = createResult.getValue();
			}
		}
		
		if(null == result){
			if (null == result) {
				result = investigateOptionsSettings(input, account.getId(), userId, controlId);
			}
	
			if (null == result) {
				result = investigateSubControlsSettings(input, account, userId, opinionId, controlId);
			}
		}	
		
		if(null == result){
			String opinionTitle = JSONHelper.optStringTrim(input, "opinionTitle");
			if(null != opinionTitle){
				BaseOperationResult opinionResult = OpinionsManager.changeOpinionName(opinionId, account.getId(), null, opinionTitle, null, null, translationId, userId);
				if(opinionResult.hasError()){
					result = opinionResult;
				}
			}
		}
		
		if(null == result){
			OperationResult<IControl> controlResult = ControlsManager.getControlById(controlId, account.getId());
			if(controlResult.hasError()){
				result = controlResult;
			} else {
				control = controlResult.getValue();
			}
		}
		
		if(null == result){
			output = control.toJson();
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	private OperationResult<Long> createControl(final JSONObject input,
			final long accountId, final String actionGuid, final long userId, final long translationId, final long opinionId, final Long parentId, final Integer parentTypeId) {

		IControlRequest request = new IControlRequest() {

			@Override
			public Long getAccountId() {
				return accountId;
			}

			@Override
			public Integer getArrangeId() {
				return JSONHelper.optInt(input, IControl.JsonNames.ARRANGE_ID);
			}

			@Override
			public String getContent() {
				return JSONHelper.optString(input, IControl.JsonNames.CONTENT, null,
						"");
			}

			@Override
			public Integer getControlTypeId() {
				return JSONHelper.optInt(input, IControl.JsonNames.CONTROL_TYPE_ID,
						null, 0);
			}

			@Override
			public Boolean getIsMandatory() {
				return JSONHelper.optBoolean(input,
						IControl.JsonNames.IS_MANDATORY);
			}

			@Override
			public String getNote() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.NOTE);
			}

			@Override
			public Long getOpinionId() {
				return opinionId;
			}

			@Override
			public Integer getOrderId() {
				return JSONHelper.optInt(input, IControl.JsonNames.ORDER_ID);
			}

			@Override
			public Long getTranslationId() {
				return translationId;
			}

			@Override
			public String getComment() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.COMMENT);
			}

			@Override
			public Integer getInputTypeId() {
				return JSONHelper.optInt(input,
						IControl.JsonNames.INPUT_TYPE_ID);
			}

			@Override
			public Integer getInputSizeTypeId() {
				return JSONHelper.optInt(input,
						JsonNames.INPUT_SIZE_TYPE_ID);
			}

			@Override
			public String getDayTitle() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.DAY_TITLE);
			}

			@Override
			public String getHourTitle() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.HOURS_TITLE);
			}

			@Override
			public String getMinuteTitle() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.MINUTES_TITLE);
			}

			@Override
			public String getMonthTitle() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.MONTH_TITLE);
			}

			@Override
			public Long getParentId() {
				return JSONHelper.optLong(input, IControl.JsonNames.PARENT_ID, parentId);
			}

			@Override
			public Integer getParentTypeId() {
				return JSONHelper.optInt(input, IControl.JsonNames.PARENT_TYPE_ID, parentTypeId);
			}

			@Override
			public String getTimezoneTitle() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.TIMEZONE_TITLE);
			}

			@Override
			public String getYearTitle() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.YEAR_TITLE);
			}

			@Override
			public Integer getFromScale() {
				return JSONHelper.optInt(input, IControl.JsonNames.FROM_SCALE);
			}

			@Override
			public String getFromScaleTitle() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.FROM_TITLE);
			}

			@Override
			public Integer getToScale() {
				return JSONHelper.optInt(input, IControl.JsonNames.TO_SCALE);
			}

			@Override
			public String getToScaleTitle() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.TO_TITLE);
			}

			@Override
			public String getLink() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.LINK);
			}

			@Override
			public Integer getLinkTypeId() {
				return JSONHelper.optInt(input,
						IControl.JsonNames.LINK_TYPE_ID, (null == getLink() ? null : 0));
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
				return getControlTypeId() == ControlType.Attribute.getValue() ? true : JSONHelper.optBoolean(input, IControl.JsonNames.IS_HIDDEN, false);
			}

			@Override
			public String getKey() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.KEY);
			}

			@Override
			public boolean isNumerable() {
				// Attribute and Label are incalculable 
				return !(getControlTypeId() == ControlType.Attribute.getValue() || getControlTypeId() == ControlType.Label.getValue());
			}
		};

		return ControlsManager.createControl(request);
	}
	
	private BaseOperationResult investigateSubControlsSettings(
			JSONObject input, IAccount account, long userId, long opinionId, Long parentControlId) throws Exception {

		BaseOperationResult result = null;
		JSONObject controlsSettingsJo = input.optJSONObject("subControls");
		if (null != controlsSettingsJo) {

			JSONArray controlsListJa = controlsSettingsJo.optJSONArray("list");
			if (null != controlsListJa) {
				for (int index = 0; index < controlsListJa.length()
						&& null == result; index++) {
					JSONObject controlActionJo = controlsListJa
							.getJSONObject(index);
					Status status = Status.valueOf(JSONHelper.optString(controlActionJo, "status", Status.added.toString()));
					
					Long translationId = JSONHelper.optLong(input, IControl.JsonNames.TRANSLATION_ID,
							IOpinion.DEFAULT_TRANSLATION_ID);
					
					switch (status) {
					case added:
						BaseOperationResult createResult = createControl(
								controlActionJo, account.getId(), null, userId, translationId, opinionId, parentControlId, ParentType.Control.getValue());
						if (createResult.hasError()) {
							result = createResult;
						}
						break;
					case updated:
						Long controlId = controlActionJo
								.getLong(IControl.JsonNames.CONTROL_ID);
						BaseOperationResult updateOptionResult = updateControlDetails(
								controlActionJo, account.getId(), controlId, userId);
						if (updateOptionResult.hasError()) {
							result = updateOptionResult;
						}
						break;
					case deleted:
						BaseOperationResult deleteResult = deleteControl(
								controlActionJo, account.getId(), userId);
						if (deleteResult.hasError()) {
							result = deleteResult;
						}
						break;
					default:
						UUID errorId = logger
								.error(
										"investigateSubControlsSettings() : Status '%s' of control not implemented.",
										controlActionJo.getString("status"));
						result = new BaseOperationResult(
								ErrorCode.NotImplemented, errorId,
								"Status '%s' of control not implemented.",
								controlActionJo.getString("status"));
						break;
					}
				}
			}

			if (null == result) {
				JSONArray reorderControlsIds = controlsSettingsJo
						.optJSONArray("reorder");
				if (null != reorderControlsIds) {
					if (reorderControlsIds.length() != 0) {
						BaseOperationResult reorderResult = reorderControls(
								reorderControlsIds, account.getId(), userId);
						if (reorderResult.hasError()) {
							result = reorderResult;
						}
					}
				}
			}
		}
		return result;
	}
	
	public JSONObject updateControlDetails(JSONObject input) throws Exception {
		JSONObject output = null;
		BaseOperationResult result = null;
		
		Long userId = null;
		OperationResult<Long> userIdResult = getUserId();
		if(userIdResult.hasError()){
			result = userIdResult;
		} else {
			userId = userIdResult.getValue();
		}
		
		IAccount account = null;
		Long opinionId = null;
		Long controlId = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			controlId = input.getLong(IControl.JsonNames.CONTROL_ID);
	
			OperationResult<Long> updateResult = updateControlDetails(input,
					account.getId(), controlId, userId);
			if (updateResult.hasError()) {
				result = updateResult;
			} else {
				opinionId = updateResult.getValue();
			}
	
			if (null == result) {
				result = investigateOptionsSettings(input, account.getId(), userId, null);
			}
	
			if (null == result) {
				result = investigateSubControlsSettings(input, account, userId, opinionId, null);
			}
		}
		
		if (null != result && result.hasError()) {
			output = result.toJson();
		} else {
			OperationResult<IControl> controlResult = ControlsManager
					.getControlById(controlId, account.getId());
			if (controlResult.hasError()) {
				output = controlResult.toJson();
			} else {
				output = new JSONObject();
				output.put("result", controlResult.getValue().toJson());
			}
		}
		
		return output;
	}
	
	public JSONObject controlCopyTo(JSONObject input) throws Exception {

		JSONObject output;
		BaseOperationResult result = null;
		Long controlId = JSONHelper.optLong(input, "controlId");
		List<Long> controlIdList = JSONHelper.toListOfLong(JSONHelper.optJsonArray(input, "list"));
		Long opinionId = JSONHelper.optLong(input, "opinionId");
		Long parentId = JSONHelper.optLong(input, "parentId");
		Integer parentTypeId = JSONHelper.optInt(input, "parentTypeId",
				ParentType.Sheet.getValue());
		Long translationId = JSONHelper.optLong(input, "translationId", 0L);
		Integer orderId = JSONHelper.optInt(input, "orderId");
		String content = JSONHelper.optString(input, "content", null, "");

		Long userId = null;
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("controlCopyTo : Not Signed in.");
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		if(null == result) {
			if(null == controlIdList){
				output = copySingleControl(controlId, opinionId, parentId,
					parentTypeId, translationId, orderId, content, userId);
			} else {
				JSONArray ja = new JSONArray();
				for (Long actualControlId : controlIdList) {
					ja.put(copySingleControl(actualControlId, opinionId, parentId, parentTypeId, translationId, orderId, content, userId));
					if(null != orderId) orderId++;
				}
				
				output = new JSONObject().put("list", ja);
			}
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	private JSONObject copySingleControl(Long controlId, Long opinionId, Long parentId,
			Integer parentTypeId, Long translationId, Integer orderId,
			String content, Long userId) throws IOException,
			ExecutionException, Exception, JSONException {
		BaseOperationResult result = null;
		JSONObject output;
		IControl control = null;
		Long copyControlId = null;
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			OperationResult<Long> copyResult = ControlsManager.copyTo(
					controlId, opinionId, parentId, parentTypeId, translationId,
					orderId, content, userId, account.getId());
			if(copyResult.hasError()){
				result = copyResult;
			} else {
				copyControlId = copyResult.getValue();
			}
		}
		
		if(null == result){
			OperationResult<IControl> controlResult = ControlsManager.getControlById(copyControlId, account.getId());
			if(controlResult.hasError()){
				result = controlResult;
			} else {
				control = controlResult.getValue();
			}
		}
		
		if(null == result){
			output = control.toJson();
		} else {
			output = result.toJson();
		}
		return output;
	}
	
	private BaseOperationResult createOption(final JSONObject input,
			final Long accountId, final long userId, Long parentControlId) {

		BaseOperationResult result = null;
		try {
			final Long controlId = JSONHelper.optLong(input, "controlId", parentControlId);
			final String text = input.getString(com.inqwise.opinion.opinion.common.IOption.JsonNames.TEXT);
			final String value = JSONHelper.optString(input,
					com.inqwise.opinion.opinion.common.IOption.JsonNames.VALUE, text, "");
			final Integer orderId = input.getInt("orderId"); // null - to the
			// end
			final Long translationId = JSONHelper.optLong(input,
					com.inqwise.opinion.opinion.common.IOption.JsonNames.TRANSLATION_ID, IOpinion.DEFAULT_TRANSLATION_ID);
			final String link = JSONHelper.optStringTrim(input, com.inqwise.opinion.opinion.common.IOption.JsonNames.LINK);
			final Integer linkTypeId = JSONHelper.optInt(input, com.inqwise.opinion.opinion.common.IOption.JsonNames.LINK_TYPE_ID, (null == link ? null : 0));
			

			IOptionRequest createRequest = new IOptionRequest() {

				@Override
				public Long getAccountId() {
					return accountId;
				}

				@Override
				public Long getControlId() {
					return controlId;
				}

				@Override
				public Integer getOrderId() {
					return orderId;
				}

				@Override
				public String getText() {
					return text;
				}

				@Override
				public Long getTranslationId() {
					return translationId;
				}

				@Override
				public String getValue() {
					return value;
				}

				@Override
				public String getAdditionalDetailsTitle() {
					return JSONHelper.optString(input,
							com.inqwise.opinion.opinion.common.IOption.JsonNames.ADDITIONAL_DETAILS_TITLE, null, "");
				}

				@Override
				public Boolean getIsEnableAdditionalDetails() {
					return JSONHelper.optBoolean(input,
							com.inqwise.opinion.opinion.common.IOption.JsonNames.IS_ENABLE_ADDITIONAL_DETAILS);
				}

				@Override
				public Integer getOptionKindId() {
					return JSONHelper.optInt(input,
							com.inqwise.opinion.opinion.common.IOption.JsonNames.OPTION_KIND_ID, OptionKind.Simple
									.getValue());
				}

				@Override
				public Long getOpinionId() {
					return JSONHelper.optLong(input, "opinionId");
				}

				@Override
				public long getUserId() {
					return userId;
				}

				@Override
				public String getLink() {
					return link;
				}

				@Override
				public Integer getLinkTypeId() {
					return linkTypeId;
				}
			};

			result = OpinionsManager.createOption(createRequest);
		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"createOption() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, t
					.toString());
		}

		return result;
	}

	private BaseOperationResult updateOption(JSONObject input, Long accountId, long userId) {
		BaseOperationResult result;

		try {
			Long optionId = input.getLong(com.inqwise.opinion.opinion.common.IOption.JsonNames.OPTION_ID);
			String text = input.getString(com.inqwise.opinion.opinion.common.IOption.JsonNames.TEXT);
			String value = JSONHelper.optString(input, com.inqwise.opinion.opinion.common.IOption.JsonNames.VALUE,
					null, "");
			Boolean isEnableAdditionalDetails = JSONHelper.optBoolean(input,
					com.inqwise.opinion.opinion.common.IOption.JsonNames.IS_ENABLE_ADDITIONAL_DETAILS);
			String additionalDetailsTitle = JSONHelper.optString(input,
					com.inqwise.opinion.opinion.common.IOption.JsonNames.ADDITIONAL_DETAILS_TITLE, null, "");
			Long translationId = JSONHelper.optLong(input,
					com.inqwise.opinion.opinion.common.IOption.JsonNames.TRANSLATION_ID, 0L);
			String link = JSONHelper.optStringTrim(input, com.inqwise.opinion.opinion.common.IOption.JsonNames.LINK);
			Integer linkTypeId = JSONHelper.optInt(input, com.inqwise.opinion.opinion.common.IOption.JsonNames.LINK_TYPE_ID, (null == link ? null : 0));
			
			result = OpinionsManager.updateOption(optionId, text, value,
					translationId, accountId, isEnableAdditionalDetails,
					additionalDetailsTitle, userId, link, linkTypeId);
		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"updateOption() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, t
					.toString());
		}

		return result;
	}

	public BaseOperationResult deleteOption(JSONObject input, Long accountId, Long userId) {
		BaseOperationResult result;

		try {
			Long optionId = input.getLong(com.inqwise.opinion.opinion.common.IOption.JsonNames.OPTION_ID);
			result = OpinionsManager.deleteOption(optionId, accountId, userId);
		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"deleteOption() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId, t
					.toString());
		}

		return result;
	}

	private BaseOperationResult reorderOptions(JSONArray optionIdsJA,
			Long accountId, Long userId) {
		BaseOperationResult reorderResult;
		try {
			reorderResult = OpinionsManager.orderOptions(optionIdsJA.join(","),
					accountId, userId);
		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"reorderOptions() : Unexpected error occured.");
			reorderResult = new BaseOperationResult(ErrorCode.GeneralError,
					errorId, t.toString());
		}

		return reorderResult;
	}

	public JSONObject reorderControls(JSONObject input) throws NullPointerException, ExecutionException, IOException {
		JSONObject output = null;
		IOperationResult result = validateSignIn();
		JSONArray controlIdsJA = input.getJSONArray("controlIds");
		Long userId = getUserId().getValue();
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result) {
			Long accountId = account.getId();
			
			BaseOperationResult reorderResult = reorderControls(controlIdsJA,
					accountId, userId);
			if (reorderResult.hasError()) {
				result = reorderResult;
			}
		}
		
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}

		return output;
	}

	private BaseOperationResult reorderControls(JSONArray controlIdsJA,
			Long accountId, Long userId) {
		return ControlsManager.orderControls(controlIdsJA
				.join(","), accountId, userId);
	}
	
	public JSONObject deleteControl(JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = null;
		IOperationResult result = null;
		Long userId = null;
		OperationResult<Long> userIdResult = getUserId();
		if(userIdResult.hasError()){
			result = userIdResult;
		} else {
			userId = userIdResult.getValue();
		}
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			Long accountId = account.getId();
			
			BaseOperationResult deleteResult = deleteControl(input, accountId, userId);
			if (deleteResult.hasError()) {
				result = deleteResult;
			}
		}
		
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	private BaseOperationResult deleteControl(JSONObject input, Long accountId, long userId)
			throws JSONException {
		Long controlId = input.getLong(IControl.JsonNames.CONTROL_ID);
		return ControlsManager.deleteControl(controlId, accountId, userId);
	}

	

	private OperationResult<Long> updateControlDetails(final JSONObject input,
			final Long accountId, final Long controlId, final long userId) throws JSONException {

		IModifyControlDetailsRequest request = new IModifyControlDetailsRequest() {

			@Override
			public String getYearTitle() {
				return JSONHelper.optString(input,
						IControl.JsonNames.YEAR_TITLE, null, "");
			}

			@Override
			public Long getTranslationId() {
				return JSONHelper.optLong(input, IControl.JsonNames.TRANSLATION_ID,
						0L);
			}

			@Override
			public String getTimezoneTitle() {
				return JSONHelper.optString(input,
						IControl.JsonNames.TIMEZONE_TITLE, null, "");
			}

			@Override
			public String getNote() {
				return JSONHelper.optString(input, IControl.JsonNames.NOTE,
						null, "");
			}

			@Override
			public String getMonthTitle() {
				return JSONHelper.optString(input,
						IControl.JsonNames.MONTH_TITLE, null, "");
			}

			@Override
			public String getMinuteTitle() {
				return JSONHelper.optString(input,
						IControl.JsonNames.MINUTES_TITLE, null, "");
			}

			@Override
			public Boolean getIsMandatory() {
				return JSONHelper.optBoolean(input,
						IControl.JsonNames.IS_MANDATORY);
			}

			@Override
			public Integer getInputTypeId() {
				return JSONHelper.optInt(input,
						IControl.JsonNames.INPUT_TYPE_ID);
			}

			@Override
			public Integer getInputSizeTypeId() {
				return JSONHelper.optInt(input,
						JsonNames.INPUT_SIZE_TYPE_ID);
			}

			@Override
			public String getHourTitle() {
				return JSONHelper.optString(input,
						IControl.JsonNames.HOURS_TITLE, null, "");
			}

			@Override
			public String getDayTitle() {
				return JSONHelper.optString(input,
						IControl.JsonNames.DAY_TITLE, null, "");
			}

			@Override
			public Long getControlId() {
				return controlId;
			}

			@Override
			public String getContent() {
				return JSONHelper.optString(input, IControl.JsonNames.CONTENT, null,
						"");
			}

			@Override
			public String getComment() {
				return JSONHelper.optString(input,
						JsonNames.COMMENT, null, "");
			}

			@Override
			public Long getAccountId() {
				return accountId;
			}

			@Override
			public Integer getFromScale() {
				return JSONHelper.optInt(input, JsonNames.FROM_SCALE);
			}

			@Override
			public String getFromScaleTitle() {
				return JSONHelper.optString(input,
						JsonNames.FROM_TITLE, null, "");
			}

			@Override
			public Integer getToScale() {
				return JSONHelper.optInt(input, JsonNames.TO_SCALE);
			}

			@Override
			public String getToScaleTitle() {
				return JSONHelper.optStringTrim(input, JsonNames.TO_TITLE);
			}

			@Override
			public String getLink() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.LINK);
			}

			@Override
			public Integer getLinkTypeId() {
				return JSONHelper.optInt(input,
						IControl.JsonNames.LINK_TYPE_ID, (null == getLink() ? null : 0));
			}

			@Override
			public long getUserId() {
				return userId;
			}

			@Override
			public String getKey() {
				return JSONHelper.optStringTrim(input, IControl.JsonNames.KEY);
			}
		};

		OperationResult<Long> updateControlResult = ControlsManager.updateControlDetails(request);
		
		if(!updateControlResult.hasError()){
			String opinionTitle = JSONHelper.optStringTrim(input, "opinionTitle");
			Long opinionId = updateControlResult.getValue();
			if(null != opinionTitle){
				BaseOperationResult opinionResult = OpinionsManager.changeOpinionName(opinionId, accountId, null, opinionTitle, null, null, request.getTranslationId(), userId);
				if(opinionResult.hasError()){
					updateControlResult = opinionResult.toErrorResult();
				}
			}
		}
		return updateControlResult;
	}

	private BaseOperationResult investigateOptionsSettings(JSONObject input,
			Long accountId, long userId, Long controlId) throws JSONException {

		BaseOperationResult result = null;
		JSONObject optionsSettingsJo = input.optJSONObject("options");
		if (null != optionsSettingsJo) {

			JSONArray optionsListJa = optionsSettingsJo.optJSONArray("list");
			if (null != optionsListJa) {
				for (int index = 0; index < optionsListJa.length()
						&& null == result; index++) {
					JSONObject optionActionJo = optionsListJa
							.getJSONObject(index);
					Status status = Status.valueOf(optionActionJo
							.getString("status"));
					switch (status) {
					case added:
						BaseOperationResult createResult = createOption(
								optionActionJo, accountId, userId, controlId);
						if (createResult.hasError()) {
							result = createResult;
						}
						break;
					case updated:
						BaseOperationResult updateOptionResult = updateOption(
								optionActionJo, accountId, userId);
						if (updateOptionResult.hasError()) {
							result = updateOptionResult;
						}
						break;
					case deleted:
						BaseOperationResult deleteResult = deleteOption(
								optionActionJo, accountId, userId);
						if (deleteResult.hasError()) {
							result = deleteResult;
						}
						break;
					default:
						UUID errorId = logger
								.error(
										"investigateOptionsSettings() : Status '%s' of option not implemented.",
										optionActionJo.getString("status"));
						result = new BaseOperationResult(
								ErrorCode.NotImplemented, errorId,
								"Status '%s' of option not implemented.",
								optionActionJo.getString("status"));
						break;
					}
				}
			}

			if (null == result) {
				JSONArray reorderOptionsIds = optionsSettingsJo
						.optJSONArray("reorder");
				if (null != reorderOptionsIds) {
					if (reorderOptionsIds.length() != 0) {
						BaseOperationResult reorderResult = reorderOptions(
								reorderOptionsIds, accountId, userId);
						if (reorderResult.hasError()) {
							result = reorderResult;
						}
					}
				}
			}
		}
		return result;
	}

	

	public JSONObject reorderControl(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();

		IOperationResult result = null;
		Long userId = null;
		
		OperationResult<Long> userIdResult = getUserId();
		if(userIdResult.hasError()){
			result = userIdResult;
		} else {
			userId = userIdResult.getValue();
		}

		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			
			Long controlId = input.getLong("controlId");
				
			Long parentId = JSONHelper.optLong(input, "sheetId"); // able to be
			// null when
			// not moved
			// to other
			// sheet
			Integer orderId = input.getInt("orderId"); // set null when required
			// to be the last item
			// in the sheet
			BaseOperationResult reorderResult = ControlsManager.reorderControl(
					controlId, account.getId(), parentId,
					ParentType.Sheet.getValue(), orderId, userId);
			if (reorderResult.hasError()) {
				result = reorderResult;
			}
		}
		
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getControls(JSONObject input) {
		JSONObject output = new JSONObject();
		BaseOperationResult result = null;
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		Long translationId = null;
		Long sheetId = null;
		Long opinionId = null;
		
		OperationResult<List<IControl>> getControlsResult = null;
		if (null == result) {
			sheetId = JSONHelper.optLong(input, "sheetId");
			opinionId = JSONHelper.optLong(input, "opinionId");
			translationId = JSONHelper.optLong(input, "translationId");
			
			if(null == sheetId && null == opinionId){
				result = new BaseOperationResult(ErrorCode.ArgumentMandatory, "sheetId or opinionId required");
			}
			
			if(null != sheetId && null != opinionId){
				result = new BaseOperationResult(ErrorCode.ArgumentMandatory, "only one sheetId or opinionId required");
			}
		} 
		
		if(null == result) {
			long parentId;
			ParentType parentType;
			if(null == sheetId){
				parentId = opinionId;
				parentType = ParentType.Opinion;
			} else {
				parentId = sheetId;
				parentType = ParentType.Sheet;
			}
			getControlsResult = ControlsManager.getControls(parentId,
					parentType, translationId, account.getId(), null);
			if (getControlsResult.hasError()) {
				result = getControlsResult;
			}
		}

		if (null == result) {
			List<IControl> controls = getControlsResult.getValue();
			JSONArray controlsJA = new JSONArray();
			for (IControl control : controls) {
				controlsJA.put(control.toJson());
			}
			output.put("list", controlsJA);
		} else {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject getControlTypes(JSONObject input) {
		JSONObject output = new JSONObject();

		try {
			OperationResult<List<IControlType>> controlTypesResult = OpinionsManager
					.getControlTypes();
			if (controlTypesResult.hasError()) {
				output = controlTypesResult.toJson();
			} else {
				JSONArray controlTypesJA = new JSONArray();
				for (IControlType controlType : controlTypesResult.getValue()) {
					controlTypesJA.put(new JSONObject().put("controlType",
							controlType.getId()).put("title",
							controlType.getTitle()).put("description",
							controlType.getDescription()));
				}
				output.put("list", controlTypesJA);
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t,
					"getControlTypes() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId);
			} catch (JSONException e) {
				logger.error(t,
						"getControlTypes() : Unable to put to Json object");
			}
		}

		return output;
	}
	
	public JSONObject getSheetsAndControls(JSONObject input) throws NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();
		IOperationResult result = validateSignIn();

		Long opinionId = input.getLong("opinionId");
		Integer pageNumber = JSONHelper.optInt(input, "pageNumber");
		Long translationId = JSONHelper.optLong(input, "translationId");
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		OperationResult<List<ISheet>> getSheetsResult = null;
		
		Dictionary<Long, JSONArray> sheetsSet = null;
		if (null == result) {
			
			getSheetsResult = SheetsManager.getSheets(opinionId,
					translationId, account.getId());
			if (getSheetsResult.hasError()) {
				result = getSheetsResult;
				output = getSheetsResult.toJson();
			} else {
				List<ISheet> sheets = getSheetsResult.getValue();
				JSONArray sheetsJA = new JSONArray();
				sheetsSet = new Hashtable<Long, JSONArray>();
				for (ISheet sheet : sheets) {
					
					JSONArray controlsJa = new JSONArray();
					JSONObject sheetJo = sheet.toJson();
					sheetJo.put("controls", controlsJa);
					sheetsSet.put(sheet.getId(), controlsJa);
					sheetsJA.put(sheetJo);
					
				}
				output.put("list", sheetsJA);
			}
		}

		OperationResult<List<IControl>> getControlsResult = null;
		if (null == result) {
			getControlsResult = ControlsManager.getControls(opinionId,
					pageNumber,translationId, account.getId(), null);
			if (getControlsResult.hasErrorExcept(ErrorCode.NoResults)) {
				result = getControlsResult;
			}
		}

		if (null == result) {
			if(getControlsResult.hasValue()){ // no results
				List<IControl> controls = getControlsResult.getValue();
				for (IControl control : controls) {
					JSONArray controlsJA = sheetsSet.get(control.getParentId());
					if(null == controlsJA) {
						throw new NullPointerException(String.format("getSheetsAndControls: No sheet #%s found for control #%s", control.getParentId(), control.getId()));
					}
					controlsJA.put(control.toJson());
				}
			}
		} else {
			output = result.toJson();
		}

		return output;
	}
	
	public JSONObject changeSheet(JSONObject input) throws JSONException, NullPointerException, ExecutionException, IOException {
		JSONObject output = new JSONObject();
		IOperationResult result = validateSignIn();
		
		Long sheetId = input.getLong("sheetId");
		Long translationId = JSONHelper.optLong(input, "translationId", IOpinion.DEFAULT_TRANSLATION_ID);
		String title = JSONHelper.optStringTrim(input, "title");
		String description = JSONHelper.optStringTrim(input, "description");

		Long userId = null;
		IAccount account = null;
		if(null == result) {
			userId = getUserId().getValue();
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if (null == result) {
			BaseOperationResult updateSheetResult = SheetsManager.updateSheet(sheetId, account.getId(), translationId, title, description, userId);
			if (updateSheetResult.hasError()) {
				result = updateSheetResult;
			}
		}

		if (null == result) {
			output = new JSONObject().put("sheetId", sheetId).put("title",
					title).put("description", description);
		} else {
			output = result.toJson();
		}

		return output;
	}
	
	public JSONObject createSheet(final JSONObject input) throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = null;
		IOperationResult result = null;
		Long userId = null;

		OperationResult<Long> userIdResult = getUserId();
		if(userIdResult.hasError()){
			result = userIdResult;
		} else {
			userId = userIdResult.getValue();
		}
		
		Long translationId = null;
		String title = null;
		Integer pageNumber = null;
		Long opinionId = null;
		String description = null;
		ISheet sheet = null;
		Long accountId = null;
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			accountId = account.getId();
			translationId = JSONHelper.optLong(input, "translationId", IOpinion.DEFAULT_TRANSLATION_ID);
			title = JSONHelper.optStringTrim(input, "title");
			pageNumber = JSONHelper.optInt(input, "pageNumber");
			opinionId = JSONHelper.optLong(input, "opinionId");
			description = JSONHelper.optStringTrim(input, "description");
			
			OperationResult<ISheet> createResult = createSheet(userId,
					accountId, translationId, title, pageNumber, opinionId,
					description);
			
			if(createResult.hasError()){
				result = createResult;
			} else {
				sheet  = createResult.getValue();
			}
		}
		 
		if (null == result) {
			output = new JSONObject();
			output.put("sheetId", sheet.getId())
			.put("pageNumber",sheet.getPageNumber())
			.put("title", JSONObject.NULL)
			.put("description", JSONObject.NULL);
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	public OperationResult<ISheet> createSheet(final Long userId, final Long accountId, final long translationId, final String title, final Integer pageNumber, final Long opinionId, final String description) {
		
		OperationResult<ISheet> result = null;
		OperationResult<Long> createResult = SheetsManager
				.createSheet(new ISheetRequest() {

			@Override
			public Long getTranslationId() {
				return translationId;
			}

			@Override
			public String getTitle() {
				return title;
			}

			@Override
			public Integer getPageNumber() {
				return pageNumber;
			}

			@Override
			public Long getOpinionId() {
				return opinionId;
			}

			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public String getActionGuid() {
				return null;
			}

			@Override
			public Long getAccountId() {
				return accountId;
			}

			@Override
			public long getUserId() {
				return userId;
			}
		});
		
		Long sheetId = null;
		if (createResult.hasError()) {
			result = createResult.toErrorResult();
		} else {
			sheetId = createResult.getValue();
		}

		ISheet sheet = null;
		if (null == result) {
			OperationResult<ISheet> sheetResult = SheetsManager.getSheet(
					sheetId, translationId, accountId);
			if (sheetResult.hasError()) {
				result = sheetResult;
			} else {
				sheet = sheetResult.getValue();
			}
		}
		
		if (null == result){
			result = new OperationResult<>(sheet);
		}
		
		return result;
	}

	public JSONObject getSheets(JSONObject input) throws NullPointerException, ExecutionException {
		JSONObject output = new JSONObject();
		IOperationResult result = validateSignIn();
		Long accountId = null;
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if (null == result) {
			accountId = account.getId();
			Long opinionId = input.getLong("opinionId");
			Long translationId = JSONHelper.optLong(input, "translationId");
			OperationResult<List<ISheet>> getSheetResult = SheetsManager
					.getSheets(opinionId, translationId, accountId);
			if (getSheetResult.hasError()) {
				output = getSheetResult.toJson();
			} else {
				List<ISheet> sheets = getSheetResult.getValue();
				JSONArray sheetsJA = new JSONArray();
				for (ISheet sheet : sheets) {
					sheetsJA.put(sheet.toJson());
				}
				output.put("list", sheetsJA);
			}
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	public JSONObject deleteSheet(JSONObject input) throws NullPointerException, IOException, ExecutionException {
		JSONObject output = null;

		IOperationResult result = validateSignIn();
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			Long sheetId = input.getLong("sheetId");
			Long userId = getUserId().getValue();
			Long accountId = account.getId();
			BaseOperationResult deleteResult = SheetsManager.deleteSheet(
					sheetId, accountId, userId);
			if (deleteResult.hasError()) {
				result = deleteResult;
			} 
		}
		
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	public JSONObject reorderSheets(JSONObject input) throws NullPointerException, ExecutionException, IOException {
		JSONObject output = null;
		IOperationResult result = validateSignIn();
		
		JSONArray sheetIdsJA = input.getJSONArray("sheetIds");
		IAccount account = null;
		Long userId = null;
		if(null == result) {
			userId = getUserId().getValue();
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if(null == result){
			BaseOperationResult reorderResult = SheetsManager.orderSheets(
					sheetIdsJA.join(","), account.getId(), userId);
			if (reorderResult.hasError()) {
				result = reorderResult;
			}
		}
		
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	public JSONObject getStartIndexOfSheet(JSONObject input) {
		JSONObject output = new JSONObject();

		Long sheetId = input.getLong("sheetId"); 
		// able to be null when not
		// moved to other sheet
		
		OperationResult<StartSheetIndexData> startIndexResult = SheetsManager
				.getStartIndexOfSheet(sheetId);
		if (startIndexResult.hasError()) {
			output = startIndexResult.toJson();
		} else {
			StartSheetIndexData startIndexData = startIndexResult.getValue();
			output.put("startIndex", startIndexData.getIndex());
			output.put("startNumerator", startIndexData.getNumerator());
		}
		
		return output;
	}
	
	public JSONObject sheetCopyTo(JSONObject input) throws Exception {

		JSONObject output;
		BaseOperationResult result = null;
		Long sheetId = JSONHelper.optLong(input, "sheetId");
		Long opinionId = JSONHelper.optLong(input, "opinionId");
		Long translationId = JSONHelper.optLong(input, "translationId", IOpinion.DEFAULT_TRANSLATION_ID);
		Integer pageNumber = JSONHelper.optInt(input, "pageNumber");
		String title = JSONHelper.optString(input, "title", null, "");
		String description = JSONHelper.optString(input, "description", null, "");

		Long userId = null;
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("sheetCopyTo : Not Signed in.");
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		ISheet sheet = null;
		Long copySheetId = null;
		if(null == result){
			OperationResult<Long> copyResult = SheetsManager.copyTo(sheetId, opinionId, translationId, pageNumber, title, description, userId, account.getId());
			if(copyResult.hasError()){
				result = copyResult;
			} else {
				copySheetId = copyResult.getValue();
			}
		}
		
		if(null == result){
			OperationResult<ISheet> sheetResult = SheetsManager.getSheet(copySheetId, translationId, account.getId());
			if(sheetResult.hasError()){
				result = sheetResult;
			} else {
				sheet = sheetResult.getValue();
			}
		}
		
		if(null == result){
			output = sheet.toJson();
		} else {
			output = result.toJson();
		}
		
		return output;
	}
}
