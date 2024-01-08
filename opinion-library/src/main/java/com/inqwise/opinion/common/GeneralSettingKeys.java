package com.inqwise.opinion.common;

public enum GeneralSettingKeys {
	DefaultOpinionFolderName("DEFAULT_OPINION_FOLDER_NAME");
	
	private final String value;

	public String getValue() {
		return value;
	}

	private GeneralSettingKeys(String value) {
		this.value = value;
	}
}
