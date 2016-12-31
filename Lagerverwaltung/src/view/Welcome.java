package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTree;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controller.File_Manager;
import model.Lager;

import com.jgoodies.forms.layout.FormSpecs;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.CardLayout;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Welcome {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome window = new Welcome();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Welcome() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 386, 295);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton new_inventory = new JButton("Neues Lager");
		new_inventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				Warehouse window = new Warehouse();
				window.frame.setVisible(true);		
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
				Warehouse window = new Warehouse();
				window.frame.setVisible(true);
				window.getModel().setLagerliste(lager);
				window.setLager(lager);
				window.refresh();
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
