package com.inqwise.opinion.opinion.common.collectors;


public interface IDeletedCollectorDetails {
	long getCollectorId();
	String getExternalId();
	CollectorStatus getStatus();
}
