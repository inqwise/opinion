package com.inqwise.opinion.cint.common;

import java.util.Date;

public interface IEntry {

	public abstract String getTitle();

	public abstract ILink getLink();

	public abstract Date getUpdated();

	public abstract Date getPublished();

	public abstract String getId();

	public abstract IOrderEvent getOrderEvent();

}
