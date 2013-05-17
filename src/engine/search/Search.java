package engine.search;

public class Search {
	boolean word;
	String content;
	String pathdir;
	String perm;
	String extension;
	TimeSlot timeSlot;	
	int id;
	String file;
	
	public Search(boolean w, String cont, String path, String permission, String ext, TimeSlot t){
		//Seul w ne doit pas etre null
		word = w;
		content = cont;
		pathdir = path;
		perm = permission;
		extension = ext;
		timeSlot = t;
	}
	
	public void ConvertToXml(){
		//This.file string will contain the xml
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
	}
}




