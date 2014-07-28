package database.proxy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import database.trigram.Trigram;
import engine.search.BaliseCreations;
import engine.search.BaliseIndexation;
import engine.search.BaliseModifications;
import engine.search.BaliseRenommage;
import engine.search.BaliseSuppressions;
import engine.search.Mot;
import engine.search.ResultFile;
import engine.search.Search;

/**
 * PostgreSQL Database
 * 
 * @author Abou Haydar Elias - Univ. Paris Denis Diderot
 * 
 */
class PgSQL_DB implements Database {
	private BaliseIndexation balise;
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
			conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost/ifind", login, motPasse);
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

	@Override
	public void suppressionTable(String table) throws SQLException {
		querysql = "DROP TABLE" + table;
		st.executeUpdate(querysql);
		System.out.println("Suppression " + table + " reussi");
	}

	@Override
	public void insert(BaliseCreations b) {
		String s, trg;
		Trigram t;
		ArrayList<String> tab;
		ArrayList<Mot> mot = b.getIndexage();
		Iterator itm = mot.iterator();
		Iterator it;

		querysql = "INSERT INTO t_metadata VALUES ('" + b.getPath() + "','"
				+ b.getFormat() + "','" + b.getPermission() + ','
				+ b.getDatecreation() + "');";

		try {
			insert = conn.prepareStatement(querysql,
					Statement.RETURN_GENERATED_KEYS);
			insert.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		while (itm.hasNext()) {
			s = (String) itm.next();
			t = new Trigram(s);
			tab = t.toArrayList();
			it = tab.iterator();
			while (it.hasNext()) {
				trg = (String) it.next();
				try {
					querysql = "INSERT INTO T_INDEX VALUES('" + trg + "','"
							+ b.getPath() + "')";
					insert = conn.prepareStatement(querysql);
					insert.execute();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}

	@Override
	public ResultSet queryTrg(Search s) throws SQLException {
		Trigram t = new Trigram(s.getWord());
		ArrayList<String> tab = new ArrayList<String>();
		tab = t.toArrayList();

		String req = "SELECT filepath from t_index,t_metadata WHERE (";

		Iterator<String> itr = tab.iterator();
		int cpt = 0;
		while (itr.hasNext()) {
			if (cpt > 0)
				req += " OR ";
			req += "trg_id = '" + itr.next() + "'";
			cpt++;
		}
		req += ")";
		if (s.getPerm() != null)
			req += " AND t_metadata.permission = '" + s.getPerm() + "'";
		if (s.getExtension() != null)
			req += " AND t_metadata.extension = '" + s.getExtension() + "'";
		req += " AND t_index.meta_id = t_metadata.filepath GROUP BY filepath;";
		System.out.println(req);
		return st.executeQuery(req);

	}

	@Override
	public String getNameFromPath(String path) {
		StringTokenizer tokens = new StringTokenizer(path, "/");
		String token = "";
		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
		}

		return token;
	}

	@Override
	public ResultSet request(Search s) {
		ResultSet res = null;
		try {
			res = queryTrg(s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public ResultFile FromResultSetToResultFile() {
		// TODO
		return null;
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
	public void delete(BaliseSuppressions b) throws SQLException {
		PreparedStatement st = conn
				.prepareStatement("DELETE FROM t_metadata WHERE filepath = ?");
		st.setString(1, b.getPath());
		int rowsDeleted = st.executeUpdate();
		System.out.println(rowsDeleted + " rows deleted");
	}

	@Override
	public void rename(BaliseRenommage b) throws SQLException {
		String opath = b.getPath();
		String npath = b.getNewpath();
		PreparedStatement st = conn
				.prepareStatement("UPDATE t_metadata SET filepath = ? where filepath = ?;");
		st.setString(1, npath);
		st.setString(2, opath);
		int rowsUpdated = st.executeUpdate();
		System.out.println(rowsUpdated + "rows updated");
	}

	@Override
	public void update(BaliseModifications b) throws SQLException {
		b.getPath();
		b.getNewpath();
		b.getDatemodification();
		b.getPermissions();
		// TODO URGENT!!!
		System.out.println("NOT YET IMPLEMENT");
	}

	@Override
	public void setBaliseIndexation(BaliseIndexation b) {
		ArrayList<BaliseCreations> listbc = b.getCreation();
		ArrayList<BaliseSuppressions> listbs = b.getSuppression();
		ArrayList<BaliseModifications> listbm = b.getModification();
		ArrayList<BaliseRenommage> listbr = b.getRenommage();
		Iterator it;

		if (listbc != null) {
			BaliseCreations bc;
			it = listbc.iterator();
			while (it.hasNext()) {
				bc = (BaliseCreations) it.next();
				insert(bc);
			}
		}

		if (listbc != null) {
			BaliseCreations bc;
			it = listbc.iterator();
			while (it.hasNext()) {
				bc = (BaliseCreations) it.next();
				insert(bc);
			}
		}

		if (listbs != null) {
			BaliseSuppressions bs;
			it = listbs.iterator();
			while (it.hasNext()) {
				bs = (BaliseSuppressions) it.next();
				try {
					delete(bs);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (listbm != null) {
			BaliseModifications bm;
			it = listbc.iterator();
			while (it.hasNext()) {
				bm = (BaliseModifications) it.next();
				try {
					update(bm);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (listbr != null) {
			BaliseRenommage br;
			it = listbc.iterator();
			while (it.hasNext()) {
				br = (BaliseRenommage) it.next();
				try {
					rename(br);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
