package com.inqwise.opinion.cms.managers;

import java.util.List;

import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.cms.common.faq.IFaq;
import com.inqwise.opinion.cms.common.faq.INewQuestionEmailData;
import com.inqwise.opinion.cms.entities.FaqEntity;

public class FaqsManager {
	public static OperationResult<List<IFaq>> getFaqs(Integer top){
		return FaqEntity.getFaqs(null, null, top);
	}
	
	public static OperationResult<List<IFaq>> searchFaqs(String searchWord, String categoryName, Integer top){
		return FaqEntity.getFaqs(searchWord, categoryName, top);
	}

	public static BaseOperationResult submitQuestion(final String question,
			final String name, final String email, final Long userId) {
		
		BaseOperationResult result = null;
		
		// collect data for notification
		IProduct product = null;
		if(null == result){
			
			product = ProductsManager.getCurrentProduct();
			if(null == product){
				result = new BaseOperationResult(ErrorCode.NoResults);
			}
		}
		
		if(null == result){
			final IProduct finalProduct = product;
			INewQuestionEmailData data = new INewQuestionEmailData() {
				
				@Override
				public String getQuestion() {
					return question;
				}
				
				@Override
				public String getNoreplyEmail() {
					return finalProduct.getNoreplyEmail();
				}
				
				@Override
				public String getFeedbackCaption() {
					return finalProduct.getFeedbackCaption();
				}
				
				@Override
				public String getEmail() {
					return finalProduct.getSupportEmail();
				}
				
				@Override
				public String getAutorName() {
					return name;
				}
				
				@Override
				public String getAutorEmail() {
					return email;
				}
	
				@Override
				public Long getUserId() {
					return userId;
				}
			};
			
			result = EmailsManager.sendNewQuestionEmail(data);
		}
		
		return result;
	}
}
