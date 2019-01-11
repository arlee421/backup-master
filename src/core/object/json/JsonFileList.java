package core.object.json;

import java.util.ArrayList;
import java.util.List;

public class JsonFileList {
	
	private AuthorInfo	author;
	private List<JsonFileObject> files;
	
	public JsonFileList() {
		this.author = new AuthorInfo();
		this.files = new ArrayList<>();
	}
	
	public JsonFileList(AuthorInfo author) {
		this.author = author;
		this.files = new ArrayList<>();
	}
	
	public JsonFileList(AuthorInfo author, List<JsonFileObject> files) {
		this.author = author;
		this.files = files;
	}
	
	public AuthorInfo getAuthor() {
		return author;
	}
	
	public void setAuthor(AuthorInfo author) {
		this.author = author;
	}
	
	public List<JsonFileObject> getFiles() {
		return files;
	}
	
	public void setFiles(List<JsonFileObject> files) {
		this.files = files;
	}
}
