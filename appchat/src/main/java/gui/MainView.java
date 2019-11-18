package gui;


import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.border.Border;

import controlador.ControladorUsuarios;
import dominio.Usuario;
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
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

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
		
		//---------------------------------------Barra de tareas
		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 845, 55);
		layeredPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		Usuario u = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
		String ruta_imagen = u.getImagenPerfil();
		String nombre = u.getNombre();
		
		// !!!!! QUITAR NOMBRE, SÓLO TIENE QUE SALIR LA FOTO...
		
		
		//Boton Mi Usuario
		BotonChat boton1 = new BotonChat(ruta_imagen, nombre);
		boton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InfoUsuario infousuario = new InfoUsuario();
				infousuario.makeVisible();
			}
		});
		panel.add(boton1);
		
		//Espacio
		
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
		
		//Boton Usuario Chat
		final BotonChat boton4 = new BotonChat("", "");
		boton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InfoUChat infochat = new InfoUChat("Usuario Chat", "Saludoss");
				infochat.makeVisible();
			}
		});
		panel.add(boton4);
		
		panel.add(Box.createHorizontalStrut(320));
		
		//Boton Buscador
		final BotonChat boton5 = new BotonChat("icons/lupa.jpg");
		boton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Buscador buscador = new Buscador();
				buscador.makeVisible();
			}
		});
		boton5.makeVisible(false);
		panel.add(boton5);
		
		//Boton Opciones de Chat
		final BotonChat boton6 = new BotonChat("icons/3dots.jpg", 5, 20);
		boton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpcionesChat opciones = new OpcionesChat();
				opciones.makeVisible();
			}
		});
		boton6.makeVisible(false);
		panel.add(boton6);
		
		//---------------------------------------CHAT INICIO
		final JPanel panel_2 = new JPanel();
		panel_2.setBounds(297, 55, 558, 419);
		Border blackline2 = BorderFactory.createLineBorder(Color.black);
		panel_2.setBorder(blackline2);
		layeredPane.add(panel_2);
		
		final JLabel texto_default = new JLabel("Selecciona un chat para empezar a hablar");
		texto_default.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_2.add(texto_default);
		
		//--------------------------------------LISTA DE CHATS
		JScrollPane listaChat = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listaChat.setBounds(10, 55, 277, 452);
		layeredPane.add(listaChat);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 55, 277, 452);
		panel_1.setPreferredSize(new Dimension(277, 75*10+20));//Calcular altura dependiendo de los chats que existan
		listaChat.setViewportView(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		final LinkedList<BoxChat> chats = new LinkedList<BoxChat>();
		
		for(int i = 0; i<10; i++) { //10 Chats de prueba
			final BoxChat chat = new BoxChat("Usuario "+i, "ultimo mensaje "+i); //Usuario y ultima mensaje del chat
			chats.add(chat);
			chat.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Actualizamos la información de la barra de botones
					reiniciarPaneles(chats);
					boton4.setText("Ruben Ortega"); //Obtener nombre del usuario del chat
					boton4.changeIcon("icons/profile_picture.png", 30, 30); //Obtener imagen del usuario del chat
					boton5.makeVisible(true);
					boton6.makeVisible(true);
					chat.setResaltar(true);
					panel_2.removeAll();
					panel_2.repaint();
					//RELLENAMOS EL CHAT CON LOS MENSAJES DE LA CONVERSACION -> Pasar los dos usuarios y lista de mensajes
					rellenarChat(panel_2, "Hola ", "Respuesta de prueba");
				}
			});
			panel_1.add(chat);
		}
		
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
	
	void rellenarChat(JPanel panel, String ... mensajes) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut);
		//Obtener lista de mensajes y recorrerla poniendo las burbujas en el panel
		boolean propietario = true;
		for (String mensaje : mensajes) {
			if(propietario) {
				BubbleText burbuja;
				burbuja = new BubbleText(panel, mensaje, Color.GRAY, "Yo  ", BubbleText.SENT);
				panel.add(burbuja);
				propietario = false;
			} else {
				BubbleText burbuja;
				burbuja = new BubbleText(panel, mensaje, Color.GREEN, "Otro Usuario", BubbleText.RECEIVED);
				panel.add(burbuja);
				propietario = true;
			}
			
		}
	}
	
	void reiniciarPaneles(LinkedList<BoxChat> chats) {
		for (BoxChat boxChat : chats) {
			boxChat.setResaltar(false);
		}
	}
	
}
