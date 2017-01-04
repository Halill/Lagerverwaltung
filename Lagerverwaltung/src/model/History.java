package model;

import java.util.ArrayList;

public class History 
{	
	Lager lager;
	ArrayList<String> transactions = new ArrayList<String>();
	
	
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
		transactions.add(transaction);
	}
	
	public ArrayList<String> getTransaction()
	{
		return transactions;
	}

}
