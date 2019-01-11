package core.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.object.FileObject;;

public class FileObjectList implements Iterable<FileObject> {

	private List<FileObject> attrList = null;

	public FileObjectList() {
		attrList = new ArrayList<>();
	}

	public FileObjectList getList() {
		return this;
	}

	public List<FileObject> getAttrList() {
		return attrList;
	}

	public void add(FileObject obj) {
		attrList.add(obj);
	}

	public void clear() {
		attrList.clear();
	}

	@Override
	public Iterator<FileObject> iterator() {
		return new FileObjectIterator();
	}

	private class FileObjectIterator implements Iterator<FileObject> {

		private int position = 0;

		@Override
		public boolean hasNext() {
			if (position < attrList.size()) {
				return true;
			}
			return false;
		}

		@Override
		public FileObject next() {
			if (this.hasNext()) {
				return attrList.get(position++);
			}
			return null;
		}

	}
}
