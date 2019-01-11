package app;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import core.app.ConsoleApp;
import core.app.FileObjectList;
import core.misc.TedLogger;
import core.object.FileObject;
import view.LayoutController;

public class EventProcessor implements Runnable, core.app.Message {
	
	private final ConsoleApp core;
	private final BlockingQueue<QBox> que;
	private final LayoutController ctr;
	
	private Logger log = TedLogger.getInstance();	

	public EventProcessor(ConsoleApp core, BlockingQueue<QBox> que, LayoutController ctr) {
		
		this.core = core;
		this.que = que;
		this.ctr = ctr;
	}
	
	@Override
	public void run() {

		log.info("Internal Event Handler starts");

		try {
			while (true) {
				QBox q = que.take();
				
				if(q.msg.equals(LIST_UPDATED)) {
					
					log.info(LIST_UPDATED);
					if(q.obj instanceof FileObjectList) {
						FileObjectList list = (FileObjectList)q.obj;
						for(FileObject o: list) {
							log.info(o.toString());
						}
					} else {
						log.severe("invalid queue argument");
					}
					
				} else if(q.msg.equals(SET_EXTENSION)) {
					
					if(q.obj instanceof String[]) {
						String[] extension = (String[])q.obj;
						core.setExtensions(extension);
					} else {
						log.severe("invalid queue argument");
					}
					
				} else if(q.msg.equals(SET_ORIGIN_PATH)) {
					
					if(q.obj instanceof String) {
						String originPath = (String)q.obj;
						core.setOriginPath(originPath);
					} else {
						log.severe("invalid queue argument");
					}
					
				} else if(q.msg.equals(SET_BACKUP_PATH)) {
					
					if(q.obj instanceof String) {
						String backupPath = (String)q.obj;
						core.setOriginPath(backupPath);
					} else {
						log.severe("invalid queue argument");
					}
					
				} else if(true) {
					// extends some event here..
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
