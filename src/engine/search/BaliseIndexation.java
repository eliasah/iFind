package engine.search;

public class BaliseIndexation {
	
	private BaliseRenommage renommage; //Fichierrenomme( path, newpath)
	private BaliseModifications modification; //Fichiermodifier (path, datemodification, taille, proprietaire, groupe, permissions, indexage, newpath? )
	private BaliseSuppressions suppression; //Fichiersuprime (path)
	private BaliseCreations creation; //fichiercree (path, format, datecreation, taille, proprietaire, groupe, permission, indexage)
	
	public BaliseIndexation(){
		renommage = null;
		modification = null;
		suppression=null;
		creation = null;
	}

	public BaliseRenommage getRenommage() {
		return renommage;
	}

	public void setRenommage(BaliseRenommage renommage) {
		this.renommage = renommage;
	}

	public BaliseModifications getModification() {
		return modification;
	}

	public void setModification(BaliseModifications modification) {
		this.modification = modification;
	}

	public BaliseSuppressions getSuppression() {
		return suppression;
	}

	public void setSuppression(BaliseSuppressions suppression) {
		this.suppression = suppression;
	}

	public BaliseCreations getCreation() {
		return creation;
	}

	public void setCreation(BaliseCreations creation) {
		this.creation = creation;
	}
	
	
	
}
