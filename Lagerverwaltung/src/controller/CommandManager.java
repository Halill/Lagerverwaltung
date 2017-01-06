package controller;

import model.Lager;

public interface CommandManager 
{
	public void undo();
	public int redo();
	public void execute(Lager lager);
	public int getUnits();
	public Lager getLager();
	public int getMenge();
}
	    

