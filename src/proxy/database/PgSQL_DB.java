package proxy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

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

	public  void createDB() {
		querysql = "CREATE TABLE t_index ( trg_id VARCHAR(3) NOT NULL, meta_id INT NOT NULL REFERENCES t_metadata(meta_id), PRIMARY KEY(trg_id, meta_id));";
		try {
			st.executeUpdate(querysql);
			System.out.println("Creation reussi");

		} catch (SQLException e) {
			System.out.println("createDB Error : SQLException");
			e.printStackTrace();
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
}
