package model;

import java.util.ArrayList;

public class Model{
	
	private ArrayList<Lager> lagerliste = new ArrayList<Lager>();
	
	public Lager lagerAnlegen(String name, int kapazitaet, int bestand){
		Lager lager = new Lager();
		lager.setName(name);
		lager.setKapazitaet(kapazitaet);
		lager.setBestand(bestand);
		lagerliste.add(lager);
		return lager;
	}

	public ArrayList<Lager> getLagerliste() {
		return lagerliste;
	}

	public void setLagerliste(ArrayList<Lager> lagerliste) {
		this.lagerliste = lagerliste;
	}
	//Lager Einfügen Methode für Fall 2: so wird das neue Lager zwischen zwei Lagern eingefügt
	public void neuesLagereinfuegen(Lager elternlager, Lager kindlager, Lager neueslager){
		//Das neue Lager wird über das Kindlager eingefügt
		neueslager.setKindlager(kindlager);
		//Das neue Lager wird unter dem Elternlager eingefügt
		elternlager.setKindlager(neueslager);
		elternlager.deleteKindlager(kindlager);
		
		neueslager.setKapazitaet(kindlager.getKapazitaet());
		neueslager.setBestand(kindlager.getBestand());	
	}
	
	//Lager Einfügen Methode für Fall 1 und 3
	public void neuesLagereinfuegen(Lager lager, Lager neueslager){
		//Fall 3
		if(lager.getKindlager().isEmpty()) lager.setKindlager(neueslager);
		//Fall 1
		else neueslager.setKindlager(lager);
		
		//in beiden Fällen wird Kapazitaet und der Bestand vom anderen Lager übernommen
		neueslager.setKapazitaet(lager.getKapazitaet());
		neueslager.setBestand(lager.getBestand());
	}
	
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
			}
			else{
				System.out.println(" , Kindlager: keine Kindlager vorhanden");
			}


		}
		
	}
	public void legeInitialeStrukturFest(){
		
		Lager hannover = lagerAnlegen("Hannover-Misburg", 1000, 500);
		Lager nienburg = lagerAnlegen("Nienburg", 500, 300);
		Lager niedersachsen = new Lager();
		niedersachsen = lagerAnlegen("Niedersachsen", 0, 0);
		niedersachsen.setKindlager(nienburg);
		niedersachsen.setKindlager(hannover);
		niedersachsen.setKapazitaet(niedersachsen.durchlaufenKapazitaet());
		niedersachsen.setBestand(niedersachsen.durchlaufenBestand());
		Lager nrw = lagerAnlegen("NRW", 15000, 5000);
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
		deutschland.setBestand(deutschland.durchlaufenBestand());
		for(Lager l : lagerliste){
			l.setLagerStatus();
		}
		
	}

	
// 	hier wird eine zufällige Struktur generiert
//	public void generiereStruktur(){
//		int durchlaufe = (int) (Math.random()*1000);
//		while(durchlaufe != 0){
//			int kapazitaet = (int) (Math.random()*1000);
//			int bestand = (int) (Math.random()*100);
//			int eltern = (int) (Math.random()*durchlaufe);
//			int kind = (int) (Math.random()*durchlaufe);
//			if(eltern == kind && kapazitaet >= bestand){
//			lagerAnlegen("Name"+durchlaufe,kapazitaet,bestand,"Name"+eltern,"Name"+kind);
//			}
//			durchlaufe = durchlaufe - 1;
//		}
//		
//		
//	}

}