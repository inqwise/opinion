package com.cint.entities;

import org.w3c.dom.Element;

import com.cint.common.IRegion;

class RegionEntity extends IdNameEntity implements IRegion 
{
	public RegionEntity(Element element) {
		XmlHelper.parse(element, this);
	}
}
