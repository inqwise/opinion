package com.inqwise.opinion.managers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.UUID;

import com.inqwise.opinion.automation.EventsServiceClient;
import com.inqwise.opinion.automation.common.events.ParticipantCompletedEventArgs;
import com.inqwise.opinion.common.IResponseRequest;
import com.inqwise.opinion.common.ResponseType;
import com.inqwise.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.common.emails.IAnswererFinishResponseEmailData;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.dao.Responses;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.countries.ICountry;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.CountriesManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public final class ResponsesManager {
	private static ApplicationLog logger = ApplicationLog
			.getLogger(ResponsesManager.class);

	public static OperationResult<Long> createResponse(
			IResponseRequest request, ICollector collector, IOpinion opinion, IProduct product, IAccount account) throws NotBoundException, IOException {

		OperationResult<Long> result = null;

		if (null == result) {
			try {
				// get geoCountry
				ICountry geoCountry = CountriesManager.getCountryByIp(request.getClientIp());
				Integer geoCountryId = (null == geoCountry ? null : geoCountry.getId());
				result = Responses.setResponse(request, geoCountryId);

				// Email notification 
				if (!result.hasError()
						&& request.getResponseTypeId() == ResponseType.FinishOpinion.getValue()){
					
					sendEvent(opinion, product, collector, request, account);
				}
				
				if (!result.hasError() && collector.getCollectorStatus() == CollectorStatus.Verify) {
					collector.changeStatus(CollectorStatus.Open, null);
				}
				
			} catch (DAOException e) {
				UUID errorId = logger.error(e,
						"createResponse() : Unexpected error occured.");
				result = new OperationResult<Long>(ErrorCode.GeneralError,
						errorId);
			}
		}
		return result;
	}

	public static void sendEvent(final IOpinion opinion, IProduct product,
			final ICollector collector, final IResponseRequest request,
			final IAccount account) throws 
			MalformedURLException, NotBoundException {

		try {
			EventsServiceClient.getInstance().fire(
					new ParticipantCompletedEventArgs(product.getId(), opinion
							.getId(), OpinionType.Survey.getValue(), request
							.getAnswerSessionId(), collector.getId(), account
							.getOwnerId()));
		} catch (RemoteException e) {
			logger.error(e, "sendEvent: AutomationService not available");
		}
	}

	public static BaseOperationResult emailNotification(final String answerSessionId,final ICollector collector, final IProduct product, final IUser opinionOwner, final OpinionType opinionType, final IOpinion opinion) {
			 
		BaseOperationResult result = null;
		try{
			IAnswererFinishResponseEmailData data = new IAnswererFinishResponseEmailData() {
				
				@Override
				public String getAnswererSessionId() {
					return answerSessionId;
				}

				@Override
				public String getApplicationUrl() {
					return ApplicationConfiguration.Opinion.getUrl();
				}

				@Override
				public int getCurrentYear() {
					return Calendar.getInstance().get(Calendar.YEAR);
				}

				@Override
				public String getEmail() {
					return opinionOwner.getEmail();
				}

				@Override
				public String getFeedbackCaption() {
					return product.getFeedbackCaption();
				}

				@Override
				public String getFeedbackShortCaption() {
					return product.getFeedbackShortCaption();
				}

				@Override
				public String getSupportEmail() {
					return product.getSupportEmail();
				}

				@Override
				public String getNoreplyEmail() {
					return product.getNoreplyEmail();
				}

				@Override
				public String getUrl() {
					return String.format("%s/en-us/surveys/%s/responses/%s", ApplicationConfiguration.Opinion.getUrl(), collector.getOpinionId(), answerSessionId);
				}

				@Override
				public OpinionType getOpinionType() {
					return opinionType;
				}
				
				@Override
				public IOpinion getOpinion() {
					return opinion;
				}
			};
			result = OpinionEmailsManager.sendAnswererFinishResponseEmail(data);
			
		} catch(Throwable e){
			UUID errorId = logger.error(e, "emailNotification() : Unexpected error occured.");
			result = new OperationResult<Long>(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
}

