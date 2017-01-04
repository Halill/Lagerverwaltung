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
		File_Manager filemanager = new File_Manager();
		ArrayList<Lager> lagerliste = filemanager.load_inventory();
		m.sysoLagerstruktur(lagerliste);
	}

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

	//hier wird die Lagerstruktur generiert
	//m.legeInitialeStrukturFest();
	
	
	//Das wird alles für eine Buchung benötigt:
	//Buchung b = m.neueBuchung(500);
	//b.fuegeLagerzuBuchungHinzu(m.getLagerliste().get(6));
	//b.fuegeLagerzuBuchungHinzu(m.getLagerliste().get(7));
	//b.fuegeLagerzuBuchungHinzu(m.getLagerliste().get(8));
	//b.setBuchungstyp(Buchung.ZUBUCHUNG);
	//Gesamtbuchungsliste des Models, in der alle Buchungen enthalten sind:
	//m.sysoBuchungsliste(m.getBuchungliste());
	//Liste von Lagern die zur Buchung b gehören:
	//m.sysoLagerstruktur(b.getBuchungLagerListe());
	
	
	//Festlegen eines Schlüssels für Verteilung:
	//Double[] schluessel = new Double[]{0.25,0.2,0.55};
	//b.setVerteilungsschluessel(schluessel);
	//Fuehrt die Buchung schlussendlich aus:
	//m.fuehreBuchungenaus();

	
	//ArrayList<Buchung> buchungsliste = m.getLagerliste().get(6).getBuchungsliste();
	//Buchungsliste des Lagers Hessen:
	//m.sysoBuchungsliste(buchungsliste);

	

	//hier ist die gesamte Lagerliste für die View
	//m.getLagerliste();

	//Methode für mich zum überprüfen, ob die richtige Lagerstruktur erscheint.
	//m.sysoLagerstruktur(m.getLagerliste());
	

