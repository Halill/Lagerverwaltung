package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;

import controller.Controller;
import controller.File_Manager;
import model.Lager;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
/**
 * Das Fenster Welcome dient als Eingangsfenster in die Anwendung. Man kann entweder eine neue Lagerstruktur generieren <br>
 * oder eine vorhandene aus einer txt Datei lesen. Bei beiden Wegen wird als nächstes das Fenster Warehouse angezeigt.
 */
public class Welcome {

	public JFrame frame;

	/**
	 * Konstruktor für das Fenster Welcome.
	 */
	public Welcome() {
		initialize();
	}
	/**
	 * initialisiert die Inhalte des Fenster Welcome.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 386, 295);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton new_inventory = new JButton("Neues Lager");
		new_inventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				Controller.getInstance().openWarehouse();
				frame.dispose();
			}
		});
		
		new_inventory.setBounds(45, 168, 126, 56);
		panel.add(new_inventory);
		
		JButton load_inventory = new JButton("Lager \u00D6ffnen");
		load_inventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File_Manager file = new File_Manager();
				ArrayList<Lager> lager = file.load_inventory();
				if(lager == null)
					return;
				Controller.getInstance().openWarehouse(lager);
				frame.dispose();
			}
		});
		load_inventory.setBounds(201, 168, 126, 56);
		panel.add(load_inventory);
		
		JLabel logo = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		logo.setIcon(new ImageIcon(img));
		logo.setBounds(45, 11, 284, 82);
		panel.add(logo);
	}
}
