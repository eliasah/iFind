package MoteurDeRecherche;
import java.text.SimpleDateFormat;

public class TimeSlot {
	
	//Represente l'objet timeslot de la DTD qui contien 2 champ, begin et end qui contiennent eux day,month,year
	//Ici tout est mis directement dans TimeSlot pour Žviter de multiples objets non nŽcessaire.
	//DayB -> Day Begin, monthB -> month Begin etc.
	int dayB;
	int monthB;
	int yearB;
	int dayE;
	int monthE;
	int yearE;
	
	public TimeSlot(){
		dayB= 0;
		monthB=0;
		yearB=0;
		dayE= 0;
		monthE=0;
		yearE=0;	
	}
	public TimeSlot(int db, int mb, int yb, int de, int me, int ye){
		dayB= db;
		monthB = mb;
		yearB = yb;
		dayE = de;
		monthE = me;
		yearE = ye;
	}
}
