package com.inqwise.opinion.library.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.emails.IInviteEmailData;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.invites.IInvite;
import com.inqwise.opinion.library.common.invites.IInviteSettingsChangeRequest;
import com.inqwise.opinion.library.dao.InvitesDataAccess;
import com.inqwise.opinion.library.managers.EmailsManager;
import com.inqwise.opinion.library.managers.InvitesManager;

public class InviteEntity implements IInvite {

	private static ApplicationLog logger = ApplicationLog.getLogger(InviteEntity.class);
	
	private long id;
	private String name;
	private String email;
	private Date inviteDate;
	private Date modifyDate;
	private long accountId;
	private String externalId;
	
	public InviteEntity(ResultSet reader) throws SQLException {
		id = reader.getLong(IInvite.ResultSetNames.INVITE_ID);
		accountId = reader.getLong(IInvite.ResultSetNames.ACCOUNT_ID);
		name = ResultSetHelper.optString(reader, IInvite.ResultSetNames.INVITE_NAME);
		email = ResultSetHelper.optString(reader, IInvite.ResultSetNames.INVITE_EMAIL);
		inviteDate = ResultSetHelper.optDate(reader, IInvite.ResultSetNames.INVITE_DATE);
		modifyDate = ResultSetHelper.optDate(reader, IInvite.ResultSetNames.MODIFY_DATE);
		externalId = ResultSetHelper.optString(reader, IInvite.ResultSetNames.EXTERNAL_ID);
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public Date getInviteDate() {
		return inviteDate;
	}

	@Override
	public Date getModifyDate() {
		return modifyDate;
	}

	@Override
	public long getAccountId() {
		return accountId;
	}

	@Override
	public String getExternalId() {
		return externalId;
	}

	@Override
	public BaseOperationResult send(IInviteEmailData data) {
		BaseOperationResult result = null;
		
		if(null == result){
			BaseOperationResult emailResult = EmailsManager.sendInviteEmail(data);
			if(emailResult.hasError()){
				result = emailResult;
			}
		}
		
		if(null == result){
			result = changeInviteDate(getId(), getAccountId(), new Date()); 
		}
		
		if(null == result){
			result = BaseOperationResult.Ok;
		}
		
		return result;
	}

	private BaseOperationResult changeInviteDate(final long inviteId, final long accountId, final Date inviteDate) {
		BaseOperationResult result = null;
		try{
			InvitesDataAccess.updateInvite(new IInviteSettingsChangeRequest() {
				
				@Override
				public String getInviteName() {
					return null;
				}
				
				@Override
				public long getInviteId() {
					return inviteId;
				}
				
				@Override
				public Date getInviteDate() {
					return inviteDate;
				}
				
				@Override
				public String getEmail() {
					return null;
				}
				
				@Override
				public Long getAccountId() {
					return accountId;
				}
			});
			
			this.inviteDate = inviteDate;
			this.modifyDate = new Date();
		} catch(DAOException ex){
			UUID errorId = logger.error(ex, "changeInviteDate() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
}
