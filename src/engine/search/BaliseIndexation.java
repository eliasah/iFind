package engine.search;

import java.util.ArrayList;

public class BaliseIndexation {
	
	private ArrayList<BaliseRenommage> renommage; //Fichierrenomme( path, newpath)
	private ArrayList<BaliseModifications> modification; //Fichiermodifier (path, datemodification, taille, proprietaire, groupe, permissions, indexage, newpath? )
	private ArrayList<BaliseSuppressions> suppression; //Fichiersuprime (path)
	private ArrayList<BaliseCreations> creation; //fichiercree (path, format, datecreation, taille, proprietaire, groupe, permission, indexage)
	
	public BaliseIndexation(){
		renommage = null;
		modification = null;
		suppression = null;
		creation = null;
	}

	public ArrayList<BaliseRenommage> getRenommage() {
		return renommage;
	}

	public void setRenommage(ArrayList<BaliseRenommage> renommage) {
		this.renommage = renommage;
	}

	public ArrayList<BaliseModifications> getModification() {
		return modification;
	}

	public void setModification(ArrayList<BaliseModifications> modification) {
		this.modification = modification;
	}

	public ArrayList<BaliseSuppressions> getSuppression() {
		return suppression;
	}

	public void setSuppression(ArrayList<BaliseSuppressions> suppression) {
		this.suppression = suppression;
	}

	public ArrayList<BaliseCreations> getCreation() {
		return creation;
	}

	public void setCreation(ArrayList<BaliseCreations> creation) {
		this.creation = creation;
	}
	
}
