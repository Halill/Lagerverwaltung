package controller;

import model.Lager;
/**
 * 
 * Diese Klasse dient um gewisse Funktionen für das Buchen zu ermöglichen: <br>
 * - undo<br>
 * - redo<br>
 * - execute bzw. ausführen der eigentlichen Buchung<br>
 * Des Weitern sind diverse Getter und Setter für die Klassenattributte vorhanden. Außerdem nutzt die Klasse ausschließlich das Interface CommandManager.
 *
 */

public class Book implements CommandManager {
			
		private Lager l;
		private int menge;		
		
		/**
		 * Undo Mechanismus. Die Buchung wird zurück von der Buchungsmenge auf das Lager gebucht.
		 */
	    public void undo() 
	    {
	    	l.setBestand(l.getBestand() + menge);
	    	System.out.println(menge + " wurde von dem Lager abgezogen");
	    }
	    /**
	     * Redo Mechanismus. Die Buchungsmenge wird vom Lager zurück in die Buchungsmenge übertragen.
	     */
	    public int redo() 
	    {	    	
	    	l.setBestand(l.getBestand() - menge);
	    	System.out.println(menge + " wurde auf das Lager gebucht");
	    	return menge;
	    }
	    /**
	     * Führt die eigentliche Buchung aus. Es wird geprüft, ob ein Lager übergeben wird, ansonsten wird nicht gebucht.
	     */
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
	    /**
	     * Getter-Methode für das Attribut Lager.
	     */
	    public Lager getLager()
	    {
	    	return l;
	    }
	    /**
	     * Getter-Methode für das Attribut Menge.
	     */
		public int getMenge() {
			return menge;
		}
		/**
		 * Setter-Methode für das Attribut Menge.
		 * @param menge Menge, die gesetzt werden soll.
		 */
		public void setMenge(int menge) {
			this.menge = menge;
		} 
}
