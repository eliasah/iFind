package MoteurDeRecherche;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread {
	
	Socket s;
	
	public ServerThread(Socket socket){
		this.s=socket;
	}
	
	public void run(){
		try {
			DataInputStream inputstream = new DataInputStream(s.getInputStream());
			DataOutputStream outputstream = new DataOutputStream(s.getOutputStream());
			
			while(s.isConnected()){
				int longeur = inputstream.readByte();
				byte[] data = new byte[longeur];
				inputstream.read(data);
				
				ArrayList<File> filesfound = this.findFiles("/Users/ahl/Downloads", data.toString());
				
				outputstream.writeByte(filesfound.size());
				for(int i = 0; i<filesfound.size();i++){
					outputstream.writeByte(filesfound.get(i).getName().length());
					outputstream.writeBytes(filesfound.get(i).getName());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<File> findFiles(String directoryPath, String mot) {
		File directory = new File(directoryPath);
		ArrayList<File> files = new ArrayList<File>();
		
		if(!directory.exists()){
			System.out.println("Le fichier/rŽpertoire '"+directoryPath+"' n'existe pas");
		}
		
		else if(!directory.isDirectory()){
			System.out.println("Le chemin '"+directoryPath+"' correspond ˆ un fichier et non ˆ un rŽpertoire");
		}
		
		else {
			File[] subfiles = directory.listFiles();
			String message = "Le rŽpertoire '"+directoryPath+"' contient "+ subfiles.length+" fichier"+(subfiles.length>1?"s":"");
			System.out.println(message);
			
			for(int i=0 ; i<subfiles.length; i++){
				if(subfiles[i].getName().contains(mot)){
					files.add(subfiles[i]);
				}
			}
		}
		return files;
	}
}
