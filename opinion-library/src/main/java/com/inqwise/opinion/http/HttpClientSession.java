/**
 * 
 */
package com.inqwise.opinion.opinion.http;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;

/**
 * @author basil
 * 
 */
public class HttpClientSession extends BaseOperationResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7157896907642270849L;
	private String sessionId;
	private Long userId;
	private String clientIp;
	private int loginProductId;
	private String loginClientIp;

	/**
	 * 
	 */
	public HttpClientSession() {
		super();
	}

	public HttpClientSession(String sessionId, Long userId, String clientIp, int productId, String loginClientIp) {
		super();
		this.sessionId = sessionId;
		this.userId = userId;
		this.clientIp = clientIp;
		this.loginProductId = productId;
		this.loginClientIp = loginClientIp;
	}

	public HttpClientSession(UUID errorId, String errorDescription) {
		super(ErrorCode.GeneralError, errorId, errorDescription);
	}
	
	public HttpClientSession(ErrorCode code, String description){
		super(code, description);
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public int getLoginProductId() {
		return loginProductId;
	}

	public void setLoginProductId(int productId) {
		this.loginProductId = productId;
	}

	public String getLoginClientIp() {
		return loginClientIp;
	}

	public void setLoginClientIp(String loginClientIp) {
		this.loginClientIp = loginClientIp;
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		if(hasError()){
			return super.toJson();
		} else {
			return new JSONObject(this);
		}
	}
}
