package com.inqwise.opinion.cms.common;

public interface ITag {
	public abstract String getName();

	public abstract Long getId();

	public abstract Long getCountOfLinkedItems();

	public abstract int getGradeByFive();
}
