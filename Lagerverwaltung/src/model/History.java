package model;

import javax.swing.tree.DefaultMutableTreeNode;

public class History 
{	
	Lager lager;
	private String transactions;
	private boolean isAllowed = false;
	private DefaultMutableTreeNode node;
	
	public void setLager(Lager l)
	{
		this.lager = l;
	}
	
	public Lager getLager()
	{
		return this.lager;
	}
	
	public void addTransaction(String transaction)
	{
		this.transactions = transaction;
	}
	
	public String getTransaction()
	{
		return this.transactions;
	}

	public boolean isAllowed() {
		return isAllowed;
	}

	public void setAllowed(boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

	public DefaultMutableTreeNode getNode() {
		return node;
	}

	public void setNode(DefaultMutableTreeNode node) {
		this.node = node;
	}

}
