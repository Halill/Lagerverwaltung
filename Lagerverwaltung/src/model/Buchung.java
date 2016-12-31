package model;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import view.Buchen;

public class Buchung extends Buchen{
	
	
	private int menge;
	private int buchungstyp = -1;
	private static int ABBUCHUNG = 0, ZUBUCHUNG = 1;
	private static Date datum = new Date();
	private ArrayList<Lager>buchunglagerliste = new ArrayList<Lager>();
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
	/**
	 * 
	 * @return
	 */
	public String getBuchungstyp(){
		if(buchungstyp == ZUBUCHUNG) return "Zubuchung";
		if(buchungstyp == ABBUCHUNG) return "Abbuchung";
		return "";
	}
	
	/**
	 * 
	 * @param buchungstyp gibt den Buchungstyp an. 0 = Abbuchung, 1 = Zubuchung
	 */
	public void setBuchungstyp(int buchungstyp){
		if(buchungstyp == ZUBUCHUNG) this.buchungstyp = ZUBUCHUNG;
		if(buchungstyp == ABBUCHUNG) this.buchungstyp = ABBUCHUNG;
	}
	public void fuegeLagerzuBuchungHinzu(Lager lager){
		this.buchunglagerliste.add(lager);
	}
	
	public void zubuchen(Double[] schluessel){
		this.verteilungsschluessel = schluessel;
		double schluesselsumme = 0;
		ArrayList<Lager> elternlagerliste = new ArrayList<Lager>();
		Boolean geaendert = true;
		for(int i = 0;i<schluessel.length;i++){
			schluesselsumme = schluesselsumme + schluessel[i];
		}
		schluesselsumme = round(schluesselsumme,2);
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
					//Prüfung, ob das Lager keine Kinder hat, ansonsten schlägt die Buchung fehl, und ob das jeweilige Lager noch genügend Restkapazität hat
					if(lagerl[i].getKindlager().size() == 0 && ((lagerl[i].getKapazitaet()-lagerl[i].getBestand()) >= this.menge*schluessel[i]) && geaendert){
						lagerl[i].setBestand(lagerl[i].getBestand()+(int)(this.menge*schluessel[i]));
						
						//aktualisiert die Bestände der Elternlager auf allen oberen Stufen
						lagerl[i].durchlaufenElternlager(lagerl[i], elternlagerliste);
						for(Lager l : elternlagerliste){
							l.setBestand(l.durchlaufenBestand());
						}
						
					}
					else{
						geaendert = false;
						//für den Fall, dass eine Teilbuchung nicht funktioniert, und die vorherigen Teilbuchung schon gebucht worden sind
						for(int j = i-1; j>=0;j--){
							lagerl[j].setBestand(lagerl[j].getBestand()+(int)(this.menge*schluessel[j]));
						}
					
					
			}
		}
			}
			setLager(lagerl);
			}
	}
	private void setLager(Lager[] lagerl) {
		
		for(int i = 0; i < buchunglagerliste.size();i++)
			buchunglagerliste.set(i,lagerl[i]);
		setLager(buchunglagerliste);
		
	}
	public void abbuchen(Double[] schluessel){
		this.verteilungsschluessel = schluessel;
		double schluesselsumme = 0;
		ArrayList<Lager> elternlagerliste = new ArrayList<Lager>();
		Boolean geaendert = true;
		for(int i = 0;i<schluessel.length;i++){
			schluesselsumme = schluesselsumme + schluessel[i];
		}
		schluesselsumme = round(schluesselsumme,2);
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

					//Prüfung, ob das Lager keine Kinder hat, ansonsten schlägt die Buchung fehl, und ob das jeweilige Lager noch genügend Restbestand hat
					if(lagerl[i].getKindlager().size() == 0 && (lagerl[i].getBestand()) >= this.menge*schluessel[i] && geaendert){
						lagerl[i].setBestand(lagerl[i].getBestand()-(int)(this.menge*schluessel[i]));
						
						//aktualisiert die Bestände der Elternlager auf allen oberen Stufen
						lagerl[i].durchlaufenElternlager(lagerl[i], elternlagerliste);
						for(Lager l : elternlagerliste){
							l.setBestand(l.durchlaufenBestand());
						}
					
					}
					else {
						geaendert = false;
						//für den Fall, dass eine Teilbuchung nicht funktioniert, und die vorherigen Teilbuchung schon gebucht worden sind
						for(int j = i-1; j>=0;j--){
							lagerl[j].setBestand(lagerl[j].getBestand()-(int)(this.menge*schluessel[j]));
							
						}
					}

					
			}
		}
			setLager(lagerl);
		}
	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}


}
	

	
	
