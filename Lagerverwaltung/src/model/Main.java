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

//		Test f�r Fall 1: Das neue Lager wird �ber Deutschland angelegt
//		m.neuesLagereinfuegen(lagerl.get(9), neuesLager);
		
//		Test f�r Fall 2: Das neue Lager wird zwischen Deutschland und MV eingef�gt
//		m.neuesLagereinfuegen(lagerl.get(9), lagerl.get(8), neuesLager);
		
//		Test f�r Fall 3: Das neue Lager wird unter MV eingef�gt
//		m.neuesLagereinfuegen(lagerl.get(8), neuesLager);
		
		m.sysoLagerstruktur(lagerl);

		
	}
}