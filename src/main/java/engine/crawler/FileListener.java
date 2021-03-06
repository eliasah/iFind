package engine.crawler;

import java.util.concurrent.LinkedBlockingQueue;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

/**
 * Cette classe implémente JNotifyListener, un utilitaire permettant de
 * surveiller un répertoire et d'en capter les évènements tels que la 
 * création, le renommage, la modification ou la suppression d'un fichier.
 * Lorsqu'un évènement est détecté, il est ajouté à la liste fournie
 * lors de la construction de la classe.
 * 
 * @author Isabelle Richard
 *
 */

public class FileListener implements JNotifyListener {

	private LinkedBlockingQueue<Event> events;

	/**
	 * Constructeur qui lance l'écoute des évènements d'un répertoire.
	 * @param path Le chemin du répertoire à surveiller.
	 * @param events La file où stocker les évènements captés.
	 * @throws JNotifyException
	 */
	public FileListener(String path, LinkedBlockingQueue<Event> events) 
			throws JNotifyException {
		this.events = events;
		int mask = JNotify.FILE_ANY;
		boolean watchSubtree = true;
		JNotify.addWatch(path, mask, watchSubtree, this);
	}

	public void fileCreated(int wd, String rootPath, String name) {
		String path = rootPath + "/" + name;
		events.add(new Event(Event.CREATION, path, null));
	}

	public void fileDeleted(int wd, String rootPath, String name) {
		String path = rootPath + "/" + name;
		events.add(new Event(Event.DELETION, path, null));
	}

	public void fileModified(int wd, String rootPath, String name) {
		String path = rootPath + "/" + name;
		events.add(new Event(Event.MODIFICATION, path, null));
	}

	public void fileRenamed(int wd, String rootPath, String oldName, 
			String newName) {
		String path = rootPath + "/" + oldName;
		String newPath = rootPath + "/" + newName;
		events.add(new Event(Event.RENAMING, path, newPath));
	}
}

