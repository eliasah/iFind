package engine.search;

public class ResultFile {
	String name;
	String path;
	String perm; //String ??
	String size; // INT ??
	String lastmodif;
	String proprio;
	
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
	
}
