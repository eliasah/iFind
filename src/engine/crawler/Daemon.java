package engine.crawler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * TODO documentation
 * 
 * @author isabelle
 *
 */

class Daemon {

	private LinkedList<String> corpusList = new LinkedList<String>();
	private LinkedList<String> excludedTypesList = new LinkedList<String>();

	public Daemon() {
		BufferedReader br;
		String line;
		try {
			// reading configuration file for the corpus to watch 
			br = new BufferedReader(new FileReader("corpus.dat"));
			line = br.readLine();
			while (line != null) {
				corpusList.add(line);
				line = br.readLine();
			}
			br.close();

			// reading configuration file for the types to exclude in indexation
			br = new BufferedReader(new FileReader("excluded_types.dat"));
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