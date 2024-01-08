package com.inqwise.opinion.cint.entities;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IOrderEventPaused;
import com.inqwise.opinion.cint.common.OrderEventType;

public class OrderEventPausedEntity extends OrderEventEntity implements
		IOrderEventPaused {

    private String reason;

    @Override
	public String getReason() {
        return reason;
    }

    public void setReason(String value) {
        this.reason = value;
    }
    
	protected OrderEventPausedEntity(Element element) {
		super(element);
	}

	@Override
	public OrderEventType getEventType() {
		return OrderEventType.Paused;
	}
	
	@Override
	public void onElementFound(Element element) {
		switch(element.getTagName()){
		
		case "reason":
			setReason(element.getTextContent());
			break;
		default:
			super.onElementFound(element);
			break;
		}
	}

}
