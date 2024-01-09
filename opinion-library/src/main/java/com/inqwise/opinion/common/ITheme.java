package com.inqwise.opinion.common;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.common.opinions.OpinionType;

public interface ITheme {

	public class JsonNames {
		public static String ID = "themeId";
		public static String NAME = "name";
		public static String IS_TEMPLATE = "isTemplate";
		public static String CSS_CONTENT = "styleBlock";
		public static String OPINION_TYPE_ID = "opinionTypeId";
	} 
	
	public final class ResultSetNames {
		public static final String NAME = "theme_name";
		public static final String IS_TEMPLATE = "is_template";
		public static String THEME_ID = "theme_id";
		public static String OPINION_TYPE_ID = "opinion_type_id";
	}
	
	public abstract long getId();
	public abstract String getName();
	public abstract boolean isTemplate();
	public abstract OpinionType getOpinionType();
	public abstract String getCssContent();
	public abstract JSONObject getExportJson() throws JSONException;
}