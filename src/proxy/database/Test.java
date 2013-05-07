package proxy.database;

import java.io.Console;
import java.sql.SQLException;

public class Test {
	public static void main(String[] args) {
		System.out.println("START");
		Console cons;
		char[] pass;
		try {
			cons = System.console();
			pass = cons.readPassword("[%s]", "Password:");
			PgSQL_DB conn= new PgSQL_DB("abou", pass.toString()); // IDENTIFIANT UBUNTU
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("END");
	}
}