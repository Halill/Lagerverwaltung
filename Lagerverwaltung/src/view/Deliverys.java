package view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;

import model.History;
import model.InstanceH;

import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
/**
 * Das Fenster Deliverys zeigt alle vorhandenen Buchungen an. Oben stehen alle Lager, auf die gebucht wurde. Klickt man auf ein Lager erscheinen im
 * <br> unteren Teil die zugehörigen Buchungen.
 */
public class Deliverys {

	public JFrame frame;

	/**
	 *  Konstruktor für das Fenster Delivery.
	 */
	public Deliverys() {
		initialize();
	}
	/**
	 * initialisiert die Inhalte des Fenster Delivery.
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
		listenModell_1.add(0, "Klicken Sie auf eine Lieferung, um die Details zu erfahren");
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
	/**
	 * hier werden Lager in das Fenster geladen, bei denen Transaktionen durchgeführt wurde.
	 * @param listenModell_1  hier wird die Liste der Buchungen gespeichert
	 * @param lager Lager, das angeklickt wurde
	 */
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
	/**
	 * hier werden die einzelnen Histories erzeugt. Drückt man im oberen Teil des Fenster auf ein Lager, so wird die jeweilige History angezeigt.
	 * @param listenModell hier wird die Liste der Buchungen gespeichert.
	 */
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
