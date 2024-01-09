package com.inqwise.opinion.cint.entities;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.ILink;
import com.inqwise.opinion.cint.common.IXmlOwnerCallback;

public class LinkEntity implements ILink, IXmlOwnerCallback{
	protected String href;
    protected String title;
    protected String rel;
    protected String type;

    public LinkEntity(Element element) {
		XmlHelper.parse(element, this);
	}

	@Override
	public String getHref() {
        return href;
    }

    public void setHref(String value) {
        this.href = value;
    }

    @Override
	public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    @Override
	public String getRel() {
        return rel;
    }

    public void setRel(String value) {
        this.rel = value;
    }

    @Override
	public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }
    
    public void onElementFound(Element element){
    	switch(element.getTagName()){
    	
    	case "href":
    		setHref(element.getTextContent());
    		break;
    	case "title":
    		setTitle(element.getTextContent());
    		break;
    	case "rel":
    		setRel(element.getTextContent());
    		break;
    	case "type":
    		setType(element.getTextContent());
    		break;
    	}
    }
}
