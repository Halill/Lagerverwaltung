package model;


import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

public class Buchung {
	
	private int menge;
	private static int ABBUCHUNG = 0, ZUBUCHUNG = 1;
	private static Date datum = new Date();
	private ArrayList<Lager>buchunglagerliste = new ArrayList<Lager>();
	private int schrittweite;
	private Double[] verteilungsschluessel = new Double[]{};
	
	public Double[] getVerteilungsschluessel() {
		return verteilungsschluessel;
	}
	public void setVerteilungsschluessel(Double[] verteilungsschluessel) {
		
		this.verteilungsschluessel = verteilungsschluessel;
	}
	public int getMenge() {
		return menge;
	}
	public void setMenge(int menge) {
			this.menge = menge;
		
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum() {
		Date date = new Date();
		DateFormat df;
		df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
		df.format(date);
		Buchung.datum = date;
	}
	public ArrayList<Lager> getBuchungLagerListe() {
		return buchunglagerliste;
	}
	public void setBuchungLagerListe(ArrayList<Lager> lagerl) {
		this.buchunglagerliste = lagerl;
	}
	
	public void fuegeLagerzuBuchungHinzu(Lager lager){
		this.buchunglagerliste.add(lager);
	}
	
	public void buchen(Double[] schluessel){
		this.verteilungsschluessel = schluessel;
		double schluesselsumme = 0;
		for(int i = 0;i<schluessel.length;i++){
			schluesselsumme = schluesselsumme + schluessel[i];
		}
		//Prüfung, ob die ganze Buchung verteilt wird
		if(schluesselsumme == 1.0){
			//Umwandlung der Buchungslagerliste in einen Array, um die jeweiligen Lager und Schlüssel zu verknüpfen.
			Lager[] lagerl = new Lager[this.buchunglagerliste.size()];
			for(int i = 0;i<this.buchunglagerliste.size();i++){
				lagerl[i] = this.buchunglagerliste.get(i);
			}
			//Prüfung, ob Anzahl Lager und Größe des Verteilerschlüssel gleich sind.
			if (this.buchunglagerliste.size()==schluessel.length){	
				for(int i = 0;i<schluessel.length;i++){	
					//Prüfung, ob das Lager ein Leaflager ist, ansonsten schlägt die Buchung fehl, und ob das jeweilige Lager noch genügend Restkapazität hat
					if(lagerl[i].getLagerStatus()=="Leaflager" && ((lagerl[i].getKapazitaet()-lagerl[i].getBestand()) >= this.menge*schluessel[i])){
						lagerl[i].setBestand(lagerl[i].getBestand()+(int)(this.menge*schluessel[i]));
						
						lagerl[i].hasChanged();
						lagerl[i].notify();
						lagerl[i].getElternlager().setBestand(lagerl[i].getElternlager().durchlaufenBestand());
					}
					
					
			}
		}
		}
	}
}
	

	
	
