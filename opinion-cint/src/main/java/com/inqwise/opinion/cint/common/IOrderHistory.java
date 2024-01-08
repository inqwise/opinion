package com.inqwise.opinion.cint.common;

import java.util.List;

public interface IOrderHistory {

	public abstract List<IEntry> getEntries();

	public abstract List<ILink> getLinks();

}
