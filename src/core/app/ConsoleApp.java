package core.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.misc.Copier;
import core.misc.TedLogger;
import core.object.FileObject;

public class ConsoleApp implements core.app.Message {

	private String[] exts = new String[] {
			".xls", ".xlsm", "xlsx", 
			".doc", ".docx",
			".ppt", ".pptx",
			".pdf", ".tiff",
			".jpg", ".png", ".bmp",
			".txt", ".log",
			".zip", ".gz", ".tar"
	}; 
	
	private String orgPath = "./test";  
	private String bakPath = "./backup";  
	
	private FileObjectListBuilder orgAttr = null;
	private FileObjectListBuilder bakAttr = null;
	
	private Analyzer azer = null;

	boolean[] status = null;
	
	private Logger log = TedLogger.getInstance();
	
	public static void main(String[] args) {
		(new ConsoleApp()).consoleTest();
	}
	
	public ConsoleApp() {
		
		this.status = new boolean[Status.last.ordinal()];
		this.setStatus(Status.initDone);
		orgAttr = new FileObjectListBuilder();
		bakAttr = new FileObjectListBuilder();
		this.azer = new Analyzer(orgAttr, bakAttr);
	}
	
	private void consoleTest() {
		
		log.info("launch()");
		
		if(scanAllFile()==false) {
			System.err.println("scanning error occurs");
			return;
		}
		
		// compare.
		makeDiffList();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(FileObject fo: getUpdateList()) {
			System.out.println(fo.getPathAsString());
		}
		export();
		log.info("job done");
	}
	
	private void export() {
		
		ObjectMapper mapper = new ObjectMapper();
		List<FileObject> list = getUpdateList();
		
		String json;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
			log.info(json);
			
			log.info("============================");
			ObjectMapper omapper = new ObjectMapper();
			
			List<FileObject> readList = omapper.readValue(json, new TypeReference<List<FileObject>>(){});
			for(FileObject fo: readList) {
				log.info(fo.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public boolean scanAllFile() {
		
		Predicate<String> filter = new Predicate<String>() {
			@Override
			public boolean test(String str) {
				for(String ext: exts) {
					if(str.endsWith(ext)) {
						return true;
					}
				}
				return false;
			}
		};
		
		FileListBuilder org = new FileListBuilder(orgPath);
		FileListBuilder bak = new FileListBuilder(bakPath);
		
		if( org.listup(filter)==false || bak.listup(filter)==false) {
			System.err.println("listup() failed");
			return false;
		}
		
		orgAttr.setFileList(org);
		bakAttr.setFileList(bak);
		
		if(orgAttr.build()==false || bakAttr.build()==false) {
			System.err.println("getAttribute() failed");
			return false;
		}
		
		if(orgAttr==null || bakAttr==null) {
			System.err.println("error on analyzing diff");
			return false;
		}
		
		setStatus(Status.scanDone);
		return true;
	}
	
	public boolean scanOrigins() {
		
		Predicate<String> filter = new Predicate<String>() {
			@Override
			public boolean test(String str) {
				for(String ext: exts) {
					if(str.endsWith(ext)) {
						return true;
					}
				}
				return false;
			}	
		};
		
		FileListBuilder fb = new FileListBuilder(orgPath);
		fb.listup(filter);
		
		orgAttr = new FileObjectListBuilder(fb);
		if(orgAttr.build()==false) {
			System.err.println("getAttribute() failed");
			return false;
		}
		return true;
	}

	public List<FileObject> getUpdateList() {

		if (azer == null) {
			return null;
		}

		List<FileObject> diff = azer.getDiffList();
		List<FileObject> revised = azer.getRevisedList();

		if (diff == null && revised == null) {
			return null;
		}

		List<FileObject> updates = new ArrayList<>();
		if (diff != null) {
			updates.addAll(diff);
		}
		if (revised != null) {
			updates.addAll(revised);
		}

		return updates;
	}

	public boolean makeDiffList() {

		new Thread(() -> {
			while (true) {
				log.info("starts analyzing");
				azer.analyze();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		return true;
	}
	
	public void analyze(long tick, Consumer<String> notifier) {

		new Thread(()-> {
			while(true) {
				try {
					log.info("starts analyzing");
					if(azer.analyze()==true) {
						log.info("list updated");
						notifier.accept(LIST_UPDATED);
					}
					Thread.sleep(tick);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public boolean backup() {

		List<FileObject> diff = azer.getDiffList();
		List<FileObject> revised = azer.getRevisedList();

		if (diff == null || revised == null) {
			log.severe("null diff");
			return false;
		}

		Copier cpy = new Copier.Builder().preserveDate(true).createDir(true).build();

		for (FileObject fo : diff) {
			try {
				String dst = bakPath + "/" + fo.getName();
				log.info(String.format("copying %s to %s", fo.getName(), dst));
				cpy.copy(fo.getPathAsString(), dst);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (FileObject fo : revised) {
			log.info(fo.toString());
		}
		return true;
	}
	
	public void setExtensions(String[] exts) {
		this.exts = exts;
	}

	public String[] getExtensions() {
		return this.exts;
	}

	private void setStatus(Status status) {
		this.status[status.ordinal()] = true;
	}

	private void clearStatus(Status status) {
		this.status[status.ordinal()] = false;
	}
	
	public void setOriginPath(String path) {
		this.orgPath = path;
	}
	
	public String getOriginPath() {
		return this.orgPath;
	}
	
	public void setBackupPath(String path) {
		this.bakPath = path;
	}
	
	public String getBackupPath() {
		return this.bakPath;
	}
}

enum Status {
	error,
	initDone,
	scanDone,
	diffDone,
	
	last
}
