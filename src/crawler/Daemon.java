package crawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Cette classe lance des écouteurs sur chaque élément du corpus à surveiller
 * et un indexeur qui a pour rôle de traiter les évènements captés
 * pour ensuite générer un document XML à envoyer à la base d'indexation.
 * @author isabelle
 *
 */
public class Daemon {

	private LinkedBlockingQueue<Event> events;

	/**
	 * Constructeur qui lance le moteur d'indexation, à savoir
	 * les écouteurs et l'indexeur.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Daemon() throws UnknownHostException, IOException {
		this.events = new LinkedBlockingQueue<Event>();
		this.addIndexer();
		this.addListeners();
	}

	/**
	 * Lance l'indexeur qui gèrera les évènements captés par les écouteurs.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void addIndexer() throws UnknownHostException, IOException {
		(new Indexer(this.events)).start();
	}

	/**
	 * Ajoute des écouteurs sur chacun des répertoires qui doivent
	 * être surveillés.
	 * @throws IOException
	 */
	public void addListeners() throws IOException {
		BufferedReader br;
		String line;
		br = new BufferedReader(new FileReader("config/corpus.dat"));
		while ((line = br.readLine()) != null) {
			new FileListener(line, this.events);
		}
		br.close();
	}

	public static void main(String[] args) {
		try {
			new Daemon();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
