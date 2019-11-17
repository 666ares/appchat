package gui;


import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.border.Border;

import gui.elements.BotonChat;
import gui.elements.BoxChat;
import gui.elements.Buscador;
import gui.elements.Estados;
import gui.elements.InfoUChat;
import gui.elements.InfoUsuario;
import gui.elements.OpcionesChat;
import gui.elements.OpcionesUser;
import tds.BubbleText;

import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.Box;
import java.awt.Color;
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
		
		//Boton Mi Usuario
		BotonChat boton1 = new BotonChat("icons/profile_picture.png", "Usuario");
		boton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InfoUsuario infousuario = new InfoUsuario("Usuario", "Gang Gang");
				infousuario.makeVisible();
			}
		});
		panel.add(boton1);
		
		panel.add(Box.createHorizontalStrut(75));
		
		//Status
		BotonChat boton2 = new BotonChat("icons/status_icon.png");
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Estados estados = new Estados();
				estados.makeVisible();
			}
		});
		panel.add(boton2);
		
		//Opciones de Usuario
		BotonChat boton3 = new BotonChat("icons/3dots.jpg", 5, 20);
		boton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpcionesUser opciones = new OpcionesUser();
				opciones.makeVisible();
			}
		});
		panel.add(boton3);
		
		panel.add(Box.createHorizontalStrut(310));
		
		//Boton Usuario Chat
		BotonChat boton4 = new BotonChat("icons/profile_picture.png", "Usuario Chat");
		boton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InfoUChat infochat = new InfoUChat("Usuario Chat", "Saludoss");
				infochat.makeVisible();
			}
		});
		panel.add(boton4);
		
		//Boton Buscador
		BotonChat boton5 = new BotonChat("icons/lupa.jpg");
		boton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Buscador buscador = new Buscador();
				buscador.makeVisible();
			}
		});
		panel.add(boton5);
		
		//Boton Opciones de Chat
		BotonChat boton6 = new BotonChat("icons/3dots.jpg", 5, 20);
		boton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpcionesChat opciones = new OpcionesChat();
				opciones.makeVisible();
			}
		});
		panel.add(boton6);
		
		//--------------------------------------LISTA DE CHATS
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 55, 277, 452);
		layeredPane.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		BoxChat chat1 = new BoxChat("Pipi Estrada", "Ooole oleee es viernesss");
		panel_1.add(chat1);
		
		BoxChat chat2 = new BoxChat("Ruben Ortega", "Guapo, atento y homosexual");
		panel_1.add(chat2);
		
		//---------------------------------------CHAT
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(297, 55, 558, 419);
		
		Border blackline2 = BorderFactory.createLineBorder(Color.black);
		panel_2.setBorder(blackline2);
		
		layeredPane.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel_2.add(verticalStrut);
		
		BubbleText burbuja;
		burbuja = new BubbleText(panel_2, "Hola", Color.GRAY, "Yo  ", BubbleText.SENT);
		panel_2.add(burbuja);
		
		BubbleText burbuja2;
		burbuja2 = new BubbleText(panel_2, "Ey", Color.GREEN, "Usuario 2", BubbleText.RECEIVED);
		panel_2.add(burbuja2);
		
		//----------------------------------------TEXTO
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(297, 474, 558, 33);
		
		layeredPane.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		BotonChat boton7 = new BotonChat("icons/emoji.png", 25, 25);
		panel_3.add(boton7);
		
		textField = new JTextField();
		panel_3.add(textField);
		textField.setColumns(10);
		
		BotonChat boton8 = new BotonChat("icons/send.png");
		panel_3.add(boton8);
		
	}
	
}
