package engine.search;

public class ResultFile {
	private String name;
	private String path;
	private String perm;
	private String size;
	private String lastmodif;
	private String proprio;
	
	public ResultFile(){
		name = null;
		path = null;
		perm = null;
		size = null;
		lastmodif = null;
		proprio = null;
	}
	
	public ResultFile(String n, String p, String pe, String si, String lm, String prop){
		name = n;
		path=p;
		perm=pe;
		size = si;
		lastmodif = lm;
		proprio = prop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPerm() {
		return perm;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getLastmodif() {
		return lastmodif;
	}

	public void setLastmodif(String lastmodif) {
		this.lastmodif = lastmodif;
	}

	public String getProprio() {
		return proprio;
	}

	public void setProprio(String proprio) {
		this.proprio = proprio;
	}
	
	
}
