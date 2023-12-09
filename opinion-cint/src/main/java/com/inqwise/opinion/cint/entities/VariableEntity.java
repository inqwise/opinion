package com.cint.entities;

import org.w3c.dom.Element;

import com.cint.common.IEducationLevel;
import com.cint.common.IVariable;

class VariableEntity extends IdNameEntity implements IVariable {

	public VariableEntity(Element element) {
		XmlHelper.parse(element, this);
	}
	
}
