package engine.search;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SearchDBServer {

	private ServerSocket ss;
	
	public SearchDBServer(int port){
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
	
	public static void main(String[] args) {
		SearchDBServer server = new SearchDBServer(30000);
		
		server.start();
	}

}
