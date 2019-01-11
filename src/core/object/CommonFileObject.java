package core.object;

import java.io.File;
import java.io.IOException;

public class CommonFileObject extends FileObject {

	protected CommonFileObject(File file) throws IOException {
		super(file);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(super.equals(obj)) {
			// Write down extra conditions here.
			return true;
		}
		return false;
	}
	
}
