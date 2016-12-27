package model;
/**
 * @author Halil
 * Die Klasse Lager im Package Model.
 * 
 * In dieser Klasse sind Methoden enthalten, die die Lager-Attribute verändern und ausgeben können.
 */
import java.util.ArrayList;

public class Lager {
	
	/** Attribut Name*/
	private String name;
	/** Attribut Kapazität*/
	private int kapazitaet;
	/** Attribut Bestand*/
	private int bestand;
	/** Attribut Elternlager. Setzt die Beziehung zu einem "höheren" Lager.*/
	private Lager elternlager;
	/** Attribut Kindlager. Eine Liste, die die Lager unter diesem Lager enthält.*/
	private ArrayList<Lager> kindlager = new ArrayList<Lager>();
	/** 
	 * @author Halil
	 * Attribut Lagerstatus.
	 * Erklärung der Lagerstatus:
	 * Rootlager:
	 * Das Lager an der obersten Stelle in der Hierarchie
	 * 
	 * Treelager:
	 * Die Lager, die zwischen dem Rootlager und den Leaflagern liegen
	 * 
	 * Leaflager:
	 * Die Lager, an der untersten Stelle in der Hierarchie
	 * 
	 */
	private static int ROOTLAGER = 0, TREELAGER = 1, LEAFLAGER = 2; 
	
	
	/**
	 * @author Halil
	 * Methode zum setzen des Lagerstatus. Kann erst nachdem die Lagerstruktur aufgebaut wurde, ausgeführt werden, da sonst Fehler entstehen.
	 * Deswegen wird diese Methode in der Methode der Klasse Model @see {@link Model#legeInitialeStrukturFest()} erst nachdem alle Lager angelegt wurden aufgerufen.
	 * 
	 * @return Setzt den Lagerstatus eines Lagers. Trifft keine Bedingung ein, so wird ein anderer Wert (5) ausgegeben.
	 * 
	 */
	public int setLagerStatus(){
		if(this.elternlager==null && this.kindlager.isEmpty()==false){
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
	 * @author Halil
	 * Methode zum ausgeben des Lagerstatus. Kann erst nachdem die Methode @see {@link Lager#setLagerStatus()} ausgeführt werden, da beim Anlegen dieser Wert noch nicht gesetzt wurde.
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
	 * ausgeführt werden, da sonst der Lagername noch nicht gesetzt wurde.
	 * Diese Methode wird hauptsächlich in der Methode @see {@link Model#sysoLagerstruktur(ArrayList)} verwendet.
	 * @return gibt den Lagernamen als String aus.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @author Halil
	 * 
	 * Methode zum setzen des Lagernamens. Wird hauptsächlich in der Methode @see {@link Model#lagerAnlegen(String, int, int)} verwendet.
	 * @param name der Name eines Lagers wird per String gesetzt.
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return
	 */
	public int getKapazitaet() {
		return kapazitaet;
	}
	public void setKapazitaet(int kapazitaet) {
			this.kapazitaet = kapazitaet; 
	}
	public int getBestand() {
		return bestand;
	}
	public void setBestand(int bestand) {

		this.bestand = bestand;
	}
	
	public Lager getElternlager() {
		return elternlager;
	}
	
	public ArrayList<Lager> getKindlager() {
		return kindlager;
	}
	
	//setzen der Beziehung Kind-Eltern und Eltern-Kind
	public void setKindlager(Lager kindlager) {
		this.kindlager.add(kindlager);
		kindlager.elternlager = this;
	}
	
	public void deleteKindlager(Lager l){
		for(int i = 0; i < this.kindlager.size(); i++){
			if(this.kindlager.get(i)==l){
				this.kindlager.remove(i);
			}
		}
	}
	
	public  int durchlaufenKapazitaet(){
		int kapazitaet = 0;
		for(Lager l : this.kindlager){
			kapazitaet = kapazitaet + l.kapazitaet;
		}
		return kapazitaet;
	}
	
	public int durchlaufenBestand(){
		int bestand = 0;
		for(Lager l : this.kindlager){
			bestand = bestand + l.bestand;
		}
		return bestand;
	}
	
	
//
//	public void durchlaufen(Lager lager){
//		System.out.println(lager.getName());
//		
//		for(Lager l : lager.kindlager){	
//			durchlaufen(l);	
//
//		}
//
//		
//	}
	
}