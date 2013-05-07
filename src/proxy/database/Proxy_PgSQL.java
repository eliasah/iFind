package proxy.database;


import java.sql.*;
import java.util.Scanner;

class Proxy_PgSQL implements Database {

	private String line;
	private PgSQL_DB db;

	public Proxy_PgSQL(String id,String p) {
		try {
			db = new PgSQL_DB(id,p);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void request(){
		// TODO Auto-generated method stub
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}
}