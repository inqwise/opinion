package com.inqwise.opinion.cms.common.faq;

import java.util.List;

public interface IFaq {
	public abstract long getId();
	public abstract String getQuestion();
	public abstract String getAnswer();
	public abstract List<String> getCategories();
}
