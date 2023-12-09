package com.inqwise.opinion.opinion.common;

public class OptionResult {

	private Long optionId;
	private Long controlId;
	private String optionValue;
	private String optionText;
	private Integer orderId;
	private OptionKind optionKind;
	
	public OptionResult(Long optionId) {
		setOptionId(optionId);
	}
	private void setOptionId(Long optionId) {
		this.optionId = optionId;
	}
	public Long getOptionId() {
		return optionId;
	}
	public void setControlId(Long controlId) {
		this.controlId = controlId;
	}
	public Long getControlId() {
		return controlId;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}
	public String getOptionText() {
		return optionText;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOptionKind(OptionKind optionKind) {
		this.optionKind = optionKind;
	}
	public OptionKind getOptionKind() {
		return optionKind;
	}
	

}
