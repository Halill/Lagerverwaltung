package controller;


import java.util.ArrayList;

import model.Lager;
import view.Buchen;
import view.Deliverys;
import view.Warehouse;
import view.Welcome;
/**
 * Die Klasse Controller beinhaltet die Main-Methode, in der initial das Fenster "Welcome" ge�ffnet wird. Des Weiteren sind Methoden zur Steuerung der einzelnen
 * Fenster vorhanden.
 * @author Halil
 *
 */
public class Controller {

	
	private static Controller instance = new Controller();
	Warehouse warehouse;
	/**
	 * Main-Methode, in der das Fenster "Welcome" initilialisiert wird.
	 * @param arg0 Argumente die �bergeben werden k�nnen
	 */
	public static void main(String[] arg0){
		
		Welcome welcome = new Welcome();
		welcome.frame.setVisible(true);

	}
	/**
	 * Methode zum erstellen eines Warehouse Fensters. Diese Methode wird genommen, falls Warehouse zum ersten mal aufgerufen wurde. Der Aufruf von Warehouse <br>
	 * wird in der Warehouse Klasse selbst detaillierter beschrieben.
	 */
	public void openWarehouse()
	{
		warehouse = new Warehouse();
		warehouse.frame.setVisible(true);		
		ObserverTree.getInstance().addObserver(warehouse);
	}
	/**
	 * Methode zum erstellen eines Warehouse Fensters. Diese Methode wird benutzt, wenn die Lagerstruktur sich ver�ndert hat.
	 * @param lager die ArrayList in der sich die Lagerstruktur befindet
	 */
	public void openWarehouse(ArrayList<Lager> lager)
	{
		warehouse = new Warehouse(lager);
		warehouse.frame.setVisible(true);		
		ObserverTree.getInstance().addObserver(warehouse);
	}
	/**
	 * Methode zum erstellen eines Buchen Fensters.
	 */
	public void openBuchen()
	{
		Buchen window = new Buchen(warehouse);
		window.frame.setVisible(true);		
		ObserverTree.getInstance().addObserver(window);
	}
	/**
	 * Methode zum erstellen eines Delivery Fensters.
	 */
	public void openDelivery()
	{
		Deliverys window = new Deliverys();
		window.frame.setVisible(true);;
	}
	/**
	 *  Getter-Methode f�r das Attribut instance vom Typ Controller. Wird ben�tigt, um den Wechsel zwischen Fenster zu erm�glichen.
	 * @return gibt die aktelle instance aus
	 */
	public static Controller getInstance() {
		return instance;
	}
	/**
	 * Setter-Methode f�r das Attribut instance vom Typ Controller.
	 * @param instance setzt die aktuelle instance
	 */
	public static void setInstance(Controller instance) {
		Controller.instance = instance;
	}

}	

	//Halils Sammelkiste zum testen diverser Methoden:
	
	//Lager neuesLager = m.lagerAnlegen("neues Lager", 0, 0);

	//Test f�r Fall 1: Das neue Lager wird �ber Deutschland angelegt
	//m.neuesLagereinfuegen(m.getLagerliste().get(9), neuesLager);

	//Test f�r Fall 2: Das neue Lager wird zwischen Deutschland und MV eingef�gt
	//m.neuesLagereinfuegen(m.getLagerliste().get(9), m.getLagerliste().get(8), neuesLager);
	
	//Test f�r Fall 3: Das neue Lager wird unter MV eingef�gt
	//m.neuesLagereinfuegen(m.getLagerliste().get(8), neuesLager);
	
	//Test f�r Fall 1: Das Rootlager wird gel�scht und MV wird neues Rootlager
	//m.lagerLoeschen(m.getLagerliste().get(8), m.getLagerliste().get(9));
	
	//Test f�r Fall 2: Das Lager Niedersachsen wird gel�scht und das Lager Hannover-Misburg wird es ersetzen.
	//m.lagerLoeschen(m.getLagerliste().get(0), m.getLagerliste().get(2));
	
	//Test f�r Fall 3: Das Lager MV wird gel�scht
	//m.lagerLoeschen(m.getLagerliste().get(8));

	//hier wird die Lagerstruktur generiert
	//m.legeInitialeStrukturFest();
		

	//hier ist die gesamte Lagerliste f�r die View
	//m.getLagerliste();

	//m.legeInitialeStrukturFest();
	//File_Manager filemanager = new File_Manager();
	//ArrayList<Lager> lagerliste = filemanager.load_inventory();
	
	//File_Manager filemanager1 = new File_Manager();
	//ArrayList<Lager> lagerliste1 = filemanager1.load_inventory();
	//m.setLagerliste(lagerliste1);
	
	//m.sysoLagerstruktur(lagerliste1);	

