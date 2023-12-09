package com.inqwise.opinion.library.common.resources;

public enum FileSystemItemType {
	Folder(1), File(2);
	
	private final Integer value;
	
	public Integer getValue(){
		return value;
	}
	
	private FileSystemItemType(Integer value){
		this.value = value;
	}
}
