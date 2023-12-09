package com.inqwise.opinion.opinion.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.opinion.actions.opinions.ICreatePollRequest;
import com.inqwise.opinion.opinion.actions.opinions.IModifyPollRequest;
import com.inqwise.opinion.opinion.actions.opinions.IModifySurveyRequest;
import com.inqwise.opinion.opinion.actions.opinions.OpinionsActionsFactory;
import com.inqwise.opinion.opinion.common.IControl;
import com.inqwise.opinion.opinion.common.ParentType;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.opinions.IPoll;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.opinion.common.opinions.IOpinion.JsonNames;
import com.inqwise.opinion.opinion.entities.controls.ControlEntity;
import com.inqwise.opinion.opinion.managers.ControlsManager;

public class PollEntity extends OpinionEntity implements IPoll {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4900358974697580896L;
	private String voteButtonTitle;
	private String resultsButtonTitle;
	private String previousButtonTitle;
	
	public PollEntity(ResultSet reader, HashMap<String, IVariableSet> permissions) throws SQLException {
		super(reader, permissions);
		setVoteButtonTitle(ResultSetHelper.optString(reader, ResultSetNames.FINISH_BUTTON_TITLE));
		setResultsButtonTitle(ResultSetHelper.optString(reader, ResultSetNames.NEXT_BUTTON_TITLE));
		setPreviousButtonTitle(ResultSetHelper.optString(reader, ResultSetNames.PREVIOUS_BUTTON_TITLE));
	}

	private interface ICreateCopy extends ICreatePollRequest, ICreatePollRequest.IRequestExtension {
	}
	
	@Override
	public OperationResult<Long> copyTo(final Long translationId,
			final String translationName, final long accountId,
			final String name, final String actionGuid, final long userId) {

		final PollEntity s = this;
		ICreateCopy request = new ICreateCopy() {
			
			@Override
			public String getTranslationName() {
				return null == translationName ? s.getTranslationName() : translationName;
			}
			
			@Override
			public Long getTranslationId() {
				return null == translationId ? s.getTranslationId() : translationId;
			}
			
			@Override
			public String getTitle() {
				return s.getTitle();
			}
			
			@Override
			public Integer getThemeId() {
				return s.getThemeId();
			}
			
			@Override
			public long getAccountId() {
				return accountId;
			}
			
			@Override
			public String getName() {
				return null == name ? s.getName() + DEFAULT_COPY_SUFFIX : name;
			}
			
			@Override
			public String getDescription() {
				return s.getDescription();
			}
			
			@Override
			public String getCulture() {
				return s.getCulture();
			}

			@Override
			public String getActionGuid() {
				return actionGuid;
			}

			@Override
			public String getVoteButtonTitle() {
				return s.getVoteButtonTitle();
			}

			@Override
			public Boolean getIsHighlightRequestedQuestions() {
				return s.getIsHighlightRequiredQuestions();
			}

			@Override
			public String getResultsButtonTitle() {
				return s.getResultsButtonTitle();
			}

			@Override
			public String getPreviousButtonTitle() {
				return s.getPreviousButtonTitle();
			}

			@Override
			public boolean isRtl() {
				return s.isRtl();
			}

			@Override
			public long getUserId() {
				return userId;
			}

			@Override
			public Boolean isHidePoweredBy() {
				return null;
			}
		};
		
		OperationResult<Long> result = OpinionsActionsFactory.getInstance().create(request);
		Long opinionId = null;
		
		
		List<IControl> controls = null;
		if(!result.hasError()){
			opinionId = result.getValue();
			OperationResult<List<IControl>> controlsResult = getControls();
			if(controlsResult.hasError() && controlsResult.getError() != ErrorCode.NoResults){
				result = controlsResult.toErrorResult();
			} else if (!controlsResult.hasError()) {
				controls = controlsResult.getValue();
			}
		}
		
		if(!result.hasError() && null != controls){
			for(IControl controlInterface : controls){
				ControlEntity control = (ControlEntity) controlInterface;
				OperationResult<Long> controlResult = control.copyTo(opinionId, opinionId,
															ParentType.Opinion.getValue(),
															null == translationId ? IOpinion.DEFAULT_TRANSLATION_ID : translationId, null,
															null, actionGuid, userId, accountId);
				if(controlResult.hasError()){
					result = controlResult.toErrorResult();
					break;
				}
			}
		}
		
		return result;
	}

	@Override
	public BaseOperationResult getTranslations() {
		
		// TODO Auto-generated method stub
		throw new Error("Not implemented");
	}

	@Override
	public String getVoteButtonTitle() {
		return voteButtonTitle;
	}

	public void setVoteButtonTitle(String voteButtonTitle) {
		this.voteButtonTitle = voteButtonTitle;
	}

	@Override
	public OpinionType getOpinionType() {
		return OpinionType.Poll;
	}

	@Override
	public String getResultsButtonTitle() {
		return resultsButtonTitle;
	}

	public void setResultsButtonTitle(String resultsButtonTitle) {
		this.resultsButtonTitle = resultsButtonTitle;
	}

	@Override
	public String getPreviousButtonTitle() {
		return previousButtonTitle;
	}

	public void setPreviousButtonTitle(String previousButtonTitle) {
		this.previousButtonTitle = previousButtonTitle;
	}
	
	@Override
	public OperationResult<List<IControl>> getControls() {
		return ControlEntity.getControls(getId(), ParentType.Opinion.getValue(), getId(), null, getTranslationId(), null, null);		
	}
	
	@Override
	public JSONObject getSettingsJson() throws JSONException {
		JSONObject result = super.getSettingsJson();
		
		result.put("messages", getMessagesJson());
		
		return result;
	}
	
	@Override
	public BaseOperationResult modify(final JSONObject input, final long userId, final Long accountId)
			throws JSONException {
		
		final boolean rtl = input.getBoolean(JsonNames.IS_RTL);
		final boolean highlightRequiredQuestions = input.getBoolean(JsonNames.HIGHLIGHT_REQUIRED_QUESTIONS);
		
		BaseOperationResult modifyResult = OpinionsActionsFactory.getInstance().modify(new IModifyPollRequest() {
			
			@Override
			public boolean isRtl() {
				return rtl;
			}
			
			@Override
			public boolean isHighlightRequiredQuestions() {
				return highlightRequiredQuestions;
			}
			
			@Override
			public long getUserId() {
				return userId;
			}
			
			@Override
			public Long getTranslationId() {
				return JSONHelper.optLong(input, JsonNames.TRANSLATION_ID, IOpinion.DEFAULT_TRANSLATION_ID);
			}
			
			@Override
			public long getOpinionId() {
				return getId();
			}
			
			@Override
			public Long getAccountId() {
				return accountId;
			}
			
			@Override
			public String getPreviousButtonTitle() {
				return JSONHelper.optString(input, JsonNames.PREVIOUS_BUTTON_TITLE);
			}

			@Override
			public String getVoteButtonTitle() {
				return JSONHelper.optString(input, JsonNames.VOTE_BUTTON_TITLE);
			}

			@Override
			public String getResultsButtonTitle() {
				return JSONHelper.optString(input, JsonNames.RESULTS_BUTTON_TITLE);
			}
			
			@Override
			public Boolean isHidePoweredBy() {
				return JSONHelper.optBoolean(input, JsonNames.HIDE_POWERED_BY);
			}
		});
		
		return modifyResult;
	}

	@Override
	public JSONObject getJson(Integer timezoneOffset) throws JSONException {
		JSONObject result = super.getJson(timezoneOffset);
		
		result.put(JsonNames.MESSAGES, getMessagesJson());
		
		return result;
	}
	
	@Override
	protected JSONObject getMessagesJson() throws JSONException{
		JSONObject messagesJo = new JSONObject();
		messagesJo.put(JsonNames.PREVIOUS_BUTTON_TITLE, JSONHelper.getNullable(getPreviousButtonTitle()));
		messagesJo.put(JsonNames.VOTE_BUTTON_TITLE, JSONHelper.getNullable(getVoteButtonTitle()));
		messagesJo.put(JsonNames.RESULTS_BUTTON_TITLE, JSONHelper.getNullable(getResultsButtonTitle()));
		
		return messagesJo;
	}
	
	private JSONObject getExportMessagesJson() throws JSONException{
		JSONObject messagesJo = new JSONObject();
		messagesJo.put(JsonNames.PREVIOUS_BUTTON_TITLE, getPreviousButtonTitle());
		messagesJo.put(JsonNames.VOTE_BUTTON_TITLE, getVoteButtonTitle());
		messagesJo.put(JsonNames.RESULTS_BUTTON_TITLE, getResultsButtonTitle());
		
		return messagesJo;
	}
	
	@Override
	public JSONObject getShortDetailsJson() throws JSONException {
		
		JSONObject output = super.getShortDetailsJson();
		output.put(JsonNames.MESSAGES, getMessagesJson());
		
		return output;
	}
	
	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = super.getExportJson();
		
		output.put(JsonNames.CONTROLS, getControlsExportJson());
		output.put(JsonNames.MESSAGES, getExportMessagesJson());
		return output;
	}
	
	private JSONArray getControlsExportJson() throws JSONException {
		JSONArray output = null;
		OperationResult<List<IControl>> controlsResult = ControlsManager.getControls(getId(), ParentType.Opinion, getTranslationId(), null, null);
		if(controlsResult.hasValue()){
			output = new JSONArray();
			List<IControl> controls = controlsResult.getValue();
			for (IControl control : controls) {
				output.put(control.getExportJson());
			}
		}
		
		return output;
	}
}
