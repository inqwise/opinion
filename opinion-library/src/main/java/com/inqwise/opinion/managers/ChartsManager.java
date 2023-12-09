package com.inqwise.opinion.opinion.managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.time.DateUtils;

import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.library.common.basicTypes.EntityBox;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.opinion.common.charts.ActivityChartDataItem;
import com.inqwise.opinion.opinion.common.charts.TimePointRange;
import com.inqwise.opinion.opinion.dao.Charts;

public class ChartsManager {
	private static ApplicationLog logger = ApplicationLog.getLogger(ChartsManager.class);
	
	public static OperationResult<List<ActivityChartDataItem>> getActivityChart(Long accountId, Long opinionId, Long collectorId, TimePointRange timePointRange, Date fromDate, Date toDate){
		
		OperationResult<List<ActivityChartDataItem>> result;
		
		if(null == fromDate || null == toDate || fromDate.before(toDate)){
			
			List<ActivityChartDataItem> items = new ArrayList<ActivityChartDataItem>();
			
			BaseOperationResult fillResult = fillActivityChartData(accountId, opinionId, collectorId, timePointRange, fromDate, toDate, items);
			if(fillResult.hasError()){
				result = fillResult.toErrorResult();
			} else {
				result = new OperationResult<List<ActivityChartDataItem>>(items);
			}
		} else {
			String message = "The 'fromDate' value must be less that 'afterDate' value";
			logger.warn(message);
			result = new OperationResult<List<ActivityChartDataItem>>(ErrorCode.ArgumentOutOfRange, UUID.randomUUID(), message);
		}
		return result;
	}
	
	private static void fillActiveChartTemplate(int capacity, Date fromDate, Date toDate, TimePointRange timePointRange, List<ActivityChartDataItem> items){
		Date date = null;
		for(int i = 0 ; i < capacity ; i++){
			switch(timePointRange){
			case Hour:
				date = DateUtils.addHours(toDate, -i);
				break;
			case Day:
				date = DateUtils.addDays(toDate, -i);
				break;
			case Week:
				date = DateUtils.addWeeks(toDate, -i);
				break;
			case Month:
				date = DateUtils.addMonths(toDate, -i);
				break;
			}
			
			if(date.after(toDate)){
				date = toDate;
			}
			
			if(date.before(fromDate)){
				date = fromDate;
			}
			
			items.add(i, new ActivityChartDataItem(date));
		}
		
		if(null != date && date.after(fromDate)){
			items.add(capacity, new ActivityChartDataItem(fromDate));
		}
		
		Collections.reverse(items);
	}
	
	private static BaseOperationResult fillActivityChartData(Long accountId, Long opinionId, Long collectorId, final TimePointRange timePointRange, Date fromDate, Date toDate, final List<ActivityChartDataItem> items){
		
		BaseOperationResult result;
		final EntityBox<Date> fromDateBox = new EntityBox<Date>();
		final EntityBox<Date> toDateBox = new EntityBox<Date>();
		final AtomicBoolean haveExtraPoint = new AtomicBoolean();
		
		IResultSetCallback callback = new IResultSetCallback() {
			
			@Override
			public void call(ResultSet reader, int generationId) throws Exception {
				if(0 == generationId){ // minDate, maxDate
					if(reader.next()){
						fromDateBox.setValue(ResultSetHelper.optDate(reader, "min_date"));
						toDateBox.setValue(ResultSetHelper.optDate(reader, "max_date"));
					
						double diffInSeconds = (toDateBox.getValue().getTime() - fromDateBox.getValue().getTime()) / 1000;
						int countOfPoints;
						switch(timePointRange){
							case Hour:
								countOfPoints = (int) Math.ceil(diffInSeconds / 60f / 60f);
								break;
							case Week:
								countOfPoints = (int) Math.ceil(diffInSeconds / 60f / 60f / 24f / 7f);
								break;
							case Month:
								countOfPoints = (int) Math.ceil(diffInSeconds / 60f / 60f / 24f / 30f);
								break;
							case Day:
							default:
								countOfPoints = (int) Math.ceil(diffInSeconds / 60f / 60f / 24f);
								break;
						}
						
						fillActiveChartTemplate(countOfPoints, fromDateBox.getValue(), toDateBox.getValue(), timePointRange, items);
						haveExtraPoint.set(items.size() > countOfPoints);
					}
				} else {
					while(reader.next()) {
						Integer itemId = ResultSetHelper.optInt(reader, "id");
						if(haveExtraPoint.get()) itemId += 1;
						ActivityChartDataItem item = items.get(itemId);
						int itemTypeId = reader.getInt("typeId");
						switch(itemTypeId){
						case 1:
							item.setCountOfStartedOpinions(ResultSetHelper.optLong(reader, "cnt"));
							break;
						case 2:
							item.setCountOfFinishedOpinions(ResultSetHelper.optLong(reader, "cnt"));
							break;
						}
					}
				}
				
			}
		};
		
		try {
			Charts.getActivityChartData(accountId, opinionId, collectorId, fromDate, toDate, timePointRange, callback);
			
			result = BaseOperationResult.Ok;
		} catch (DAOException ex) {
			UUID errorId = logger.error(ex, "fillActivityChartData() : Unexpected error occured.");
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
}
