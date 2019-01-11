package core.app;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import core.misc.TedLogger;
import core.object.FileObject;

public class Analyzer {
	
	private final FileObjectList origin;	
	private final FileObjectList backup;

	private List<FileObject> diffList;
	private List<FileObject> revisedList;
	
	private Logger log = TedLogger.getInstance();
	
	public Analyzer(FileObjectList origin, FileObjectList backup) {
		
		this.origin = origin;
		this.backup = backup;
		this.diffList = new ArrayList<>();
		this.revisedList = new ArrayList<>();
	}
	
	public boolean analyze() {
		
		if(origin==null || backup==null) {
			log.severe("null target");
			return false;
		}
		
		boolean updated = false;
		diffList.clear();
		revisedList.clear();
		
		synchronized(this) {
			for(FileObject obj: origin) {

				boolean flg = true;
				for(FileObject comp: backup) {
					if(obj.equals(comp)) {
						flg = false;
						break;
					}
					if(obj.isRevised(comp)) {
						flg = false;
						revisedList.add(obj);
						updated = true;
						break;
					}
				}
				if(flg) {
					diffList.add(obj);
					updated = true;
				}
			}
			notify();
		}
		return updated;
	}
	
	public List<FileObject> getDiffList() {
		return diffList;
	}
	
	public List<FileObject> getRevisedList() {
		return revisedList;
	}
}
