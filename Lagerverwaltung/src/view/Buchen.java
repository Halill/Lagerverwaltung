package view;


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
/**
 * Im Fenster Buchen k�nnen Buchungen auf Lager erfolgen. Als erstes wird eine Buchungsmenge bestimmt, danach wird der Buchungsanteil bestimmt.<br>
 * Beim Klicken auf ein Lager wird diese prozentuale Menge auf dieses Lager gebucht. Unten links stehen eventuelle Fehlermeldungen oder wie viel noch gebucht werden kann.<br>
 * Des Weitern gibt es einen "vor" und "zur�ck" Button, der das hin und her laufen zwischen Buchungsschritte erm�glicht.<br>
 * Wird der Button buchen gedr�ckt werden endg�ltig die Buchungen durchgef�hrt.
 */
public class Buchen extends Warehouse{

	public JFrame frame;
	Warehouse warehouse;
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
	 * Konstruktor f�r die Klasse. �bergeben wird das Warehouse Fenster.
	 * @param wareh  das Fenster Warehouse wird �bergeben
	 */
	public Buchen(Warehouse wareh) {
		warehouse = wareh;
		initialize();	
	}
	/**
	 * In dieser Methode wird das komplette Fenster initialisiert.
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
					setInfoLabel("Es m�ssen alle Einheiten verbucht sein", Color.red);;
					return;
				}
				InstanceH.getInstance().addHistory(history);
				ObserverTree.getInstance().setTreeModel((DefaultTreeModel) tree.getModel());
				refresh();
				frame.dispose();
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
						l�sche_von_Lager();
					}
				}
			}
			
		});
		tree.setModel(warehouse.getTree().getModel());		
		scrollPane.setViewportView(tree);

		expandAllNodes(0,tree.getRowCount());
		getAllCapacity(); 
		textField.setForeground(Color.black);
		
		info_Label = new JLabel("");
		info_Label.setBounds(10, 394, 378, 25);
		frame.getContentPane().add(info_Label);
	}
	/**
	 * Methode, in der das Attribut capacity und currentUnits bestimmt wird. Es werden hierbei alle Lager ber�cksichtigt.
	 */
	private void getAllCapacity() {
		
		capacity = 0;


		for (int i = 0; i < tree.getModel().getChildCount(tree.getModel().getRoot()); i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getModel().getChild(tree.getModel().getRoot(), i);
			TreePath path = new TreePath(node.getPath());
			tree.setSelectionPath(path);
			Lager l = getLagerFromTree();
			if(l == null)
				return;
			capacity += (l.getKapazitaet() - l.getBestand());
			currentUnits += l.getBestand();
		}
		System.out.println(capacity + " Einheiten k�nnen noch verteilt werden und " + currentUnits + " Einheiten sind vorhanden");
		canBook = true;
	}
	/**
	 * Methode, die beim Klicken auf ein Lager im Buchen Fenster ausgef�hrt wird. <br>
	 * Hier werden auch die Buchungsbedinungen �berpr�ft:<br>
	 * - das ausgew�hlte Lager besitzt keine Kindlager.<br>
	 * - ein Lager �berhaupt wurde ausgew�hlt  oder es gibt eine Restbuchungsmenge, die zu verteilen w�re.<br>
	 * - ausreichend Kapazit�t im Lager<br>
	 * - die Buchungsmenge als ganze Zahle gebucht wird<br>
	 * Diese Methode wird f�r Zubuchungen gebraucht.
	 */
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
			setInfoLabel("Bitte ein Lager ausw�hlen, das auch verwaltet werden kann", Color.red);
			return;
			//buche_auf_Kinder(l,units_left,m,menge,0);	//Diese Methode wurde aus Zeit gr�nden nicht Fertiggestellt. Funktioniert nur mit kleinen Abweichungen
		}
		
		if(l == null || units_left <= 0)
		{	
			setInfoLabel("Kein Lager ausgew�hlt oder keine Einheiten zu verteilen", Color.red);
			return;
		}
		freeUnits =  l.getKapazitaet() - l.getBestand();
		
		if(menge >= 0.5 && menge < 1.0 && units_left > 0)
		{
			if(freeUnits >= 1)
				b.setMenge(1);
			else
			{	
				setInfoLabel("Die Kapazit�t reicht nicht aus", Color.red);
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
					setInfoLabel("Die Kapazit�t reicht nicht aus", Color.red);
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
					setInfoLabel("Die Kapazit�t reicht nicht aus", Color.red);
					return;
				}		
				units_left = 0;
			}
		}
		else 
		{
			setInfoLabel("Eine Einheit kann nur als ganzes verwaltet werden. Ab 50% aufrunden.",Color.red);
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
	/**
	 * Methode, die beim Klicken auf ein Lager im Buchen Fenster ausgef�hrt wird. <br>
	 * Hier werden auch die Buchungsbedinungen �berpr�ft:<br>
	 * - das ausgew�hlte Lager besitzt keine Kindlager.<br>
	 * - ein Lager �berhaupt wurde ausgew�hlt  oder es gibt eine Restbuchungsmenge, die zu verteilen w�re.<br>
	 * - ausreichend Bestand im Lager<br>
	 * - die Buchungsmenge als ganze Zahle gebucht wird<br>
	 * Diese Methode wird f�r Abbuchungen gebraucht.
	 */
	private void l�sche_von_Lager() 
	{
		double menge = ((double)percent_Step * all_Units *-1) / 100.0;
		int m = (int)menge;
		int UnitsToDelete;		
		int units_left = Integer.parseInt(textField.getText()) *-1;
	
		Book b = new Book();
		Lager l = getLagerFromTree();
		
		if(l.getKindlager().size() > 0)
		{
			setInfoLabel("Bitte ein Lager ausw�hlen was auch verwaltet werden kann", Color.red);
			return;
			//buche_auf_Kinder(l,units_left,m,menge,0);	//Diese Methode wurde aus Zeit gr�nden nicht Fertiggestellt. Funktioniert nur mit kleinen Abweichungen
		}
		
		if(l == null || units_left <= 0)
		{	
			setInfoLabel("Kein Lager ausgew�hlt oder keine Einheiten zu verteilen", Color.red);
			return;
		}
		UnitsToDelete =  l.getBestand();
		
		if(menge >= 0.5 && menge < 1.0 && units_left > 0)
		{
			if(UnitsToDelete >= 1)
				b.setMenge(-1);
			else
			{	
				setInfoLabel("Es sind nicht gen�gend Einheiten vorhanden", Color.red);
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
					setInfoLabel("Es sind nicht gen�gend Einheiten vorhanden", Color.red);
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
					setInfoLabel("Es sind nicht gen�gend Einheiten vorhanden", Color.red);
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
	/**
	 * Getter-Methode f�r Lager im Jtree. 
	 * @return Gibt ein Lager-Objekt zur�ck
	 */
	private Lager getLagerFromTree() {

		String index = tree.getLastSelectedPathComponent().toString();
		
		for(Lager l : warehouse.getLager())
		{
			if(index.contains(l.getName()))
			{
				System.out.println(l.getName());
				return l;
			}
		}
		return null;
		
	}
	/**
	 *  Rekursive Methode, die den ganzen Baum beim �ffnen des Fenster aufklappt.
	 * @param startingIndex Index, von dem gestartet wird. Wird 0 angegeben, wird der ganze Baum aufgeklappt.
	 * @param rowCount Anzahl Knoten eines Baums.
	 */
	private void expandAllNodes(int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }

	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(rowCount, tree.getRowCount());
	    }
	}
	/**
	 * Aktualisiert das kleine Textfeld, indem angezeigt wird, wie viel Prozent der Gesamtbuchungsmenge auf ein Lager gebucht wird.
	 */
	private void stepChanged() {
		
		rest_label.setText(percent_Step + "% Schritte von " + all_Units);
		
	}
	/**
	 * wird aufgerufen, wenn der Button "vor" geklickt wird. Somit wird ein Buchungsschritt vorgegangen.
	 */
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
			units += commands.get(i).getMenge();
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
	/**
	 * wird aufgerufen, wenn der Button "zur�ck" geklickt wird. Somit wird ein Buchungsschirtt zur�ckgegangen.
	 */
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
			units -= commands.get(i).getMenge();
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
	/**
	 * aktualisiert das kleine Textfeld unten links, in dem steht wie viele Einheiten noch gebucht werden soll. <br>
	 * Des Weiteren werden in diesem Textfeld noch weitere Benachrichtigungen angezeigt.
	 */
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
			setInfoLabel("Es sind nicht gen�gend Resourcen vorhanden",Color.red);
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
	 * Setter-Methode f�r das Datum und die Zeit
	 */
	public void setDatum() {
		datum = new Date();
		
	}
	/**
	 * Methode, die das Textfeld unten links aktualisiert. 
	 * @param info Information, die angezeigt werden soll
	 * @param color in welcher Farbe die Information angezeigt werden soll.
	 */
	public void setInfoLabel(String info,Color color)
	{
		if (color != null && info_Label != null)
		{
			info_Label.setForeground(color);
			info_Label.setText(info);
		}
	}
}
//Folgende Methode ist f�r die Verteilung auf Kindlager zust�dnig wenn ein Root oder Treelager ausgew�lt wurde
//Da dies keine Anforderung war und diese Methode nicht ganz Fehlerfrei l�uft wurde es aus Zeitgr�nden eingestellt 
//private void buche_auf_Lager(Lager l,int units_left, int offset) 
//{				
//	double menge = ((double)percent_Step * all_Units) / 100.0;
//	int m = (int)menge;
//	int freeUnits;
//
//	Book b = new Book();
//			
//	units_left += offset;
//	
//	if(l == null || units_left <= 0)
//		return;
//	freeUnits =  l.getKapazitaet() - l.getBestand();
//	
//	if(menge >= 0.5 && menge < 1.0 && units_left > 0)
//	{
//		if(freeUnits >= 1)
//			b.setMenge(1);
//		else
//			return;
//		units_left--;		
//	}
//	else if(m > 0)
//	{
//		if (units_left >= m) 
//		{			
//			if(freeUnits >= m)
//				b.setMenge(m);
//			else
//				return;
//			
//			units_left -= m;
//		}
//		else
//		{
//			if(freeUnits >= units_left)
//				b.setMenge(units_left);
//			else
//				return;			
//			units_left = 0;
//		}
//	}
//	else 
//	{
//		setInfoLabel("Eine gr��ere Prozentzahl w�hlen",Color.red);
//		return;
//	}
//	
//	
//	textField.setText(units_left + "");
//	b.execute(l);
//	commands.add(b);
//	last_command = b;
//	stepChanged();
//	
//	
//	History h = new History();
//	h.setLager(l);
//	h.setAllowed(true);
//	h.setNode((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent());
//	setDatum();
//	h.addTransaction(getDatum() + ": " + b.getMenge() + " Einheiten wurden transferiert");
//	history.add(h);
//	setInfoLabel("Einheiten wurden verteilt",Color.black);
//	
//	
//}
//
//private void buche_auf_Kinder(Lager l, int units_left, int m, double menge, int offset) 
//{
//	
//	int count = l.getKindlager().size();
//	int offsets = units_left % count;	
//	offsets += offset;
//	
//	if(offsets == 0)
//		units_left = units_left / count;
//	else
//	{
//		offset = offsets % count;
//		offsets = Math.floorDiv(offsets,count) + offset;
//		
//		units_left = Math.floorDiv(units_left,count) + offsets;
//
//	}
//	
//	
//	for (int i = 0; i < count; i++) 
//	{
//		if(l.getKindlager().get(i).getKindlager().size() > 0)
//			buche_auf_Kinder(l.getKindlager().get(i), units_left, m, menge, offsets);
//		else
//			buche_auf_Lager(l,units_left,offsets);
//	}
//	
//}