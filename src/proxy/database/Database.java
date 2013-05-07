package proxy.database;

import java.sql.*;
import java.util.Scanner;

public interface Database {

	boolean connect2DB();
	boolean disconnect();
	void request();
	String readPassword(String prompt);
}
