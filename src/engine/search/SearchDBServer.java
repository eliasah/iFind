package engine.search;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** Lance le serveur, qui lance des sous thread pour ecouter 3 ports: 30000 30001 et 30002
 * 
 * @author Ahl Mikael
 *
 */
public class SearchDBServer extends Thread {

	private int[] ports;
	
	public SearchDBServer(int[] p){
		ports=p;
	}
	
	public void run(){
		for (int i = 0; i < ports.length; i++) {
			new DBServerConnectionListener(ports[i]).run();
		}
	}
}
