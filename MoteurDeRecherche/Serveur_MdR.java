package MoteurDeRecherche;

import java.io.IOException;
import java.net.*;

public class Serveur_MdR {

	ServerSocket ss;
	static final int PORT = 5558;
	
	public Serveur_MdR(){
		try {
			this.ss = new ServerSocket(PORT);
		} catch (IOException e) {
			System.err.println("Server Socket failed to bind on port "+PORT);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void start(){
		try {
			while (true)
			{
				Socket s = ss.accept();
				new ServerThread(s).run();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Serveur_MdR s= new Serveur_MdR();
		s.start();
	}
}
