package engine.search;

public class Search {
	//Objet qui represente un XML Search comme définie dans la DTD
	//Permet une manupulation facile contrairement a un XML
	
	boolean word;
	String content;
	String pathdir;
	String perm;
	String extension;
	TimeSlot timeSlot;	
	int id;

	
	public Search(boolean w, String cont, String path, String permission, String ext, TimeSlot t){
		//Seul w ne doit pas etre null
		word = w;
		content = cont;
		pathdir = path;
		perm = permission;
		extension = ext;
		timeSlot = t;
	}
	
	public Search(int parseInt) {
		// TODO Auto-generated constructor stub
	}

	public String ConvertToXml(){
		//This.file string will contain the xml
		String file;
		file = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		file += "<SEARCH id=\""+this.id+"\">\n";
		file += "    <WORD>"+word+"</WORD>\n";
		file += "    <CONTENT>"+ content +"</CONTENT>\n";
		if (pathdir != null){
			file += "    <PATHDIR>"+pathdir+"</PATHDIR>\n";
		}
		if (perm != null){
			file += "    <PERM>"+perm+"</PERM>\n";
		}
		if (extension != null){
			file += "    <EXTENSION>"+extension+"</EXTENSION>\n";
		}
		if (timeSlot != null){
			file += "    <TIMESLOT>\n";
			file += "        <BEGIN>\n";
			file += "            <DAY>"+timeSlot.dayB+"</DAY>\n";
			file += "            <MONTH>"+timeSlot.monthB+"</MONTH>\n";
			file += "            <YEAR>"+timeSlot.yearB+"</YEAR>\n";
			file += "        </BEGIN>\n";
			file += "        <END>\n";
			file += "            <DAY>"+timeSlot.dayE+"</DAY>\n";
			file += "            <MONTH>"+timeSlot.monthE+"</MONTH>\n";
			file += "            <YEAR>"+timeSlot.yearE+"</YEAR>\n";
			file += "        </END>\n";
			file += "    </TIMESLOT>\n";
		}
		file += "</SEARCH>\n\0";
		return file;
	}
}




