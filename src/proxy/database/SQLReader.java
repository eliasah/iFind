/** 
 * This class can be used to read sql files into an array of Strings, each  
 * representing a single query terminated by ";"  
 * Comments are filtered out.  
 */  
package proxy.database;  
  
  
import java.io.BufferedReader;    
  
import java.io.File;    
import java.io.FileReader;   
import java.util.ArrayList;  
  
public class SQLReader {   
    private static ArrayList<String> listOfQueries = null;  
      
    public ArrayList<String> createQueries(String path) {   
        String queryLine =      new String();  
        StringBuffer sBuffer =  new StringBuffer();  
        listOfQueries =         new ArrayList<String>();  
          
        try {    
            FileReader fr =     new FileReader(new File(path));         
            BufferedReader br = new BufferedReader(fr);    
        
            //read the SQL file line by line  
            while((queryLine = br.readLine()) != null) {    
                // ignore comments beginning with #  
                int indexOfCommentSign = queryLine.indexOf('#');  
                if(indexOfCommentSign != -1)  
                {  
                    if(queryLine.startsWith("#"))  
                    {  
                        queryLine = new String("");  
                    }  
                    else   
                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));  
                }  
                // ignore comments beginning with --  
                indexOfCommentSign = queryLine.indexOf("--");  
                if(indexOfCommentSign != -1)  
                {  
                    if(queryLine.startsWith("--"))  
                    {  
                        queryLine = new String("");  
                    }  
                    else   
                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));  
                }  
                // ignore comments surrounded by /* */  
                indexOfCommentSign = queryLine.indexOf("/*");  
                if(indexOfCommentSign != -1)  
                {  
                    if(queryLine.startsWith("#"))  
                    {  
                        queryLine = new String("");  
                    }  
                    else  
                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));  
                      
                    sBuffer.append(queryLine + " ");   
                    // ignore all characters within the comment  
                    do  
                    {  
                        queryLine = br.readLine();  
                    }  
                    while(queryLine != null && !queryLine.contains("*/"));  
                    indexOfCommentSign = queryLine.indexOf("*/");  
                    if(indexOfCommentSign != -1)  
                    {  
                        if(queryLine.endsWith("*/"))  
                        {  
                            queryLine = new String("");  
                        }  
                        else  
                            queryLine = new String(queryLine.substring(indexOfCommentSign+2, queryLine.length()-1));  
                    }  
                }  
                  
                //  the + " " is necessary, because otherwise the content before and after a line break are concatenated  
                // like e.g. a.xyz FROM becomes a.xyzFROM otherwise and can not be executed   
                if(queryLine != null)  
                    sBuffer.append(queryLine + " ");    
            }    
            br.close();  
              
            // here is our splitter ! We use ";" as a delimiter for each request   
            String[] splittedQueries = sBuffer.toString().split(";");  
              
            // filter out empty statements  
            for(int i = 0; i<splittedQueries.length; i++)    
            {  
                if(!splittedQueries[i].trim().equals("") && !splittedQueries[i].trim().equals("\t"))    
                {  
                    listOfQueries.add(new String(splittedQueries[i]));  
                }  
            }  
        }    
        catch(Exception e) {    
            System.out.println("*** Error : "+e.toString());    
            System.out.println("*** ");    
            System.out.println("*** Error : ");    
            e.printStackTrace();    
            System.out.println("################################################");    
            System.out.println(sBuffer.toString());    
        }  
        return listOfQueries;  
    }   
}  

class SQLReaderTest {
	public static void main(String[] args) {
		SQLReader s = new SQLReader();
		s.createQueries("config/indices.sql");
	}
}