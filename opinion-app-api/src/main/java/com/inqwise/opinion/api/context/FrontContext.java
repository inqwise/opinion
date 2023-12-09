package com.inqwise.opinion.api.context;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restexpress.Request;
import org.restexpress.Response;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.NetworkHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.IPostmasterObject;
import com.inqwise.opinion.opinion.facade.front.AccountsEntry;
import com.inqwise.opinion.opinion.facade.front.ChargesEntry;
import com.inqwise.opinion.opinion.facade.front.CintEntry;
import com.inqwise.opinion.opinion.facade.front.CollectorsEntry;
import com.inqwise.opinion.opinion.facade.front.ControlsEntry;
import com.inqwise.opinion.opinion.facade.front.Entry;
import com.inqwise.opinion.opinion.facade.front.InvitesEntry;
import com.inqwise.opinion.opinion.facade.front.InvoicesEntry;
import com.inqwise.opinion.opinion.facade.front.MessagesEntry;
import com.inqwise.opinion.opinion.facade.front.OpinionsEntry;
import com.inqwise.opinion.opinion.facade.front.PaymentsEntry;
import com.inqwise.opinion.opinion.facade.front.ProductsEntry;
import com.inqwise.opinion.opinion.facade.front.ResponsesEntry;
import com.inqwise.opinion.opinion.facade.front.ThemesEntry;
import com.inqwise.opinion.opinion.facade.front.UsersEntry;
import com.inqwise.opinion.opinion.http.HttpClientSession;

public class FrontContext implements IPostmasterContext {

	static ApplicationLog logger = ApplicationLog.getLogger(FrontContext.class);
	
	private Request request;
	private Response response;
	private Cookies cookies;
	private Long accountId;
	
	OperationResult<IUser> _userResult = null;
	
	OperationResult<Long> _userIdResult = null;
	
	Entry _opinions;
	public Entry getOpinions() {
		if(null == _opinions){
			_opinions = new OpinionsEntry(this);
		}
		
		return _opinions;
	}
	
	AccountsEntry _accounts;
	
	public AccountsEntry accounts() {
		if(_accounts == null){
			_accounts = new AccountsEntry(this);
		}
		
		return _accounts;
	}

	private CollectorsEntry _collectors;
	public CollectorsEntry collectors(){
		if(null == _collectors){
			_collectors = new CollectorsEntry(this);
		}
		
		return _collectors;
	}
	
	private CintEntry _cint;
	public CintEntry cint(){
		if(null == _cint){
			_cint = new CintEntry(this);
		}
		
		return _cint;
	}
	
	private UsersEntry _users;
	public UsersEntry users(){
		if(null == _users){
			_users = new UsersEntry(this);
		}
		
		return _users;
	}
	
	private PaymentsEntry _payments;
	public PaymentsEntry getPayments(){
		if(null == _payments){
			_payments = new PaymentsEntry(this);
		}
		
		return _payments;
	}
	
	private InvoicesEntry _invoices;
	public InvoicesEntry getInvoices(){
		if(null == _invoices){
			_invoices = new InvoicesEntry(this);
		}
		
		return _invoices;
	}
	
	private ProductsEntry _products ;
	
	public ProductsEntry getProducts() {
		if(null == _products){
			_products = new ProductsEntry(this);
		}
		
		return _products;
	}
	
	private ChargesEntry _charges;
	public ChargesEntry getCharges(){
		if(null == _charges){
			_charges = new ChargesEntry(this);
		}
		
		return _charges;
	}
	
	private Entry _controls;
	public Entry getControls(){
		if(null == _controls){
			_controls = new ControlsEntry(this);
		}
		
		return _controls;
	}
	
	private Entry _responses;
	public Entry getResponses(){
		if(null == _responses){
			_responses = new ResponsesEntry(this);
		}
		
		return _responses;
	}
	
	private Entry _themes;
	public Entry getThemes(){
		if(null == _themes){
			_themes = new ThemesEntry(this);
		}
		
		return _themes;
	}
	
	private Entry _messages;
	public Entry getMessages(){
		if(null == _messages){
			_messages = new MessagesEntry(this);
		}
		
		return _messages;
	}
	
	private Entry _invites;
	public Entry getInvites(){
		if(null == _invites){
			_invites = new InvitesEntry(this);
		}
		
		return _invites;
	}
	
	public FrontContext(Request request, Response response, Cookies cookies, Long accountId) throws MalformedURLException {
		this.request = request;
		this.response = response;
		this.cookies = cookies;
		this.accountId = accountId;
		//new URL(request.getUrl());
	}
	
	public void invokeMethods(JSONObject source, JSONArray methodNames,
			String methodPath, JSONObject output) throws JSONException {
		
		invokeMethods(this, source, methodNames, methodPath, output);
	}
	
	private void invokeMethods(Object actualObject, JSONObject source, JSONArray methodNames, String methodPath, JSONObject output) throws JSONException {
		try{
			for (int i = 0; i < methodNames.length(); i++) {
				String methodName = methodNames.optString(i);
				JSONObject methodObject = source.optJSONObject(methodName);
				BaseOperationResult result = null;
				
				Method m = identifyMethod(actualObject.getClass().getMethods(), methodName);
				try{
					if(null == m){
						UUID warnId = UUID.randomUUID();
						logger.warn("invokeMethods() : Requested method not found. MethodName: '%s', MethodPath: '%s', Id: '%s'. Signature of the method must be: methodName(JSONObject) or methodName()", methodName, methodPath, warnId.toString()); 
						result = new BaseOperationResult(ErrorCode.MethodNotFound, warnId, "Method not found");
					}
					
					if(null == result){
						if(null == accountId){
							accountId = JSONHelper.optLong(methodObject, IAccount.JsonNames.ACCOUNT_ID);
						}
						
						Object returnValue;
						if(m.getParameterTypes().length == 0){
							returnValue = m.invoke(actualObject);
						} else {
							returnValue = m.invoke(actualObject, methodObject);
						}
						
						if(returnValue instanceof IPostmasterObject){
							actualObject = returnValue;
							JSONObject currentOutput = new JSONObject();
							output.put(methodName, currentOutput);
							String currentMethodPath = methodPath == null ? methodName : methodPath + "." + methodName;
							invokeMethods(actualObject, methodObject, methodObject.names(), currentMethodPath, currentOutput);
						} else if(m.getReturnType() != void.class){
							output.put(methodName, returnValue);
						} else {
							output.put(methodName, "done");
						}
					}
				} catch(Throwable t){
					UUID errorId = logger.error(t, "invokeMethods() : Unexpected error occured.");
					result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
				}
				
				if(null != result){
					output.put(methodName, result.toJson());
				}
			}
		}
		catch(Throwable t){
			UUID errorId = logger.error(t, "invokeMethods() : Unexpected error occured in main loop.");
			output = BaseOperationResult.toJsonGeneralError(t, errorId);
		}
		
	}
	
	private Method identifyMethod(Method[] methods, String methodName){
		
		for(Method method : methods){
			Class<?>[] parameterTypes = method.getParameterTypes();
			if((method.getName().equalsIgnoreCase(methodName) || method.getName().equalsIgnoreCase("get" + methodName)) && (parameterTypes.length == 0 || (parameterTypes.length == 1 && parameterTypes[0] == JSONObject.class))){
				return method;
			} 
		}
		return null;
	}


	@Override
	public String getClientIp() {
		return NetworkHelper.identifyClientIp(request.getRemoteAddress().getHostString(), request.getHeader(NetworkHelper.X_FORWARDED_FOR_HEADER_NAME));
	}

	private HttpClientSession _session;
	@Override
	public HttpClientSession getSession() throws IOException {
		
		if(null == _session){
			_session = ClientSessionDescryptor2.getSession(cookies, getClientIp());
		}
		return _session;
	}

	@Override
	public URL getAbsoluteURL() throws MalformedURLException {
		throw new NotImplementedException("");
		//return getAbsoluteURL(null);
	}
	
	@Override
	public URL getAbsoluteURL(boolean secure) throws MalformedURLException {
		throw new NotImplementedException("");
		//return getAbsoluteURL(secure);
	}
	
	@Override
	public URL getApplicationURL() throws MalformedURLException {
		throw new NotImplementedException("");
		//return getApplicationURL(null);
	}
	
	@Override
	public URL getApplicationURL(boolean secure) throws MalformedURLException {
		throw new NotImplementedException("");
		//return getApplicationURL(secure);
	}
	
	@Override
	public void addUserIdToSession(Long userId, int productId, String clientIp, boolean untilEndSession) throws IOException {
		//TODO:
		//ClientSessionDescryptor2.addUserIdToSession(userId, productId, clientIp, untilEndSession, cookies);
	}

	@Override
	public void addSessionIdToSession(UUID sessionId, boolean isPersist) throws UnsupportedEncodingException {
		//TODO:
		//ClientSessionDescryptor2.addSessionIdToSession(sessionId, isPersist, cookies);
	}

	@Override
	public OperationResult<IUser> getLoggedInUser() throws IOException, NullPointerException, ExecutionException {
		if(null == _userResult){
			
			HttpClientSession session = getSession();
			if(session.hasError()){
				_userResult = session.toErrorResult();
			} else {
				_userResult = UsersManager.getUser(session.getUserId(), session.getLoginProductId());
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

	public OperationResult<IProduct> getLoggedInProduct() throws IOException {
		OperationResult<IProduct> result = null;
		
		HttpClientSession session = getSession();
		if(session.hasError()){
			result = session.toErrorResult();
		}
		
		if(null == result){
			result = ProductsManager.getProductById(session.getLoginProductId());
		} 
		return result;
	}
	
	@Override
	public List<IAccount> getLoggedInUserAllowedAccounts() throws IOException, NullPointerException, ExecutionException {
		IUser user = null;
		
		OperationResult<IUser> userResult = getLoggedInUser();
		if(userResult.hasError()){
			throw new Error("User not authenticated. " + userResult.toString());
		} else {
			user = userResult.getValue();
		}
		
		List<IAccount> accounts = null;
		if(null != user){
			OperationResult<List<IAccount>> accountsResult = user.getAccounts(getSession().getLoginProductId());
			if(accountsResult.hasError()){
				logger.error("getLoggedInUserAllowedAccounts : Failed to get accounts for userId '%s'", user.getUserId());
			} else {
				accounts = accountsResult.getValue();
			}
		}
		
		return accounts;
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
	public IAccount getDefaultAccount() throws IOException, NullPointerException, ExecutionException {
		List<IAccount> accounts = getLoggedInUserAllowedAccounts();
		return getDefaultAccount(accounts);
	}

	private IAccount getDefaultAccount(List<IAccount> accounts)
			throws IOException {
		IAccount firstAccount = accounts.get(0);
		for (IAccount account : accounts) {
			if(null != account.getOwnerId() && 0 == Long.compare(account.getOwnerId(), getUserId().getValue())){
				return account;
			}
		}
		
		return firstAccount;
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
	public boolean IsSignedIn() throws NullPointerException, ExecutionException {
		try {
			return !getLoggedInUser().hasError();
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public void setHeader(String key, String value) {
		response.addHeader(key, value);
	}

	@Override
	public void setContentType(String contentType) {
		response.setContentType(contentType);
	}

	@Override
	public void sendRedirect(String string) {
		throw new NotImplementedException("");
	}

	public URL getServerUrl() throws MalformedURLException {
		return null;
	}

	public URL getServerUrl(boolean secure) throws MalformedURLException {
		return null;
	}

	@Override
	public String getRequestUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationResult<IAccount> getAccount(){
		OperationResult<IAccount> result = null;
		try {
			OperationResult<IUser> userResult = getLoggedInUser();
			if(userResult.hasError()){
				result = userResult.toErrorResult();
			}
			
			if(null == result){
				result = userResult.getValue().getAccount(getSession().getLoginProductId(), accountId, true); 
			}
		} catch(Exception ex){
			UUID errorTicket = logger.error(ex, "getAccount : Unexpected error occured");
			result = new OperationResult<>(ErrorCode.GeneralError, errorTicket);
		}
		return result;
	}
}
