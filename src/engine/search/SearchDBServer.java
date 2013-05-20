package engine.search;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/** Lance le serveur, qui lance des sous thread pour ecouter 3 ports: 30000 30001 et 30002
 * 
 * @author ahl
 *
 */
public class SearchDBServer {

	private int[] ports;
	
	public SearchDBServer(int[] p){
		ports=p;
	}
	
	public void start(){

		for (int i = 0; i < ports.length; i++) {
			new DBServerConnectionListener(ports[i]).run();
		}
	}
	
	public static void main(String[] args) {
		int [] ports = {30000,30001,30002};
		SearchDBServer server = new SearchDBServer(ports);
		server.start();
	}

}
