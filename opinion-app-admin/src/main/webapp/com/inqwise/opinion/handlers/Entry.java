package com.inqwise.opinion.handlers;

import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.IPostmasterObject;

public abstract class Entry implements IPostmasterObject {
	//static ApplicationLog logger = ApplicationLog.getLogger(Entry.class);
	
	private IPostmasterContext context;
	protected Entry(IPostmasterContext context){
		this.setContext(context);
	}
	
	public void setContext(IPostmasterContext context) {
		this.context = context;
	}

	public IPostmasterContext getContext() {
		return context;
	}
}
