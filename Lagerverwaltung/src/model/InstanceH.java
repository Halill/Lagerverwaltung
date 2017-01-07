package model;

import java.util.ArrayList;
/**
 * 
 * Diese Klasse verwaltet alle get�tigten Buchungen zu einem Lager. D.h. in der InstanceH stehen alle Buchungen als History-Objekt zu einem Lager. 
 *
 */
public class InstanceH {
	
	private static InstanceH instance = new InstanceH();

	private ArrayList<History> history = new ArrayList<History>();
	
	/**
	 * Getter-Methode f�r das Attribut instance.
	 * @return gibt das Attribut instance zur�ck.
	 */
	public static InstanceH getInstance() {
		return instance;
	}
	/**
	 * Getter-Methode f�r das Attribut history.
	 * @return gibt das Attribut history zur�ck.
	 */
	public ArrayList<History> getHistory() {
		return history;
	}
	/**
	 * Setter-Methode f�r das Attribut history
	 * @param history Parameter der die history �berschreibt.
	 */
	public void setHistory(ArrayList<History> history) {
		this.history = history;
	}
	/**
	 * Diese Methode f�gt eine Arraylist an das vorhandene Attribut history.
	 * @param addhistory Parameter der an das vorhandem Attribut history eine ArrayList setzt.
	 */
	public void addHistory(ArrayList<History> addhistory) 
	{
		history.addAll(addhistory);
	}
}
