package com.cint.common;

public interface IOrderEvent {
	OrderEventType getEventType();
	String getOrderNumber();
}
