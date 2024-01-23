package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.http.HttpClientSession;
import com.inqwise.opinion.http.HttpClientSessionUserArgs;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.EmailProvider;
import com.inqwise.opinion.infrastructure.systemFramework.NetworkHelper;
import com.inqwise.opinion.infrastructure.systemFramework.cryptography.StringEncrypter;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class DataPostmasterDescryptorBase implements IPostmasterContext {

	public static int[] DEFAULT_PORTS = new int[] {80, 443};
	
	public final static String SESSION_ID_COOKIE_KEY = "sib";
	public final static String UNIQUE_ID_COOKIE_KEY = "uib";
	private final String passPhrase = "3a4d5@01e%TN";
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext context;
	
	protected static SimpleDateFormat fixedformat = new SimpleDateFormat("MMMM dd, yyyy");
	protected static Format mdyhmsFormatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
	protected static EmailProvider emailProvider;
	
	protected static ApplicationLog logger = ApplicationLog
				.getLogger(DataPostmasterDescryptorBase.class);
	

	OperationResult<IUser> _userResult = null;
	
	OperationResult<Long> _userIdResult = null;

	public DataPostmasterDescryptorBase(HttpServletRequest request, HttpServletResponse response,
			ServletContext context) {
		super();
		this.request = request;
		this.response = response;
		this.context = context; 
	}

	private String _clientIp; 
	@Override
	public String getClientIp() {
		if(null == _clientIp){
			_clientIp = getClientIp(request); 
		}
		return _clientIp;
	}

	public static String getClientIp(HttpServletRequest request){
		String remoteAddr = request.getRemoteAddr();
		String xForwardedForHeader = request.getHeader(NetworkHelper.X_FORWARDED_FOR_HEADER_NAME);
		remoteAddr = NetworkHelper.identifyClientIp(remoteAddr, xForwardedForHeader);
		return remoteAddr;    
	}
	
	public ServletContext getServletContext() {
		return context;
	}

	public HttpClientSession getSession() {
		
		String sessionId = null;
		String encryptedUserArgs = null;
		String clientIp = getClientIp(request);
		StringEncrypter desEncrypter = new StringEncrypter(passPhrase);
		
		/*
		 * Cookies
		 */
		Cookie[] cookies = this.request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookie.getMaxAge() != 0){
					String cookieName = cookie.getName();
					if (cookieName.equals(SESSION_ID_COOKIE_KEY)) {
						sessionId = cookie.getValue();
					} else if (cookieName.equals(UNIQUE_ID_COOKIE_KEY)) {
						encryptedUserArgs = cookie.getValue();
					}
				}
			}
		}
		
		try {
			String decryptedUserArgsStr = (null == encryptedUserArgs) ? null : desEncrypter.decrypt(encryptedUserArgs.replaceAll(" ", "+"));
			if(null == decryptedUserArgsStr) {
				return new HttpClientSession(ErrorCode.NotSignedIn, "Not signed in");
			} else {
				
				 // Deserialize
				var userArgs = new HttpClientSessionUserArgs(new JSONObject(decryptedUserArgsStr));
				return new HttpClientSession( sessionId, userArgs.getUserId(),
						clientIp, userArgs.getProductId(), userArgs.getClientIp());
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "getSession() : Unexpected error occured.");
			return new HttpClientSession(errorId, t.toString());
		}
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public OperationResult<IUser> getLoggedInUser() throws IOException, NullPointerException, ExecutionException {
		if(null == _userResult){
			
			OperationResult<Long> userIdResult = getUserId();
			if(userIdResult.hasError()){
				_userResult = userIdResult.toErrorResult();
			} else {
				_userResult = UsersManager.getUser(userIdResult.getValue(), getCurrentProduct().getId());
			}
		}
		
		return _userResult;
	}

	@Override
	public IProduct getCurrentProduct() {
		IProduct product = ProductsManager.getCurrentProduct();
		if(null == product){
			logger.error("getCurrentProduct : Failed to get current product");
		}
		return product;
	}

	@Override
	public OperationResult<Long> getUserId() throws IOException {
		if(null == _userIdResult){
			_userIdResult = new OperationResult<Long>();
			HttpClientSession session = getSession();
			if(session.hasError()){
				_userIdResult.setError(session);
			} else {
				_userIdResult.setValue(session.getUserId());
			}
		}
		return _userIdResult;
	}

	@Override
	public boolean IsSignedIn() throws NullPointerException, ExecutionException {
		try {
			return !getLoggedInUser().hasError();
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public IOperationResult validateSignIn() throws NullPointerException, ExecutionException {
		if(IsSignedIn()){
			return null;
		} else {
			logger.warn("Not Signed in.");
			return new BaseOperationResult(ErrorCode.NotSignedIn);
		}
	}

	@Override
	public List<IAccount> getLoggedInUserAllowedAccounts() throws IOException, NullPointerException, ExecutionException {
		IUser user = null;
		IProduct product = getCurrentProduct();
		
		if(null != product){
			OperationResult<IUser> userResult = getLoggedInUser();
			if(userResult.hasError()){
				throw new Error("User not authenticated. " + userResult.toString());
			} else {
				user = userResult.getValue();
			}
		}
		
		List<IAccount> accounts = null;
		if(null != user){
			OperationResult<List<IAccount>> accountsResult = user.getAccounts(product.getId());
			if(accountsResult.hasError()){
				logger.error("getLoggedInUserAllowedAccounts : Failed to get accounts for userId '%s'", user.getUserId());
			} else {
				accounts = accountsResult.getValue();
			}
		}
		
		return accounts;
	}
	
	@Override
	public IAccount getDefaultAccount() throws IOException, NullPointerException, ExecutionException {
		return getLoggedInUserAllowedAccounts().get(0);
	}
	
	public void addUserIdToSession(Long userId, int productId, String clientIp, boolean isPersist) throws IOException{
		var userArgs = HttpClientSessionUserArgs.builder()
				.withUserId(userId)
				.withProductId(productId)
				.withClientIp(clientIp)
				.build();
		
		 // Serialize
		addToSession(UNIQUE_ID_COOKIE_KEY, userArgs.toJson().toString(), true, isPersist);
	}
	
	public void addSessionIdToSession(UUID sessionId, boolean isPersist){
		addToSession(SESSION_ID_COOKIE_KEY, sessionId.toString(), false, isPersist);
	}
	
	private void addToSession(String key, String value, Boolean encripted, boolean isPersist){
		
		int cookieExpire = isPersist ? -1 : (60 * 60 * 24) * 30; // 30 days
		StringEncrypter desEncrypter = new StringEncrypter(passPhrase);
		if(encripted){
			value = desEncrypter.encrypt(value);
		}
		
		Cookie uniqueCookie = new Cookie(key, value);
		uniqueCookie.setMaxAge(cookieExpire);
		uniqueCookie.setPath("/");
		if(null != ApplicationConfiguration.BackOffice.getSessionDomain()){
			uniqueCookie.setDomain(ApplicationConfiguration.BackOffice.getSessionDomain());
		}
		response.addCookie(uniqueCookie);
	}
	
	@Override
	public URL getAbsoluteURL() throws MalformedURLException {
		return getAbsoluteURL(null);
	}
	
	@Override
	public URL getAbsoluteURL(boolean secure) throws MalformedURLException {
		return getAbsoluteURL(secure);
	}
	
	private URL getAbsoluteURL(Boolean secure) throws MalformedURLException {
		Locale locale = null;
		locale = Locale.US;
		String relativePath = request.getContextPath() + "/" + locale.toString().replace("_", "-").toLowerCase();
		
		return getAbsoluteURL(secure, relativePath);
	}
	
	private URL getAbsoluteURL(Boolean secure, String relativePath) throws MalformedURLException {
		
		String scheme = null;
		Integer port = null;
		boolean isDefaultPort = ArrayUtils.contains(DEFAULT_PORTS, request.getServerPort());
		if(null == secure){
			scheme = request.getScheme();
			if(!isDefaultPort) port = request.getServerPort();
		} else {
			scheme = secure ? "https" : "http";
			if(!isDefaultPort) { 
				port = secure ? 8443 : 8080;
			}
		}
		
		return isDefaultPort ? new URL(scheme, request.getServerName(), relativePath) : new URL(scheme, request.getServerName(), port, relativePath);
	}

	@Override
	public URL getApplicationURL() throws MalformedURLException {
		return getApplicationURL(null);
	}
	
	@Override
	public URL getApplicationURL(boolean secure) throws MalformedURLException {
		return getApplicationURL(secure);
	}
	
	private URL getApplicationURL(Boolean secure) throws MalformedURLException {
		/*
		String scheme = null;
		Integer port = null;
		boolean isDefaultPort = ArrayUtils.contains(DEFAULT_PORTS, request.getServerPort());
		if(null == secure){
			scheme = request.getScheme();
			if(!isDefaultPort) port = request.getServerPort();
		} else {
			scheme = secure ? "https" : "http";
			if(!isDefaultPort) { 
				port = secure ? 8443 : 8080;
			}
		}
		
		return isDefaultPort ? new URL(scheme, request.getServerName(), request.getContextPath()) : new URL(scheme, request.getServerName(), port, request.getContextPath());
		*/
		
		return getAbsoluteURL(secure, request.getContextPath());
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public JSONObject validateUser(JSONObject input) throws IOException {
		
		BaseOperationResult result = null;
		JSONObject returnObject = new JSONObject();
		
		try {
			HttpClientSession session = getSession();
			if(null == session || session.hasError())
			{
				returnObject.put("error", ErrorCode.UniqueUserIdIncorrect);
				result = session;
			}
			
			IProduct product = null;
			if(null == result){
				product = ProductsManager.getCurrentProduct();
				if(null == product){
					result = new BaseOperationResult(ErrorCode.NoResults);
				}
			}
			
			// User
			IUser user = null;
			if(null == result){
				OperationResult<IUser> validateResult = UsersManager.getUser(session.getUserId(), product.getId());
				if(validateResult.hasError()) {
					result = validateResult;
					switch (validateResult.getError()) {
					case UserNotExist:
						returnObject.put("error", validateResult.getError());
						break;
					default:
						// Service temporary is unavailable
						logger.error("validateUser() : getUser unexpected error :" + validateResult.toString());
						returnObject.put("error", ErrorCode.GeneralError);
						break;
					}
				} else {
					user = validateResult.getValue(); 
				} 
			}
			
			List<IAccount> accounts = null;
			if(null == result){
				OperationResult<List<IAccount>> accountsResult = user.getAccounts(product.getId());
				if(accountsResult.hasError()){
					result = accountsResult;
					returnObject = result.toJson();
				} else {
					accounts = accountsResult.getValue();
				}
			}
			
			if(null == result && accounts.size() == 0){
				logger.warn("validateUser : user # '%s' without account", session.getUserId());
				result = new BaseOperationResult(ErrorCode.NotAllowedForProduct);
				returnObject = result.toJson();
			}
			
			if(null == result) {
				returnObject.put("userName", user.getUserName());
				returnObject.put("displayName", user.getDisplayName());
			}
			
		} catch (Throwable t) {
			logger.error(t, "ValidateUser() : unexpected error occured.");
			try {
				returnObject.put("error", ErrorCode.GeneralError);
			} catch (JSONException e) {
				logger.error(t, "ValidateUser() : error occured when was write error to output object.");
			}
		}
		
		return returnObject;
	}

	@Override
	public void setHeader(String key, String value) {
		response.setHeader(key, value);
	}

	@Override
	public void setContentType(String arg0) {
		response.setContentType(arg0);
	}

	@Override
	public void sendRedirect(String arg0) {
		try {
			response.sendRedirect(arg0);
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	@Override
	public String getRequestUrl() {
		String result;
		Object obj = request.getAttribute("javax.servlet.forward.request_uri");
		if(null == obj){
			result = request.getContextPath() + "?" + request.getQueryString();
		} else {
			result = obj.toString();
		}
		
		return result;
	}

	@Override
	public URL getServerUrl(boolean secure) throws MalformedURLException{
		return getServerUrl(secure);
	}
	
	@Override
	public URL getServerUrl() throws MalformedURLException{
		return getServerUrl(null);
	}
	
	private URL getServerUrl(Boolean secure) throws MalformedURLException {
		return getAbsoluteURL(secure, request.getContextPath() + "/");
	}

	public OperationResult<IAccount> getAccount() {
		return null;
	}
}
