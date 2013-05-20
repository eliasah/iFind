package engine.search;

/**	Objet qui represente un XML Search comme définie dans la DTD 
 * permet une manupulation facile contrairement a un XML
 * 
 * @author Ahl Mikael - Univ. Paris Denis Diderot
 *
 */
public class Search {

	private boolean content;
	private String extension;
	private int id;
	private String pathdir;
	private String perm;
	private TimeSlot timeSlot;
	private String word;	
	
	public Search(int i){
		id = i;
	}
	
	public Search(int i,String w, boolean cont, String path, String permission, String ext, TimeSlot t){
		id = i;
		word = w;
		content = cont;
		pathdir = path;
		perm = permission;
		extension = ext;
		timeSlot = t;
	}

	/** Converti l'objet search en flux XML
	 * 
	 * @return 
	 */
	public String ConvertToXml(){
		String file = new String();
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
			file += "            <DAY>"+timeSlot.getDayB()+"</DAY>\n";
			file += "            <MONTH>"+timeSlot.getMonthB()+"</MONTH>\n";
			file += "            <YEAR>"+timeSlot.getYearB()+"</YEAR>\n";
			file += "        </BEGIN>\n";
			file += "        <END>\n";
			file += "            <DAY>"+timeSlot.getDayE()+"</DAY>\n";
			file += "            <MONTH>"+timeSlot.getMonthE()+"</MONTH>\n";
			file += "            <YEAR>"+timeSlot.getYearE()+"</YEAR>\n";
			file += "        </END>\n";
			file += "    </TIMESLOT>\n";
		}
		file += "</SEARCH>\n\0";
		return file;
	}

	public String getExtension() {
		return extension;
	}

	public int getId() {
		return id;
	}

	public String getPathdir() {
		return pathdir;
	}

	public String getPerm() {
		return perm;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public String getWord() {
		return word;
	}

	public boolean isContent() {
		return content;
	}

	public void setContent(boolean content) {
		this.content = content;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPathdir(String pathdir) {
		this.pathdir = pathdir;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public String toString() {
		return "Search [content=" + content + ", extension=" + extension
				+ ", id=" + id + ", pathdir=" + pathdir + ", perm=" + perm
				+ ", timeSlot=" + timeSlot + ", word=" + word + "]";
	}
	
	
}




