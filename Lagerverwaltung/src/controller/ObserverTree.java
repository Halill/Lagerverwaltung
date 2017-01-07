package controller;

import java.util.Observable;
import javax.swing.tree.DefaultTreeModel;
/**
 * Hilfsklasse, um den JTree in dem Fenster Warehouse und Buchen zu aktualisieren.
 * @author Halil
 *
 */
public class ObserverTree extends Observable
{
	private static ObserverTree instance = new ObserverTree();

	private DefaultTreeModel treeModel;
	/**
	 * Getter-Methode für das Attribut treeModel.
	 * @return gibt das Attribut treeModel zurück
	 */
	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}
	/**
	 * Setter-Methode für das Attribut treeModel. 
	 * @param treeModel das übergebene treeModel wird zusätzlich per Observable Methoden benachrichtigt.
	 */
	public void setTreeModel(DefaultTreeModel treeModel) 
	{
		this.treeModel = treeModel;
	    setChanged();
	    notifyObservers(treeModel);
	}
	/**
	 * Getter-Methode für das Attribut instance.
	 * @return gibt das Attribut instance zurück
	 */
	public static ObserverTree getInstance() {
		return instance;
	}

}
