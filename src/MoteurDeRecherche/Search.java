package MoteurDeRecherche;

public class Search {
	boolean word;
	String content;
	String pathdir;
	String perm;
	String extension;
	TimeSlot timeSlot;	

	public Search(boolean w){
		word = w;
		content = null;
		pathdir = null;
		perm= null;
		extension = null;
		timeSlot= null;
	}
	
	public Search(boolean w, String cont, String path, String permission, String ext, TimeSlot t){
		//Seul w ne doit pas être null
		word = w;
		content = cont;
		pathdir = path;
		perm = permission;
		extension = ext;
		timeSlot = t;
	}
}


