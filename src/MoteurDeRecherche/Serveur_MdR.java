package MoteurDeRecherche;

import java.io.IOException;
import java.net.*;

public class Serveur_MdR {

	ServerSocket ss;
	// TODO Ce port ne doit pas etre en final. Il faut regarder config/config-sample-MoteurR.xml pour configurer
	static final int PORT = 3000; 
	
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
			while (true) {
				Socket s = ss.accept();
				new ServerThread(s).run();
			}
		} catch (IOException e) {
			// TODO Signaler que la connection n'a pas été établie.
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Serveur_MdR s= new Serveur_MdR();
		s.start();
	}
}
