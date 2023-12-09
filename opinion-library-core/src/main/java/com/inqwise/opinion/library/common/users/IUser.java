package com.inqwise.opinion.library.common.users;

import java.util.Date;
import java.util.List;

import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public interface IUser {
	
	final class JsonNames {
	}
	
	final class ResultSetNames {
		
	}
	
	public Long getUserId();

	public String getUserName();

	public String getDisplayName();

	public String getEmail();
	
	public Boolean getSendNewsLetters();
	
	public BaseOperationResult resetPassword(String password, String clientIp, int sourceId, Long backofficeUserId, Date passwordExpiryDate, String comments);
	
	public BaseOperationResult sendHtmlMail(String subject, String content);
	
	public OperationResult<List<IAccount>> getAccounts(int productId);

	public Date getInsertDate();

	public Integer getCountryId();

	public String getCultureCode();

	public IAccount getDefaultAccount(int productId);
	
	public LoginProvider getProvider();

	public String getUserExternalId();
	
	public Long getGatewayId();

	public abstract OperationResult<IAccount> createAccount(int sourceId, int productId,
			String accountName, String clientIp, int servicePackageId, Long backofficeUserId);

	public abstract BaseOperationResult detachAccount(int sourceId, long accountId, Long backofficeUserId);

	public abstract BaseOperationResult attachAccount(int sourceId, long accountId, Long backofficeUserId);

	public OperationResult<IAccount> getAccount(int productId, Long accountId, boolean defaultIfNotFound);

}
