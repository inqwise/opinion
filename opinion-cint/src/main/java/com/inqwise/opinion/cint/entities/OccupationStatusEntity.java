package com.cint.entities;

import org.w3c.dom.Element;

import com.cint.common.IOccupationStatus;

class OccupationStatusEntity extends IdNameEntity implements IOccupationStatus {

	public OccupationStatusEntity(Element element) {
		XmlHelper.parse(element, this);
	}
	
}
