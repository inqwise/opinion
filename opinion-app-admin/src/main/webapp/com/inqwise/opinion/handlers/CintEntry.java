package com.inqwise.opinion.handlers;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.cint.CintApiService;
import com.inqwise.opinion.cint.OrderDetailsRequest;
import com.inqwise.opinion.cint.common.IOrder;
import com.inqwise.opinion.cint.common.errorHandle.CintOperationResult;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

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
