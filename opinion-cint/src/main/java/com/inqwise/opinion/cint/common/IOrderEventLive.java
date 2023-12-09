package com.cint.common;

public interface IOrderEventLive extends IOrderEvent {

	public abstract Integer getTotalQuotaFulls();

	public abstract Integer getTotalScreenOuts();

	public abstract Integer getTotalNumberOfCompletes();

	public abstract Integer getTotalNumberOfInvitations();

}
