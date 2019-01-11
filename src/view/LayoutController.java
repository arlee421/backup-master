package view;

import java.io.File;
import java.util.logging.Logger;

import app.EventHandler;
import app.FileObjectProperty;
import app.MainApp;
import core.misc.TedLogger;
import core.object.FileObject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class LayoutController {
	
	private final static String NULL = "null";
	private EventHandler handler;
	
	private Logger log = TedLogger.getInstance();
	
	@FXML
	private TableView<FileObjectProperty> fileTable;
	
	@FXML
	private TableColumn<FileObjectProperty, String> fileName;
	
	@FXML
	private TableColumn<FileObjectProperty, String> modifiedDate;
	
	@FXML
	private TableColumn<FileObjectProperty, Boolean> status;
	
	@FXML
	private TableColumn<FileObjectProperty, Boolean> validity;
	
	@FXML
	private TextField textOrigin;
	
	@FXML
	private TextField textBackup;
	
	@FXML
	private void initialize() {

		fileName.setCellValueFactory(cellDate -> cellDate.getValue().getName());
		modifiedDate.setCellValueFactory(cellData -> cellData.getValue().getLastModifiedTime());
		validity.setCellValueFactory(cellData->cellData.getValue().isValidity());
		validity.setCellFactory(tc->{ 
			CheckBoxTableCell<FileObjectProperty, Boolean> checkBox = new CheckBoxTableCell<>();
			checkBox.setEditable(true);
			return checkBox;
			});
		textOrigin.setText(NULL);
		textBackup.setText(NULL);
	}
	
	@FXML
	private void handleScanFile() {
		
		log.info("handle(scan)");
		
		String origin = textOrigin.getText();
		String backup = textBackup.getText();
		
		if(origin.equals(NULL) || backup.equals(NULL)) {
			log.warning("null directory");
			return;
		}
		handler.scanFiles(null, origin, backup);
	}
	
	@FXML
	private void handleSelectOrigin() {

		log.info("origin");
		File dir = handler.openDirectoryChooser("Select Origin Directory");
		if(dir==null) {
			log.warning("null dir");
		} else {
			log.info(dir.getAbsolutePath());
			textOrigin.setText(dir.getAbsolutePath());
		}
	}
	
	@FXML
	private void handleSelectBackup() {

		log.info("backup");
		File dir = handler.openDirectoryChooser("Select Backup Directory");
		if(dir==null) {
			log.warning("null dir");
		} else {
			log.info(dir.getAbsolutePath());
			textBackup.setText(dir.getAbsolutePath());
		}
	}
	
	@FXML
	private void handleSync() {
	
		handler.sync(textBackup.getText());
	}

	public void updateList() {
		
		log.info("updateList()");
		fileTable.setItems(handler.getFileObjectData());	
	}
	
	public void setHandler(EventHandler handler) {
		this.handler = handler;
	}
}
