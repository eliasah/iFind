package database.proxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import engine.search.BaliseCreations;
import engine.search.BaliseIndexation;
import engine.search.BaliseModifications;
import engine.search.BaliseRenommage;
import engine.search.BaliseSuppressions;
import engine.search.ResultFile;
import engine.search.Search;

/**
 * Interface Database qui definit les actions de connections vers la BD
 * @author Abou Haydar Elias - Univ. Paris Denis Diderot
 *
 */
public interface Database {

	void close() throws SQLException;
	
	void createDatabase();
	
	Statement createStatement(int t,int r);
	
	void delete(BaliseSuppressions b) throws SQLException;
	
	ResultFile FromResultSetToResultFile();

	String getNameFromPath(String path);
	
	void insert(BaliseCreations b);
	
	boolean isconnected();

	PreparedStatement prepareStatement(String sql);

	ResultSet queryTrg(Search s) throws SQLException;

	void rename(BaliseRenommage b) throws SQLException;

	ResultSet request(Search s);

	void resetDatabase();
	
	void setBaliseIndexation(BaliseIndexation b);

	void suppressionTable(String table) throws SQLException;
	
	void update(BaliseModifications b) throws SQLException;
}

