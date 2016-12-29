package view;

import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.ScrollPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import model.Lager;
import model.Model;

import javax.swing.tree.DefaultMutableTreeNode;

public class Warehouse {

	JFrame frame;
	private JTree inventory;
	private JPanel navigationBar;
	private JScrollPane scrollPane;
	private JButton[] naviButtons;

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
	public Warehouse() 
	{
		initialize();
		load_Inventory();
	}

	private void load_Inventory() 
	{		
		Model m = new Model();

		ArrayList<Lager> lagerl = new ArrayList<Lager>();
		m.legeInitialeStrukturFest();
		lagerl = m.getLagerliste();
		
		//m.sysoLagerstruktur(lagerl);
		
	//	Lager neuesLager = m.lagerAnlegen("neues Lager", 0, 0);

//		Test f�r Fall 1: Das neue Lager wird �ber Deutschland angelegt
//		m.neuesLagereinfuegen(lagerl.get(9), neuesLager);
		
//		Test f�r Fall 2: Das neue Lager wird zwischen Deutschland und MV eingef�gt
//		m.neuesLagereinfuegen(lagerl.get(9), lagerl.get(8), neuesLager);
		
//		Test f�r Fall 3: Das neue Lager wird unter MV eingef�gt
//		m.neuesLagereinfuegen(lagerl.get(8), neuesLager);
		
		m.sysoLagerstruktur(lagerl);
		
		MutableTreeNode root = new DefaultMutableTreeNode("Gesamtlager");
		MutableTreeNode[] nodes = new MutableTreeNode[lagerl.size()];
		MutableTreeNode lastsubroot = null,lastTree = null;
		
		
		for(int i = 0; i < lagerl.size();i++)
		{
			nodes[i] = new DefaultMutableTreeNode(lagerl.get(i).getName());
			
			if(lagerl.get(i).getLagerStatus() == "Rootlager")
			{
				root.insert(nodes[i], root.getChildCount());
				lastsubroot = nodes[i];
			}
			if(lagerl.get(i).getLagerStatus() == "Treelager")
			{
				if(lastsubroot != null)
				{
					lastsubroot.insert(nodes[i], lastsubroot.getChildCount());
					lastTree = nodes[i];
				}
				else
					System.out.println(nodes[i] + " konnte nicht erstellt werden");
			}
			if(lagerl.get(i).getLagerStatus() == "Leaflager")
			{
				if(lastTree != null)
				{
					MutableTreeNode node = addInfo(nodes[i],lagerl.get(i));
					lastTree.insert(node, lastTree.getChildCount());
				}
				else
					System.out.println(nodes[i] + " konnte nicht erstellt werden");
			}			
							
		}
		
	    
		DefaultTreeModel model = new DefaultTreeModel(root);
		inventory.setModel(model);	
		
		int count =  inventory.getModel().getChildCount(root);
		naviButtons = new JButton[count];
		
		
		//L�dt die Ober-Lager in die Navigations Liste
		for(int i=0;i < count;i++)
		{
			System.out.println(inventory.getModel().getChild(root, i));
			createIButton(i);
		}
		
		//L�dt die Buchen und Abbuchen Buttons
		JButton buchen = new JButton("Buchen");
		buchen.setLocation(0,count * 30 + 30);
		buchen.setSize(navigationBar.getSize().width,30);
		buchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Buchen window = new Buchen();
				window.frame.setVisible(true);			
			}
		});
		scrollPane.add(buchen);
		
		JButton abbuchen = new JButton("Abbuchen");
		abbuchen.setLocation(0,(count + 1) * 30 + 30);
		abbuchen.setSize(navigationBar.getSize().width,30);
		abbuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}
		});
		scrollPane.add(abbuchen);
		
		
	}

	private MutableTreeNode addInfo(MutableTreeNode node, Lager lager) 
	{
		node = new DefaultMutableTreeNode(lager.getName() + " (Kapazitaet: " + lager.getKapazitaet() + " Bestand: " + lager.getBestand() + ")");
		return node;
	}

	private void createIButton(int i) 
	{
		naviButtons[i] = new JButton("Lager " + (1 + i));
		naviButtons[i].setSize(navigationBar.getSize().width,30);
		naviButtons[i].setLocation(0,i * 30);	
		
		naviButtons[i].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = i;


			}
		});
		scrollPane.add(naviButtons[i]);
		
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
		
		navigationBar = new JPanel();
		navigationBar.setBounds(0, 0, 195, 455);
		frame.getContentPane().add(navigationBar);
		navigationBar.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		navigationBar.add(scrollPane, BorderLayout.CENTER);
		
		inventory = new JTree();
		inventory.setBounds(205, 0, 638, 455);
		frame.getContentPane().add(inventory);

		
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
		
		JList list = new JList();
		list.setBackground(SystemColor.inactiveCaptionBorder);
		panel.add(list);
	}
}
