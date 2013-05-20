package engine.search;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Test {


	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		Result r = new Result(10);
		ResultFile rf1 = new ResultFile("mon nom", "/user/MonNom", "chmod","10 MB",null,null );
		r.files.add(rf1);
		ResultFile rf2 = new ResultFile("toto", "/user/toto", "chmod","1024 Octets","1/1/1","toto" );
		r.files.add(rf2);
		
		System.out.println(r.ConvertToXml());
		File f = new File("test");
		FileWriter fw = new FileWriter(f);
		fw.write(r.ConvertToXml());
		fw.flush();
		SimpleSaxParser s = new SimpleSaxParser(f.getPath());
		
		Result r2 = s.myHandler.getResult();
		
		System.out.println(r2.files.get(0).name);
		// Pareil pour Search
	}

}
