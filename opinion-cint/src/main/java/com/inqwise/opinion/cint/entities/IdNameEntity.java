package com.inqwise.opinion.cint.entities;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IXmlOwnerCallback;

abstract class IdNameEntity implements IXmlOwnerCallback {
	protected String id;
	protected String name;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }
    
    public void onElementFound(Element element){
    	switch(element.getTagName()){
    	case "id":
    		setId(element.getTextContent());
    		break;
    	case "name":
    		setName(element.getTextContent());
    		break;
    	}
    }
}
