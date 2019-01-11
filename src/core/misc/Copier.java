package core.misc;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Copier {
	
	public static class Builder {
	
		private boolean preserveDate = false;
		private boolean createDir = false;
		
		public Builder preserveDate(boolean val) {
			preserveDate = val;
			return this;
		}
		public Builder createDir(boolean val) {
			createDir = val;
			return this;
		}
		public Copier build() {
			return new Copier(this);
		}
	}
	
	private final boolean preserveDate;
	private final boolean createDir;
	
	public void copy(File src, File dst) throws IOException {
		
		if(createDir==true) {
			mkdir(getPath(dst));
		}
		copyProc(src, dst);
	}
	
	public void copy(String src, String dst) throws IOException {
		
		if(createDir==true) {
			mkdir(getPath(dst));
		}
		copyProc(new File(src), new File(dst));
	}
	
	public void mkdir(String dir) {
		
		File f = new File(dir);
		mkdir(f);
	}
	
	public void mkdir(File dir) {
		
		if(dir.exists()==false) { 
			dir.mkdirs();
		}
	}
	
	private Copier(Builder builder) {
		this.preserveDate = builder.preserveDate;
		this.createDir = builder.createDir;
	}
	
	private void copyProc(File in, File out) throws IOException {
		
		FileUtils.copyFile(in, out, preserveDate);
	}
	
	private String getPath(File file) {
		
		return file.getParent();
	}
	
	private String getPath(String file) {
		
		return getPath(new File(file));
	}
}
