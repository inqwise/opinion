package com.inqwise.opinion.opinion.facade.front;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.emails.IPasswordChangedEmailData;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.common.users.IUserDetails;
import com.inqwise.opinion.library.common.users.IUserView;
import com.inqwise.opinion.library.common.users.LoginProvider;
import com.inqwise.opinion.library.managers.EmailsManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.IPostmasterObject;

public class UsersEntry extends Entry implements IPostmasterObject {

	static ApplicationLog logger = ApplicationLog.getLogger(UsersEntry.class);
	private static final int DEFAULT_EXPIRATION_IN_DAYS = 30;
	public UsersEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject getUserDetails(final JSONObject input){
		JSONObject output = null;
		
		try{
			BaseOperationResult result = null;
			Long userId = null;
			if(IsSignedIn()){
				userId = getUserId().getValue();
			} else {
				logger.warn("getUserDetails : Not Signed in.");
				result = new BaseOperationResult(ErrorCode.NotSignedIn);
			}
			
			IUserDetails userDetails = null;
			if(null == result){
				OperationResult<IUserDetails> userDetailsResult = UsersManager.getUserDetails(userId);
				if(userDetailsResult.hasError()){
					result = userDetailsResult;
				} else {
					userDetails = userDetailsResult.getValue();
				}
			}
			
			if(null == result){
				output = userDetails.toJson()
						.put("userId", userDetails.getUserId());
			} else {
				output = result.toJson();
			}
			
		} catch (Throwable t) {
			UUID errorId = logger.error(t,"getUserDetails() : Unexpected error occured.");
			try {
				output = BaseOperationResult.toJsonGeneralError(t, errorId);
			} catch (JSONException e) {
				logger.error(t,"getUserDetails() : Unable to put to Json object");
			}
		}
		
		return output;
	}
	
	public JSONObject changePassword(JSONObject input) throws JSONException, IOException, NullPointerException, ExecutionException{
		JSONObject output = new JSONObject();
		
		BaseOperationResult result = null;
		Long userId = null;
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("getUserDetails : Not Signed in.");
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		String newPassword = null;
		boolean sendConfirmEmail = false;
		boolean neverExpiry = false;
		
		if(null == result){
			newPassword = JSONHelper.optStringTrim(input, "newPassword");
			sendConfirmEmail = JSONHelper.optBoolean(input, "sendConfirmEmail", false);
			neverExpiry = JSONHelper.optBoolean(input, "neverExpiry", true);
					
			if(null == newPassword){
				result = new BaseOperationResult(ErrorCode.ArgumentMandatory, "newPassword");
			}
		}
		
		//Get user
		IUser user = null;
		String clientIp = null;
		Calendar passwordExpirationDate = null;
		if(null == result){
			clientIp = getContext().getClientIp();
			
			passwordExpirationDate = Calendar.getInstance();
			if (neverExpiry) {
				passwordExpirationDate.set(9999, 11, 31, 23, 59, 59);
			} else {
				passwordExpirationDate.add(Calendar.DAY_OF_YEAR, DEFAULT_EXPIRATION_IN_DAYS);
			}
			
			OperationResult<IUser> userResult = UsersManager.getUser(null, userId, LoginProvider.Inqwise, null);
			if(userResult.hasError()) {
				result = userResult;
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
			BaseOperationResult resetPasswordResult = user.resetPassword(newPassword, clientIp, product.getId(), null, passwordExpirationDate.getTime(), null);
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
	
	private void sendConfirmResetPasswordEmail(final IProduct product, final IUser user, final String password) {
		
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
	
	public JSONObject getUsers(JSONObject input) throws JSONException, NullPointerException, IOException, ExecutionException {
		JSONObject output;
		BaseOperationResult result = null;
		Long userId = null;
		if(IsSignedIn()){
			userId = getUserId().getValue();
		} else {
			logger.warn("getUserDetails : Not Signed in.");
			result = new BaseOperationResult(ErrorCode.NotSignedIn);
		}
		
		IAccount account = null;
		if(null == result){
			OperationResult<IAccount> accountResult = getContext().getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
				if(!account.getOwnerId().equals(userId)){
					logger.warn("User #%s not have owner privilages for account #%s", userId, account.getId());
					result = new BaseOperationResult(ErrorCode.NotPermitted);
				}
			}
		}
		
		List<IUserView> users = null;
		if(null == result){
			OperationResult<List<IUserView>> usersResult = UsersManager.getUsers(100, getContext().getSession().getLoginProductId(), null, null, account.getId());
			if(usersResult.hasError()){
				result = usersResult;
			} else {
				
				users = usersResult.getValue();
			}
		}
		
		if(null == result){
			JSONArray list = new JSONArray();
			for (IUserView user : users) {
				
				JSONObject item = new JSONObject();
				item.put("userId", user.getUserId());
				item.put("userName", user.getUserName());
				//item.put("countryName", user.getCountryName());
				item.put("insertDate", DateConverter.getDateFormat(account.addDateOffset(user.getInsertDate()), "MMM dd, yyyy HH:mm:ss"));
				/*
				if(user.getProvider() != LoginProvider.Inqwise){
					item.put("provider", user.getProvider());
				}
				*/
				list.put(item);
			}
			
			output = new JSONObject();
			output.put("list", list);
			
		} else {
			output = result.toJson();
		}
		return output;
	}
}
