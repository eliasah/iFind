package engine.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * Cette classe joue le rôle de l'indexeur, qui gère les évènements captés
 * par les écouteurs. 
 * Les évènements captés sont traités puis envoyés à la base d'indexation
 * lorsque le nombre d'évènements courant dépasse un certain seuil.
 * Lorsqu'un évènement est capté et ajouté à la file d'évènements partagée 
 * par les écouteurs et l'indexeur, celui-ci traite l'évènement et vérifie
 * qu'il ne crée pas de conflit avec un autre évènement.
 * Par exemple, un premier évènement "modification de A" est détecté.
 * Puis un deuxième évènement "renommage de A en B" est détecté.
 * Enfin l'évènement "suppression de B" est détecté.
 * Si aucun prétraitement n'est effectué, une erreur sera détectée lors du 
 * traitement du premier évènement "modification de A", puisque le fichier A,
 * renommé B, a été supprimé du disque.
 * Il convient donc dans ce cas là de supprimer les deux premiers évènements
 * et de simplement envoyer à la base d'indexation l'évènement 
 * "suppression de A".
 * D'autres conflits de ce type peuvent avoir lieu et sont traités au moment
 * de l'ajout des évènements, selon leur type. 
 * 
 * @author isabelle
 *
 */
public class Indexer extends Thread {

	private final int LIMIT = 5;
	private final String TYPES_FILE = "config/excluded_types.dat";
	private final String COMMUNICATION_FILE = "config/daemon_to_bi.dat";
	private String address;
	private int port;

	private LinkedBlockingQueue<Event> events;
	private Hashtable<String, Event> eventsTable;
	private LinkedList<String> excludedTypes;
	private Socket socket;
	private BufferedWriter bw;

	/**
	 * Constructeur qui lance l'indexeur.
	 * @param events La file partagée des évènements détectés.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Indexer(LinkedBlockingQueue<Event> events) 
			throws UnknownHostException, IOException {
		this.configureCommunication();
		this.events = events;
		this.excludedTypes = new LinkedList<String>();
		this.eventsTable = new Hashtable<String, Event>();
		this.socket = new Socket(address, port);
		this.bw = new BufferedWriter(
				new OutputStreamWriter(this.socket.getOutputStream()));
		this.loadExcludedTypes();
	}

	public void run() {
		while (true) {
			try {
				// Les évènements sont traités en temps réel
				Event event = this.events.take();
				this.addEvent(event);
				if (this.eventsTable.size() >= LIMIT) {
					// Le nombre d'évènements nécessaires à l'envoi a été
					// atteint, les évènements sont envoyés 
					// à la base d'indexation
					this.sendEvents();
					this.eventsTable = new Hashtable<String, Event>();
				}
			} catch (InterruptedException e) {
				// FIXME
			} catch (IOException e) {
				// FIXME
			}
		}
	}
	
	public void configureCommunication() throws IOException {
		BufferedReader br =
				new BufferedReader(new FileReader(COMMUNICATION_FILE));
		this.address = br.readLine();
		this.port = Integer.parseInt(br.readLine());
		br.close();
	}


	/**
	 * Récupère la liste des types de fichiers à exclure de l'indexation.
	 * @throws IOException
	 */
	public void loadExcludedTypes() throws IOException {
		BufferedReader br =
				new BufferedReader(new FileReader(TYPES_FILE));
		String line;
		while ((line = br.readLine()) != null) {
			this.excludedTypes.add(line);
		}
		br.close();
	}

	/**
	 * Vérifie si un fichier doit être indexé ou non.
	 * @param path Le chemin du fichier à analyser.
	 * @return False si le fichier doit être ignoré, True sinon.
	 */
	public boolean isIndexable(String path) {
		boolean indexable = true;
		for (String s : this.excludedTypes) {
			if (Pattern.compile(s).matcher(path).matches()) {
				return false;
			}
		}
		return indexable;
	}

	/**
	 * Selon le type de l'évènement passé en paramètre, appelle la méthode
	 * appropriée qui supprimera les conflits entraînés par l'ajout de certains
	 * évènements incompatibles.
	 * @param event L'évènement du fichier à traiter.
	 * @throws IOException 
	 */
	public void addEvent(Event event) throws IOException {
		int type = event.getType();
		String path = event.getPath();

		if (!isIndexable(path)) return; // Ignorer les fichiers exclus 
		
		switch (type) {
		case Event.CREATION:
			this.addCreation(event);
			break;
		case Event.DELETION:
			this.addDeletion(event);
			break;
		case Event.MODIFICATION:
			this.addModification(event);
			break;
		case Event.RENAMING:
			this.addRenaming(event);
			break;
		}
	}

	/**
	 * Ajoute un évènement à la liste des évènements déjà traités
	 * et résoud les conflits si besoin.
	 * @param event L'évènement du fichier à indexer.
	 */
	public void addCreation(Event event) {
		String path = event.getPath();
		if (this.eventsTable.containsKey(path)) {
			if (this.eventsTable.get(path).getType() == Event.DELETION) {
				// Le fichier a été supprimé puis recréé:
				// cela revient à une modification du fichier
				this.eventsTable.get(path).setType(Event.MODIFICATION);
			}
		} else {
			this.eventsTable.put(path, event);
		}
	}

	/**
	 * Ajoute un évènement à la liste des évènements déjà traités
	 * et résoud les conflits si besoin.
	 * @param event L'évènement du fichier à supprimer.
	 */
	public void addDeletion(Event event) {
		String path = event.getPath();
		if (this.eventsTable.containsKey(path)) {
			if (this.eventsTable.get(path).getType() == Event.CREATION) {
				// Le fichier a été créé puis supprimé: inutile de l'indexer
				this.eventsTable.remove(path);
			} else {
				// Le fichier a déjà été indexé, puis a été renommé ou modifié:
				// il suffit de le supprimer
				this.eventsTable.get(path).setType(Event.DELETION);
			}
		} else {
			this.eventsTable.put(path, event);
		}
	}

	/**
	 * Ajoute un évènement à la liste des évènements déjà traités
	 * et résoud les conflits si besoin.
	 * @param event L'évènement du fichier à réindexer.
	 */
	public void addModification(Event event) {
		String path = event.getPath();
		if (this.eventsTable.containsKey(path)) {
			if (this.eventsTable.get(path).getType() == Event.RENAMING) {
				// Le fichier a été renommé et est maintenant modifié
				this.eventsTable.get(path).setType(
						Event.MODIFICATION_AND_RENAMING);
			}
		} else {
			this.eventsTable.put(path, event);
		}
	}

	/**
	 * Ajoute un évènement à la liste des évènements déjà traités
	 * et résoud les conflits si besoin.
	 * @param event L'évènement du fichier à renommer.
	 */
	public void addRenaming(Event event) {
		String path = event.getPath();
		String newPath = event.getNewPath();
		if (this.eventsTable.containsKey(path)) {
			if (this.eventsTable.get(path).getType() == Event.CREATION) {
				// Le fichier n'a pas encore été indexé: il suffit de l'indexer
				// avec le nom attribué lors du renommage 
				this.eventsTable.remove(path);
				Event newEvent = new Event(Event.CREATION, newPath, null);
				this.eventsTable.put(newPath, newEvent);
			} else if (this.eventsTable.get(path).getType() 
					== Event.MODIFICATION
					|| this.eventsTable.get(path).getType() 
					== Event.MODIFICATION_AND_RENAMING) {
				// Le fichier a été modifié: il faut indexer la 
				// modification sous forme de "modification et renommage"
				String oldPath = this.eventsTable.get(path).getPath();
				this.eventsTable.remove(path);
				Event newEvent = new Event(Event.MODIFICATION_AND_RENAMING, 
						oldPath, newPath);
				this.eventsTable.put(newPath, newEvent);
			} else if (this.eventsTable.get(path).getType() == Event.RENAMING) {
				// L'ancien renommage du fichier n'a pas encore été indexé:
				// on fusionne les deux renommages
				String oldPath = this.eventsTable.get(path).getPath();
				this.eventsTable.remove(path);
				Event newEvent = new Event(Event.RENAMING, oldPath, newPath);
				this.eventsTable.put(newPath, newEvent);
			}
		} else {
			this.eventsTable.put(newPath, event);
		}
	}

	/**
	 * Génère le code XML associé aux évènements captés et l'envoie
	 * à la base d'indexation.
	 * @throws IOException
	 */
	public void sendEvents() throws IOException {
		XMLBuilder xmlBuilder = new XMLBuilder();

		Iterator<Event> it = this.eventsTable.values().iterator();
		while (it.hasNext()) {
			xmlBuilder.addEvent(it.next());
		}
		
		this.bw.write(xmlBuilder.buildXML());
		this.bw.flush();
		System.out.println("Flux XML envoyé"); // FIXME
	}

}
