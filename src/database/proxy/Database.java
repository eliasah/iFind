package database.proxy;

import java.sql.*;
import java.util.Scanner;

import engine.search.Search;

/**
 * Interface Database qui définit les actions de connections vers la BD
 * @author Abou Haydar Elias - Univ. Paris Denis Diderot
 *
 */
public interface Database {

	void close() throws SQLException;
	
	void createDatabase();
	
	boolean isconnected();
	
	void request(Search s);
	
	void resetDatabase();
	
}

