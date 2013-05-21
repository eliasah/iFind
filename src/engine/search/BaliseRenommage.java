package engine.search;
/**
 * 
 * @author ahl
 *
 */
public class BaliseRenommage {
	private int id;
	private String path;
	private String newpath;
	
	public BaliseRenommage(int id){
		this.id = id;
		path=null;
		newpath=null;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNewpath() {
		return newpath;
	}

	public void setNewpath(String newpath) {
		this.newpath = newpath;
	}
	
	
}
