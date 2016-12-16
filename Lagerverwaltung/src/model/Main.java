package model;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Model m = new Model();
//		
//		String[] kindlager = new String[2];
//		kindlager[0] = "Lager2";
//		kindlager[1] = null;
//		m.lagerAnlegen("Lager1", 1000, 1000, null, kindlager);
//		m.lagerAnlegen("Lager2", 1000, 800, "Lager1", null);
//		
//
		System.out.println(m.getLagerliste());
		ArrayList<Lager> lagerl = new ArrayList<Lager>();
		m.legeInitialeStrukturFest();
		lagerl = m.getLagerliste();
		for(Lager lager : lagerl){
			System.out.println("\n\nLagername: " + lager.getName() + ", " + "Kapazitaet: " + lager.getKapazitaet() + "," +
					"Bestand: " + lager.getBestand() + ",");
			if(lager.getElternlager()!=null){
				System.out.print("Elternlager: " + lager.getElternlager().getName());
			}
			else{
				System.out.print("Elternlager: kein Elternlager vorhanden");
			}
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
}