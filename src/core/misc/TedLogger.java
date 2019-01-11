package core.misc;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class TedLogger {
	
	private static final String LOGGER_NAME = "Backup";
	
	private static final String[] OUTPUT_FILES = new String[] {"log.txt"};
	
	private static final String logHdrFmt = "%-3d %-20s : ";
	
	private static final String lineSeparator;
	static {
		lineSeparator = System.getProperty("line.separator");
	}
	
	private static final Logger LOG;
	static {
		LOG = Logger.getLogger(LOGGER_NAME);
		Formatter fmter = new Formatter() {

			@Override
			public String format(LogRecord rec) {
				StringBuilder str = new StringBuilder();
				StringBuilder hdr = new StringBuilder();
				
				hdr.append(rec.getSourceClassName().substring(rec.getSourceClassName().lastIndexOf(".")+1));
				hdr.append(":");
				hdr.append(rec.getSourceMethodName());

				Level lv = rec.getLevel();
				if(lv==Level.WARNING) {
					str.append("WARNING  ");
				} else if(lv==Level.SEVERE) {
					str.append("SEVERE  ");
				} else if(lv==Level.CONFIG) {
					str.append("CONFIG  ");
				}
				
				str.append(String.format(logHdrFmt, 
						rec.getThreadID(), hdr.toString()));
				str.append(rec.getMessage());
				str.append("taeyoung");
				str.append(lineSeparator);
				return str.toString();
			}
		};

		LOG.setUseParentHandlers(false);
		LOG.setLevel(Level.INFO);

		ConsoleHandler consHandler = new ConsoleHandler();
		LOG.addHandler(consHandler);
		consHandler.setFormatter(fmter);

		try {
			for (String output : OUTPUT_FILES) {
				FileHandler fileHandle = new FileHandler(output);
				fileHandle.setFormatter(fmter);
				LOG.addHandler(fileHandle);
			}
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
	

	private TedLogger() {}

	
	public static Logger getInstance() {
		return LOG;
	}
}
