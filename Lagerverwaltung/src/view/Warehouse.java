package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.ScrollPane;

public class Warehouse {

	private JFrame frame;

	/**
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
		
		JPanel navigationBar = new JPanel();
		navigationBar.setBackground(SystemColor.controlHighlight);
		navigationBar.setBounds(0, 0, 195, 455);
		frame.getContentPane().add(navigationBar);
		navigationBar.setLayout(new BorderLayout(0, 0));
		
		ScrollPane scrollPane = new ScrollPane();
		navigationBar.add(scrollPane);
		
		JTree tree = new JTree();
		tree.setBounds(205, 0, 638, 455);
		frame.getContentPane().add(tree);
		
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
