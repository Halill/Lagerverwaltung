package model;

import javax.swing.tree.DefaultMutableTreeNode;
/**
 * Diese Klasse verwaltet eine Buchung zu einem Lager. D.h. in der History steht eine getätigte Buchung.
 *
 */
public class History 
{	
	Lager lager;
	private String transactions;
	private boolean isAllowed = false;
	private DefaultMutableTreeNode node;
	/**
	 * Setter-Methode für das Attribut lager.
	 * @param l Lager das gesetzt werden soll.
	 */
	public void setLager(Lager l)
	{
		this.lager = l;
	}
	/**
	 * Getter-Methode für das Attribut lager.
	 * @return gibt das Attribut lager zurück.
	 */
	public Lager getLager()
	{
		return this.lager;
	}
	/**
	 * Setter-Methode für das Attribut transactions.
	 * @param transaction String der gesetzt werden soll
	 */
	public void addTransaction(String transaction)
	{
		this.transactions = transaction;
	}
	/**
	 * Getter-Methode für das Attribut transactions.
	 * @return gibt das Attribut transactions zurück.
	 */
	public String getTransaction()
	{
		return this.transactions;
	}
	/**
	 * Getter-Methoder für das Attribut isAllowed.
	 * @return gibt das Attribut isAllowed zurück.
	 */
	public boolean isAllowed() {
		return isAllowed;
	}
	/**
	 * Setter-Methode für das Attribut isAllowed.
	 * @param isAllowed setzt den Bool-Wert isAllowed
	 */
	public void setAllowed(boolean isAllowed) {
		this.isAllowed = isAllowed;
	}
	/**
	 * Getter-Methode für das Attribut node.
	 * @return gibt das Attribut node zurück.
	 */
	public DefaultMutableTreeNode getNode() {
		return node;
	}
	/**
	 * Setter-Methode für das Attribut node.
	 * @param node der gesetzt werden soll.
	 */
	public void setNode(DefaultMutableTreeNode node) {
		this.node = node;
	}

}
