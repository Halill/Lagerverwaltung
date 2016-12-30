package model;
/**
 * Die Klasse Model im Package model.
 * 
 * In dieser Klasse wird die Lagerstruktur festgelegt. Hier sind Methoden, die diese verändern.
 */
import java.util.ArrayList;


public class Model{
	
	/**Liste aller Lager, die in der Lagerstruktur enthalten sind.*/
	private ArrayList<Lager> lagerliste = new ArrayList<Lager>();
	/**Liste aller Buchungen, die je mit der Anwendung erstellt worden sind. */
	private ArrayList<Buchung> buchungliste = new ArrayList<Buchung>();
	/**
	 * Die zentrale Methode, mit der neue Lager angelegt werden. 
	 * Zunächst werden nur Name, Kapazität und Bestand festgelegt. Die Beziehungen zwischen den Lagern entsteht erst im nachhinein.
	 * Dies trifft nur auf Leaflager (siehe Definition {@link Lager#setLagerStatus()})zu.
	 * Bei Root- und Treelager werden Bestand und Kapazität erst im nachhinein durch die Methoden {@link Lager#durchlaufenKapazitaet()} und {@link Lager#durchlaufenBestand()}
	 * gesetzt, da diese die Summe ihrer Kindlager erhalten und diese bei Neuanlage noch nicht bekannt sind.
	 * <br><br>
	 * Der Grund, warum hier auf einen Konstruktor für die Klasse Lager verzichtet wurde, ist der, das nach Anlage eines Lagers dieses in die Lagerliste hinzugefügt wird und diese Lagerliste
	 * in der Klasse Model vorhanden ist und nicht in der Klasse Lager. <br>Und der Grund warum die Lagerliste ein Attribut der Klasse Model und nicht der Klasse Lager ist, ist der, dass 
	 * in der Klasse Model die Lagerstruktur verwaltet wird und in der Klasse Lager das jeweilige spezifische Lager.
	 * @param name setzt den Namen des Lagers als String
	 * @param kapazitaet setzt die Kapazität des Lagers als int
	 * @param bestand setzt den Bestand des Lagers als int
	 * @return gibt ein Lagerobjekt zurück
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
	 * Methode, die eine komplette Lagerstruktur übergibt.
	 * @param lagerliste
	 */
	public void setLagerliste(ArrayList<Lager> lagerliste) {
		this.lagerliste = lagerliste;
	}
	/**
	 * Methode zum einfügen eines Lagers zwischen zwei vorhandenen Lager. <br>
	 * Vorbedingungen:<br>
	 * Das einzufügende Lager muss angelegt sein.
	 * @param elternlager das Lager, dass über dem neuen Lager liegen soll
	 * @param kindlager das Lager, dass unter dem neuen Lager liegen soll
	 * @param neueslager das neue Lager
	 */
	public void neuesLagereinfuegen(Lager elternlager, Lager kindlager, Lager neueslager){
		//Das neue Lager wird über das Kindlager eingefügt
		neueslager.setKindlager(kindlager);
		//Das neue Lager wird unter dem Elternlager eingefügt
		elternlager.setKindlager(neueslager);
		elternlager.deleteKindlager(kindlager);
		
		neueslager.setKapazitaet(kindlager.getKapazitaet());
		neueslager.setBestand(kindlager.getBestand());	
	}
	/**
	 * Methode zum einfügen eines Lagers über einem Lager(also dem Rootlager), oder unter einem Lager (also einem Leaflager).
	 * @param lager das Lager, dass entweder über oder unter dem neuen Lager liegt
	 * @param neueslager das neue Lager
	 */
	public void neuesLagereinfuegen(Lager lager, Lager neueslager){
		//Fall 3
		if(lager.getKindlager().isEmpty()) lager.setKindlager(neueslager);
		//Fall 1
		else neueslager.setKindlager(lager);
		
		//in beiden Fällen wird Kapazitaet und der Bestand vom anderen Lager übernommen
		neueslager.setKapazitaet(lager.getKapazitaet());
		neueslager.setBestand(lager.getBestand());
	}
	/**
	 *  Mit Hilfe dieser Methode werden Leaflager gelöscht, also Lager am untersten Ende der Hierarchie. (Fall 1)
	 *  Das Lager wird aus der Lagerliste gelöscht und wird aus der Kindlagerliste seines Elternlagers gelöscht.
	 *  Zusätlich werden Bestand und Kapazität des Elternlagers neuberechnet.
	 * @param lager das zu löschende Lager
	 */
	public void lagerLoeschen(Lager lager){
		Lager elternlager = lager.getElternlager();
		elternlager.deleteKindlager(lager);
		lagerliste.remove(lager);
		elternlager.setKapazitaet(elternlager.durchlaufenKapazitaet());
		elternlager.setBestand(elternlager.durchlaufenBestand());	
	}
	/**
	 * Mit dieser Methode werden zwei Fälle des Löschens abgedeckt. <br>
	 * - Löschen des Rootlager:<br>
	 * Das Rootlager wird aus der Lagerliste gelöscht, sowie werden Beziehungen zu Kindlagern durch Beziehungen zum Ersatzlager ersetzt.
	 * Das Ersatzlager wird aus der Kindlagerliste des Rootlagers gelöscht.
	 * Zusätzlich werden Bestand und Kapazität des Ersatzlagers berechnet. Damit das Ersatzlager ein Rootlager wird, wird das Elternlager vom Ersatzlager auf null gesetzt.
	 * <br>
	 * - Löschen eines Treelagers (hierzu siehe Definition {@link Lager#setLagerStatus()}:<br>
	 * Ähnlicher Ablauf wie beim Fall des Rootlagers. Hier wird aber das zu löschende Lager aus der Kindlagerliste seines Elternlagers durch das Ersatzlager ersetzt.
	 * @param ersatzlager das Lager, dass das zu löschende Lager ersetzt.
	 * @param lager das zu löschende Lager
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
	 * Methode, die den Restbestand eines Rootlagers errechnet, also der maximal buchbaren Restkapazitäten 
	 * @param lagerliste benötigt hierzu die Gesamtlagerliste vom Model, kann auch je nach Bedarf für Treelager verwendet werden.
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
	 *  Lagername, Kapazität, Bestand, Lagerstatus, Kindlager, Elternlager
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
	 * @param lagerliste Lagerliste mit Lagern. Wird genutzt für die Sortierung der Gesamtlagerliste im Model
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
		
		lagerAnlegen("Großbritannien", 5000, 4500);
	
		for(Lager l : lagerliste){
			l.setLagerStatus();
		}
		lagerliste = sortiereLagerliste(lagerliste);
	}
	/**
	 * Prüft, ob die Menge einer Buchung nicht die Restkapazitäten des Rootlagers übersteigen
	 * @param menge Buchungsmenge einer Buchung
	 * @return gibt true aus, falls die Prüfung erfolgreich war (also das die Buchungsmenge kleinergleich dem Restbestand ist)
	 */
	public Boolean pruefeBuchungsmenge(int menge){
		if(addiereRestLagerBestand(lagerliste)<=menge) return false;
		else return true;
	}
	/**
	 * Erstellt eine neue Buchung. Zunächst wird hier nur die Menge festgelegt.
	 * Da die Auswahl der Lager dynamisch im Fenster der View generiert werden, werden hier noch keine Lager eingegeben. Das Datum einer Buchung wird aus bei der Methode
	 * {@link Model#fuehreBuchungenaus()} gesetzt, da dann die tatsächliche Buchung stattfindet.
	 * Der Buchungsschlüssel und der Buchungstyp werden auch erst im Fenster festgelegt.
	 * @param menge Menge einer Buchung
	 * @return gibt eine neues Buchungs-Objekt aus
	 */
	public Buchung neueBuchung(int menge){
		Buchung buchung = new Buchung();
		if(pruefeBuchungsmenge(menge)) buchung.setMenge(menge);
		buchungliste.add(buchung);
		return buchung;
	}
	/**
	 * Gibt die Gesamtbuchungsliste wieder
	 * @return Gesamtbuchungsliste der Model-Klasse
	 */
	public ArrayList<Buchung> getBuchungliste() {
		return buchungliste;
	}
	/**
	 * Setter-Methode zur Gesamtbuchungsliste.
	 * @param buchungliste Buchungsliste, die die aktuelle Buchungsliste überschreibt
	 */
	public void setBuchungliste(ArrayList<Buchung> buchungliste) {
		this.buchungliste = buchungliste;
	}
	/**
	 * System.out.println() einer Buchungsliste
	 * @param buchungliste hier wird hauptsächlich die Gesamtbuchungsliste verwendet, oder die spezifischen Buchungslisten der Lager
	 */
	public void sysoBuchungsliste(ArrayList<Buchung> buchungliste){
		for(Buchung b : buchungliste){
			System.out.println("Buchungstyp: " + b.getBuchungstyp());
			System.out.println("Buchungsmenge: " + b.getMenge());
			System.out.println("Buchungsdatum: " + b.getDatum());
		}
	}
	/**
	 * Diese Methode führt die Buchungen aus. Hier wird das Buchungsdatum und die Buchungszeit gesetzt.
	 * Hier wird unterschieden, ob es sich um eine Zubuchung oder um eine Abbuchung handelt.
	 */
	public void fuehreBuchungenaus(){
		for(Buchung b : buchungliste){
			if(pruefeBuchungsmenge(b.getMenge()) && b.getBuchungstyp()=="Zubuchung"){
				b.zubuchen();
				b.setDatum();
			}
			if(pruefeBuchungsmenge(b.getMenge()) && b.getBuchungstyp()=="Abbuchung"){
				b.abbuchen();
				b.setDatum();
			}
		}
	}
	

}