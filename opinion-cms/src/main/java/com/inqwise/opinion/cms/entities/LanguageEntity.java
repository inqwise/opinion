package com.inqwise.opinion.cms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import com.inqwise.opinion.cms.common.ILanguage;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;

public class LanguageEntity implements ILanguage {
	
	private static final int CULTURE_CODE_LENGTH = 5;
	static ApplicationLog logger = ApplicationLog.getLogger(LanguageEntity.class);
	private int id;
	private String cultureCode;
	private String culture;
	private String fallbackCulture;
	private String adaptedCultureCode;
	
	public LanguageEntity(ResultSet reader) throws SQLException {
		setId(reader.getInt("language_id"));
		setCultureCode(ResultSetHelper.optString(reader, "culture_code"));
		if(cultureCode.length() != CULTURE_CODE_LENGTH){
			logger.warn("LanguageEntity : THe length of characters in CultureCode '%s' isn't %s", cultureCode, CULTURE_CODE_LENGTH);
		}
		setCulture(ResultSetHelper.optString(reader, "culture"));
		setFallbackCulture(ResultSetHelper.optString(reader, "fallback_culture"));
		setAdaptedCultureCode(getCultureCode().toLowerCase().replace("_","-"));
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setCultureCode(String cultureCode) {
		this.cultureCode = cultureCode;
	}

	public String getCultureCode() {
		return cultureCode;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public String getCulture() {
		return culture;
	}

	public void setFallbackCulture(String fallbackCulture) {
		this.fallbackCulture = fallbackCulture;
	}

	public String getFallbackCulture() {
		return fallbackCulture;
	}

	public Locale getLocale() {
		return new Locale(cultureCode.substring(0, 2).toLowerCase(), cultureCode.substring(3, 5).toUpperCase());
	}

	public void setAdaptedCultureCode(String adaptedCultureCode) {
		this.adaptedCultureCode = adaptedCultureCode;
	}

	public String getAdaptedCultureCode() {
		return adaptedCultureCode;
	}
}
