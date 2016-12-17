package model;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Model m = new Model();

		ArrayList<Lager> lagerl = new ArrayList<Lager>();
		m.legeInitialeStrukturFest();
		lagerl = m.getLagerliste();
		
		m.sysoLagerstruktur(lagerl);
		
		Lager neuesLager = m.lagerAnlegen("neues Lager", 0, 0);

//		Test für Fall 1: Das neue Lager wird über Deutschland angelegt
//		m.neuesLagereinfuegen(lagerl.get(9), neuesLager);
		
//		Test für Fall 2: Das neue Lager wird zwischen Deutschland und MV eingefügt
//		m.neuesLagereinfuegen(lagerl.get(9), lagerl.get(8), neuesLager);
		
//		Test für Fall 3: Das neue Lager wird unter MV eingefügt
//		m.neuesLagereinfuegen(lagerl.get(8), neuesLager);
		
		m.sysoLagerstruktur(lagerl);

		
	}
}