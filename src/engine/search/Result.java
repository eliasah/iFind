package engine.search;

import java.util.ArrayList;

public class Result {
	private ArrayList<ResultFile> files;
	private int id;
	
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
			file+= "        <NAME>"+files.get(i).getName()+"</NAME>\n";
			file+= "        <PATH>"+files.get(i).getPath()+"</PATH>\n";
			file+= "        <PERM>"+files.get(i).getPerm()+"</PERM>\n";
			file+= "        <SIZE>"+files.get(i).getSize()+"</SIZE>\n";
			if (files.get(i).getLastmodif() != null){
				file+= "        <LASTMODIF>"+files.get(i).getLastmodif()+"</LASTMODIF>\n";
			}
			if (files.get(i).getProprio() != null){
			file+= "        <PROPRIO>"+files.get(i).getProprio()+"</PROPRIO>\n";
			}
			file+= "    </FILE>\n";
			}
		file += "</RESULT>";
		return file;
	}
	
}
