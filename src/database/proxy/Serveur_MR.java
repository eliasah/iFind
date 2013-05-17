package database.proxy;

import java.io.IOException;
import java.net.*;

import engine.search.ServerThread;

public class Serveur_MR {

	ServerSocket ss;
	// TODO Ce port ne doit pas etre en final. Il faut regarder config/config-sample-MoteurR.xml pour configurer
	int port; 
	
	public Serveur_MR(int p){
		try {
			System.out.println("ServerSocket");
			this.ss = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Server Socket failed to bind on port " + port);
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
			System.err.println("Connection pas établie");
			// e.printStackTrace();
		}
	}
}
