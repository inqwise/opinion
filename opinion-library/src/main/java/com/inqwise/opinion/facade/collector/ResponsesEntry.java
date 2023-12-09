package com.inqwise.opinion.opinion.facade.collector;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.basicTypes.EntityBox;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.common.GuidType;
import com.inqwise.opinion.opinion.common.ICollectorPostmasterContext;
import com.inqwise.opinion.opinion.common.IPostmasterObject;
import com.inqwise.opinion.opinion.common.ResultsPermissionType;
import com.inqwise.opinion.opinion.common.analizeResults.IAnalizeControl;
import com.inqwise.opinion.opinion.common.collectors.ICollector;
import com.inqwise.opinion.opinion.common.collectors.IPollsCollector;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.common.opinions.IPoll;
import com.inqwise.opinion.opinion.managers.ResultsManager;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;

public class ResponsesEntry extends Entry implements IPostmasterObject {
	static ApplicationLog logger = ApplicationLog.getLogger(ResponsesEntry.class);
	
	//private static Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
	
	public ResponsesEntry(ICollectorPostmasterContext context){
		super(context);
	}
	
	public JSONObject getResults(JSONObject input) throws JSONException {
		JSONObject output = new JSONObject();
		BaseOperationResult result = null;
		
		final String guid = JSONHelper.optString(input, "guid");
		final GuidType guidType = GuidType.fromInt(input.optInt("guidTypeId"));
		
		IOpinion opinion = null;
		List<IAnalizeControl> controls = null;
		EntityBox<ICollector> collectorBox = new EntityBox<>();
		
		OperationResult<IOpinion> opinionResult = getOpinion(guid, guidType, collectorBox);
		if(opinionResult.hasError()){
			result = opinionResult;
		} else {
			opinion = opinionResult.getValue();
		}
		
		ResultsPermissionType resultsType = null;
		if(null == result){
			resultsType = (opinion instanceof IPoll) ? ResultsPermissionType.All : ResultsPermissionType.None;
			if(collectorBox.hasValue()){
				if(collectorBox.getValue() instanceof IPollsCollector){
					resultsType = ((IPollsCollector) collectorBox.getValue()).getResultsType();
				}
			}
			
			if(resultsType == ResultsPermissionType.None || resultsType == ResultsPermissionType.Undefinded){
				logger.warn("Trying to get results for not allowed opinion #%s", opinion.getId());
				result = new BaseOperationResult(ErrorCode.NotPermitted);
			}
		}
		
		if(null == result){
			OperationResult<List<IAnalizeControl>> controlsResult = ResultsManager
					.getAnalizeControls(opinion.getId(), null, null, null, false, false, false);
			
			if(controlsResult.hasError()){
				result = controlsResult;
			} else {
				controls = controlsResult.getValue();
			}
		}
		
		if(null == result){
			
			JSONArray controlsJa = new JSONArray();
			for (IAnalizeControl control : controls) {
				controlsJa.put(control.toJson(resultsType == ResultsPermissionType.Percentage));
			}

			output.put("controls", new JSONObject().put("list",controlsJa));
		} else {
			output = result.toJson();
		}

		return output;
	}
}
