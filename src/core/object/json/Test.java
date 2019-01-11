package core.object.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
	
	
	public void test1() {
		
		System.out.println("test1");
		
		List<JsonFileObject> foList = new ArrayList<>();
		foList.add(new JsonFileObject("file1", "y"));
		foList.add(new JsonFileObject("file2", "y"));
		foList.add(new JsonFileObject("file3", "n"));
		foList.add(new JsonFileObject("file4", "n"));
		
		JsonFileList list = new JsonFileList(new AuthorInfo("tykim", "taeyoung", "10"), foList);
		
		try {

			ObjectMapper mapper = new ObjectMapper();

			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
			System.out.println(json);
			
			JsonFileList read = mapper.readValue(json, JsonFileList.class);
			System.out.println("done");
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		(new Test()).test1();
	}
}
