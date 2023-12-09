package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.NetworkHelper;
import com.inqwise.opinion.library.common.IGateway;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.ISession;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.users.ICompleteLoginResult;
import com.inqwise.opinion.library.common.users.IRegisterDetails;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.GatewaysManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.opinion.facade.front.ClientSessionDescryptor;

public class SocialAuthLoginHandler extends HttpServlet {

	static int[] DEFAULT_PORTS = new int[] {80, 443};
	static ApplicationLog logger = ApplicationLog.getLogger(SocialAuthLoginHandler.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -4140094983614095898L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("SocialAuthLoginHandler: received login request");
		BaseOperationResult result = null;
		String redirectUrl = null;
		URL applicationUrl = null;
		String lang = "en-us";
		try {
			applicationUrl = ArrayUtils.contains(DEFAULT_PORTS, req.getServerPort()) ? new URL(req.getScheme(), req.getServerName(), req.getContextPath()) : new URL(req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath());
			ISession session;
			ICompleteLoginResult completeLoginData;
			String url = req.getRequestURI();
			String key = StringUtils.substringBefore(StringUtils.substringAfter(url, "/k:"), "/");
			
			IProduct product = null;
			if (null == result){
				product = ProductsManager.getCurrentProduct();
				if(null == product){
					result = new OperationResult<IRegisterDetails>(ErrorCode.NoResults);
				}
			}
			
			final String clientIp = getClientIp(req);
			OperationResult<ICompleteLoginResult> loginResult = UsersManager.completeLogin(key, req, clientIp);
			if(loginResult.hasError()){
				result = loginResult;
			} else {
				completeLoginData = loginResult.getValue(); 
				session = completeLoginData.getSession();
				redirectUrl = completeLoginData.getReturnUrl();
				
				if(null == redirectUrl){
					redirectUrl = identifyPostRegisterRedirectUrl(completeLoginData.getCharge(), applicationUrl, lang, completeLoginData.getUser(), completeLoginData.getCountLogins());
				}
				
				// Set session cookies
				addUserIdToSession(session.getUser().getUserId(), product.getId(), clientIp, session.isPersist(), resp);
				addSessionIdToSession(session.getSessionId(), session.isPersist(), resp);
			}
		} catch (Exception e) {
			UUID errorTicket = logger.error(e, "doGet: unexpected error occured");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorTicket);
		}
		
		if(null == result){
			if(null == redirectUrl){
				redirectUrl = applicationUrl + "/" + lang + "/dashboard";;
			}
			resp.sendRedirect(redirectUrl);
		} else {
			resp.sendRedirect(applicationUrl + "/"+ lang +"/login?external-login-error=" + result.getError());
		}
	}
	
	public void addUserIdToSession(Long userId, int productId, String clientIp, boolean untilEndSession, HttpServletResponse response) throws IOException {
		ClientSessionDescryptor.addUserIdToSession(userId, productId, clientIp, untilEndSession, response);
	}

	public void addSessionIdToSession(UUID sessionId, boolean isPersist, HttpServletResponse response) {
		ClientSessionDescryptor.addSessionIdToSession(sessionId, isPersist, response);
	}
	
	private static String identifyPostRegisterRedirectUrl(ICharge charge, URL applicationUrl, String lang, IUser user, int countOfLogigns) {
		String redirectUrl;
		
		if(null == charge){
			IGateway gateway = null;
			if(countOfLogigns == 1 && null != user.getGatewayId()){
				OperationResult<IGateway> gatewayResult = GatewaysManager.get(user.getGatewayId(), true);
				
				if(gatewayResult.hasValue()){
					gateway = gatewayResult.getValue();
				}
			}
			
			if(null == gateway || null == gateway.getFirstLoginLandingPage()){
				redirectUrl = applicationUrl + "/" + lang + "/dashboard"; 
			} else {
				redirectUrl = applicationUrl + "/" + lang + "/" + gateway.getFirstLoginLandingPage();
			}
		} else {
			redirectUrl = applicationUrl + "/" + lang + "/make-payment?return_url=dashboard&charges=" + charge.getId();
		}
		return redirectUrl;
	}
	
	public static String getClientIp(HttpServletRequest request){
		String remoteAddr = request.getRemoteAddr();
		String xForwardedForHeader = request.getHeader(NetworkHelper.X_FORWARDED_FOR_HEADER_NAME);
		remoteAddr = NetworkHelper.identifyClientIp(remoteAddr, xForwardedForHeader);
		return remoteAddr;    
	}
}
