package com.inqwise.opinion.opinion.actions.opinions;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.SqlProcParameter;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.opinion.actions.collectors.CollectorsActionsFactory;
import com.inqwise.opinion.opinion.actions.collectors.ICreatePollsCollectorRequest;
import com.inqwise.opinion.opinion.actions.opinions.ICreatePollRequest.IRequestExtension;
import com.inqwise.opinion.opinion.common.ResultsPermissionType;
import com.inqwise.opinion.opinion.common.opinions.IPoll;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;

class CreatePollFlow extends CreateOpinionFlow<ICreatePollRequest> {
	private static final String DEFAULT_VOTE_BUTTON_TITLE = "Vote";
	private static final String DEFAULT_RESULTS_BUTTON_TITLE = "View Results";
	private static final String DEFAULT_PREVIOUS_BUTTON_TITLE = "Back";
	
	protected IRequestExtension getRequestExtension() {
		return (IRequestExtension) super.getRequestExtension();
	}
	
	@Override
	protected OpinionType getOpinionType() {
		return OpinionType.Poll;
	}
	
	@Override
	protected BaseOperationResult collect(ICreatePollRequest request) throws DAOException {
		
		BaseOperationResult result = super.collect(request);
		
		return result;
	}

	@Override
	public String getFinishButtonTitle() {
		return (null == getRequestExtension() ? DEFAULT_VOTE_BUTTON_TITLE : getRequestExtension().getVoteButtonTitle());
	}

	@Override
	public String getPreviousButtonTitle() {
		return (null == getRequestExtension() ? DEFAULT_PREVIOUS_BUTTON_TITLE : getRequestExtension().getPreviousButtonTitle());
	}

	@Override
	public String getNextButtonTitle() {
		return (null == getRequestExtension() ? DEFAULT_RESULTS_BUTTON_TITLE : getRequestExtension().getResultsButtonTitle());
	}

	@Override
	protected BaseOperationResult createDefaultCollector(final String actionGuid, final long userId, final long opinionId) {
		return CollectorsActionsFactory.getInstance().create(new ICreatePollsCollectorRequest() {
			
			@Override
			public long getUserId() {
				return userId;
			}
			
			@Override
			public Long getOpinionId() {
				return opinionId;
			}
			
			@Override
			public String getName() {
				return null; // Default id will be assigned
			}
			
			@Override
			public Integer getCollectorSourceId() {
				return null; // Default id will be assigned
			}
			
			@Override
			public String getActionGuid() {
				return actionGuid;
			}

			@Override
			public Integer getResultsTypeId() {
				return ResultsPermissionType.All.getValue();
			}
		});
	}

	@Override
	public String getRedirectUrl() {
		return null;
	}

	@Override
	public Integer getFinishBehaviourTypeId() {
		return null;
	}
	
	@Override
	public Integer getThemeId() {
		return (null == getRequestExtension() ? IPoll.DEFAULT_THEME_ID : getRequestExtension().getThemeId());
	}

	@Override
	public Integer getLabelPlacementId() {
		return null;
	}

	@Override
	public Boolean isShowPaging() {
		return null;
	}

	@Override
	public String getAlreadyCompletedMessage() {
		return null;
	}
}
