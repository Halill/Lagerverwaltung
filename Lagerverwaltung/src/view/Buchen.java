package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import controller.Book;
import controller.CommandManager;
import model.Buchung;
import model.Lager;
import javax.swing.JTree;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Buchen extends Warehouse{

	JFrame frame;
	private JTextField textField;
	private int percent_Step = 5;
	private int all_Units = 1;
	private JLabel rest_label;
	private JTree tree;
    private ArrayList<CommandManager> commands = new ArrayList<CommandManager>();
    private CommandManager last_command;

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
		frame.setBounds(100, 100, 708, 464);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		panel.setBounds(0, 0, 692, 164);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
			      char enter = evt.getKeyChar();
			        if(!(Character.isDigit(enter))){
			            evt.consume();
			        }			      
			}
		});
		textField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {	
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Zu verteilende Einheiten: " + textField.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {

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
		
		JButton rem_10 = new JButton("-10");
		rem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(count - 10 + "");
				all_Units = Integer.parseInt(textField.getText());
			}
		});
		rem_10.setBounds(421, 103, 64, 23);
		panel.add(rem_10);
		
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
				Buchung buchen = new Buchung();
				if(all_Units > 0)
					buchen.setBuchungstyp(1);
				else
					buchen.setBuchungstyp(0);

				buchen.setDatum();				
				buchen.setBuchungLagerListe(getLager());
				buchen.setMenge(Integer.parseInt(textField.getText()));
								
				if (buchen.getMenge() > 0 ) 
					buchen.zubuchen();				
				else 
					buchen.abbuchen();
				
				refresh();
			}
		});
		Buchen.setBounds(587, 387, 105, 38);
		frame.getContentPane().add(Buchen);
		
		JButton btnVor = new JButton("Vor");
		btnVor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent click) {
				if (commands.size() > 0) {
					redo();
				}
			}
		});
		btnVor.setBounds(484, 387, 105, 38);
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
		btnZurck.setBounds(381, 387, 105, 38);
		frame.getContentPane().add(btnZurck);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 164, 692, 224);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		tree = new JTree();
		tree.setModel(getTree().getModel());		
		scrollPane.setViewportView(tree);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evn) 
			{
				buche_auf_Lager();
			}
		});
		expandAllNodes(0,tree.getRowCount());
	}

	private void buche_auf_Lager() 
	{		
		int Units_left = Integer.parseInt(textField.getText());
		if(Units_left <= 0)
			return;
		
		Book b = new Book();
		
		double menge = ((double)percent_Step * all_Units) / 100.0;
		int m = (int)menge;
		
		if(menge >= 0.5 && menge < 1.0 && Units_left > 0)
		{
			b.setMenge(1);
			Units_left--;		
		}
		else if(m > 0)
		{
			if (Units_left >= m) 
			{			
				b.setMenge(m);
				Units_left -= m;
			}
			else
			{
				b.setMenge(Units_left);
				Units_left = 0;
			}
		}
		else 
		{
			System.out.println("Eine größere Prozentzahl wählen");
			return;
		}
		
		textField.setText(Units_left + "");
		b.execute(getLagerFromTree());
		commands.add(b);
		last_command = b;
		
		stepChanged();
		
	}
	
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
		if(i >= 0)
		{
			if(i > 0)
				last_command = commands.get(i - 1);
			else
				last_command = null;
			
			int units = Integer.parseInt(textField.getText());
			units += commands.get(i).getUnits();
			textField.setText(units + "");
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
			last_command = commands.get(i);
			
			int units = Integer.parseInt(textField.getText());
			units -= commands.get(i).getUnits();
			textField.setText(units + "");
		}

	}
}
