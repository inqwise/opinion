package com.cint;

import com.cint.common.IOrderSubmit;
import com.cint.common.IRequestVoid;
import com.cint.common.ITargetGroupSubmit;
import com.cint.common.OrderEventType;
import com.cint.common.errorHandle.CintBaseOperationResult;
import com.cint.common.errorHandle.CintErrorCode;
import com.cint.common.errorHandle.CintOperationResult;
import com.cint.core.RequestType;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class OrderChangeStatusRequest implements IRequestVoid {

	static ApplicationLog logger = ApplicationLog.getLogger(OrderChangeStatusRequest.class);
	private OrderEventType status = null;
	private String locationId;
	
	@Override
	public String getUrl() {
		return "/orders/" + locationId + "/" + status.toString().toLowerCase();
	}

	@Override
	public CintBaseOperationResult validate() {
		CintBaseOperationResult result = null;
		if(null == status){
			logger.warn("validate : status is mandatory");
			result = new CintBaseOperationResult(CintErrorCode.StatusIsMandatory);
		}
		
		if(null == result && !(status == OrderEventType.Release || status == OrderEventType.Cancel)){
			logger.warn("validate : only OrderEventType.Release and OrderEventType.Cancel supported. Supplied: '%s'", status);
			result = new CintBaseOperationResult(CintErrorCode.StatusIsMandatory);
		}
		
		if(null == result && null == locationId){
			logger.warn("validate : locationId must be positive. Supplied: '%s'", locationId);
			result = new CintBaseOperationResult(CintErrorCode.LocationIsMandatory);
		}
		
		return result;
	}

	@Override
	public RequestType getRequestType() {
		return RequestType.Put;
	}

	@Override
	public String toXml() {
		return "";
	}

	public OrderEventType getStatus() {
		return status;
	}

	public void setStatus(OrderEventType status) {
		this.status = status;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	
public static void main(String[] args) {
		
		String locationId = null;
		CintBaseOperationResult result = null;
		System.out.println("Creating Order purchase request");
		OrderPurchaseRequest request = new OrderPurchaseRequest();
		request.setInitialStatus(OrderEventType.Hold);
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
			locationId = OrderPurchaseRequest.identifyLocationId(responseResult.getValue());
			System.out.println("Order location: " + locationId);
		}
		
		if(null == result){
			System.out.println();
			System.out.println();
			System.out.println();
			
			System.out.println("Change status to cancel");
			OrderChangeStatusRequest changeStatusRequest = new OrderChangeStatusRequest();
			changeStatusRequest.setLocationId(locationId);
			changeStatusRequest.setStatus(OrderEventType.Cancel);
			
			CintApiService service2 = new CintApiService();
			System.out.println("Send request to Cint");
			CintBaseOperationResult chageStatusResult = service2.call(changeStatusRequest);
			if(chageStatusResult.hasError()){
				result = chageStatusResult;
			} else{
				System.out.println("Order canceled");
			}
		}
		
		if(null != result){
			System.out.println("Error: " + result.toString());
		}
	}

@Override
public boolean isRequiredSignature() {
	return true;
}

}
