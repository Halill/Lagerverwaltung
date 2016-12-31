package model;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Buchung {
	
	/**Buchungsmenge*/
	private int menge;
	/**Buchungstyp, hier mit -1 initialisiert, da falls der Buchungstyp nicht gesetzt wird, keine Abbuchung ausgegeben wird*/
	private int buchungstyp = -1;
	public static final int ABBUCHUNG = 0, ZUBUCHUNG = 1;
	/**Buchungsdatum*/
	private static Date datum = new Date();
	/**Liste der Lager, auf die gebucht wird*/
	private ArrayList<Lager> buchunglagerliste = new ArrayList<Lager>();
	/**	Prozentuale Verteilungsschl�ssel f�r die zu buchenden Lager */
	private Double[] verteilungsschluessel = new Double[]{};
	/**
	 * Getter-Methode f�r den Verteilungsschl�ssel.
	 * @return gibt den Verteilungsschl�ssel aus.
	 */
	public Double[] getVerteilungsschluessel() {
		return verteilungsschluessel;
	}
	/**
	 * Setter-Methode f�r den Verteilungsschl�ssel
	 * @param verteilungsschluessel
	 */
	public void setVerteilungsschluessel(Double[] verteilungsschluessel) {	
		this.verteilungsschluessel = verteilungsschluessel;
	}
	/**
	 * Getter-Methode f�r die Buchungsmenge
	 * @return gibt die Buchungsmenge als int aus
	 */
	public int getMenge() {
		return menge;
	}
	/**
	 * Setter-Methode f�r die Buchungsmenge
	 * @param menge Buchungsmenge als int
	 */
	public void setMenge(int menge) {
			this.menge = menge;
	}
	/**
	 * Getter-Methode des Buchungsdatums und der Zeit.
	 * Hier wird auch dies formatiert.
	 * @return gibt die Buchungszeit und -datum im folgendem Format als String aus: dd.MM.yyyy HH:mm:ss
	 */
	public String getDatum() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		return simpleDateFormat.format(datum);
	}
	/**
	 * Setter-Methode f�r das Datum und die Zeit
	 */
	public void setDatum() {
		Date datum = new Date();
		Buchung.datum = datum;
	}
	/**
	 * Getter-Methode f�r die Liste der Lager, auf die gebucht wird
	 * @return ArrayListy<Lager> der Lager, auf die gebucht wird
	 */
	public ArrayList<Lager> getBuchungLagerListe() {
		return buchunglagerliste;
	}
	/**
	 * Setter-Methode f�r die Liste der Lager, auf die gebucht wird
	 * @param lagerl Liste der Lager, auf die gebucht wird
	 */
	public void setBuchungLagerListe(ArrayList<Lager> lagerl) {
		this.buchunglagerliste = lagerl;
	}
	/**
	 * gibt den Buchungstyps aus. 
	 * 0 = Abbuchung, 1 = Zubuchung
	 * @return Buchungstyp als String
	 */
	public String getBuchungstyp(){
		if(buchungstyp == ZUBUCHUNG) return "Zubuchung";
		if(buchungstyp == ABBUCHUNG) return "Abbuchung";
		return "";
	}
	/**
	 * Setter-Methode f�r den Buchungstyp. 0 = Abbuchung, 1 = Zubuchung
	 * @param buchungstyp gibt den Buchungstyp an.
	 */
	public void setBuchungstyp(int buchungstyp){
		if(buchungstyp == ZUBUCHUNG) this.buchungstyp = ZUBUCHUNG;
		if(buchungstyp == ABBUCHUNG) this.buchungstyp = ABBUCHUNG;
	}
	/**
	 * f�gt ein Lager zu Buchungslagerliste hinzu und gleichzeitig wird die Buchung in der Liste der Buchungen eines Lager eingetragen.
	 * @param lager Lager, dass hinzugef�gt werden soll
	 */
	public void fuegeLagerzuBuchungHinzu(Lager lager){
		this.buchunglagerliste.add(lager);
		lager.addBuchung(this);
	}
	/**
	 * F�hrt eine Zubuchung aus.
	 * Gepr�ft wird, ob
	 * - die komplette Buchung vollst�ndig verteilt wird
	 * - das Lager ein Leaflager ist
	 * - gen�gend Restkapazit�t vorhanden ist
	 * - eine Teilbuchung fehlgeschlagen ist
	 * 
	 * Im Falle, dass eine Teilbuchung fehlschl�gt, werden die vorherigen Teilbuchungen zur�ckgenommen.
	 */
	public void zubuchen(){
		double schluesselsumme = 0;
		ArrayList<Lager> elternlagerliste = new ArrayList<Lager>();
		Boolean geaendert = true;
		for(int i = 0;i<this.verteilungsschluessel.length;i++){
			schluesselsumme = schluesselsumme + this.verteilungsschluessel[i];
		}
		//Pr�fung, ob die ganze Buchung verteilt wird
		if(schluesselsumme == 1.0){
			//Umwandlung der Buchungslagerliste in einen Array, um die jeweiligen Lager und Schl�ssel zu verkn�pfen.
			Lager[] lagerl = new Lager[this.buchunglagerliste.size()];
			for(int i = 0;i<this.buchunglagerliste.size();i++){
				lagerl[i] = this.buchunglagerliste.get(i);
			}
			//Pr�fung, ob Anzahl Lager und Gr��e des Verteilerschl�ssel gleich sind.
			if (this.buchunglagerliste.size()==this.verteilungsschluessel.length){	
				for(int i = 0;i<this.verteilungsschluessel.length;i++){	
					//Pr�fung, ob das Lager ein Leaflager ist, ansonsten schl�gt die Buchung fehl, und es wird gepr�ft ob das jeweilige Lager noch gen�gend Restkapazit�t hat
					if(lagerl[i].getLagerStatus()=="Leaflager" && ((lagerl[i].getKapazitaet()-lagerl[i].getBestand()) >= this.menge*this.verteilungsschluessel[i]) && geaendert){
						lagerl[i].setBestand(lagerl[i].getBestand()+(int)(this.menge*this.verteilungsschluessel[i]));
						
						//aktualisiert die Best�nde der Elternlager auf allen oberen Stufen
						lagerl[i].durchlaufenElternlager(lagerl[i], elternlagerliste);
						for(Lager l : elternlagerliste) l.setBestand(l.durchlaufenBestand());
					}
					else{
						//schl�gt eine Buchung fehl, wird hier geaendert auf false gesetzt, damit keine weiteren Buchungen gebucht werden
						geaendert = false;
						//f�r den Fall, dass eine Teilbuchung nicht funktioniert, und die vorherigen Teilbuchung schon gebucht worden sind,
						//werden diese r�ckg�ngig gemacht
						for(int j = i-1; j>=0;j--){
							lagerl[j].setBestand(lagerl[j].getBestand()-(int)(this.menge*this.verteilungsschluessel[j]));
						}	
					}
				}
			}
		}
	}
	/**
	 * F�hrt eine Abbuchung aus.
	 * Gepr�ft wird, ob
	 * - die komplette Buchung vollst�ndig verteilt wird
	 * - das Lager ein Leaflager ist
	 * - gen�gend Restbestand vorhanden ist
	 * - eine Teilbuchung fehlgeschlagen ist
	 * 
	 * Im Falle, dass eine Teilbuchung fehlschl�gt, werden die vorherigen Teilbuchungen zur�ckgenommen.
	 */
	public void abbuchen(){
		
		double schluesselsumme = 0;
		ArrayList<Lager> elternlagerliste = new ArrayList<Lager>();
		Boolean geaendert = true;
		for(int i = 0;i<this.verteilungsschluessel.length;i++){
			schluesselsumme = schluesselsumme + this.verteilungsschluessel[i];
		}
		//Pr�fung, ob die ganze Buchung verteilt wird
		if(schluesselsumme == 1.0){
			//Umwandlung der Buchungslagerliste in einen Array, um die jeweiligen Lager und Schl�ssel zu verkn�pfen.
			Lager[] lagerl = new Lager[this.buchunglagerliste.size()];
			for(int i = 0;i<this.buchunglagerliste.size();i++){
				lagerl[i] = this.buchunglagerliste.get(i);
			}
			//Pr�fung, ob Anzahl Lager und Gr��e des Verteilerschl�ssel gleich sind.
			if (this.buchunglagerliste.size()==this.verteilungsschluessel.length){	
				for(int i = 0;i<this.verteilungsschluessel.length;i++){
					//Pr�fung, ob das Lager ein Leaflager ist, ansonsten schl�gt die Buchung fehl, und es wird gepr�ft ob das jeweilige Lager noch gen�gend Restbestand hat
					if(lagerl[i].getLagerStatus()=="Leaflager" && (lagerl[i].getBestand()) >= this.menge*this.verteilungsschluessel[i] && geaendert){
						lagerl[i].setBestand(lagerl[i].getBestand()-(int)(this.menge*this.verteilungsschluessel[i]));
						
						//aktualisiert die Best�nde der Elternlager auf allen oberen Stufen
						lagerl[i].durchlaufenElternlager(lagerl[i], elternlagerliste);
						for(Lager l : elternlagerliste) l.setBestand(l.durchlaufenBestand());
					}
					else {
						//schl�gt eine Buchung fehl, wird hier geaendert auf false gesetzt, damit keine weiteren Buchungen gebucht werden
						geaendert = false;
						//f�r den Fall, dass eine Teilbuchung nicht funktioniert, und die vorherigen Teilbuchung schon gebucht worden sind,
						//werden diese r�ckg�ngig gemacht
						for(int j = i-1; j>=0;j--){
							lagerl[j].setBestand(lagerl[j].getBestand()+(int)(this.menge*this.verteilungsschluessel[j]));
						}
					}	
				}
			}
		}
	}
}
	

	
	
