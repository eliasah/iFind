package crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;

public class FirstIndexation {

	public static class BadCorpusFile extends Exception {};

	private final String ADDRESS = "localhost";
	private final int PORT = 40000;

	private LinkedBlockingQueue<Event> events;

	public FirstIndexation() throws IOException, BadCorpusFile {
		this.events = new LinkedBlockingQueue<Event>();
		this.scanDirectories();
		this.sendToDatabase();
	}

	public void scanDirectories() throws IOException, BadCorpusFile {
		BufferedReader br;
		String line;
		br = new BufferedReader(new FileReader("config/corpus.dat"));
		while ((line = br.readLine()) != null) {
			this.scanDirectory(line);
		}
		br.close();
	}

	public void scanDirectory(String path) throws BadCorpusFile {
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

	public void sendToDatabase() throws UnknownHostException, IOException {
		Socket socket = new Socket(ADDRESS, PORT);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		XMLBuilder xmlBuilder = new XMLBuilder();

		for (Event e : this.events) {
			xmlBuilder.addEvent(e);
		}

		//System.out.println(xmlBuilder.buildXML());
		bw.write(xmlBuilder.buildXML());
		bw.flush();
		socket.close();
	}

	public static void main(String[] args) {
		try {
			new FirstIndexation();
		} catch (BadCorpusFile e) {
			System.err.println("Erreur : fichier de configuration \"corpus.dat\"");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
