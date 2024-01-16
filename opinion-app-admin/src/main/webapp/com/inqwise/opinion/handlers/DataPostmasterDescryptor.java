package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.ISession;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;

public class DataPostmasterDescryptor extends DataPostmasterDescryptorBase {

	private PrintWriter out = null;
	public DataPostmasterDescryptor(PrintWriter out, HttpServletRequest request,
			HttpServletResponse response, ServletContext context) {
		super(request, response, context);
		
		this.out = out;
	}

	PaymentsEntry _payments;

	public PaymentsEntry getPayments() {
		if(_payments == null){
			synchronized (PaymentsEntry.class) {
				if(_payments == null){
					_payments = new PaymentsEntry(this);
				}
			}
		}
		return _payments;
	}
	
	UsersEntry _users;

	public UsersEntry getUsers() {
		if(_users == null){
			_users = new UsersEntry(this);
		}
		return _users;
	}
	
	ProductsEntry _products;

	public ProductsEntry getProducts() {
		if(_products == null){
			_products = new ProductsEntry(this);
		}
		return _products;
	}
	
	AccountsEntry _accounts;

	public AccountsEntry getAccounts() {
		if(_accounts == null){
			_accounts = new AccountsEntry(this);
		}
		return _accounts;
	}
	
	ControlEntry _control;
	public ControlEntry getControl(){
		if(_control == null){
			_control = new ControlEntry(this);
		}
		return _control;
	}
	
	CollectorsEntry _collectors;
	public CollectorsEntry getCollectors(){
		if(null == _collectors){
			_collectors = new CollectorsEntry(this);
		}
		return _collectors;
	}
	
	OpinionsEntry _opinions;
	public OpinionsEntry getOpinions(){
		if(_opinions == null){
			_opinions = new OpinionsEntry(this);
		}
		return _opinions;
	}
	
	private CintEntry _cint;
	public CintEntry cint(){
		if(null == _cint){
			_cint = new CintEntry(this);
		}
		
		return _cint;
	}
	
	private InvoicesEntry _invoices;
	public InvoicesEntry getInvoices(){
		if(null == _invoices){
			_invoices = new InvoicesEntry(this);
		}
		
		return _invoices;
	}
	
	private ChargesEntry _charges;
	public ChargesEntry getCharges(){
		if(null == _charges){
			_charges = new ChargesEntry(this);
		}
		
		return _charges;
	}
	
	private MessagesEntry _messages;
	public MessagesEntry getMessages(){
		if(null == _messages){
			_messages = new MessagesEntry(this);
		}
		
		return _messages;
	}
	
	private BlogsEntry _blogs;
	public BlogsEntry getBlogs(){
		if(null == _blogs){
			_blogs = new BlogsEntry(this);
		}
		
		return _blogs;
	}
	
	private KnowledgeBaseEntry _knowledgeBase;
	public KnowledgeBaseEntry getKB() {
		if(null == _knowledgeBase) {
			_knowledgeBase = new KnowledgeBaseEntry(this);
		}
		return _knowledgeBase;
	}
	
	private void invokeMethods(Object actualObject, JSONObject source, JSONArray methodNames, String methodPath, JSONObject output) throws JSONException
	{
		for (int i = 0; i < methodNames.length(); i++) {
			BaseOperationResult result = null;
			String methodName = methodNames.optString(i);
			JSONObject methodObject = source.optJSONObject(methodName);
			
			Method m = identifyMethod(actualObject.getClass().getMethods(), methodName);
			try{
				if(null == m){
					UUID warnId = UUID.randomUUID();
					logger.warn("invokeMethods() : Requested method not found. MethodName: '%s', MethodPath: '%s', Id: '%s'. Signature of the method must be: methodName(JSONObject) or methodName()", methodName, methodPath, warnId.toString()); 
					result = new BaseOperationResult(ErrorCode.MethodNotFound, warnId, "Method not found");
				}
				
				if(null == result){
					Object returnValue;
					if(m.getParameterTypes().length == 0){
						returnValue = m.invoke(actualObject);
					} else {
						returnValue = m.invoke(actualObject, source.get(methodName));
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
	
	private Method identifyMethod(Method[] methods, String methodName){
	
		for(Method method : methods){
			Class<?>[] parameterTypes = method.getParameterTypes();
			if((method.getName().equalsIgnoreCase(methodName) || method.getName().equalsIgnoreCase("get" + methodName)) && (parameterTypes.length == 0 || (parameterTypes.length == 1 && parameterTypes[0] == JSONObject.class))){
				return method;
			} 
		}
		return null;
	}
	
	public void invokeMethods(JSONObject source, JSONArray methodNames,
			String methodPath, JSONObject output) throws JSONException {
		invokeMethods(this, source, methodNames, methodPath, output);
		
	}
	
	public JSONObject login(JSONObject input) throws IOException {
		
		JSONObject output = new JSONObject();
	
		try {
			String username = input.getString("username");
			String password = input.getString("password");
			String newPassword = JSONHelper.optString(input, "newPassword", null, "");
			String clientIp = getClientIp();
			boolean isPersist = input.getBoolean("isPersist");
			login(output, username, password, newPassword, clientIp, isPersist);
			
		} catch (Throwable t) {
			logger.error(t, "login() : unexpected error occured.");
	
			try {
				output.put("error", ErrorCode.GeneralError);
			} catch (JSONException el) {
				logger.error(el, "login() : put to json failed.");
			}
		}
		
		return output;
	}

	private OperationResult<Long> _userId = null;
	
	private BaseOperationResult login(JSONObject functionOutput, String username, String password,
			String newPassword, String clientIp, boolean isPersist) {
		
		BaseOperationResult result = null;
		
		try {
			
			IProduct product = ProductsManager.getCurrentProduct();
			if(null == product){
				result = new BaseOperationResult(ErrorCode.NoResults);
			}
			
			ISession session = null;
			if(null == result){
				OperationResult<ISession> loginResult = UsersManager.login(username, password, newPassword,
						clientIp, ProductsManager.getCurrentProduct().getId(), product.getId(), isPersist);
				
				if (loginResult.hasError()) {
					result = loginResult;
				} else {
					session = loginResult.getValue();
				}
			}
			
			if (null == result) {
				functionOutput.put("sessionId", session.getSessionId().toString());
				
				// Set session cookies
				addUserIdToSession(session.getUser().getUserId(), product.getId(), clientIp, isPersist);
				addSessionIdToSession(session.getSessionId(), isPersist);
			} else {
				switch (result.getError()) {
				case InvalidUsernameOrPassword:
					// Invalid username or password
					functionOutput.put("error", result.getError());
					break;
				case PasswordExpiry:
					// Password expire
					functionOutput.put("error", result.getError());
					break;
				case NotAllowedForProduct:
					// No accounts
					functionOutput.put("error", result.getError());
					break;
				default:
					// Login temporary is unavailable
					logger.error("LoginException :" + result.toString());
					functionOutput.put("error", ErrorCode.GeneralError);
					break;
				}
			}
			
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "login() : Unexpected error occured.");
			result = new OperationResult<ISession>(ErrorCode.GeneralError, errorId, t.toString());
			
			// Login temporary is unavailable
			try {
				functionOutput.put("error", ErrorCode.GeneralError);
			} catch (JSONException e) {
				logger.error(e, "login() : json put failed.");
			}
		}
		
		return result;
	}

}
