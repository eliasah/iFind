package database.proxy;


import java.sql.*;
import java.util.Scanner;

import database.trigram.Trigram;

import engine.search.Search;

public class Proxy_PgSQL implements Database {

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
		return db.isconnected();
	}

	@Override
	public void request(Search s){
		db.request(s);
	}

	@Override
	public void resetDatabase() {
		db.resetDatabase();		
	}

	@Override
	public ResultSet queryTrg(Trigram t) throws SQLException {
		return db.queryTrg(t);
	}

	@Override
	public void insert(String s) {
		db.insert(s);
	}

	@Override
	public PreparedStatement prepareStatement(String sql){
		return db.prepareStatement(sql);
	}

	@Override
	public Statement createStatement(int t, int r) {
		return db.createStatement(t, r);
	}

	

}