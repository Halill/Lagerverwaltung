package model;
/**
 * Die Klasse Lager im Package Model.
 * 
 * In dieser Klasse sind Methoden enthalten, die die Lager-Attribute ver�ndern und ausgeben k�nnen.
 * @author Halil
 */
import java.util.ArrayList;

public class Lager{
	
	/** Attribut Name*/
	private String name;
	/** Attribut Kapazit�t*/
	private int kapazitaet;
	/** Attribut Bestand*/
	private int bestand;
	/** Attribut Elternlager. Setzt die Beziehung zu einem "h�heren" Lager.*/
	private Lager elternlager;
	/** Attribut Kindlager. Eine Liste, die die Lager unter diesem Lager enth�lt.*/
	private ArrayList<Lager> kindlager = new ArrayList<Lager>();
	/**Attribut Lagerstatus.*/
	
	private ArrayList<Buchung> buchungsliste = new ArrayList<Buchung>();

	private static int ROOTLAGER = 0, TREELAGER = 1, LEAFLAGER = 2; 
	
	/**
	 * Methode zum setzen des Lagerstatus. Kann erst nachdem die Lagerstruktur aufgebaut wurde, ausgef�hrt werden, da sonst Fehler entstehen.
	 * Deswegen wird diese Methode in der Methode der Klasse Model @see {@link Model#legeInitialeStrukturFest()} erst nachdem alle Lager angelegt wurden aufgerufen.
	 * <br><br>
	 * Erkl�rung der Lagerstatus:<br><br>
	 * Rootlager:<br>
	 * Das Lager an der obersten Stelle in der Hierarchie.<br>
	 * <br>
	 * Treelager:<br>
	 * Die Lager, die zwischen dem Rootlager und den Leaflagern liegen.<br>
	 * <br>
	 * Leaflager:<br>
	 * Die Lager, an der untersten Stelle in der Hierarchie<br>
	 * <br>
	 * @return Setzt den Lagerstatus eines Lagers. Trifft keine Bedingung ein, so wird ein anderer Wert (5) ausgegeben.
	 * 
	 */
	public int setLagerStatus(){
		if(this.elternlager==null){
			return ROOTLAGER;
		}
		if(this.elternlager!=null && this.kindlager.isEmpty()==false){
			return TREELAGER;
		}
		if (this.elternlager!=null && this.kindlager.isEmpty()){
			return LEAFLAGER;
		}
		return 5;	
	}
	/**
	 * 
	 * Methode zum ausgeben des Lagerstatus. Kann erst nachdem die Methode @see {@link Lager#setLagerStatus()} ausgef�hrt werden, da beim Anlegen dieser Wert noch nicht gesetzt wurde.
	 * 
	 * @return gibt den aktuellen Lagerstatus als String aus. Falls das Lager keine der drei Status besitzt, wird die 5 ausgegeben, damit man Fehler erkennt.
	 */
	public String getLagerStatus(){
		if(this.setLagerStatus()==ROOTLAGER){
			return "Rootlager";
		}
		if(this.setLagerStatus()==TREELAGER){
			return "Treelager";
		}
		if(this.setLagerStatus()==LEAFLAGER){
			return "Leaflager";
		}
		return "5";
	}	
	/**
	 * Methode zum ausgeben des Lagernamens. Kann erst nachdem die Methode @see {@link Lager#setName(String)} oder @see {@link Model#lagerAnlegen(String, int, int)} fehlerfrei
	 * ausgef�hrt werden, da sonst der Lagername noch nicht gesetzt wurde.
	 * Diese Methode wird haupts�chlich in der Methode @see {@link Model#sysoLagerstruktur(ArrayList)} verwendet.
	 * @return gibt den Lagernamen als String aus.
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * 
	 * Methode zum setzen des Lagernamens. Wird haupts�chlich in der Methode @see {@link Model#lagerAnlegen(String, int, int)} verwendet.
	 * @param name der Name eines Lagers wird per String gesetzt.
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * Methode zum ausgeben der Lagerkapazit�t. Kann erst nachdem die Methode @see {@link Lager#setKapazitaet(int)} oder @see {@link Model#lagerAnlegen(String, int, int)} fehlerfrei
	 * ausgef�hrt werden, da sonst die Lagerkapazit�t noch nicht gesetzt wurde.
	 * Diese Methode wird haupts�chlich in der Methode @see {@link Model#sysoLagerstruktur(ArrayList)} verwendet.
	 * @return gibt die Lagerkapazit�t als int aus.
	 */
	public int getKapazitaet() {
		return kapazitaet;
	}
	/**
	 * 
	 * Methode zum setzen der Lagerkapazit�t. Wird haupts�chlich in der Methode @see {@link Model#lagerAnlegen(String, int, int)} verwendet.
	 * @param kapazitaet die Kapazit�t eines Lagers wird per int gesetzt.
	 */
	public void setKapazitaet(int kapazitaet) {
			this.kapazitaet = kapazitaet; 
	}
	/**
	 *
	 * Methode zum ausgeben des Lagerbestands. Kann erst nachdem die Methode @see {@link Lager#setBestand(int)} oder @see {@link Model#lagerAnlegen(String, int, int)} fehlerfrei
	 * ausgef�hrt werden, da sonst der Lagerbestand noch nicht gesetzt wurde.
	 * @return gibt den Lagerbestand als int aus.
	 */
	public int getBestand() {
		return bestand;
	}
	/**
	 *
	 * Methode zum setzen des Lagerbestands. Wird haupts�chlich in der Methode @see {@link Model#lagerAnlegen(String, int, int)} verwendet.
	 * @param bestand der Bestand eines Lagers wird per int gesetzt
	 */
	public void setBestand(int bestand) {

		this.bestand = bestand;
	}
	/**
	 *
	 * Methode zum ausgeben des Elternlagers (siehe Definition Elternlager bei @see {@link Lager#setKindlager(Lager)}). Kann erst nachdem die Methode @see {@link Lager#setKindlager(int)} fehlerfrei
	 * ausgef�hrt werden, da sonst das Elternlager noch nicht gesetzt wurde. 
	 * @return gibt das Elternlager als Lager-Objekt aus
	 */
	public Lager getElternlager() {
		return elternlager;
	}
	/**
	 * Diese Methode setzt das Elternlager eines Lagers.
	 * Sie wird nur in der Methode {@link Model#lagerLoeschen(Lager, Lager)} verwendet.
	 * @param l
	 */
	public void setElternlager(Lager l){
		this.elternlager = l;
	}
	/**
	 *
	 * Methode zum ausgeben der Kindlager (siehe Definition Kindlager bei @see {@link Lager#setKindlager(Lager)}). Kann erst nachdem die Methode @see {@link Lager#setKindlager(Lager)} fehlerfrei
	 * ausgef�hrt werden, da sonst die Arraylist der Kindlager leer ist.
	 * @return gibt die Kindlager eines Lagers als Arraylist aus
	 */
	public ArrayList<Lager> getKindlager() {
		return kindlager;
	}
	/**
	 *
	 * Methode zum setzen von Beziehungen zwischen zwei Lager. 
	 * 
	 * Definitionen:
	 * Kindlager: das Lager,das in der Hierarchie unter einem Lager liegt. Ein Lager kann nur ein Elternlager haben.
	 * Elternlager: das Lager, das in der Hierarchie �ber einem Lager liegt Ein Lager kann mehrere Elternlager haben.
	 * 
	 * Hier wird automatisch die Kind-Eltern <b>und</b> die Eltern-Kind Beziehung gesetzt. Deswegen wird auf eine zweite Methode setElternlager(Lager) verzichtet.
	 * 
	 * @param kindlager der Parameter kindlager setzt das �bergebene Lager in die Kindlager Liste des aktuellen Lagers.
	 */
	public void setKindlager(Lager kindlager) {
		this.kindlager.add(kindlager);
		kindlager.elternlager = this;
	}
	/**
	 *
	 * Mit Hilfe dieser Methode wird das �bergebene Lager aus der Kindlager Liste des aktuellen Lagers entfernt.
	 * Wird haupts�chlich in der Methode @see {@link Model#neuesLagereinfuegen(Lager, Lager, Lager)} genutzt. 
	 * Wird zur verbesserten Lesbarkeit verwendet.
	 * @param lager das Lager, dass aus der Kindlager Liste des aktuellen Lagers gel�scht wird.
	 */
	public void deleteKindlager(Lager lager){
		this.kindlager.remove(lager);

	}
	/**
	 * Methode, die die Kapazit�ten in den Kindlagern addiert. Diese Methode wird genutzt, um die Kapazit�ten der Elternlager zu berechnen, da diese die
	 * Kapazit�ten der Kindlager erhalten.

	 * @return die Gesamtkapazit�t der Kindlager als int.
	 */
	public  int durchlaufenKapazitaet(){
		int kapazitaet = 0;
		ArrayList<Integer> summekapazitaet = new ArrayList<Integer>();
		durchlaufenKapazitaet(this, summekapazitaet);
		for(int i : summekapazitaet){
			kapazitaet = kapazitaet + i;
		}
		return kapazitaet;
	}
	/**
	 * rekursive Hilfsmethode f�r die Methode durchlaufenKapazitaet().
	 * 
	 * @param lager Elternlager, dass die Kapazit�ten der zugeh�rigen Leaflager erh�lt
	 * @param liste Arraylist f�r die Zwischenspeicherung der Kapazit�ten.
	 */
	private void durchlaufenKapazitaet(Lager lager, ArrayList<Integer> liste){
		if(lager.getLagerStatus()=="Leaflager"){
			liste.add(lager.getKapazitaet());
		}
		for(Lager l : lager.kindlager){
			durchlaufenKapazitaet(l, liste);
		}
	}
	/**
	 * Methode, die die Best�nde in den Kindlagern addiert. Diese Methode wird genutzt, um den Bestand der Elternlager zu berechnen, da diese die
	 * Best�nde der Kindlager erhalten.

	 * @return den Gesamtbestand der Kindlager als int.
	 */
	public int durchlaufenBestand(){
		int bestand = 0;
		ArrayList<Integer> summebestand = new ArrayList<Integer>();
		durchlaufenBestand(this, summebestand);
		for(int i : summebestand){
			bestand = bestand + i;
		}
		return bestand;
	}	
	/**
	 * rekursive Hilfsmethode f�r die Methode durchlaufenBestand().
	 * 
	 * @param lager Elternlager, dass die Best�nde der zugeh�rigen Leaflager erh�lt.
	 * @param liste Arraylist f�r die Zwischenspeicherung der Best�nde.
	 */
	private void durchlaufenBestand(Lager lager, ArrayList<Integer> liste){
		if(lager.getLagerStatus()=="Leaflager"){
			liste.add(lager.getBestand());
		}
		for(Lager l : lager.kindlager){
			durchlaufenBestand(l, liste);
		}	
	}
	public void durchlaufenKindlager(Lager lager, ArrayList<Lager> liste){
		liste.add(lager);
		for(Lager l : lager.kindlager){
			durchlaufenKindlager(l, liste);
		}

	}
	public void durchlaufenElternlager(Lager lager, ArrayList<Lager> liste){
		Lager elternlager = lager.getElternlager();
		if(elternlager!=null){
			liste.add(elternlager);
			durchlaufenElternlager(elternlager, liste);
		}
	}
	public ArrayList<Buchung> getBuchungsliste() {
		return buchungsliste;
	}
	public void addBuchung(Buchung buchung) {
		this.buchungsliste.add(buchung);
	}
}		
