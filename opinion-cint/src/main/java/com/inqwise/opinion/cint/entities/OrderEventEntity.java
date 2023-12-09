package com.cint.entities;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cint.common.IOrderEvent;
import com.cint.common.IXmlOwnerCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public abstract class OrderEventEntity implements IOrderEvent, IXmlOwnerCallback {
	
	static ApplicationLog logger = ApplicationLog.getLogger(OrderEventEntity.class);
	private String state;
	private String orderNumber;
	
	public String getState() {
        return state;
    }

    public void setState(String value) {
        this.state = value;
    }
    
    @Override
	public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String value) {
        this.orderNumber = value;
    }
    
	protected OrderEventEntity(Element element) {
		XmlHelper.parse(element, this);
	}
	
	public void onElementFound(Element element) {
    	switch(element.getTagName()){
    	case "state":
    		setState(element.getTextContent());
    		break;
    	case "order-number":
			setOrderNumber(element.getTextContent());
			break;
    	}
    }

	public static OrderEventEntity parse(Element element) {
		
		OrderEventEntity entity = null;
		NodeList list = element.getElementsByTagName("state");
		if(null == list || list.getLength() == 0){
			logger.warn("parse() : Unable to find tag with name 'state' in element. Data:'%s'", element.getTextContent()); 
		} else {
			Node node = list.item(0);
			String state = node.getTextContent();
			switch(state){
			case "completed":
				entity = new OrderEventCompletedEntity(element);
				break;
			case "denied":
				entity = new OrderEventDeniedEntity(element);
				break;
			case "live":
				entity = new OrderEventLiveEntity(element);
				break;
			case "new":
				entity = new OrderEventNewEntity(element);
				break;
			case "paused":
				entity = new OrderEventPausedEntity(element);
				break;
			case "pending":
				entity = new OrderEventPendingEntity(element);
				break;
			default:
				throw new Error(String.format("parse() : state '%s' not implemented", state));
			}
		}
		
		return entity;
	}
}
