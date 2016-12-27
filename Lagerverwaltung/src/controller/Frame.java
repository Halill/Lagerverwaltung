package controller;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame 
{
	JFrame frame;
	
	public JFrame Window(int x, int y, String WindowName)
	{
		frame = new JFrame(WindowName);
		frame.setSize(x,y);
		frame.setDefaultCloseOperation(3);
		return frame;	
	}
	
	public JPanel Panel(int sizex, int sizey, int posx,int posy)
	{
		JPanel panel = new JPanel(true);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setSize(sizex,sizey);
		panel.setLocation(posx,posy);
		panel.setBackground(Color.BLACK);
		return panel;
	}
	
	public JButton Button(int sizex, int sizey, int posx,int posy, String ButtonText)
	{
		JButton button = new JButton(ButtonText);
		button.setSize(sizex,sizey);
		button.setLocation(posx,posy);
		return button;
	}

}