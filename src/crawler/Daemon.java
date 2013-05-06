package crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.LinkedList;
import java.util.Set;

public class Daemon {

	public static final int TIME = 10000; // time in seconds 
	public static final int PORT = 40000;

	private LinkedList<String> corpusList = new LinkedList<String>();
	private LinkedList<String> excludedTypesList = new LinkedList<String>();

	public Daemon() {
		BufferedReader br;
		String line;
		try {
			// reading configuration file for the corpus to watch 
			br = new BufferedReader(new FileReader("config/corpus.dat"));
			line = br.readLine();
			while (line != null) {
				corpusList.add(line);
				line = br.readLine();
			}
			br.close();

			// reading configuration file for the types to exclude in indexation
			br = new BufferedReader(new FileReader("config/excluded_types.dat"));
			line = br.readLine();
			while (line != null) {
				excludedTypesList.add(line);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public LinkedList<String> getCorpusList() {
		return corpusList;
	}

	public LinkedList<String> getExcludedTypesList() {
		return excludedTypesList;
	}

	public static String getCreation(String path) {
		String creation	= "<FICHIERCREE>";
		try {
			Path p = Paths.get(path);
			String[] tab = path.split(".");
			if (tab.length < 2) return ""; // no extension, invalid path
			String format = tab[tab.length-1];
			long time = Files.getLastModifiedTime(p).toMillis();
			long size = Files.size(p);
			PosixFileAttributes attrs = Files.readAttributes(p, PosixFileAttributes.class);
			Set<PosixFilePermission> permissions = attrs.permissions();
			creation += "<PATH>" + path + "</PATH>";
			creation += "<format>" + format + "</format>";
			creation += "<DATEMODIFICATION>" + time + "</DATEMODIFICATION>";
			creation += "<TAILLE>" + size + "</TAILLE>";
			creation += "<PROPRIETAIRE>" + attrs.owner() + "</PROPRIETAIRE>";
			creation += "<GROUPE>" + attrs.group() + "</GROUPE>";
			creation += "<PERMISSIONS>" + PosixFilePermissions.toString(permissions) + "</PERMISSIONS>";
			creation += "<INDEXAGE>" + "</INDEXAGE>"; // TODO
			creation += "</FICHIERCREE>";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return creation;
	}
	
	public static String getModification(String path) {
		String modification	= "<FICHIERMODIFIE>";
		try {
			Path p = Paths.get(path);
			if (p == null) return "";
			long time = Files.getLastModifiedTime(p).toMillis();
			long size = Files.size(p);
			PosixFileAttributes attrs = Files.readAttributes(p, PosixFileAttributes.class);
			Set<PosixFilePermission> permissions = attrs.permissions();
			modification += "<PATH>" + path + "</PATH>";
			modification += "<DATEMODIFICATION>" + time + "</DATEMODIFICATION>";
			modification += "<TAILLE>" + size + "</TAILLE>";
			modification += "<PROPRIETAIRE>" + attrs.owner() + "</PROPRIETAIRE>";
			modification += "<GROUPE>" + attrs.group() + "</GROUPE>";
			modification += "<PERMISSIONS>" + PosixFilePermissions.toString(permissions) + "</PERMISSIONS>";
			modification += "<INDEXAGE>" + "</INDEXAGE>"; // TODO
			modification += "</FICHIERMODIFIE>";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modification;
	}

	public static void main(String[] args) {
		LinkedList<Event> events = new LinkedList<Event>();
		LinkedList<JListener> observers = new LinkedList<JListener>();

		Daemon daemon = new Daemon();

		for (String corpus : daemon.getCorpusList()) {
			observers.add(new JListener(corpus, events));
			System.out.println("nouvel observer ajouté sur " + corpus);
		}

		try {
//			Socket socket = new Socket("localhost", PORT);
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			while (true) {
				Thread.sleep(TIME);
				String creations = "<CREATIONS>";
				String suppressions = "<SUPPRESSIONS>";
				String modifications = "<MODIFICATIONS>";
				String renommages = "<RENOMMAGES>";
				Event event = events.poll();
				while (event != null) {
					switch (event.getType()) {
					case 'C':
						creations += getCreation(event.getNewPath());
						break;
					case 'D':
						suppressions += "<FICHIERSUPPRIME><PATH>" + event.getNewPath() + "</PATH></FICHIERSUPPRIME>";
						break;
					case 'M':
						modifications += getModification(event.getNewPath());
						break;
					case 'R':
						renommages += "<FICHIERRENOMME><PATH>" + event.getOldPath() + "</PATH>"
								+ "<NEWPATH>" + event.getNewPath() + "</NEWPATH></FICHIERRENOMME>";
						break;
					}
					creations += "</CREATIONS>";
					suppressions += "</SUPPRESSIONS>";
					modifications += "</MODIFICATIONS>";
					renommages += "</RENOMMAGES>";
					event = events.poll();
				}
				String messageXML = "<INDEXATION>";
				if (!creations.equals("<CREATIONS></CREATIONS>"))
					messageXML += creations;
				if (!creations.equals("<SUPPRESSIONS></SUPPRESSIONS>"))
					messageXML += suppressions;
				if (!creations.equals("<MODIFICATIONS></MODIFICATIONS>"))
					messageXML += modifications;
				if (!creations.equals("<RENOMMAGES></RENOMMAGES>"))
					messageXML += renommages;
				messageXML += "</INDEXATION>";
//				if (!messageXML.equals("<INDEXATION></INDEXATION>"))
//					bw.write(messageXML);
				System.out.println(messageXML);
			}
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}