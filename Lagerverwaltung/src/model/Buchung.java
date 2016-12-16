package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Buchung {
	
	private int menge;
	private static int ABBUCHUNG = 0, ZUBUCHUNG = 1;
	private static Date datum = new Date();
	
	public static void main(String[] arg0){
		DateFormat dfmt = new SimpleDateFormat("dd.MM.yy");
		System.out.println( dfmt.format(new Date()) ); 
		

		}



	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	public static int getABBUCHUNG() {
		return ABBUCHUNG;
	}

	public static void setABBUCHUNG(int aBBUCHUNG) {
		ABBUCHUNG = aBBUCHUNG;
	}

	public static int getZUBUCHUNG() {
		return ZUBUCHUNG;
	}

	public static void setZUBUCHUNG(int zUBUCHUNG) {
		ZUBUCHUNG = zUBUCHUNG;
	}

	public static String getDatum() {
		DateFormat dfmt = new SimpleDateFormat("dd.MM.yy");
		return dfmt.format(datum);
	}

	public static void setDatum(Date datum) {
		Buchung.datum = datum;
	}
	
	

}