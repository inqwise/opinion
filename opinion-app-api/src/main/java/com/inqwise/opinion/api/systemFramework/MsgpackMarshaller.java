package com.inqwise.opinion.api.systemFramework;

import org.infinispan.commons.marshall.AbstractDelegatingMarshaller;
import org.msgpack.MessagePack;

public class MsgpackMarshaller extends AbstractDelegatingMarshaller {

	private MessagePack m = new MessagePack();

	@Override
	public void start() {
		
	}
	
}
