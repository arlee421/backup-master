package core.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileList implements Iterable<File> {

	private List<File> fileList;
	
	public FileList() {
		this.fileList = new ArrayList<>(); 
	}
	
	public List<File> getFileList() {
		return fileList;
	}
	
	public void add(File file) {
		fileList.add(file);
	}
	
	public void clear() {
		fileList.clear();
	}

	@Override
	public Iterator<File> iterator() {
		return new FileListIterator();
	}
	
	private class FileListIterator implements Iterator<File> {

		private int position = 0;
		
		@Override
		public boolean hasNext() {
			if(position<fileList.size()) {
				return true;
			}
			return false;
		}

		@Override
		public File next() {
			if(this.hasNext()) {
				return fileList.get(position++);
			}
			return null;
		}
	}
}
