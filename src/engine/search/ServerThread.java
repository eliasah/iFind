package engine.search;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.xml.sax.SAXException;

import database.proxy.Database;
import database.proxy.Proxy_PgSQL;

/**
 * 
 * @author Ahl Mikael, Jeremie Rouach - Univ. Paris Denis Diderot
 * 
 */

public class ServerThread extends Thread {
	private Socket s;
	private InputStreamReader in;
	private OutputStreamWriter out;
	
	public ServerThread(Socket socket){
		this.s=socket;
	}

	public void run(){
		
		try {
			while(true){
			
				in = new InputStreamReader(s.getInputStream());
				String SearchXml="";
				char[] cbuf= new char[10];
				while( -1 !=in.read(cbuf)){
					SearchXml += cbuf; 
				}
				URI uri =URI.create(SearchXml);
				try {
					SimpleSaxParser s = new SimpleSaxParser(uri.getPath());
				
				
				
					Search search = s.getHandler().getSearch();
					
					Database db = new Proxy_PgSQL("abou", "x55efviq");
					db.request(search);
					
					
					//TODO Construire l'objet result en demandant le resultat a la BDD en utilisant search 
					Result result= new Result(1);
				
				
					out = new OutputStreamWriter(this.s.getOutputStream());
					out.write(result.ConvertToXml());
					out.flush();
			
				} catch (SAXException e) {
					e.printStackTrace();
				}
			
			}
		} catch (IOException e) {
			System.out.println("nothing in the input");
		}
	}
}
