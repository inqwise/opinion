package com.inqwise.opinion.opinion.common.collectors;

import com.inqwise.opinion.opinion.common.ResultsPermissionType;

public interface IPollsCollector extends ICollector, ICollector.IEndDateExtension, ICollector.IMultiplyResponsesExtension, ICollector.IPasswordExtension, ICollector.IMessagesExtension {
	ResultsPermissionType getResultsType();
}
