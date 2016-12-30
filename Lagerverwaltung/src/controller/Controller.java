package controller;


import java.util.ArrayList;

import model.Buchung;
import model.Lager;
import model.Model;

public class Controller {

	public static void main(String[] arg0){
		Model m = new Model();

		//hier wird die Lagerstruktur generiert
		m.legeInitialeStrukturFest();
		
		
		//Das wird alles für eine Buchung benötigt:
		ArrayList<Lager> buchungslagerliste = new ArrayList<Lager>();
		buchungslagerliste.add(m.getLagerliste().get(6));
		buchungslagerliste.add(m.getLagerliste().get(7));
		buchungslagerliste.add(m.getLagerliste().get(8));
		Buchung b = m.neueBuchung(20000, buchungslagerliste);
		b.setBuchungstyp(1);
		//m.sysoBuchungsliste(m.getBuchungliste());
		
		Double[] schluessel = new Double[]{0.25,0.2,0.55};
		m.fuehreBuchungenaus(schluessel);

		//hier ist die gesamte Lagerliste für die View
		m.getLagerliste();

		//Methode für mich zum überprüfen, ob die richtige Lagerstruktur erscheint.
		m.sysoLagerstruktur(m.getLagerliste());



	}

	
	//Halils Sammelkiste:
	
	//Lager neuesLager = m.lagerAnlegen("neues Lager", 0, 0);

	//Test für Fall 1: Das neue Lager wird über Deutschland angelegt
	//m.neuesLagereinfuegen(m.getLagerliste().get(9), neuesLager);

	//Test für Fall 2: Das neue Lager wird zwischen Deutschland und MV eingefügt
	//m.neuesLagereinfuegen(m.getLagerliste().get(9), m.getLagerliste().get(8), neuesLager);
	
	//Test für Fall 3: Das neue Lager wird unter MV eingefügt
	//m.neuesLagereinfuegen(m.getLagerliste().get(8), neuesLager);
	
	//Test für Fall 1: Das Rootlager wird gelöscht und MV wird neues Rootlager
	//m.lagerLoeschen(m.getLagerliste().get(8), m.getLagerliste().get(9));
	
	//Test für Fall 2: Das Lager Niedersachsen wird gelöscht und das Lager Hannover-Misburg wird es ersetzen.
	//m.lagerLoeschen(m.getLagerliste().get(0), m.getLagerliste().get(2));
	
	//Test für Fall 3: Das Lager MV wird gelöscht
	//m.lagerLoeschen(m.getLagerliste().get(8));

	//System.out.print(m.addiereRestLagerBestand(m.getLagerliste()));
	

}