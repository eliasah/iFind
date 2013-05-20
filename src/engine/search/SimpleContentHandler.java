package engine.search;

import java.util.Stack;

import org.xml.sax.*;
import org.xml.sax.helpers.LocatorImpl;

/**
 * 
 * @author ahl
 *
 */


//Implementation pour les XML de type Search fini, il renvoie en sortie un objet Search comme attribu de la classe.
//La construction se fait au fil du parsing, donc en cas d'erreur l'objet peut contenir des attribus null
//TODO idem pour les fichiers RESULT

public class SimpleContentHandler implements ContentHandler {
	
	private Locator locator;
	private Search search=null;
	private Result result = null;
	private TimeSlot timeSlot;
	private Stack<String> balises = new Stack<String>();
	
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
                	this.result.getFiles().add(new ResultFile());
                }
                
                this.balises.push(localName);
                
        }

        public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
        	//Fermeture d'une balise, on la depile donc.
                System.out.print("Fermeture de la balise : " + localName);
                this.balises.pop();
                if ( ! "".equals(nameSpaceURI)) { // name space non null
                        System.out.print("appartenant a l'espace de nommage : " + localName);
                }

                System.out.println();
        }

        public void characters(char[] ch, int start, int end) throws SAXException {
        	// lit l'interieure des balises, la balise sur la pile est la balise courante,
        	//On regarde ainsi dans quel balise on est pour recup�r� le string et le mettre dans l'attribu de search correspondant
        	String content = new String(ch, start, end).trim();
        	
        	if(content.equals(""))
        		return;
        	
        	//########   PARTIE SEARCH   ########
                if(balises.peek().equals("WORD")){
                	search.setWord(content);
                	}
                if(balises.peek().equals("CONTENT")){
                	search.setContent(Boolean.parseBoolean(content));
                }
                if(balises.peek().equals("PATHDIR")){
                	search.setPathdir(content);
                }
                if(balises.peek().equals("PERM")){
                	search.setPerm(content);
                }
                if(balises.peek().equals("EXTENSION")){
                	search.setExtension(content);
                }
                if(balises.peek().equals("TIMESLOT")){
                	this.timeSlot= new TimeSlot(0, 0, 0, 0, 0, 0);
                }
                if(balises.peek().equals("DAY")){
                	String temp = balises.pop();
                	if(balises.peek().equals("BEGIN")){
                		timeSlot.setDayB(Integer.parseInt(content));
                	}
                	if(balises.peek().equals("END")){
                		timeSlot.setDayE(Integer.parseInt(content));
                	}
                	balises.push(temp);
                }
                if(balises.peek().equals("MONTH")){
                	String temp = balises.pop();
                	if(balises.peek().equals("BEGIN")){
                		timeSlot.setMonthB(Integer.parseInt(content));
                	}
                	if(balises.peek().equals("END")){
                		timeSlot.setMonthE(Integer.parseInt(content));
                	}
                	balises.push(temp);
                }
                if(balises.peek().equals("YEAR")){
                	String temp = balises.pop();
                	if(balises.peek().equals("BEGIN")){
                		timeSlot.setYearB(Integer.parseInt(content));
                	}
                	if(balises.peek().equals("END")){
                		timeSlot.setYearE(Integer.parseInt(content));
                	}
                	balises.push(temp);
                }
                //##### FIN PARTIE SEARCH #####
                
                
                //##### PARTIE RESULT ######
                if(balises.peek().equals("NAME")){
                	result.getFiles().get(result.getFiles().size()-1).setName(content);
                }
                if(balises.peek().equals("PATH")){
                	result.getFiles().get(result.getFiles().size()-1).setPath(content);
                }
                if(balises.peek().equals("PERM")){
                	result.getFiles().get(result.getFiles().size()-1).setPerm(content);
                }
                if(balises.peek().equals("SIZE")){
                	result.getFiles().get(result.getFiles().size()-1).setSize(content);
                }
                if(balises.peek().equals("LASTMODIF")){
                	result.getFiles().get(result.getFiles().size()-1).setLastmodif(content);
                }
                if(balises.peek().equals("PROPRIO")){
                	result.getFiles().get(result.getFiles().size()-1).setProprio(content);
                }
                
                //##### FIN PARTIE RESULT ######
        }

		@Override
		public void endPrefixMapping(String arg0) throws SAXException {
			
		}

		@Override
		public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
				throws SAXException {
			
		}

		@Override
		public void processingInstruction(String arg0, String arg1)
				throws SAXException {
			
		}

		@Override
		public void skippedEntity(String arg0) throws SAXException {
			
		}

		@Override
		public void startPrefixMapping(String arg0, String arg1)
				throws SAXException {
			
		}

        
}