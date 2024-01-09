package com.inqwise.opinion.cint.entities;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IEducationLevel;
import com.inqwise.opinion.cint.common.IVariable;

class VariableEntity extends IdNameEntity implements IVariable {

	public VariableEntity(Element element) {
		XmlHelper.parse(element, this);
	}
	
}
