package com.inqwise.opinion.opinion.common;

import org.json.JSONException;
import org.json.JSONObject;

public interface IControl {
	
	final class JsonNames {
		public static final String OPINION_ID = "opinionId";
		public static final String COMMENT = "comment";
		public static final String INPUT_TYPE_ID = "inputTypeId";
		public static final String IS_MANDATORY = "isMandatory";
		public static final String NOTE = "note";
		public static final String ARRANGE_ID = "arrangeId";
		public static final String LINK = "link";
		public static final String LINK_TYPE_ID = "linkTypeId";
		public static final String ANSWER_COMMENT_KEY = "answerComment";
		public static final String INPUT_SIZE_TYPE_ID = "inputSizeTypeId";
		public static final String ANSWER_VALUE_KEY = "answerValue";
		public static final String SUB_CONTROLS = "subControls";
		public static final String OPTIONS = "options";
		public static final String LIST = "list";
		public static final String TO_TITLE = "toTitle";
		public static final String FROM_TITLE = "fromTitle";
		public static final String TO_SCALE = "toScale";
		public static final String FROM_SCALE = "fromScale";
		public static final String KEY = "key";
		public static final String TRANSLATION_ID = "translationId";
		public static final String CONTENT = "content";
		public static final String CONTROL_TYPE_ID = "controlTypeId";
		public static final String PARENT_TYPE_ID = "parentTypeId";
		public static final String PARENT_ID = "parentId";
		public static final String CONTROL_ID = "controlId";
		public static final String IS_HIDDEN = "hidden";
		public static final String INCALCULABLE = "incalculable";
		public static final String COUNT_OF_ANSWERS = "answered";
		public static final String COUNT_OF_SKIP = "skipped";
		public static final String COMPLETION_RATE = "completionRate";
		public static final String TIMEZONE_TITLE = "timeZoneTitle";
		public static final String MINUTES_TITLE = "minutesTitle";
		public static final String HOURS_TITLE = "hoursTitle";
		public static final String DAY_TITLE = "dayTitle";
		public static final String MONTH_TITLE = "monthTitle";
		public static final String YEAR_TITLE = "yearTitle";
		public static final String ORDER_ID = "orderId";
	}
	
	public Long getId();

	public Long getOpinionId();

	public Long getParentId();

	public ControlType getControlType();

	public Long getTranslationId();

	public String getContent();

	public Integer getOrderId();
	
	public JSONObject toJson() throws JSONException;

	public ParentType getParentType();

	public JSONObject toJson(boolean withAnswer) throws JSONException;

	public JSONObject getExportJson() throws JSONException;
	
	public boolean isHidden();
	
	public String getKey();
}
