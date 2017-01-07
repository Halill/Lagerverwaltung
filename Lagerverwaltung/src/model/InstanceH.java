package model;

import java.util.ArrayList;
/**
 * 
 * Diese Klasse verwaltet alle getätigten Buchungen zu einem Lager. D.h. in der InstanceH stehen alle Buchungen als History-Objekt zu einem Lager. 
 *
 */
public class InstanceH {
	
	private static InstanceH instance = new InstanceH();

	private ArrayList<History> history = new ArrayList<History>();
	
	/**
	 * Getter-Methode für das Attribut instance.
	 * @return gibt das Attribut instance zurück.
	 */
	public static InstanceH getInstance() {
		return instance;
	}
	/**
	 * Getter-Methode für das Attribut history.
	 * @return gibt das Attribut history zurück.
	 */
	public ArrayList<History> getHistory() {
		return history;
	}
	/**
	 * Setter-Methode für das Attribut history
	 * @param history Parameter der die history überschreibt.
	 */
	public void setHistory(ArrayList<History> history) {
		this.history = history;
	}
	/**
	 * Diese Methode fügt eine Arraylist an das vorhandene Attribut history.
	 * @param addhistory Parameter der an das vorhandem Attribut history eine ArrayList setzt.
	 */
	public void addHistory(ArrayList<History> addhistory) 
	{
		history.addAll(addhistory);
	}
}
