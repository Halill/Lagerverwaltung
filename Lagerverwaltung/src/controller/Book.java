package controller;

import model.Lager;

public class Book implements CommandManager {
			
		private Lager l;
		private int menge;		
		
	    public void undo() 
	    {
	    	l.setBestand(l.getBestand() + menge);
	    	System.out.println(menge + " wurde von dem Lager abgezogen");
	    }
	    public int redo() 
	    {	    	
	    	l.setBestand(l.getBestand() - menge);
	    	System.out.println(menge + " wurde auf das Lager gebucht");
	    	return menge;
	    }
	    public void execute(Lager lager)
	    {
	    	if(lager == null)
	    	{
	    		System.out.println("Befehl konnte nicht ausgeführt werden");
	    		return;
	    	}
	    	l = lager;
	    	lager.setBestand(lager.getBestand() + menge);
	    	System.out.println(menge + " wurde verteilt");
	    }
	    
	    public Lager getLager()
	    {
	    	return l;
	    }
	    
		public int getMenge() {
			return menge;
		}
		public void setMenge(int menge) {
			this.menge = menge;
		}

		public int getUnits() {

			return menge;
		}
		
	    
}
