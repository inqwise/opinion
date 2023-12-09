package com.inqwise.opinion.library.managers;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccountView;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.entities.ProductEntity;
import com.inqwise.opinion.library.systemFramework.ApplicationConfiguration;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class ProductsManager {
	static ApplicationLog logger = ApplicationLog.getLogger(ProductsManager.class);
	
	private static Object _productsCacheLocker = new Object(); 
	private static LoadingCache<Object, OperationResult<IProduct>> _productsCache;
	private static LoadingCache<Object, OperationResult<IProduct>> getProductsCache(){
		if(null == _productsCache){
			synchronized (_productsCacheLocker) {
				if(null == _productsCache){
					_productsCache = CacheBuilder.newBuilder().
							maximumSize(100).
							expireAfterWrite(1, TimeUnit.HOURS).
							build(new CacheLoader<Object, OperationResult<IProduct>>(){
								@Override
								public OperationResult<IProduct> load(
										Object key) throws Exception {
									return null;
								}
							});
				}
			}
		}
		
		return _productsCache;
	}
	
	public static OperationResult<IProduct> getProductByGuid(final UUID productGuid){
		try {
			return getProductsCache().get(productGuid, new Callable<OperationResult<IProduct>>() {

				public OperationResult<IProduct> call() throws Exception {
					return ProductEntity.getProduct(productGuid, null);
				}
			});
		} catch (ExecutionException e) {
			UUID errorTicket = logger.error(e, "getProductByGuid: Unexpected error occured");
			return new OperationResult<IProduct>(ErrorCode.GeneralError, errorTicket);
		}
	}
	
	public static OperationResult<IProduct> getProductById(final int productId){
		try {
			return getProductsCache().get(productId, new Callable<OperationResult<IProduct>>() {

				public OperationResult<IProduct> call() throws Exception {
					return ProductEntity.getProduct(null, productId);
				}
			});
		} catch (ExecutionException e) {
			UUID errorTicket = logger.error(e, "getProductById: Unexpected error occured");
			return new OperationResult<IProduct>(ErrorCode.GeneralError, errorTicket);
		}
	}

	private static IProduct currentProduct;
	
	public static IProduct getCurrentProduct() {
		
		if(null == currentProduct){
			synchronized (IProduct.class) {
				if(null == currentProduct){
					OperationResult<IProduct> productResult = getProductByGuid(ApplicationConfiguration.General.getProductGuid());
					if(!productResult.hasError()){
						currentProduct = productResult.getValue();
					}
				}
			}
		}
		
		return currentProduct;
	}
	
	public static OperationResult<Collection<IProduct>> getProducts() {
		
		return ProductEntity.getProducts();
	}
}
