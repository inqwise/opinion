package com.inqwise.opinion.common.collectors;


public interface IDeletedCollectorDetails {
	long getCollectorId();
	String getExternalId();
	CollectorStatus getStatus();
}
