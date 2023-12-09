package com.inqwise.opinion.library.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.NotImplementedException;

import com.inqwise.opinion.infrastructure.dao.DAOException;
import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.basicTypes.Skipable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.resources.FileSystemItemType;
import com.inqwise.opinion.library.dao.Resources;


public abstract class FileSystemItem {
	
	static ApplicationLog logger = ApplicationLog.getLogger(FileSystemItem.class);
	
	public abstract FileSystemItemType getItemType();
	
	void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private String name;
	
	private Long parentId;
	private Long rootId;
	private Long id;
	private String content;
	
	protected FileSystemItem(ResultSet reader) throws SQLException{
		setName(reader.getString("item_name"));
		setParentId(reader.getLong("item_parent_id"));
		setRootId(reader.getLong("item_root_id"));
		setId(reader.getLong("item_id"));
		setContent(reader.getString("item_content"));
	}

	FileSystemItem(Long id, String name, Long parentId, Long rootId, String content) {
		setId(id);
		setName(name);
		setParentId(parentId);
		setRootId(rootId);
		setContent(content);
	}

	void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	void setRootId(Long rootId) {
		this.rootId = rootId;
	}

	public Long getRootId() {
		return rootId;
	}

	void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	static List<FileSystemItem> getSubItems(Long itemId, Long accountId) throws DAOException{
		
		IDataFillable<FileSystemItem> data = new IDataFillable<FileSystemItem>()
		{
			public FileSystemItem fill(ResultSet reader) throws SQLException
			{
				FileSystemItemType itemType =  
				FileSystemItemType.valueOf(reader.getString("item_type"));
				switch(itemType){
					case File:
						return new FileEntity(reader);
					case Folder:
						return new FolderEntity(reader);
					default:
						throw new NotImplementedException(itemType.toString());
				}
			}
		};
	
		return Resources.getItemsByParentId(itemId, accountId, data);
	}
	
	static BaseOperationResult getItem(Long itemId, FileSystemItemType itemType, Long accountId) throws DAOException{
		
		IDataFillable<FileSystemItem> data = new IDataFillable<FileSystemItem>()
		{
			public FileSystemItem fill(ResultSet reader) throws SQLException
			{
				FileSystemItemType itemType =  
				FileSystemItemType.valueOf(reader.getString("item_type"));
				switch(itemType){
					case File:
						return new FileEntity(reader);
					case Folder:
						return new FolderEntity(reader);
					default:
						throw new NotImplementedException(itemType.toString());
				}
			}
		};
	
		return Resources.getItem(itemId, itemType.getValue(), accountId, data);
	}
	
	protected static OperationResult<Long> addFolder(String folderName, Long parentId,
								Long rootId, Long accountId, String clientIp) throws DAOException {
		
		return Resources.addFolder(folderName, parentId, rootId, accountId, clientIp);
	}
	
	protected static OperationResult<Long> addFile(String fileName, String path, String extension, String contentType, Long size, Long parentId,
			Long rootId, Long accountId, String clientIp) throws DAOException {

		return Resources.addFile(fileName, path, extension, contentType, size, parentId, rootId, accountId, clientIp);
	}
	
	private static BaseOperationResult deleteItem(Long itemId, Long accountId, String clientIp) throws DAOException {

		return Resources.deleteItem(itemId, accountId, clientIp, false);
	}
	
	public BaseOperationResult delete(Long accountId, String clientIp){
		BaseOperationResult result;
		try{
			result = deleteItem(getId(),  accountId, clientIp);
		} catch(Throwable ex){
			UUID errorId = logger.error(ex, "delete({0}, {1}) : Unexpected Error occured.", accountId, clientIp);
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	BaseOperationResult update(String name, Long parentId, Long rootId, Skipable<String> path,
								Skipable<String> content, Skipable<String> description,
								Skipable<String[]> alowedContentTypes, Boolean isRoot, Long ownerId,
								Integer ownerType, Long accountId, String clientIp){
		
		BaseOperationResult result;
		
		try{
			result = Resources.update(getId(), name, parentId, rootId, path, content, description,
									alowedContentTypes, isRoot, ownerId, ownerType, accountId, clientIp);
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "update({0}, {1}, {2}, ...) : Unexpected Error occured.",
										getId(), name, parentId);
			result = new BaseOperationResult(ErrorCode.GeneralError, errorId);
		}
		
		if(!result.hasError()){
			if(null != parentId){
				this.parentId = parentId;
			}
			
			if(null != rootId){
				this.rootId = rootId;
			}
			
			if(null != content){
				this.content = content.getValue();
			}
		}
		return result;
	} 
}
