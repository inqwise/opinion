package com.cint.entities;

import java.util.Date;

import org.w3c.dom.Element;

import com.cint.common.IEntry;
import com.cint.common.ILink;
import com.cint.common.IOrderEvent;
import com.cint.common.IXmlOwnerCallback;

public class OrderHistoryItemEntity implements IEntry, IXmlOwnerCallback {
	
	
	protected String id;
    protected Date published;
    protected Date updated;
    protected LinkEntity link;
    protected String title;
    protected OrderEventEntity orderEvent;

    @Override
	public IOrderEvent getOrderEvent() {
        return orderEvent;
    }

    public void setOrderEvent(OrderEventEntity value) {
        this.orderEvent = value;
    }

    @Override
	public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    @Override
	public Date getPublished() {
        return published;
    }

    public void setPublished(Date value) {
        this.published = value;
    }

    @Override
	public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date value) {
        this.updated = value;
    }

    @Override
	public ILink getLink() {
        return link;
    }

    public void setLink(LinkEntity value) {
        this.link = value;
    }

    @Override
	public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }
    
	protected OrderHistoryItemEntity(Element element) {
		XmlHelper.parse(element, this);
	}
	
	public void onElementFound(Element element) {
		
    	switch(element.getTagName()){
    	case "id":
    		setId(element.getTextContent());
    		break;
    	case "published":
    		setPublished(XmlHelper.parseDate(element.getTextContent()));
    		break;
    	case "updated":
    		setUpdated(XmlHelper.parseDate(element.getTextContent()));
    		break;
    	case "link":
    		setLink(new LinkEntity(element));
    		break;
    	case "title":
    		setTitle(element.getTextContent());
    		break;
    	case "content":
    		XmlHelper.parse(element, this);
    		break;
    	case "order-event":
    		setOrderEvent(OrderEventEntity.parse(element));
    		break;
    	}
    }
}
