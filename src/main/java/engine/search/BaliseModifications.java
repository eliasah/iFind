package engine.search;

import java.util.ArrayList;

/**
 * 
 * @author ahl
 *
 */
public class BaliseModifications {
	private int id;
	private String path;
	private String datemodification;
	private String taille;
	private String proprietaire;
	private String groupe;
	private String permissions;
	private ArrayList<Mot> indexage;
	private String newpath;
	
	public BaliseModifications(int id){
		this.id = id;
		path=null;
		datemodification=null;
		taille=null;
		proprietaire=null;
		groupe=null;
		permissions=null;
		indexage=null;
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

	public String getDatemodification() {
		return datemodification;
	}

	public void setDatemodification(String datemodification) {
		this.datemodification = datemodification;
	}

	public String getTaille() {
		return taille;
	}

	public void setTaille(String taille) {
		this.taille = taille;
	}

	public String getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(String proprietaire) {
		this.proprietaire = proprietaire;
	}

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	public ArrayList<Mot> getIndexage() {
		return indexage;
	}

	public void setIndexage(ArrayList<Mot> indexage) {
		this.indexage = indexage;
	}

	public String getNewpath() {
		return newpath;
	}

	public void setNewpath(String newpath) {
		this.newpath = newpath;
	}
	
}
