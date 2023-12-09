package com.inqwise.opinion.library.managers;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import net.casper.data.model.CDataCacheContainer;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.invites.IInvite;
import com.inqwise.opinion.library.common.invites.IInviteCreateRequest;
import com.inqwise.opinion.library.common.invites.IInviteSettingsChangeRequest;
import com.inqwise.opinion.library.common.users.IUserView;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.library.dao.InvitesDataAccess;
import com.inqwise.opinion.library.entities.InviteEntity;
import com.inqwise.opinion.library.entities.UserEntity;
import com.inqwise.opinion.library.jobs.AccountsServicePackagesExpirationChecker;

public class InvitesManager {
	private static ApplicationLog logger = ApplicationLog.getLogger(InvitesManager.class);
	
	public static OperationResult<Long> createInvite(final IInviteCreateRequest request){
		final OperationResult<Long> result = new OperationResult<>();
		try {
			if(null != request.getMaxUsers()){
				int countOfInvitedUsers = getInvites(request.getAccountId()).getAll().size();
				int maxCountOfAssignedUsers = request.getMaxUsers() - Math.min(countOfInvitedUsers, request.getMaxUsers());
				OperationResult<List<IUserView>> usersResult = UsersManager.getUsers(request.getMaxUsers(), request.getAccountProductId(), null, null, request.getAccountId());
				if(usersResult.hasErrorExcept(ErrorCode.NoResults)){
					result.setError(usersResult);
				} else if (usersResult.hasValue() && usersResult.getValue().size() >= maxCountOfAssignedUsers){
					logger.warn("Max users limit reached. Account: #%s, maxUsers: '%s', countOfInvites: '%s', countOfUsers: '%s'", request.getAccountId(), request.getMaxUsers(), countOfInvitedUsers, usersResult.getValue().size());
					result.setError(ErrorCode.MaxUsersReached);
				} 
			}
			
			if(!result.hasError()){
				IResultSetCallback callback = new IResultSetCallback() {
					public void call(ResultSet reader, int generationId) throws Exception {
						while(reader.next()){
							if(0 == generationId){
								Integer errorCode = ResultSetHelper.optInt(reader, DAOBase.ERROR_CODE, 0);
								switch(errorCode){
								case 0:
									if(result.hasValue()){
										throw new Exception("createInvite() : More than one record received");
									} else {
										result.setValue(reader.getLong(IInvite.ResultSetNames.INVITE_ID));
									}
									break;
								case 1: // account already contain invite for the email
									logger.warn("Email already exist. Account: #%s, email: '%s'", request.getAccountId(), request.getEmail());
									result.setError(ErrorCode.EmailAlreadyExist);
									break;
								default: // Unexpected Error
									throw new OperationNotSupportedException("createInvite() : errorCode not supported. Value: '" + errorCode);
								}							
							}
						}
					}
				};
				
				InvitesDataAccess.insertInvite(request, callback);
				
				if(!result.hasError() && !result.hasValue()){
					result.setError(ErrorCode.NoResults);
				}
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "createInvite() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult changeInviteSettings(IInviteSettingsChangeRequest request){
		BaseOperationResult result = null;
		try {
			InvitesDataAccess.updateInvite(request);
			return BaseOperationResult.Ok;
		} catch (Exception e) {
			UUID errorId = logger.error(e, "updateInvite() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static BaseOperationResult deleteInvite(long inviteId, long accountId){
		BaseOperationResult result;
		try{
			if(InvitesDataAccess.deleteInvite(inviteId, accountId)){
				if(logger.IsDebugEnabled()){
					logger.debug("deleteInvite() : Invite #%s deleted.", inviteId);
				}
				result = BaseOperationResult.Ok;
			} else {
				logger.warn("deleteInvoice() : InviteId '%s' not deleted. Check condition.", inviteId);
				result = new BaseOperationResult(ErrorCode.NoResults);
			}
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "deleteInvite() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<IInvite> getInvite(String externalId){
		return getInvite(externalId, null ,null);
	}
	
	private static OperationResult<IInvite> getInvite(String externalId, Long inviteId, Long accountId){
		final OperationResult<IInvite> result = new OperationResult<IInvite>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							IInvite invite = new InviteEntity(reader);
							result.setValue(invite);							
						}
					}
				}
			};
			
			InvitesDataAccess.getInviteResultSet(inviteId, accountId, externalId, callback);
			
			if(!result.hasError() && !result.hasValue()){
				result.setError(ErrorCode.NoResults);
			}
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getInvite() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static CDataCacheContainer getInvites(long accountId){
		try {
			return InvitesDataAccess.getInvites(accountId);
		} catch (DAOException e) {
			throw new Error(e);
		}
	}

	public static OperationResult<IInvite> getInvite(long inviteId, long accountId) {
		return getInvite(null, inviteId, accountId);
	}
}
