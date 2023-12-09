package com.inqwise.opinion.cms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.cms.common.faq.IFaq;
import com.inqwise.opinion.cms.dao.FaqsDataAccess;;

public class FaqEntity implements IFaq {
	public static ApplicationLog logger = ApplicationLog.getLogger(FaqEntity.class);
	
	private long id;
	private String question;
	private String answer;
	private List<String> categories;
	
	public FaqEntity(ResultSet reader) throws SQLException{
		setId(reader.getLong("faq_id"));
		setQuestion(ResultSetHelper.optString(reader, "faq_question"));
		setAnswer(ResultSetHelper.optString(reader, "faq_answer"));
		categories = new ArrayList<String>();
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestion() {
		return question;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}
	
	public static OperationResult<List<IFaq>> getFaqs(String searchWord, String categoryName, Integer top){
		OperationResult<List<IFaq>> result;
		try{
			IDataFillable<IFaq> data = new IDataFillable<IFaq>()
			{
				public IFaq fill(ResultSet reader) throws Exception
				{
					return new FaqEntity(reader);
				}
			};
			
			result = FaqsDataAccess.getFaqs(data, searchWord, categoryName, top);
		}
		catch(Exception ex){
			UUID errorId = logger.error(ex, "getFaqs() : Error occured.");
			result = new OperationResult<List<IFaq>>(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
	
	public List<String> getCategories(){
		return categories;
	}
}
