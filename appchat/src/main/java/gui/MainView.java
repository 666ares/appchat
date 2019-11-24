package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.border.Border;

import controlador.ControladorUsuarios;
import dominio.Contacto;
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

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	
	private JLayeredPane layeredPane;
	
	private BotonChat boton1;
	private BotonChat boton2;
	private BotonChat boton3;
	private BotonChat boton4;
	private BotonChat boton5;
	private BotonChat boton6;
	private BotonChat boton7;
	private BotonChat boton8;
	
	private JLabel texto_default;
	private JScrollPane listaChat;
	private JTextField 	textField;
	
	public MainView() {
		initialize();
	}

	private void initialize() {
		setTitle("AppChat");
		setBounds(100, 100, 881, 557);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		setContentPane(contentPane);
		
		layeredPane = new JLayeredPane();
		contentPane.add(layeredPane);
		
		// ==========================
		// Barra de opciones superior
		// ==========================
		panel = new JPanel();
		panel.setBounds(10, 0, 845, 55);
		layeredPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		// ===================
		// Botón de mi usuario
		// ===================
		
		// Llamada al controlador para recuperar el nombre y la
		// foto de perfil del usuario.
		Usuario u = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
		String ruta_imagen = u.getImagenPerfil();
		String nombre = u.getNombre();
				
		boton1 = new BotonChat(ruta_imagen, nombre);
		boton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InfoUsuario infousuario = new InfoUsuario();
				infousuario.makeVisible();
				infousuario.addWindowListener(new WindowAdapter() {
					
					@Override
					public void windowClosing(WindowEvent e) {
						boton1.changeIcon(
								ControladorUsuarios.getUnicaInstancia().getUsuarioActual().getImagenPerfil(), 
								30, 
								30);
						boton1.repaint();	
					}
					
				});
			}
		});
		
		panel.add(boton1);
		
		// ========
		// Estados
		// ========
		boton2 = new BotonChat("icons/status_icon.png");
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Estados estados = new Estados();
				estados.makeVisible();
			}
		});
		
		panel.add(boton2);
		
		// ===================
		// Opciones de usuario
		// ===================
		boton3 = new BotonChat("icons/3dots.jpg", 5, 20);
		boton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Obtener número de contactos del usuario antes de que se cierre
				// la ventana para añadir un nuevo contacto
				Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				List<Contacto> contactos = ControladorUsuarios.getUnicaInstancia().obtenerContactos(usuarioAct.getLogin());
				int size = contactos.size();
				
				OpcionesUser opciones = new OpcionesUser();
				opciones.makeVisible();
				opciones.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						// Obtener número de contactos después de cerrar la ventana para
						// añadir un contacto
						Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
						List<Contacto> contactos = ControladorUsuarios.getUnicaInstancia().obtenerContactos(usuarioAct.getLogin());
						
						// Si el número de contactos es distinto es porque se agregó uno nuevo
						// Hay que repintar la lista de chats
						if (contactos.size() == size + 1)
							mostrarChats();
					}
				});
			}
		});
		
		panel.add(boton3);
		
		// ======================
		// Botón del otro usuario
		// ======================
		boton4 = new BotonChat("", "");
		boton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Al llamar a mostrarChats(), para cada chat, se guardará
				// el contacto en el botón4
				Contacto contacto = boton4.getContacto();
				InfoUChat infochat = new InfoUChat(contacto);
				infochat.makeVisible();
			}
		});
		
		panel.add(boton4);
		panel.add(Box.createHorizontalStrut(320));
		
		// ===============
		// Buscador (lupa)
		// ===============
		boton5 = new BotonChat("icons/lupa.jpg");
		boton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Buscador buscador = new Buscador();
				buscador.makeVisible();
			}
		});
		
		boton5.makeVisible(false);
		panel.add(boton5);
		
		// =================
		// Opciones de chat
		// =================
		boton6 = new BotonChat("icons/3dots.jpg", 5, 20);
		boton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpcionesChat opciones = new OpcionesChat();
				opciones.makeVisible();
			}
		});
		
		boton6.makeVisible(false);
		panel.add(boton6);
		
		// ====================================
		// Chat de inicio (ninguno al comienzo)
		// ====================================
		panel_2 = new JPanel();
		panel_2.setBounds(297, 55, 558, 419);
		Border blackline2 = BorderFactory.createLineBorder(Color.black);
		panel_2.setBorder(blackline2);
		layeredPane.add(panel_2);
		
		texto_default = new JLabel("Selecciona un chat para empezar a hablar");
		texto_default.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel_2.add(texto_default);
		
		// ==========================
		// Lista de chats del usuario
		// ==========================
		listaChat = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
									ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listaChat.setBounds(10, 55, 277, 463);
		layeredPane.add(listaChat);

		// ===================================
		// Mostrar la lista de chats recientes
		// ===================================
		mostrarChats();
		
		// =================================
		// Barra inferior de la conversación
		// Icono 'enviar' y 'emojis'
		// =================================
		panel_3 = new JPanel();
		panel_3.setBounds(297, 484, 558, 33);
		
		layeredPane.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		boton7 = new BotonChat("icons/emoji.png", 25, 25);
		panel_3.add(boton7);
		panel_3.add(Box.createVerticalStrut(40));
		
		textField = new JTextField();
		panel_3.add(textField);
		textField.setColumns(10);
		
		boton8 = new BotonChat("icons/send.png");
		panel_3.add(boton8);
		
	}
	
	void mostrarChats() {
		// Creamos de nuevo el panel que contiene los chats, de modo que cuando se llame
		// a la función después de agregar un nuevo contacto, no se duplicarán los chats
		// que ya existían antes de agregar al contacto
		panel_1 = new JPanel();
		panel_1.setBounds(10, 55, 277, 452);
		
		Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
		List<Contacto> contactos = ControladorUsuarios.getUnicaInstancia().obtenerContactos(usuarioAct.getLogin());
			
		final LinkedList<BoxChat> chats = new LinkedList<BoxChat>();
		for (int i = 0; i < contactos.size(); i++) {
			BoxChat chat = new BoxChat(contactos.get(i), "ultimo mensaje");
			chats.add(chat);
		}
			
		// Calcular altura dependiendo de los chats que existan
		panel_1.setPreferredSize(new Dimension(277, (75 * (contactos.size() - 1)) + 20));
		listaChat.setViewportView(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
	
		// =================================
		// Lista de Chats
		// =================================
		for (BoxChat chat : chats) {		
			chat.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					reiniciarPaneles(chats);
						
					boton4.setText(chat.getContacto().getNombre());
					boton4.setContacto(chat.getContacto());
					
					// Obtener imagen del usuario del chat
					boton4.changeIcon("icons/profile_picture.png", 20, 20);
						
					boton5.makeVisible(true);
					boton6.makeVisible(true);
						
					chat.setResaltar(true);
						
					panel_2.removeAll();
					panel_2.repaint();
						
					// RELLENAMOS EL CHAT CON LOS MENSAJES DE LA CONVERSACION 
					// TODO Obtener la lista de mensajes
						
					rellenarChat(panel_2, chat.getContacto().getNombre(), usuarioAct,  "Hola ", "Respuesta de prueba");
				}
			});
			panel_1.add(chat);
		}		
	}
	
	void rellenarChat(JPanel panel, String contacto,Usuario user, String ... mensajes) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut);
		// TODO: Obtener lista de mensajes y recorrerla poniendo las burbujas en el panel
		boolean propietario = true;
		for (String mensaje : mensajes) {
			if (propietario) {
				BubbleText burbuja;
				burbuja = new BubbleText(panel, mensaje, Color.GRAY, user.getNombre() + "  ", BubbleText.SENT);
				panel.add(burbuja);
				propietario = false;
			} else {
				BubbleText burbuja;
				burbuja = new BubbleText(panel, mensaje, Color.GREEN, "  " + contacto, BubbleText.RECEIVED);
				panel.add(burbuja);
				propietario = true;
			}
			
		}
	}
	
	void reiniciarPaneles(LinkedList<BoxChat> chats) {
		for (BoxChat boxChat : chats)
			boxChat.setResaltar(false);
	}
	
}
