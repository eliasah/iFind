package crawler;

/**
 * Cette classe permet d'associer un chemin de fichier à un évènement :
 * - création
 * - suppression
 * - modification
 * - renommage
 * - modification et renommage
 * Dans le cas d'un renommage (associé à une modification ou non), l'ancien
 * et le nouveau chemin sont conservés. 
 * Dans les autres cas, le nouveau chemin est nul.
 * 
 * @author isabelle
 *
 */
public class Event {
	public static final int CREATION = 0;
	public static final int DELETION = 1;
	public static final int MODIFICATION = 2;
	public static final int RENAMING = 3;
	public static final int MODIFICATION_AND_RENAMING = 4;
	
	private int type;
	private String path;
	private String newPath;
	
	/**
	 * Constructeur d'un évènement. 
	 * @param type Le type d'évènement capté.
	 * @param path Le chemin du fichier sur lequel un évènement a été capté. 
	 * @param newPath Le nouveau chemin du fichier. Nul s'il n'y a pas de 
	 * renommage.
	 */
	public Event(int type, String path, String newPath) {
		this.type = type;
		this.path = path;
		this.newPath = newPath;
	}
	
	/**
	 * Récupère le type de l'évènement.
	 * @return Le type de l'évènement.
	 */
	public int getType() {
		return this.type;
	}
	
	/**
	 * Modifie le type de l'évènement.
	 * @param type Le nouveau type de l'évènement.
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Récupère le chemin du fichier associé à l'évènement.
	 * @return Le chemin du fichier associé à l'évènement.
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Récupère le nouveau chemin du fichier.
	 * @return Le nouveau chemin du fichier. Nul si l'évènement n'est pas un 
	 * renommage.
	 */
	public String getNewPath() {
		return this.newPath;
	}
	
}
