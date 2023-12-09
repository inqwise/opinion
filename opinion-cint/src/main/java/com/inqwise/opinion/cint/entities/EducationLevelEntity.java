package com.cint.entities;

import org.w3c.dom.Element;

import com.cint.common.IEducationLevel;

class EducationLevelEntity extends IdNameEntity implements IEducationLevel {

	public EducationLevelEntity(Element element) {
		XmlHelper.parse(element, this);
	}
}
