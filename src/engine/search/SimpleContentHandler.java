package engine.search;

import java.util.Stack;

import org.xml.sax.*;
import org.xml.sax.helpers.LocatorImpl;



//Implementation pour les XML de type Search fini, il renvoie en sortie un objet Search comme attribu de la classe.
//La construction se fait au fil du parsing, donc en cas d'erreur l'objet peut contenir des attribus null
//TODO idem pour les fichiers RESULT

public class SimpleContentHandler implements ContentHandler {

	Search search=null;
	Result result = null;
	TimeSlot timeSlot;
	Stack<String> balises = new Stack<String>();
	
        public SimpleContentHandler() {
                super();
                // On definit le locator par defaut.
                locator = new LocatorImpl();
        }

        public void setDocumentLocator(Locator value) {
                locator =  value;
        }

        public void startDocument() throws SAXException {
                System.out.println("Debut du parsing");
        }

        public void endDocument() throws SAXException {
                System.out.println("Fin du parsing" );
        }

        public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException {
            //Ouverture d'une balise,on la met sur la pile   
        	if (localName.equals("SEARCH")){
        		if (attributs.getLocalName(0).equals("id")){
        			this.search = new Search(Integer.parseInt(attributs.getValue(0)));
        		}
        	}
        	
        	if (localName.equals("RESULT")){
        		if (attributs.getLocalName(0).equals("id")){
        			this.result = new Result(Integer.parseInt(attributs.getValue(0)));
        		}
        	}
        	
        	if (localName.equals("FILE")){
        		this.result.files.add(new ResultFile());
        	}
        	
        	this.balises.push(localName);
        }

        public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
        	//Fermeture d'une balise, on la depile donc.
                this.balises.pop();
        }

        public void characters(char[] ch, int start, int end) throws SAXException {
        	// lit l'interieure des balises, la balise sur la pile est la balise courante,
        	//On regarde ainsi dans quel balise on est pour recupéré le string et le mettre dans l'attribu de search correspondant
        	String content = new String(ch, start, end);
        	
        	if(content.trim().equals("")){
        		return;
        	}
        	System.out.println("PCDATA:"+content);
        	//########   PARTIE SEARCH   ########
        	if(this.balises.peek().equals("WORD")){
               	this.search.word = Boolean.parseBoolean(content);
        	}
        	if(this.balises.peek().equals("CONTENT")){
        		this.search.content=content;
        	}
        	if(this.balises.peek().equals("PATHDIR")){
        		this.search.pathdir=content;
        	}
        	if(this.balises.peek().equals("PERM")){
        		if(this.search != null)
        			this.search.perm=content;
        	}
        	if(this.balises.peek().equals("EXTENSION")){
        		this.search.extension=content;
        	}
        	if(this.balises.peek().equals("TIMESLOT")){
        		this.timeSlot= new TimeSlot(0, 0, 0, 0, 0, 0);
        	}
        	if(this.balises.peek().equals("DAY")){
        		String temp = this.balises.pop();
        		if(this.balises.peek().equals("BEGIN")){
        			this.timeSlot.dayB= Integer.parseInt(content);
        		}
        		if(this.balises.peek().equals("END")){
        			this.timeSlot.dayE= Integer.parseInt(content);
        		}
        		this.balises.push(temp);
        	}
        	if(this.balises.peek().equals("MONTH")){
        		String temp = this.balises.pop();
        		if(this.balises.peek().equals("BEGIN")){
        			this.timeSlot.monthB= Integer.parseInt(content);
        		}
        		if(this.balises.peek().equals("END")){
        			this.timeSlot.monthE= Integer.parseInt(content);
        		}
        		this.balises.push(temp);
        	}
        	
        	if(this.balises.peek().equals("YEAR")){
        		String temp = this.balises.pop();
        		if(this.balises.peek().equals("BEGIN")){
        			this.timeSlot.yearB= Integer.parseInt(content);
        		}
        		if(this.balises.peek().equals("END")){
        			this.timeSlot.yearE= Integer.parseInt(content);
        		}
        		this.balises.push(temp);
        	}
        	//##### FIN PARTIE SEARCH #####
        	
        	
        	//##### PARTIE RESULT ######
        	if(this.balises.peek().equals("NAME")){
        		this.result.files.get(this.result.files.size()-1).name = content;
        	} 
        	if(this.balises.peek().equals("PATH")){
        		this.result.files.get(this.result.files.size()-1).path = content;
        	}
        	if(this.balises.peek().equals("PERM")){
        		if(this.result != null)
        			this.result.files.get(this.result.files.size()-1).perm = content;
        	}
        	if(this.balises.peek().equals("SIZE")){
        		this.result.files.get(this.result.files.size()-1).size = content;
        	}
        	if(this.balises.peek().equals("LASTMODIF")){
        		this.result.files.get(this.result.files.size()-1).lastmodif = content;
        	}
        	if(this.balises.peek().equals("PROPRIO")){
        		this.result.files.get(this.result.files.size()-1).proprio = content;
        	}
        	
        	//##### FIN PARTIE RESULT ######
        	}
        
        public Result getResult(){
        	return this.result;
        }
        public Search getSearch(){
        	return this.search;
        }

        private Locator locator;

}