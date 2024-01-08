package com.inqwise.opinion.cint.common;

public interface IOrderEventCompleted extends IOrderEvent {

	public abstract String getCurrency();

	public abstract Integer getActualNumberOfCompletes();

	public abstract double getPricePerComplete();

	public abstract double getActualPrice();

}
