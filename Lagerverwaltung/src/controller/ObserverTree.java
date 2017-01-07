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
	 * Getter-Methode f�r das Attribut treeModel.
	 * @return gibt das Attribut treeModel zur�ck
	 */
	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}
	/**
	 * Setter-Methode f�r das Attribut treeModel. 
	 * @param treeModel das �bergebene treeModel wird zus�tzlich per Observable Methoden benachrichtigt.
	 */
	public void setTreeModel(DefaultTreeModel treeModel) 
	{
		this.treeModel = treeModel;
	    setChanged();
	    notifyObservers(treeModel);
	}
	/**
	 * Getter-Methode f�r das Attribut instance.
	 * @return gibt das Attribut instance zur�ck
	 */
	public static ObserverTree getInstance() {
		return instance;
	}

}
