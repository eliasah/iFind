package engine.search;

import java.util.ArrayList;

public class Result {
	ArrayList<ResultFile> files;
	int id;
	
	public Result(int n){
		id = n;
		files = new ArrayList<ResultFile>();
	}
	
	public String ConvertToXml(){
		String file;
		file = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		file += "<RESULT id =\""+this.id +"\">\n";
		for (int i = 0; i < files.size(); i++) {
			file+= "    <FILE> \n";
			file+= "        <NAME>"+files.get(i).name+"</NAME>\n";
			file+= "        <PATH>"+files.get(i).path+"</PATH>\n";
			file+= "        <PERM>"+files.get(i).perm+"</PERM>\n";
			file+= "        <SIZE>"+files.get(i).size+"</SIZE>\n";
			if (files.get(i).lastmodif != null){
				file+= "        <LASTMODIF>"+files.get(i).lastmodif+"</LASTMODIF>\n";
			}
			if (files.get(i).proprio != null){
			file+= "        <PROPRIO>"+files.get(i).proprio+"</PROPRIO>\n";
			}
			file+= "    </FILE>\n";
			}
		file += "</RESULT>";
		return file;
	}
	
}
