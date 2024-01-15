package com.inqwise.opinion.common;

import org.json.JSONObject;

public class ThemesRepositoryParser {
	public ThemesModel parse(JSONObject json) {
		return ThemesModel.builder()
				.withThemeId(json.optIntegerObject(ITheme.ResultSetNames.THEME_ID))
				.withName(json.optString(ITheme.ResultSetNames.NAME))
				.withIsTemplate(json.optBooleanObject(ITheme.ResultSetNames.IS_TEMPLATE))
				.build();
	}
}