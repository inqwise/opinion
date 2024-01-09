package com.inqwise.opinion.actions.collectors;

import com.inqwise.opinion.infrastructure.dao.SqlProcParameter;
import com.inqwise.opinion.common.opinions.OpinionType;


class CreatePollsCollectorFlow extends CreateCollectorFlow<ICreatePollsCollectorRequest> {

	protected CreatePollsCollectorFlow() {
		
	}

	@Override
	public OpinionType getOpinionType() {
		return OpinionType.Poll;
	}

	@Override
	public Integer getResultsTypeId() {
		return getRequest().getResultsTypeId();
	}
}
