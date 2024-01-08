package com.inqwise.opinion.common;

import java.util.ArrayList;
import java.util.List;

public class ControlResult {

	private Long controlId;
	private Long parentId;
	private ParentType parentType;
	private ControlType controlType;
	private String content;
	private Integer inputTypeId;
	private Integer pageNumber;
	private Integer fromScale;
	private Integer toScale;
	private List<ControlResult> subControls;
	private List<OptionResult> options;
	
	public ControlResult(Long controlId) {
		setControlId(controlId);
	}

	public void addSubControl(ControlResult controlResult) {
		if(null == subControls){
			synchronized (this) {
				if(null == subControls){
					subControls = new ArrayList<ControlResult>();
				}
			}
		}
		
		subControls.add(controlResult);
	}

	public Long getControlId() {
		return controlId;
	}

	public void addOption(OptionResult optionResult) {
		if(null == options){
			synchronized (this) {
				if(null == options){
					options = new ArrayList<OptionResult>();
				}
			}
		}
		
		options.add(optionResult);
	}

	private void setControlId(Long controlId) {
		this.controlId = controlId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentType(ParentType parentType) {
		this.parentType = parentType;
	}

	public ParentType getParentType() {
		return parentType;
	}

	public void setControlType(ControlType controlType) {
		this.controlType = controlType;
	}

	public ControlType getControlType() {
		return controlType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setInputTypeId(Integer inputTypeId) {
		this.inputTypeId = inputTypeId;
	}

	public Integer getInputTypeId() {
		return inputTypeId;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setFromScale(Integer fromScale) {
		this.fromScale = fromScale;
	}

	public Integer getFromScale() {
		return fromScale;
	}

	public void setToScale(Integer toScale) {
		this.toScale = toScale;
	}

	public Integer getToScale() {
		return toScale;
	}

	public List<OptionResult> getOptions() {
		return options;
	}

	public List<ControlResult> getSubControls() {
		return subControls;
	}

}
