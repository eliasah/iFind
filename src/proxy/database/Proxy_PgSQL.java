package proxy.database;


import java.sql.*;
import java.util.Scanner;

class Proxy_PgSQL implements Database {

	private String line;
	private PgSQL_DB db;

	@Override
	public boolean connect2DB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disconnect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void request() {
		// TODO Auto-generated method stub

	}

	@Override
	public String readPassword(String prompt) {
		// TODO Auto-generated method stub
		return null;
	}

}