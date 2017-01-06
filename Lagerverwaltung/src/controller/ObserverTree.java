package controller;

import java.util.Observable;
import javax.swing.tree.DefaultTreeModel;

public class ObserverTree extends Observable
{
	private static ObserverTree instance = new ObserverTree();

	private DefaultTreeModel treeModel;

	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	public void setTreeModel(DefaultTreeModel treeModel) 
	{
		this.treeModel = treeModel;
	    setChanged();
	    notifyObservers(treeModel);
	}

	public static ObserverTree getInstance() {
		return instance;
	}

}
