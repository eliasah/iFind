package proxy.database;

import java.sql.SQLException;

public class Test {
	public static void main(String[] args) {
		System.out.println("START");
		try {
			PgSQL_DB conn= new PgSQL_DB("postgres", "x55efviq");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("END");
	}
}