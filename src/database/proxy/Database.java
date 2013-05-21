package database.proxy;

import java.sql.*;
import java.util.Scanner;

import database.trigram.Trigram;

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
	
	boolean isconnected();
	
	ResultSet request(Search s);
	
	void resetDatabase();

	void delete(BaliseSuppressions b) throws SQLException;
	
	PreparedStatement prepareStatement(String sql);
	
	Statement createStatement(int t,int r);

	void rename(BaliseRenommage b) throws SQLException;

	void suppressionTable(String table) throws SQLException;

	String getNameFromPath(String path);

	ResultFile FromResultSetToResultFile();

	ResultSet queryTrg(Search s) throws SQLException;
	
	void update(BaliseModifications b) throws SQLException;

	void insert(BaliseCreations b);
	
	void setBaliseIndexation(BaliseIndexation b);
}

