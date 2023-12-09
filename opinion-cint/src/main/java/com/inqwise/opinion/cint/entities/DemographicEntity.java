package com.cint.entities;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.cint.common.IDemographic;
import com.cint.common.ILink;

public class DemographicEntity extends IdNameEntity implements IDemographic {
	private List<ILink> links;
    
    public DemographicEntity(Element element) {
		XmlHelper.parse(element, this);
	}

	public List<ILink> getLinks() {
        if (links == null) {
            links = new ArrayList<ILink>();
        }
        return this.links;
    }

    @Override
    public void onElementFound(Element element) {
    	switch(element.getTagName()){
    	case "link":
    		getLinks().add(new LinkEntity(element));
    		break;
    	default:
    		super.onElementFound(element);
    		break;
    		
    	}
    }
}
