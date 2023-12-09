package com.cint.entities;

import org.w3c.dom.Element;

import com.cint.common.IGender;

class GenderEntity extends IdNameEntity implements IGender {

	public GenderEntity(Element element) {
		XmlHelper.parse(element, this);
	}
}
