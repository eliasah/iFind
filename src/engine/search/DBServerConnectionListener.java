package engine.search;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 
 * @author Ahl Mikael
 *
 */
public class DBServerConnectionListener extends Thread{
	
	private int port;
	private ServerSocket ss;
	
	public DBServerConnectionListener(int p){
		port = p;
		try {
			ss = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			try {
				Socket socket = ss.accept();
				new ServerThread(socket).run();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
