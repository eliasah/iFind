package proxy.database;

import java.sql.*;
import java.util.Scanner;

public interface Database {

	void close() throws SQLException;
	void request();
	boolean isconnected();
	void createDB();
}
