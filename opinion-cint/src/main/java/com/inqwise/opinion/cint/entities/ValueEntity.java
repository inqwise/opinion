package com.inqwise.opinion.cint.entities;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IValue;
import com.inqwise.opinion.cint.common.IXmlOwnerCallback;

public class ValueEntity implements IValue, IXmlOwnerCallback {
	
	private String id;
    private String code;
    private String text;

    public ValueEntity(Element element) {
		XmlHelper.parse(element, this);
	}

	@Override
	public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    @Override
	public String getCode() {
        return code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    @Override
	public String getText() {
        return text;
    }

    public void setText(String value) {
        this.text = value;
    }
    
    @Override
	public void onElementFound(Element element) {
		switch(element.getTagName()){
		case "id":
			setId(element.getTextContent());
			break;
		case "code":
			setCode(element.getTextContent());
			break;
		case "text":
			setText(element.getTextContent());
			break;
		}
	}
}
