package controller;


import java.util.ArrayList;


import model.Lager;
import model.Model;

public class Controller {

	public static void main(String[] arg0){
		Model m = new Model();

		//hier wird die Lagerstruktur generiert
		m.legeInitialeStrukturFest();
		
		//hier ist die gesamte Lagerliste für die View
		m.getLagerliste();

		//Methode für mich zum überprüfen, ob die richtige Lagerstruktur erscheint.
		m.sysoLagerstruktur(m.getLagerliste());



	}

	
	//Halils Sammelkiste:
	//ArrayList<Lager> buchungslagerliste = new ArrayList<Lager>();
	//buchungslagerliste.add(m.getLagerliste().get(0));
	//buchungslagerliste.add(m.getLagerliste().get(1));
	//m.neueBuchung(400, buchungslagerliste);
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

	//Double[] schluessel = new Double[]{0.5,0.5};
	//m.fuehreBuchungenaus(schluessel);
	//m.sysoBuchungsliste(m.getBuchungliste());
	//System.out.print(m.addiereRestLagerBestand(m.getLagerliste()));
	

}