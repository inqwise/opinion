package com.inqwise.opinion.cms.managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.inqwise.opinion.cms.common.kb.IArticle;
import com.inqwise.opinion.cms.dao.kb.Articles;
import com.inqwise.opinion.cms.entities.ArticleEntity;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;

public class ArticleManager {

	static ApplicationLog logger = ApplicationLog
			.getLogger(ArticleManager.class);

	public static OperationResult<List<IArticle>> getArticles(Integer topicId) {
		OperationResult<List<IArticle>> result = null;
		final List<IArticle> articles = new ArrayList<IArticle>();
		try {
			IResultSetCallback callback = new IResultSetCallback() {
				@Override
				public void call(ResultSet reader, int generationId)
						throws Exception {
					if (generationId == 1) {
						while (reader.next()) {
							articles.add(new ArticleEntity(reader));
						}
					}
				}
			};

			Articles.get(callback, null, null, topicId);
			result = new OperationResult<List<IArticle>>(articles);

		} catch (Exception ex) {
			UUID errorTicket = logger.error(ex,
					"getArticles : Unexpected error occured");
			result = new OperationResult<List<IArticle>>(
					ErrorCode.GeneralError, errorTicket);
		}

		return result;
	}

	public static OperationResult<IArticle> getArticle(Integer id, String uri) {

		final OperationResult<IArticle> result = new OperationResult<IArticle>();
		try {
			
			IResultSetCallback callback = new IResultSetCallback() {

				@Override
				public void call(ResultSet reader, int generationId)
						throws Exception {
					if (generationId == 1) {
						if (reader.next()) {
							ArticleEntity article = new ArticleEntity(reader);
							result.setValue(article);
						}
					}
				}
			};
			
			Articles.get(callback, id, uri, null);
			
			if (!result.hasError() && !result.hasValue()) {
				result.setError(ErrorCode.NoResults);
			}

		} catch (Exception ex) {
			UUID errorId = logger.error(ex,
					"getArticle() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}

		return result;
	}

	public static OperationResult<Integer> setArticle(String title,
			String uri, String content, Integer topicId, Boolean popular,
			Boolean active) {
		OperationResult<Integer> result = new OperationResult<Integer>();
		
		
		try {
			result.setValue(Articles.set(null, title, uri, content, topicId, popular,
					active));
		} catch (DAOException e) {
			UUID errorId = logger.error(e,
					"setArticle : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}

	public static OperationResult<Integer> updateArticle(Integer id,
			String title, String uri, String content, Integer topicId, Boolean popular,
			Boolean active) {
		OperationResult<Integer> result = new OperationResult<Integer>();
		try {
			result.setValue(Articles.set(id, title, uri, content, topicId, popular,
					active));
		} catch (DAOException e) {
			UUID errorId = logger.error(e,
					"updateArticle : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

	public static BaseOperationResult deleteArticle(Integer id) {
		BaseOperationResult result = new BaseOperationResult();
		try {
			Articles.delete(id);
		} catch (DAOException e) {
			UUID errorId = logger.error(e,
					"deleteArticle : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}

}
