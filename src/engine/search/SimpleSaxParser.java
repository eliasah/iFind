package engine.search;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
/**
 * 
 * @author ahl
 *
 */

public class SimpleSaxParser {

    //Simple parseur qui utilise le SimpleContentHandler
	//Tout se fait dans le contentHandler, ceci ne sert qu'a le lancer
	
	private SimpleContentHandler handler;
	
	public SimpleSaxParser(String uri) throws SAXException, IOException {
		XMLReader saxReader = XMLReaderFactory.createXMLReader();
		handler = new SimpleContentHandler();
		saxReader.setContentHandler(handler);
		saxReader.parse(uri);
	}
	
	public SimpleContentHandler getHandler(){
		return handler;
	}
}