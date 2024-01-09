package com.inqwise.opinion.common;

import org.json.JSONException;
import org.json.JSONObject;

public interface IOption {
	
	final class JsonNames {

		public static final String OPTION_KIND_ID = "optionKindId";
		public static final String IS_ENABLE_ADDITIONAL_DETAILS = "isEnableAdditionalDetails";
		public static final String ADDITIONAL_DETAILS_TITLE = "additionalDetailsTitle";
		public static final String TRANSLATION_ID = "translationId";
		public static final String VALUE = "value";
		public static final String TEXT = "text";
		public static final String OPTION_ID = "optionId";
		public static final String ANSWER_SELECTED_CONTROLS_IDS = "answerSelectedControlsIds";
		public static final String SELECT_TYPE_ID = "selectTypeId";
		public static final String ANSWER_VALUE = "answerValue";
		public static final String ANSWER_COMMENT = "answerComment";
		public static final String LINK = "link";
		public static final String LINK_TYPE_ID = "linkTypeId";
	}
	
	public String getValue();
	public String getText();
	public Long getId();
	public Object getControlId();
	public JSONObject getExportJson() throws JSONException;
}
