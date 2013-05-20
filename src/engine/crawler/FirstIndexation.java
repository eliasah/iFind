package engine.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * Cette classe génère un document XML contenant l'ensemble des fichiers
 * inclus dans les répertoires à surveiller.
 * 
 * @author isabelle
 *
 */
public class FirstIndexation {

	/**
	 * Cette exception est lancée lorsque le fichier contenant l'ensemble
	 * du corpus à surveiller n'existe pas.
	 *  
	 * @author isabelle
	 *
	 */
	public static class BadCorpusFile extends Exception {};

	private final String ADDRESS = "localhost";
	private final int PORT = 40000;
	private final String CORPUS_FILE = "config/corpus.dat";
	private final String TYPES_FILE = "config/excluded_types.dat";
	private LinkedBlockingQueue<Event> events;
	private LinkedList<String> excludedTypes;

	/**
	 * Constructeur qui lance l'indexation sur le corpus.
	 * @throws IOException 
	 * @throws BadCorpusFile
	 */
	public FirstIndexation() throws IOException, BadCorpusFile {
		this.events = new LinkedBlockingQueue<Event>();
		this.excludedTypes = new LinkedList<String>();
		this.loadExcludedTypes();
		this.scanDirectories();
		this.sendToDatabase();
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
		String name = (new File(path).getName());
		for (String s : this.excludedTypes) {
			System.out.print("Pattern.compile("+s+").matcher("+name+").matches() : ");
			System.out.println(Pattern.compile(s).matcher(name).matches());
			if (Pattern.compile(s).matcher(name).matches()) {
				return false;
			}
		}
		return indexable;
	}

	/**
	 * Appelle la fonction {@link #scanDirectory(String)} 
	 * pour chaque élément du corpus à indexer. 
	 * @throws IOException
	 * @throws BadCorpusFile
	 */
	public void scanDirectories() throws IOException, BadCorpusFile {
		BufferedReader br;
		String line;
		br = new BufferedReader(new FileReader(CORPUS_FILE));
		while ((line = br.readLine()) != null) {
			this.scanDirectory(line);
		}
		br.close();
	}

	/**
	 * Ajoute le fichier dont le chemin est passé en paramètre à la liste
	 * des fichiers à ajouter dans la base de d'indexation.
	 * Si le fichier est un répertoire, la fonction est appelée sur chacun
	 * de ses éléments.
	 * @param path Chemin du fichier à traiter.
	 * @throws BadCorpusFile
	 */
	public void scanDirectory(String path) throws BadCorpusFile {
		if (!isIndexable(path)) return;
		File directory = new File(path);
		if (!directory.exists()) {
			throw new BadCorpusFile();
		} else if (!directory.isDirectory()) {
			this.events.add(new Event(Event.CREATION, path, null));
		} else {
			File[] files = directory.listFiles();
			for (int i=0; i<files.length; i++) {
				String p = files[i].getAbsolutePath();
				this.scanDirectory(p);
			}
			this.events.add(new Event(Event.CREATION, path, null));
		}
	}

	/**
	 * Génère le document XML contenant l'ensemble des fichiers à indexer
	 * puis l'envoie à la base d'indexation.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void sendToDatabase() throws UnknownHostException, IOException {
		Socket socket = new Socket(ADDRESS, PORT);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream()));
		XMLBuilder xmlBuilder = new XMLBuilder();

		for (Event e : this.events) {
			xmlBuilder.addEvent(e);
		}

		bw.write(xmlBuilder.buildXML());
		bw.flush();
		socket.close();
	}

	public static void main(String[] args) {
		try {
			new FirstIndexation();
		} catch (BadCorpusFile e) {
			System.err.println("Erreur : fichier de configuration \"corpus.dat\"");
		} catch (ConnectException e) {
			System.err.println("Connexion au serveur refusée");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
