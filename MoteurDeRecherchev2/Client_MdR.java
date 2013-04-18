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
			
			DataOutputStream dos = new DataOutputStream(service.getOutputStream());
		//	System.out.println("Test 0");
			
			dos.writeByte(5);
			dos.writeBytes("Hello"); // le nom du mot recherché 
			dos.flush();
			
			
			
			
		//	System.out.println("Test 1");
			
		//	int len = dis.readInt(); // a ce moment : erreur de socket
			
			int len = 4;

		//	System.out.println("Test 2"); // dont wok
			
			byte[][] bufDebuf = new byte[len][];
			
			//for(int i =0;i<len;i++){
				
			
			//	int leng = dis.readByte();
				int leng = 3;
		//		System.out.println("Test 3");// d w
			//	bufDebuf[i] = new byte[leng];
				
		//		for(int j=0;i<bufDebuf[i].length;j++){
				//	System.out.print(bufDebuf[i][j]+" "); // affiche 0 0 0 // 3 fois vu qu on fixe la taille a 3
			//	}
			//	System.out.println("Test 4");// d w
		//	}
				
 		} catch (Exception e){
			System.err.println("Erreur de Socket");
		}
 		
		
		
	}
	 
}
		
		
		
		 
	 


