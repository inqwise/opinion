package com.inqwise.opinion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.dao.SqlParam;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.DAOBase;
import com.inqwise.opinion.library.dao.DAOFactory;
import com.inqwise.opinion.library.dao.Databases;
import com.inqwise.opinion.common.IControl;
import com.inqwise.opinion.common.IControlRequest;
import com.inqwise.opinion.common.ICreateResult;
import com.inqwise.opinion.common.IModifyControlDetailsRequest;
import com.inqwise.opinion.common.IOption;
import com.inqwise.opinion.common.ParentType;
import com.inqwise.opinion.common.analizeResults.IControlsContainer;
import com.inqwise.opinion.common.analizeResults.IOptionsContainer;

public final class ControlsDataAccess extends DAOBase {
	private static final String CONTROL_ID_PARAM = "$control_id";
	private static final String ACCOUNT_ID_PARAM = "$account_id";
	private static final String CONTROL_TYPE_ID_PARAM = "$control_type_id";
	private static final String CONTENT_PARAM = "$content";
	private static final String ORDER_ID_PARAM = "$order_id";
	private static final String OPINION_ID_PARAM = "$opinion_id";
	private static final String PARENT_ID_PARAM = "$parent_id";
	private static final String PARENT_TYPE_ID_PARAM = "$parent_type_id";
	private static final String TRANSLATION_ID_PARAM = "$translation_id";
	private static final String ARRANGE_ID_PARAM = "$arrange_id";
	private static final String PAGE_NUMBER_PARAM = "$page_number";
	private static final String NOTE_PARAM = "$note";
	private static final String IS_MANDATORY_PARAM = "$is_mandatory";
	private static final String IS_ENABLE_NOTE_PARAM = "$is_enable_note";
	private static final String INPUT_TYPE_ID_PARAM = "$input_type_id";
	private static final String COMMENT_PARAM = "$comment";
	private static final String IS_ENABLE_COMMENT_PARAM = "$is_enable_comment";
	private static final String JOINED_IDS_PARAM = "$ids";
	private static final String INPUT_SIZE_TYPE_ID_PARAM = "$input_size_type_id";
	private static final String YEAR_TITLE_PARAM = "$year_title";
	private static final String MONTH_TITLE_PARAM = "$month_title";
	private static final String DAY_TITLE_PARAM = "$day_title";
	private static final String HOUR_TITLE_PARAM = "$hour_title";
	private static final String MINUTE_TITLE_PARAM = "$minute_title";
	private static final String TIMEZONE_TITLE_PARAM = "$timezone_title";
	private static final String FROM_SCALE_PARAM = "$from_scale";
	private static final String TO_SCALE_PARAM = "$to_scale";
	private static final String FROM_SCALE_TITLE_PARAM = "$from_scale_title";
	private static final String TO_SCALE_TITLE_PARAM = "$to_scale_title";
	private static final String LINK_PARAM = "$link";
	private static final String LINK_TYPE_ID_PARAM = "$link_type_id";
	private static final String ANSWER_SESSION_ID_PARAM = "$answer_session_id";
	private static final String ACTION_GUID_PARAM = "$action_guid";
	private static final String USER_ID_PARAM = "$user_id";
	private static final String IS_HIDDEN_PARAM = "$is_hidden";
	private static final String CONTROL_KEY_PARAM = "$control_key";
	private static final String IS_NUMERABLE_PARAM = "$numerable";

	public static OperationResult<ICreateResult> setControl(
			IControlRequest request) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		OperationResult<ICreateResult> result;

		SqlParam[] params = {
				new SqlParam(CONTROL_TYPE_ID_PARAM, request.getControlTypeId()),
				new SqlParam(CONTENT_PARAM, request.getContent()),
				new SqlParam(ACCOUNT_ID_PARAM, request.getAccountId()),
				new SqlParam(ORDER_ID_PARAM, request.getOrderId()),
				new SqlParam(OPINION_ID_PARAM, request.getOpinionId()),
				new SqlParam(PARENT_ID_PARAM, request.getParentId()),
				new SqlParam(PARENT_TYPE_ID_PARAM, request.getParentTypeId()),
				new SqlParam(TRANSLATION_ID_PARAM, request.getTranslationId()),
				new SqlParam(ARRANGE_ID_PARAM, request.getArrangeId()),
				new SqlParam(NOTE_PARAM, request.getNote()),
				new SqlParam(IS_MANDATORY_PARAM, request.getIsMandatory()),
				new SqlParam(IS_ENABLE_NOTE_PARAM, null),
				new SqlParam(INPUT_TYPE_ID_PARAM, request.getInputTypeId()),
				new SqlParam(COMMENT_PARAM, request.getComment()),
				new SqlParam(IS_ENABLE_COMMENT_PARAM, null),
				new SqlParam(INPUT_SIZE_TYPE_ID_PARAM, request
						.getInputSizeTypeId()),
				new SqlParam(YEAR_TITLE_PARAM, request.getYearTitle()),
				new SqlParam(MONTH_TITLE_PARAM, request.getMonthTitle()),
				new SqlParam(DAY_TITLE_PARAM, request.getDayTitle()),
				new SqlParam(HOUR_TITLE_PARAM, request.getHourTitle()),
				new SqlParam(MINUTE_TITLE_PARAM, request.getMinuteTitle()),
				new SqlParam(TIMEZONE_TITLE_PARAM, request.getTimezoneTitle()),
				new SqlParam(FROM_SCALE_PARAM, request.getFromScale()),
				new SqlParam(TO_SCALE_PARAM, request.getToScale()),
				new SqlParam(FROM_SCALE_TITLE_PARAM, request
						.getFromScaleTitle()),
				new SqlParam(TO_SCALE_TITLE_PARAM, request.getToScaleTitle()),
				new SqlParam(LINK_PARAM, request.getLink()),
				new SqlParam(LINK_TYPE_ID_PARAM, request.getLinkTypeId()),
				new SqlParam(ACTION_GUID_PARAM, request.getActionGuid()),
				new SqlParam(USER_ID_PARAM, request.getUserId()),
				new SqlParam(IS_HIDDEN_PARAM, request.isHidden()),
				new SqlParam(CONTROL_KEY_PARAM, request.getKey()),
				new SqlParam(IS_NUMERABLE_PARAM, request.isNumerable()),
			};
		

		try {
			Database factory = DAOFactory.getInstance(Databases.Opinion);

			call = factory.GetProcedureCall("setControl", params);

			connection = call.getConnection();

			resultSet = call.executeQuery();

			if (resultSet.next()) {

				int errorCode = (null == resultSet.getObject("error_code")) ? 0
						: resultSet.getInt("error_code");

				switch (errorCode) {
				case 0:
					final Long controlId = Long.valueOf(resultSet
							.getLong("control_id"));
					final Integer orderId = Integer.valueOf(resultSet
							.getInt("order_id"));
					result = new OperationResult<ICreateResult>(
							new ICreateResult() {

								@Override
								public Long getId() {
									return controlId;
								}

								@Override
								public Integer getOrderId() {
									return orderId;
								}
							});
					break;
				case 4: // no have permissions
					result = new OperationResult<ICreateResult>(
							ErrorCode.NotPermitted);
					break;
				default: // Unsupported Error
					throw new OperationNotSupportedException(
							"setControl() : errorCode not supported. Value: "
									+ errorCode);
				}

			} else {
				result = new OperationResult<ICreateResult>(ErrorCode.NoResults);
			}

			return result;

		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	/*
	public static OperationResult<IControl> getControl(Long controlId,
			IDataFillable<IControl> data, Long accountId, String answerSessionId) throws DAOException {
		return getControl(controlId, null, data, accountId, answerSessionId);
	}

	
	public static OperationResult<IControl> getControl(Long controlId,
			Long translationId, IDataFillable<IControl> data, Long accountId,
			String answerSessionId) throws DAOException {
		OperationResult<IControl> fillControlResult = fillControls(controlId,
				null, null, null, null, translationId, data, null, accountId,
				answerSessionId);
		if (fillControlResult.hasError()) {
			return fillControlResult.toErrorResult();
		} else {
			try {
				return new OperationResult<IControl>(fillControlResult
						.getValue());
			} catch (Exception e) {
				throw new DAOException(e);
			}
		}
	}

	public static OperationResult<List<IControl>> getControls(Long parentId,
			Integer parentTypeId, Long opinionId, Integer pageNumber,
			Long translationId, IDataFillable<IControl> data, Long accountId,
			String answerSessionId) throws DAOException {

		List<IControl> controls = new ArrayList<IControl>();
		BaseOperationResult fillControlsResult = fillControls(null, parentId,
				parentTypeId, opinionId, pageNumber, translationId, data,
				controls, accountId, answerSessionId);
		if (fillControlsResult.hasError()) {
			return fillControlsResult.toErrorResult();
		} else {
			return new OperationResult<List<IControl>>(controls);
		}
	}
*/
	public static List<IControl> getControls(Long controlId,
			Long parentId,
			Integer parentTypeId, Long opinionId, Integer pageNumber,
			Long translationId, Long accountId, String answerSessionId,
			IDataFillable<IControl> controlHandler,
			IDataFillable<IOption> optionHandler) throws DAOException {
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		Dictionary<Long, IControl> controlsDictionary = new Hashtable<Long, IControl>();
		List<IControl> controls = new ArrayList<IControl>();

		try {
			
			SqlParam[] params = 
			{
					new SqlParam(CONTROL_ID_PARAM, controlId),
					new SqlParam(PARENT_ID_PARAM, parentId),
					new SqlParam(PARENT_TYPE_ID_PARAM, parentTypeId),
					new SqlParam(OPINION_ID_PARAM, opinionId),
					new SqlParam(PAGE_NUMBER_PARAM, pageNumber),
					new SqlParam(TRANSLATION_ID_PARAM, translationId),
					new SqlParam(ACCOUNT_ID_PARAM, accountId),
					new SqlParam(ANSWER_SESSION_ID_PARAM, answerSessionId),
			};
			
			Database factory = DAOFactory
					.getInstance(Databases.Opinion);
			call = factory.GetProcedureCall("getControlsAndOptions", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			// controls
			while (resultSet.next()) {
				IControl control = controlHandler.fill(resultSet);
				if (control.getParentType() == ParentType.Control && !controlsDictionary.isEmpty()) {
					IControlsContainer<IControl> controlsContainer = (IControlsContainer<IControl>) controlsDictionary.get(control.getParentId());
					controlsContainer.addControl(control);
				} else {
					controls.add(control);
				}
				controlsDictionary.put(control.getId(),
						control);
			}
			resultSet.close();

			// options
			if (call.getMoreResults()) {
				resultSet = call.getResultSet();
				while (resultSet.next()) {
					IOption optionResult = optionHandler.fill(resultSet);
					IOptionsContainer<IOption> optionsContainer = (IOptionsContainer<IOption>) controlsDictionary.get(optionResult.getControlId());
					optionsContainer.addOption(optionResult);
				}
			}

			return controls;

		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call, e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
	
	public static BaseOperationResult deleteControl(Long id, Long accountId, long userId)
			throws DAOException {

		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;

		SqlParam[] params = { 
				new SqlParam(CONTROL_ID_PARAM, id),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(USER_ID_PARAM, userId),
		};

		try {
			call = factory.GetProcedureCall("deleteControl", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			if (resultSet.next()) {

				int errorCode = (null == resultSet.getObject("error_code")) ? 0
						: resultSet.getInt("error_code");

				switch (errorCode) {
				case 0:
					result = new BaseOperationResult(ErrorCode.NoError);
					break;
				case 4: // no have permissions
					result = new BaseOperationResult(ErrorCode.NotPermitted);
					break;
				default: // Unsupported Error
					throw new OperationNotSupportedException(
							"deleteControl() : errorCode not supported. Value: "
									+ errorCode);
				}

			} else {
				result = new OperationResult<String>(ErrorCode.NoResults);
			}

			return result;

		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call
					,e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}

	}

	public static BaseOperationResult updateControlDetails(
			IModifyControlDetailsRequest request) throws DAOException {

		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;

		SqlParam[] params = {
				new SqlParam(CONTROL_ID_PARAM, request.getControlId()),
				new SqlParam(TRANSLATION_ID_PARAM, request.getTranslationId()),
				new SqlParam(CONTENT_PARAM, request.getContent()),
				new SqlParam(NOTE_PARAM, request.getNote()),
				new SqlParam(IS_MANDATORY_PARAM, request.getIsMandatory()),
				new SqlParam(ACCOUNT_ID_PARAM, request.getAccountId()),
				new SqlParam(IS_ENABLE_NOTE_PARAM, null),
				new SqlParam(INPUT_TYPE_ID_PARAM, request.getInputTypeId()),
				new SqlParam(COMMENT_PARAM, request.getComment()),
				new SqlParam(IS_ENABLE_COMMENT_PARAM, null),
				new SqlParam(INPUT_SIZE_TYPE_ID_PARAM, request
						.getInputSizeTypeId()),
				new SqlParam(YEAR_TITLE_PARAM, request.getYearTitle()),
				new SqlParam(MONTH_TITLE_PARAM, request.getMonthTitle()),
				new SqlParam(DAY_TITLE_PARAM, request.getDayTitle()),
				new SqlParam(HOUR_TITLE_PARAM, request.getHourTitle()),
				new SqlParam(MINUTE_TITLE_PARAM, request.getMinuteTitle()),
				new SqlParam(TIMEZONE_TITLE_PARAM, request.getTimezoneTitle()),
				new SqlParam(FROM_SCALE_PARAM, request.getFromScale()),
				new SqlParam(TO_SCALE_PARAM, request.getToScale()),
				new SqlParam(FROM_SCALE_TITLE_PARAM, request
						.getFromScaleTitle()),
				new SqlParam(TO_SCALE_TITLE_PARAM, request.getToScaleTitle()),
				new SqlParam(LINK_PARAM, request.getLink()),
				new SqlParam(LINK_TYPE_ID_PARAM, request.getLinkTypeId()), 
				new SqlParam(USER_ID_PARAM, request.getUserId()),
				new SqlParam(CONTROL_KEY_PARAM, request.getKey()),
		};

		try {
			call = factory.GetProcedureCall("updateControlDetails", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			if (resultSet.next()) {

				int errorCode = (null == resultSet.getObject("error_code")) ? 0
						: resultSet.getInt("error_code");

				switch (errorCode) {
				case 0:
					result = BaseOperationResult.Ok;
					break;
				case 4: // no have permissions
					result = new BaseOperationResult(ErrorCode.NotPermitted);
					break;
				default: // Unsupported Error
					throw new OperationNotSupportedException(
							"updateControlDetails() : errorCode not supported. Value: "
									+ errorCode);
				}

			} else {
				result = new OperationResult<String>(ErrorCode.NoResults);
			}

			return result;

		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call
					,e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}

	}

	public static BaseOperationResult reorderControl(Long controlId,
			Long accountId, Long parentId, Integer parentTypeId, Integer orderId, long userId)
			throws DAOException {
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;

		SqlParam[] params = { new SqlParam(CONTROL_ID_PARAM, controlId),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(PARENT_ID_PARAM, parentId),
				new SqlParam(PARENT_TYPE_ID_PARAM, parentTypeId),
				new SqlParam(ORDER_ID_PARAM, orderId),
				new SqlParam(USER_ID_PARAM, userId),
				};

		try {
			call = factory.GetProcedureCall("reorderControl", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			if (resultSet.next()) {

				int errorCode = (null == resultSet.getObject("error_code")) ? 0
						: resultSet.getInt("error_code");

				switch (errorCode) {
				case 0:
					result = BaseOperationResult.Ok;
					break;
				case 4: // no have permissions
					result = new BaseOperationResult(ErrorCode.NotPermitted);
					break;
				default: // Unsupported Error
					throw new OperationNotSupportedException(
							"reorderControl() : errorCode not supported. Value: "
									+ errorCode);
				}

			} else {
				result = new OperationResult<String>(ErrorCode.NoResults);
			}

			return result;

		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call
					,e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}

	public static BaseOperationResult orderControls(String joinedIds,
			Long accountId, long userId) throws DAOException {
		Database factory = DAOFactory.getInstance(Databases.Opinion);
		Connection connection = null;
		CallableStatement call = null;
		ResultSet resultSet = null;
		BaseOperationResult result;

		SqlParam[] params = { new SqlParam(JOINED_IDS_PARAM, joinedIds),
				new SqlParam(ACCOUNT_ID_PARAM, accountId),
				new SqlParam(USER_ID_PARAM, userId),
				};

		try {
			call = factory.GetProcedureCall("orderControls", params);
			connection = call.getConnection();
			resultSet = call.executeQuery();
			if (resultSet.next()) {

				int errorCode = (null == resultSet.getObject("error_code")) ? 0
						: resultSet.getInt("error_code");

				switch (errorCode) {
				case 0:
					result = new BaseOperationResult(ErrorCode.NoError);
					break;
				case 4: // no have permissions
					result = new BaseOperationResult(ErrorCode.NotPermitted);
					break;
				default: // Unsupported Error
					throw new OperationNotSupportedException(
							"orderOptions() : errorCode not supported. Value: "
									+ errorCode);
				}

			} else {
				result = new OperationResult<String>(ErrorCode.NoResults);
			}

			return result;

		} catch (Exception e) {
			throw null == call ? new DAOException(e) : new DAOException(call
					,e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(call);
			DAOUtil.close(connection);
		}
	}
}
