package engine.search;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Proxy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import database.proxy.Database;
import database.proxy.Proxy_PgSQL;

public class Test {


	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		Database db = new Proxy_PgSQL("abou", "x55efviq");
		Search s = new Search(1, "exem", false, null, "654", "txt", null); // id , word , content , path , permission ,ext , timeslot
		ResultSet rs = db.request(s);
		Result res = new Result(1);
		try {
			while (rs.next()) { 
				String filename = rs.getString(1);
				ResultFile rf = new ResultFile();
				rf.setPath(filename);
				res.getFiles().add(rf);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(res.ConvertToXml());
	}

}
