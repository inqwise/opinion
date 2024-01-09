package com.inqwise.opinion.library.entities.accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.DateConverter;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.dao.AccountsDataAccess;
import com.inqwise.opinion.library.entities.UserEntity;

public class AccountEntity implements IAccount, IAccountView {

	static ApplicationLog logger = ApplicationLog.getLogger(AccountEntity.class);
	private long id;
	private Long ownerId;
	private int productId;
	private String name;
	private int servicePackageId;
	private IUser owner;
	private Date insertDate;
	private boolean isActive;
	private String servicePackageName;
	private String ownerName;
	private Date servicePackageExpiryDate;
	private Integer timezoneOffset;
	private Integer maxUsers;
	private Integer minDepositAmount;
	private Integer maxDepositAmount;
	
	public AccountEntity(ResultSet reader) throws SQLException {
		setId(reader.getLong("account_id"));
		setOwnerId(ResultSetHelper.optLong(reader, "owner_id"));
		setProductId(reader.getInt("product_id"));
		setName(reader.getString("account_name"));
		setServicePackageId(ResultSetHelper.optInt(reader, "service_package_id"));
		setActive(ResultSetHelper.optBool(reader, "is_active", true));
		setInsertDate(ResultSetHelper.optDate(reader,"insert_date"));
		setOwnerName(ResultSetHelper.optString(reader, "owner_name"));
		setServicePackageName(ResultSetHelper.optString(reader, "sp_name"));
		setServicePackageExpiryDate(ResultSetHelper.optDate(reader, "service_package_expiry_date"));
		setTimezoneOffset(ResultSetHelper.optInt(reader, "time_offset"));
		setMaxUsers(ResultSetHelper.optInt(reader, "max_users"));
		setMinDepositAmount(ResultSetHelper.optInt(reader, "min_deposit_amount"));
		setMaxDepositAmount(ResultSetHelper.optInt(reader, "max_deposit_amount"));
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductId() {
		return productId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setServicePackageId(int servicePackageId) {
		this.servicePackageId = servicePackageId;
	}

	public int getServicePackageId() {
		return servicePackageId;
	}

	public void setOwner(IUser owner) {
		this.owner = owner;
	}

	public IUser getOwner() {
		if(null == owner){
			OperationResult<IUser> ownerResult = UserEntity.getUser(null, getOwnerId(), null, null);
			if(ownerResult.hasError()){
				throw new Error("Object owner not initialized for account#" + getId());
			} else {
				owner = ownerResult.getValue();
			}
		}
		return owner;
	}

	public static OperationResult<IAccount> getAccount(Long accountId) {
		final OperationResult<IAccount> result = new OperationResult<IAccount>();
		try{
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(result.hasValue()){
							throw new Error("getAccount : More than one result return");
						} else {
							result.setValue(new AccountEntity(reader));
						}
					}
				}
			};
			
			AccountsDataAccess.getAccount(accountId, callback);
			
			if(!result.hasValue()){
				logger.warn("getAccount : No results for id " + accountId);
				result.setError(ErrorCode.NoResults);
			}
		} catch(DAOException e) {
			e.printStackTrace();
			UUID errorId = logger.error(e, "getAccount() : Unexpacted error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setServicePackageName(String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}

	public String getServicePackageName() {
		return servicePackageName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public Date getServicePackageExpiryDate() {
		return servicePackageExpiryDate;
	}

	public void setServicePackageExpiryDate(Date servicePackageExpiryDate) {
		this.servicePackageExpiryDate = servicePackageExpiryDate;
	}

	public Integer getTimezoneOffset() {
		return timezoneOffset;
	}

	public void setTimezoneOffset(Integer timeOffset) {
		this.timezoneOffset = timeOffset;
	}
	
	public Date addDateOffset(Date date){
		return DateConverter.addDateOffset(date, getTimezoneOffset());
	}
	
	public Date removeDateOffset(Date date){
		return DateConverter.removeDateOffset(date, getTimezoneOffset());
	}

	public Integer getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(Integer maxUsers) {
		this.maxUsers = maxUsers;
	}

	@Override
	public Integer getMinDepositAmount() {
		return minDepositAmount;
	}

	public void setMinDepositAmount(Integer minDepositAmount) {
		this.minDepositAmount = minDepositAmount;
	}

	@Override
	public Integer getMaxDepositAmount() {
		return maxDepositAmount;
	}

	public void setMaxDepositAmount(Integer maxDepositAmount) {
		this.maxDepositAmount = maxDepositAmount;
	}
}
