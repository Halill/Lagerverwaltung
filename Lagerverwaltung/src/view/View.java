package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import controller.Frame;

public class View {

	public static void main(String[] args) 
	{
		//Erstellen eines Frames mit Komponenten
		Frame f = new Frame();
		JFrame w = f.Window(300, 300, "Fenster");
		JPanel panel = f.Panel(0, 0, 50, 50);
		JButton button = f.Button(20,20,40,40, "Text");
		
		
		//Zuweisung der Koponenten in die Fenster
		panel.add(button);
		w.add(panel);
		
		w.setVisible(true);
		
	}
	
}