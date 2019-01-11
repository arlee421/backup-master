package core.object;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;

public class FileObject {
	
	private String name;
	private String pathAsString;
	
	private long size;
	private FileTime creationTime;
	private FileTime lastAccessTime;
	private FileTime lastModifiedTime;
	private Object fileKey;
	
	private HashMap<String, String> keywords;
	private String strPath;
	private String format;
	
	private File filePath;
	private Path path;
	
	private boolean validity;
	
	protected FileObject(File file) throws IOException {
		
		if( file==null || file.isDirectory()  ) {
			throw new IOException(String.format("invalid file %s", file.getName()));
		}
		
		this.filePath = file;
		
		this.name = file.getName();
		this.pathAsString = file.getPath();
		
		this.path = Paths.get(file.toURI());
		BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
		this.creationTime = attr.creationTime();
		this.lastAccessTime = attr.lastAccessTime();
		this.lastModifiedTime = attr.lastModifiedTime();
		this.size = attr.size();
		this.fileKey = attr.fileKey();
		this.validity = true;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof FileObject) {
			FileObject fobj = (FileObject)obj;
			if( this.name.equals(fobj.name) && 
				this.size==fobj.size &&
				//this.creationTime.equals(fobj.creationTime) &&
				this.lastModifiedTime.equals(fobj.lastModifiedTime) ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isRevised(FileObject obj) {
		
		if(obj instanceof FileObject) {
			if(this.name.equals(obj.name)) {
				if(this.size!=obj.size || this.lastModifiedTime.equals(obj)==false) {
					return true;
				}
			}
		}
		return false;
	}
	
	static public <ExtClass> ExtClass create(File file, Class<ExtClass> type) {
		
		ExtClass fo;
		
		try {
			fo = type.getDeclaredConstructor(File.class).newInstance(file);
				
			if((fo instanceof FileObject)==false) {
				System.err.println("argument type error");
				return (ExtClass)null;
			}
			return fo;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return (ExtClass)null;
	}
	
	
	@Override
	public String toString() {
		return "FileObject [name=" + name + ", pathAsString=" + pathAsString + ", size=" + size + ", creationTime="
				+ creationTime + ", lastAccessTime=" + lastAccessTime + ", lastModifiedTime=" + lastModifiedTime
				+ ", fileKey=" + fileKey + ", keywords=" + keywords + ", strPath=" + strPath + ", format=" + format
				+ ", filePath=" + filePath + ", path=" + path + "]";
	}


	////////////////////////////////////////////////////////////////////////////
	// Getter/ Setter
	////////////////////////////////////////////////////////////////////////////
	
	public boolean setFilePath(File filePath) {
		
		if(filePath==null || filePath.isDirectory()) {
			return false;
		}
		this.filePath = filePath;
		return true;
	}
	
	public String getPathAsString() {
		return pathAsString;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLastModifiedTime() {
		return lastModifiedTime.toString();
	}
	
	public boolean isValidity() {
		return validity;
	}
}