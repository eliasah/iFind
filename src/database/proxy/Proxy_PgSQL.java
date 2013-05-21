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
/** Proxy class for PgSQL_DB
 * 
 * @author Abou Haydar Elias - Univ. Paris Denis Diderot
 *
 */
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
	public ResultSet request(Search s){
		return db.request(s);
	}

	@Override
	public void resetDatabase() {
		db.resetDatabase();		
	}

	@Override
	public void insert(BaliseCreations b){
		db.insert(b);
	}

	@Override
	public PreparedStatement prepareStatement(String sql){
		return db.prepareStatement(sql);
	}

	@Override
	public Statement createStatement(int t, int r) {
		return db.createStatement(t, r);
	}

	@Override
	public void rename(BaliseRenommage b) throws SQLException {
		db.rename(b);		
	}

	@Override
	public void delete(BaliseSuppressions b) throws SQLException {
		db.delete(b);
	}

	@Override
	public void suppressionTable(String table) throws SQLException {
		db.suppressionTable(table);
	}

	@Override
	public String getNameFromPath(String path) {
		return db.getNameFromPath(path);
	}

	@Override
	public ResultFile FromResultSetToResultFile() {
		return db.FromResultSetToResultFile();
	}

	@Override
	public ResultSet queryTrg(Search s) throws SQLException {
		return db.queryTrg(s);
	}

	@Override
	public void update(BaliseModifications b) throws SQLException {
		db.update(b);
	}

	@Override
	public void setBaliseIndexation(BaliseIndexation b) {
		db.setBaliseIndexation(b);
	}

	

}