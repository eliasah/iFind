package crawler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

public class Indexer extends Thread {

	private final String ADDRESS = "localhost";
	private final int PORT = 40000;
	private final int LIMIT = 3;

	private LinkedBlockingQueue<Event> events;
	private Hashtable<String, Event> eventsTable;
	private Socket socket;
	private BufferedWriter bw;

	public Indexer(LinkedBlockingQueue<Event> events) throws UnknownHostException, IOException {
		this.events = events;
		this.eventsTable = new Hashtable<String, Event>();
		this.socket = new Socket(ADDRESS, PORT);
		this.bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

	}

	public void run() {
		while (true) {
			try {
				Event event = this.events.take();
				this.addEvent(event);
				if (this.eventsTable.size() > LIMIT) {
					this.sendEvents();
					this.eventsTable = new Hashtable<String, Event>();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void addEvent(Event event) {
		int type = event.getType();
		String path = event.getPath();

		if (Pattern.matches(path, ".*~")) // ignoring backup files
			return;
		if (Pattern.matches(path, ".*/\\.swp")) // ignoring swap files
			return;
		if (Pattern.matches(path, "/\\..*")) // ignoring hidden files
			return;

		// TODO ignorer aussi les types de fichiers choisis par l'utilisateur

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

	public void addCreation(Event event) {
		String path = event.getPath();
		if (this.eventsTable.containsKey(path)) {
			if (this.eventsTable.get(path).getType() == Event.DELETION) {
				this.eventsTable.get(path).setType(Event.MODIFICATION);
			}
			if (this.eventsTable.get(path).getType() == Event.RENAMING) {
				this.eventsTable.put(path, event); // path is the old name of the event above ; the new name must be added
			}
		} else {
			this.eventsTable.put(path, event);
		}
	}

	public void addDeletion(Event event) {
		String path = event.getPath();
		if (this.eventsTable.containsKey(path)) {
			if (this.eventsTable.get(path).getType() == Event.CREATION) {
				this.eventsTable.remove(path);
			} else {
				this.eventsTable.get(path).setType(Event.DELETION);
			}
		} else {
			this.eventsTable.put(path, event);
		}
	}

	public void addModification(Event event) {
		String path = event.getPath();
		if (this.eventsTable.containsKey(path)) {
			if (this.eventsTable.get(path).getType() == Event.RENAMING) { // renamed and modified
				this.eventsTable.get(path).setType(Event.MODIFICATION_AND_RENAMING);
			}
		} else {
			this.eventsTable.put(path, event);
		}
	}

	public void addRenaming(Event event) {
		String path = event.getPath();
		String newPath = event.getNewPath();
		if (this.eventsTable.containsKey(path)) {
			if (this.eventsTable.get(path).getType() == Event.CREATION) {
				this.eventsTable.remove(path);
				Event newEvent = new Event(Event.CREATION, newPath, null);
				this.eventsTable.put(newPath, newEvent);
			} else if (this.eventsTable.get(path).getType() == Event.MODIFICATION
					|| this.eventsTable.get(path).getType() == Event.MODIFICATION_AND_RENAMING) {
				String oldPath = this.eventsTable.get(path).getPath();
				this.eventsTable.remove(path);
				Event newEvent = new Event(Event.MODIFICATION_AND_RENAMING, oldPath, newPath);
				this.eventsTable.put(newPath, newEvent);
			} else if (this.eventsTable.get(path).getType() == Event.RENAMING) {
				String oldPath = this.eventsTable.get(path).getPath();
				this.eventsTable.remove(path);
				Event newEvent = new Event(Event.RENAMING, oldPath, newPath);
				this.eventsTable.put(newPath, newEvent);
			}
		} else {
			this.eventsTable.put(newPath, event);
		}
	}

	public void sendEvents() throws IOException {
		XMLBuilder xmlBuilder = new XMLBuilder();

		Iterator<Event> it = this.eventsTable.values().iterator();
		while (it.hasNext()) {
			xmlBuilder.addEvent(it.next());
		}

		this.bw.write(xmlBuilder.buildXML());
		this.bw.flush();
	}

}
