package proxy.database;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import crawler.FileListener;

class PgSQL_DB implements Database {

	Connection conn; // la connexion a la base
	Statement st;
	PreparedStatement insert;
	PreparedStatement delete;
	PreparedStatement update;
	String querysql;
	ResultSet rs;
	boolean connected;

	protected PgSQL_DB(String login, String motPasse) {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/ifind",login,motPasse);
			st = conn.createStatement();
			connected = true;
		} catch (SQLException | ClassNotFoundException e) {
			connected = false; 
			// e.printStackTrace();
		} // Connexion UBUNTU
	}

	@Override
	public boolean isconnected() {
		return connected;
	}

	@Override
	public void close() throws SQLException{ 
		st.close();
		conn.close();
	}

	public void createDatabase() {
		System.out.println("Creating Database");
		
		ArrayList<String> req = new ArrayList();
		
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new FileReader("config/indices.sql"));
		} catch (FileNotFoundException e1) {
			System.out.println("DB config file not found");
			e1.printStackTrace();
		}
		try {
			while ((line = br.readLine()) != null) {
				req.add(line);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Iterator it = req.iterator();
		while (it.hasNext()){
			querysql = (String) it.next();
			System.out.println(querysql);
			try {
				st.executeUpdate(querysql);
				System.out.println("Creation reussi");

			} catch (SQLException e) {
				System.out.println("createDB Error : SQLException");
				e.printStackTrace();
			}
		}
	}	 

	public void suppressionTable(String table) throws SQLException {
		querysql = "DROP TABLE"+table;
		st.executeUpdate(querysql);
		System.out.println("Suppression "+table+" reussi");
	}

	public void insertionTuplesPredefinis() throws SQLException{
		querysql="INSERT...";
		insert = conn.prepareStatement(querysql);
		insert.execute();
		System.out.println("Insertion reussi");
	}

	public ResultSet query(int a) throws SQLException{
		if (a == 1) rs = st.executeQuery("SELECT * FROM douane;"); 
		else rs =st.executeQuery("SELECT * FROM produit;");    	
		return rs;
	}

	public void insertionTuplesUtilisateur(int num,String nom,String ville,int etoiles,String directeur) throws SQLException{
		Scanner src = new Scanner(System.in);
		System.out.println("Entrez le numero de l Hotel:");
		num = src.nextInt();
	}
	@Override
	public void request() {
		// TODO Auto-generated method stub

	}

	public void resetDatabase()  
	{  
		String s = new String();  
		StringBuffer sb = new StringBuffer();  

		try  
		{  
			FileReader fr = new FileReader(new File("config/indices.sql"));  
			// be sure to not have line starting with "--" or "/*" or any other non aplhabetical character  

			BufferedReader br = new BufferedReader(fr);  

			while((s = br.readLine()) != null) {  
				sb.append(s);  
			}  
			br.close();  

			// here is our splitter ! We use ";" as a delimiter for each request  
			// then we are sure to have well formed statements  
			String[] inst = sb.toString().split(";");  

			Connection c = ((Statement) conn).getConnection();  
			Statement st = c.createStatement();  

			for(int i = 0; i<inst.length; i++)  
			{  
				// we ensure that there is no spaces before or after the request string  
				// in order to not execute empty statements  
				if(!inst[i].trim().equals(""))  
				{  
					st.executeUpdate(inst[i]);  
					System.out.println(">>"+inst[i]);  
				}  
			}  

		}  
		catch(Exception e)  
		{  
			System.out.println("*** Error : "+e.toString());  
			System.out.println("*** ");  
			System.out.println("*** Error : ");  
			e.printStackTrace();  
			System.out.println("################################################");  
			System.out.println(sb.toString());  
		}  

	}  
}
