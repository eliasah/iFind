package engine.search;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SearchClient {
 
	private Search req;
	private int[] port = {30000,30001,30002};
	private Socket service;
	
	SearchClient(Search r){
		req = r;
		try {
			System.out.println("connection on port " + port[0]);
			service = new Socket("localhost",port[0]);
			System.err.println("connection succeeded on port " + port[0]);
		} catch (IOException e) {
			try {
				System.out.println("connection on port " + port[1]);
				service = new Socket("localhost",port[1]);
				System.err.println("connection succeeded on port " + port[1]);
			} catch (IOException e1) {
				try {
					System.out.println("connection on port " + port[2]);
					service = new Socket("localhost",port[2]);
					System.err.println("connection succeeded on port " + port[2]);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			// e.printStackTrace();
		}

		try {
			DataInputStream dis = new DataInputStream(service.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DataOutputStream dos = new DataOutputStream(service.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String toString() {
		return "SearchClient [port=" + Arrays.toString(port) + ", service="
				+ service + "]";
	}
}

class SearchClienTest{
		
	public static void main(String[] args) {
			Search req = new Search(1);
			SearchClient sc = new SearchClient(req);
			System.out.println(req.toString());
			System.out.println(sc.toString());
			
	}
}