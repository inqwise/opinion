package com.inqwise.opinion.entities.analizeResults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.common.ControlType;
import com.inqwise.opinion.common.analizeResults.IAnalizeControl;
import com.inqwise.opinion.common.analizeResults.IAnalizeOption;
import com.inqwise.opinion.common.analizeResults.IControlsContainer;
import com.inqwise.opinion.common.analizeResults.IOptionsContainer;

public class AnalizeMatrixControlEntity extends AnalizeAdvancedControlEntity implements IControlsContainer<IAnalizeControl>, IOptionsContainer<IAnalizeOption> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ApplicationLog logger = ApplicationLog
	.getLogger(AnalizeMatrixControlEntity.class);
	
	private List<IAnalizeControl> subControls = new ArrayList<IAnalizeControl>();
	private List<IAnalizeOption> options = new ArrayList<IAnalizeOption>();
	
	@Override
	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		// TODO Auto-generated method stub
		super.fill(reader, controlType);
	}

	@Override
	public void addControl(IAnalizeControl control) {
		subControls.add(control);
		
	}

	@Override
	public List<IAnalizeControl> getControls() {
		return subControls;
	}

	@Override
	public void addOption(IAnalizeOption option) {
		options.add(option);		
	}

	@Override
	public List<IAnalizeOption> getOptions() {
		return options;
	}
	
	@Override
	public JSONObject toJson(boolean onlyPercentageInStatistics) throws JSONException {
		JSONObject result = super.toJson(onlyPercentageInStatistics);
		
		JSONArray controlsJa = new JSONArray();
		// Render controls
		if(!subControls.isEmpty()){
			for(IAnalizeControl control : subControls){
				controlsJa.put(control.toJson(onlyPercentageInStatistics));
			}
		}
		result.put("controls", new JSONObject().put("list", controlsJa));
		
		JSONArray optionsJa = new JSONArray();
		// render Options
		if(!options.isEmpty()){
			for(IAnalizeOption option : options){
				optionsJa.put(option.toJson(onlyPercentageInStatistics));
			}
		}
		result.put("options", new JSONObject().put("list", optionsJa));
		return result;
	}
}
