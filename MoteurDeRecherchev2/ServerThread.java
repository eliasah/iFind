package MoteurDeRecherche;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerThread extends Thread {
	
	Socket s;
	
	public ServerThread(Socket socket){
		this.s=socket;
	}
	
	public void run(){
		try {
			DataInputStream inputstream = new DataInputStream(s.getInputStream());
			DataOutputStream outputstream = new DataOutputStream(s.getOutputStream());
			
		//	while(s.isConnected()){
			//	int longeur = inputstream.readByte();
				//byte[] data = new byte[5];
				//inputstream.read(data);
				//System.out.println(data.toString());
			
			
				Scanner sc = new Scanner(System.in);
				System.out.println("Entrez le mot a rechercher...");
				String mot = sc.nextLine();
				
				ArrayList<File> filesfound = this.findFiles("/Users/jeremierouach/Documents", mot);
				int taille = filesfound.size();
				
				if(taille == 0) {
					System.out.println("\nLa recherche est infructeuse. ");

					
				// erreur  : Broken pipe 
					//outputstream.writeInt(taille); //a modifier vu que ca casse la socket
					
					/*for(int i = 0; i<filesfound.size();i++){
						outputstream.writeByte(filesfound.get(i).getName().length());
						outputstream.writeBytes(filesfound.get(i).getName());
					}*/
				}
				

	//	}
	}      catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<File> findFiles(String directoryPath, String mot) {
		File directory = new File(directoryPath);
		ArrayList<File> files = new ArrayList<File>();
		
		if(!directory.exists()){
			System.out.println("Le fichier ou repertoire '"+directoryPath+"' n existe pas");
		}
		
		else if(!directory.isDirectory()){
			System.out.println("Le chemin '"+directoryPath+"' correspond a un fichier et non a un repertoire");
		}
		
		else {
			File[] subfiles = directory.listFiles();
			String message = "Le repertoire '"+directoryPath+"' contient "+ subfiles.length+" fichiers.";
			String message2 = "Les fichiers contenus sont : ";
			System.out.println(message);
			System.out.println(message2);

			for(int i=0;i<subfiles.length;i++){
				System.out.println("Fichier n¡"+i+" : "+subfiles[i].getName());
			}
 			
			for(int i=0 ; i<subfiles.length; i++){
				if(subfiles[i].getName().contains(mot)){
					files.add(subfiles[i]);
					System.out.println("\nFichier trouve : "+subfiles[i].getName());
				} 
			}
 
			
		}
		return files;
	}
}
