package model;

import java.util.ArrayList;

public class InstanceH {
	
	private static InstanceH instance = new InstanceH();

	private ArrayList<History> history = new ArrayList<History>();
	
	
	public static InstanceH getInstance() {
		return instance;
	}


	public ArrayList<History> getHistory() {
		return history;
	}


	public void setHistory(ArrayList<History> history) {
		this.history = history;
	}


}
