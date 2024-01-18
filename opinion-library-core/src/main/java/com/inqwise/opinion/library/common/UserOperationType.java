package com.inqwise.opinion.library.common;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONObject;
import com.google.common.base.MoreObjects;

public class UserOperationType {
	private int id;
	private String name;

	public UserOperationType(JSONObject json) {
		throw new NotImplementedException("ctor");
	}
	
	private UserOperationType(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}
	
	public JSONObject toJson() {
		throw new NotImplementedException("toJson");
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(UserOperationType userOperationType) {
		return new Builder(userOperationType);
	}

	public static final class Builder {
		private int id;
		private String name;

		private Builder() {
		}

		private Builder(UserOperationType userOperationType) {
			this.id = userOperationType.id;
			this.name = userOperationType.name;
		}

		public Builder withId(int id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public UserOperationType build() {
			return new UserOperationType(this);
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("name", name).toString();
	}
	
}
