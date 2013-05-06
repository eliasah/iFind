package proxy.database;

import java.sql.*;
import java.util.Scanner;

public interface Database {

	boolean connect2DB();
	boolean disconnect();
	void request();
	String readPassword(String prompt);
}

class PgSQL_DB implements Database {
	
	Connection conn; // la connexion a la base
	Statement st;
	PreparedStatement insert;
	PreparedStatement delete;
	PreparedStatement update;
	String querysql;
	ResultSet rs;
	
	// connection a la base
	public PgSQL_DB(String login, String motPasse) throws SQLException, ClassNotFoundException{
		// -------------------
		// Connexion a la base
		// --------------------
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://localhost/" + login,login,motPasse);
		st = conn.createStatement();
	}

	// fermeture de la connection
	public void close() throws SQLException{ 
		st.close();
		conn.close();
	}

	public  void creationTable() throws SQLException {
		querysql = "";
		st.executeUpdate(querysql);
		System.out.println("Creation reussi");
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
	public boolean connect2DB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disconnect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void request() {
		// TODO Auto-generated method stub

	}

	@Override
	public String readPassword (String prompt) {
		String password = "";
		try {
			password = new String(System.console().readPassword("%s", prompt));
		}
		catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return password;
	}
}

class Proxy_PgSQL implements Database {

	private String line;
	private PgSQL_DB db;

	@Override
	public boolean connect2DB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disconnect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void request() {
		// TODO Auto-generated method stub

	}

	@Override
	public String readPassword(String prompt) {
		// TODO Auto-generated method stub
		return null;
	}

}
