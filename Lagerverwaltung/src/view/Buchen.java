package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Scrollbar;
import java.awt.ScrollPane;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.Label;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DropMode;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Buchen {

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
		
		JButton btnNewButton_1 = new JButton("+1");
		btnNewButton_1.setBounds(347, 69, 64, 23);
		panel.add(btnNewButton_1);
		
		JButton button = new JButton("+10");
		button.setBounds(421, 69, 64, 23);
		panel.add(button);
		
		JButton button_1 = new JButton("+100");
		button_1.setBounds(495, 69, 64, 23);
		panel.add(button_1);
		
		JButton button_2 = new JButton("+1000");
		button_2.setBounds(569, 69, 72, 23);
		panel.add(button_2);
		
		JButton button_3 = new JButton("-1000");
		button_3.setBounds(569, 103, 72, 23);
		panel.add(button_3);
		
		JButton button_4 = new JButton("-100");
		button_4.setBounds(495, 103, 64, 23);
		panel.add(button_4);
		
		JButton button_5 = new JButton("-10");
		button_5.setBounds(421, 103, 64, 23);
		panel.add(button_5);
		
		JButton button_6 = new JButton("-1");
		button_6.setBounds(347, 103, 64, 23);
		panel.add(button_6);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		
		for ( int i = 0; i < 4; i++ ){
		  model.addElement( "test" );
		}
		
		JButton btnNewButton = new JButton("Buchen");
		btnNewButton.setBounds(587, 387, 105, 38);
		frame.getContentPane().add(btnNewButton);
		
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
		
		String[] ar = getLager();
		JList list_1 = new JList(ar);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	

		scrollPane.setViewportView(list_1);
	}

	private String[] getLager() 
	{
		String[] lagerStructure =  {"element1","element2","element3"};
		
		
		return lagerStructure;
	}
}
