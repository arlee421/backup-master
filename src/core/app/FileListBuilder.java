package core.app;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import core.misc.TedLogger;


public class FileListBuilder extends FileList {
	
	private String root;
	private Logger log = TedLogger.getInstance();
	
	public FileListBuilder(String root) {
		super();
		this.root = root;
	}
	
	public boolean listup(Predicate<String> filter) {
		
		clear();
		return listup(root, filter);
	}

	private boolean listup(String path, Predicate<String> filter) {

		if(path==null || path.isEmpty()) {
			log.severe("args error occurs.");
			return false;
		}

		File root = new File(path);
		File[] files = root.listFiles();
		
		if(files==null) {
			return true;
		} 

		for(File f: files) {
			if(f.isDirectory()) {
				listup(f.getAbsolutePath(), filter);
			} else if(filter.test(f.getName())) {
				add(f);
			}
		}
		return true;		
	}
}
