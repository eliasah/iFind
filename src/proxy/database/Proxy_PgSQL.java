package proxy.database;


import java.sql.*;
import java.util.Scanner;

class Proxy_PgSQL implements Database {

	private String line;
	private PgSQL_DB db;
	
	public Proxy_PgSQL(String id,String p) {
			db = new PgSQL_DB(id,p);
	}

	@Override
	public void request(){
		// TODO Auto-generated method stub
	}

	@Override
	public void close() throws SQLException {
		db.close();
	}

	@Override
	public boolean isconnected() {
		return db.connected;
	}

	@Override
	public void createDatabase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetDatabase() {
		// TODO Auto-generated method stub
		
	}
}