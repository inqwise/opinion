package com.cint;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import com.cint.common.IOrderSubmit;
import com.cint.common.IRequest;
import com.cint.common.ITargetGroupSubmit;
import com.cint.common.OrderEventType;
import com.cint.common.errorHandle.CintBaseOperationResult;
import com.cint.common.errorHandle.CintErrorCode;
import com.cint.common.errorHandle.CintOperationResult;
import com.cint.core.RequestType;
import com.cint.entities.OrderSubmitEntity;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class OrderPurchaseRequest implements IRequest<String>  {
	
	static ApplicationLog logger = ApplicationLog.getLogger(OrderPurchaseRequest.class);
	private OrderSubmitEntity order;
	private OrderEventType initialStatus;
	
	public OrderPurchaseRequest() {
		order = new OrderSubmitEntity();
		initialStatus = OrderEventType.New;
	}
	
	@Override
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append(order.toXml());
		return sb.toString();
	}
	
	public CintBaseOperationResult validate(){
		
		CintBaseOperationResult result = null;
		if(initialStatus != OrderEventType.New && initialStatus != OrderEventType.Hold){
			logger.warn("validate : Only New or Hold statuses supported. Received: '%s'", initialStatus);
			result = new CintBaseOperationResult(CintErrorCode.NumberOfQuestionsIsOutOfRange);
		}
		return (null == result ? order.validate() : result);
	}
	
	public IOrderSubmit getOrder(){
		return order;
	}

	@Override
	public String getUrl() {
		return "/orders" + (initialStatus == OrderEventType.Hold ? "/hold" : "");
	}
	
	@Override
	public String parseResponse(Document doc) {
		throw new Error("Not implemented");
	}
	
	public static void main(String[] args) {
		
		CintBaseOperationResult result = null;
		System.out.println("Creating Order purchase request");
		OrderPurchaseRequest request = new OrderPurchaseRequest();
		IOrderSubmit order = request.getOrder();
		order.setSurveyTitle("Test title");
		order.setContactCompany("Test contact company");
		order.setContactEmail("TestContactEmail@inqwise.com");
		order.setContactName("Test contact name");
		order.setNumberOfCompletes(10);
		order.setNumberOfQuestions(11);
		order.setSurveyUrl("http://www.inqwise.com");
		
		ITargetGroupSubmit targetGroup = order.getTargetGroup();
		targetGroup.setCountryId("US");
		
		CintApiService service = new CintApiService();
		System.out.println("Send request to Cint");
		CintOperationResult<String> responseResult = service.call(request);
		if(responseResult.hasError()){
			result = responseResult;
		} else {
			System.out.println("Order location: " + responseResult.getValue());
		}
		
		if(null == result){
			System.out.println();
			System.out.println();
			System.out.println();
			
			System.out.println("Creating Order purchase request on hold");
			OrderPurchaseRequest request2 = new OrderPurchaseRequest();
			request.setInitialStatus(OrderEventType.Hold);
			IOrderSubmit order2 = request2.getOrder();
			order2.setSurveyTitle("Test title");
			order2.setContactCompany("Test contact company");
			order2.setContactEmail("TestContactEmail@inqwise.com");
			order2.setContactName("Test contact name");
			order2.setNumberOfCompletes(10);
			order2.setNumberOfQuestions(11);
			order2.setSurveyUrl("http://www.inqwise.com");
			
			ITargetGroupSubmit targetGroup2 = order2.getTargetGroup();
			targetGroup2.setCountryId("US");
			
			CintApiService service2 = new CintApiService();
			System.out.println("Send request to Cint");
			CintOperationResult<String> responseResult2 = service2.call(request);
			if(!responseResult2.hasError()){
				System.out.println("Order location: " + responseResult2.getValue());
			}
		}
		
		if(null != result){
			System.out.println("Error: " + result.toString());
		}
	}

	public OrderEventType getInitialStatus() {
		return initialStatus;
	}

	/*
	 * Only OrderEventType.New or OrderEventType.Hold supported  
	 */
	public void setInitialStatus(OrderEventType initialStatus) {
		this.initialStatus = initialStatus;
	}

	@Override
	public String parseResponse(String raw) {
		return raw;
	}

	@Override
	public RequestType getRequestType() {
		return RequestType.Post;
	}

	public static String identifyLocationId(String orderLocation) {
		if(null == orderLocation){
			throw new Error("identifyLocationId : orderLocation is null");
		}
		
		String result = StringUtils.substringAfterLast(orderLocation, "/"); 
		if(!StringUtils.isNumeric(result)){
			throw new Error(String.format("identifyLocationId : unable to seek locationId from orderLocation '%s'", orderLocation));
		}
		
		return result;
	}

	public String getOrderDetails(){
		return order.getOrderDetails();
	}

	@Override
	public boolean isRequiredSignature() {
		return true;
	}
}
