package crawler;

public class Event {
	public static final int CREATION = 0;
	public static final int DELETION = 1;
	public static final int MODIFICATION = 2;
	public static final int RENAMING = 3;
	public static final int MODIFICATION_AND_RENAMING = 4;
	
	private int type;
	private String path;
	private String newPath;
	
	public Event(int type, String path, String newPath) {
		this.type = type;
		this.path = path;
		this.newPath = newPath;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public String getNewPath() {
		return this.newPath;
	}
	
	public void setNewPath(String newPath) {
		this.newPath = newPath;
	}
}
