package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;

public class Deliverys {

	static JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Deliverys window = new Deliverys();
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
	public Deliverys() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 690, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 675, 240);
		panel.add(scrollPane);
		
		JList<String> list = new JList<String>();
		scrollPane.setViewportView(list);
		DefaultListModel<String> listenModell = new DefaultListModel<String>();
		listenModell.add(0, "Hier stehen Ihre Lieferungen");
		list.setModel(listenModell);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(5, 250, 675, 215);
		panel.add(scrollPane_1);
		
		JList<String> list_1 = new JList<String>();
		DefaultListModel<String> listenModell_1 = new DefaultListModel<String>();
		listenModell_1.add(0, "Klicken Sie auf eine Lieferung im die Detail zu erfahren");
		list_1.setModel(listenModell_1);
		scrollPane_1.setViewportView(list_1);
		
		load_history();
	}

	private void load_history() 
	{
		
	}
}
