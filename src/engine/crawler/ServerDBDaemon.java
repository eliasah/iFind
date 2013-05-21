package engine.crawler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

import org.xml.sax.SAXException;

import engine.search.BaliseIndexation;
import engine.search.SimpleSaxParser;
/**
 * 
 * @author ahl
 *
 */
public class ServerDBDaemon extends Thread{
	private ServerSocket ss;
	private Socket clientSocket;
	private int port;
	
	public ServerDBDaemon (int p){
		port = p;
	}
	public void run(){
		try {
			ss = new ServerSocket(port);
			clientSocket = ss.accept();
			
			while(true){
				InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());

				String IndexXml="";
				char[] cbuf= new char[10];
				while( -1 !=in.read(cbuf)){
					IndexXml += cbuf; 
				}
				URI uri = URI.create(IndexXml);
				try {
					SimpleSaxParser s = new SimpleSaxParser(uri.getPath());
					
					BaliseIndexation bi = null;  //  s.getHandler()  .getBaliseIndexation() ?
					
					
					
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
