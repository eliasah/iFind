package database.proxy;


import java.sql.*;
import java.util.Scanner;

class Proxy_PgSQL implements Database {

	private PgSQL_DB db;
	private String line;
	
	/**
	 * @param id
	 * @param p
	 */
	public Proxy_PgSQL(String id,String p) {
        db = new PgSQL_DB(id,p);
	}

	@Override
	public void close() throws SQLException {
		db.close();
	}

	@Override
	public void createDatabase() {
		db.createDatabase();
	}

	@Override
	public boolean isconnected() {
		return db.connected;
	}

	@Override
	public void request(){
		// TODO Auto-generated method stub
	}

	@Override
	public void resetDatabase() {
		db.resetDatabase();		
	}
}