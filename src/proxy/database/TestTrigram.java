package proxy.database;

import java.io.File;

public class TestTrigram {

	public static void main(String[] args){
		System.out.println("Trigram File Test");
		File f = new File("example2.txt");
		Trigram t1 = new Trigram(f);
		t1.printMap();
		
		/*System.out.println("Trigram File HashMap");
		Trigram t2 = new Trigram("toto et titi et toto et tata et toto");
		t2.printMap();*/
	}
}
