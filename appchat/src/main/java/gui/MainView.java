package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import tds.BubbleText;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JList;
import java.awt.Component;
import javax.swing.JToggleButton;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.Box;
import tds.BubbleText;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JTextField;

public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("AppChat");
		setBounds(100, 100, 881, 557);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 845, 55);
		layeredPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		ImageIcon icon = new ImageIcon("C:\\Users\\Juanjo\\Desktop\\Apuntes\\TDS\\proyecto\\appchat\\appchat\\icons\\profile_picture.png");
		Image imageIcon = icon.getImage();
		Image newImage = imageIcon.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnNewButton = new JButton("Usuario");
		btnNewButton.setIcon(new ImageIcon(newImage));
		btnNewButton.setOpaque(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		panel.add(btnNewButton);
		
		panel.add(Box.createHorizontalStrut(75));
		
		ImageIcon icon2 = new ImageIcon("C:\\Users\\Juanjo\\Desktop\\Apuntes\\TDS\\proyecto\\appchat\\appchat\\icons\\status_icon.png");
		Image imageIcon2 = icon2.getImage();
		Image newImage2 = imageIcon2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnNewButton_3 = new JButton();
		btnNewButton_3.setIcon(new ImageIcon(newImage2));
		btnNewButton_3.setOpaque(false);
		btnNewButton_3.setContentAreaFilled(false);
		btnNewButton_3.setBorderPainted(false);
		btnNewButton_3.setFocusPainted(false);
		panel.add(btnNewButton_3);
		
		ImageIcon icon3 = new ImageIcon("C:\\Users\\Juanjo\\Desktop\\Apuntes\\TDS\\proyecto\\appchat\\appchat\\icons\\3dots.jpg");
		Image imageIcon3 = icon3.getImage();
		Image newImage3 = imageIcon3.getScaledInstance(5, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnNewButton_4 = new JButton();
		btnNewButton_4.setIcon(new ImageIcon(newImage3));
		btnNewButton_4.setOpaque(false);
		btnNewButton_4.setContentAreaFilled(false);
		btnNewButton_4.setBorderPainted(false);
		btnNewButton_4.setFocusPainted(false);
		panel.add(btnNewButton_4);
		
		panel.add(Box.createHorizontalStrut(350));
		
		JButton btnNewButton_5 = new JButton("Usuario Chat");
		panel.add(btnNewButton_5);
		
		ImageIcon icon4 = new ImageIcon("C:\\Users\\Juanjo\\Desktop\\Apuntes\\TDS\\proyecto\\appchat\\appchat\\icons\\lupa.jpg");
		Image imageIcon4 = icon4.getImage();
		Image newImage4 = imageIcon4.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnNewButton_2 = new JButton();
		btnNewButton_2.setIcon(new ImageIcon(newImage4));
		btnNewButton_2.setOpaque(false);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setFocusPainted(false);
		panel.add(btnNewButton_2);
		
		ImageIcon icon5 = new ImageIcon("C:\\Users\\Juanjo\\Desktop\\Apuntes\\TDS\\proyecto\\appchat\\appchat\\icons\\3dots.jpg");
		Image imageIcon5 = icon5.getImage();
		Image newImage5 = imageIcon5.getScaledInstance(5, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnNewButton_1 = new JButton();
		btnNewButton_1.setIcon(new ImageIcon(newImage5));
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setFocusPainted(false);
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 55, 277, 452);
		layeredPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnChat = new JButton("Chat 1");
		panel_1.add(btnChat);
		
		JButton btnChat_1 = new JButton("Chat 2                                 <hora>");
		btnChat_1.setToolTipText("");
		panel_1.add(btnChat_1);
		
		//---------------------------------------CHAT
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(297, 55, 558, 419);
		
		Border blackline = BorderFactory.createLineBorder(Color.black);
		panel_2.setBorder(blackline);
		
		layeredPane.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel_2.add(verticalStrut);
		
		BubbleText burbuja;
		burbuja = new BubbleText(panel_2, "Viva España", Color.GRAY, "Mi padre  ", BubbleText.SENT);
		panel_2.add(burbuja);
		
		BubbleText burbuja2;
		burbuja2 = new BubbleText(panel_2, "Y viva VOX", Color.GREEN, "Franco", BubbleText.RECEIVED);
		panel_2.add(burbuja2);
		
		//----------------------------------------TEXTO
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(297, 474, 558, 33);
		
		layeredPane.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		ImageIcon icon6 = new ImageIcon("C:\\Users\\Juanjo\\Desktop\\Apuntes\\TDS\\proyecto\\appchat\\appchat\\icons\\emoji.png");
		Image imageIcon6 = icon6.getImage();
		Image newImage6 = imageIcon6.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnd = new JButton();
		btnd.setIcon(new ImageIcon(newImage6));
		btnd.setOpaque(false);
		btnd.setContentAreaFilled(false);
		btnd.setBorderPainted(false);
		btnd.setFocusPainted(false);
		panel_3.add(btnd);
		
		textField = new JTextField();
		panel_3.add(textField);
		textField.setColumns(10);
		
		ImageIcon icon7 = new ImageIcon("C:\\Users\\Juanjo\\Desktop\\Apuntes\\TDS\\proyecto\\appchat\\appchat\\icons\\send.png");
		Image imageIcon7 = icon7.getImage();
		Image newImage7 = imageIcon7.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnEnviar = new JButton();
		btnEnviar.setIcon(new ImageIcon(newImage7));
		btnEnviar.setOpaque(false);
		btnEnviar.setContentAreaFilled(false);
		btnEnviar.setBorderPainted(false);
		btnEnviar.setFocusPainted(false);
		panel_3.add(btnEnviar);
		
	}
}
