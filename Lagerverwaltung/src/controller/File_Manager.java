package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		String path = chooser.getSelectedFile().getAbsolutePath();
		
		ArrayList<Lager> lagerliste = new ArrayList<Lager>();
		
		
	    FileReader fr;
		try {
			fr = new FileReader(path);
		    BufferedReader br = new BufferedReader(fr);
		    String text = "";
		    Lager lager = new Lager();
		    Model m = new Model();

	    	String[] elternlager = new String[20];
	    	String[] kindlager = new String[20];
	    	int i = 1;

		

	    	while((text = br.readLine()) != null ) {

		    	String nkbek[]= text.split("/");
		    	String name = nkbek[0];
		    	int kapazitaet = Integer.parseInt(nkbek[1]);
		    	int bestand = Integer.parseInt(nkbek[2]);
		    	elternlager[i] = nkbek[4];
		    	kindlager[i] = nkbek[5];


		    	lager = m.lagerAnlegen(name, kapazitaet, bestand);
	
		    	lagerliste.add(lager);	


		    	i++;
			}
	    	br.close();
		    for(int e = 0;e<lagerliste.size();e++){
		    	String[] kinder = kindlager[e].split(",");
		    	for(int k = 0;k<lagerliste.size();k++){
		    		for(int j = 0; j<kinder.length;j++){
		    			if(kinder[j]==lagerliste.get(k).getName()){
		    				lagerliste.get(e).setKindlager(lagerliste.get(k));
		    			}
		    		}
		    	}
		    	lagerliste.get(e).setLagerStatus();
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