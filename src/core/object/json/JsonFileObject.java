package core.object.json;

public class JsonFileObject {

	private String name;
	private String validity;
	
	public JsonFileObject() {
		this.name = "";
		this.validity = "no";
	}
	
	public JsonFileObject(String name, String validity) {
		this.name = name;
		this.validity = validity;
	}

	public String getName() {
		return name;
	}

	public String getValidity() {
		return validity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}
}
