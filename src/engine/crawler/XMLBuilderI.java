package engine.crawler;

import java.io.IOException;

public interface XMLBuilderI {

	/**
	 * Retourne le document XML final construit à partir des évènements
	 * fournis à la méthode {@link #addEvent(Event)}.
	 * @return Une chaîne représentant le document XML généré.
	 */
	public abstract String buildXML();

	/**
	 * Selon le type de l'évènement passé en paramètre, appelle la méthode
	 * appropriée qui génèrera le code XML associé.
	 * @param event L'évènement à ajouter au document XML.
	 * @throws IOException
	 */
	public abstract void addEvent(Event event) throws IOException;

	/**
	 * Récupère les informations et le contenu du fichier qui a été créé
	 * et génère le code XML associé.
	 * @param path Le chemin du fichier à ajouter.
	 * @throws IOException
	 */
	public abstract void addCreation(String path) throws IOException;

	/**
	 * Génère le code XML indiquant à la base d'indexation de supprimer
	 * le fichier dont le chemin est passé en paramètre.
	 * @param path Le chemin du fichier à supprimer.
	 */
	public abstract void addDeletion(String path);

	/**
	 * Récupère les informations et le contenu du fichier qui a été modifié
	 * et génère le code XML associé.
	 * @param path Le chemin du fichier à réindexer.
	 * @throws IOException
	 */
	public abstract void addModification(String path) throws IOException;

	/**
	 * Génère le code XML indiquant à la base d'indexation de renommer 
	 * le fichier passé en paramètre.
	 * @param oldPath Le chemin du fichier à renommer.
	 * @param path Le chemin à donner au fichier renommé.
	 */
	public abstract void addRenaming(String oldPath, String path);

	/**
	 * Récupère les informations et le contenu du fichier qui a été modifié
	 * et renommé et génère le code XML associé.
	 * @param oldPath Le chemin du fichier à renommer et à réindexer.
	 * @param path Le chemin à donner au fichier renommé.
	 * @throws IOException
	 */
	public abstract void addModificationAndRenaming(String oldPath, String path)
			throws IOException;

	/**
	 * Récupère les mots et leur fréquence d'un fichier
	 * et génère le code XML associé.  
	 * @param path Le chemin du fichier à indexer.
	 * @return Le code XML correspondant à la liste des mots du fichier
	 * donné au paramètre.
	 * @throws IOException
	 */
	public abstract String indexWords(String path) throws IOException;

}