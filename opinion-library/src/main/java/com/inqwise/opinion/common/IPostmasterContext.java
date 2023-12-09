package com.inqwise.opinion.opinion.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.opinion.http.HttpClientSession;

public interface IPostmasterContext {
	String getClientIp();
	URL getAbsoluteURL() throws MalformedURLException;
	URL getApplicationURL() throws MalformedURLException;
	void addUserIdToSession(Long userId, int productId, String clientIp, boolean untilEndSession) throws UnsupportedEncodingException, IOException;
	void addSessionIdToSession(UUID sessionId, boolean untilEndSession) throws UnsupportedEncodingException;
	public abstract OperationResult<IUser> getLoggedInUser() throws IOException, NullPointerException, ExecutionException;
	public abstract IProduct getCurrentProduct();
	public abstract List<IAccount> getLoggedInUserAllowedAccounts() throws IOException, NullPointerException, ExecutionException;
	public abstract OperationResult<Long> getUserId() throws IOException;
	@Deprecated
	/**
	 * @deprecated Use getAccount instead
	 */
	public abstract IAccount getDefaultAccount() throws IOException, NullPointerException, ExecutionException;
	IOperationResult validateSignIn() throws NullPointerException, ExecutionException;
	boolean IsSignedIn() throws NullPointerException, ExecutionException;
	URL getApplicationURL(boolean secure) throws MalformedURLException;
	URL getAbsoluteURL(boolean secure) throws MalformedURLException;
	HttpClientSession getSession() throws IOException;
	void setHeader(String key, String value);
	void setContentType(String string);
	void sendRedirect(String string);
	String getRequestUrl();
	public abstract URL getServerUrl() throws MalformedURLException;
	public abstract URL getServerUrl(boolean secure)
			throws MalformedURLException;
	public abstract OperationResult<IAccount> getAccount();
}
