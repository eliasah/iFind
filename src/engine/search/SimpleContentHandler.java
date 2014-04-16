package engine.search;

import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.*;
import org.xml.sax.helpers.LocatorImpl;

/**
 * 
 * @author ahl, isabelle
 *
 */


//Implementation pour les XML de type Search fini, il renvoie en sortie un objet Search comme attribu de la classe.
//La construction se fait au fil du parsing, donc en cas d'erreur l'objet peut contenir des attribus null

public class SimpleContentHandler implements ContentHandler {

	private Locator locator;
	private Search search=null;
	private Result result = null;
	private TimeSlot timeSlot;
	private BaliseIndexation indexation=null;
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
		System.out.println("Ouverture de la balise : " + localName);

		// ### INDEXATION
		if (localName.equals("INDEXATION"))
			this.indexation = new BaliseIndexation();
		if (localName.equals("CREATIONS"))
			this.indexation.setCreation(new ArrayList<BaliseCreations>());
		if (localName.equals("MODIFICATIONS"))
			this.indexation.setModification(new ArrayList<BaliseModifications>());
		if (localName.equals("RENOMMAGES"))
			this.indexation.setRenommage(new ArrayList<BaliseRenommage>());
		if (localName.equals("SUPPRESSIONS"))
			this.indexation.setSuppression(new ArrayList<BaliseSuppressions>());
		// INDEXATION : sous-balises
		if (localName.equals("FICHIERCREE"))
			//			if (attributs.getLocalName(0).equals("id"))
			//				this.indexation.getCreation().add(new BaliseCreations(Integer.parseInt(attributs.getValue(0))));
			//			else
			this.indexation.getCreation().add(new BaliseCreations(0));
		if (localName.equals("FICHIERMODIFIE"))
			//			if (attributs.getLocalName(0).equals("id"))
			//				this.indexation.getModification().add(new BaliseModifications(Integer.parseInt(attributs.getValue(0))));
			//			else
			this.indexation.getModification().add(new BaliseModifications(0));
		if (localName.equals("FICHIERRENOMME"))
			//			if (attributs.getLocalName(0).equals("id")) 
			//				this.indexation.getRenommage().add(new BaliseRenommage(Integer.parseInt(attributs.getValue(0))));
			//			else
			this.indexation.getRenommage().add(new BaliseRenommage(0));
		if (localName.equals("FICHIERSUPPRIME"))
			//			if (attributs.getLocalName(0).equals("id"))
			//				this.indexation.getSuppression().add(new BaliseSuppressions(Integer.parseInt(attributs.getValue(0))));
			//			else
			this.indexation.getSuppression().add(new BaliseSuppressions(0));
		// INDEXATION : sous-sous-balises
		if (localName.equals("INDEXAGE")) {
			if (balises.peek().equals("FICHIERCREE")) {
				this.indexation.getCreation().get(this.indexation.getCreation().size()-1).setIndexage(new ArrayList<Mot>());
				System.out.println("TOTO " + this.indexation.getCreation().get(this.indexation.getCreation().size()-1).getIndexage());
			}
			if (balises.peek().equals("FICHIERMODIFIE")) {
				System.out.println("BIDULE");
				this.indexation.getModification().get(this.indexation.getModification().size()-1).setIndexage(new ArrayList<Mot>());
				System.out.println("TOTO " + this.indexation.getModification().get(this.indexation.getModification().size()-1).getIndexage());
			}
			//balises.push(tmp);
		}

		if (localName.equals("MOT")) {
			System.out.println(balises.toString());
			System.out.println("char mot");
			String tmp1 = balises.pop(); // indexage
			int frequence = 0;
			if (balises.peek().equals("FICHIERCREE")) {
				System.out.println("mot -> index -> fichiercree");
				if (attributs.getLocalName(0).equals("frequence"))
					frequence = Integer.parseInt(attributs.getValue(0));
				// PROBLEME APRES
				this.indexation.getCreation().get(this.indexation.getCreation().size()-1).getIndexage().add(new Mot(frequence));
				System.out.println("mot -> index -> fichiercree FIN");
			}
			if (balises.peek().equals("FICHIERMODIFIE")) {
				System.out.println("mot -> index ->fichiermodifier");
				if (attributs.getLocalName(0).equals("frequence"))
					frequence = Integer.parseInt(attributs.getValue(0));
				System.out.println("TEST");// PROBLEME APRES
				System.out.println(indexation.getModification().get(0).getIndexage().size());
				this.indexation.getModification().get(this.indexation.getModification().size()-1).getIndexage().add(new Mot(frequence));
				System.out.println("mot -> index -> fichiermodifie FIN");
			}
			//balises.push(tmp2);
			balises.push(tmp1);
		}

		// ### FIN INDEXATION

		// RECHERCHE
		if (localName.equals("SEARCH"))
			if (attributs.getLocalName(0).equals("id"))
				this.search = new Search(Integer.parseInt(attributs.getValue(0)));
		if (localName.equals("RESULT"))
			if (attributs.getLocalName(0).equals("id"))
				this.result = new Result(Integer.parseInt(attributs.getValue(0)));
		if (localName.equals("FILE"))
			this.result.getFiles().add(new ResultFile());

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

		//######### PARTIE INDEXATION ##########
		if(balises.peek().equals("PATH")){
			String tmp = balises.pop();
			if(balises.peek().equals("FICHIERSUPPRIME"))
				this.indexation.getSuppression().get(indexation.getSuppression().size()-1).setPath(content);
			if(balises.peek().equals("FICHIERRENOMME"))
				this.indexation.getRenommage().get(indexation.getRenommage().size()-1).setPath(content);
			if (balises.peek().equals("FICHIERMODIFIE")) 
				this.indexation.getModification().get(indexation.getModification().size()-1).setPath(content);
			if (balises.peek().equals("FICHIERCREE")) 
				this.indexation.getCreation().get(indexation.getCreation().size()-1).setPath(content);
			balises.push(tmp);
		}
		if (balises.peek().equals("FORMAT"))
			this.indexation.getCreation().get(indexation.getCreation().size()-1).setFormat(content);
		if (balises.peek().equals("NEWPATH")) {
			String tmp = balises.pop();
			if (balises.peek().equals("FICHIERRENOMME"))
				this.indexation.getRenommage().get(indexation.getRenommage().size()-1).setNewpath(content);
			if (balises.peek().equals("FICHIERMODIFIE"))
				this.indexation.getModification().get(indexation.getModification().size()-1).setNewpath(content);
			balises.push(tmp);
		}
		if (balises.peek().equals("DATECREATION"))
			this.indexation.getCreation().get(indexation.getCreation().size()-1).setDatecreation(content);
		if (balises.peek().equals("DATEMODIFICATION"))
			this.indexation.getModification().get(indexation.getModification().size()-1).setDatemodification(content);
		if (balises.peek().equals("TAILLE")) {
			String tmp = balises.pop();
			if (balises.peek().equals("FICHIERMODIFIE"))
				this.indexation.getModification().get(indexation.getModification().size()-1).setTaille(content);
			if (balises.peek().equals("FICHIERCREE"))
				this.indexation.getCreation().get(indexation.getCreation().size()-1).setTaille(content);
			balises.push(tmp);
		}
		if (balises.peek().equals("PROPRIETAIRE")) {
			String tmp = balises.pop();
			if (balises.peek().equals("FICHIERMODIFIE"))
				this.indexation.getModification().get(indexation.getModification().size()-1).setProprietaire(content);
			if (balises.peek().equals("FICHIERCREE"))
				this.indexation.getCreation().get(indexation.getCreation().size()-1).setProprietaire(content);
			balises.push(tmp);
		}
		if (balises.peek().equals("GROUPE")) {
			String tmp = balises.pop();
			if (balises.peek().equals("FICHIERMODIFIE"))
				this.indexation.getModification().get(indexation.getModification().size()-1).setGroupe(content);
			if (balises.peek().equals("FICHIERCREE"))
				this.indexation.getCreation().get(indexation.getCreation().size()-1).setGroupe(content);
			balises.push(tmp);
		}
		if (balises.peek().equals("PERMISSIONS")) {
			String tmp = balises.pop();
			if (balises.peek().equals("FICHIERMODIFIE"))
				this.indexation.getModification().get(indexation.getModification().size()-1).setPermissions(content);
			if (balises.peek().equals("FICHIERCREEE"))
				this.indexation.getCreation().get(indexation.getCreation().size()-1).setPermission(content);
			balises.push(tmp);
		}
		if (balises.peek().equals("MOT")) {
			String tmp1 = balises.pop();
			String tmp2 = balises.pop();
			if (balises.peek().equals("FICHIERCREE")) {
				System.out.println("toto");
				ArrayList<Mot> mots = this.indexation.getCreation().get(this.indexation.getCreation().size()-1).getIndexage();
				mots.get(mots.size()-1).setMot(content);
			}
			if (balises.peek().equals("FICHIERMODIFIE")) {
				System.out.println("titi");
				ArrayList<Mot> mots = this.indexation.getModification().get(this.indexation.getModification().size()-1).getIndexage();
				mots.get(mots.size()-1).setMot(content);
			}
			balises.push(tmp2);
			balises.push(tmp1);
		}


		//######## FIN PARTIE INDEXATION ##########

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
			String tmp = balises.pop();
			if (balises.peek().equals("FILE"))
				result.getFiles().get(result.getFiles().size()-1).setPath(content);
			balises.push(tmp);
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

	public Search getSearch() {
		return search;
	}

	public Result getResult() {
		return result;
	}


	public BaliseIndexation getIndexation() {
		return indexation;
	}

}