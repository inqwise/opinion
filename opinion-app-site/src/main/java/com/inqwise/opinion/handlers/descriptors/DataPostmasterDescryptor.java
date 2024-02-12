package com.inqwise.opinion.handlers.descriptors;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.automation.EventsServiceClient;
import com.inqwise.opinion.automation.common.events.LoginEventArgs;
import com.inqwise.opinion.cms.common.blog.Blogs;
import com.inqwise.opinion.cms.common.blog.ICommentRequest;
import com.inqwise.opinion.cms.common.blog.IPost;
import com.inqwise.opinion.cms.common.faq.IFaq;
import com.inqwise.opinion.cms.managers.BlogManager;
import com.inqwise.opinion.cms.managers.FaqsManager;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.facade.front.AccountsEntry;
import com.inqwise.opinion.facade.front.ChargesEntry;
import com.inqwise.opinion.facade.front.CintEntry;
import com.inqwise.opinion.facade.front.CollectorsEntry;
import com.inqwise.opinion.facade.front.ControlsEntry;
import com.inqwise.opinion.facade.front.Entry;
import com.inqwise.opinion.facade.front.InvoicesEntry;
import com.inqwise.opinion.facade.front.MessagesEntry;
import com.inqwise.opinion.facade.front.OpinionsEntry;
import com.inqwise.opinion.facade.front.PaymentsEntry;
import com.inqwise.opinion.facade.front.ProductsEntry;
import com.inqwise.opinion.facade.front.ResponsesEntry;
import com.inqwise.opinion.facade.front.ThemesEntry;
import com.inqwise.opinion.facade.front.UsersEntry;
import com.inqwise.opinion.http.HttpClientSession;
import com.inqwise.opinion.infrastructure.systemFramework.EmailProvider;
import com.inqwise.opinion.infrastructure.systemFramework.GeoIpManager;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.infrastructure.systemFramework.PasswordGenerator;
import com.inqwise.opinion.infrastructure.systemFramework.cryptography.StringEncrypter;
import com.inqwise.opinion.library.common.IGateway;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.ISession;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.emails.IContactUsRequestEmailData;
import com.inqwise.opinion.library.common.emails.ICustomPlanRequestEmailData;
import com.inqwise.opinion.library.common.emails.IPasswordChangedEmailData;
import com.inqwise.opinion.library.common.emails.IReportBugEmailData;
import com.inqwise.opinion.library.common.emails.IResetPasswordEmailData;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.common.users.IRegisterDetails;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.common.users.LoginProvider;
import com.inqwise.opinion.library.managers.EmailsManager;
import com.inqwise.opinion.library.managers.GatewaysManager;
import com.inqwise.opinion.library.managers.ParametersManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.systemFramework.TimeZones;

public class DataPostmasterDescryptor extends DataPostmasterDescryptorBase {

	protected static final String FREE_PACKAGE_REGISTER_PAGE_NAME = "/register";
	final DateFormat resetConfirmationCodeDateFormat = new SimpleDateFormat("yy-MM-dd");

	public DataPostmasterDescryptor(PrintWriter out, HttpServletRequest request, HttpServletResponse response,
			ServletContext context) {
		super(request, response, context);

	}

	static SimpleDateFormat fixedformat = new SimpleDateFormat("MMMM dd, yyyy");
	static Format mdyhmsFormatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");

	static EmailProvider emailProvider;

	Entry _opinions;

	public Entry getOpinions() {
		if (null == _opinions) {
			_opinions = new OpinionsEntry(this);
		}

		return _opinions;
	}

	AccountsEntry _accounts;

	public AccountsEntry accounts() {
		if (_accounts == null) {
			_accounts = new AccountsEntry(this);
		}

		return _accounts;
	}

	private CollectorsEntry _collectors;

	public CollectorsEntry collectors() {
		if (null == _collectors) {
			_collectors = new CollectorsEntry(this);
		}

		return _collectors;
	}

	private CintEntry _cint;

	public CintEntry cint() {
		if (null == _cint) {
			_cint = new CintEntry(this);
		}

		return _cint;
	}

	private UsersEntry _users;

	public UsersEntry users() {
		if (null == _users) {
			_users = new UsersEntry(this);
		}

		return _users;
	}

	private PaymentsEntry _payments;

	public PaymentsEntry getPayments() {
		if (null == _payments) {
			_payments = new PaymentsEntry(this);
		}

		return _payments;
	}

	private InvoicesEntry _invoices;

	public InvoicesEntry getInvoices() {
		if (null == _invoices) {
			_invoices = new InvoicesEntry(this);
		}

		return _invoices;
	}

	private ProductsEntry _products;

	public ProductsEntry getProducts() {
		if (null == _products) {
			_products = new ProductsEntry(this);
		}

		return _products;
	}

	private ChargesEntry _charges;

	public ChargesEntry getCharges() {
		if (null == _charges) {
			_charges = new ChargesEntry(this);
		}

		return _charges;
	}

	private Entry _controls;

	public Entry getControls() {
		if (null == _controls) {
			_controls = new ControlsEntry(this);
		}

		return _controls;
	}

	private Entry _responses;

	public Entry getResponses() {
		if (null == _responses) {
			_responses = new ResponsesEntry(this);
		}

		return _responses;
	}

	private Entry _themes;

	public Entry getThemes() {
		if (null == _themes) {
			_themes = new ThemesEntry(this);
		}

		return _themes;
	}

	private Entry _messages;

	public Entry getMessages() {
		if (null == _messages) {
			_messages = new MessagesEntry(this);
		}

		return _messages;
	}

	public void init() {

	}

	private void invokeMethods(Object actualObject, JSONObject source, JSONArray methodNames, String methodPath,
			JSONObject output) throws JSONException {
		logger.debug("invokeMethods(%s,%s,%s,%s,%s)");
		try {
			for (int i = 0; i < methodNames.length(); i++) {
				String methodName = methodNames.optString(i);
				JSONObject methodObject = source.optJSONObject(methodName);
				BaseOperationResult result = null;

				Method m = identifyMethod(actualObject.getClass().getMethods(), methodName);
				try {
					if (null == m) {
						UUID warnId = UUID.randomUUID();
						logger.warn(
								"invokeMethods() : Requested method not found. MethodName: '%s', MethodPath: '%s', Id: '%s'. Signature of the method must be: methodName(JSONObject) or methodName()",
								methodName, methodPath, warnId.toString());
						result = new BaseOperationResult(ErrorCode.MethodNotFound, warnId, "Method not found");
					}

					if (null == result) {
						Object returnValue;
						if (null == accountId) {
							accountId = JSONHelper.optLong(methodObject, IAccount.JsonNames.ACCOUNT_ID);
						}
						if (m.getParameterTypes().length == 0) {
							returnValue = m.invoke(actualObject);
						} else {
							returnValue = m.invoke(actualObject, methodObject);
						}

						if (returnValue instanceof IPostmasterObject) {
							actualObject = returnValue;
							JSONObject currentOutput = new JSONObject();
							output.put(methodName, currentOutput);
							String currentMethodPath = methodPath == null ? methodName : methodPath + "." + methodName;
							invokeMethods(actualObject, methodObject, methodObject.names(), currentMethodPath,
									currentOutput);
						} else if (m.getReturnType() != void.class) {
							output.put(methodName, returnValue);
						} else {
							output.put(methodName, "done");
						}
					}
				} catch (Throwable t) {
					UUID errorId = logger.error(t,
							"invokeMethods() : Unexpected error occured. methodName: '%s', methodObject: '%s', session: '%s'",
							methodName, methodObject, getSession().toJson());
					result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
				}

				if (null != result) {
					output.put(methodName, result.toJson());
				}
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "invokeMethods() : Unexpected error occured in main loop.");
			output = BaseOperationResult.toJsonGeneralError(t, errorId);
		}
	}

	private Method identifyMethod(Method[] methods, String methodName) {

		for (Method method : methods) {
			Class<?>[] parameterTypes = method.getParameterTypes();
			if ((method.getName().equalsIgnoreCase(methodName) || method.getName().equalsIgnoreCase("get" + methodName))
					&& (parameterTypes.length == 0
							|| (parameterTypes.length == 1 && parameterTypes[0] == JSONObject.class))) {
				return method;
			}
		}
		return null;
	}

	public JSONObject login(JSONObject input) throws IOException, JSONException {

		JSONObject output = new JSONObject();
		BaseOperationResult result = null;

		String clientIp = getClientIp();
		boolean isPersist = input.getBoolean("isPersist");

		IProduct product = ProductsManager.getCurrentProduct();
		if (null == product) {
			result = new BaseOperationResult(ErrorCode.NoResults);
		}

		if (null == result) {
			String email = input.getString("email");
			String password = input.getString("password");
			String newPassword = StringUtils.trimToNull(JSONHelper.optString(input, "newPassword"));
			login(output, email, password, newPassword, clientIp, isPersist, product);
		}

		if (null != result) {
			output = result.toJson();
		}

		return output;
	}

	private BaseOperationResult login(JSONObject functionOutput, String username, String password, String newPassword,
			String clientIp, boolean untilEndSession, IProduct product) {

		BaseOperationResult result = null;

		try {

			ISession session = null;
			if (null == result) {
				int productId = ProductsManager.getCurrentProduct().getId();
				OperationResult<ISession> loginResult = UsersManager.login(username, password, newPassword, clientIp,
						productId, product.getId(), untilEndSession);

				if (loginResult.hasError()) {
					result = loginResult;
				} else {
					session = loginResult.getValue();

					// Set session cookies
					addUserIdToSession(session.getUser().getUserId(), productId, session.getClientIp(),
							untilEndSession);
					addSessionIdToSession(session.getSessionId(), untilEndSession);
				}
			}

			if (null == result) {
				functionOutput.put("sessionId", session.getSessionId().toString());
				result = BaseOperationResult.Ok;
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
					// Not allowed
					functionOutput.put("error", result.getError());
					break;
				case AccountDisabled:
					// Account disabled
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

	private Long getGatewayId() {
		Long gatewayId = null;
		try {
			for (Cookie cookie : getRequest().getCookies()) {
				if (cookie.getName().equals("gid")) {
					gatewayId = Long.valueOf(cookie.getValue());
					break;
				}
			}
		} catch (Exception ex) {
			logger.warn("login() : json put failed.");
		}

		return gatewayId;
	}

	public JSONObject registerAndLogin(JSONObject input) throws IOException {
		JSONObject output = new JSONObject();
		BaseOperationResult result = null;

		try {

			final Long gatewayId = getGatewayId();
			final String email = input.getString("email");
			final String password = input.getString("password");
			final String displayName = JSONHelper.optString(input, "displayName", null, "");
			final Boolean isAcceptToReceiveNewsletters = input.getBoolean("isAcceptToReceiveNewsletters");
			final String clientIp = getClientIp();
			int servicePackageId = JSONHelper.optInt(input, "planId", 1 /* Free */);
			int countOfMonths = JSONHelper.optInt(input, "countOfMonths", 1);
			String inviteId = JSONHelper.optStringTrim(input, "inviteId");
			IRegisterDetails registerDetails = null;

			IProduct product = ProductsManager.getCurrentProduct();
			if (null == product) {
				result = new BaseOperationResult(ErrorCode.NoResults);
			}

			if (null == result) {
				OperationResult<IRegisterDetails> registerResult = register(output, displayName, email, password,
						isAcceptToReceiveNewsletters, gatewayId, servicePackageId, countOfMonths, inviteId);
				if (registerResult.hasError()) {
					result = registerResult;
				} else {
					registerDetails = registerResult.getValue();
				}
			}

			// Login
			if (null == result) {
				BaseOperationResult loginResult = login(output, email, password, null, clientIp, true, product);
				if (loginResult.hasError()) {
					result = loginResult.toErrorResult();
				}
			}

			// create redirect path
			if (null == result) {
				output.put("redirectUrl",
						identifyPostRegisterRedirectUrl(registerDetails.getCharge(), registerDetails.getUser()));
			} else {
				output = result.toJson();
			}

		} catch (Throwable t) {
			logger.error(t, "registerAndLogin() : unexpected error occured.");

			try {
				output.put("error", ErrorCode.GeneralError);
			} catch (JSONException el) {
				logger.error(el, "registerAndLogin() : put to json failed.");
			}
		}

		return output;
	}

	private static String identifyPostRegisterRedirectUrl(ICharge charge, IUser user) {
		String redirectUrl;
		IGateway gateway = null;

		if (null == charge) {
			if (null != user.getGatewayId()) {
				OperationResult<IGateway> gatewayResult = GatewaysManager.get(user.getGatewayId(), true);

				if (gatewayResult.hasValue()) {
					gateway = gatewayResult.getValue();
				}

			}

			if (null == gateway || null == gateway.getFirstLoginLandingPage()) {
				redirectUrl = "dashboard";
			} else {
				redirectUrl = gateway.getFirstLoginLandingPage();
			}
		} else {
			redirectUrl = "make-payment?return_url=dashboard&charges=" + charge.getId();
		}
		return redirectUrl;
	}

	public JSONObject register(JSONObject input) throws IOException {

		JSONObject output = new JSONObject();
		try {

			String displayName = JSONHelper.optStringTrim(input, "displayName");
			final String email = input.getString("email");
			final String password = input.getString("password");
			final Long gatewayId = getGatewayId();
			Boolean isAcceptToReceiveNewsletters = input.getBoolean("isAcceptToReceiveNewsletters");
			int servicePackageId = JSONHelper.optInt(input, "planId", 1 /* Free */);
			int countOfMonths = JSONHelper.optInt(input, "countOfMonths", 1);
			String inviteId = JSONHelper.optStringTrim(input, "inviteId");
			OperationResult<IRegisterDetails> result = register(output, displayName, email, password,
					isAcceptToReceiveNewsletters, gatewayId, servicePackageId, countOfMonths, inviteId);

			if (result.hasError()) {
				output = result.toJson();
			}

		} catch (Throwable t) {
			logger.error(t, "register() : Unexpected error occured");
			// Service temporary is unavailable
			try {
				output.put("error", ErrorCode.GeneralError);
			} catch (JSONException el) {
				logger.error("JSONException :" + el.getMessage());
			}

		}

		return output;
	}

	private OperationResult<IRegisterDetails> register(JSONObject functionOutput, String displayName,
			final String email, final String password, Boolean isAcceptToReceiveNewsletters, Long gatewayId,
			int servicePackageId, int countOfMonths, String inviteId) throws JSONException {
		OperationResult<IRegisterDetails> result = null;

		IProduct product = null;
		if (null == result) {
			product = ProductsManager.getCurrentProduct();
			if (null == product) {
				result = new OperationResult<IRegisterDetails>(ErrorCode.NoResults);
			}
		}

		if (null == result) {

			String clientIp = getClientIp();
			result = UsersManager.register(email, password, email, isAcceptToReceiveNewsletters, clientIp, displayName,
					product.getId(), product, gatewayId, servicePackageId, countOfMonths, inviteId);
		}

		return result;
	}

	public JSONObject getMonthNames(JSONObject input) throws JSONException {
		String culture = JSONHelper.optString(input, "culture", "int_US");
		Boolean isShortNames = JSONHelper.optBoolean(input, "isShort", true);
		Locale locale = new Locale(culture);
		String[] names;
		if (isShortNames) {
			names = DateFormatSymbols.getInstance(locale).getShortMonths();
		} else {
			names = DateFormatSymbols.getInstance(locale).getMonths();
		}

		return new JSONObject().put("list", names);
	}

	public JSONObject getTimeZones(JSONObject input) throws JSONException {
		String culture = JSONHelper.optString(input, "culture", "int_US");
		Locale locale = new Locale(culture);
		JSONArray ja = new JSONArray();
		for (TimeZone timeZone : TimeZones.getTimeZones()) {
			String value = timeZone.getID();
			String text = timeZone.getDisplayName(locale);
			ja.put(new JSONObject().put("value", value).put("text", text));
		}

		return new JSONObject().put("list", ja);
	}

	public JSONObject validateUser(JSONObject input) throws IOException {

		BaseOperationResult result = null;
		JSONObject returnObject = new JSONObject();

		try {
			HttpClientSession session = getSession();
			if (null == session || session.hasError()) {
				returnObject.put("error", ErrorCode.UniqueUserIdIncorrect);
				result = session;
			}

			IProduct product = null;
			if (null == result) {
				OperationResult<IProduct> productResult = ProductsManager.getProductById(session.getLoginProductId());
				if (productResult.hasError()) {
					result = productResult.toErrorResult();
				} else {
					product = productResult.getValue();
				}
			}

			// User
			IUser user = null;
			if (null == result) {
				OperationResult<IUser> validateResult = UsersManager.getUser(session.getUserId(), product.getId());
				if (validateResult.hasError()) {
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
			if (null == result) {
				OperationResult<List<IAccount>> accountsResult = user.getAccounts(product.getId());
				if (accountsResult.hasError()) {
					result = accountsResult;
					returnObject = result.toJson();
				} else {
					accounts = accountsResult.getValue();
				}
			}

			if (null == result && accounts.size() == 0) {
				logger.warn("validateUser : user # '%s' without account", session.getUserId());
				result = new BaseOperationResult(ErrorCode.UserWithoutAccount);
				returnObject = result.toJson();
			}

			IAccount defaultAccount = null;
			IServicePackage servicePackage = null;
			if (null == result) {
				defaultAccount = user.getDefaultAccount(product.getId());
				servicePackage = product.getServicePackage(defaultAccount.getServicePackageId());
			}

			if (null == result) {

				returnObject.put("userName", user.getUserName());
				returnObject.put("displayName", user.getDisplayName());
				returnObject.put("email", user.getEmail());

				JSONObject servicePackageJo = new JSONObject();
				servicePackageJo.put("planId", servicePackage.getId());
				servicePackageJo.put("title", servicePackage.getName());
				servicePackageJo.put("isFree", servicePackage.getAmount() == 0);
				if (null == defaultAccount.getServicePackageExpiryDate()) {
					servicePackageJo.put("planExpirationDate", JSONObject.NULL);
				} else {
					servicePackageJo.put("planExpirationDate",
							JSONHelper.getDateFormat(defaultAccount.getServicePackageExpiryDate(), "MMM dd, yyyy"));
				}

				returnObject.put("plan", servicePackageJo);
				returnObject.put("accountId", defaultAccount.getId());

				if (accounts.size() > 1) {
					JSONArray accountsJa = new JSONArray();

					for (IAccount account : accounts) {
						JSONObject accountJo = new JSONObject();
						accountJo.put("id", account.getId());
						accountJo.put("name", account.getName());
						accountsJa.put(accountJo);
					}

					returnObject.put("accounts", accountsJa);
				}

			}

			HashMap<String, IVariableSet> variables = null;

			if (null == result) {
				String servicePackageKey = ParametersManager.getReferenceKey(EntityType.ServicePackage,
						defaultAccount.getServicePackageId());
				OperationResult<HashMap<String, IVariableSet>> variablesResult = ParametersManager.getVariables(
						defaultAccount.getId(), EntityType.Account, null, new String[] { servicePackageKey });
				if (variablesResult.hasError()) {
					result = variablesResult;
				} else {
					variables = variablesResult.getValue();
				}
			}

			if (null == result) {
				Set<String> keys = variables.keySet();
				JSONObject variablesJo = new JSONObject();
				for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					variablesJo.put(key, variables.get(key).getActual().getJsonValue());
				}
				returnObject.put("permissions", variablesJo);
			}

			if (null == result && JSONHelper.optBoolean(input, "isBeforeLogin", false)) {
				try {

					String clientIp = getClientIp();
					EventsServiceClient.getInstance()
							.fire(new LoginEventArgs(product.getId(), product.getId(), user.getUserName(),
									user.getUserId(), GeoIpManager.getInstance().getCountryName(clientIp),
									user.getEmail(), GeoIpManager.getInstance().getCountryCode(clientIp),
									session.getSessionId(), clientIp, true));
				} catch (Exception e) {
					logger.error(e, "failed to fireLoginEvent");
				}
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

	public JSONObject invokeMethods(JSONObject source, JSONArray methodNames, String methodPath) throws JSONException {

		JSONObject output = new JSONObject();
		invokeMethods(this, source, methodNames, methodPath, output);
		return output;
	}

	public JSONObject resetPassword(JSONObject input) {
		JSONObject output = new JSONObject();
		try {
			BaseOperationResult result = null;
			final String userName = JSONHelper.optStringTrim(input, "userName");

			if (null == userName) {
				result = new BaseOperationResult(ErrorCode.ArgumentMandatory);
			}

			IUser user = null;
			// Get user
			if (null == result) {
				OperationResult<IUser> userResult = UsersManager.getUser(userName, null, LoginProvider.Inqwise, null);
				if (userResult.hasError()) {
					result = userResult;
				} else {
					user = userResult.getValue();
				}
			}

			// get current Product details
			IProduct product = null;
			if (null == result) {

				product = ProductsManager.getCurrentProduct();
				if (null == product) {
					result = new BaseOperationResult(ErrorCode.NoResults);
				}
			}

			// Send reset password Email
			if (null == result) {
				sendResetPasswordEmail(user, product);
				output = BaseOperationResult.JsonOk;
			} else if (result.getError() == ErrorCode.UserNotExist) {
				output = BaseOperationResult.JsonOk;
			} else {
				output = result.toJson();
			}

		} catch (Throwable t) {
			UUID errorId = logger.error(t, "resetPassword() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId", errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t, "resetPassword() : Unable to put to Json object");
			}
		}

		return output;
	}

	public void sendResetPasswordEmail(final IUser user, final IProduct product) {
		Calendar expDate = Calendar.getInstance();
		expDate.add(Calendar.DAY_OF_MONTH, 1);

		/*
		 * Encryption The new encrypted value for confirmation reset code is include
		 * accountId + | + expDate, with pipe as seperator
		 */
		StringEncrypter desEncrypter = new StringEncrypter(passPhrase);
		final String encryptedValue = desEncrypter
				.encrypt(user.getUserId().toString() + "|" + resetConfirmationCodeDateFormat.format(expDate.getTime()));

		IResetPasswordEmailData data = new IResetPasswordEmailData() {

			public String getSupportEmail() {
				return product.getSupportEmail();
			}

			public String getFeedbackShortCaption() {
				return product.getFeedbackShortCaption();
			}

			public String getFeedbackCaption() {
				return product.getFeedbackCaption();
			}

			public String getEmail() {
				return user.getEmail();
			}

			public int getCurrentYear() {
				return Calendar.getInstance().get(Calendar.YEAR);
			}

			public String getApplicationUrl() {
				return ApplicationConfiguration.Opinion.getUrl();
			}

			public String getResetPasswordLink() {
				return ApplicationConfiguration.Opinion.getSecureUrl() + "/en-us/reset-password?reset_code="
						+ encryptedValue + "&secret=2";
			}

			public String getNoreplyEmail() {
				return product.getNoreplyEmail();
			}
		};

		EmailsManager.sendResetPasswordEmail(data);
	}

	public JSONObject confirmResetPassword(JSONObject input) throws JSONException {
		JSONObject output = new JSONObject();
		BaseOperationResult result = null;
		try {
			// Confirmation Reset Code -> Encripted accountId
			String confirmationResetCode = JSONHelper.optString(input, "passwordResetCode");
			String clientIp = getClientIp();

			/*
			 * Decryption
			 */
			StringEncrypter desEncrypter = new StringEncrypter(passPhrase);
			String decryptedValue = desEncrypter.decrypt(confirmationResetCode.replaceAll(" ", "+"));

			if (null == decryptedValue) {
				logger.warn("Reset code is wrong. '%s'", confirmationResetCode);
				result = new BaseOperationResult(ErrorCode.ArgumentWrong);
			}

			Long userId = null;
			if (null == result) {
				String[] values = decryptedValue.toString().split("\\|"); // This is regex mean pipe like |.

				userId = Long.valueOf(values[0]);

				Date now = new Date();
				Date expDate;

				expDate = resetConfirmationCodeDateFormat.parse(values[1]);

				// Validate if expiration date after then now
				if (!now.before(expDate)) {
					logger.warn("confirmationResetCode are expired for userId: '%s'", userId);
					result = new BaseOperationResult(ErrorCode.Expired);
				}
			}

			IUser user = null;
			if (null == result) {

				OperationResult<IUser> validateResult = UsersManager.getUser(null, userId, LoginProvider.Inqwise, null);
				if (validateResult.hasError()) {
					result = validateResult;
				} else {
					// Get user
					user = validateResult.getValue();
				}
			}

			// Get current Product
			IProduct product = null;
			if (null == result) {
				product = ProductsManager.getCurrentProduct();
				if (null == product) {
					result = new BaseOperationResult(ErrorCode.NoResults);
				}
			}

			String password = null;
			if (null == result) {
				// Generate new password
				password = new PasswordGenerator().getPassword();

				Calendar passwordExpirationDate = Calendar.getInstance();
				passwordExpirationDate.add(Calendar.DAY_OF_YEAR, -1);

				// Reset password
				BaseOperationResult resetPasswordResult = user.resetPassword(password, clientIp, product.getId(), null,
						passwordExpirationDate.getTime(), null);
				if (resetPasswordResult.hasError()) {
					result = resetPasswordResult;
				}
			}

			if (null == result) {
				sendConfirmResetPasswordEmail(product, user, password);
				// Output
				output.put("userName", user.getUserName());
			}

		} catch (ParseException e) {
			UUID errorId = logger.error(e, "confirmResetPassword() : Failed to parse date");
			result = new BaseOperationResult(ErrorCode.ArgumentWrong, errorId);
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "confirmResetPassword() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}

		if (null != result) {
			output = result.toJson();
		}

		return output;
	}

	public void sendConfirmResetPasswordEmail(final IProduct product, final IUser user, String password) {
		final String finalPassword = password;

		IPasswordChangedEmailData data = new IPasswordChangedEmailData() {

			public String getNoreplyEmail() {
				return product.getNoreplyEmail();
			}

			public String getSupportEmail() {
				return product.getSupportEmail();
			}

			public String getFeedbackShortCaption() {
				return product.getFeedbackShortCaption();
			}

			public String getFeedbackCaption() {
				return product.getFeedbackCaption();
			}

			public String getEmail() {
				return user.getEmail();
			}

			public int getCurrentYear() {
				return Calendar.getInstance().get(Calendar.YEAR);
			}

			public String getApplicationUrl() {
				return ApplicationConfiguration.Opinion.getUrl();
			}

			public String getPassword() {
				return finalPassword;
			}
		};

		EmailsManager.sendPasswordChangedEmail(data);
	}

	public JSONObject postComment(final JSONObject input) {
		JSONObject output = null;

		try {
			BaseOperationResult result = null;
			final Long userId = getSession().getUserId();

			ICommentRequest request = new ICommentRequest() {

				@Override
				public Long getPostId() {
					return input.optLong("postId");
				}

				@Override
				public Long getParentId() {
					return JSONHelper.optLong(input, "parentId");
				}

				@Override
				public String getContent() {
					return JSONHelper.optString(input, "comment");
				}

				@Override
				public String getAutorUrl() {
					return JSONHelper.optString(input, "websiteUrl", null, "");
				}

				@Override
				public String getAutorName() {
					return JSONHelper.optString(input, "name");
				}

				@Override
				public Long getAutorId() {
					return userId;
				}

				@Override
				public String getAutorEmail() {
					return JSONHelper.optString(input, "email");
				}

				@Override
				public Integer getBlogId() {
					return Blogs.OfficialInqwiseBlog.getId();
				}
			};

			BaseOperationResult insertResult = BlogManager.insertComment(request);
			if (insertResult.hasError()) {
				result = insertResult;
			}

			if (null == result) {
				output = BaseOperationResult.JsonOk;
			} else {
				output = insertResult.toJson();
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "insertComment() : Unexpected error occured.");
			try {
				output = new JSONObject();
				output.put("error", ErrorCode.GeneralError).put("errorId", errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t, "insertComment() : Unable to put to Json object");
			}
		}

		return output;
	}

	public JSONObject submitQuestion(final JSONObject input) throws JSONException {
		JSONObject output = null;

		try {
			BaseOperationResult result = null;
			HttpClientSession session = getSession();
			if (null == session || session.hasError()) {
				logger.warn("Client not signed in");
				result = new BaseOperationResult(ErrorCode.NotSignedIn);
			}

			if (null == result) {
				Long userId = session.getUserId();
				String question = JSONHelper.optString(input, "question", null, "");
				String name = JSONHelper.optString(input, "name", null, "");
				String email = JSONHelper.optString(input, "email", null, "");
				BaseOperationResult submitResult = FaqsManager.submitQuestion(question, name, email, userId);
				if (submitResult.hasError()) {
					result = submitResult;
				}
			}
			if (null == result) {
				output = BaseOperationResult.JsonOk;
			} else {
				output = result.toJson();
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "submitQuestion() : Unexpected error occured.");
			output = BaseOperationResult.toJsonGeneralError(t, errorId);
		}

		return output;
	}

	public JSONObject giveUsYourFeedback(final JSONObject input) throws JSONException {
		JSONObject output = null;

		try {
			BaseOperationResult result = null;
			HttpClientSession session = getSession();
			Long userId = null;
			if (!(null == session || session.hasError())) {
				userId = session.getUserId();
			}

			if (null == result) {
				String title = JSONHelper.optString(input, "title", null, "");
				String description = JSONHelper.optString(input, "description", null, "");
				String email = JSONHelper.optString(input, "email", null, "");
				BaseOperationResult emailResult = EmailsManager.sendUsEmail(title, description, userId, email);
				if (emailResult.hasError()) {
					result = emailResult;
				}
			}

			if (null == result) {
				output = BaseOperationResult.JsonOk;
			} else {
				output = result.toJson();
			}
		} catch (Throwable t) {
			UUID errorId = logger.error(t, "giveUsYourFeedback() : Unexpected error occured.");
			output = BaseOperationResult.toJsonGeneralError(t, errorId);
		}

		return output;
	}

	public JSONObject sendClientInfo(JSONObject input) {
		logger.warn("sendClientInfo : input : %s", input.toString());
		return BaseOperationResult.JsonOk;
	}

	public JSONObject createBugReport(final JSONObject input)
			throws IOException, JSONException, NullPointerException, ExecutionException {
		JSONObject output = null;

		BaseOperationResult result = null;
		IUser user = null;
		OperationResult<IUser> userResult = getLoggedInUser();
		if (userResult.hasError()) {
			result = userResult;

		} else {
			user = getLoggedInUser().getValue();
		}

		if (null == result) {
			final IProduct finalProduct = getCurrentProduct();
			final IUser finalUser = user;
			// send email
			BaseOperationResult emailResult = EmailsManager.sendReportBug(new IReportBugEmailData() {

				@Override
				public String getNoreplyEmail() {
					return finalProduct.getNoreplyEmail();
				}

				@Override
				public String getTitle() {
					return JSONHelper.optString(input, "title");
				}

				@Override
				public String getTags() {
					return JSONHelper.optString(input, "tags");
				}

				@Override
				public String getStepsToReproduce() {
					return JSONHelper.optString(input, "stepsToReproduce");
				}

				@Override
				public String getFeedbackCaption() {
					return finalProduct.getFeedbackCaption();
				}

				@Override
				public String getExpectedBehavior() {
					return JSONHelper.optString(input, "expectedBehavior");
				}

				@Override
				public String getDescription() {
					return JSONHelper.optString(input, "description");
				}

				@Override
				public String getBugsEmail() {
					return finalProduct.getSupportEmail();
				}

				@Override
				public String getActualBehavior() {
					return JSONHelper.optString(input, "actualBehavior");
				}

				@Override
				public IUser getUser() {
					return finalUser;
				}
			});

			if (emailResult.hasError()) {
				output = emailResult.toJson();
			} else {
				output = BaseOperationResult.JsonOk;
			}
		}

		return output;
	}

	public JSONObject getBlogPosts(JSONObject input) {
		JSONObject output;

		try {
			Integer top = JSONHelper.optInt(input, "top", 100);
			OperationResult<List<IPost>> postsResult = BlogManager.getPosts(top, Blogs.OfficialInqwiseBlog);
			List<IPost> posts;
			if (postsResult.hasError()) {
				output = postsResult.toJson();
			} else {
				posts = postsResult.getValue();
				JSONArray postsJa = new JSONArray();
				for (IPost post : posts) {
					postsJa.put(new JSONObject().put("id", post.getId()).put("title", post.getTitle()).put("content",
							post.getContent()));
				}

				output = new JSONObject();
				output.put("posts", postsJa);
			}
		} catch (Throwable t) {
			output = new JSONObject();
			UUID errorId = logger.error(t, "getBlogPosts() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId", errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t, "getBlogPosts() : Unable to put to Json object");
			}
		}

		return output;
	}

	public JSONObject getFaqs(JSONObject input) {
		JSONObject output;

		try {
			Integer top = JSONHelper.optInt(input, "top", 100);
			OperationResult<List<IFaq>> faqsResult = FaqsManager.getFaqs(top);
			List<IFaq> faqs;
			if (faqsResult.hasError()) {
				output = faqsResult.toJson();
			} else {
				faqs = faqsResult.getValue();
				JSONArray faqsJa = new JSONArray();
				for (IFaq faq : faqs) {
					faqsJa.put(new JSONObject().put("id", faq.getId()).put("question", faq.getQuestion())
							.put("answer", faq.getAnswer())
							.put("answerIntroduction", StringEscapeUtils.escapeHtml4(faq.getAnswer()).substring(0, 20)));
				}

				output = new JSONObject();
				output.put("faqs", faqsJa);
			}
		} catch (Throwable t) {
			output = new JSONObject();
			UUID errorId = logger.error(t, "getFaqs() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId", errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t, "getFaqs() : Unable to put to Json object");
			}
		}

		return output;
	}

	public JSONObject sendContactUsRequest(JSONObject input) throws JSONException {
		BaseOperationResult result = null;
		try {
			final String contactEmail = JSONHelper.optString(input, "email", null, "");
			final String comments = JSONHelper.optStringTrim(input, "comments");
			final String name = JSONHelper.optStringTrim(input, "name");
			final String phone = JSONHelper.optStringTrim(input, "phone");

			if (null == contactEmail) {
				logger.warn("sendContactUsRequest : email didn't supplied");
				result = new BaseOperationResult(ErrorCode.ArgumentNull, "email is mandatory");
			}

			// Get current Product
			IProduct product = null;
			if (null == result) {
				product = ProductsManager.getCurrentProduct();
				if (null == product) {
					result = new BaseOperationResult(ErrorCode.NoResults);
				}
			}

			if (null == result) {
				final IProduct finalProduct = product;
				result = EmailsManager.sendContactUsRequestEmail(new IContactUsRequestEmailData() {

					@Override
					public String getEmail() {
						return contactEmail;
					}

					@Override
					public String getComments() {
						return comments;
					}

					@Override
					public String getName() {
						return name;
					}

					@Override
					public String getPhone() {
						return phone;
					}

					@Override
					public String getFeedbackCaption() {
						return finalProduct.getFeedbackCaption();
					}
				});
			}
		} catch (Exception ex) {
			UUID errorTicket = logger.error(ex, "sendContactUsRequest : Unsupported error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorTicket);
		}

		if (result.hasError()) {
			return result.toJson();
		} else {
			return BaseOperationResult.JsonOk;
		}
	}

	public JSONObject sendCustomPlanRequest(JSONObject input) throws JSONException {
		BaseOperationResult result = null;
		try {
			final String contactEmail = JSONHelper.optString(input, "email", null, "");
			final String notes = JSONHelper.optString(input, "notes", null, "");

			if (null == contactEmail) {
				logger.warn("sendCustomPlanRequest : email didn't supplied");
				result = new BaseOperationResult(ErrorCode.ArgumentNull, "email is mandatory");
			}

			// Get current Product
			IProduct product = null;
			if (null == result) {
				product = ProductsManager.getCurrentProduct();
				if (null == product) {
					result = new BaseOperationResult(ErrorCode.NoResults);
				}
			}

			if (null == result) {
				final IProduct finalProduct = product;
				result = EmailsManager.sendCustomPlanRequestEmail(new ICustomPlanRequestEmailData() {

					@Override
					public String getNotes() {
						return notes;
					}

					@Override
					public String getFeedbackCaption() {
						return finalProduct.getFeedbackCaption();
					}

					@Override
					public String getEmail() {
						return contactEmail;
					}
				});
			}
		} catch (Exception ex) {
			UUID errorTicket = logger.error(ex, "sendCustomPlanRequest : Unsupported error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorTicket);
		}

		if (result.hasError()) {
			return result.toJson();
		} else {
			return BaseOperationResult.JsonOk;
		}
	}

	/*
	 * @Override public OperationResult<IAccount> getAccount(){
	 * OperationResult<IAccount> result = null; try { OperationResult<IUser>
	 * userResult = getLoggedInUser(); if(userResult.hasError()){ result =
	 * userResult.toErrorResult(); }
	 * 
	 * if(null == result){ result =
	 * userResult.getValue().getAccount(getCurrentProduct().getId(), accountId,
	 * true); } } catch(Exception ex){ UUID errorTicket = logger.error(ex,
	 * "getAccount : Unexpected error occured"); result = new
	 * OperationResult<>(ErrorCode.GeneralError, errorTicket); } return result; }
	 */
}
