package controller;

import model.Lager;
/**
 * 
 * Diese Klasse dient um gewisse Funktionen f�r das Buchen zu erm�glichen: <br>
 * - undo<br>
 * - redo<br>
 * - execute bzw. ausf�hren der eigentlichen Buchung<br>
 * Des Weitern sind diverse Getter und Setter f�r die Klassenattributte vorhanden. Au�erdem nutzt die Klasse ausschlie�lich das Interface CommandManager.
 *
 */

public class Book implements CommandManager {
			
		private Lager l;
		private int menge;		
		
		/**
		 * Undo Mechanismus. Die Buchung wird zur�ck von der Buchungsmenge auf das Lager gebucht.
		 */
	    public void undo() 
	    {
	    	l.setBestand(l.getBestand() + menge);
	    	System.out.println(menge + " wurde von dem Lager abgezogen");
	    }
	    /**
	     * Redo Mechanismus. Die Buchungsmenge wird vom Lager zur�ck in die Buchungsmenge �bertragen.
	     */
	    public int redo() 
	    {	    	
	    	l.setBestand(l.getBestand() - menge);
	    	System.out.println(menge + " wurde auf das Lager gebucht");
	    	return menge;
	    }
	    /**
	     * F�hrt die eigentliche Buchung aus. Es wird gepr�ft, ob ein Lager �bergeben wird, ansonsten wird nicht gebucht.
	     */
	    public void execute(Lager lager)
	    {
	    	if(lager == null)
	    	{
	    		System.out.println("Befehl konnte nicht ausgef�hrt werden");
	    		return;
	    	}
	    	l = lager;
	    	lager.setBestand(lager.getBestand() + menge);
	    	System.out.println(menge + " wurde verteilt");
	    }
	    /**
	     * Getter-Methode f�r das Attribut Lager.
	     */
	    public Lager getLager()
	    {
	    	return l;
	    }
	    /**
	     * Getter-Methode f�r das Attribut Menge.
	     */
		public int getMenge() {
			return menge;
		}
		/**
		 * Setter-Methode f�r das Attribut Menge.
		 * @param menge Menge, die gesetzt werden soll.
		 */
		public void setMenge(int menge) {
			this.menge = menge;
		} 
}
