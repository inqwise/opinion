package com.inqwise.opinion.opinion.entities.controls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.opinion.common.ControlType;
import com.inqwise.opinion.opinion.common.IControl;
import com.inqwise.opinion.opinion.common.IControlRequest;
import com.inqwise.opinion.opinion.common.ParentType;
import com.inqwise.opinion.opinion.common.analizeResults.IControlsContainer;

public class MatrixControlEntity extends OptionListControlEntity implements IControlsContainer<IControl>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1961072344821879811L;
	private List<IControl> controls;
	private static ApplicationLog logger = ApplicationLog.getLogger(MatrixControlEntity.class);
	
	public MatrixControlEntity(ResultSet reader) throws Exception {
		super(reader, ControlType.Matrix);
	}

	public MatrixControlEntity() {
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jsonObject = super.toJson();
		jsonObject.put(JsonNames.SUB_CONTROLS, new JSONObject().put(JsonNames.LIST, subControlsToJson()));
		return jsonObject;
	}

	public List<IControl> getControls() {
		return controls;
	}

	@Override
	public void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		super.fill(reader, controlType);
	}

	private JSONArray subControlsToJson() throws JSONException {

		JSONArray jsonControls = new JSONArray();
		if (null != controls) {
			for (IControl control : controls) {
				jsonControls.put(((ControlEntity) control).toJson());
			}
		}
		return jsonControls;
	}

	@Override
	public BaseOperationResult importRequest(IControlRequest request) {
		return super.importRequest(request);
	}
	
	@Override
	public OperationResult<Long> copyTo(Long opinionId, Long parentId,
			Integer parentTypeId, Long translationId, Integer orderId,
			String content, String actionGuid, long userId, long accountId) {
		
		OperationResult<Long> result = super.copyTo(opinionId, parentId, parentTypeId, translationId, orderId, content, actionGuid, userId, accountId);
		
		Long controlId = null;
		if(!result.hasError()){
			controlId = result.getValue();
		}
		
		if(!result.hasError() && null != controls){
			for(IControl controlInterface : controls){
				ControlEntity subControl = (ControlEntity) controlInterface;
				OperationResult<Long> subControlResult = subControl.copyTo(opinionId, controlId,
																ParentType.Control.getValue(), translationId,
																null, null, null, userId, accountId);
				if(subControlResult.hasError()){
					result = subControlResult;
					break;
				} 
			}
		}
		
		return result;
	}

	@Override
	public void addControl(IControl control) {
		if(null == controls){
			controls = new ArrayList<IControl>();
		}
		controls.add(control);
	}
	
	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = super.getExportJson();
		output.put(JsonNames.SUB_CONTROLS, getSubControlsExportJson());
		return output;
	}
	
	private JSONArray getSubControlsExportJson() throws JSONException {
		JSONArray jsonControls = new JSONArray();
		if (null != controls) {
			for (IControl control : controls) {
				jsonControls.put(control.getExportJson());
			}
		}
		return jsonControls;
	}
}
