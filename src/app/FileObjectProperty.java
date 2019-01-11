package app;

import java.io.IOException;

import core.object.FileObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileObjectProperty {

	private final FileObject fileObject;
	
	public FileObjectProperty(FileObject fileObject) throws IOException {
		
		if(fileObject==null) {
			throw new IOException("null object");
		}
		this.fileObject = fileObject;
	}
	
	public StringProperty getName() {
		return new SimpleStringProperty(fileObject.getName());
	}
		
	public StringProperty getLastModifiedTime() {
		return new SimpleStringProperty(fileObject.getLastModifiedTime());
	}
	
	public BooleanProperty isValidity() {
		return new SimpleBooleanProperty(fileObject.isValidity());
	}
}
