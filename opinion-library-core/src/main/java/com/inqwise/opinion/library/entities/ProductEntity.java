package com.inqwise.opinion.library.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.servicePackages.IServicePackage;
import com.inqwise.opinion.library.dao.ProductsDataAccess;
import com.inqwise.opinion.library.managers.ServicePackagesManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;

public class ProductEntity implements IProduct  {

	static ApplicationLog logger = ApplicationLog.getLogger(UsersManager.class);
	
	private int id;
	private UUID guid;
	private String name;
	private String description;
	private String feedbackCaption;
	private String feedbackShortCaption;
	private String supportEmail;
	private String noreplyEmail;
	private String adminEmail;
	private String salesEmail;
	private String contactUsEmail;
	//private Hashtable<Integer, IServicePackage> servicePackages;
	private String url;
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	private ProductEntity(ResultSet reader) throws SQLException{
		setId(reader.getInt("product_id"));
		setGuid(ResultSetHelper.optUUID(reader, "product_guid"));
		setName(reader.getString("product_name"));
		setDescription(ResultSetHelper.optString(reader, "description"));
		setFeedbackCaption(ResultSetHelper.optString(reader, "feedback_caption"));
		setFeedbackShortCaption(ResultSetHelper.optString(reader, "feedback_short_caption"));
		setSupportEmail(ResultSetHelper.optString(reader, "support_email"));
		setNoreplyEmail(ResultSetHelper.optString(reader, "no_reply_email"));
		setAdminEmail(ResultSetHelper.optString(reader, "admin_email"));
		setSalesEmail(ResultSetHelper.optString(reader, "sales_email"));
		setContactUsEmail(ResultSetHelper.optString(reader, "contact_us_email"));
	}
	
	public void setGuid(UUID guid) {
		this.guid = guid;
	}

	public UUID getGuid() {
		return guid;
	}

	public void setFeedbackCaption(String feedbackCaption) {
		this.feedbackCaption = feedbackCaption;
	}

	public String getFeedbackCaption() {
		return feedbackCaption;
	}

	public void setFeedbackShortCaption(String feedbackShortCaption) {
		this.feedbackShortCaption = feedbackShortCaption;
	}

	public String getFeedbackShortCaption() {
		return feedbackShortCaption;
	}

	public void setSupportEmail(String supportEmail) {
		this.supportEmail = supportEmail;
	}

	public String getSupportEmail() {
		return supportEmail;
	}

	public void setNoreplyEmail(String noreplyEmail) {
		this.noreplyEmail = noreplyEmail;
	}

	public String getNoreplyEmail() {
		return noreplyEmail;
	}

	public static OperationResult<IProduct> getProduct(UUID productGuid, Integer productId) {
		
		final OperationResult<IProduct> result = new OperationResult<IProduct>();
		
		IResultSetCallback callback = new IResultSetCallback() {
			
			public void call(ResultSet reader, int generationId) throws Exception {
				while(reader.next()){
					switch(generationId){
					case 0: // product
						if(result.hasValue()){
							throw new Exception("getProduct() : More than one record receaved");
						} else {
							ProductEntity product = new ProductEntity(reader);
							result.setValue(product);
						}
						break;
					}
				}
			}
		};
		
		try{
			if(null == productGuid && null == productId){
				UUID errorId = logger.error("getProduct : productGuid or productId is mandatory");
				result.setError(ErrorCode.ArgumentNull, errorId, "productGuid or productId is mandatory");
			}
			
			if (!result.hasError()){
				ProductsDataAccess.getProductsReader(productGuid, callback, productId);
				if(!result.hasValue() && !result.hasError()){
					logger.warn("No results for product '%s'", productGuid.toString());
					result.setError(ErrorCode.NoResults);
				}
			}
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "getProduct() : Error is occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public static OperationResult<Collection<IProduct>> getProducts() {
		
		final OperationResult<Collection<IProduct>> result = new OperationResult<Collection<IProduct>>();
		final Hashtable<Integer, IProduct> products = new Hashtable<Integer, IProduct>();
		IResultSetCallback callback = new IResultSetCallback() {
			
			public void call(ResultSet reader, int generationId) throws Exception {
				while(reader.next()){
					switch(generationId){
					case 0: // product
						ProductEntity product = new ProductEntity(reader);
						products.put(product.getId(), product);
						
						break;
					}
				}
			}
		};
		
		try{
			if (!result.hasError()){
				ProductsDataAccess.getProductsReader(null, callback, null);
				if(!result.hasError() && products.size() == 0){
					result.setError(ErrorCode.NoResults);
				} else {
					result.setValue(products.values());
				}
			}
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "getProducts() : Error is occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setSalesEmail(String salesEmail) {
		this.salesEmail = salesEmail;
	}

	public String getSalesEmail() {
		return salesEmail;
	}

	public void setContactUsEmail(String contactUsEmail) {
		this.contactUsEmail = contactUsEmail;
	}

	public String getContactUsEmail() {
		return contactUsEmail;
	}

	public IServicePackage getServicePackage(int servicePackageId) {
		IServicePackage servicePackage = null;
		OperationResult<IServicePackage> result = ServicePackagesManager.getServicePackage(servicePackageId, getId());
		
		if(result.hasError()){
			throw new NullPointerException(String.format("getServicePackage : servicePackage #%s not exist in product #%s.", servicePackageId, getId()));
		} else {
			servicePackage = result.getValue();
		}
		
		return servicePackage;
	}

	public Collection<IServicePackage> getServicePackages() {
		OperationResult<List<IServicePackage>> result = ServicePackagesManager.getServicePackages(this.getId(), false);
		if(null == result){
			return null;
		}
		return result.getValue();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public IServicePackage getServicePackageOrNull(int servicePackageId) {
		
		IServicePackage servicePackage;
		OperationResult<IServicePackage> result = ServicePackagesManager.getServicePackage(servicePackageId, getId());
		
		if(result.hasError()){
			servicePackage = null;
		} else {
			servicePackage =  result.getValue();
		}
		
		return servicePackage;
	}

	public IServicePackage getDefaultServicePackage() {
		
		IServicePackage servicePackage = null;
		List<IServicePackage> list = null;
		OperationResult<List<IServicePackage>> result = ServicePackagesManager.getServicePackages(this.getId(), false);
		if(result.hasError()){
			result = null;
		} else {
			list = result.getValue();
			
			for (IServicePackage actualServicePackage : list) {
				if(actualServicePackage.isDefault()){
					servicePackage = actualServicePackage;
					break;
				}
			}
		}
		
		return servicePackage;
	}
}
