package database.trigram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author Abou Haydar Elias - Univ. Paris Denis Diderot
 *
 */
public class Trigram{
	private HashMap set;
	private int cpt;
	private String delim = " \t\n\r\f,.:;?!'()[]";

	public Trigram(String s){
		set = new HashMap<String,Integer>();
		cut(s);
	}

	public Trigram(File f) {
		set = new HashMap<String,Integer>();
		cutfile(f);
	}

	public boolean containsKey(String s) {
		return set.containsKey(s);
	}
	private void cut(String s) {
		int length = s.length();
		String ss;
		int key = 0;
		for (int i = 0;i<length-2;i++) {
			ss = s.substring(i,i+3);
			if (!set.containsKey(ss))
				set.put(ss,1);
			else {
				set.put(ss, (int)set.get(ss)+1);		
			}
		cpt++;
		}
	}

	private void cutfile(File f) {
		try {
			Scanner sc = new Scanner(f);
			System.out.println("File found and in process");
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				cut(line);
			}
			System.out.println("End processing");
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<String> toArrayList() {
		ArrayList<String> ret = new ArrayList<String>();
		Iterator it = set.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        ret.add((String) pairs.getKey());
	        it.remove();
	    }
		return ret;
		
	}
	
	public String toString(){
		String res = "{";
		Iterator it = set.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        res += pairs.getKey() + " = " + pairs.getValue()+"\n";
	        it.remove();
	    }
		return res+"}";
	}
	
	public void printMap() {
	    Iterator it = set.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    System.out.println("# trigram = " + cpt);
	}
}