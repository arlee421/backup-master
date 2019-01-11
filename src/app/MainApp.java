package app;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import core.app.ConsoleApp;
import core.misc.TedLogger;
import core.object.FileObject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.LayoutController;

public class MainApp extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;
	
	private LayoutController layoutControl = null;

	private Logger log = TedLogger.getInstance(); 
	private BlockingQueue<QBox> que = null;
	private ConsoleApp coreApp = null;
	private EventHandler handler = null;
	private boolean scanning = false;
	
	@Override
	public void start(Stage primaryStage) {
		
		log.info("Application starts");
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("TEDbackEx");
		
		/// Instantiate console app.
		coreApp = new ConsoleApp();
		
		/// Instantiate queue
		que = new LinkedBlockingQueue<>();
		
		/// Instantiate event handler
		handler = new EventHandler(this, que);		
		
		//initOriginFileList();
		initRootLayout();
		
		/// Bring up the internal event handler thread.
		new Thread(new EventProcessor(coreApp, que, layoutControl)).start();;
	}
	
	private void initRootLayout() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/MainLayout.fxml"));
			rootLayout = (AnchorPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			layoutControl = loader.getController();
			layoutControl.setHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public List<FileObject> getUpdateList() {
		return coreApp.getUpdateList();
	}
	
	public boolean isScanning() {
		return false;
	}
	
	public void setScanning(boolean val) {
		scanning = val;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	
}
