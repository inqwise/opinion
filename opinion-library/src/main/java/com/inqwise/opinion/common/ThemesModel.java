package com.inqwise.opinion.common;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class ThemesModel {

	private Integer themeId;
	private String name;
	private Boolean isTemplate;

	private ThemesModel(Builder builder) {
		this.themeId = builder.themeId;
		this.name = builder.name;
		this.isTemplate = builder.isTemplate;
	}	
	
	public static final class Keys{

		public static final String THEME_ID = "theme_id";
		public static final String NAME = "name";
		public static final String IS_TEMPLATE = "is_template";
	}
	
	public ThemesModel(JSONObject json) {
		themeId = json.optIntegerObject(Keys.THEME_ID);
		name = json.optString(Keys.NAME);
		isTemplate = json.optBooleanObject(Keys.IS_TEMPLATE);
	}

	public Integer getThemeId() {
		return themeId;
	}

	public String getName() {
		return name;
	}

	public Boolean getIsTemplate() {
		return isTemplate;
	}

	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.THEME_ID, themeId);
		json.put(Keys.NAME, name);
		json.put(Keys.IS_TEMPLATE, isTemplate);
		return json;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(ThemesModel themesModel) {
		return new Builder(themesModel);
	}

	public static final class Builder {
		private Integer themeId;
		private String name;
		private Boolean isTemplate;

		private Builder() {
		}

		private Builder(ThemesModel themesModel) {
			this.themeId = themesModel.themeId;
			this.name = themesModel.name;
			this.isTemplate = themesModel.isTemplate;
		}

		public Builder withThemeId(Integer themeId) {
			this.themeId = themeId;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withIsTemplate(Boolean isTemplate) {
			this.isTemplate = isTemplate;
			return this;
		}

		public ThemesModel build() {
			return new ThemesModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("themeId", themeId).add("name", name).add("isTemplate", isTemplate)
				.toString();
	}

}
