package com.inqwise.opinion.common.collectors;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class CollectorModel {
	
	private Long id;

	private CollectorModel(Builder builder) {
		this.id = builder.id;
	}

	public static final class Keys{
		public static String ID = "id";		
	}	
	
	public CollectorModel(JSONObject json) {
		id = json.optLongObject(Keys.ID);
	}
	
	public Long getId() {
		return id;
	}
	
	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.ID, id);
		return json;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(CollectorModel collectorModel) {
		return new Builder(collectorModel);
	}

	public static final class Builder {
		private Long id;

		private Builder() {
		}

		private Builder(CollectorModel collectorModel) {
			this.id = collectorModel.id;
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public CollectorModel build() {
			return new CollectorModel(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).toString();
	}
	
}
