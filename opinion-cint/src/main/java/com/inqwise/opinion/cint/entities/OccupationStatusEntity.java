package com.inqwise.opinion.cint.entities;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IOccupationStatus;

class OccupationStatusEntity extends IdNameEntity implements IOccupationStatus {

	public OccupationStatusEntity(Element element) {
		XmlHelper.parse(element, this);
	}
	
}
