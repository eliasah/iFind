package engine.search;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.rmi.CORBA.Util;

import org.xml.sax.SAXException;
/**
 * 
 * @author ahl
 *
 */
public class SearchClient {
 
	private Search[] req;
	private int[] port = {30000,30001,30002};
	private Socket socket;
	private OutputStreamWriter out;
	private InputStreamReader in;
	
	public SearchClient(){
		
	}
	
	public void Connect(){
		try {
			System.out.println("connection on port " + port[0]);
			socket = new Socket("localhost",port[0]);
			System.err.println("connection succeeded on port " + port[0]);
			
		} catch (IOException e) {
			try {
				System.out.println("connection on port " + port[1]);
				socket = new Socket("localhost",port[1]);
				System.err.println("connection succeeded on port " + port[1]);
				
			} catch (IOException e1) {
				try {
					System.out.println("connection on port " + port[2]);
					socket = new Socket("localhost",port[2]);
					System.err.println("connection succeeded on port " + port[2]);
					
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			// e.printStackTrace();
		}
	}
	
	
	
	public void Demande(Search[] r){
		//Envoie la demande avec la requete req de l'objet
		System.out.println("debut demande");
		this.req = r;
		
		try {
			for(int i=0;i<req.length;i++){
				out = new OutputStreamWriter(socket.getOutputStream());
				out.write(req[i].ConvertToXml());
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Result EcouteReponse(){
		//Recoit la reponse du serveur, la recoit et transforme l'xml recu en un objet Result
		//Une fois les result correspondant a la recherche recu, si il y en avait plusieurs, les tri
		//Ceux qui corresponde a plusieurs Search en premier, (plus pertinent) etc...
		
		System.out.println("debut ecouteReponse");
		Result retour= new Result(req[0].getId());
		Result[] result = new Result[req.length];
		
		try {
			
			in = new InputStreamReader(socket.getInputStream());
			char[] cbuf = new char[10];
			
			for(int i=0; i< req.length;	i++){
				System.out.println("Ecoute i:"+i);
				String ResultXml="";
				while( -1 !=in.read(cbuf)){
					ResultXml += cbuf; 
				}
				URI uri = URI.create(ResultXml);
				try {
					SimpleSaxParser parser = new SimpleSaxParser(uri.getPath());
					result[i] = parser.getHandler().getResult();
				} catch (SAXException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//### VERIFICATION DE LA REPONSE #####
		for (int i = 0; i < result.length; i++) {
			if(result[i].getId() != req[i].getId()){
				System.err.println("Reponse du serveur ne correspondes pas a la demande: Id differentes");
				return null;
			}
		}
		
		
		
		//### PARTI TRI SELON PERTINENCE ####
		
		if(result.length == 0){
			return null;
		}
		if(result.length == 1){
			return result[0];
		}
		ArrayList<Integer> pertinenceList = new ArrayList<Integer>();
		ArrayList<ResultFile> nontri = new ArrayList<ResultFile>();
		
		
		//Prend chaque element de chaque Array de result et le compare avec tous les autres elements
		//Pour voir si un certain fichier est dans plusieurs retour de result (ex: search titi search toto)
		// fichier qui contient les 2 sera dans les 2 result, donc tres pertinent, nous le voulons en premier
		for(int i=0; i<result.length;i++){
			ArrayList<ResultFile> flist = result[i].getFiles();
			for (int j = 0; j < result[i].getFiles().size(); j++) {
				ResultFile f = flist.get(j);
				int pertinence = 0;
				for (int k = 0; k < result.length; k++) {
					for (int k2 = 0; k2 < result[k].getFiles().size(); k2++) {
						if(k != i){
							if (result[k].getFiles().get(k2).getPath().equals(f.getPath())) {
								pertinence ++;
								//on enleve l'autre element, nous ne voulons pas de doublons;
								result[k].getFiles().remove(k2);
							} 
						}
					}
				}
				pertinenceList.add(pertinence);
				nontri.add(f);
			}	
		}
		
		//Une fois les pertinence de chaque element vue, on met tous dans retour de manier trie
		for (int j = 0; j < pertinenceList.size(); j++) {
			int max = 0;
			for (int i = 0; i < pertinenceList.size(); i++) {
				if(pertinenceList.get(i) >= max){
					max = pertinenceList.get(i);
				}
			}
			retour.getFiles().add(nontri.get(max));
			nontri.remove(max);
			pertinenceList.remove(max);
		}
		return retour;
	}
	
	@Override
	public String toString() {
		return "SearchClient [port=" + Arrays.toString(port) + ", service="
				+ socket + "]";
	}
	public static void main(String[] args) {
		
		SearchClient client= new SearchClient();
		Search s1 = new Search(1,"exem",false,"nouveau",null,null,null);
		Search s2 = new Search(2,"titi",false,"user/ahl",null,null,null);
		Search[] req = {s1,s2};
		client.Connect();
		client.Demande(req);
		Result r = client.EcouteReponse();
		System.out.println(r.ConvertToXml());
		
	}
}