package com.inqwise.opinion.handlers;

import org.json.JSONException;
import org.json.JSONObject;

import com.cint.CintApiService;
import com.cint.OrderDetailsRequest;
import com.cint.common.IOrder;
import com.cint.common.errorHandle.CintOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.IPostmasterObject;

public class CintEntry extends Entry implements IPostmasterObject {
	
	static ApplicationLog logger = ApplicationLog.getLogger(CintEntry.class);
	public CintEntry(IPostmasterContext context) {
		super(context);
	}

	public JSONObject getOrderDetails(JSONObject input) throws JSONException{
		JSONObject output;
		String locationId = input.optString("externalId");
		OrderDetailsRequest detailsRequest = new OrderDetailsRequest();
		detailsRequest.setLocationId(locationId);
		
		CintApiService api = new CintApiService();
		CintOperationResult<IOrder> detailsResponse = api.call(detailsRequest);
		if(detailsResponse.hasError()){
			output = detailsResponse.toJson();
		} else {
			output = new JSONObject(detailsResponse.getValue());
		}
		
		return output;
	}
}
