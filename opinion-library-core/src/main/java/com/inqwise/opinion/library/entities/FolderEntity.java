package com.inqwise.opinion.library.entities;

import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.basicTypes.Skipable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.resources.FileSystemItemType;
import com.inqwise.opinion.library.dao.Resources;

public class FolderEntity extends FileSystemItem {
	
	private static ApplicationLog logger = ApplicationLog.getLogger(FolderEntity.class);
	private static final String SEPARATOR = "|";
	
	public FileSystemItemType getItemType() {
		return FileSystemItemType.Folder;
	}

	public String[] allowedContentTypes = null; 
	
	
	
	public FolderEntity(ResultSet reader) throws SQLException{
		super(reader);
		if(null != reader.getObject("item_alowed_content_types")){
			allowedContentTypes = StringUtils.split(reader.getString("item_alowed_content_types"), SEPARATOR);
		}
	}
	
	public FolderEntity(Long id, String name, Long parentId, Long rootId) {
		super(id, name, parentId, rootId, null);
	}

	public BaseOperationResult getItems(Long accountId){
		
		BaseOperationResult result;
		try{
			result = new OperationResult<List<FileSystemItem>>(getSubItems(getId(), accountId));
		} catch(Throwable ex){
			UUID errorId = logger.error(ex, "getItems() for parentId '{0}' failed.", getId());
			result = new OperationResult<List<FileSystemItem>>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public BaseOperationResult addFolder(String folderName, Long accountId, String clientIp){
		BaseOperationResult result;
		try{
			
			OperationResult<Long> addFolderResult = addFolder(folderName, getId(), getRootId(), accountId, clientIp);
			if(addFolderResult.hasError()){
				result = addFolderResult.toErrorResult();
			} else {
				FolderEntity folder = new FolderEntity(addFolderResult.getValue(), folderName, getId(), getRootId());
				result = new OperationResult<FolderEntity>(folder, addFolderResult.getTransactionId());
				
			}
		} catch(Throwable ex){
			UUID errorId = logger.error(ex, "AddFolder({0}, {1}, {2}) : Unexpected Error occured.", folderName, accountId, clientIp);
			result = new OperationResult<FolderEntity>(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public BaseOperationResult addFile(String fileName, String extension,
			String contentType, Long size, InputStream reader, Long accountId, String clientIp){
		
		BaseOperationResult result = new OperationResult<FileEntity>(ErrorCode.NoError);
		Boolean isDeleteContainerFile = false;
		File containerFile = null;
		OperationResult<String> folderPathResult;
		String folderPath = null;
		
		try{
			
			// get container path
			folderPathResult = Resources.getContainerFolderPath(getId());
			
			if(folderPathResult.hasError()){
				result = folderPathResult.toErrorResult();
			} else {
				folderPath = folderPathResult.getValue();
			}
			
			if(!result.hasError()){
				// save file to container
				containerFile = FileEntity.saveFileToContainer(folderPath, extension, reader);
				
				OperationResult<Long> addFileResult = addFile(fileName, containerFile.getName(), extension, contentType, size, getId(),
																getRootId(), accountId, clientIp);
				if(addFileResult.hasError()){
					isDeleteContainerFile = true;
					result = addFileResult.toErrorResult();
				} else {
					FileEntity file = new FileEntity(addFileResult.getValue(), fileName, containerFile.getName(), extension, contentType,
														size, getId(), getRootId());
					result = new OperationResult<FileEntity>(file, addFileResult.getTransactionId());
					
				}
			}
		} catch(Throwable ex){
			isDeleteContainerFile = true;
			UUID errorId = logger.error(ex, "AddFile({0}, {1}, {2}) : Unexpected Error occured.", fileName, accountId, clientIp);
			result = new OperationResult<FileEntity>(ErrorCode.GeneralError, errorId);
		}
		
		//delete file from container when error occurred
		if(isDeleteContainerFile && null != containerFile){
			try{
				containerFile.delete();
			}catch(Throwable t){}
		}
		
		return result;
	}
	
	public BaseOperationResult update(String name, Long parentId, Long rootId, Skipable<String> path,
										Skipable<String> content, Skipable<String> description,
										Skipable<String[]> allowedContentTypes, Boolean isRoot, Long ownerId,
										Integer ownerType, Long accountId, String clientIp){
		
		BaseOperationResult result = super.update(name, parentId, rootId, path, content, description, allowedContentTypes,
													isRoot, ownerId, ownerType, accountId, clientIp);
		
		if(!result.hasError()){
			if(null != allowedContentTypes){
				this.allowedContentTypes = allowedContentTypes.getValue();
			}
		}
		
		return result;
	}
}
