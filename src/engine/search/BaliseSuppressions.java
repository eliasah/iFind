package engine.search;
/**
 * 
 * @author ahl
 *
 */
public class BaliseSuppressions {
	private int id;
	private String path;
	
	public BaliseSuppressions(int id){
		this.id = id;
		path = null;
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
	
}
