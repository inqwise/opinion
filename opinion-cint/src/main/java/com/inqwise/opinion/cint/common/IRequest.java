package com.inqwise.opinion.cint.common;

import org.w3c.dom.Document;

public interface IRequest<TResponse> extends IRequestVoid {
	TResponse parseResponse(Document doc);
	TResponse parseResponse(String raw);
}
