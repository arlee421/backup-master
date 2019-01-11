package app;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import core.misc.Copier;
import core.misc.TedLogger;
import core.object.FileObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;

/**
 * Layout에서 발생하는 이벤트를 처리해 주는 클래스
 * @author		Ted
 * @since		2019-01-10 
 *
 */
public class EventHandler implements core.app.Message {

	/** MainApp의 PrimaryStage 등의 레이아웃 관련된 
	 * 변수 참고를 위해 MainApp의 인스턴스를 갖는다. */
	private final MainApp app;
	
	/** EventProcessor와 인터페이스를 위한 메시지 큐 */
	private final BlockingQueue<QBox> que;	

	/** 레이아웃의 테이블 정보를 표시하기 위한 클래스 */
	private ObservableList<FileObjectProperty> fileObjectData = 
			FXCollections.observableArrayList();
	
	/** Initialize logger */
	private Logger log = TedLogger.getInstance();

	/** 대상 파일의 확장자를 정의 */
	private final String[] exts = new String[] {
			".xls", ".xlsm", "xlsx", 
			".doc", ".docx",
			".ppt", ".pptx",
			".pdf", ".tiff",
			".jpg", ".png", ".bmp",
			".txt", ".log",
			".zip", ".gz", ".tar"
	}; 
	
	/** 이 클래스는 반드시 {@link #EventHandler(MainApp, BlockingQueue)} 
	 * 를 이용하여 생성되어야 한다. */
	public EventHandler(MainApp app, BlockingQueue<QBox> que) {
		this.app = app;
		this.que = que;
	}
	
	public File openDirectoryChooser(String title) {

		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle(title);
		File curDir = new File(System.getProperty("user.dir"));
		if(curDir!=null) {
			dc.setInitialDirectory(curDir);
		}
		return dc.showDialog(app.getPrimaryStage());
	}

	public ObservableList<FileObjectProperty> getFileObjectData() {
		return fileObjectData;
	}
	
	public void sync(String backupPath) {

		if(backupPath.isEmpty()) {
			log.severe("empty backup directory");
			return;
		}

//		Platform.runLater(()->{
//
//			List<FileObject> list = getUpdateList();
//			if(list==null) {
//				log.warning("no updated item");
//				return;
//			}
//
//			try {
//				Copier cpy = new Copier.Builder().preserveDate(true).createDir(true).build();
//
//				for(FileObject fo: list) {
//					if(fo.isValidity()==true) {
//						String dst = backupPath+File.separatorChar+fo.getName();
//						cpy.copy(fo.getPathAsString(), dst);
//					}
//				}
//			} catch(IOException e) {
//				e.printStackTrace();
//				log.severe("copying failed");
//				return;
//			}
//			log.info("sync done");
//		});
	}

	public boolean scanFiles(String[] extensions, String originRoot, String backupRoot) {

		log.info("start scanning origins");

		if (originRoot.isEmpty() || backupRoot.isEmpty()) {
			log.warning("origin path is empty");
			return false;
		}

		File file = new File(originRoot);
		if (file.isDirectory() == false) {
			log.warning(originRoot + " is not directory");
			return false;
		}

//		Platform.runLater(() -> {
//
//			que.put(new QBox.Builder().msg(SET_EXTENSION).obj(exts).build());
//			que.put(new QBox.Builder().msg(SET_ORIGIN_PATH).obj(originRoot).build());
//			que.put(new QBox.Builder().msg(SET_BACKUP_PATH).obj(backupRoot).build());
//
//			new Thread(() -> {
//
//				log.info("Scanner starts");
//				while (true) {
//					log.info("scanning...");
//					if (coreApp.scanAllFile() == false) {
//						log.severe("failed to scan origins");
//					}
//					try {
//						Thread.sleep(3000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}).start();
//
//			coreApp.analyze(5000, (str) -> {
//				try {
//					que.put(str);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			});
//		});
		return true;
	}
}
