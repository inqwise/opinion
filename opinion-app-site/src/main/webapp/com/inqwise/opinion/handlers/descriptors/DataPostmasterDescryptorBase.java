package com.inqwise.opinion.handlers.descriptors;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;

import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.facade.front.ClientSessionDescryptor;
import com.inqwise.opinion.http.HttpClientSession;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.Convert;
import com.inqwise.opinion.infrastructure.systemFramework.NetworkHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;

public class DataPostmasterDescryptorBase extends ClientSessionDescryptor implements IPostmasterContext {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext context;
	protected Long accountId;
	
	public static int[] DEFAULT_PORTS = new int[] {80, 443};
	protected static ApplicationLog logger = ApplicationLog
				.getLogger(DataPostmasterDescryptorBase.class);
	

	OperationResult<IUser> _userResult = null;
	
	OperationResult<Long> _userIdResult = null;

	public DataPostmasterDescryptorBase(HttpServletRequest request, HttpServletResponse response,
			ServletContext context) {
		super();
		this.request = request;
		this.response = response;
		this.accountId = Convert.toOptLong(request.getParameter(IAccount.JsonNames.ACCOUNT_ID));
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

	private HttpClientSession _session;
	@Override
	public HttpClientSession getSession() throws IOException {
		if(null == _session){
			_session = ClientSessionDescryptor.getSession(request);
		}
		return _session;
	}

	@Override
	public void addUserIdToSession(Long userId, int productId, String clientIp, boolean untilEndSession) throws IOException {
		ClientSessionDescryptor.addUserIdToSession(userId, productId, clientIp, untilEndSession, response);
	}

	@Override
	public void addSessionIdToSession(UUID sessionId, boolean isPersist) {
		ClientSessionDescryptor.addSessionIdToSession(sessionId, isPersist, response);
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
		String lang = request.getParameter("lang");
		if(null == lang || lang.length() == 0){
			lang = Locale.US.toString().replace("_", "-");
		}
		
		String relativePath = request.getContextPath() + "/" + lang.toLowerCase();
		
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
		return getLoggedInUser().getValue().getDefaultAccount(getSession().getLoginProductId());
	}

	@Override
	public void setHeader(String key, String value) {
		response.setHeader(key, value);
	}

	@Override
	public void setContentType(String arg) {
		response.setContentType(arg);
	}

	@Override
	public void sendRedirect(String arg) {
		
		try {
			response.sendRedirect(arg);
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	@Override
	public String getRequestUrl() {
		String result;
		Object obj = request.getAttribute("jakarta.servlet.forward.request_uri");
		if(null == obj){
			result = request.getServletPath() + "?" + request.getQueryString();
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

	@Override
	public OperationResult<IAccount> getAccount() {
		
		OperationResult<IAccount> result = null;
		try {
			OperationResult<IUser> userResult = getLoggedInUser();
			if(userResult.hasError()){
				result = userResult.toErrorResult();
			}
			
			if(null == result){
				result = userResult.getValue().getAccount(getCurrentProduct().getId(), accountId, true); 
			}
		} catch(Exception ex){
			UUID errorTicket = logger.error(ex, "getAccount : Unexpected error occured");
			result = new OperationResult<>(ErrorCode.GeneralError, errorTicket);
		}
		return result;
	} 
}