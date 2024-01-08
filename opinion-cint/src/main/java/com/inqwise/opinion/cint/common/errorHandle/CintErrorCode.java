package com.inqwise.opinion.cint.common.errorHandle;

import com.inqwise.opinion.infrastructure.common.IErrorCode;

public enum CintErrorCode implements IErrorCode {
	NoError,
	GeneralError,
	CountryIsMandatory,
	AgeIsOutOfRange,
	SurveyTitleIsMandatory,
	SurveyUrlIsMandatory,
	NumberOfQuestionsIsOutOfRange,
	NumberOfCompletesIsMandatory, 
	NumberOfCompletesIsOutOfRange,
	TargetGroupIsMandatory,
	NotImplemented,
	LocationIsMandatory,
	StatusIsMandatory, RespondentGuidIsMandatory, NoResults, QuoteInvalid, StateIsInvalid,
}
