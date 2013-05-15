package MoteurDeRecherche;

public class SearchToXML {
	boolean word=false;
	String keywords;
	String file="";
	int id;
	String ext="";
	TimeSlot timeSlot = null;
	
	public SearchToXML(String keywords, int id){
		file += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		this.keywords = keywords;
		this.id = id;
	}
	
	public void ConvertToXml(){
		//This.file string will contain the xml
		file += "<SEARCH id=\""+this.id+"\">\n";
		file += "    <WORD>"+this.word+"</WORD>\n";
		file += "    <CONTENT>"+ this.keywords +"</CONTENT>";
		file += "</SEARCH>";
		//TODO cas de if pour enrichir le XML avec les recherches avancé
	}
}
