package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;

import model.Lager;
import model.Model;

public class File_Manager
{
	
	public void save_inventory(ArrayList<Lager> lagerliste)
	{
		Date date = new Date();
		DateFormat df;
		df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
		df.format(date);
		
		File file = new File(System.getProperty("user.dir") + "\\Resources");
		file.mkdir();
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("Resources\\save-" + date.toString().replace(":", "-") + ".txt"));
			for (int i = 0; i < lagerliste.size(); i++) {
				System.out.println(lagerliste.get(i).getName() + "/" + lagerliste.get(i).getKapazitaet() + 
						"/" + lagerliste.get(i).getBestand() + "/" + lagerliste.get(i).getLagerStatus() + "/" +
						getEltern(lagerliste.get(i).getElternlager()) + "/" + getKinder(lagerliste.get(i).getKindlager()));
		
				    String content = lagerliste.get(i).getName() + "/" + lagerliste.get(i).getKapazitaet() + 
							"/" + lagerliste.get(i).getBestand() + "/" + lagerliste.get(i).getLagerStatus() + "/" +
							getEltern(lagerliste.get(i).getElternlager()) + "/" + getKinder(lagerliste.get(i).getKindlager()) +"\n";
			
					bw.write(content);
			}
			bw.close();
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getEltern(Lager lager) {
		
		String eltern = ""; 
		if (lager != null) 
			eltern = lager.getName();
		else
			eltern = "kein Elternlager vorhanden";
		return eltern;
	}
	
	private String getKinder(ArrayList<Lager> kindlager) {
		
		String kinder = ""; 
		if (kindlager.isEmpty() == false) 
		{
			for (int i = 0; i < kindlager.size(); i++) {
				if (kinder == "")
					kinder = kindlager.get(i).getName();
				else
					kinder = kinder + "," + kindlager.get(i).getName();
			} 
		}
		else{
			kinder = "keine Kindlager vorhanden";
		}
		return kinder;
	}

	public ArrayList<Lager> load_inventory()
	{
		File file = new File(System.getProperty("user.dir") + "\\Resources");
		file.mkdir();
        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "\\Resources");
        // Dialog zum Speichern von Dateien anzeigen
        chooser.showDialog(null, "Öffnen");
        if(chooser.getSelectedFile() == null)
        	return null;
		String path = chooser.getSelectedFile().getAbsolutePath();
		
		ArrayList<Lager> lagerliste = new ArrayList<Lager>();
		
		
	    FileReader fr;
		try {
			fr = new FileReader(path);
		    BufferedReader br = new BufferedReader(fr);
		    String text = "";
		    Lager lager = new Lager();
		    Model m = new Model();

	    	ArrayList<String> elternlager = new ArrayList<String>();
	    	ArrayList<String> kindlager = new ArrayList<String>();


	    	while((text = br.readLine()) != null ) {
		    	String nkbek[]= text.split("/");
		    	String name = nkbek[0];
		    	int kapazitaet = Integer.parseInt(nkbek[1]);
		    	int bestand = Integer.parseInt(nkbek[2]);
		    	elternlager.add(nkbek[4]);
		    	kindlager.add(nkbek[5]);


		    	lager = m.lagerAnlegen(name, kapazitaet, bestand);
	
		    	lagerliste.add(lager);	

			}
	    	br.close();
	    	ArrayList<Lager> hilfsliste = lagerliste;
	    	int i = 0;
	    	for(Lager l : lagerliste){
	    		String elternlagerl =  elternlager.get(i);
	    		for(Lager ll : hilfsliste){
	    			if(elternlagerl.equals(ll.getName())){
	    				ll.setKindlager(l);
	    			}
	    				
	    		}
	    		i++;
	    	}  
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	    
		
		return lagerliste;
	}

}