package com.inqwise.opinion.automation.actions;

import com.inqwise.opinion.automation.common.IEventAction;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.pay.ICharge;
import com.inqwise.opinion.library.common.pay.IChargePayRequest;
import com.inqwise.opinion.library.managers.ChargesManager;

public class PayChargeAction implements IEventAction, IChargePayRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7263534218728459487L;
	private long chargeId;
	private Long userId;
	private int sourceId;
	private String sessionId;
	private String geoCountryCode;
	private String clientIp;
	private ICharge charge;
	
	private static final ApplicationLog logger = ApplicationLog.getLogger(PayChargeAction.class);
	
	public PayChargeAction(long chargeId) {
		this.chargeId = chargeId;
	}
	
	@Override
	public IOperationResult run() {
		IOperationResult result = null;
		OperationResult<ICharge> chargeResult = ChargesManager.getCharge(chargeId, null);
		if(chargeResult.hasError()){
			result = chargeResult;
		} else {
			charge = chargeResult.getValue();
		}
		
		if(null == result){
			result =  ChargesManager.payCharge(this, false);
		}
		return result;
	}

	public PayChargeAction withUserId(long userId){
		this.userId = userId;
		return this;
	}

	public PayChargeAction withSourceId(int sourceId){
		this.sourceId = sourceId;
		return this;
	}
	
	public PayChargeAction withSessionId(String sessionId){
		this.sessionId = sessionId;
		return this;
	}
	
	public PayChargeAction withGeoCountryCode(String geoCountryCode){
		this.geoCountryCode = geoCountryCode;
		return this;
	}
	
	public PayChargeAction withClientIp(String clientIp){
		this.clientIp = clientIp;
		return this;
	}
	
	@Override
	public long getAccountId() {
		return charge.getAccountId();
	}

	@Override
	public long getChargeId() {
		return chargeId;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public double getAmount() {
		return charge.getAmount();
	}

	@Override
	public int getSourceId() {
		return sourceId;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public String getGeoCountryCode() {
		return geoCountryCode;
	}

	@Override
	public String getClientIp() {
		return clientIp;
	}
}
