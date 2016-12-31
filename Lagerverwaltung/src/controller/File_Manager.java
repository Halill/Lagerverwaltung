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
	
	public void save_inventory(ArrayList<Lager> lager)
	{
		Date date = new Date();
		DateFormat df;
		df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
		df.format(date);
		
		File file = new File(System.getProperty("user.dir") + "\\Resources");
		file.mkdir();
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("Resources\\save-" + date.toString().replace(":", "-") + ".txt"));
			for (int i = 0; i < lager.size(); i++) {
				System.out.println(lager.get(i).getName() + "|" + lager.get(i).getKapazitaet() + 
						"|" + lager.get(i).getBestand() + "|" + lager.get(i).getLagerStatus() + "|" +
						getEltern(lager.get(i).getElternlager()) + "|" + getKinder(lager.get(i).getKindlager()));
		
				    String content = lager.get(i).getName() + "|" + lager.get(i).getKapazitaet() + 
							"|" + lager.get(i).getBestand() + "|" + lager.get(i).getLagerStatus() + "|" +
							getEltern(lager.get(i).getElternlager()) + "|" + getKinder(lager.get(i).getKindlager()) +"\n";
			

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
		{
			eltern = lager.getName();
		}
		return eltern;
	}
	
	private String getKinder(ArrayList<Lager> kindlager) {
		
		String kinder = "keine Kindlager vorhanden"; 
		if (kindlager != null) 
		{
			for (int i = 0; i < kindlager.size(); i++) {
				if (kinder == "")
					kinder = kindlager.get(i).getName();
				else
					kinder = kinder + "," + kindlager.get(i).getName();
			} 
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
		
		ArrayList<Lager> lager = new ArrayList<Lager>();
		
	    FileReader fr;
		try {
			fr = new FileReader(path);
		    BufferedReader br = new BufferedReader(fr);
		    String zeile = "";
		    Lager l;
		    Model m = new Model();
		    
		    while( (zeile = br.readLine()) != null ) {
		    	
		    	
		    	l = m.lagerAnlegen(zeile.split("|")[0], Integer.parseInt(zeile.split("|")[1]), Integer.parseInt(zeile.split("|")[2]));
		    	l = new Lager();
		    	
//		    	l.setName(zeile.split("|")[0]);
//		    	l.setKapazitaet(zeile.split("|")[1]);
//		    	l.setBestand(zeile.split("|")[2]);   		    	
//		    	l.setElternlager(zeile.split("|")[3]);
		    	
		    	
		    	lager.add(l);	
		    	System.out.println(zeile);
			}

		    br.close();
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    
		
		return null;
	}

}
