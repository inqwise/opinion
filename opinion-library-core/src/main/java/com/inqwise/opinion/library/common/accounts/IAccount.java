package com.inqwise.opinion.library.common.accounts;

import java.util.Date;

import com.inqwise.opinion.library.common.users.IUser;

public interface IAccount {
	
	final class JsonNames {

		public static final String ACCOUNT_ID = "accountId";
		public static final String SERVICE_PACKAGE_NAME = "servicePackageName";
		public static final String ACCOUNT_NAME = "accountName";
		public static final String OWNER_ID = "ownerId";
		public static final String INSERT_DATE = "insertDate";
		public static final String IS_ACTIVE = "isActive";
		public static final String MIN_DEPOSIT_AMOUNT = "minDeposit";
		public static final String MAX_DEPOSIT_AMOUNT = "maxDeposit";
	}
	
	final class ResultSetNames {
		public static final String ACCOUNT_ID = "account_id";
		public static final String ACCOUNT_NAME = "account_name";
		public static final String SERVICE_PACKAGE_NAME = "sp_name";
		public static final String OWNER_ID = "owner_id";
		public static final String INSERT_DATE = "insert_date";
		public static final String IS_ACTIVE = "is_active";
	}

	public abstract String getName();

	public abstract long getId();

	public abstract int getServicePackageId();

	public abstract int getProductId();

	public abstract Long getOwnerId();

	public abstract Date getInsertDate();

	public abstract boolean isActive();

	public abstract IUser getOwner();

	public abstract Date getServicePackageExpiryDate();
	
	public abstract Date addDateOffset(Date date);
	
	public abstract Date removeDateOffset(Date date);

	public abstract Integer getTimezoneOffset();
	
	public abstract Integer getMaxUsers();

	public abstract Integer getMaxDepositAmount();

	public abstract Integer getMinDepositAmount();

}
