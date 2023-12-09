package com.cint.entities;

import org.w3c.dom.Element;

import com.cint.common.IOrderEventPending;
import com.cint.common.OrderEventType;

public class OrderEventPendingEntity extends OrderEventEntity implements
		IOrderEventPending {

	protected OrderEventPendingEntity(Element element) {
		super(element);
	}

	@Override
	public OrderEventType getEventType() {
		return OrderEventType.Pending;
	}
}
