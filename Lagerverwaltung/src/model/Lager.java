package model;

import java.util.ArrayList;

public class Lager {
	
	private String name;
	private int kapazitaet;
	private int bestand;
	private Lager elternlager;
	private ArrayList<Lager> kindlager = new ArrayList<Lager>();
	private static int ROOTLAGER = 0, TREELAGER = 1, LEAFLAGER = 2; 
	
	
	/*Erklärung der Lagerstatus:
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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

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