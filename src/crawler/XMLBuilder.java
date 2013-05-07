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

public class XMLBuilder {

	private String creation = "";
	private String deletion = "";
	private String modification = "";
	private String renaming = "";

	public String buildXML() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<INDEXATION>" +
				"<CREATIONS>" + this.creation + "</CREATIONS>" +
				"<SUPPRESSIONS>" + this.deletion + "</SUPPRESSIONS>" +
				"<MODIFICATIONS>" + this.modification + "</MODIFICATIONS>" +
				"<RENOMMAGES>" + this.renaming + "</RENOMMAGES>" +			
				"</INDEXATION>";
	}

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
			this.addModificationAndRenaming(event.getPath(), event.getNewPath());
			break;
		}
	}

	public void addCreation(String path) throws IOException {
		Path p = Paths.get(path);
		String format = "";
		String[] tab = path.split(".");
		if (tab.length < 2) {
			if (!(new File(path)).isDirectory()) { // regular file without extension
				return;
			} else {
				format = "DIR"; // directory
			}
		} else {
			format = tab[tab.length-1];
		}
		long time = Files.getLastModifiedTime(p).toMillis();
		long size = Files.size(p);
		PosixFileAttributes attrs = Files.readAttributes(p, PosixFileAttributes.class);
		Set<PosixFilePermission> permissions = attrs.permissions();

		this.creation += "<FICHIERCREE>" +
				"<PATH>" + path + "</PATH>" +
				"<FORMAT>" + format + "</FORMAT>" +
				"<DATECREATION>" + time + "</DATECREATION>" +
				"<TAILLE>" + size + "</TAILLE>" +
				"<PROPRIETAIRE>" + attrs.owner() + "</PROPRIETAIRE>" +
				"<GROUPE>" + attrs.group() + "</GROUPE>" +
				"<PERMISSIONS>" + PosixFilePermissions.toString(permissions) + "</PERMISSIONS>" +
				(attrs.isRegularFile() ? "<INDEXAGE>" + this.indexWords(path) + "</INDEXAGE>" : "") +
				"</FICHIERCREE>";
	}

	public void addDeletion(String path) {
		this.deletion += "<FICHIERSUPPRIME><PATH>" + path + "</PATH></FICHIERSUPPRIME>";
	}

	public void addModification(String path) throws IOException {
		Path p = Paths.get(path);
		long time = Files.getLastModifiedTime(p).toMillis();
		long size = Files.size(p);
		PosixFileAttributes attrs = Files.readAttributes(p, PosixFileAttributes.class);
		Set<PosixFilePermission> permissions = attrs.permissions();

		this.modification = "<FICHIERMODIFIE>" +
				"<PATH>" + path + "</PATH>" +
				"<DATEMODIFICATION>" + time + "</DATEMODIFICATION>" +
				"<TAILLE>" + size + "</TAILLE>" +
				"<PROPRIETAIRE>" + attrs.owner() + "</PROPRIETAIRE>" +
				"<GROUPE>" + attrs.group() + "</GROUPE>" +
				"<PERMISSIONS>" + PosixFilePermissions.toString(permissions) + "</PERMISSIONS>" +
				(attrs.isRegularFile() ? "<INDEXAGE>" + this.indexWords(path) + "</INDEXAGE>" : "") +
				"</FICHIERMODIFIE>";
	}

	public void addRenaming(String oldPath, String path) {
		this.renaming = "<FICHIERRENOMME><PATH>" + oldPath + "</PATH>" +
				"<NEWPATH>" + path + "</NEWPATH></FICHIERRENOMME>";
	}

	public void addModificationAndRenaming(String oldPath, String path) throws IOException {
		Path p = Paths.get(path);
		long time = Files.getLastModifiedTime(p).toMillis();
		long size = Files.size(p);
		PosixFileAttributes attrs = Files.readAttributes(p, PosixFileAttributes.class);
		Set<PosixFilePermission> permissions = attrs.permissions();

		this.modification = "<FICHIERMODIFIE>" +
				"<PATH>" + oldPath + "</PATH>" +
				"<DATEMODIFICATION>" + time + "</DATEMODIFICATION>" +
				"<TAILLE>" + size + "</TAILLE>" +
				"<PROPRIETAIRE>" + attrs.owner() + "</PROPRIETAIRE>" +
				"<GROUPE>" + attrs.group() + "</GROUPE>" +
				"<PERMISSIONS>" + PosixFilePermissions.toString(permissions) + "</PERMISSIONS>" +
				(attrs.isRegularFile() ? "<INDEXAGE>" + this.indexWords(path) + "</INDEXAGE>" : "") + 
				"<NEWPATH>" + path + "</NEWPATH>" +
				"</FICHIERMODIFIE>";
	}

	public String indexWords(String path) throws IOException {
		// for this method, path must design a regular file
		String words = "";
		BufferedReader br = new BufferedReader(new FileReader(path));
		Hashtable<String, Integer> frequences = new Hashtable<String, Integer>();
		String line;
		while ((line = br.readLine()) != null) {
			String delimiters = " \t\n\r\f,.:;?!'";
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
		Iterator itWords = frequences.keySet().iterator();
		Iterator itFrequences = frequences.values().iterator();
		while (itWords.hasNext() && itFrequences.hasNext()) {
			words += "<MOT frequence=\"" + itFrequences.next() + "\">" + itWords.next() + "</MOT>";
		}
		return words;
	}

}