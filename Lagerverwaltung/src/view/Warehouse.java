package view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import controller.Controller;
import controller.File_Manager;
import model.History;
import model.InstanceH;
import model.Lager;
import model.Model;

import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

import javax.swing.DefaultListModel;
/**
 * Das zentrale Fenster Warehouse, dient zur Anzeige der Lagerstruktur. Es kann hier ein Lagername geändert werden, der Sprung zum Fenster Delivery und
 * der Sprung zum Fenster Buchen sind hier möglich. Des Weiteren kann hier die Lagerstruktur in eine txt-Datei gespeichert werden, die unter /Resources gefunden wird.
 * @author Halil
 *
 */
public class Warehouse implements Observer{

	public JFrame frame;
	static Warehouse warehouse;
	private JTree inventory;
	private JPanel navigationBar;
	private JButton[] naviButtons;
	private Model m;
	private ArrayList<Lager> lagerl = new ArrayList<Lager>();
	private ArrayList<TreePath> treePath = new ArrayList<TreePath>();
	
	/**
	 * Konstruktor für das Fenster Warehouse, nachdem die Lagerstruktur verändert oder aus einer Datei geladen wird.
	 * @param lagerliste für die eine Lagerstruktur generiert werden soll
	 */
	public Warehouse(ArrayList<Lager> lagerliste) 
	{
		this.lagerl = lagerliste;
		initialize();
		load_Inventory();
	}
	/**
	 * Konstruktor für das Fenster Warehouse, beim erstmaligem Aufruf.
	 */
	public Warehouse() 
	{
		initialize();
		load_Inventory();
	}
	/**
	 * Setter-Methode für das Attribut m.
	 * @param model Model das, dass Attribut m ersetzen soll.
	 */
	public void setModel(Model model)
	{
		m = model;
	}
	/**
	 * Getter-Methode für das Attribut m.
	 * @return gibt das Attribut m zurück.
	 */
	public Model getModel()
	{
		return m;
	}
	/**
	 * Setter-Methode für das Attribut inventory.
	 * @param tree Jtree, der den aktuellen Jtree ersetzen soll.
	 */
	public void setTree(JTree tree){
		inventory = tree;
	}
	/**
	 * Getter-Methode für das Attribut inventory.
	 * @return gibt das Attribut inventory zurück.
	 */
	public JTree getTree()
	{
		return inventory;
	}
	/**
	 * Setter-Methode für das Attribut lagerl
	 * @param lager ArrayList, der die aktuelle lagerl überschreibt
	 */
	public void setLager(ArrayList<Lager> lager)
	{
		lagerl = lager;
	}
	/**
	 * Getter-Methode für das Attribut lagerl
	 * @return gibt das Attribut lagerl zurück.
	 */
	public ArrayList<Lager> getLager(){
		return lagerl;
	}
	/**
	 * Methode, die aus dem Attribut lagerl einen 
	 */
	private void load_Inventory() 
	{		
		if (lagerl.size() == 0) 
		{
			setModel(new Model());
			setLager(new ArrayList<Lager>());
			m.legeInitialeStrukturFest();
			lagerl = m.getLagerliste();
			m.sysoLagerstruktur(lagerl);
		}
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Gesamtlager");
		DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[lagerl.size()];
		
		generateTree(root, nodes);
		   
		DefaultTreeModel model = new DefaultTreeModel(root);
		inventory.setModel(model);	
		
		int count =  inventory.getModel().getChildCount(root);
		naviButtons = new JButton[count];
		
		
		//Lädt die Ober-Lager in die Navigations Liste
		for(int i=0;i < root.getChildCount();i++)
		{
			System.out.println(inventory.getModel().getChild(root, i));
			createIButton(i,root);
		}
		
		JButton booking = new JButton("Buchen/Abbuchen");
		booking.setSize(197,30);
		booking.setLocation(0,(root.getChildCount() + 1) * 30);
		booking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				Controller.getInstance().openBuchen();

			}
		});
		navigationBar.add(booking);
		
		JButton delivery_button = new JButton("Lieferliste");
		delivery_button.setSize(197,30);
		delivery_button.setLocation(0,(root.getChildCount() + 2) * 30);
		delivery_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			Controller.getInstance().openDelivery();
			}
		});
		navigationBar.add(delivery_button);
		
		JButton save_button = new JButton("Speichern");
		save_button.setSize(197,30);
		save_button.setLocation(0,(root.getChildCount() + 3) * 30);
		save_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new File_Manager().save_inventory(lagerl);
				System.out.println("Lager gespeichert");
			}
		});
		navigationBar.add(save_button);
		
		JButton Change_name_button = new JButton("Lagernamen ändern");
		Change_name_button.setSize(197,30);
		Change_name_button.setLocation(0,(root.getChildCount() + 4) * 30);
		Change_name_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				changeLName();
			}
		});
		navigationBar.add(Change_name_button);
	}
	/**
	 * Methode, die ermöglicht, dass Lagernamen geändert werden können. Wird mit dem Button "Lagernamen ändern" und Auswahl eines Lagers aufgerufen.
	 */
	private void changeLName() 
	{
		Lager l = getLagerFromTree();
		if(l == null || naviButtons == null )
			return;
		 String input = JOptionPane.showInputDialog("Wählen Sie einen neuen Namen:");
		 
		 for (int i = 0; i < lagerl.size(); i++) 
		 {
			 if(lagerl.get(i).getName().equals(input))
			 {
				 JOptionPane.showMessageDialog(frame,"Der Name ist bereits vergeben");
				 return;
			 }
		 }
		
		 
		 for (int i = 0; i < naviButtons.length; i++) 
		 {
			 String name = naviButtons[i].getText().replace(" ", "");
			 String lagerName = l.getName();
			 if(name == null)
				 continue;
			 if(name.equals(lagerName))
				 naviButtons[i].setText(input); 
			 
		 }
		 l.setName(input);
		 refresh();
	}
	/**
	 * Methode, die den Jtree aktualisiert.
	 */
	public void refresh()
	{		
		inventory.setModel(null);
		//m.sysoLagerstruktur(lagerl);

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Gesamtlager");
		DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[lagerl.size()];
	
		DefaultTreeModel model = new DefaultTreeModel(root);
	
		inventory.setModel(model);	

		generateTree(root, nodes);
		
		refreshTreeNodes();
	}
	/**
	 * Methode, die aus dem Attribut lagerl einen Tree erzeugt.
	 * @param root Rootknoten
	 * @param nodes Knoten unterhalb des Rootknoten
	 */
	public void generateTree(DefaultMutableTreeNode root, DefaultMutableTreeNode[] nodes) {
		DefaultMutableTreeNode node;
		for(int i = 0; i < lagerl.size();i++)
		{
			nodes[i] = new DefaultMutableTreeNode(lagerl.get(i).getName());
			
			if(lagerl.get(i).getLagerStatus() == "Rootlager")
			{
				if(lagerl.get(i).getKindlager().size() == 0)
					nodes[i] = addInfo(nodes[i], lagerl.get(i));
				root.insert(nodes[i], root.getChildCount());
			}
			if(lagerl.get(i).getLagerStatus() == "Treelager")
			{
				node = findNode(root,lagerl.get(i).getElternlager().getName());
				if(node != null)
				{
					if(lagerl.get(i).getKindlager().size() == 0)
						nodes[i] = addInfo(nodes[i], lagerl.get(i));
					node.insert(nodes[i], node.getChildCount());							
				}
				else
					System.out.println(nodes[i] + " konnte nicht erstellt werden");
			}
			if(lagerl.get(i).getLagerStatus() == "Leaflager")
			{
				node = findNode(root,lagerl.get(i).getElternlager().getName());
				if(node != null)
				{
					if(lagerl.get(i).getKindlager().size() == 0)
						nodes[i] = addInfo(nodes[i], lagerl.get(i));
					node.insert(nodes[i], node.getChildCount());							
				}
				else
					System.out.println(nodes[i] + " konnte nicht erstellt werden");
			}			
							
		}
		
		for(int i = 0; i < lagerl.size(); i++)
		{
			if(lagerl.get(i).getLagerStatus() == "Rootlager" && nodes[i].getChildCount() == 0)
			{
				node = addInfo(nodes[i],lagerl.get(i));
			    ((DefaultTreeModel) inventory.getModel()).nodeChanged(node);
			}	
		}
	}
	/**
	 * Methode, um einen bestimmten Knoten im Tree zu finden.
	 * @param root Rootknoten des Trees.
	 * @param s String, nach dem gesucht werden soll
	 * @return gibt den gesuchten Knoten aus
	 */
	private DefaultMutableTreeNode findNode(DefaultMutableTreeNode root, String s) {
	    @SuppressWarnings("unchecked")
	    Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
	    while (e.hasMoreElements()) {
	        DefaultMutableTreeNode node = e.nextElement();
	        if (node.toString().equalsIgnoreCase(s)) {
	            return node;
	        }
	        System.out.println(node.toString());
	    }
	    return null;
	}	
	/**
	 * Lager, die keine Kindlager besitzen, erhalten im JTree zusätzlich die Anzeige der Kapazität und des Bestands.
	 * @param node Knoten, an dem das Lager sitzt
	 * @param lager Lager, dass die Kapazität und den Bestand verwaltet
	 * @return gibt den Knoten zurück
	 */
	public DefaultMutableTreeNode addInfo(DefaultMutableTreeNode node, Lager lager) 
	{
		node = new DefaultMutableTreeNode(lager.getName() + " (Kapazitaet: " + lager.getKapazitaet() + " Bestand: " + lager.getBestand() + ")");
		return node;
	}
	/**
	 * Methode, die die Rootlager links an die Seite per Button schneller verfügbar machen.
	 * @param i Stelle des Rootlagers.
	 * @param root Knoten, der einen Button bekommt.
	 */
	private void createIButton(int i, DefaultMutableTreeNode root) 
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
		TreePath path = new TreePath(node.getPath());
		String name = root.getChildAt(i).toString().split("\\(")[0];
		
		naviButtons[i] = new JButton();
		naviButtons[i].setText(name);
		naviButtons[i].setSize(197,30);
		naviButtons[i].setLocation(0,i * 30);	
		treePath.add(path);
		
		naviButtons[i].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inventory.setSelectionPath(treePath.get(i));
				inventory.expandPath(treePath.get(i));

			}
		});
		navigationBar.add(naviButtons[i]);
		
	}
	/**
	 * initialisert die Inhalte des Fensters Warehouse.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.textHighlightText);
		frame.setBounds(100, 100, 859, 718);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 197, 455);
		frame.getContentPane().add(scrollPane);
		
		navigationBar = new JPanel();
		scrollPane.setViewportView(navigationBar);
		navigationBar.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 120, 281);
		frame.getContentPane().add(panel_1);
		
		JPanel panel_Border = new JPanel();
		panel_Border.setBackground(SystemColor.inactiveCaptionBorder);
		panel_Border.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Lager A1", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_Border.setBounds(0, 454, 853, 235);
		frame.getContentPane().add(panel_Border);
		panel_Border.setLayout(null);		
			
		JPanel panel = new JPanel();
		panel.setBounds(6, 16, 842, 208);
		panel_Border.add(panel);
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		JScrollPane scrollPane2 = new JScrollPane();
		JList<String> list = new JList<String>();
		DefaultListModel<String> listenModell = new DefaultListModel<String>();
		list.setBackground(SystemColor.inactiveCaptionBorder);
		list.setModel(listenModell);
		panel.add(scrollPane2);
		scrollPane2.setViewportView(list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(196, 0, 657, 455);
		frame.getContentPane().add(scrollPane_1);
		
		inventory = new JTree();
		scrollPane_1.setViewportView(inventory);
		inventory.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evn) {
				panel_Border.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), evn.getPath().toString().split("\\(")[0].replace("[", "").replace("]", ""), TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
				listenModell.clear();
				
				if(InstanceH.getInstance().getHistory().size() > 0 && inventory.getSelectionPath() != null)
					showTransactions(listenModell);
				else
					listenModell.add(0,"Es sind noch keine Transaktionen getätigt worden");
			}
		});
	}
	/**
	 * Hier wird verglichen welches Lager angeklickt wurde, mit den Lagern aus der History
	 * @param listenModell Liste der Buchungen auf ein Lager
	 */
	private void showTransactions(DefaultListModel<String> listenModell) {
		
		if(getLagerFromTree() == null)
			return;

		boolean allowed;
		for (int i = 0; i < InstanceH.getInstance().getHistory().size(); i++) 
		{
			allowed = InstanceH.getInstance().getHistory().get(i).isAllowed();
			String name = InstanceH.getInstance().getHistory().get(i).getLager().getName();
		
			if(allowed && inventory.getSelectionPath().getLastPathComponent().toString().contains(name))
				listenModell.add(listenModell.getSize(), (InstanceH.getInstance().getHistory().get(i).getTransaction()));	

		}
		if(listenModell.size() == 0 && getLagerFromTree().getKindlager().size() == 0)
			listenModell.add(0,"Es sind noch keine Transaktionen getätigt worden");
		
		else if(getLagerFromTree().getKindlager().size() > 0)
			listenModell.addElement(getChildCapacity());
		
	}
	/**
	 * Methode, die den Bestand aller Kindlager addiert.
	 * @return gibt einen Ausgabestring zurück, der den summierten Bestand beinhaltet.
	 */
	private String getChildCapacity() 
	{
		String capacity = "";
		refreshLager();
		int count = getLagerFromTree().durchlaufenBestand();
		capacity = "Der Bestand der alle Unterlager liegt bei " + count + " Einheiten";
		return capacity;
		
	}
	/**
	 * aktualisiert das Lager-Objekt in einem Knoten, damit der richtige Bestand angezeigt wird.
	 */
	private void refreshLager() 
	{
		ArrayList<History> history = InstanceH.getInstance().getHistory();
		String first = "",second = "";
		for (int i = 0; i < history.size(); i++) 
		{
			first = history.get(i).getLager().getName().split("\\(")[0];
			for (int j = 0; j < getLager().size(); j++) 
			{
				second = getLager().get(j).getName().split("\\(")[0];
				if(first.equals(second))
				{				
					getLager().get(j).setBestand(history.get(i).getLager().getBestand());
					break;
				}
			}
		}
		
	}
	/**
	 * Hier wird neue Model für den Tree übergeben und gesetzt
	 */
	@Override
	public void update(Observable arg0, Object arg1) 
	{	

		inventory.setModel((DefaultTreeModel) arg1);
	}
	/**
	 * 	Hier werden die neuen Pfade für die Navigationsbutton aktualisiert
	 */
	private void refreshTreeNodes() {

		DefaultMutableTreeNode root = (DefaultMutableTreeNode) inventory.getModel().getRoot();
		DefaultMutableTreeNode node;
		
		for (int i = 0; i < root.getChildCount(); i++) {
			node = (DefaultMutableTreeNode) root.getChildAt(i);
			TreePath path = new TreePath(node.getPath());
			treePath.set(i, path);
		}
	}
	/**
	 * Methode, die aus einem Knoten ein Lager ausliest.
	 * @return gibt das gewünschte Lager aus
	 */
	private Lager getLagerFromTree() {

		if(inventory.getLastSelectedPathComponent() == null)
			return null;
		
		String index = inventory.getLastSelectedPathComponent().toString();
		
		for(Lager l : getLager())
		{
			if(index.contains(l.getName()))
			{
				System.out.println(l.getName());
				return l;
			}
		}
		return null;
		
	}
}
