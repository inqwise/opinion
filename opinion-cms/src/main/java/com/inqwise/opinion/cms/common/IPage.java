package com.inqwise.opinion.cms.common;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import javax.servlet.ServletException;

import org.json.JSONObject;

import com.inqwise.opinion.library.common.IProduct;

public interface IPage {
	public static final String PAGE_OBJECT_ATTRIBUTE_NAME = "p";
	public static final int[] DEFAULT_PORTS = new int[] {80, 443};
	public int getId();
	public int getLanguageId();
	public String getTitle();
	public String getWindowTitle();
	public void setWindowTitle(String title);
	public String getDescription();
	public HashSet<String> getKeywords();
	public String getHeader();
	public String getContent();
	public Integer getParentId();
	public Integer getRootId();
	public IScript[] getScripts();
	public void addScript(IScript script);
	public void addCss(IStylesheet css);
	public IStylesheet[] getCssList();
	public void setTitle(String title);
	public String getCanonicalUrl();
	public String getCultureCode();
	public String getUri();
	public Integer getTemplateId();
	public void compile()  throws ServletException, IOException;
	public String getAbsoluteUrl();
	public String getAbsoluteSecureUrl();
	public String getRootAbsoluteUrl();
	public String getApplicationUrl();
	public boolean hasParent();
	public IPage getParent();
	public String getHtmlTag(IStylesheet css);
	public String getHtmlTag(IScript script);
	public boolean isComplex();
	public String getAdaptedCultureCode();
	public boolean isEnableGoogleAnalytics();
	public IProduct getCurrentProduct();
	public String getServerUrl();
	public void addKeywords(Collection<String> keywords);
	public void addKeyword(String keyword);
	public abstract void setDescription(String description);
	public JSONObject toJSON();
}
