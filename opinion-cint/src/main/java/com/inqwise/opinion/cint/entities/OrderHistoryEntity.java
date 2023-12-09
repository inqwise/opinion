package com.cint.entities;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.cint.common.ILink;
import com.cint.common.IEntry;
import com.cint.common.IOrderHistory;
import com.cint.common.IXmlOwnerCallback;

public class OrderHistoryEntity implements IOrderHistory, IXmlOwnerCallback {
	
	private List<ILink> links;
    private List<IEntry> entries;

    @Override
	public List<ILink> getLinks() {
        if (links == null) {
            links = new ArrayList<ILink>();
        }
        return this.links;
    }

    @Override
	public List<IEntry> getEntries() {
        if (entries == null) {
        	entries = new ArrayList<IEntry>();
        }
        return this.entries;
    }

	public OrderHistoryEntity(Element element) {
		XmlHelper.parse(element, this);
	}

	public void onElementFound(Element element) {
    	switch(element.getTagName()){
    	case "link":
    		getLinks().add(new LinkEntity(element));
    		break;
    	case "entry":
    		getEntries().add(new OrderHistoryItemEntity(element));
    		break;
    	}
    }
}