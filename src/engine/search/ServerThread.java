package engine.search;

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
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			String mot="";
			String path="";

			//On recois le mot
			int motLength = dis.readByte();
			for(int i = 0; i< motLength; i++){
				mot = mot + dis.readChar();
			}

			//On recois le path
			int pathLength = dis.readByte();
			for(int i = 0; i< pathLength; i++){
				path = path + dis.readChar();
			}

			//On cherche avec les 2
			ArrayList<File> filesfound = this.findFiles(path, mot);
			int taille = filesfound.size();

			//On envoie le nombre de fichiers trouv� puis on envoie tous les noms
			dos.writeInt(taille);


			for(int i=0 ; i< filesfound.size();i++){
				dos.writeInt(filesfound.get(i).getName().length());
				dos.writeChars(filesfound.get(i).getName());
				System.out.println("Sent: "+filesfound.get(i).getName());
			}
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
				System.out.println("Fichier n�"+i+" : "+subfiles[i].getName());
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
