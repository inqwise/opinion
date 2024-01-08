package com.inqwise.opinion.facade.collector;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.basicTypes.EntityBox;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.common.GuidType;
import com.inqwise.opinion.common.ICollectorPostmasterContext;
import com.inqwise.opinion.common.IPostmasterObject;
import com.inqwise.opinion.common.collectors.ICollector;
import com.inqwise.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.managers.CollectorsManager;
import com.inqwise.opinion.managers.OpinionsManager;

public class Entry {

	protected static ApplicationLog logger = ApplicationLog.getLogger(SurveysEntry.class);
	private ICollectorPostmasterContext context;

	public Entry(ICollectorPostmasterContext context) {
		super();
		setContext(context);
	}

	private void setContext(ICollectorPostmasterContext context) {
		this.context = context;
	}

	public ICollectorPostmasterContext getContext() {
		return context;
	}
	
	protected static OperationResult<IOpinion> getOpinion(String guid, GuidType guidType, EntityBox<ICollector> collectorBox){
		OperationResult<IOpinion> result = null;
		ICollector collector = null;
		
		switch (guidType) {
		case Collector:
			OperationResult<ICollector> collectorResult = CollectorsManager.get(guid);
			if(collectorResult.hasError()){
				result = collectorResult.toErrorResult();
			} else {
				collector = collectorResult.getValue();
				result = collector.getOpinion(null);
			}
			break;
		default:
			result = OpinionsManager.getOpinion(guid);
			break;
		}
		
		if(null != collectorBox && null != collector){
			collectorBox.setValue(collector);
		}
		
		return result;
	}

}