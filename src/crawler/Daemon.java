package crawler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * TODO documentation
 * 
 * @author isabelle
 *
 */

class Daemon {
	
	public static void main(String[] args) {
		Socket socket;
		
		LinkedList<String> createdFiles = new LinkedList<String>();
		LinkedList<String> deletedFiles = new LinkedList<String>();
		LinkedList<String> modifiedFiles = new LinkedList<String>();
		LinkedList<String> renamedFiles = new LinkedList<String>();
		
		LinkedList<JObserver> observers = new LinkedList<JObserver>();
		Daemon daemon = new Daemon();
		
		for (String corpus : daemon.getCorpusList())
			observers.add(new JObserver(corpus, createdFiles, deletedFiles, modifiedFiles, renamedFiles));
		
		try {
			socket = new Socket("localhost", 40000);
			
			while (true) {
				Thread.sleep(30000); // starts indexation every 30 seconds
				// do the ...
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

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

	public void setCorpusList(LinkedList<String> corpusList) {
		this.corpusList = corpusList;
	}

	public LinkedList<String> getExcludedTypesList() {
		return excludedTypesList;
	}

	public void setExcludedTypesList(LinkedList<String> excludedTypesList) {
		this.excludedTypesList = excludedTypesList;
	}
	
	
}