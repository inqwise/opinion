package com.inqwise.opinion.cms.entities;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.inqwise.opinion.cms.common.IPage;
import com.inqwise.opinion.cms.common.IPagesEnvironment;
import com.inqwise.opinion.cms.common.IScript;
import com.inqwise.opinion.cms.common.IStylesheet;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.entities.BufferedHttpServletResponseWrapper;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class PageEntity implements IPage  {
	
	
	static final String HTML_SCRIPT_TAG_FORMAT = "<script type=\"text/javascript\" src=\"%s\"></script>";
	static final String HTML_CSS_TAG_FORMAT = "<link rel=\"stylesheet\" rev=\"stylesheet\" href=\"%s\" type=\"text/css\" />";
	static final String INCLUDE_FOLDER = "/includes/";
	static ApplicationLog logger = ApplicationLog.getLogger(PageEntity.class);
		
	private IPagesEnvironment environment;
	private int id;
	private String uri;
	private Integer parentId;
	private HashSet<String> keywords;
	private int orderPosition;
	private String include;
	private String title;
	private String windowTitle;
	private String description;
	private String content;
	private String header;
	private Hashtable<String, IScript> scripts;
	private Hashtable<String, IStylesheet> cssList;
	private int languageId;
	private Integer templateId;
	private String cultureCode;
	private Integer rootId;
	private boolean isContentCompailed;
	private boolean enableGoogleAnalytics;
	
	public PageEntity(ResultSet reader, IPagesEnvironment environment) throws SQLException, ServletException{
		keywords = new HashSet<String>();
		
		setEnvironment(environment);
		setId(reader.getInt("page_id"));
		setUri(reader.getString("uri"));
		setParentId(ResultSetHelper.optInt(reader, "parent_id"));
		setRootId(ResultSetHelper.optInt(reader, "root_id"));
		String flatKeywords = ResultSetHelper.optString(reader, "keywords");
		if(null != flatKeywords){
			addKeywords(Arrays.asList(StringUtils.split(flatKeywords, ',')));
		}
		setOrderPosition(reader.getInt("order_position"));
		
		setTitle(ResultSetHelper.optString(reader, "title"));
		setWindowTitle(ResultSetHelper.optString(reader, "window_title"));
		setDescription(ResultSetHelper.optString(reader, "description"));
		
		setHeader(ResultSetHelper.optString(reader, "header"));
		setLanguageId(reader.getInt("language_id"));
		scripts = new Hashtable<String, IScript>();
		cssList = new Hashtable<String, IStylesheet>();
		setTemplateId(ResultSetHelper.optInt(reader, "template_id"));
		setCultureCode(reader.getString("culture_code"));
		
		setInclude(ResultSetHelper.optString(reader, "include"));
		if(null == getInclude()){
			setContent(ResultSetHelper.optString(reader, "content"));
			isContentCompailed = true;
		} 
		
		setEnableGoogleAnalytics(reader.getBoolean("enable_google_analytics"));
	}
	
	public void compile() throws ServletException, IOException{
		if(!isContentCompailed){
			setContent(loadIncludedPageContentByDispatch(environment.getRequest(), environment.getResponse(), getInclude()));
			isContentCompailed = true;
		}
	}
	
	private String loadIncludedPageContentByDispatch(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException{
		
		BufferedHttpServletResponseWrapper res = new BufferedHttpServletResponseWrapper(response);
		PrintWriter out = res.getWriter();
		ServletContext content = request.getSession(true).getServletContext();
		RequestDispatcher rd = content.getRequestDispatcher(combine(INCLUDE_FOLDER, path));
		request.setAttribute(PAGE_OBJECT_ATTRIBUTE_NAME, this);
		rd.include(request, res);
		out.flush();
		return res.getStringContent();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUri() {
		return uri;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public HashSet<String> getKeywords() {
		return keywords;
	}
	public void setOrderPosition(int orderPosition) {
		this.orderPosition = orderPosition;
	}
	public int getOrderPosition() {
		return orderPosition;
	}
	public void setInclude(String include) {
		this.include = include;
	}
	public String getInclude() {
		return include;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}
	public String getWindowTitle() {
		return windowTitle;
	}
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getHeader() {
		return header;
	}
	public IScript[] getScripts() {
		Collection<IScript> values = scripts.values();
		return values.toArray(new IScript[values.size()]);
	}
	
	public void addScript(IScript script){
		scripts.put(script.getKey(), script);
	}
	
	public IStylesheet[] getCssList() {
		Collection<IStylesheet> values = cssList.values();
		return values.toArray(new IStylesheet[values.size()]);
	}
	
	public void addCss(IStylesheet css){
		cssList.put(css.getKey(), css);
	}
	
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	
	public int getLanguageId() {
		return languageId;
	}
	
	public void setCultureCode(String cultureCode) {
		this.cultureCode = cultureCode;
	}

	public String getCultureCode() {
		return cultureCode;
	}
	
	public void setRootId(Integer rootId) {
		this.rootId = rootId;
	}

	public Integer getRootId() {
		return rootId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setEnvironment(IPagesEnvironment environment) {
		this.environment = environment;
	}

	public IPagesEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public String getAbsoluteUrl() {
		return combine(getEnvironment().getAbsoluteURL().toString(), getUri());
	}
	
	@Override
	public String getAbsoluteSecureUrl() {
		return getEnvironment().getAbsoluteSecureURL().toString();
	}

	@Override
	public String getApplicationUrl() {
		return getEnvironment().getApplicationURL().toString();
	}

	@Override
	public String getCanonicalUrl() {
		return combine(getEnvironment().getAbsoluteURL().toString(), getUri());
	}

	@Override
	public IPage getParent() {
		return getEnvironment().getPage(getParentId());
	}

	@Override
	public String getRootAbsoluteUrl() {
		return getEnvironment().getAbsoluteURL().toString();
	}

	@Override
	public boolean hasParent() {
		return null != getParentId();
	}

	private String getHref(String path, boolean isReleative){
		String fullPath;
		if(isReleative){
			String baseUrl = ApplicationConfiguration.Static.getUrl();
			if(null == baseUrl){
				baseUrl = getEnvironment().getApplicationURL().toString();
			}
			fullPath = combine(baseUrl, path);
		} else {
			fullPath = path;
		}
		return fullPath;
	}
	
	private static String combine (String url1, String url2) 
	{ 
		String adaptedUrl1 = (null == url1 || url1.endsWith("/") ? url1 : (url1 + "/"));
		String adaptedUrl2 = (null != url2 && url2.startsWith("/") ? url2.substring(1) : url2);
	    return adaptedUrl1 + adaptedUrl2;
	}
	
	@Override
	public String getHtmlTag(IStylesheet css) {
		
		return String.format(HTML_CSS_TAG_FORMAT, getHref(css.getPath(), css.isReleativePath()));
	}

	@Override
	public String getHtmlTag(IScript script) {
		return String.format(HTML_SCRIPT_TAG_FORMAT, getHref(script.getPath(), script.isReleativePath()));
	}

	@Override
	public boolean isComplex() {
		return null != getInclude();
	}
	
	public String getAdaptedCultureCode() {
		return getEnvironment().getLanguage().getAdaptedCultureCode();
	}

	public void setEnableGoogleAnalytics(boolean enableGoogleAnalytics) {
		this.enableGoogleAnalytics = enableGoogleAnalytics;
	}

	public boolean isEnableGoogleAnalytics() {
		return enableGoogleAnalytics;
	}

	@Override
	public IProduct getCurrentProduct() {
		
		IProduct product = ProductsManager.getCurrentProduct();
		if(null == product){
			logger.error("Failed to get current product object.");
		}
		return product;
	}

	@Override
	public String getServerUrl() {
		return getEnvironment().getServerURL().toString();
	}
	
	@Override
	public void addKeyword(String keyword){
		keywords.add(keyword);
	}
	
	@Override
	public void addKeywords(Collection<String> keywords){
		for (String key : keywords) {
			addKeyword(key);
		}
	}

	@Override
	public JSONObject toJSON() {
		throw new NotImplementedException("toJSON");
	}
}
