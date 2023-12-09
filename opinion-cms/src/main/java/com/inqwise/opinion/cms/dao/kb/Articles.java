package com.inqwise.opinion.cms.dao.kb;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.inqwise.opinion.cms.CmsConfiguration;
import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.dao.DAOFactory;

public class Articles {

	private static final String ARTICLE_TITLE_PARAM = "$article_title";
	private static final String ARTICLE_ID_PARAM = "$article_id";
	private static final String ARTICLE_URI_PARAM = "$article_uri";
	private static final String ARTICLE_CONTENT_PARAM = "$article_content";
	private static final String TOPIC_ID_PARAM = "$topic_id";
	/*private static final String TOPICS_PARAM = "$topics";*/
	private static final String ORDER_ID_PARAM = "$order_id";
	private static final String IS_POPULAR_PARAM = "$is_popular";
	private static final String IS_ACTIVE_PARAM = "$is_active";

	public static void get(IResultSetCallback callback, Integer id, String uri, Integer topicId)
			throws DAOException {

		CallableStatement call = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {

			SqlParam[] params = { 
					new SqlParam(ARTICLE_ID_PARAM, id),
					new SqlParam(ARTICLE_URI_PARAM, uri),
					new SqlParam(TOPIC_ID_PARAM, topicId)
			};

			Database factory = DAOFactory.getInstance(CmsConfiguration
					.getDatabaseName());

			call = factory.GetProcedureCall("getArticles", params);
			connection = call.getConnection();

			resultSet = call.executeQuery();

			callback.call(resultSet, 1);

			/*
			 * ResultSet reader = call.executeQuery();
			 * 
			 * // Article callback.call(reader, 1); reader.close();
			 */

			/*
			 * // Tags if(call.getMoreResults()){
			 * callback.call(call.getResultSet(), 2); }
			 */

		} catch (Exception e) {
			throw null == call ? new DAOException(e)
					: new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static int set(Integer id, String title, String uri, String content,
			Integer topicId, Boolean popular, Boolean active) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;

		SqlParam[] params = {
				new SqlParam(ARTICLE_ID_PARAM, id),
				new SqlParam(ARTICLE_TITLE_PARAM, title),
				new SqlParam(ARTICLE_URI_PARAM, uri),
				new SqlParam(ARTICLE_CONTENT_PARAM, content),
				new SqlParam(TOPIC_ID_PARAM, topicId),
				/*
				new SqlParam(TOPICS_PARAM, (null == topics ? null
						: StringUtils.join(topics, ","))),
						*/
				new SqlParam(ORDER_ID_PARAM, null),
				new SqlParam(IS_POPULAR_PARAM, popular),
				new SqlParam(IS_ACTIVE_PARAM, active)
		};

		try {

			Database factory = DAOFactory.getInstance(CmsConfiguration
					.getDatabaseName());
			call = factory.GetProcedureCall("setArticle", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();

			if (resultSet.next()) {
				id = resultSet.getInt("article_id");
			} else {
				throw new Exception("No Results");
			}

			resultSet.close();
			return id;

		} catch (Exception e) {
			throw null == call ? new DAOException(e)
					: new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}

	}

	public static void delete(Integer id) throws DAOException {

		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;

		SqlParam[] params = { new SqlParam(ARTICLE_ID_PARAM, id) };

		try {

			Database factory = DAOFactory.getInstance(CmsConfiguration
					.getDatabaseName());
			call = factory.GetProcedureCall("deleteArticle", params);
			connection = call.getConnection();
			call.execute();

		} catch (Exception e) {
			throw null == call ? new DAOException(e)
					: new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}

	}

}
