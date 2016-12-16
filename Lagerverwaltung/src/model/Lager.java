package model;

import java.util.ArrayList;

public class Lager {
	
	private String name;
	private int kapazitaet;
	private int bestand;
	private Lager elternlager;
	private ArrayList<Lager> kindlager = new ArrayList<Lager>();
	
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