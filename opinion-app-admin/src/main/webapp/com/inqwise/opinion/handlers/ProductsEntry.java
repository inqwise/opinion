package com.inqwise.opinion.handlers;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.common.BulkOperationResults;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.parameters.EntityType;
import com.inqwise.opinion.library.common.parameters.IVariable;
import com.inqwise.opinion.library.common.parameters.IVariableSet;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.common.servicePackages.IServicePackageUpdateRequest;
import com.inqwise.opinion.library.managers.ParametersManager;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.ServicePackagesManager;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.servicePackage.IOpinionServicePackageSettingsUpdateRequest;
import com.inqwise.opinion.opinion.common.servicePackage.IServicePackageSettings;
import com.inqwise.opinion.opinion.managers.OpinionServicePackagesManager;

public class ProductsEntry extends Entry {

	static ApplicationLog logger = ApplicationLog.getLogger(ProductsEntry.class);
	static Format mdyhmsFormatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
	
	protected ProductsEntry(IPostmasterContext context) {
		super(context);
	}
	
	public JSONObject getPackages(final JSONObject input){
		JSONObject output;
		
		try{
			
			Integer productId = JSONHelper.optInt(input, "productId");
			boolean includeNonActive = input.optBoolean("includeNonActive");
			
			OperationResult<List<IServicePackage>> result = ServicePackagesManager.getServicePackages(productId, includeNonActive);
			
			if(result.hasError()){
				output = result.toJson();
			} else {
				List<IServicePackage> items = result.getValue();
				output = new JSONObject();
				JSONArray ja = new JSONArray();
				for (IServicePackage item : items) {
					JSONObject jo = new JSONObject();
					jo.put("packageId", item.getId())
					.put("packageName", item.getName())
					.put("insertDate", mdyhmsFormatter.format(item.getInsertDate()))
					.put("description", item.getDescription())
					.put("isDefault", item.isDefault());
					if(null == item.getDefaultUsagePeriod()){
						jo.put("defaultUsagePeriod", JSONObject.NULL);
					} else {
						jo.put("defaultUsagePeriod", item.getDefaultUsagePeriod());
					}
					ja.put(jo);
				}
				output.put("list", ja);
			} 
			
		} catch (Throwable t) {
			output = new JSONObject();
			UUID errorId = logger.error(t,"getPackages() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t,"getPackages() : Unable to put to Json object");
			}
		}
		
		return output;
	}
	
	public JSONObject getPackageDetails(final JSONObject input){
		JSONObject output = null;
		BaseOperationResult result = null;
		try{
			int servicePackageId = input.getInt("packageId");
			IServicePackage servicePackage = null;
			OperationResult<IServicePackage> packageResult = ServicePackagesManager.getServicePackage(servicePackageId, null);
			
			if(packageResult.hasError()){
				result = packageResult;
			} else {
				servicePackage = packageResult.getValue();
				output = new JSONObject();
				output.put("packageId", servicePackage.getId())
				.put("description", servicePackage.getDescription())
				.put("packageName", servicePackage.getName())
				.put("insertDate", mdyhmsFormatter.format(servicePackage.getInsertDate()));
			}
			
			if (null != result) {
				output = result.toJson();
			}
		} catch (Throwable t) {
			output = new JSONObject();
			UUID errorId = logger.error(t,"getPackageDetails() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t,"getPackageDetails() : Unable to put to Json object");
			}
		}
		
		return output;
	}
	
	public JSONObject getProductDetails(final JSONObject input){
		JSONObject output = null;
		BaseOperationResult result = null;
		try {
			int productId = input.optInt("productId");
			OperationResult<IProduct> productResult = ProductsManager.getProductById(productId);
			if(productResult.hasError()){
				result = productResult;
			} else {
				IProduct product = productResult.getValue();
				output = new JSONObject();
				output.put("productId", product.getId())
				.put("adminEmail", product.getAdminEmail())
				.put("contactUsEmail", product.getContactUsEmail())
				.put("description", product.getDescription())
				.put("feedbackCation", product.getFeedbackCaption())
				.put("feedbackShortCation", product.getFeedbackShortCaption())
				.put("productGuid", product.getGuid().toString())
				.put("name", product.getName())
				.put("noreplyEmail", product.getNoreplyEmail())
				.put("salesEmail", product.getSalesEmail())
				.put("supportEmails", product.getSupportEmail());
				
				Collection<IServicePackage> packages = product.getServicePackages();
				JSONArray jaPackages = new JSONArray();
				if(null != packages){
					for (IServicePackage servicePackage : packages) {
						JSONObject joPackage = new JSONObject();
						joPackage.put("packageId", servicePackage.getId())
						.put("name", servicePackage.getName());
						
						jaPackages.put(joPackage);
					}
				}
				
				output.put("packages", jaPackages);
			}
			
			if(null != result){
				output = result.toJson();
			}
			
		} catch (Throwable t) {
			output = new JSONObject();
			UUID errorId = logger.error(t,"getProductDetails() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t,"getProductDetails() : Unable to put to Json object");
			}
		}
		
		return output;
	}
	
	public JSONObject getProducts(JSONObject input){
		JSONObject output = null;
		
		try {
			OperationResult<Collection<IProduct>> result = ProductsManager.getProducts();
			if(result.hasError()){
				output = result.toJson();
			} else {
				output = new JSONObject();
				JSONArray jaProducts = new JSONArray();
				Collection<IProduct> products = result.getValue();
				for (IProduct product : products) {
					JSONObject joProduct = new JSONObject();
					joProduct.put("productId", product.getId())
					.put("description", product.getDescription())
					.put("name", product.getName());
					
					jaProducts.put(joProduct);
				}
				
				output.put("list", jaProducts);
			}
		} catch (Throwable t) {
			output = new JSONObject();
			UUID errorId = logger.error(t,"getProducts() : Unexpected error occured.");
			try {
				output.put("error", ErrorCode.GeneralError).put("errorId",
						errorId).put("description", t.getMessage());
			} catch (JSONException e) {
				logger.error(t,"getProducts() : Unable to put to Json object");
			}
		}
		
		return output;
	}
	
	public JSONObject getPackageSettings(JSONObject input) throws JSONException{

		JSONObject output = null;
		BaseOperationResult result = null;
		
		int servicePackageId = input.getInt("packageId");
		IServicePackage servicePackage = null;
		OperationResult<IServicePackage> packageResult = ServicePackagesManager.getServicePackage(servicePackageId, null);
		
		if(packageResult.hasError()){
			result = packageResult;
		} else {
			servicePackage = packageResult.getValue();
			output = new JSONObject();
			output.put("packageId", servicePackage.getId())
			.put("description", servicePackage.getDescription())
			.put("packageName", servicePackage.getName())
			.put("insertDate", mdyhmsFormatter.format(servicePackage.getInsertDate()))
			.put("defaultUsagePeriod", JSONHelper.getNullable(servicePackage.getDefaultUsagePeriod()))
			.put("isDefault", servicePackage.isDefault())
			.put("amount", servicePackage.getAmount())
			.put("accountUsers", servicePackage.getMaxAccountUsers());
		}
		
		if(null == result){
			OperationResult<IServicePackageSettings> opinionPackageResult = OpinionServicePackagesManager.getServicePackageSettings(servicePackageId);
			if(!opinionPackageResult.hasError()){
				IServicePackageSettings settings = opinionPackageResult.getValue();
				
				output.put("maxCollectors", JSONHelper.getNullable(settings.getMaxCollectors()));
				output.put("maxControlsPerOpinion", JSONHelper.getNullable(settings.getMaxControlsPerOpinion()));
				output.put("maxOpinions", JSONHelper.getNullable(settings.getMaxOpinions()));
				output.put("maxResponsesPerOpinion", JSONHelper.getNullable(settings.getMaxResponsesPerOpinion()));
				output.put("firstCreditResponses", JSONHelper.getNullable(settings.getInitCountSessions()));
				output.put("creditResponses", JSONHelper.getNullable(settings.getSuppliedCountSessions()));
				output.put("creditResponsesPeriod", JSONHelper.getNullable(settings.getSupplyIntervalInDays()));
			}
		} 
		
		HashMap<String, IVariableSet> variables = null;
		if(null == result){
			OperationResult<HashMap<String, IVariableSet>> variablesResult = ParametersManager.getVariables(servicePackageId,  EntityType.ServicePackage, null, null);
			if(variablesResult.hasError()){
				result = variablesResult;
			} else {
				variables = variablesResult.getValue();
			}
		}
		
		if(null == result){
			Set<String> keys = variables.keySet();
			JSONArray variablesJa = new JSONArray();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				IVariableSet variableSet = variables.get(key);
				IVariable variable = variableSet.getActual();
				IVariable previousVariable = null;
				if(variable.getEntityType() == EntityType.ServicePackage){
					previousVariable = variableSet.getPrevious(variable.getPriorityId());
				} else {
					previousVariable = variable;
				}
				
				JSONObject variableJo = new JSONObject();
				variableJo.put("key", key);
				variableJo.put("value", variable.getJsonValue());
				variableJo.put("value", variable.getJsonValue());
				variableJo.put("name", variableSet.getName());
				variableJo.put("inherited", variable.getEntityType() != EntityType.ServicePackage);
				variableJo.put("parentValue", previousVariable.getJsonValue());
				variablesJa.put(variableJo);
			}
			output.put("permissions", variablesJa);
		}
		
		if(null != result) {
			output = result.toJson();
		}
		
		return output;
	}
	
	public JSONObject updatePackageSettings(final JSONObject input) throws JSONException, IOException{
		
		BulkOperationResults<IOperationResult> results = new BulkOperationResults<>();
		
		final int servicePackageId = input.getInt("packageId");
		final double amount = input.getDouble("amount");
		
		results.add(ServicePackagesManager.updateServicePackage(new IServicePackageUpdateRequest() {

			@Override
			public int getId() {
				return servicePackageId;
			}

			@Override
			public String getName() {
				return JSONHelper.optStringTrim(input, "packageName");
			}

			@Override
			public Double getAmount() {
				return amount;
			}

			@Override
			public String getDescription() {
				return JSONHelper.optStringTrim(input, "description");
			}

			@Override
			public Integer getDefaultUsagePeriod() {
				return JSONHelper.optInt(input, "usagePeriod");
			}
		}));
		
		if(!results.hasFailures()){
			results.add(OpinionServicePackagesManager.updateServicePackageSettings(new IOpinionServicePackageSettingsUpdateRequest() {
				
				@Override
				public Integer getSupplyIntervalInDays() {
					return JSONHelper.optInt(input, "creditResponsesPeriod");
				}
				
				@Override
				public Long getSuppliedCountSessions() {
					return JSONHelper.optLong(input, "creditResponses");
				}
				
				@Override
				public int getServicePackageId() {
					return servicePackageId;
				}
				
				@Override
				public Long getMaxResponsesPerOpinion() {
					return JSONHelper.optLong(input, "maxResponsesPerOpinion");
				}
				
				@Override
				public Integer getMaxOpinions() {
					return JSONHelper.optInt(input, "maxOpinions");
				}
				
				@Override
				public Integer getMaxControlsPerOpinion() {
					return JSONHelper.optInt(input, "maxControlsPerOpinion");
				}
				
				@Override
				public Integer getMaxCollectors() {
					return JSONHelper.optInt(input, "maxCollectors");
				}
				
				@Override
				public Long getInitCountSessions() {
					return JSONHelper.optLong(input, "firstCreditResponses");
				}
			}));
		}
		
		if(!results.hasFailures()){
			JSONArray permissionsJa = JSONHelper.optJsonArray(input, "permissions");
			final long modifyUserId = getContext().getUserId().getValue();
			for (int i = 0; i < permissionsJa.length(); i++) {
				JSONObject pairJo = permissionsJa.getJSONObject(i);
				Object value = pairJo.get("value");
				String key = pairJo.getString("key");
				if(null == value || JSONObject.NULL.equals(value)){
					// Remove personal variable
					results.add(ParametersManager.deleteReferences(key, ParametersManager.getReferenceKey(EntityType.ServicePackage, servicePackageId), null));
				} else {
					// Insert/Update personal variable 
					results.add(ParametersManager.updateVariable(EntityType.ServicePackage, servicePackageId, key, value, modifyUserId, null));
				}
			}
		}
		
		if(results.hasFailures()){
			return results.getFirstFail().toJson();
		} else {
			return BaseOperationResult.JsonOk;
		}
		
	}
}
