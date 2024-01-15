package com.inqwise.opinion.facade.front;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONObject;

import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.emails.IInviteEmailData;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.invites.IInvite;
import com.inqwise.opinion.library.common.invites.IInviteCreateRequest;
import com.inqwise.opinion.library.common.invites.IInviteSettingsChangeRequest;
import com.inqwise.opinion.library.managers.InvitesManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class InvitesEntry extends Entry implements IPostmasterObject {

	public InvitesEntry(IPostmasterContext context) {
		super(context);
	}
	
	public JSONObject sendInvite(JSONObject input) throws NullPointerException, ExecutionException{
		JSONObject output = null;
		IOperationResult result = validateSignIn();
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		Long inviteId = null;
		if (null == result){
			inviteId = JSONHelper.optLong(input, IInvite.JsonNames.INVITE_ID);
			
			if(null == inviteId){
				// create invite
				OperationResult<Long> inviteResult = createInvite(account.getId(), input, account.getMaxUsers(), account.getProductId());
				if(inviteResult.hasError()){
					result = inviteResult;
				} else {
					inviteId = inviteResult.getValue();
				}
			} else if(input.has(IInvite.JsonNames.INVITE_NAME)){
				// update invite
				BaseOperationResult inviteResult = changeInvite(account.getId(), input);
				if(inviteResult.hasError()){
					result = inviteResult;
				} 
			}
		}
		
		if(null == result) {
			IOperationResult sendInviteResult = sendInvite(account, inviteId);
			if(sendInviteResult.hasError()){
				result = sendInviteResult;
			}
		}
		
		if(null == result){
			output = new JSONObject().put(IInvite.JsonNames.INVITE_ID, inviteId);
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	private IOperationResult sendInvite(IAccount account, long inviteId) {
		IOperationResult result = null;
		IInvite invite = null;
		if (null == result){
			// get invite details
			OperationResult<IInvite> inviteResult = InvitesManager.getInvite(inviteId, account.getId());
			if(inviteResult.hasError()){
				result = inviteResult;
			} else {
				invite = inviteResult.getValue();
			}
		}
		
		if (null == result){
			
		}
		
		if (null == result){
			final IProduct currentProduct = getContext().getCurrentProduct();
			final IInvite finalInvite = invite;
			result = invite.send(new IInviteEmailData() {
				
				@Override
				public String getNoreplyEmail() {
					return currentProduct.getNoreplyEmail();
				}
				
				@Override
				public String getSupportEmail() {
					return currentProduct.getSupportEmail();
				}
				
				@Override
				public String getFeedbackShortCaption() {
					return currentProduct.getFeedbackShortCaption();
				}
				
				@Override
				public String getFeedbackCaption() {
					return currentProduct.getFeedbackCaption();
				}
				
				@Override
				public String getEmail() {
					return finalInvite.getEmail();
				}
				
				@Override
				public int getCurrentYear() {
					return 0;
				}
				
				@Override
				public String getApplicationUrl() {
					return ApplicationConfiguration.Opinion.getSecureUrl();
				}

				@Override
				public String inviteName() {
					return finalInvite.getName();
				}
			});
		}
		return result;
	}
	
	public JSONObject deleteInvite(JSONObject input) throws NullPointerException, ExecutionException{
		JSONObject output = null;
		IOperationResult result = validateSignIn();
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		if (null == result){
			long inviteId = input.getLong(IInvite.JsonNames.INVITE_ID);
			BaseOperationResult deleteResult = InvitesManager.deleteInvite(inviteId, account.getId());
			if(deleteResult.hasError()){
				result = deleteResult;
			}
		}
	
		if (null == result){
			output = BaseOperationResult.JsonOk;
		} else {
			output = result.toJson();
		}
		return output;
	}
	
	public JSONObject getInvites(JSONObject input) throws NullPointerException, ExecutionException{
//		JSONObject output = null;
//		IOperationResult result = validateSignIn();
//		
//		IAccount account = null;
//		if(null == result) {
//			OperationResult<IAccount> accountResult = getAccount();
//			if(accountResult.hasError()){
//				result = accountResult;
//			} else {
//				account = accountResult.getValue();
//			}
//		}
//		
//		if (null == result){
//			JSONArray ja = new JSONArray();
//			CDataCacheContainer invitesContainer = InvitesManager.getInvites(account.getId());
//			CDataRowSet rowSet = invitesContainer.getAll();
//			while(rowSet.next()){
//				JSONObject jo = new JSONObject();
//				jo.put(IInvite.JsonNames.INVITE_ID, rowSet.getLong(IInvite.ResultSetNames.INVITE_ID));
//				jo.put(IInvite.JsonNames.INVITE_NAME, rowSet.getString(IInvite.ResultSetNames.INVITE_NAME));
//				jo.put(IInvite.JsonNames.INVITE_EMAIL, rowSet.getString(IInvite.ResultSetNames.INVITE_EMAIL));
//				jo.put(IInvite.JsonNames.INVITE_DATE, DateConverter.getDateFormat(account.addDateOffset(rowSet.getDate(IInvite.ResultSetNames.INVITE_DATE)), "MMM dd, yyyy"));
//				jo.put(IInvite.JsonNames.EXTERNAL_ID, rowSet.getString(IInvite.ResultSetNames.EXTERNAL_ID));
//				ja.put(jo);
//			}
//			
//			output = new JSONObject().put("list", ja);
//		} else {
//			output = result.toJson();
//		}
//		
//		return output;
		throw new NotImplementedException("getInvites");
	}

	private OperationResult<Long> createInvite(final long accountId, final JSONObject input, final Integer accountMaxUsers, final int accountProductId) {
		return InvitesManager.createInvite(new IInviteCreateRequest() {

			@Override
			public long getAccountId() {
				return accountId;
			}

			@Override
			public String getExternalId() {
				return UUID.randomUUID().toString().replace("-", "");
			}

			@Override
			public String getEmail() {
				return JSONHelper.optString(input, IInvite.JsonNames.INVITE_EMAIL);
			}

			@Override
			public String getInviteName() {
				return JSONHelper.optString(input, IInvite.JsonNames.INVITE_NAME, "");
			}

			@Override
			public Integer getMaxUsers() {
				return accountMaxUsers;
			}

			@Override
			public int getAccountProductId() {
				return accountProductId;
			}
		});
	}
	
	private BaseOperationResult changeInvite(final long accountId, final JSONObject input) {
		return InvitesManager.changeInviteSettings(new IInviteSettingsChangeRequest() {

			@Override
			public long getInviteId() {
				return input.getLong(IInvite.JsonNames.INVITE_ID);
			}

			@Override
			public Long getAccountId() {
				return accountId;
			}

			@Override
			public String getEmail() {
				return JSONHelper.optString(input, IInvite.JsonNames.INVITE_EMAIL);
			}

			@Override
			public String getInviteName() {
				return JSONHelper.optString(input, IInvite.JsonNames.INVITE_NAME);
			}

			@Override
			public Date getInviteDate() {
				return null;
			}
		});
	}
}
