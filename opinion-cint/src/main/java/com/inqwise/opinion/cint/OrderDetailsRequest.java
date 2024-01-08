package com.inqwise.opinion.cint;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IOrder;
import com.inqwise.opinion.cint.common.IRequest;
import com.inqwise.opinion.cint.common.errorHandle.CintBaseOperationResult;
import com.inqwise.opinion.cint.common.errorHandle.CintErrorCode;
import com.inqwise.opinion.cint.common.errorHandle.CintOperationResult;
import com.inqwise.opinion.cint.core.RequestType;
import com.inqwise.opinion.cint.entities.OrderEntity;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class OrderDetailsRequest implements IRequest<IOrder> {

	static ApplicationLog logger = ApplicationLog.getLogger(OrderDetailsRequest.class);
	private String locationId;
	
	@Override
	public String getUrl() {
		return "/orders/" + locationId;
	}

	@Override
	public String toXml() {
		return null;
	}

	@Override
	public IOrder parseResponse(Document doc) {
		return new OrderEntity((Element)doc.getFirstChild());
	}

	@Override
	public IOrder parseResponse(String raw) {
		throw new Error("Not implemented");
	}

	@Override
	public CintBaseOperationResult validate() {
		CintBaseOperationResult result = null;
		if(null == locationId){
			logger.warn("validate : location is mandatory.");
			result = new CintBaseOperationResult(CintErrorCode.LocationIsMandatory);
		}
		return result;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	@Override
	public RequestType getRequestType() {
		return RequestType.Get;
	}
	
	public static void main(String[] args) {
		CintBaseOperationResult result = null;
		System.out.println("Creating Order Details request");
		OrderDetailsRequest request = new OrderDetailsRequest();
		request.setLocationId("951");
		
		CintApiService service = new CintApiService();
		System.out.println("Send request to Cint");
		CintOperationResult<IOrder> responseResult = service.call(request);
		if(responseResult.hasError()){
			result = responseResult;
		} else {
			System.out.println("Order number: " + responseResult.getValue().getOrderNumber());
		}
		
		if(null != result){
			System.out.println("Error: " + result.toString());
		}
	}

	@Override
	public boolean isRequiredSignature() {
		return false;
	}
}
