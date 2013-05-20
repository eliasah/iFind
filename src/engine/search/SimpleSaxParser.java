package engine.search;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Cette classe est livree telle quelle.
 * @author smeric
 * @version 1.0
 */
public class SimpleSaxParser {

    //Simple parseur qui utilise le SimpleContentHandler
	//Tout se fait dans le contentHandler, ceci ne sert qu'a le lancer
	SimpleContentHandler myHandler;
	
	public SimpleSaxParser(String uri) throws SAXException, IOException, ParserConfigurationException {
		
		XMLReader saxReader = XMLReaderFactory.createXMLReader();
		myHandler = new SimpleContentHandler();
		saxReader.setContentHandler(myHandler);
		saxReader.parse(uri);
		
	}
}