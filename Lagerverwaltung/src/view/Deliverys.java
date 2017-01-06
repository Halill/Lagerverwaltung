package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;

import model.History;
import model.InstanceH;

import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Deliverys {

	public JFrame frame;


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
		list.setModel(listenModell);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(5, 250, 675, 215);
		panel.add(scrollPane_1);
		
		JList<String> list_1 = new JList<String>();
		DefaultListModel<String> listenModell_1 = new DefaultListModel<String>();
		listenModell_1.add(0, "Klicken Sie auf eine Lieferung im die Detail zu erfahren");
		list_1.setModel(listenModell_1);
		scrollPane_1.setViewportView(list_1);
		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg) 
			{
				updateList(listenModell_1,list.getSelectedValue());
			}
		});
		
		load_history(listenModell);
	}

	private void updateList(DefaultListModel<String> listenModell_1, String lager) 
	{		
		listenModell_1.clear();
		ArrayList<History> history = InstanceH.getInstance().getHistory();
		
		for (int i = 0; i < history.size(); i++)
		{
			if(history.get(i).isAllowed() && lager.equals(history.get(i).getLager().getName()))
				listenModell_1.addElement(history.get(i).getTransaction());
		}
		
	}

	private void load_history(DefaultListModel<String> listenModell) 
	{
		listenModell.clear();
		ArrayList<History> history = InstanceH.getInstance().getHistory();
		
		for (int i = 0; i < history.size(); i++)
		{
			if(history.get(i).isAllowed() && !listenModell.contains(history.get(i).getLager().getName()))
				listenModell.addElement(history.get(i).getLager().getName());
		}
		if(listenModell.size() == 0)
			listenModell.add(0, "Hier stehen Ihre Lieferungen");
	}
}
