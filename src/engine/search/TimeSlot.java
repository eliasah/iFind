package engine.search;
 
import java.text.SimpleDateFormat;

/** 
 * Cette classe represente l'element TIMESLOT de la DTD qui specifie le protocole de communication
 * entre le moteur de recherche et la base d'indexation 
 * 
 * @author Ahl Michael - Univ. Paris Denis Diderot
 *
 */
public class TimeSlot {
	
	private int dayB;
	private int monthB;
	private int yearB;
	private int dayE;
	private int monthE;
	private int yearE;
	

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

	public int getDayB() {
		return dayB;
	}

	public void setDayB(int dayB) {
		this.dayB = dayB;
	}

	public int getMonthB() {
		return monthB;
	}

	public void setMonthB(int monthB) {
		this.monthB = monthB;
	}

	public int getYearB() {
		return yearB;
	}

	public void setYearB(int yearB) {
		this.yearB = yearB;
	}

	public int getDayE() {
		return dayE;
	}

	public void setDayE(int dayE) {
		this.dayE = dayE;
	}

	public int getMonthE() {
		return monthE;
	}

	public void setMonthE(int monthE) {
		this.monthE = monthE;
	}

	public int getYearE() {
		return yearE;
	}

	public void setYearE(int yearE) {
		this.yearE = yearE;
	}

	@Override
	public String toString() {
		return "TimeSlot [dayB=" + dayB + ", monthB=" + monthB + ", yearB="
				+ yearB + ", dayE=" + dayE + ", monthE=" + monthE + ", yearE="
				+ yearE + "]";
	}
	
	
}
