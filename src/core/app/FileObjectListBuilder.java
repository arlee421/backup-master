package core.app;

import java.io.File;

import core.object.CommonFileObject;
import core.object.FileObject;

public class FileObjectListBuilder extends FileObjectList {

	private FileList fileList = null;	// input argument.
	
	public FileObjectListBuilder() {
		super();
	}
	
	public FileObjectListBuilder(FileList fileList) {
		super();
		this.fileList = fileList;		
	}
	
	public void setFileList(FileList fileList) {
		this.fileList = fileList;
	}
	
	public boolean build() {
		
		if(fileList==null) {
			return false;
		}
		
		clear();
		
		for(File file: fileList.getFileList()) {
			FileObject fileObj = (FileObject)FileObject.create(file, CommonFileObject.class);			
			add(fileObj);
		}
		
		return true;
	}
	
}
