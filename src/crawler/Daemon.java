package crawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;

public class Daemon {

	private LinkedBlockingQueue<Event> events;

	public Daemon() throws UnknownHostException, IOException {
		this.events = new LinkedBlockingQueue<Event>();
		this.addIndexer();
		this.addListeners();
	}

	public void addIndexer() throws UnknownHostException, IOException {
		(new Indexer(this.events)).start();
	}

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
