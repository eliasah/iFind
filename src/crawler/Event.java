package crawler;

public class Event {
	/* the type must be : 
	 * 'C' for "creation"
	 * 'D' for "deletion"
	 * 'M' for "modification"
	 * 'R' for "renaming"
	 */
	char type;
	String oldPath;
	String newPath;
	
	public Event(char type, String oldPath, String newPath) {
		this.type = type;
		this.oldPath = oldPath;
		this.newPath = newPath;
	}
	
	public char getType() {
		return type;
	}

	public String getOldPath() {
		return oldPath;
	}

	public String getNewPath() {
		return newPath;
	}

	public boolean equals(Event event) {
		return type == event.type 
				&& oldPath == null ? event.oldPath == null : oldPath.equals(event.oldPath)
				&& newPath.equals(event.newPath);
	}
}
