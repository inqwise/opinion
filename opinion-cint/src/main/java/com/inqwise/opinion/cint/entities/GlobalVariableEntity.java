package com.inqwise.opinion.cint.entities;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IGlobalVariable;
import com.inqwise.opinion.cint.common.IValue;
import com.inqwise.opinion.cint.common.IXmlOwnerCallback;

public class GlobalVariableEntity implements IGlobalVariable, IXmlOwnerCallback {

	private String id;
    private String name;
    private String text;
    private List<IValue> values;

    @Override
	public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    @Override
	public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
	public String getText() {
        return text;
    }

    public void setText(String value) {
        this.text = value;
    }

    @Override
	public List<IValue> getValues() {
        if (values == null) {
            values = new ArrayList<IValue>();
        }
        return this.values;
    }
    
	public GlobalVariableEntity(Element element) {
		XmlHelper.parse(element, this);
	}
	
	@Override
	public void onElementFound(Element element) {
		switch(element.getTagName()){
		case "id":
			setId(element.getTextContent());
			break;
	    case "name":
	    	setName(element.getTextContent());
	    	break;
	    case "text":
	    	setText(element.getTextContent());
	    	break;
	    case "value":
	    	getValues().add(new ValueEntity(element));
	    	break;
		}
	}
}
