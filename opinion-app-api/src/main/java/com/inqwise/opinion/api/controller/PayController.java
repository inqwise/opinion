package com.inqwise.opinion.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.NotImplementedException;
import org.restexpress.Request;
import org.restexpress.Response;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.NetworkHelper;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.payments.PayProcessorsFactory;
import com.inqwise.opinion.payments.common.IDoExpressCheckoutRequest;
import com.inqwise.opinion.payments.common.IDoExpressCheckoutResponse;
import com.inqwise.opinion.payments.common.IIpnRequest;
import com.inqwise.opinion.payments.common.IPayActionResponse;
import com.inqwise.opinion.payments.common.PayActionTypes;
import com.inqwise.opinion.payments.common.PayProcessorTypes;
import com.inqwise.opinion.payments.common.errorHandle.PayOperationResult;

import io.netty.handler.codec.http.HttpResponseStatus;

public class PayController {
	enum PayControllerAction {
		Undefined(null),
		Ipn("IPN"),
		ExpressCheckout("EXPRESSCHECKOUT");
		
		private PayControllerAction(String value){
			this.value = value;
		}
		
		private String value;
		
		public static PayControllerAction fromString(String value){
			for (PayControllerAction b : PayControllerAction.values()) { 
				if (null != value && value.equalsIgnoreCase(b.value)) { 
		          return b; 
		        }
	        } 
			
			return Undefined;
		}
	} 
	
	static ApplicationLog logger = ApplicationLog.getLogger(PayController.class);

	private static PayController instance;
	public static PayController getInstance() {
		if(null == instance){
			synchronized (PayController.class) {
				if(null == instance){
					instance = new PayController();
				}
			}
		}
		return instance;
	}
	
	public void read(Request request, Response response) throws UnsupportedEncodingException{
		response.addHeader("srv", NetworkHelper.getLocalHostName());
		
		PayProcessorTypes processorType; 
		String strProcessorTypeId = request.getHeader("processorTypeId");
		if(null == strProcessorTypeId){
			logger.warn("ProcessorType is null");
			throw new org.restexpress.exception.NotFoundException();
		}
		
		processorType = PayProcessorTypes.fromInt(Integer.valueOf(strProcessorTypeId));
		if(PayProcessorTypes.Undefined == processorType){
			logger.warn("Undefined processorType. Received: '%s'", strProcessorTypeId);
			throw new org.restexpress.exception.NotFoundException();
		}
		
		Map<String, String> queryStringMap = request.getQueryStringMap();
		int sourceId = ProductsManager.getCurrentProduct().getId();
		
		 String strAction = request.getHeader("action");
		 PayControllerAction action = PayControllerAction.fromString(strAction);
		 switch (action) {
			case ExpressCheckout:
				processExpressCheckout(processorType, sourceId, queryStringMap, response);
				break;
			case Ipn:
				processIpn(processorType, sourceId, queryStringMap);
				break;
			default:
				throw new NotImplementedException(action.toString());
			}
		 return;
	}

	private void processExpressCheckout(PayProcessorTypes processorType, final int sourceId,
			final Map<String, String> queryStringMap, Response response) throws UnsupportedEncodingException {
		try { 
			final Date timestamp = new Date();
			PayOperationResult<IPayActionResponse> result = PayProcessorsFactory.getInstance().getProcessor(processorType).getAction(PayActionTypes.DoExpressCheckout).withRequest(new IDoExpressCheckoutRequest() {
				
				@Override
				public Long getUserId() {
					return null;
				}
				
				@Override
				public Date getTimeStamp() {
					return timestamp;
				}
				
				@Override
				public int getSourceId() {
					return sourceId;
				}
				
				@Override
				public Long getBackofficeUserId() {
					return null;
				}
				
				@Override
				public Long getAccountId() {
					return null;
				}
	
				@Override
				public Map<String, String> getParams() {
					return queryStringMap;
				}
	
				@Override
				public String getClientIp() {
					return null;
				}
			}).process();
			
			response.setResponseCreated();
			
			if(result.hasError()){
				response.setResponseStatus(HttpResponseStatus.SEE_OTHER);
				response.addLocationHeader(ApplicationConfiguration.Opinion.getUrl() + "?error=" + URLEncoder.encode(result.toString(), "UTF8"));
			} else {
				IDoExpressCheckoutResponse tokenResponse = (IDoExpressCheckoutResponse) result.getValue();
				response.setResponseStatus(HttpResponseStatus.SEE_OTHER);
				response.addLocationHeader(tokenResponse.getRedirectUrl() + "?aoid=" + tokenResponse.getAccountOperationId());
			}
		} catch (Throwable t){
			UUID errorTicket = logger.error(t, "processExpressCheckout : unexpected error occured");
			response.setResponseStatus(HttpResponseStatus.SEE_OTHER);
			response.addLocationHeader(ApplicationConfiguration.Opinion.getUrl() + "?errorId=" + errorTicket.toString());
		}
	}

	private void processIpn(PayProcessorTypes processorType, final int sourceId, final Map<String, String> queryStringMap) {		
		final Date timestamp = new Date();
		PayOperationResult<IPayActionResponse> result = PayProcessorsFactory.getInstance().getProcessor(processorType).getAction(PayActionTypes.Ipn).withRequest(new IIpnRequest() {
			
			@Override
			public Long getUserId() {
				return null;
			}
			
			@Override
			public Date getTimeStamp() {
				return timestamp;
			}
			
			@Override
			public int getSourceId() {
				return sourceId;
			}
			
			@Override
			public Long getBackofficeUserId() {
				return null;
			}
			
			@Override
			public Long getAccountId() {
				return null;
			}
			
			@Override
			public Map<String, String> getParams() {
				return queryStringMap;
			}
		}).process();
		
		if(result.hasError()){
			throw new Error(result.toString());
		}
	}
}
