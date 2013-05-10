package crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Cette classe génère un document XML destiné à être envoyé 
 * à la base d'indexation.
 * Le document contient les informations concernant les évènements captés
 * par les écouteurs placés sur les différents éléments du corpus,
 * qui ont ensuite été transmises par l'indexeur.
 * Le document généré respecte la DTD fournie à l'ensemble des groupes. 
 * 
 * @author Isabelle Richard
 *
 */
public class XMLBuilder {

	private String creation = "";
	private String deletion = "";
	private String modification = "";
	private String renaming = "";

	/**
	 * Retourne le document XML final construit à partir des évènements
	 * fournis à la méthode {@link #addEvent(Event)}.
	 * @return Une chaîne représentant le document XML généré.
	 */
	public String buildXML() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<CREATIONS>" + this.creation + "</CREATIONS>" +
				"<INDEXATION>" +
				"<SUPPRESSIONS>" + this.deletion + "</SUPPRESSIONS>" +
				"<MODIFICATIONS>" + this.modification + "</MODIFICATIONS>" +
				"<RENOMMAGES>" + this.renaming + "</RENOMMAGES>" +			
				"</INDEXATION>";
	}

	/**
	 * Selon le type de l'évènement passé en paramètre, appelle la méthode
	 * appropriée qui génèrera le code XML associé.
	 * @param event L'évènement à ajouter au document XML.
	 * @throws IOException
	 */
	public void addEvent(Event event) throws IOException {
		int type = event.getType();
		switch(type) {
		case Event.CREATION:
			this.addCreation(event.getPath()); 
			break;
		case Event.DELETION:
			this.addDeletion(event.getPath()); 
			break;
		case Event.MODIFICATION:
			this.addModification(event.getPath()); 
			break;
		case Event.RENAMING:
			this.addRenaming(event.getPath(), event.getNewPath()); 
			break;
		case Event.MODIFICATION_AND_RENAMING:
			this.addModificationAndRenaming(event.getPath(), 
					event.getNewPath());
			break;
		}
	}

	/**
	 * Récupère les informations et le contenu du fichier qui a été créé
	 * et génère le code XML associé.
	 * @param path Le chemin du fichier à ajouter.
	 * @throws IOException
	 */
	public void addCreation(String path) throws IOException {
		Path p = Paths.get(path);
		String format = "";
		StringTokenizer tokens = new StringTokenizer(path, ".");
		String token = "";
		while (tokens.hasMoreTokens())
			token = tokens.nextToken();
		if (token.equals("")) {
			if (!(new File(path)).isDirectory()) {
				return; // fichier régulier sans extension
			} else {
				format = "DIR"; // répertoire
			}
		} else {
			format = token;
		}
		long time = Files.getLastModifiedTime(p).toMillis();
		long size = Files.size(p);
		PosixFileAttributes attrs = Files.readAttributes(p, 
				PosixFileAttributes.class);
		Set<PosixFilePermission> permissions = attrs.permissions();

		this.creation += "<FICHIERCREE>" +
				"<PATH>" + path + "</PATH>" +
				"<FORMAT>" + format + "</FORMAT>" +
				"<DATECREATION>" + time + "</DATECREATION>" +
				"<TAILLE>" + size + "</TAILLE>" +
				"<PROPRIETAIRE>" + attrs.owner() + "</PROPRIETAIRE>" +
				"<GROUPE>" + attrs.group() + "</GROUPE>" +
				"<PERMISSIONS>" + PosixFilePermissions.toString(permissions) + 
				"</PERMISSIONS>" +
				(attrs.isRegularFile() ? "<INDEXAGE>" + this.indexWords(path) +
						"</INDEXAGE>" : "") +
						"</FICHIERCREE>";
	}

	/**
	 * Génère le code XML indiquant à la base d'indexation de supprimer
	 * le fichier dont le chemin est passé en paramètre.
	 * @param path Le chemin du fichier à supprimer.
	 */
	public void addDeletion(String path) {
		this.deletion += "<FICHIERSUPPRIME><PATH>" + path +
				"</PATH></FICHIERSUPPRIME>";
	}

	/**
	 * Récupère les informations et le contenu du fichier qui a été modifié
	 * et génère le code XML associé.
	 * @param path Le chemin du fichier à réindexer.
	 * @throws IOException
	 */
	public void addModification(String path) throws IOException {
		Path p = Paths.get(path);
		long time = Files.getLastModifiedTime(p).toMillis();
		long size = Files.size(p);
		PosixFileAttributes attrs = Files.readAttributes(p, 
				PosixFileAttributes.class);
		Set<PosixFilePermission> permissions = attrs.permissions();

		this.modification = "<FICHIERMODIFIE>" +
				"<PATH>" + path + "</PATH>" +
				"<DATEMODIFICATION>" + time + "</DATEMODIFICATION>" +
				"<TAILLE>" + size + "</TAILLE>" +
				"<PROPRIETAIRE>" + attrs.owner() + "</PROPRIETAIRE>" +
				"<GROUPE>" + attrs.group() + "</GROUPE>" +
				"<PERMISSIONS>" + PosixFilePermissions.toString(permissions) + 
				"</PERMISSIONS>" +
				(attrs.isRegularFile() ? "<INDEXAGE>" + this.indexWords(path) + 
						"</INDEXAGE>" : "") +
						"</FICHIERMODIFIE>";
	}

	/**
	 * Génère le code XML indiquant à la base d'indexation de renommer 
	 * le fichier passé en paramètre.
	 * @param oldPath Le chemin du fichier à renommer.
	 * @param path Le chemin à donner au fichier renommé.
	 */
	public void addRenaming(String oldPath, String path) {
		this.renaming = "<FICHIERRENOMME><PATH>" + oldPath + "</PATH>" +
				"<NEWPATH>" + path + "</NEWPATH></FICHIERRENOMME>";
	}

	/**
	 * Récupère les informations et le contenu du fichier qui a été modifié
	 * et renommé et génère le code XML associé.
	 * @param oldPath Le chemin du fichier à renommer et à réindexer.
	 * @param path Le chemin à donner au fichier renommé.
	 * @throws IOException
	 */
	public void addModificationAndRenaming(String oldPath, String path) 
			throws IOException {
		Path p = Paths.get(path);
		long time = Files.getLastModifiedTime(p).toMillis();
		long size = Files.size(p);
		PosixFileAttributes attrs = Files.readAttributes(p, 
				PosixFileAttributes.class);
		Set<PosixFilePermission> permissions = attrs.permissions();

		this.modification = "<FICHIERMODIFIE>" +
				"<PATH>" + oldPath + "</PATH>" +
				"<DATEMODIFICATION>" + time + "</DATEMODIFICATION>" +
				"<TAILLE>" + size + "</TAILLE>" +
				"<PROPRIETAIRE>" + attrs.owner() + "</PROPRIETAIRE>" +
				"<GROUPE>" + attrs.group() + "</GROUPE>" +
				"<PERMISSIONS>" + PosixFilePermissions.toString(permissions) + 
				"</PERMISSIONS>" +
				(attrs.isRegularFile() ? "<INDEXAGE>" + this.indexWords(path) +
						"</INDEXAGE>" : "") + 
						"<NEWPATH>" + path + "</NEWPATH>" +
						"</FICHIERMODIFIE>";
	}

	/**
	 * Récupère les mots et leur fréquence d'un fichier
	 * et génère le code XML associé.  
	 * @param path Le chemin du fichier à indexer.
	 * @return Le code XML correspondant à la liste des mots du fichier
	 * donné au paramètre.
	 * @throws IOException
	 */
	public String indexWords(String path) throws IOException {
		// FIXME actuellement ne fonctionne qu'avec un fichier texte simple 
		String words = "";
		BufferedReader br = new BufferedReader(new FileReader(path));
		Hashtable<String, Integer> frequences =
				new Hashtable<String, Integer>();
		String line;
		while ((line = br.readLine()) != null) {
			String delimiters = " \t\n\r\f,.:;?!'+*-/()[]{}";
			StringTokenizer token = new StringTokenizer(line, delimiters);
			while (token.hasMoreTokens()) {
				String tmp = token.nextToken();
				if (!frequences.containsKey(tmp)) {
					frequences.put(tmp, 1);
				} else {
					int oldFrequence = frequences.get(tmp);
					frequences.put(tmp, oldFrequence+1);
				}
			}
		}
		br.close();
		Iterator<String> itWords = frequences.keySet().iterator();
		Iterator<Integer> itFrequences = frequences.values().iterator();
		while (itWords.hasNext() && itFrequences.hasNext()) {
			words += "<MOT frequence=\"" + itFrequences.next() + "\">" + 
					itWords.next() + "</MOT>";
		}
		return words;
	}

}