package model;

import java.util.ArrayList;

/**
 * Die Klasse Model im Package model.
 * 
 * In dieser Klasse wird die Lagerstruktur festgelegt. Hier sind Methoden, die diese ver�ndern.
 */
public class Model{
	
	/**Liste aller Lager, die in der Lagerstruktur enthalten sind.*/
	private ArrayList<Lager> lagerliste = new ArrayList<Lager>();
	/**Liste aller Buchungen, die je mit der Anwendung erstellt worden sind. */
	/**
	 * Die zentrale Methode, mit der neue Lager angelegt werden. 
	 * Zun�chst werden nur Name, Kapazit�t und Bestand festgelegt. Die Beziehungen zwischen den Lagern entsteht erst im nachhinein.
	 * Dies trifft nur auf Leaflager (siehe Definition {@link Lager#setLagerStatus()})zu.
	 * Bei Root- und Treelager werden Bestand und Kapazit�t erst im nachhinein durch die Methoden {@link Lager#durchlaufenKapazitaet()} und {@link Lager#durchlaufenBestand()}
	 * gesetzt, da diese die Summe ihrer Kindlager erhalten und diese bei Neuanlage noch nicht bekannt sind.
	 * <br><br>
	 * Der Grund, warum hier auf einen Konstruktor f�r die Klasse Lager verzichtet wurde, ist der, das nach Anlage eines Lagers dieses in die Lagerliste hinzugef�gt wird und diese Lagerliste
	 * in der Klasse Model vorhanden ist und nicht in der Klasse Lager. <br>Und der Grund warum die Lagerliste ein Attribut der Klasse Model und nicht der Klasse Lager ist, ist der, dass 
	 * in der Klasse Model die Lagerstruktur verwaltet wird und in der Klasse Lager das jeweilige spezifische Lager.
	 * @param name setzt den Namen des Lagers als String
	 * @param kapazitaet setzt die Kapazit�t des Lagers als int
	 * @param bestand setzt den Bestand des Lagers als int
	 * @return gibt ein Lagerobjekt zur�ck
	 */
	public Lager lagerAnlegen(String name, int kapazitaet, int bestand){
		Lager lager = new Lager();
		lager.setName(name);
		lager.setKapazitaet(kapazitaet);
		lager.setBestand(bestand);
		lagerliste.add(lager);
		return lager;
	}
	/**
	 * Methode, die die Lagerliste ausgibt.
	 * @return Lagerliste als Arraylist
	 */
	public ArrayList<Lager> getLagerliste() {
		return lagerliste;
	}
	/**
	 * Methode, die eine komplette Lagerstruktur �bergibt.
	 * @param lagerliste Lagerliste, die die aktuelle Lagerliste �berschreibt
	 */
	public void setLagerliste(ArrayList<Lager> lagerliste) {
		this.lagerliste = lagerliste;
	}
	/**
	 * Methode zum einf�gen eines Lagers zwischen zwei vorhandenen Lager. <br>
	 * Vorbedingungen:<br>
	 * Das einzuf�gende Lager muss angelegt sein.
	 * @param elternlager das Lager, dass �ber dem neuen Lager liegen soll
	 * @param kindlager das Lager, dass unter dem neuen Lager liegen soll
	 * @param neueslager das neue Lager
	 */
	public void neuesLagereinfuegen(Lager elternlager, Lager kindlager, Lager neueslager){
		//Das neue Lager wird �ber das Kindlager eingef�gt
		neueslager.setKindlager(kindlager);
		//Das neue Lager wird unter dem Elternlager eingef�gt
		elternlager.setKindlager(neueslager);
		elternlager.deleteKindlager(kindlager);
		
		neueslager.setKapazitaet(kindlager.getKapazitaet());
		neueslager.setBestand(kindlager.getBestand());	
	}
	/**
	 * Methode zum einf�gen eines Lagers �ber einem Lager(also dem Rootlager), oder unter einem Lager (also einem Leaflager).
	 * @param lager das Lager, dass entweder �ber oder unter dem neuen Lager liegt
	 * @param neueslager das neue Lager
	 */
	public void neuesLagereinfuegen(Lager lager, Lager neueslager){
		//Fall 3
		if(lager.getKindlager().isEmpty()) lager.setKindlager(neueslager);
		//Fall 1
		else neueslager.setKindlager(lager);
		
		//in beiden F�llen wird Kapazitaet und der Bestand vom anderen Lager �bernommen
		neueslager.setKapazitaet(lager.getKapazitaet());
		neueslager.setBestand(lager.getBestand());
	}
	/**
	 *  Mit Hilfe dieser Methode werden Leaflager gel�scht, also Lager am untersten Ende der Hierarchie. (Fall 1)
	 *  Das Lager wird aus der Lagerliste gel�scht und wird aus der Kindlagerliste seines Elternlagers gel�scht.
	 *  Zus�tlich werden Bestand und Kapazit�t des Elternlagers neuberechnet.
	 * @param lager das zu l�schende Lager
	 */
	public void lagerLoeschen(Lager lager){
		Lager elternlager = lager.getElternlager();
		elternlager.deleteKindlager(lager);
		lagerliste.remove(lager);
		elternlager.setKapazitaet(elternlager.durchlaufenKapazitaet());
		elternlager.setBestand(elternlager.durchlaufenBestand());	
	}
	/**
	 * Mit dieser Methode werden zwei F�lle des L�schens abgedeckt. <br>
	 * - L�schen des Rootlager:<br>
	 * Das Rootlager wird aus der Lagerliste gel�scht, sowie werden Beziehungen zu Kindlagern durch Beziehungen zum Ersatzlager ersetzt.
	 * Das Ersatzlager wird aus der Kindlagerliste des Rootlagers gel�scht.
	 * Zus�tzlich werden Bestand und Kapazit�t des Ersatzlagers berechnet. Damit das Ersatzlager ein Rootlager wird, wird das Elternlager vom Ersatzlager auf null gesetzt.
	 * <br>
	 * - L�schen eines Treelagers (hierzu siehe Definition {@link Lager#setLagerStatus()}:<br>
	 * �hnlicher Ablauf wie beim Fall des Rootlagers. Hier wird aber das zu l�schende Lager aus der Kindlagerliste seines Elternlagers durch das Ersatzlager ersetzt.
	 * @param ersatzlager das Lager, dass das zu l�schende Lager ersetzt.
	 * @param lager das zu l�schende Lager
	 */
	public void lagerLoeschen(Lager ersatzlager, Lager lager){
		//Fall Rootlager
		if (lager.getLagerStatus()=="Rootlager"){
			for(Lager l : lager.getKindlager()){
				ersatzlager.setKindlager(l);
			}	
			lagerliste.remove(lager);	
			ersatzlager.deleteKindlager(ersatzlager);
			ersatzlager.setElternlager(null);
			ersatzlager.setKapazitaet(ersatzlager.durchlaufenKapazitaet());
			ersatzlager.setBestand(ersatzlager.durchlaufenBestand());
		}
		//Fall Treelager
		else{
			for(Lager l : lager.getKindlager()){
				ersatzlager.setKindlager(l);
			}	
			lagerliste.remove(lager);	
			ersatzlager.deleteKindlager(ersatzlager);
			lager.getElternlager().setKindlager(ersatzlager);
			lager.getElternlager().deleteKindlager(lager);

			ersatzlager.setKapazitaet(ersatzlager.durchlaufenKapazitaet());
			ersatzlager.setBestand(ersatzlager.durchlaufenBestand());
			
		}


	}
	/**
	 * Methode, die den Restbestand eines Rootlagers errechnet, also der maximal buchbaren Restkapazit�ten 
	 * @param lagerliste ben�tigt hierzu die Gesamtlagerliste vom Model, kann auch je nach Bedarf f�r Treelager verwendet werden.
	 * @return gibt den Restbestand eines Rootlagers aus
	 */
	public int addiereRestLagerBestand(ArrayList<Lager> lagerliste){
		int restbestand = 0;
		for(Lager l : lagerliste){
			if(l.getLagerStatus()=="Rootlager"){
				restbestand = restbestand + (l.getKapazitaet()-l.getBestand());
			}
		}
		return restbestand;
	}
	/**
	 *  System.out.println() der aktuellen Lagerstruktur
	 *  Es werden folgende Werte zu einem Lager ausgegeben:
	 *  Lagername, Kapazit�t, Bestand, Lagerstatus, Kindlager, Elternlager
	 * @param lagerliste die Lagerstruktur als Arraylist
	 */
	public void sysoLagerstruktur(ArrayList<Lager> lagerliste){
		for(Lager lager : lagerliste){
			System.out.println("\nLagername: " + lager.getName() + ", " + "Kapazitaet: " + lager.getKapazitaet() + "," +
					"Bestand: " + lager.getBestand() + "," + "Lagerstatus: " + lager.getLagerStatus());
			if(lager.getElternlager()!=null) System.out.print("Elternlager: " + lager.getElternlager().getName());
			else System.out.print("Elternlager: kein Elternlager vorhanden");
			if(lager.getKindlager().isEmpty()==false){
				System.out.print(" , Kindlager: ");
				for(Lager l : lager.getKindlager()){
					System.out.print(l.getName() + ",");
				}
				System.out.println();
			}
			else System.out.println(" , Kindlager: keine Kindlager vorhanden");
		}
		
	}	
	/**
	 * Sortiert eine Lagerliste nach folgendem Schema:
	 * Rootlager
	 * Treelager, alle Lager unter diesem Lager
	 * Treelager, alle Lager unter diesem Lager
	 * ...
	 * Leaflager (die direkt unter dem Rootlager liegen)
	 * Rootlager
	 * ...
	 * @param lagerliste Lagerliste mit Lagern. Wird genutzt f�r die Sortierung der Gesamtlagerliste im Model
	 * @return gibt eine sortierte Lagerliste wieder
	 */
	private ArrayList<Lager> sortiereLagerliste(ArrayList<Lager> lagerliste){
		ArrayList<Lager> lagerl = new ArrayList<Lager>();
		for(Lager lager : lagerliste){
			if(lager.getLagerStatus()=="Rootlager"){
				lager.durchlaufenKindlager(lager, lagerl);
			}
		}
		return lagerl;
	}
	/**
	 * Legt die initiale Lagerstruktur an. Hier werden alle Werte aller Lager und deren Beziehungen zueinander festgelegt.
	 * 
	 */
	public void legeInitialeStrukturFest(){	
		Lager hannover = lagerAnlegen("Hannover-Misburg", 1000, 500);
		Lager nienburg = lagerAnlegen("Nienburg", 500, 300);
		Lager niedersachsen = new Lager();
		niedersachsen = lagerAnlegen("Niedersachsen", 0, 0);
		niedersachsen.setKindlager(nienburg);
		niedersachsen.setKindlager(hannover);
		niedersachsen.setKapazitaet(niedersachsen.durchlaufenKapazitaet());
		niedersachsen.setBestand(niedersachsen.durchlaufenBestand());
		Lager nrw = lagerAnlegen("NRW", 25000, 5000);
		Lager bremen = lagerAnlegen("Bremen", 2000, 0);
		Lager hessen = lagerAnlegen("Hessen", 12500, 5000);
		Lager sachsen = lagerAnlegen("Sachsen", 5000, 1000);
		Lager brandenburg = lagerAnlegen("Brandenburg", 6000, 2000);
		Lager mv = lagerAnlegen("MV", 3000, 500);
		Lager deutschland = new Lager();
		deutschland = lagerAnlegen("Deutschland", 0,0);
		deutschland.setKindlager(niedersachsen);
		deutschland.setKindlager(nrw);
		deutschland.setKindlager(bremen);
		deutschland.setKindlager(hessen);
		deutschland.setKindlager(sachsen);
		deutschland.setKindlager(brandenburg);
		deutschland.setKindlager(mv);
		deutschland.setKapazitaet(deutschland.durchlaufenKapazitaet());
		deutschland.setBestand(deutschland.durchlaufenBestand()); //14300
		
		Lager paris = lagerAnlegen("Paris-Nord", 5000, 4500);
		Lager orleans = lagerAnlegen("Orleans", 5000, 4500);
		Lager marseille = lagerAnlegen("Marseille", 5000,4500);
		Lager nimes = lagerAnlegen("Nimes", 5000, 4500);
		Lager frankreich = new Lager();
		frankreich = lagerAnlegen("Frankreich",0,0);
		frankreich.setKindlager(paris);
		frankreich.setKindlager(orleans);
		frankreich.setKindlager(marseille);
		frankreich.setKindlager(nimes);
		frankreich.setKapazitaet(frankreich.durchlaufenKapazitaet());
		frankreich.setBestand(frankreich.durchlaufenBestand()); // 18000
		
		Lager mailand = lagerAnlegen("Mailand", 5000, 4500);
		Lager laquila = lagerAnlegen("L'Aquila", 5000, 4500);
		Lager italien = new Lager();
		italien = lagerAnlegen("Italien", 0, 0);
		italien.setKindlager(mailand);
		italien.setKindlager(laquila);
		italien.setBestand(italien.durchlaufenBestand()); // 9000
		italien.setKapazitaet(italien.durchlaufenKapazitaet());
		
		Lager spanien = lagerAnlegen("Spanien", 5000, 4500);
		
		Lager europa = new Lager();
		europa = lagerAnlegen("Europa",0,0);
		europa.setKindlager(frankreich);
		europa.setKindlager(italien);
		europa.setKindlager(spanien);
		europa.setKapazitaet(europa.durchlaufenKapazitaet());
		europa.setBestand(europa.durchlaufenBestand()); // 31500
		
		lagerAnlegen("Gro�britannien", 5000, 4500);	
		
		for(Lager l : lagerliste){
			l.setLagerStatus();
		}
		lagerliste = sortiereLagerliste(lagerliste);
	}
}