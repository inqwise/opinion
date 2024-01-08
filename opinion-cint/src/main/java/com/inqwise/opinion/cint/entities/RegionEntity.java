package com.inqwise.opinion.cint.entities;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IRegion;

class RegionEntity extends IdNameEntity implements IRegion 
{
	public RegionEntity(Element element) {
		XmlHelper.parse(element, this);
	}
}
