package com.inqwise.opinion.library.common.pay;

import org.json.JSONObject;

import com.google.common.base.MoreObjects;

public class ChargeModel {
	
	private Long id;
	private ChargeStatus status;
	private Double amount;

	private ChargeModel(Builder builder) {
		this.id = builder.id;
		this.status = builder.status;
		this.amount = builder.amount;
	}

	public static final class Keys {
		public static String ID = "id";
		public static String STATUS_ID = "status_id";
		public static String AMOUNT = "amount";
	}  
	
	public ChargeModel(JSONObject json) {
		id = json.optLong(Keys.ID);
		var statusId = json.optIntegerObject(Keys.STATUS_ID);
		if(null != statusId) {
			status = ChargeStatus.fromInt(statusId);
		}
		amount = json.optDoubleObject(Keys.AMOUNT);
	}
	
	public Long getId() {
		return id;
	}
	
	public ChargeStatus getStatus() {
		return status;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public JSONObject toJson() {
		var json = new JSONObject();
		json.put(Keys.ID, id);
		if(null != status) {
			json.put(Keys.STATUS_ID, status.getValue());
		}
		
		json.put(Keys.AMOUNT, amount);
		return json;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("status", status).add("amount", amount).toString();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(ChargeModel chargeModel) {
		return new Builder(chargeModel);
	}

	public static final class Builder {
		private Long id;
		private ChargeStatus status;
		private Double amount;

		private Builder() {
		}

		private Builder(ChargeModel chargeModel) {
			this.id = chargeModel.id;
			this.status = chargeModel.status;
			this.amount = chargeModel.amount;
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withStatus(ChargeStatus status) {
			this.status = status;
			return this;
		}

		public Builder withAmount(Double amount) {
			this.amount = amount;
			return this;
		}

		public ChargeModel build() {
			return new ChargeModel(this);
		}
	}
}
