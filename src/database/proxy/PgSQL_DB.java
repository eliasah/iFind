package database.proxy;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import database.trigram.Trigram;

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
	private String querysql;
	private ResultSet rs;
	private PreparedStatement insert;
	private boolean connected;
	private int id_meta;
	
	protected PgSQL_DB(String login, String motPasse) {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/ifind", login, motPasse);
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
	public void close() throws SQLException {
		st.close();
		conn.close();
	}

	@Override
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
		while (it.hasNext()) {
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
		querysql = "DROP TABLE" + table;
		st.executeUpdate(querysql);
		System.out.println("Suppression " + table + " reussi");
	}
	
	public int getIdMeta(){
		int i = 0;
		
		return i;
	}
	
	@Override
	public void insert(String mot) {
		Trigram t = new Trigram(mot);
		ArrayList<String> tab = t.toArrayList();
		Iterator it = tab.iterator();
		String trg;
		querysql = "INSERT INTO t_metadata VALUES (DEFAULT,'fichier 1','path 1',777,'1999-01-08');";
		ResultSet rs = null;
		try {
			insert = conn.prepareStatement(querysql,Statement.RETURN_GENERATED_KEYS);
			insert.execute();
		} catch (SQLException e1) {
			String errmsg = "ERROR:  duplicate key value violates unique constraint 't_metadata_pkey' \n" +
					"DETAIL:  Key (meta_id)=( X ) already exists.";
			System.out.println(errmsg);
		}	

		try {
			rs = insert.getResultSet();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		try {
			i = rs.getInt(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(i);
		/*while (it.hasNext()) {
			trg = (String) it.next();
			try {
				querysql = "INSERT INTO T_INDEX VALUES('" + trg + "'," + id_meta +");";
				insert = conn.prepareStatement(querysql);
				insert.execute();

			} catch (SQLException e) {
				String errmsg = "ERROR:  duplicate key value violates unique constraint 't_index_pkey' \n " +
						"DETAIL:  Key (trg_id, meta_id)=("+trg+","+ id_meta+") already exists.";
				System.out.println(errmsg);
			}
		}*/
	}
	
	
	public ResultSet query(String s) throws SQLException {
		return st.executeQuery("SELECT * FROM t_index,t_metadata WHERE trg_id = '"+ s + "' and t_index.meta_id = t_metadata.meta_id;");
	}

	@Override
	public ResultSet queryTrg(Trigram t) throws SQLException {
		ArrayList<String> tab = new ArrayList<String>();
		tab = t.toArrayList();

		String req = "SELECT * from t_index,t_metadata WHERE ";

		Iterator<String> itr = tab.iterator();
		int cpt = 0;
		while (itr.hasNext()) {
			if (cpt > 0)
				req += " AND ";
			req += "trg_id = '" + itr.next() + "'";
			cpt++;
		}
		req += " AND t_index.meta_id = t_metadata.meta_id;";
		System.out.println(req);
		return st.executeQuery(req);

	}

	@Override
	public ResultSet request(Search s) {
		Trigram trg = new Trigram(s.getWord());
		ResultSet res = null;
		try {
			res = queryTrg(trg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void resetDatabase() {
		createDatabase();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

	@Override
	public Statement createStatement(int t, int r) {
		Statement s = null;
		try {
			s = conn.createStatement(t, r);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	@Override
	public void delete(String path) throws SQLException {
		PreparedStatement st = conn.prepareStatement("DELETE FROM t_metadata WHERE filepath = ?");
		st.setString(1, path);
		int rowsDeleted = st.executeUpdate();
		System.out.println(rowsDeleted + " rows deleted");
	}

	@Override
	public void update(String opath, String npath) throws SQLException {
		PreparedStatement st = conn.prepareStatement("UPDATE t_metadata SET filepath = ? where filepath = ?;");
		st.setString(1,npath);
		st.setString(2,opath);
		int rowsUpdated = st.executeUpdate();
		System.out.println(rowsUpdated + "rows updated");
	}
	
}
