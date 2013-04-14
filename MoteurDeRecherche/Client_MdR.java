package MoteurDeRecherche;

import java.io.DataInputStream;
<<<<<<< HEAD

=======
>>>>>>> fb9e76bf1e029cf58e7988475d435016036ff118
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client_MdR {

 	
 
	public static final int port = 22678;
	
	public static void main(String[] args) {
		try{
			
			Socket service = new Socket("localhost",port);
			
			DataInputStream dis = new DataInputStream(service.getInputStream());
			
			DataOutputStream dos = new DataOutputStream(service.getOutputStream());
			
			dos.writeByte(5);
			dos.writeBytes("Hello");
			dos.flush();
			
			int nbWord = dis.readByte();
            
            System.out.println("Read byte");
			
			byte[][] bufDebuf = new byte[nbWord][];
			for(int i =0;i<nbWord;i++){
				
			
				int len = dis.readByte();
				bufDebuf[i] = new byte[len];
				
				for(int j=0;i<bufDebuf[i].length;j++){
					System.out.print(bufDebuf[i][j]+" ");
				}
			
			}
				
 		} catch (Exception e){
			System.err.println("Erreur de Socket");
		}
 		
		
		
	}
	 
}
		
		
		
		 
	 


