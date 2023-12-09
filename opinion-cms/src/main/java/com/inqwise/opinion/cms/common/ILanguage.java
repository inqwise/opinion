package com.inqwise.opinion.cms.common;

import java.util.Locale;

public interface ILanguage {

	public abstract String getFallbackCulture();

	public abstract String getCulture();

	public abstract String getCultureCode();

	public abstract int getId();

	public abstract Locale getLocale();
	
	public abstract String getAdaptedCultureCode();

}
