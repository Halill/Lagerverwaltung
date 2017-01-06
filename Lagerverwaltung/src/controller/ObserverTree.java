package controller;

import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.DefaultTreeModel;

import model.InstanceH;
import view.Warehouse;

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
