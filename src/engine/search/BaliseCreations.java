package engine.search;

import java.util.Hashtable;

/**
 * 
 * @author ahl
 *
 */
public class BaliseCreations {
	private int id;
	private String path;
	private String format;
	private String datecreation;
	private String taille;
	private String proprietaire;
	private String groupe;
	private String permission;
	private Hashtable<String, Integer> indexage;
	
	public BaliseCreations(int id){
		this.id = id;
		path=null;
		format=null;
		datecreation=null;
		taille=null;
		proprietaire=null;
		groupe=null;
		permission=null;
		indexage=new Hashtable<>();
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

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getDatecreation() {
		return datecreation;
	}

	public void setDatecreation(String datecreation) {
		this.datecreation = datecreation;
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

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Hashtable<String, Integer> getIndexage() {
		return indexage;
	}

	public void setIndexage(Hashtable<String, Integer> indexage) {
		this.indexage = indexage;
	}
}
