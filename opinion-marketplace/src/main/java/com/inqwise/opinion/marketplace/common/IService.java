package com.inqwise.opinion.marketplace.common;

import java.util.Date;

public interface IService {

	public abstract Date getModifyDate();

	public abstract String getDescription();

	public abstract String getName();

	public abstract long getId();

}
