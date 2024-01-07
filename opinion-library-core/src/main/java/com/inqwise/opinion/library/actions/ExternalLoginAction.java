package com.inqwise.opinion.library.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Permission;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.exception.UserDeniedPermissionException;
import org.brickred.socialauth.util.SocialAuthUtil;

import com.inqwise.opinion.automation.EventsServiceClient;
import com.inqwise.opinion.automation.common.events.LoginEventArgs;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.Convert;
import com.inqwise.opinion.infrastructure.systemFramework.GeoIpManager;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.ISession;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.basicTypes.EntityBox;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.invites.IInvite;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.common.users.ICompleteLoginResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.common.users.LoginProvider;
import com.inqwise.opinion.library.dao.UsersDataAccess;
import com.inqwise.opinion.library.entities.SessionEntity;
import com.inqwise.opinion.library.managers.InvitesManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.ServicePackagesManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class ExternalLoginAction {

	private static ApplicationLog logger = ApplicationLog.getLogger(new Object() { }.getClass().getEnclosingClass());
	private SocialAuthManager manager;
	private String returnUrl;
	private Integer servicePackageId;
	private Integer countOfMonths;
	private String key;
	private String clientIp;
	private int sourceId;
	private int productId;
	private Long gatewayId;
	private boolean persistLogin;
	private IServicePackage servicePackage = null;
	private ICharge charge = null;
	private IUser user;
	private String userName;
	private ExternalUserResult externalUserResult;
	private LoginProvider provider;
	private String inviteExternalId;
	
	public String getKey() {
		return key;
	}

	public ExternalLoginAction() throws Exception {
		manager = generateManager();
		key = Convert.getUniqueKey();
	}
	
	public OperationResult<String> generateLoginUrl(LoginProvider provider, String clientIp, int sourceId,
			int productId, Long gatewayId){
		
		OperationResult<String> result = new OperationResult<String>();
		try{
			this.setClientIp(clientIp);
			this.setSourceId(sourceId);
			this.productId = productId;
			this.setGatewayId(gatewayId);
			this.provider = provider;
			String url = manager.getAuthenticationUrl(provider.getSocialAuthId(), buildSuccessUrl(key, provider), Permission.AUTHENTICATE_ONLY);
			
			if(null == url){
				UUID errorTicket = logger.error("getLoginUrl : No Url received from method: getAuthenticationUrl.");
				result.setError(ErrorCode.NoResults, errorTicket);
			} else {
				result.setValue(url);
			}
		} catch (Exception ex) {
			UUID errorCode = logger.error("getLoginUrl: Unexpected error occured");
			result.setError(ErrorCode.GeneralError, errorCode);
		}
		return result;
	}
	
	private SocialAuthManager generateManager() throws Exception{
		SocialAuthConfig conf = SocialAuthConfig.getDefault();
		FileInputStream fr = new FileInputStream (ApplicationConfiguration.SocialAuth.getConfigPath());
		conf.load(fr);
		fr.close();
		SocialAuthManager manager = new SocialAuthManager();
		manager.setSocialAuthConfig(conf);
		return manager;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public Integer getServicePackageId() {
		return servicePackageId;
	}

	public void setServicePackageId(Integer servicePackageId) {
		this.servicePackageId = servicePackageId;
	}

	public Integer getCountOfMonths() {
		return countOfMonths;
	}

	public void setCountOfMonths(Integer countOfMonths) {
		this.countOfMonths = countOfMonths;
	}
	
	private static String buildSuccessUrl(String key, LoginProvider provider) throws UnsupportedEncodingException {
		StringBuilder successUrl = new StringBuilder(ApplicationConfiguration.Opinion.getSecureUrl()).append("/socialAuthLogin").append("/k:").append(key);
		successUrl.append("/p:").append(provider.getValue());
		return successUrl.toString();
	}
	
	public OperationResult<ICompleteLoginResult> completeLogin(HttpServletRequest request, String clientIp){
		
		OperationResult<ICompleteLoginResult> result = null;
		try{
			if(!StringUtils.equalsIgnoreCase(getClientIp(), clientIp)){
				logger.warn("completeLogin: clientIp '%s' not equal to requested '%s'", clientIp, getClientIp());
			}
			
			Profile profile = null;
			if(null == result){
				if(null == manager){
					result = new OperationResult<ICompleteLoginResult>(ErrorCode.NoResults);
				} else {
					Map<String, String> parameters = SocialAuthUtil.getRequestParametersMap(request);
					AuthProvider provider = manager.connect(parameters);
					profile = provider.getUserProfile();
				}
			}
			
			IProduct product = null;
			if(null == result){
				OperationResult<IProduct> productResult = ProductsManager.getProductById(productId);
				if(productResult.hasError()){
					result = productResult.toErrorResult();
				} else {
					product = productResult.getValue();
				}
			}
			
			IInvite invite = null;
			if(null == result && null != getInviteExternalId()){
				OperationResult<IInvite> inviteResult = InvitesManager.getInvite(getInviteExternalId());
				if(inviteResult.hasError()){
					result = inviteResult.toErrorResult();
				} else {
					invite = inviteResult.getValue();
				}
			}
			
			UUID sessionId = null;
			if(null == result){
				// generate sessionId
				sessionId = SessionEntity.generateSessionId();
				BaseOperationResult externalUserResult = setExternalUser(profile, clientIp, product, isPersistLogin(), sessionId, invite);
				if(externalUserResult.hasError()){
					result = externalUserResult.toErrorResult();
				} 
			}
			
			if(null == result){
				SessionEntity session = new SessionEntity(sessionId, user, clientIp, isPersistLogin());
				result = new OperationResult<ICompleteLoginResult>(generateCompleteLoginResult(session, user, charge, getReturnUrl(), externalUserResult.getCountOfLogins()));
			}
			
		} 
		catch(SocialAuthException ex){
			UUID errorTicket = logger.error(ex, "completeLogin: SocialAuthManager.connect() Failed");
			result = new OperationResult<ICompleteLoginResult>(ErrorCode.NotPermitted, errorTicket, ex.getMessage());
		}
		catch(UserDeniedPermissionException ex){
			result = new OperationResult<ICompleteLoginResult>(ErrorCode.NotPermitted);
		}
		catch(Exception ex){
			UUID errorTicket = logger.error(ex, "completeLogin: Failed to complete login");
			result = new OperationResult<ICompleteLoginResult>(ErrorCode.GeneralError, errorTicket);
		}
		
		return result;
	}
	
	private static ICompleteLoginResult generateCompleteLoginResult(final ISession session, final IUser user, final ICharge charge, final String returnUrl, final int countOfLogins){
		return new ICompleteLoginResult() {
			
			public ISession getSession() {
				return session;
			}
			
			public String getReturnUrl() {
				return returnUrl;
			}
			
			public ICharge getCharge() {
				return charge;
			}
			
			public IUser getUser() {
				return user;
			}
			
			public int getCountLogins(){
				return countOfLogins;
			}
		};
	}
	
	private class ExternalUserResult{
		
		private int countOfLogins;
		private long userId;
		
		public ExternalUserResult(ResultSet reader) throws SQLException {
			setCountOfLogins(reader.getInt("count_of_logins"));
			setUserId(reader.getLong("user_id"));
		}
		public int getCountOfLogins() {
			return countOfLogins;
		}
		public void setCountOfLogins(int countOfLogins) {
			this.countOfLogins = countOfLogins;
		}
		public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		
	}
	
	private BaseOperationResult setExternalUser(final Profile profile, String clientIp,
			final IProduct product, boolean persist, UUID sessionId, IInvite invite)
			throws IOException, Exception {
		
		final BaseOperationResult result = new BaseOperationResult();
		final EntityBox<ExternalUserResult> externalUserResultBox = new EntityBox<ExternalLoginAction.ExternalUserResult>();
		
		if(null == profile.getEmail()){
			logger.warn("setExternalUser: User without email. profile: {%s}", profile);
			result.setError(ErrorCode.InvalidEmail);
		}
		
		String displayName = null;
		
		if (!result.hasError() && null != invite && !invite.getEmail().equalsIgnoreCase(profile.getEmail())){
			logger.warn("setExternalUser: User email not equal to invite. profile: {%s}, invite: '%s'", profile, invite.getId());
			result.setError(ErrorCode.InvalidEmail);
		}
		
		if (!result.hasError()){
			userName = profile.getEmail();
			displayName = profile.getFullName();
			servicePackage = product.getServicePackage(getServicePackageId());
		}
		
		if (!result.hasError()){
			IResultSetCallback callback = new IResultSetCallback() {
				public void call(ResultSet reader, int generationId) throws Exception {
					while (reader.next()) {
						switch (generationId) {
						case 0:
							externalUserResultBox.setValue(new ExternalUserResult(reader));
							
							break;
						default:
							throw new OperationNotSupportedException("setExternalUser() : generationId not supported. Value: '" + generationId);
						}
		            }
				}				
			};
			
			// get geoCountry
			String countryCode = GeoIpManager.getInstance().getCountryCode(clientIp);
			
			UsersDataAccess.setExternalUser(userName, displayName, profile.getValidatedId(), profile.getEmail(), clientIp,
											countryCode, sessionId, getSourceId(), product.getId(), callback, profile.getFirstName(),
											profile.getLastName(),
											(servicePackage.getAmount() > 0 ? product.getDefaultServicePackage().getId() : getServicePackageId()),
											getGatewayId(), provider.getValue(), (null == invite ? null : invite.getAccountId()));
		}
		
		if(!result.hasError()){
			if(externalUserResultBox.hasValue()){
				externalUserResult = externalUserResultBox.getValue();
			} else {
				throw new Exception("setExternalUser() : No results");
			}
		}
		
		if(!result.hasError()){
			OperationResult<IUser> userResult = UsersManager.getUser(externalUserResult.getUserId(), productId);
			if(userResult.hasError()){
				result.setError(userResult);
			} else {
				user = userResult.getValue();
			}
		}
		
		List<IAccount> accounts = null;
		if(!result.hasError()){
			OperationResult<List<IAccount>> accountsResult = user.getAccounts(product.getId());
			if(accountsResult.hasError()){
				result.setError(accountsResult);
			} else {
				accounts = accountsResult.getValue();
				
				// check for account
				if(accounts.size() == 0){
					logger.warn("User '%s' trying to login into product '%s' without account.", userName, product.getId());
					result.setError(ErrorCode.NotAllowedForProduct);
				}
			}
		}
		
		if(!result.hasError()){
			// Send Login Event
			try {
				EventsServiceClient.getInstance().fire(new LoginEventArgs(sourceId, product.getId(), user.getUserName(), user.getUserId(), GeoIpManager.getInstance().getCountryName(clientIp), user.getEmail(), GeoIpManager.getInstance().getCountryCode(clientIp), sessionId.toString(), clientIp, false));
			} catch (Exception e) {
				logger.error(e, "failed to fireLoginEvent");
			}
		}
		
		// delete invite
		if(!result.hasError() && null != invite){
			InvitesManager.deleteInvite(invite.getId(), invite.getAccountId());
		}
		
		// create charge for new user
		if(!result.hasError()){
			if(servicePackage.getAmount() > 0 && externalUserResult.getCountOfLogins() == 0){
				OperationResult<ICharge> chargeResult = ServicePackagesManager.createCharge(servicePackage, getCountOfMonths(), user.getDefaultAccount(product.getId()), user.getUserId(), false);
				if(chargeResult.hasError()){
					result.setError(chargeResult);
				} else {
					charge = chargeResult.getValue();
				}
			}
		}
		
		return result;
	}

	public String getClientIp() {
		return clientIp;
	}

	private void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public int getSourceId() {
		return sourceId;
	}

	private void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public Long getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Long gatewayId) {
		this.gatewayId = gatewayId;
	}

	public void setIsPersistLogin(boolean isPersist) {
		this.persistLogin = isPersist;
	}
	
	public boolean isPersistLogin(){
		return this.persistLogin;
	}

	public String getInviteExternalId() {
		return inviteExternalId;
	}

	public void setInviteExternalId(String inviteExternalId) {
		this.inviteExternalId = inviteExternalId;
	}
}
