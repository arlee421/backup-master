package core.app;

public interface Message {
	
	/* DON'T add abstraction methods in this interface. */  
	public static final String LIST_UPDATED =		"LIST_UPDATED";
	public static final String START_SCANNING =		"START_SCANNING";
	public static final String SET_EXTENSION =		"SET_EXTENSION";
	public static final String SET_ORIGIN_PATH =	"SET_ORIGIN_PATH";
	public static final String SET_BACKUP_PATH =	"SET_BACKUP_PATH";
}
