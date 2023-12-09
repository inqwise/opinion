package com.inqwise.opinion.library.entities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.basicTypes.Skipable;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.resources.FileSystemItemType;

public class FileEntity extends FileSystemItem {

	static ApplicationLog logger = ApplicationLog.getLogger(FileEntity.class);
	
	public FileSystemItemType getItemType() {
		return FileSystemItemType.File;
	}

	private String path;
	private String contentType;
	private String extension;
	private Long size;
	
	FileEntity(ResultSet reader) throws SQLException{
		super(reader);
		path = reader.getString("item_path");
		contentType = reader.getString("item_content_type");
		extension = reader.getString("item_extension");
		size = reader.getLong("item_size");
	}

	FileEntity(Long id, String name, String path, String extension,
			String contentType, Long size, Long parentId,
			Long rootId) {

			super(rootId, name, parentId, rootId, null);
			
			this.path = path;
			this.contentType = contentType;
			this.extension = extension;
			this.size = size;
	}

	static File saveFileToContainer(String folderPath, String extension, InputStream reader) throws IOException {
		
		File folder = new File(folderPath);
		
		//create folder if not exist
		if(!folder.exists()){
			folder.mkdirs();
		}
		
		java.io.File file = File.createTempFile("", "." + extension, folder);
		
		OutputStream out=new FileOutputStream(file);
	    byte buf[]=new byte[1024];
	    int len;
	    while((len=reader.read(buf))>0){
	    	out.write(buf,0,len);
	    }
	    out.close();
	    
		return file;
	}
	
	public BaseOperationResult update(String name, Long parentId, Long rootId, Skipable<String> path,
										Skipable<String> content, Skipable<String> description, Long ownerId,
										Integer ownerType, Long accountId, String clientIp){
		
		BaseOperationResult result = super.update(name, parentId, rootId, path, content, description,
													null, false, ownerId, ownerType, accountId, clientIp);
		
		if(!result.hasError()){
			if(null != path){
				this.path = path.getValue();
			}
		}
		
		return result;
	} 
}
