package engine.search;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.xml.sax.SAXException;
/**
 * 
 * @author ahl
 *
 */
public class SearchClient {
 
	private Search req;
	private int[] port = {30000,30001,30002};
	private Socket socket;
	private OutputStreamWriter out;
	private InputStreamReader in;
	
	public SearchClient(Search r){
		req = r;
		try {
			System.out.println("connection on port " + port[0]);
			socket = new Socket("localhost",port[0]);
			System.err.println("connection succeeded on port " + port[0]);
			this.Demande();
			Result result = this.EcouteReponse();
			
		} catch (IOException e) {
			try {
				System.out.println("connection on port " + port[1]);
				socket = new Socket("localhost",port[1]);
				System.err.println("connection succeeded on port " + port[1]);
				this.Demande();
				Result result = this.EcouteReponse();
				
			} catch (IOException e1) {
				try {
					System.out.println("connection on port " + port[2]);
					socket = new Socket("localhost",port[2]);
					System.err.println("connection succeeded on port " + port[2]);
					this.Demande();
					Result result = this.EcouteReponse();
					
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			// e.printStackTrace();
		}
	}
	
	public void Demande(){
		//Envoie la demande avec la requete req de l'objet
		try {
			out = new OutputStreamWriter(socket.getOutputStream());
			out.write(req.ConvertToXml());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Result EcouteReponse(){
		//Recoit la réponse du serveur, la recoit et transforme l'xml recu en un objet Result
		Result result = null;
		try {
			in = new InputStreamReader(socket.getInputStream());
			char[] cbuf = new char[10];
			String ResultXml="";
			while( -1 !=in.read(cbuf)){
				ResultXml += cbuf; 
			}
			URI uri = URI.create(ResultXml);
			try {
				SimpleSaxParser parser = new SimpleSaxParser(uri.getPath());
				result = parser.getHandler().getResult();
			} catch (SAXException e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		return "SearchClient [port=" + Arrays.toString(port) + ", service="
				+ socket + "]";
	}
}