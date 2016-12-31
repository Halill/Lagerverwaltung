package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Buchung;
import model.Lager;
import model.Model;


public class Buchen extends Warehouse{

	JFrame frame;
	private JTextField textField;

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
		panel.setBounds(0, 0, 692, 142);
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
			}
		});
		add_one.setBounds(347, 69, 64, 23);
		panel.add(add_one);
		
		JButton add_ten = new JButton("+10");
		add_ten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(10 + count + "");
			}
		});
		add_ten.setBounds(421, 69, 64, 23);
		panel.add(add_ten);
		
		JButton add_hundred = new JButton("+100");
		add_hundred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(100 + count + "");
			}
		});
		add_hundred.setBounds(495, 69, 64, 23);
		panel.add(add_hundred);
		
		JButton add_thousand = new JButton("+1000");
		add_thousand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(1000 + count + "");
			}
		});
		add_thousand.setBounds(569, 69, 72, 23);
		panel.add(add_thousand);
		
		JButton rem_thousand = new JButton("-1000");
		rem_thousand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(count - 1000 + "");
			}
		});
		rem_thousand.setBounds(569, 103, 72, 23);
		panel.add(rem_thousand);
		
		JButton rem_hundred = new JButton("-100");
		rem_hundred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(count - 100 + "");
			}
		});
		rem_hundred.setBounds(495, 103, 64, 23);
		panel.add(rem_hundred);
		
		JButton rem_10 = new JButton("-10");
		rem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(count - 10 + "");
			}
		});
		rem_10.setBounds(421, 103, 64, 23);
		panel.add(rem_10);
		
		JButton rem_one = new JButton("-1");
		rem_one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = Integer.parseInt(textField.getText());
				textField.setText(count - 1 + "");			
			}
		});
		rem_one.setBounds(347, 103, 64, 23);
		panel.add(rem_one);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		
		for ( int i = 0; i < 4; i++ ){
		  model.addElement( "test" );
		}
		
		JButton Buchen = new JButton("Buchen");

		Buchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Buchung buchen = new Buchung();
				if(Integer.parseInt(textField.getText()) > 0)
					buchen.setBuchungstyp(1);
				else
					buchen.setBuchungstyp(0);
				
				Double d = 1.0 / getLager().size();
				Double[] key = {d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d,d};
				buchen.setVerteilungsschluessel(key);
				buchen.setDatum();				
				buchen.setBuchungLagerListe(getLager());
				buchen.setMenge(Integer.parseInt(textField.getText()));
				buchen.zubuchen();
				refresh();
			}
		});
		Buchen.setBounds(587, 387, 105, 38);
		frame.getContentPane().add(Buchen);
		
		JButton btnVor = new JButton("Vor");
		btnVor.setBounds(484, 387, 105, 38);
		frame.getContentPane().add(btnVor);
		
		JButton btnZurck = new JButton("Zur\u00FCck");
		btnZurck.setBounds(381, 387, 105, 38);
		frame.getContentPane().add(btnZurck);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 141, 692, 247);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		String[] ar = getLager(getModel().getLagerliste());
		JList list_1 = new JList(ar);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	

		scrollPane.setViewportView(list_1);
	}

	private String[] getLager(ArrayList<Lager> arrayList) 
	{
		String[] lagerStructure =  new String[arrayList.size()];
		
		int i = 0;
		for(Lager lager : arrayList)
		{
			lagerStructure[i] = lager.getName();
			i++;
		}
		
		
		return lagerStructure;
	}
}
