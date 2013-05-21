package engine.search;

import java.io.IOException;
import java.net.URI;

import org.xml.sax.SAXException;

public class Test {

	public static void main(String[] args) {
		URI uri = URI.create("testDTD2");
		try {
			SimpleSaxParser parser = new SimpleSaxParser(uri.getPath());
			//BaliseIndexation b = parser.getHandler().getIndexation();
			//System.out.println(b.getSuppression().get(0).getPath());
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		};
	}
}
