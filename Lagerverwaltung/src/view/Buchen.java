package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import javax.swing.tree.TreePath;

import controller.Book;
import controller.CommandManager;
import controller.ObserverTree;
import model.History;
import model.InstanceH;
import model.Lager;

import javax.swing.JTree;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.text.SimpleDateFormat;

public class Buchen extends Warehouse{

	JFrame frame;
	private JTextField textField;
	private int percent_Step = 5;
	private int all_Units = 1;
	private int capacity;
	private int currentUnits;
	private JLabel rest_label;
	private JTree tree;
	private boolean canBook;
    private ArrayList<CommandManager> commands = new ArrayList<CommandManager>();
    private CommandManager last_command;
    private ArrayList<History> history = new ArrayList<History>();
    private Date datum = new Date();
    private JLabel info_Label;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buchen window = new Buchen();
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
	public Buchen() {
		initialize();	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		
		frame = new JFrame();
		frame.setBounds(100, 100, 708, 453);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		panel.setBounds(0, 0, 702, 164);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
			      char enter = evt.getKeyChar();
			        if(!(Character.isDigit(enter)) || canBook){
			            evt.consume();
						checkTextField();
			        }			      
			}
		});
		textField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {	
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) 
			{						
				checkTextField();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) 
			{
				
			}
		});
		textField.setBackground(SystemColor.inactiveCaptionBorder);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 40));
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setText("1");
		textField.setBounds(38, 70, 293, 56);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblWhlenSieDie = new JLabel("W\u00E4hlen Sie die Menge der Zulieferung");
		lblWhlenSieDie.setBounds(38, 28, 480, 25);
		panel.add(lblWhlenSieDie);
		
		JButton add_one = new JButton("+1");
		add_one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(++count + "");
				all_Units = Integer.parseInt(textField.getText());
			}
		});
		add_one.setBounds(347, 69, 64, 23);
		panel.add(add_one);
		
		JButton add_ten = new JButton("+10");
		add_ten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(10 + count + "");
				all_Units = Integer.parseInt(textField.getText());
			}
		});
		add_ten.setBounds(421, 69, 64, 23);
		panel.add(add_ten);

		JButton add_hundred = new JButton("+100");
		add_hundred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(100 + count + "");
				all_Units = Integer.parseInt(textField.getText());
			}
		});
		add_hundred.setBounds(495, 69, 64, 23);
		panel.add(add_hundred);
		
		JButton add_thousand = new JButton("+1000");
		add_thousand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(1000 + count + "");
				all_Units = Integer.parseInt(textField.getText());
			}
		});
		add_thousand.setBounds(569, 69, 72, 23);
		panel.add(add_thousand);
		
		JButton rem_thousand = new JButton("-1000");
		rem_thousand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(count - 1000 + "");
				all_Units = Integer.parseInt(textField.getText());
			}
		});
		rem_thousand.setBounds(569, 103, 72, 23);
		panel.add(rem_thousand);
		
		JButton rem_hundred = new JButton("-100");
		rem_hundred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(count - 100 + "");
				all_Units = Integer.parseInt(textField.getText());
			}
		});
		rem_hundred.setBounds(495, 103, 64, 23);
		panel.add(rem_hundred);
		
		JButton rem_ten = new JButton("-10");
		rem_ten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(count - 10 + "");
				all_Units = Integer.parseInt(textField.getText());
			}
		});
		rem_ten.setBounds(421, 103, 64, 23);
		panel.add(rem_ten);
		
		JButton rem_one = new JButton("-1");
		rem_one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(count - 1 + "");			
				all_Units = Integer.parseInt(textField.getText());
			}
		});
		rem_one.setBounds(347, 103, 64, 23);
		panel.add(rem_one);
		
		rest_label = new JLabel("5% Schritte von 1");
		rest_label.setBounds(38, 137, 293, 25);
		panel.add(rest_label);
		
		
		JButton add_one_per = new JButton("+1%");
		add_one_per.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				if (percent_Step < 100) {
					percent_Step++;
				}
				stepChanged();
			}
		});
		add_one_per.setBounds(347, 138, 64, 23);
		panel.add(add_one_per);
		
		JButton rem_one_per = new JButton("-1%");
		rem_one_per.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (percent_Step > 1) {
					percent_Step--;
				}
				stepChanged();
			}
		});
		rem_one_per.setBounds(421, 137, 64, 23);
		panel.add(rem_one_per);
		
		JButton add_ten_per = new JButton("+10%");
		add_ten_per.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (percent_Step <= 90) {
					percent_Step += 10;
				}
				stepChanged();
			}
		});
		add_ten_per.setBounds(495, 138, 64, 23);
		panel.add(add_ten_per);
		
		JButton rem_ten_per = new JButton("-10%");
		rem_ten_per.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (percent_Step >= 10) {
					percent_Step -= 10;
				}
				
				stepChanged();
			}
		});
		rem_ten_per.setBounds(569, 137, 72, 23);
		panel.add(rem_ten_per);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		
		for ( int i = 0; i < 4; i++ ){
		  model.addElement( "test" );
		}
		
		JButton Buchen = new JButton("Buchen");

		Buchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(!textField.getText().equals("0"))
				{
					setInfoLabel("Es müssen alle Einheiten verbucht sein", Color.red);;
					return;
				}
				InstanceH.getInstance().setHistory(history);
				ObserverTree.getInstance().setTreeModel((DefaultTreeModel) tree.getModel());
				refresh();
				frame.dispose();
//				Buchung buchen = new Buchung();
//				if(all_Units > 0)
//					buchen.setBuchungstyp(1);
//				else
//					buchen.setBuchungstyp(0);
//
//				buchen.setDatum();				
//				buchen.setBuchungLagerListe(getLager());
//				buchen.setMenge(Integer.parseInt(textField.getText()));
//								
//				if (buchen.getMenge() > 0 ) 
//					buchen.zubuchen();				
//				else 
//					buchen.abbuchen();
//				
//				refresh();
			}
		});
		Buchen.setBounds(596, 386, 105, 38);
		frame.getContentPane().add(Buchen);
		
		JButton btnVor = new JButton("Vor");
		btnVor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent click) {
				if (commands.size() > 0) {
					redo();
				}
			}
		});
		btnVor.setBounds(493, 386, 105, 38);
		frame.getContentPane().add(btnVor);
		
		JButton btnZurck = new JButton("Zur\u00FCck");
		btnZurck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent click) 
			{				
				if (last_command != null) {
					undo();
				}
			}
		});
		btnZurck.setBounds(390, 386, 105, 38);
		frame.getContentPane().add(btnZurck);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 164, 702, 224);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		tree = new JTree();
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(canBook)
				{
					add_one.setEnabled(false);
					add_ten.setEnabled(false);
					add_hundred.setEnabled(false);
					add_thousand.setEnabled(false);
				
					rem_one.setEnabled(false);
					rem_ten.setEnabled(false);
					rem_hundred.setEnabled(false);
					rem_thousand.setEnabled(false);
				
					if (Integer.parseInt(textField.getText()) > 0)
					{
						buche_auf_Lager();
					}
					else
					{
						lösche_von_Lager();
					}
				}
			}
			
		});
		tree.setModel(getTree().getModel());		
		scrollPane.setViewportView(tree);

		expandAllNodes(0,tree.getRowCount());
		getAllCapacity(); 
		textField.setForeground(Color.black);
		
		info_Label = new JLabel("");
		info_Label.setBounds(10, 394, 378, 25);
		frame.getContentPane().add(info_Label);
	}
	

	private void getAllCapacity() {
		
		capacity = 0;

		for (int i = 0; i < tree.getModel().getChildCount(tree.getModel().getRoot()); i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getModel().getChild(tree.getModel().getRoot(), i);
			TreePath path = new TreePath(node.getPath());
			tree.setSelectionPath(path);
			Lager l = getLagerFromTree();
			capacity += (l.getKapazitaet() - l.getBestand());
			currentUnits += l.getBestand();
		}
		System.out.println(capacity + " Einheiten können noch verteilt werden und " + currentUnits + " Einheiten sind vorhanden");
		canBook = true;
	}
	
	private void buche_auf_Lager() 
	{				
		double menge = ((double)percent_Step * all_Units) / 100.0;
		int m = (int)menge;
		int freeUnits;		
		int units_left = Integer.parseInt(textField.getText());
	
		Book b = new Book();
		Lager l = getLagerFromTree();
		
		if(l.getKindlager().size() > 0)
		{
			setInfoLabel("Bitte ein Lager auswählen was auch verwalted werden kann", Color.red);
			return;
			//buche_auf_Kinder(l,units_left,m,menge,0);	//Diese Methode wurde aus Zeit gründen nicht Fertiggestellt. Funktioniert nur mit kleinen Abweichungen
		}
		
		if(l == null || units_left <= 0)
		{	
			setInfoLabel("Kein Lager ausgewählt oder keine Einheiten zu verteilen", Color.red);
			return;
		}
		freeUnits =  l.getKapazitaet() - l.getBestand();
		
		if(menge >= 0.5 && menge < 1.0 && units_left > 0)
		{
			if(freeUnits >= 1)
				b.setMenge(1);
			else
			{	
				setInfoLabel("Die Kapazität reicht nicht aus", Color.red);
				return;
			}
			units_left--;		
		}
		else if(m > 0)
		{
			if (units_left >= m) 
			{			
				if(freeUnits >= m)
					b.setMenge(m);
				else
				{	
					setInfoLabel("Die Kapazität reicht nicht aus", Color.red);
					return;
				}
				
				units_left -= m;
			}
			else
			{
				if(freeUnits >= units_left)
					b.setMenge(units_left);
				else
				{	
					setInfoLabel("Die Kapazität reicht nicht aus", Color.red);
					return;
				}		
				units_left = 0;
			}
		}
		else 
		{
			setInfoLabel("1 Einheit kann nur als ganzes verwalted werden. Ab 50% aufrunden.",Color.red);
			return;
		}
				
		textField.setText(units_left + "");
		//Menge wird hier auf die Lager gebucht
		b.execute(l);
		commands.add(b);
		last_command = b;
		stepChanged();
		
		
		History h = new History();
		h.setLager(l);
		h.setAllowed(true);
		h.setNode((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent());
		//Datum und Zeit einholen und festsetzen
		setDatum();
		h.addTransaction(getDatum() + ": " + b.getMenge() + " Einheiten wurden transferiert");
		history.add(h);
		
		if (getLagerFromTree().getKindlager().size() == 0)
		{
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
			node.setUserObject(addInfo(node,getLagerFromTree()).getUserObject());
			model.nodeChanged(node);
		}
		setInfoLabel("Einheiten wurden verteilt",Color.black);
		
		
	}
	
	
	private void lösche_von_Lager() 
	{
		double menge = ((double)percent_Step * all_Units *-1) / 100.0;
		int m = (int)menge;
		int UnitsToDelete;		
		int units_left = Integer.parseInt(textField.getText()) *-1;
	
		Book b = new Book();
		Lager l = getLagerFromTree();
		
		if(l.getKindlager().size() > 0)
		{
			setInfoLabel("Bitte ein Lager auswählen was auch verwalted werden kann", Color.red);
			return;
			//buche_auf_Kinder(l,units_left,m,menge,0);	//Diese Methode wurde aus Zeit gründen nicht Fertiggestellt. Funktioniert nur mit kleinen Abweichungen
		}
		
		if(l == null || units_left <= 0)
		{	
			setInfoLabel("Kein Lager ausgewählt oder keine Einheiten zu verteilen", Color.red);
			return;
		}
		UnitsToDelete =  l.getBestand();
		
		if(menge >= 0.5 && menge < 1.0 && units_left > 0)
		{
			if(UnitsToDelete >= 1)
				b.setMenge(-1);
			else
			{	
				setInfoLabel("Es sind nicht genügend Einheiten vorhanden", Color.red);
				return;
			}
			units_left--;		
		}
		else if(m > 0)
		{
			if (units_left >= m) 
			{			
				if(UnitsToDelete >= m)
					b.setMenge(-m);
				else
				{	
					setInfoLabel("Es sind nicht genügend Einheiten vorhanden", Color.red);
					return;
				}
				
				units_left -= m;
			}
			else
			{
				if(UnitsToDelete >= units_left)
					b.setMenge(-units_left);
				else
				{	
					setInfoLabel("Es sind nicht genügend Einheiten vorhanden", Color.red);
					return;
				}		
				units_left = 0;
			}
		}
		else 
		{
			setInfoLabel("1 Einheit kann nur als ganzes verwalted werden. Ab 50% aufrunden",Color.red);
			return;
		}
				
		textField.setText(-units_left + "");
		//Menge wird hier auf die Lager gebucht
		b.execute(l);
		commands.add(b);
		last_command = b;
		stepChanged();
		
		
		History h = new History();
		h.setLager(l);
		h.setAllowed(true);
		h.setNode((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent());
		//Datum und Zeit einholen und festsetzen
		setDatum();
		h.addTransaction(getDatum() + ": " + b.getMenge() + " Einheiten wurden transferiert");
		history.add(h);
		
		if (getLagerFromTree().getKindlager().size() == 0)
		{
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
			node.setUserObject(addInfo(node,getLagerFromTree()).getUserObject());
			model.nodeChanged(node);
		}
		setInfoLabel("Einheiten wurden verteilt",Color.black);
		
	}	
	
	
	//Folgende Methode ist für die Verteilung auf Kindlager zustädnig wenn ein Root oder Treelager ausgewält wurde
	//Da dies keine Anforderung war und diese Methode nicht ganz Fehlerfrei läuft wurde es aus Zeitgründen eingestellt 
//	private void buche_auf_Lager(Lager l,int units_left, int offset) 
//	{				
//		double menge = ((double)percent_Step * all_Units) / 100.0;
//		int m = (int)menge;
//		int freeUnits;
//	
//		Book b = new Book();
//				
//		units_left += offset;
//		
//		if(l == null || units_left <= 0)
//			return;
//		freeUnits =  l.getKapazitaet() - l.getBestand();
//		
//		if(menge >= 0.5 && menge < 1.0 && units_left > 0)
//		{
//			if(freeUnits >= 1)
//				b.setMenge(1);
//			else
//				return;
//			units_left--;		
//		}
//		else if(m > 0)
//		{
//			if (units_left >= m) 
//			{			
//				if(freeUnits >= m)
//					b.setMenge(m);
//				else
//					return;
//				
//				units_left -= m;
//			}
//			else
//			{
//				if(freeUnits >= units_left)
//					b.setMenge(units_left);
//				else
//					return;			
//				units_left = 0;
//			}
//		}
//		else 
//		{
//			setInfoLabel("Eine größere Prozentzahl wählen",Color.red);
//			return;
//		}
//		
//		
//		textField.setText(units_left + "");
//		b.execute(l);
//		commands.add(b);
//		last_command = b;
//		stepChanged();
//		
//		
//		History h = new History();
//		h.setLager(l);
//		h.setAllowed(true);
//		h.setNode((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent());
//		setDatum();
//		h.addTransaction(getDatum() + ": " + b.getMenge() + " Einheiten wurden transferiert");
//		history.add(h);
//		setInfoLabel("Einheiten wurden verteilt",Color.black);
//		
//		
//	}
//	
//	private void buche_auf_Kinder(Lager l, int units_left, int m, double menge, int offset) 
//	{
//		
//		int count = l.getKindlager().size();
//		int offsets = units_left % count;	
//		offsets += offset;
//		
//		if(offsets == 0)
//			units_left = units_left / count;
//		else
//		{
//			offset = offsets % count;
//			offsets = Math.floorDiv(offsets,count) + offset;
//			
//			units_left = Math.floorDiv(units_left,count) + offsets;
//
//		}
//		
//		
//		for (int i = 0; i < count; i++) 
//		{
//			if(l.getKindlager().get(i).getKindlager().size() > 0)
//				buche_auf_Kinder(l.getKindlager().get(i), units_left, m, menge, offsets);
//			else
//				buche_auf_Lager(l,units_left,offsets);
//		}
//		
//	}

	private Lager getLagerFromTree() {

		String index = tree.getLastSelectedPathComponent().toString();
		
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

	
	private void expandAllNodes(int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }

	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(rowCount, tree.getRowCount());
	    }
	}

	
	private void stepChanged() {
		
		rest_label.setText(percent_Step + "% Schritte von " + all_Units);
		
	}
	
	
	private void undo() {
		int i = commands.indexOf(last_command);
		commands.get(i).undo();
		history.get(i).setAllowed(false);
		
		if(i >= 0)
		{
			if(i > 0)
				last_command = commands.get(i - 1);
			else
				last_command = null;
			
			int units = Integer.parseInt(textField.getText());
			units += commands.get(i).getUnits();
			textField.setText(units + "");
			
			
			commands.get(i).getLager().setBestand(commands.get(i).getLager().getBestand() - commands.get(i).getMenge() * 2);
			
			if (history.get(i).getLager().getKindlager().size() == 0)
			{
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				DefaultMutableTreeNode node = history.get(i).getNode();
				node.setUserObject(addInfo(node,history.get(i).getLager()).getUserObject());
				
				model.nodeChanged(node);
			}
		}
	}
	private void redo() {
		
		int i = 0;
		if(last_command != null)
			i = commands.indexOf(last_command) + 1;
		if(i < commands.size())
		{
			last_command = commands.get(i);
			commands.get(i).redo();
			history.get(i).setAllowed(true);
			last_command = commands.get(i);
			
			int units = Integer.parseInt(textField.getText());
			units -= commands.get(i).getUnits();
			textField.setText(units + "");
			
			commands.get(i).getLager().setBestand(commands.get(i).getLager().getBestand() + commands.get(i).getMenge() * 2);
			
			if (history.get(i).getLager().getKindlager().size() == 0)
			{
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				DefaultMutableTreeNode node = history.get(i).getNode();
				node.setUserObject(addInfo(node,history.get(i).getLager()).getUserObject());
				
				model.nodeChanged(node);
			}
		}

	}

	private void checkTextField() {
		
		if(textField.getText().length() == 0 || textField.getText().equals("0"))
		{
				tree.setEnabled(false);
				return;
		}
		else if(tree != null)
			tree.setEnabled(true);
		
		int units = Integer.parseInt(textField.getText());	
		if(units > capacity || units < currentUnits * -1)
		{
			setInfoLabel("Es sind nicht genügend Resourcen vorhanden",Color.red);
			textField.setForeground(Color.red);
			canBook = false;
		}
		else
		{
			setInfoLabel("Zu verteilende Einheiten: " + textField.getText(),Color.black);
			textField.setForeground(Color.black);
			canBook = true;
		}
	}
	
	/**
	 * Getter-Methode des Buchungsdatums und der Zeit.
	 * Hier wird auch dies formatiert.
	 * @return gibt die Buchungszeit und -datum im folgendem Format als String aus: dd.MM.yyyy HH:mm:ss
	 */
	public String getDatum() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		return simpleDateFormat.format(datum);
	}
	/**
	 * Setter-Methode für das Datum und die Zeit
	 */
	public void setDatum() {
		datum = new Date();
		
	}
	public void setInfoLabel(String info,Color color)
	{
		if (color != null && info_Label != null)
		{
			info_Label.setForeground(color);
			info_Label.setText(info);
		}
	}
}
