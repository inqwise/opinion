package com.cint;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cint.common.IOrder;
import com.cint.common.IOrderHistory;
import com.cint.common.IRequest;
import com.cint.common.errorHandle.CintBaseOperationResult;
import com.cint.common.errorHandle.CintErrorCode;
import com.cint.common.errorHandle.CintOperationResult;
import com.cint.core.RequestType;
import com.cint.entities.OrderHistoryEntity;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class OrderHistoryRequest implements IRequest<IOrderHistory> {
	static ApplicationLog logger = ApplicationLog.getLogger(OrderDetailsRequest.class);
	
	private String locationId;
	
	@Override
	public String getUrl() {
		return "/orders/" + locationId + "/history";
	}

	@Override
	public String toXml() {
		return null;
	}

	@Override
	public IOrderHistory parseResponse(Document doc) {
		return new OrderHistoryEntity((Element)doc.getFirstChild());
	}

	@Override
	public IOrderHistory parseResponse(String raw) {
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
		System.out.println("Creating Order History request");
		OrderHistoryRequest request = new OrderHistoryRequest();
		request.setLocationId("951");
		
		CintApiService service = new CintApiService();
		System.out.println("Send request to Cint");
		CintOperationResult<IOrderHistory> responseResult = service.call(request);
		if(responseResult.hasError()){
			result = responseResult;
		} else {
			System.out.println("Count of entities: " + responseResult.getValue().getEntries().size());
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
