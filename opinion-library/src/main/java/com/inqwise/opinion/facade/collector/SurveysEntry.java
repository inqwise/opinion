package com.inqwise.opinion.facade.collector;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.common.GuidType;
import com.inqwise.opinion.common.IAnswererSession;
import com.inqwise.opinion.common.ICollectorPostmasterContext;
import com.inqwise.opinion.common.IControl;
import com.inqwise.opinion.common.IHttpAnswererSession;
import com.inqwise.opinion.common.IOpinionAccount;
import com.inqwise.opinion.common.IOpinionComplexData;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.common.IResponseRequest;
import com.inqwise.opinion.common.OutputMode;
import com.inqwise.opinion.common.ParentType;
import com.inqwise.opinion.common.ResponseType;
import com.inqwise.opinion.common.collectors.CollectorStatus;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.common.collectors.ICollector.IEndDateExtension;
import com.inqwise.opinion.common.collectors.ICollector.IMessagesExtension;
import com.inqwise.opinion.common.collectors.ICollector.IMultiplyResponsesExtension;
import com.inqwise.opinion.common.collectors.ISurveysCollector;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.common.opinions.ISurvey;
import com.inqwise.opinion.common.servicePackage.IServicePackageSettings;
import com.inqwise.opinion.common.sheet.ISheet;
import com.inqwise.opinion.entities.ServicePackageSettingsEntity;
import com.inqwise.opinion.http.HttpClientSession;
import com.inqwise.opinion.managers.AccountsManager;
import com.inqwise.opinion.managers.AnswerersSessionsManager;
import com.inqwise.opinion.managers.CollectorsManager;
import com.inqwise.opinion.managers.ControlsManager;
import com.inqwise.opinion.managers.OpinionsManager;
import com.inqwise.opinion.managers.ResponsesManager;
import com.inqwise.opinion.managers.SheetsManager;

public class SurveysEntry extends OpinionsEntry {

	public SurveysEntry(ICollectorPostmasterContext context) {
		super(context);
	}
	
	public JSONObject getSurveyDetails(JSONObject input) throws JSONException, IOException {	
		return getDetails(input);
	}
	
}
