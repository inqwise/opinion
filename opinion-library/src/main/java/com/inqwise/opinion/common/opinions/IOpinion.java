package com.inqwise.opinion.common.opinions;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public interface IOpinion {

	long DEFAULT_TRANSLATION_ID = 0;
	public static final String PREVIEW_URL_FORMAT = "%s/d/%s/%s/0";

	final class JsonNames {

		public static final String REDIRECT_URL = "redirectUrl";
		public static final String FINISH_BUTTON_TITLE = "finish";
		public static final String FINISH_MESSAGE = "finishMessage";
		public static final String NEXT_BUTTON_TITLE = "next";
		public static final String PREVIOUS_BUTTON_TITLE = "back";
		public static final String START_BUTTON_TITLE = "start";
		public static final String START_MESSAGE = "startMessage";
		public static final String SHOW_PROGRESS_BAR = "showProgressBar";
		public static final String TRANSLATION_ID = "translationId";
		public static final String USE_PAGE_NUMBERING = "usePageNumbering";
		public static final String USE_QUESTION_NUMBERING = "useQuestionNumbering";
		public static final String HIGHLIGHT_REQUIRED_QUESTIONS = "highlightRequiredQuestions";
		public static final String IS_RTL = "isRtl";
		public static final String USE_CUSTOM_START_MESSAGE = "useCustomStartMessage";
		public static final String LOGO_URL = "logoUrl";
		public static final String PREVIEW_URL = "previewUrl";
		public static final String THEME_ID = "themeId";
		public static final String GUID = "guid";
		public static final String MODIFY_DATE = "modifyDate";
		public static final String NAME = "name";
		public static final String CREATE_DATE = "createDate";
		public static final String TITLE = "title";
		public static final String RESULTS_BUTTON_TITLE = "viewResults";
		public static final String VOTE_BUTTON_TITLE = "vote";
		public static final String OPINION_ID = "opinionId";
		public static final String OPINION_TYPE_ID = "opinionTypeId";
		public static final String ACCOUNT_ID = "accountId";
		public static final String CNT_STARTED_OPINIONS = "started";
		public static final String DESCRIPTION = "description";
		public static final String TRANSLATION_NAME = "translationName";
		public static final String SHOW_WELCOME_MESSAGE = "showWelcomeMessage";
		public static final String IS_ENABLE_START_MESSAGE = "isEnableStartMessage";
		public static final String MESSAGES = "messages";
		public static final String FINISH_BEHAVIOUR_TYPE_ID = "customFinishBehaviour";
		public static final String CULTURE = "culture";
		public static final String SHEETS = "sheets";
		public static final String CONTROLS = "controls";
		public static final String THEME = "theme";
		public static final String PREVIEW_SECURE_URL = "previewSecureUrl";
		public static final String CNT_FINISHED_OPINIONS = "completed";
		public static final String CNT_PARTIAL_OPINIONS = "partial";
		public static final String COMPLETION_RATE = "completionRate";
		public static final String LAST_RESPONSE_DATE = "lastResponseDate";
		public static final String TIME_TAKEN = "timeTaken";
		public static final String LABEL_PLACEMENT_ID = "labelPlacement";
		public static final String SHOW_PAGING = "showPaging";
		public static final String COUNT_CONTROLS = "cntControls";
		public static final String HIDE_POWERED_BY = "hidePoweredBy";
		public static final String ACCOUNT_NAME = "accountName";
		public static final String ALREADY_COMPLETED_MESSAGE = "completedMessage";
		public static final String REQUIRED_QUESTION_ERROR_MESSAGE = "requiredQuestionErrorMessage";
	}
	
	final class ResultSetNames {

		public static final String TRANSLATION_NAME = "translation_name";
		public static final String DESCRIPTION = "description";
		public static final String THEME_ID = "theme_id";
		public static final String NAME = "name";
		public static final String OPINION_ID = "opinion_id";
		public static final String IS_HIGHLIGHT_REQUIRED_QUESTIONS = "is_highlight_required_questions";
		public static final String VERSION = "version";
		public static final String MODIFY_USER_ID = "modify_user_id";
		public static final String CULTURE = "culture";
		public static final String IS_RTL = "is_rtl";
		public static final String GUID = "guid";
		public static final String ACCOUNT_ID = "account_id";
		public static final String TRANSLATION_ID = "translation_id";
		public static final String MODIFY_DATE = "modify_date";
		public static final String CREATE_DATE = "create_date";
		public static final String LOGO_URL = "logo_url";
		public static final String USE_QUESTION_NUMBERING = "use_question_numbering";
		public static final String NEXT_BUTTON_TITLE = "next_button_title";
		public static final String USE_PAGE_NUMBERING = "use_page_numbering";
		public static final String PREVIOUS_BUTTON_TITLE = "previous_button_title";
		public static final String FINISH_BUTTON_TITLE = "finish_button_title";
		public static final String START_BUTTON_TITLE = "start_button_title";
		public static final String REDIRECT_URL = "redirect_url";
		public static final String SHOW_PROGRESS_BAR = "show_progress_bar";
		public static final String FINISH_MESSAGE = "finish_message";
		public static final String START_MESSAGE = "start_message";
		public static final String TITLE = "title";
		public static final String CNT_STARTED_OPINIONS = "cnt_started_opinions";
		public static final String OPINION_TYPE_ID = "opinion_type_id";
		public static final String FINISH_BEHAVIOUR_TYPE_ID = "finish_behaviour_type_id";
		public static final String CNT_FINISHED_OPINIONS = "cnt_finished_opinions";
		public static final String LAST_START_DATE = "last_start_date";
		public static final String AVG_TIME_TAKEN_SEC = "avg_time_taken_sec";
		public static final String LABEL_PLACEMENT_ID = "label_placement_id";
		public static final String SHOW_PAGING = "show_paging";
		public static final String COUNT_CONTROLS = "cnt_of_controls";
		public static final String HIDE_POWERED_BY = "disable_powered_by";
		public static final String ALREADY_COMPLETED_MESSAGE = "already_completed_message";
		public static final String REQUIRED_QUESTION_ERROR_MESSAGE = "required_question_error_message";
	}
	
	public Long getId();
	public String getName();
	public String getTitle();

	public JSONObject toJson() throws JSONException;

	public String getGuid();
	public Long getTranslationId();
	public Long getAccountId();
	public boolean isRtl();
	public IAccount getAccount();
	public Integer getThemeId();
	public Long getLastModifyUserId();
	public BaseOperationResult getTranslations();
	public Date getCreateDate();
	public Date getModifyDate();
	public Integer getVersion();
	public String getRequiredQuestionErrorMessage();
	
	public abstract OperationResult<Long> copyTo(Long translationId, String translationName,
			long accountId, String name, String actionGuid, long userId);

	public abstract OpinionType getOpinionType();
	
	public abstract Boolean getIsHighlightRequiredQuestions();
	
	public JSONObject getSettingsJson() throws JSONException;

	public BaseOperationResult modify(JSONObject input, long userId, Long accountId) throws JSONException;
	
	public abstract String getPreviewUrl(boolean secure);
	
	public JSONObject getJson(Integer timezoneOffset) throws JSONException;

	public JSONObject getShortDetailsJson() throws JSONException;
	
	public FinishBehaviourType getFinishBehaviourType();
	
	public JSONObject getExportJson() throws JSONException;

	boolean isHidePoweredBy();
	
}