package com.inqwise.opinion.opinion.common.collectors;

import java.util.Date;


public interface ICollectorSource {

	Date getModifyDate();

	String getDescription();

	CollectorSourceType getType();
	String getName();
	CollectorStatus getInitialStatus();

	String getReturnUrl();

	String getScreenOutUrl();

	String getReferer();

	String getClosedUrl();

}
