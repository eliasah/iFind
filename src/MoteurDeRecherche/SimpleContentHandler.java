package MoteurDeRecherche;

import java.util.Stack;

import org.xml.sax.*;
import org.xml.sax.helpers.LocatorImpl;

/**
 * @author smeric
 *
 * Exemple d'implementation extremement simplifiee d'un SAX XML ContentHandler. Le but de cet exemple
 * est purement pedagogique.
 * Very simple implementation sample for XML SAX ContentHandler.
 */
public class SimpleContentHandler implements ContentHandler {

	Search search=null;
	TimeSlot timeSlot;
	Stack<String> balises = new Stack<String>();
	
        public SimpleContentHandler() {
                super();
                // On definit le locator par defaut.
                locator = new LocatorImpl();
        }

        /**
         * Definition du locator qui permet a tout moment pendant l'analyse, de localiser
         * le traitement dans le flux. Le locator par defaut indique, par exemple, le numero
         * de ligne et le numero de caractere sur la ligne.
         * @author smeric
         * @param value le locator a utiliser.
         * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
         */
        public void setDocumentLocator(Locator value) {
                locator =  value;
        }

        /**
         * Evenement envoye au demarrage du parse du flux xml.
         * @throws SAXException en cas de probleme quelconque ne permettant pas de
         * se lancer dans l'analyse du document.
         * @see org.xml.sax.ContentHandler#startDocument()
         */
        public void startDocument() throws SAXException {
                System.out.println("Debut de l'analyse du document");
        }

        /**
         * Evenement envoye a la fin de l'analyse du flux XML.
         * @throws SAXException en cas de probleme quelconque ne permettant pas de
         * considerer l'analyse du document comme etant complete.
         * @see org.xml.sax.ContentHandler#endDocument()
         */
        public void endDocument() throws SAXException {
                System.out.println("Fin de l'analyse du document" );
        }

        /**
         * Debut de traitement dans un espace de nommage.
         * @param prefixe utilise pour cet espace de nommage dans cette partie de l'arborescence.
         * @param URI de l'espace de nommage.
         * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
         */
        public void startPrefixMapping(String prefix, String URI) throws SAXException {
                System.out.println("Traitement de l'espace de nommage : " + URI + ", prefixe choisi : " + prefix);
        }

        /**
         * Fin de traitement de l'espace de nommage.
         * @param prefixe le prefixe choisi a l'ouverture du traitement de l'espace nommage.
         * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
         */
        public void endPrefixMapping(String prefix) throws SAXException {
                System.out.println("Fin de traitement de l'espace de nommage : " + prefix);
        }

        /**
         * Evenement recu a chaque fois que l'analyseur rencontre une balise XML ouvrante.
         * @param nameSpaceURI l'URL de l'espace de nommage.
         * @param localName le nom local de la balise.
         * @param rawName nom de la balise en version 1.0 <code>nameSpaceURI + ":" + localName</code>
         * @throws SAXException si la balise ne correspond pas a ce qui est attendu,
         * comme non-respect d'une DTD.
         * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
         */
        public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException {
                System.out.println("Ouverture de la balise : " + localName);
                
                if (localName.equals("SEARCH")){
                	if (attributs.getLocalName(0).equals("id")){
                		this.search = new Search(Integer.parseInt(attributs.getValue(0)));
                	}
                }
                
                this.balises.push(localName);
                
                /*
                if ( ! "".equals(nameSpaceURI)) { // espace de nommage particulier
                        System.out.println("  appartenant a l'espace de nom : "  + nameSpaceURI);
                }
                */
                System.out.println("  Attributs de la balise : ");

                for (int index = 0; index < attributs.getLength(); index++) { // on parcourt la liste des attributs
                        System.out.println("     - " +  attributs.getLocalName(index) + " = " + attributs.getValue(index));
                }
        }

        /**
         * Evenement recu a chaque fermeture de balise.
         * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
         */
        public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
                System.out.print("Fermeture de la balise : " + localName);
                this.balises.pop();
                if ( ! "".equals(nameSpaceURI)) { // name space non null
                        System.out.print("appartenant a l'espace de nommage : " + localName);
                }

                System.out.println();
        }

        /**
         * Evenement recu a chaque fois que l'analyseur rencontre des caracteres (entre
         * deux balises).
         * @param ch les caracteres proprement dits.
         * @param start le rang du premier caractere a traiter effectivement.
         * @param end le rang du dernier caractere a traiter effectivement
         * @see org.xml.sax.ContentHandler#characters(char[], int, int)
         */
        public void characters(char[] ch, int start, int end) throws SAXException {
                System.out.println("#PCDATA : " + new String(ch, start, end));
                
                if(this.balises.peek().equals("WORD")){
                	this.search.word = Boolean.parseBoolean(new String(ch, start, end));
                }
                if(this.balises.peek().equals("CONTENT")){
                	this.search.content=new String(ch, start, end);
                }
                if(this.balises.peek().equals("PATHDIR")){
                	this.search.pathdir=new String(ch, start, end);
                }
                if(this.balises.peek().equals("PERM")){
                	this.search.perm=new String(ch, start, end);
                }
                if(this.balises.peek().equals("EXTENSION")){
                	this.search.extension=new String(ch, start, end);
                }
                if(this.balises.peek().equals("TIMESLOT")){
                	this.timeSlot= new TimeSlot();
                }
                if(this.balises.peek().equals("DAY")){
                	String temp = this.balises.pop();
                	if(this.balises.peek().equals("BEGIN")){
                		this.timeSlot.dayB= Integer.parseInt(new String(ch, start, end));
                	}
                	if(this.balises.peek().equals("END")){
                		this.timeSlot.dayE= Integer.parseInt(new String(ch, start, end));
                	}
                	this.balises.push(temp);
                }
                if(this.balises.peek().equals("MONTH")){
                	String temp = this.balises.pop();
                	if(this.balises.peek().equals("BEGIN")){
                		this.timeSlot.monthB= Integer.parseInt(new String(ch, start, end));
                	}
                	if(this.balises.peek().equals("END")){
                		this.timeSlot.monthE= Integer.parseInt(new String(ch, start, end));
                	}
                	this.balises.push(temp);
                }
                if(this.balises.peek().equals("YEAR")){
                	String temp = this.balises.pop();
                	if(this.balises.peek().equals("BEGIN")){
                		this.timeSlot.yearB= Integer.parseInt(new String(ch, start, end));
                	}
                	if(this.balises.peek().equals("END")){
                		this.timeSlot.yearE= Integer.parseInt(new String(ch, start, end));
                	}
                	this.balises.push(temp);
                }
        }

        /**
         * Recu chaque fois que des caracteres d'espacement peuvent etre ignores au sens de
         * XML. C�est-a-dire que cet evenement est envoye pour plusieurs espaces se succedant,
         * les tabulations, et les retours chariot se succedant ainsi que toute combinaison de ces
         * trois types d'occurrence.
         * @param ch les caracteres proprement dits.
         * @param start le rang du premier caractere a traiter effectivement.
         * @param end le rang du dernier caractere a traiter effectivement
         * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
         */
        public void ignorableWhitespace(char[] ch, int start, int end) throws SAXException {
                System.out.println("espaces inutiles rencontres : ..." + new String(ch, start, end) +  "...");
        }

        /**
         * Rencontre une instruction de fonctionnement.
         * @param target la cible de l'instruction de fonctionnement.
         * @param data les valeurs associees a cette cible. En general, elle se presente sous la forme 
         * d'une serie de paires nom/valeur.
         * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
         */
        public void processingInstruction(String target, String data) throws SAXException {
                System.out.println("Instruction de fonctionnement : " + target);
                System.out.println("  dont les arguments sont : " + data);
        }

        /**
         * Recu a chaque fois qu'une balise est evitee dans le traitement a cause d'un
         * probleme non bloque par le parser. Pour ma part je ne pense pas que vous
         * en ayez besoin dans vos traitements.
         * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
         */
        public void skippedEntity(String arg0) throws SAXException {
                // Je ne fais rien, ce qui se passe n'est pas franchement normal.
                // Pour eviter cet evenement, le mieux est quand meme de specifier une DTD pour vos
                // documents XML et de les faire valider par votre parser.              
        }

        private Locator locator;

}