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
import com.inqwise.opinion.common.analizeResults.IAnalizeOption;
import com.inqwise.opinion.common.analizeResults.IOptionsContainer;

public class AnalizeOptionListControlEntity extends AnalizeAdvancedControlEntity implements IOptionsContainer<IAnalizeOption> {
	
	private static ApplicationLog logger = ApplicationLog
	.getLogger(AnalizeOptionListControlEntity.class);
	
	private List<IAnalizeOption> options = new ArrayList<IAnalizeOption>();
	
	@Override
	protected void fill(ResultSet reader, ControlType controlType)
			throws SQLException, Exception {
		// TODO Auto-generated method stub
		super.fill(reader, controlType);
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
		JSONArray optionsJa = new JSONArray();
		if(!options.isEmpty()){
			for(IAnalizeOption option : options){
				optionsJa.put(option.toJson(onlyPercentageInStatistics));
			}
		}
		result.put("options", new JSONObject().put("list", optionsJa));
		return result;
	}
}
