package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import controlador.ControladorUsuarios;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Mensaje;
import dominio.Usuario;
import gui.elements.BotonChat;
import gui.elements.BoxChat;
import gui.elements.Buscador;
import gui.elements.Estados;
import gui.elements.InfoUChat;
import gui.elements.InfoUsuario;
import gui.elements.MenuOpciones;
import gui.elements.OpcionesChat;
import gui.elements.OpcionesUser;
import tds.BubbleText;

import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.AbstractAction;
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
	private BotonChat boton9;

	private JLabel texto_default;
	private JScrollPane listaChat;
	private JTextField textField;
	private JScrollPane listaMensajes;
	
	private MainView principal = this;

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
						boton1.changeIcon(ControladorUsuarios.getUnicaInstancia().getUsuarioActual().getImagenPerfil(),
								30, 30);
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
		
		MenuOpciones opciones = new MenuOpciones(); 
		boton3.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) { 
	    		// TODO Crear PopupMenu con los emojis que se pueden mandar 
	    		opciones.show(boton3, 10, 40);
	    		opciones.setMainView(principal);
	    	} 
		});
		
		/*
		boton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Obtener número de contactos del usuario antes de que se cierre
				// la ventana para añadir un nuevo contacto
				Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				List<Contacto> contactos = ControladorUsuarios.getUnicaInstancia()
						.obtenerContactos(usuarioAct.getLogin());
				int size = contactos.size();

				OpcionesUser opciones = new OpcionesUser();
				opciones.makeVisible();
				opciones.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						// Obtener número de contactos después de cerrar la ventana para
						// añadir un contacto
						Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
						List<Contacto> contactos = ControladorUsuarios.getUnicaInstancia()
								.obtenerContactos(usuarioAct.getLogin());

						// Si el número de contactos es distinto es porque se agregó uno nuevo
						// Hay que repintar la lista de chats
						if (contactos.size() != size)
							mostrarChats();
					}
				});

			}
		});
		*/
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

		// =================================================================
		// Botón para agregar a un nuevo contacto en caso de que nos haya
		// mandado un mensaje y no lo tengamos en nuestra lista de contactos
		// =================================================================
		boton9 = new BotonChat("", "");
		boton9.setPreferredSize(new Dimension(200, 25));
		boton9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField txtNombre = new JTextField();
				Object[] campos = { "Nombre del contacto:", txtNombre };

				JOptionPane.showConfirmDialog(null, 
											  campos, 
											  "Añadir contacto", 
											  JOptionPane.OK_CANCEL_OPTION);

				String nombre = txtNombre.getText();

				if (nombre.equals("")) {
					JOptionPane.showMessageDialog(null,
												  "El nombre no puede estar vacío",
												  "Se ha producido un error",
												  JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Cambiar nombre del contacto
				boton4.getContacto().setNombre(nombre);
				// Actualizar el botón para que muestre el nombre
				boton4.revalidate();

				Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				ControladorUsuarios.getUnicaInstancia().updateIndividual((ContactoIndividual) boton4.getContacto());
				ControladorUsuarios.getUnicaInstancia().updateUsuario(usuarioAct);
				boton9.makeVisible(false);
				
				// TODO hacer mas ancho el boton9 para que la lupa se quede bien
				// Llamar a mostrarChats() para que se actualice el nombre en la lista de chats
				mostrarChats();
			}
		});
		panel.add(boton9);

		panel.add(Box.createHorizontalStrut(120));

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
		Border blackline2 = BorderFactory.createLineBorder(Color.black);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		listaMensajes = new JScrollPane(panel_2);
		listaMensajes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listaMensajes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listaMensajes.setBorder(blackline2);
		listaMensajes.setBounds(297, 55, 558, 419);
		layeredPane.add(listaMensajes);

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

		JPopupMenu menuEmoji = new JPopupMenu();
		menuEmoji.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		menuEmoji.setBounds(0, 0, 100, 100);
		menuEmoji.setPreferredSize(new Dimension(360, 145));

		for (int i = 0; i < 25; i++) {
			ImageIcon emoji = BubbleText.getEmoji(i);
			Image imageIcon = emoji.getImage();
			imageIcon = imageIcon.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
			ImageIcon emoji2 = new ImageIcon(imageIcon);
			int numEmoji = i;

			menuEmoji.add(new JMenuItem(new AbstractAction("", emoji2) {

				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					if (boton4.getContacto() != null) {
						Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
						Mensaje mensaje = new Mensaje(Integer.toString(numEmoji), usuarioAct, boton4.getContacto());

						Contacto contacto = boton4.getContacto();
						String tlf = "";
						if (contacto instanceof ContactoIndividual) {
							tlf = ((ContactoIndividual) contacto).getTelefono();

							Usuario receptor = ControladorUsuarios.getUnicaInstancia()._buscarUsuario(tlf);

							List<Contacto> con = receptor.getContactos();
							ContactoIndividual ci2 = null;
							for (Contacto cont : con) {
								if (cont instanceof ContactoIndividual) {
									if (((ContactoIndividual) cont).getTelefono().equals(usuarioAct.getTelefono())) {
										ci2 = (ContactoIndividual) cont;
									}
								}
							}
							// Si no lo tiene crear un contacto con el numero de telefono de usuario Act
							if (ci2 == null) {
								ci2 = new ContactoIndividual(usuarioAct.getTelefono(), usuarioAct.getTelefono());
								ControladorUsuarios.getUnicaInstancia().añadirContacto(receptor.getLogin(), ci2);
							}

							// Añadir mensaje a la base de datos
							ControladorUsuarios.getUnicaInstancia().enviarMensaje(mensaje);
							ControladorUsuarios.getUnicaInstancia().recibirMensaje(mensaje, ci2);
						} else if (contacto instanceof Grupo) {
							Grupo grupo = (Grupo) contacto;
							List<ContactoIndividual> miembros = grupo.getMiembros();

							ControladorUsuarios.getUnicaInstancia().enviarMensaje(mensaje);
							for (ContactoIndividual cInd : miembros) {
								ControladorUsuarios.getUnicaInstancia().recibirMensaje(mensaje, cInd);
							}
						}

						// Actualizar las conversaciones
						actualizarChat(contacto, usuarioAct, contacto.getMensajes());
						mostrarChats();
					}
				}
			}));
		}

		boton7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Crear PopupMenu con los emojis que se pueden mandar
				menuEmoji.show(boton7, 0, -150);

			}
		});

		panel_3.add(boton7);
		panel_3.add(Box.createVerticalStrut(40));

		textField = new JTextField();
		panel_3.add(textField);
		textField.setColumns(10);

		// ====================================
		// Botón para enviar un mensaje al chat
		// ====================================
		boton8 = new BotonChat("icons/send.png");
		boton8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String texto = textField.getText();
				textField.setText("");

				Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				Mensaje mensaje = new Mensaje(usuarioAct, boton4.getContacto(), texto);

				Contacto contacto = boton4.getContacto();
				String tlf = "";
				if (contacto instanceof ContactoIndividual) {
					tlf = ((ContactoIndividual) contacto).getTelefono();

					Usuario receptor = ControladorUsuarios.getUnicaInstancia()._buscarUsuario(tlf);

					List<Contacto> con = receptor.getContactos();
					ContactoIndividual ci2 = null;
					for (Contacto cont : con) {
						if (cont instanceof ContactoIndividual) {
							if (((ContactoIndividual) cont).getTelefono().equals(usuarioAct.getTelefono())) {
								ci2 = (ContactoIndividual) cont;
							}
						}
					}
					// Si no lo tiene crear un contacto con el numero de telefono de usuario Act
					if (ci2 == null) {
						ci2 = new ContactoIndividual(usuarioAct.getTelefono(), usuarioAct.getTelefono());
						ControladorUsuarios.getUnicaInstancia().añadirContacto(receptor.getLogin(), ci2);
					}

					// Añadir mensaje a la base de datos
					ControladorUsuarios.getUnicaInstancia().enviarMensaje(mensaje);
					ControladorUsuarios.getUnicaInstancia().recibirMensaje(mensaje, ci2);
				} else if (contacto instanceof Grupo) {
					Grupo grupo = (Grupo) contacto;
					List<ContactoIndividual> miembros = grupo.getMiembros();

					ControladorUsuarios.getUnicaInstancia().enviarMensaje(mensaje);
				}

				// Actualizar las conversaciones
				actualizarChat(contacto, usuarioAct, contacto.getMensajes());
				
				// Mostrar de nuevo la lista de chats para que aparezca el último
				// mensaje enviado junto a la hora de envío
				mostrarChats();

			}
		});
		panel_3.add(boton8);

	}

	public void mostrarChats() {
		// Creamos de nuevo el panel que contiene los chats, de modo que cuando se llame
		// a la función después de agregar un nuevo contacto, no se duplicarán los chats
		// que ya existían antes de agregar al contacto
		panel_1 = new JPanel();
		panel_1.setBounds(0, 55, 277, 452);

		Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
		List<Contacto> contactos = ControladorUsuarios.getUnicaInstancia().obtenerContactos(usuarioAct.getLogin());

		final LinkedList<BoxChat> chats = new LinkedList<BoxChat>();
		for (int i = 0; i < contactos.size(); i++) {
			List<Mensaje> mensajes = contactos.get(i).getMensajes();
			
			String ultMensaje = "";
			String horaUltimoMensaje = "";
			
			if (!mensajes.isEmpty()) {
				Mensaje ultimo = mensajes.get(mensajes.size() - 1);
				if(!ultimo.getEmoticono().equals("")) {
					ultMensaje = "Emoticono";
				} else ultMensaje = ultimo.getTexto();
				horaUltimoMensaje = "[" + mensajes.get(mensajes.size() - 1).getHora().toString() + "]";
			}
			else
				ultMensaje = "Aún no hay mensajes";
			
			BoxChat chat;
			if (contactos.get(i) instanceof ContactoIndividual)
				chat = new BoxChat(contactos.get(i), ultMensaje, horaUltimoMensaje, false);
			else
				chat = new BoxChat(contactos.get(i), ultMensaje, horaUltimoMensaje, true);
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
					if (chat.getContacto() instanceof ContactoIndividual)
						boton4.changeIcon("icons/profile_picture.png", 20, 20);
					else
						boton4.changeIcon("icons/group.png", 20, 20);

					Usuario nuevo = ControladorUsuarios.getUnicaInstancia()._buscarUsuario(boton4.getText());
					if (nuevo != null) {
						boton9.setText("Añadir Contacto");
						boton9.setBorderPainted(true);
						boton9.setBorderPainted(true);
						boton9.setOpaque(true);
						boton9.setContentAreaFilled(true);
						boton9.setBackground(Color.PINK);
					}

					boton5.makeVisible(true);
					boton6.makeVisible(true);

					panel_2.removeAll();
					panel_2.revalidate();
					panel_2.repaint();

					chat.setResaltar(true);
					// Rellenamos el chat con los mensajes del contacto
					List<Mensaje> mensajes = chat.getContacto().getMensajes();

					rellenarChat(panel_2, chat.getContacto().getNombre(), usuarioAct, mensajes);

				}
			});
			
			
			if (boton4.getContacto() != null) {
				for (BoxChat chat2 : chats) {
					if(chat2.getContacto().equals(boton4.getContacto())) {
						chat2.setResaltar(true);
					}
				}
			}
			panel_1.add(chat);
		}
	}

	void rellenarChat(JPanel panel, String contacto, Usuario user, List<Mensaje> mensajes) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut);
		for (Mensaje mensaje : mensajes) {

			if (mensaje.getEmisor().equals(user)) {
				BubbleText burbuja;
				if (mensaje.getEmoticono().equals(""))
					burbuja = new BubbleText(panel, mensaje.getTexto(), Color.GRAY, user.getNombre() + "  ",
							BubbleText.SENT);
				else
					burbuja = new BubbleText(panel, Integer.parseInt(mensaje.getEmoticono()), Color.GRAY,
							user.getNombre() + "  ", BubbleText.SENT, 12);
				burbuja.setPreferredSize(new Dimension(540, 419));
				panel.add(burbuja);
			} else {
				BubbleText burbuja;
				if (mensaje.getEmoticono().equals(""))
					burbuja = new BubbleText(panel, mensaje.getTexto(), Color.GREEN, "  " + mensaje.getEmisor().getNombre(),
							BubbleText.RECEIVED);
				else
					burbuja = new BubbleText(panel, Integer.parseInt(mensaje.getEmoticono()), Color.GREEN,
							" " + mensaje.getEmisor().getNombre(), BubbleText.RECEIVED, 12);
				burbuja.setPreferredSize(new Dimension(540, 419));
				panel.add(burbuja);
			}

		}
	}

	void reiniciarPaneles(LinkedList<BoxChat> chats) {
		for (BoxChat boxChat : chats)
			boxChat.setResaltar(false);
	}

	void actualizarChat(Contacto contacto, Usuario user, List<Mensaje> mensajes) {
		panel_2.removeAll();
		panel_2.revalidate();
		panel_2.repaint();
		rellenarChat(panel_2, contacto.getNombre(), user, mensajes);
	}

}
