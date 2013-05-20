package engine.search;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Ahl Mikael, Jeremie Rouach - Univ. Paris Denis Diderot
 *
 */
public class ServerThread extends Thread {
	private Socket s;
	private InputStreamReader in;
	
	public ServerThread(Socket socket){
		this.s=socket;
	}

	public void run(){
		try {
			in = new InputStreamReader(s.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("nothing in the input");
			//e.printStackTrace();
		}
	}
}
