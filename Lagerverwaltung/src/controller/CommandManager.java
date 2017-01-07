package controller;

import model.Lager;
/**
 * Das Interface CommandManager wird in der Klasse Book genutzt.
 * Hauptaufgabe dieses Interfaces ist es das Buchen zu ermöglichen.
 * 
 *
 */
public interface CommandManager 
{
	public void undo();
	public int redo();
	public void execute(Lager lager);
	public Lager getLager();
	public int getMenge();
}
	    

