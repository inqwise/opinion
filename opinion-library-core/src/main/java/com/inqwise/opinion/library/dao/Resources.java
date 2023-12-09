package com.inqwise.opinion.library.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.DAOUtil;
import com.inqwise.opinion.infrastructure.dao.Database;
import com.inqwise.opinion.library.common.basicTypes.Skipable;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.entities.FileEntity;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public final class Resources extends DAOBase {
	
	static ApplicationLog logger = ApplicationLog.getLogger(Resources.class);
	
	
	public static <T> List<T> getItemsByParentId(Long parentId, Long accountId, IDataFillable<T> data) throws DAOException {
    	
		Database factory = DAOFactory.getInstance(Databases.Resources);
		Connection connection = null;
    	CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		List<T> items = new ArrayList<T>();
		try {
			callableStatement = connection.prepareCall("{ CALL getItemsByParentId(?, ?) }");
			DAOUtil.setValues(callableStatement, parentId, accountId);
            resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
            	items.add(data.fill(resultSet));
            }
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(callableStatement);
			DAOUtil.close(connection);
		}
		
		return items;
    }

	public static <T> BaseOperationResult getItem(Long id, Integer itemType, Long accountId, IDataFillable<T> data) throws DAOException {
    	
		Database factory = DAOFactory.getInstance(Databases.Resources);
		Connection connection = null;
    	CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		try {
			connection = factory.getConnection();
			callableStatement = connection.prepareCall("{ CALL getItem(?, ?, ?, ?) }");
			DAOUtil.setValues(callableStatement, id, itemType, accountId, true);
            resultSet = callableStatement.executeQuery();
            if(resultSet.next()) {
            	T item = data.fill(resultSet);
            	result = new OperationResult<T>(item);
            } else {
            	result = new OperationResult<T>(ErrorCode.NoResults);
            }
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(callableStatement);
			DAOUtil.close(connection);
		}
		
		return result;
    }
	
	public static OperationResult<Long> addFolder(String folderName, Long parentId,
			Long rootId, Long accountId, String clientIp) throws DAOException {
		
		OperationResult<Long> result = null;
		Database factory = DAOFactory.getInstance(Databases.Resources);
		Connection connection = null;
    	CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = factory.getConnection();
			callableStatement = connection.prepareCall("{ CALL setFolder(?,?,?,?,?,?,?,?,?,?,?) }");
			Object[] values = { 
            		folderName,
            		parentId,
            		rootId,
            		null,	// content
            		null,	// description
            		null,	// alowedContentTypes
            		null,	// ownerId
            		false,	// isRoot
            		accountId,
            		clientIp,
            		true
            };
            
            DAOUtil.setValues(callableStatement, values);
            resultSet = callableStatement.executeQuery();
            if(resultSet.next()) {
            	int errorCode = (null == resultSet.getObject("error_code")) ? 0 : resultSet.getInt("error_code");
            	switch(errorCode){
            	case 0:
            		Long folderId = resultSet.getLong("file_id");
            		result = new OperationResult<Long>(folderId, resultSet.getLong("transaction_id"));
            		break;
            	case 1: // Name already exist
            		result = new OperationResult<Long>(ErrorCode.NameAlreadyExist);
            		break;
            	case 2: // parentId not exist
            		result = new OperationResult<Long>(ErrorCode.ParentIdNotExist);
            		break;
            	case 3: // rootId not exist
            		result = new OperationResult<Long>(ErrorCode.RootIdNotExist);
            		break;
            	case 4: // no have permissions
            		result = new OperationResult<Long>(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("addFolder() : errorCode not supported. Value: " + errorCode);
            	}
            } else {
            	result = new OperationResult<Long>(ErrorCode.NoResults);
            } 	
            
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(callableStatement);
			DAOUtil.close(connection);
		}
		
		return result;
	}
	
	public static BaseOperationResult deleteItem(Long itemId, Long accountId, String clientIp, Boolean deleteSubItems) throws DAOException {
		
		BaseOperationResult result = null;
		Database factory = DAOFactory.getInstance(Databases.Resources);
		Connection connection = null;
    	CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = factory.getConnection();
			callableStatement = connection.prepareCall("{ CALL deleteItem(?,?,?,?) }");
			Object[] values = { 
            		itemId,
            		accountId,
            		clientIp,
            		true
            };
            
            DAOUtil.setValues(callableStatement, values);
            resultSet = callableStatement.executeQuery();
            if(resultSet.next()) {
            	int errorCode = (null == resultSet.getObject("error_code")) ? 0 : resultSet.getInt("error_code");
            	switch(errorCode){
            	case 0:
            		result = new BaseOperationResult(resultSet.getLong("transaction_id"));
            		break;
            	case 1: // not exits
            		result = new BaseOperationResult(ErrorCode.NotExist);
            		break;
            	case 2: // sub items exists
            		result = new BaseOperationResult(ErrorCode.SubItemsExits);
            		break;
            	case 3: // not permitted
            		result = new BaseOperationResult(ErrorCode.NotPermitted);
            		break;
            	case 4: // no have permissions
            		result = new OperationResult<Long>(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("deleteItem() : errorCode not supported. Value: " + errorCode);
            	}
            } else {
            	result = new BaseOperationResult(ErrorCode.NoResults);
            }
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(callableStatement);
			DAOUtil.close(connection);
		}
		
		return result;
	}
	
	public static OperationResult<Long> addFile(String fileName, String filePath, String extension,
			String contentType, Long size, Long parentId,
			Long rootId, Long accountId, String clientIp) throws DAOException {
		
		OperationResult<Long> result = null;
		Database factory = DAOFactory.getInstance(Databases.Resources);
		Connection connection = null;
    	CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = factory.getConnection();
			callableStatement = connection.prepareCall("{ CALL setFile(?,?,?,?,?,?,?,?,?) }");
			Object[] values = { 
            		fileName,
            		filePath,
            		extension,
            		contentType,
            		size,
            		parentId,
            		rootId,
            		accountId,
            		clientIp,
            		true
            };
            
            DAOUtil.setValues(callableStatement, values);
            resultSet = callableStatement.executeQuery();
            if(resultSet.next()) {
            	int errorCode = (null == resultSet.getObject("error_code")) ? 0 : resultSet.getInt("error_code");
            	switch(errorCode){
            	case 0:
            		Long fileId = resultSet.getLong("file_id");
            		result = new OperationResult<Long>(fileId, resultSet.getLong("transaction_id"));
            		break;
            	case 1: // Name already exist
            		result = new OperationResult<Long>(ErrorCode.NameAlreadyExist);
            		break;
            	case 2: // parentId not exist
            		result = new OperationResult<Long>(ErrorCode.ParentIdNotExist);
            		break;
            	case 3: // rootId not exist
            		result = new OperationResult<Long>(ErrorCode.RootIdNotExist);
            		break;
            	case 4: // no have permissions
            		result = new OperationResult<Long>(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("addFile() : errorCode not supported. Value: " + errorCode);
            	}
            } else {
            	result = new OperationResult<Long>(ErrorCode.NoResults);
            }
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(callableStatement);
			DAOUtil.close(connection);
		}
		
		return result;
	}

	public static OperationResult<String> getContainerFolderPath(Long itemId) throws DAOException {

		Database factory = DAOFactory.getInstance(Databases.Resources);
		Connection connection = null;
    	CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		OperationResult<String> result = null;
		try {
			connection = factory.getConnection();
			callableStatement = connection.prepareCall("{ CALL getContainerFolderPath(?) }");
			DAOUtil.setValues(callableStatement, itemId);
            resultSet = callableStatement.executeQuery();
            if(resultSet.next()) {
            	UUID errorId;
            	long topItemId = resultSet.getLong("item_id");
            	long topRootId = resultSet.getLong("root_id");
            	int iterations = resultSet.getInt("iterations");
            	int errorCode = (null == resultSet.getObject("error_code")) ? 0 : resultSet.getInt("error_code");
            	
            	switch(errorCode){
            	case 0:
            		String path = resultSet.getString("path");
            		result = new OperationResult<String>(path, resultSet.getLong("transaction_id"));
            		logger.info("getContainerFolderPath({0}) : recieved the path to container folder. topItemId='{1}', topRootId='{2}', iterations='{3}', path='{4}'", itemId, topItemId, topRootId, iterations, path);
            		break;
            	case 2: // circle reference or root_id not exist
            		errorId = logger.error("getContainerFolderPath({0}) : Circle reference or root_id not exist. topItemId='{1}', topRootId='{2}'", itemId, topItemId, topRootId);
            		result = new OperationResult<String>(ErrorCode.DatabaseError, errorId);
            		break;
            	case 3: // max number of iteration reached
            		errorId = logger.error("getContainerFolderPath({0}) : Max number of iteration reached. topItemId='{1}', topRootId='{2}', iterations='{3}'", itemId, topItemId, topRootId, iterations);
            		result = new OperationResult<String>(ErrorCode.DatabaseError, errorId);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("getContainerFolderPath() : errorCode not supported. Value: " + errorCode);
            	}
            } else {
            	result = new OperationResult<String>(ErrorCode.NoResults);
            }
            
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(callableStatement);
			DAOUtil.close(connection);
		}
		
		return result;
	}

	public static BaseOperationResult update(Long itemId, String name, Long parentId, Long rootId,
			Skipable<String> path, Skipable<String> content,
			Skipable<String> description,
			Skipable<String[]> alowedContentTypes, Boolean isRoot,
			Long ownerId, Integer ownerType, Long accountId, String clientIp) throws DAOException {

		Database factory = DAOFactory.getInstance(Databases.Resources);
		Connection connection = null;
    	CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		
		try {
			connection = factory.getConnection();
			callableStatement = connection.prepareCall("{ CALL update(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			
			Object[] values = { 
					itemId,
					GenerateSQLValue(name),
					GenerateSQLValue(parentId),
					GenerateSQLValue(rootId),
            		GenerateSQLValue(path),
            		GenerateSQLValue(content),
            		GenerateSQLValue(description),
            		GenerateSQLValue(null == alowedContentTypes ? alowedContentTypes : StringUtils.join(alowedContentTypes.getValue(), ',')),
            		GenerateSQLValue(isRoot),
            		GenerateSQLValue(ownerId),
            		GenerateSQLValue(ownerType),
            		accountId,
            		clientIp,
            		true
            };
			
			DAOUtil.setValues(callableStatement, values);
            resultSet = callableStatement.executeQuery();
            if(resultSet.next()) {
            	//UUID errorId;
            	int errorCode = (null == resultSet.getObject("error_code")) ? 0 : resultSet.getInt("error_code");
            	
            	switch(errorCode){
            	case 4: // no have permissions
            		result = new BaseOperationResult(ErrorCode.NotPermitted);
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("update() : errorCode not supported. Value: " + errorCode);
            	}
            	
            } else {
            	result = new OperationResult<String>(ErrorCode.NoResults);
            }
            
            return result;
            
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(callableStatement);
			DAOUtil.close(connection);
		}
	}
    
	public static BaseOperationResult appendTemplate(Integer templateId, Long parentId, Long rootId, Long ownerId, Long accountId, String clientIp) throws DAOException {
		
		BaseOperationResult result = null;
		Database factory = DAOFactory.getInstance(Databases.Resources);
		Connection connection = null;
    	CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = factory.getConnection();
			callableStatement = connection.prepareCall("{ CALL appendTemplateToItem(?,?,?,?,?,?) }");
			Object[] values = { 
            		templateId,
            		parentId,
            		rootId,
            		ownerId,
            		accountId,
            		clientIp
            };
            
            DAOUtil.setValues(callableStatement, values);
            resultSet = callableStatement.executeQuery();
            if(resultSet.next()) {
            	int errorCode = (null == resultSet.getObject("error_code")) ? 0 : resultSet.getInt("error_code");
            	switch(errorCode){
            	case 0:
            		result = new BaseOperationResult(resultSet.getLong("transaction_id"));
            		break;
            	default: // Unsupported Error
        			throw new OperationNotSupportedException("appendTemplate() : errorCode not supported. Value: " + errorCode);
            	}
            } else {
            	result = new BaseOperationResult(ErrorCode.NoResults);
            }
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(callableStatement);
			DAOUtil.close(connection);
		}
		
		return result;
	}
	
	public static <T> Dictionary<String, T> getTemplates(IDataFillable<T> data) throws Exception {
    	
		Database factory = DAOFactory.getInstance(Databases.Resources);
		Connection connection = null;
    	CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		Dictionary<String, T> items = new Hashtable<String, T>();
		try {
			connection = factory.getConnection();
			callableStatement = connection.prepareCall("{ CALL getTemplates() }");
			DAOUtil.setValues(callableStatement);
            resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
            	String name = resultSet.getString("template_name");
            	items.put(name, data.fill(resultSet));
            }
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(callableStatement);
			DAOUtil.close(connection);
		}
		
		return items;
    }
	
public static <T> BaseOperationResult getTemplateByName(String name, IDataFillable<T> data) throws Exception {
    	
		Database factory = DAOFactory.getInstance(Databases.Resources);
		Connection connection = null;
    	CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		BaseOperationResult result;
		try {
			connection = factory.getConnection();
			callableStatement = connection.prepareCall("{ CALL getTemplateByName(?) }");
			DAOUtil.setValues(callableStatement, name);
            resultSet = callableStatement.executeQuery();
            if(resultSet.next()) {
            	result = new OperationResult<T>(data.fill(resultSet));
            } else {
            	result = new OperationResult<T>(ErrorCode.NoResults);
            }
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtil.close(resultSet);
			DAOUtil.close(callableStatement);
			DAOUtil.close(connection);
		}
		
		return result;
    }
}


