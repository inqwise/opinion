package com.inqwise.opinion.common.audit;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;


public class AuditData {
	public static final String ENCRYPTED_VALUE = "[encrypted]";
	List<AuditDataItem> items;
	
	public class AuditDataItem {
		private static final String NAME_KEY = "name";
		private static final String VALUE_KEY = "value";
		private static final String OLD_VALUE_KEY = "oldValue";
		private static final String NEW_VALUE_KEY = "newValue";
		private static final String TYPE_KEY = "type";
		
		String name;
		String value;
		String toValue;
		AuditDataItemType type;
		
		private AuditDataItem(String name, String value, String toValue, AuditDataItemType type){
			this.name = name;
			this.value = value;
			this.toValue = toValue;
			this.type = type;
		}
		
		public JSONObject toJson() throws AuditDataException{
			try{
				JSONObject jo = new JSONObject().put(TYPE_KEY, type.getValue()).put(NAME_KEY, name);
				switch(type){
				case Fixed:
					jo.put(VALUE_KEY, value);
					break;
				case Changed:
					jo.put(OLD_VALUE_KEY, JSONHelper.getNullable(value));
					jo.put(NEW_VALUE_KEY, JSONHelper.getNullable(value));
					break;
				default:
					throw new AuditDataException("toJson() : Unimplemented type: " + type);
				}
				
				return jo;
			} catch(JSONException e){
				throw new AuditDataException(e);
			}
		}
		
		@Override
		public String toString() {
			try {
				return toJson().toString();
			} catch (AuditDataException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public AuditData(){
		items = new ArrayList<AuditDataItem>();
	}
	
	public void AddFixedItem(String name, String value){
		items.add(new AuditDataItem(name, value, null, AuditDataItemType.Fixed));
	}
	
	public void AddChangedItem(String name, String fromValue, String toValue){
		items.add(new AuditDataItem(name, fromValue, toValue, AuditDataItemType.Changed));
	}
	
	@Override
	public String toString() {
		return StringUtils.join(items, ',');
	}
}
