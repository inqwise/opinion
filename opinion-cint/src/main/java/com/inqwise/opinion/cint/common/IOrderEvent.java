package com.inqwise.opinion.cint.common;

public interface IOrderEvent {
	OrderEventType getEventType();
	String getOrderNumber();
}
