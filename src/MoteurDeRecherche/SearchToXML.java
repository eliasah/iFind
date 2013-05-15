package MoteurDeRecherche;

public class SearchToXML {
	String file;
	Search s;
	int id;
	
	public SearchToXML(Search search, int id){
		file = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		this.s = search;
		this.id = id;
	}
	
	public void ConvertToXml(){
		//This.file string will contain the xml
		file += "<SEARCH id=\""+this.id+"\">\n";
		file += "    <WORD>"+this.s.word+"</WORD>\n";
		file += "    <CONTENT>"+ this.s.content +"</CONTENT>";
		file += "</SEARCH>";
		//TODO cas de if pour enrichir le XML avec les recherches avancé
	}
}
