package database.proxy;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import database.trigram.Trigram;

import engine.crawler.FileListener;
import engine.search.Search;

/**
 * PostgreSQL Database
 * 
 * @author Abou Haydar Elias - Univ. Paris Denis Diderot
 *
 */
class PgSQL_DB implements Database {

	private Connection conn;
	private Statement st;
	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement update;
	private String querysql;
	private ResultSet rs;
	private boolean connected;

	protected PgSQL_DB(String login, String motPasse) {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/ifind",login,motPasse);
			st = conn.createStatement();
			connected = true;
		} catch (SQLException | ClassNotFoundException e) {
			connected = false; 
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

	public ResultSet query(String s) throws SQLException{
		return st.executeQuery("SELECT * FROM t_index,t_metadata WHERE trg_id = '"+ s + "' and t_index.meta_id = t_metadata.meta_id;"); 
	}

	public ResultSet queryTrg(Trigram t) throws SQLException{
		ArrayList<String> tab = new ArrayList<String>();
		tab = t.toArrayList();
		
		String req = "SELECT * from t_index,t_metadate WHERE trg_id = ";
		
		// TODO cut array list to fit query
		
		return st.executeQuery(req);
		
	}
	
	@Override
	public void request(Search s) {
		Trigram trg = new Trigram(s.getWord());
		System.out.println(trg.toString());
		
		
	}

	@Override
	public void resetDatabase() {
		// TODO Auto-generated method stub
		
	}
	
/**	public void resetDatabase()  
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

	}  **/
}
