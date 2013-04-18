package MoteurDeRecherche;

import java.io.DataInputStream;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client_MdR {
 
	public static final int port = 5558;
 		
	public static void main(String[] args) {
		try{
			
			
			Socket service = new Socket("localhost",port);
			DataInputStream dis = new DataInputStream(service.getInputStream());

			
			// RECEPTION DE LA DONNEE
			
		    int len = dis.readInt(); // du mot/fichier trouve..
		    System.out.println("Longueur a recevoir : "+len);
		    
		    byte[] data = new byte[len];
		    if (len > 0) {
		        dis.readFully(data);
		    }
		    
	 
		    
		    for(int i=0;i<len;i++){
		    	System.out.print(data[i]+" ");
		    } 
		  
		    //DataOutputStream dos = new DataOutputStream(service.getOutputStream());
 
				
 		} catch (Exception e){
			System.err.println("Erreur de Socket");
		}
 		
		
		
	}
	 
}
		
		
		
		 
	 


