package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import net.casper.data.model.CDataCacheContainer;
import net.casper.data.model.CDataGridException;
import net.casper.data.model.CDataRowSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.emails.IPasswordChangedEmailData;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.common.users.IUserDetails;
import com.inqwise.opinion.library.common.users.IUserDetailsChangeRequest;
import com.inqwise.opinion.library.common.users.IUserView;
import com.inqwise.opinion.library.common.users.LoginProvider;
import com.inqwise.opinion.library.managers.AccountsManager;
import com.inqwise.opinion.library.managers.EmailsManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.managers.UsersOperationsManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.opinion.common.IPostmasterContext;

public class UsersEntry extends Entry {
	private static final int DEFAULT_EXPIRATION_IN_DAYS = 30;

	protected UsersEntry(IPostmasterContext context) {
		super(context);
		
	}

	static ApplicationLog logger = ApplicationLog.getLogger(UsersEntry.class);
	static Format mdyhmsFormatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
	
	public JSONObject getUsers(JSONObject input) throws JSONException {
		JSONObject output;
		int top = JSONHelper.optInt(input, "top", 100);
		int productId = JSONHelper.optInt(input, "productId", 1);
		Date fromDate = JSONHelper.optDate(input, "fromDate");
		Date toDate = JSONHelper.optDate(input, "toDate");
		Long accountId = JSONHelper.optLong(input, "accountId");
		
		OperationResult<List<IUserView>> usersResult = UsersManager.getUsers(top, productId, fromDate, toDate, accountId);
		if(usersResult.hasError()){
			output = usersResult.toJson();
		} else {
			List<IUserView> users = usersResult.getValue();
			output = new JSONObject();
			
			JSONArray list = new JSONArray();
			for (IUserView user : users) {
				
				JSONObject item = new JSONObject();
				item.put("userId", user.getUserId());
				item.put("userName", user.getUserName());
				item.put("countryName", user.getCountryName());
				item.put("insertDate", mdyhmsFormatter.format(user.getInsertDate()));
				item.put("sendNewsLetters", user.getSendNewsLetters());
				if(user.getProvider() != LoginProvider.Inqwise){
					item.put("provider", user.getProvider());
				}
				list.put(item);
				
			}
			
			output = new JSONObject();
			output.put("list", list).put("top", top).put("productId", productId);
		}
		return output;
	}
	
	public JSONObject getUserDetails(final JSONObject input){
		JSONObject output;
		
		try{
			long userId = input.getLong("userId");
			OperationResult<IUserDetails> result = UsersManager.getUserDetails(userId);
			if(result.hasError()){
				output = result.toJson();
			} else {
				IUserDetails userDetails = result.getValue();
				output = userDetails.toJson()
				.put("userId", userDetails.getUserId())
				.put("insertDate", mdyhmsFormatter.format(userDetails.getInsertDate()))
				.put("gatewayId", userDetails.getGatewayId());
				if(userDetails.getProvider() != LoginProvider.Inqwise){
					output.put("isExternal", true);
					output.put("provider", userDetails.getProvider());
				}
				
			}
		} catch (Throwable t) {
			output = new JSONObject();
			UUID errorId = logger.error(t,"getUserDetails() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t,"getUserDetails() : Unable to put to Json object");
			}
		}
		
		return output;
	}
	
	public JSONObject setUserDetails(final JSONObject input){
		JSONObject output;
		try {
			final long userId = input.getLong("userId");
			IUserDetailsChangeRequest userDetailsChangeRequest = new IUserDetailsChangeRequest() {
				
				@Override
				public String getTitle() {
					return JSONHelper.optString(input, "title", "", null);
				}
				
				@Override
				public Integer getStateId() {
					return JSONHelper.optInt(input, "stateId");
				}
				
				@Override
				public String getPostalCode() {
					return JSONHelper.optString(input, "postalCode", "", null);
				}
				
				@Override
				public String getPhone1() {
					return JSONHelper.optString(input, "phone1", "", null);
				}
				
				@Override
				public String getLastName() {
					return JSONHelper.optString(input, "lastName", "", null);
				}
				
				@Override
				public String getFirstName() {
					return JSONHelper.optString(input, "firstName", "", null);
				}
				
				@Override
				public int getCountryId() {
					return input.optInt("countryId");
				}
				
				@Override
				public String getComments() {
					return JSONHelper.optString(input, "comments", "", null);
				}
				
				@Override
				public String getCity() {
					return JSONHelper.optString(input, "city", "", null);
				}
				
				@Override
				public String getAddress2() {
					return JSONHelper.optString(input, "address2", "", null);
				}
				
				@Override
				public String getAddress1() {
					return JSONHelper.optString(input, "address1", "", null);
				}

				@Override
				public long getUserId() {
					return userId;
				}

				@Override
				public String getDisplayName() {
					return JSONHelper.optString(input, "displayName", "", null);
				}

				@Override
				public String getEmail() {
					return JSONHelper.optString(input, "email", "", null);
				}

				@Override
				public boolean isSendNewsLetters() {
					return input.optBoolean("sendNewsLetters");
				}
			};
			
			BaseOperationResult result = UsersManager.setUserDetails(userDetailsChangeRequest);
			if(result.hasError()){
				output = result.toJson();
			} else {
				output = BaseOperationResult.JsonOk;
			}
		} catch (Throwable t) {
			output = new JSONObject();
			UUID errorId = logger.error(t,"setUserDetails() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t,"setUserDetails() : Unable to put to Json object");
			}
		}
		
		return output;
	}
	
	public JSONObject getHistory(JSONObject input) throws JSONException, CDataGridException{
		JSONObject output;
		Long userId = JSONHelper.optLong(input, "userId");
		int top = JSONHelper.optInt(input, "top", 100);
		Integer usersOperationsTypeId = JSONHelper.optInt(input, "typeId");
		Integer productId = JSONHelper.optInt(input, "productId");
		Date fromDate = JSONHelper.optDate(input, "fromDate");
		Date toDate = JSONHelper.optDate(input, "toDate");
		Integer[] usersOperationsTypeIds = JSONHelper.toArrayOfInt(JSONHelper.optJsonArray(input, "typeIds"));
		
		if(null == usersOperationsTypeIds && null != usersOperationsTypeId){
			usersOperationsTypeIds = new Integer[] {usersOperationsTypeId};
		}
		
		CDataCacheContainer ds = UsersOperationsManager.getUserOperations(top, userId, usersOperationsTypeIds, fromDate, toDate, productId);
		CDataRowSet rowSet = ds.getAll();
		JSONArray ja = new JSONArray();
		Format formatter = new SimpleDateFormat(
				"MMM dd, yyyy HH:mm:ss");
		while(rowSet.next()){
			JSONObject item = new JSONObject();
			item.put("id", rowSet.getLong("usop_id"));
			item.put("typeId", rowSet.getInt("usop_type_id"));
			item.put("typeName", rowSet.getString("usop_type_value"));
			if(null == userId){
				item.put("userName", rowSet.getString("user_name"));
				item.put("userId", rowSet.getLong("user_id"));
			}
			item.put("clientIp", rowSet.getString("client_ip"));
			//item.put("geoCountryId", rowSet.getString("geo_country_id"));
			//item.put("sessionId", rowSet.getString("session_id"));
			if(null == productId){
				item.put("sourceId", rowSet.getInt("product_id"));
			}
			//item.put("sourceName", rowSet.getString("product_name"));
			item.put("insertDate", formatter.format(rowSet.getDate("insert_date")));
			// item.put("comments", rowSet.getDate("comments"));
			//item.put("referenceId", rowSet.getLong("reference_id"));
			//item.put("backofficeUserId", rowSet.getLong("backoffice_user_id"));
			item.put("countryName", rowSet.getString("country_name"));
			ja.put(item);
		}
		
		output = new JSONObject();
		output.put("list", ja);
		return output;
	}
	
	public JSONObject resetPassword(JSONObject input) throws JSONException, IOException{
		JSONObject output = new JSONObject();
		
		BaseOperationResult result = null;
		final Long userId = JSONHelper.optLong(input, "userId");
		final String newPassword = JSONHelper.optStringTrim(input, "newPassword");
		final boolean sendConfirmEmail = JSONHelper.optBoolean(input, "sendConfirmEmail", false);
		final long backofficeUserId = getContext().getUserId().getValue();
		boolean mustChangePassword = JSONHelper.optBoolean(input, "mustChangePassword", true);
		boolean neverExpiry = JSONHelper.optBoolean(input, "neverExpiry", true);
		String comments = JSONHelper.optStringTrim(input, "comments");
		if(null == userId){
			result = new BaseOperationResult(ErrorCode.ArgumentMandatory, "userId");
		}
		
		if(null == newPassword){
			result = new BaseOperationResult(ErrorCode.ArgumentMandatory, "newPassword");
		}
		
		//Get user
		IUser user = null;
		String clientIp = null;
		Calendar passwordExpirationDate = null;
		if(null == result){
			clientIp = getContext().getClientIp();
			
			passwordExpirationDate = Calendar.getInstance();
			if(mustChangePassword){
				passwordExpirationDate.add(Calendar.DAY_OF_YEAR, -1);
			} else if (neverExpiry) {
				passwordExpirationDate.set(9999, 11, 31, 23, 59, 59);
			} else {
				passwordExpirationDate.add(Calendar.DAY_OF_YEAR, DEFAULT_EXPIRATION_IN_DAYS);
			}
			OperationResult<IUser> userResult = UsersManager.getUser(null, userId, null, null);
			if(userResult.hasError()) {
				UUID errorId = logger.error("resetPassword : Unexpected error occured :" + userResult.toString());
				result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
				
				if(userResult.hasError()){
					result = userResult;
				}
			} else {
				user = userResult.getValue();
			}
		}
		
		// get current Product details
		IProduct product = null;
		if(null == result){
			
			product = ProductsManager.getCurrentProduct();
			if(null == product){
				result = new BaseOperationResult(ErrorCode.NoResults);
			}
		}

		if(null == result){
			
			// Reset password
			BaseOperationResult resetPasswordResult = user.resetPassword(newPassword, clientIp, product.getId(), backofficeUserId, passwordExpirationDate.getTime(), comments);
			if(resetPasswordResult.hasError()){
				result = resetPasswordResult;
			}
		}
		
		// Send confirm reset password email
		if(null == result && sendConfirmEmail){
			sendConfirmResetPasswordEmail(product, user, newPassword);
		}
		
		if(null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}

		return output;
	}
		
	public void sendConfirmResetPasswordEmail(final IProduct product, final IUser user, final String password) {
		
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
				return password;
			}
		};
		
		EmailsManager.sendPasswordChangedEmail(data);
	}
	
	public JSONObject attachAccount(JSONObject input) throws ExecutionException, NullPointerException, IOException{
		JSONObject output;
		
		BaseOperationResult result = null;
		final Long userId = JSONHelper.optLong(input, "userId");
		final Long accountId = JSONHelper.optLong(input, "accountId");
		IAccountView account = null;
		OperationResult<IAccountView> accountResult = AccountsManager.getAccount(accountId);
		IProduct sourceProduct = ProductsManager.getCurrentProduct();
		
		if(accountResult.hasError()){
			result = accountResult;
		} else {
			account = accountResult.getValue();
		}
		
		if(null == result){
			OperationResult<IUser> userResult = UsersManager.getUser(userId, account.getProductId());
			if(userResult.hasError()){
				result = userResult;
			} else {
				result = userResult.getValue().attachAccount(sourceProduct.getId(), accountId, getContext().getLoggedInUser().getValue().getUserId());
			}
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
	
	public JSONObject detachAccount(JSONObject input) throws ExecutionException, NullPointerException, IOException{
		JSONObject output;
		
		BaseOperationResult result = null;
		final Long userId = JSONHelper.optLong(input, "userId");
		final Long accountId = JSONHelper.optLong(input, "accountId");
		IAccountView account = null;
		OperationResult<IAccountView> accountResult = AccountsManager.getAccount(accountId);
		IProduct sourceProduct = ProductsManager.getCurrentProduct();
		
		if(accountResult.hasError()){
			result = accountResult;
		} else {
			account = accountResult.getValue();
		}
		
		if(null == result){
			OperationResult<IUser> userResult = UsersManager.getUser(userId, account.getProductId());
			if(userResult.hasError()){
				result = userResult;
			} else {
				result = userResult.getValue().detachAccount(sourceProduct.getId(), accountId, getContext().getLoggedInUser().getValue().getUserId());
			}
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
	
	public JSONObject createAccount(JSONObject input) throws NullPointerException, IOException, ExecutionException{
		JSONObject output;
		
		BaseOperationResult result = null;
		final Long userId = JSONHelper.optLong(input, "userId");
		int productId = input.getInt("productId");
		String accountName = input.getString("accountName");
		IProduct sourceProduct = ProductsManager.getCurrentProduct();
		IProduct product = null;
		
		OperationResult<IProduct> productResult = ProductsManager.getProductById(productId);
		if(productResult.hasError()){
			result = productResult;
		} else {
			product = productResult.getValue();
		}
		
		if(null == result){
			OperationResult<IUser> userResult = UsersManager.getUser(userId, productId);
			if(userResult.hasError()){
				result = userResult;
			} else {
				result = userResult.getValue().createAccount(sourceProduct.getId(), productId, accountName, null, product.getDefaultServicePackage().getId(), getContext().getLoggedInUser().getValue().getUserId());
			}
		}
		
		if(result.hasError()){
			output = result.toJson();
		} else {
			output = BaseOperationResult.JsonOk;
		}
		
		return output;
	}
}
