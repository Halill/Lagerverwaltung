package model;

import javax.swing.tree.DefaultMutableTreeNode;
/**
 * Diese Klasse verwaltet eine Buchung zu einem Lager. D.h. in der History steht eine get�tigte Buchung.
 *
 */
public class History 
{	
	Lager lager;
	private String transactions;
	private boolean isAllowed = false;
	private DefaultMutableTreeNode node;
	/**
	 * Setter-Methode f�r das Attribut lager.
	 * @param l Lager das gesetzt werden soll.
	 */
	public void setLager(Lager l)
	{
		this.lager = l;
	}
	/**
	 * Getter-Methode f�r das Attribut lager.
	 * @return gibt das Attribut lager zur�ck.
	 */
	public Lager getLager()
	{
		return this.lager;
	}
	/**
	 * Setter-Methode f�r das Attribut transactions.
	 * @param transaction String der gesetzt werden soll
	 */
	public void addTransaction(String transaction)
	{
		this.transactions = transaction;
	}
	/**
	 * Getter-Methode f�r das Attribut transactions.
	 * @return gibt das Attribut transactions zur�ck.
	 */
	public String getTransaction()
	{
		return this.transactions;
	}
	/**
	 * Getter-Methoder f�r das Attribut isAllowed.
	 * @return gibt das Attribut isAllowed zur�ck.
	 */
	public boolean isAllowed() {
		return isAllowed;
	}
	/**
	 * Setter-Methode f�r das Attribut isAllowed.
	 * @param isAllowed setzt den Bool-Wert isAllowed
	 */
	public void setAllowed(boolean isAllowed) {
		this.isAllowed = isAllowed;
	}
	/**
	 * Getter-Methode f�r das Attribut node.
	 * @return gibt das Attribut node zur�ck.
	 */
	public DefaultMutableTreeNode getNode() {
		return node;
	}
	/**
	 * Setter-Methode f�r das Attribut node.
	 * @param node der gesetzt werden soll.
	 */
	public void setNode(DefaultMutableTreeNode node) {
		this.node = node;
	}

}
