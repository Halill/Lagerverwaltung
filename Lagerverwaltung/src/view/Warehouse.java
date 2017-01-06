package view;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.ScrollPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import controller.File_Manager;
import model.InstanceH;
import model.Lager;
import model.Model;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Savepoint;

import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextPane;
import javax.swing.AbstractListModel;

public class Warehouse implements Observer{

	JFrame frame;
	private JTree inventory;
	private JPanel navigationBar;
	private JButton[] naviButtons;
	private Model m;
	private ArrayList<Lager> lagerl = new ArrayList<>();


	/**
	 * @author Markus
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Warehouse window = new Warehouse();
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
	public Warehouse(ArrayList<Lager> lagerliste) 
	{
		this.lagerl = lagerliste;
		initialize();
		load_Inventory();
	}
	public Warehouse() 
	{
		initialize();
		load_Inventory();
	}
	
	public void setModel(Model model)
	{
		m = model;
	}
	public Model getModel()
	{
		return m;
	}
	
	public void setTree(JTree tree)
	{
		inventory = tree;
	}
	public JTree getTree()
	{
		return inventory;
	}
	
	public void setLager(ArrayList<Lager> lager)
	{
		lagerl = lager;
	}
	public ArrayList<Lager> getLager()
	{
		return lagerl;
	}
	
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
				Buchen window = new Buchen();
				window.frame.setVisible(true);
			}
		});
		navigationBar.add(booking);
	}
	
	public void refresh()
	{		
		inventory.setModel(null);
		//m.sysoLagerstruktur(lagerl);
		
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Gesamtlager");
		DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[lagerl.size()];
	
		
		DefaultTreeModel model = new DefaultTreeModel(root);
	
		inventory.setModel(model);	
		generateTree(root, nodes);
		

	}

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

	public DefaultMutableTreeNode addInfo(DefaultMutableTreeNode node, Lager lager) 
	{
		node = new DefaultMutableTreeNode(lager.getName() + " (Kapazitaet: " + lager.getKapazitaet() + " Bestand: " + lager.getBestand() + ")");
		return node;
	}

	private void createIButton(int i, DefaultMutableTreeNode root) 
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
		TreePath path = new TreePath(node.getPath());
		String name = root.getChildAt(i).toString().split("\\(")[0];
		
		naviButtons[i] = new JButton(name);
		naviButtons[i].setSize(197,30);
		naviButtons[i].setLocation(0,i * 30);	
		
		naviButtons[i].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inventory.setSelectionPath(path);
				inventory.expandPath(path);

			}
		});
		navigationBar.add(naviButtons[i]);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.textHighlightText);
		frame.setBounds(100, 100, 859, 718);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
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
		panel_Border.setBounds(0, 454, 843, 226);
		frame.getContentPane().add(panel_Border);
		panel_Border.setLayout(null);		
			
		JPanel panel = new JPanel();
		panel.setBounds(6, 16, 833, 199);
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
		scrollPane_1.setBounds(196, 0, 647, 455);
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
	
	private void showTransactions(DefaultListModel<String> listenModell) {
		
		/*
		 * Hier wird verglichen welches Lager angeklickt wurde, mit den Lagern aus der History
		 * diese Methode eignet sich nur auf beschränkte Weise, da es bei gleichnamigen Lagern zu Problemen führt, 
		 * diese jedoch hier nicht gegeben sind
		*/
		boolean allowed;
		for (int i = 0; i < InstanceH.getInstance().getHistory().size(); i++) 
		{
			allowed = InstanceH.getInstance().getHistory().get(i).isAllowed();
			String name = InstanceH.getInstance().getHistory().get(i).getLager().getName();
		
			if(allowed && inventory.getSelectionPath().getLastPathComponent().toString().contains(name))
				listenModell.add(listenModell.getSize(), (InstanceH.getInstance().getHistory().get(i).getTransaction()));	

		}
		if(listenModell.size() == 0)
			listenModell.add(0,"Es sind noch keine Transaktionen getätigt worden");
		
	}

	@Override
	public void update(Observable arg0, Object arg1) 
	{		
		System.out.println("Test");
		inventory.setModel((DefaultTreeModel) arg1);

	}
}
