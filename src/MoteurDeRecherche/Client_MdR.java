package MoteurDeRecherche;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client_MdR {
 
	public static final int port = 5558;
 		
	public static void main(String[] args) {
		try{
			
			
			Socket service = new Socket("localhost",port);
			DataInputStream dis = new DataInputStream(service.getInputStream());
			DataOutputStream dos = new DataOutputStream(service.getOutputStream());
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Entrez le mot a rechercher...");
			String mot = sc.nextLine();
			
			String path = System.getProperty("user.dir");
			System.out.println("PATH= "+path);
			
			//Envoie de la requete au serveur
			dos.writeByte(mot.length());
			dos.writeChars(mot);
			
			dos.writeByte(path.length());
			dos.writeChars(path);
			
		    int nbFichiers = dis.readInt(); // nb de fichiers trouvé
		    System.out.println("Nombre de fichiers trouves : "+nbFichiers);
		    
		    ArrayList<String> fichiers= new ArrayList<String>();
		    String nomFichiers;
		    
		    for(int j =0;j < nbFichiers;j++){
		    	
		    	nomFichiers ="";
		    	int nomLength = dis.readInt();
		    	for(int i = 0; i< nomLength; i++){
		    		nomFichiers = nomFichiers + dis.readChar();
		    		
		    	}
		    fichiers.add(nomFichiers);
		    }
		    
 		} catch (Exception e){
			System.err.println("Erreur de Socket");
		}
	}
}
		
		
		
		 
	 


