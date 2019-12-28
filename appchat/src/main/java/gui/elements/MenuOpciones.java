package gui.elements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controlador.ControladorUsuarios;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Usuario;
import gui.MainView;

public class MenuOpciones extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	
	MainView principal;
	
	public MenuOpciones() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		setBounds(150, 150, 135, 155);
		setPreferredSize(new Dimension(135, 155));
		
		add(new JMenuItem(new AbstractAction("Añadir Contacto") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Obtener nombre y teléfono del contacto que se quiere agregar
				JTextField txtNombre = new JTextField();
				JTextField txtTelefono = new JTextField();
				
				Object[] campos = {
				    "Nombre del contacto:", txtNombre,
				    "Número de teléfono: ", txtTelefono
				};
				
				JOptionPane.showConfirmDialog(null,
											  campos,
											  "Añadir contacto", 
											  JOptionPane.OK_CANCEL_OPTION);
				
				String nombre = txtNombre.getText();
				String telefono = txtTelefono.getText();
				
				if (nombre.equals("") || telefono.equals("")) {
					JOptionPane.showMessageDialog(null,
												  "El nombre o teléfono no pueden estar vacíos",
												  "Se ha producido un error",
												  JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Crear objeto con el contacto
				ContactoIndividual ci = new ContactoIndividual(nombre, telefono);
				// Obtener el usuario que está intentando registrar un contacto
				Usuario uAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				// Obtener el usuario asociada al teléfono del contacto que se quiere añadir
				Usuario nuevo = ControladorUsuarios.getUnicaInstancia()._buscarUsuario(telefono);
				
				// Comprobar que el usuario asociado al teléfono existe!!
				if (nuevo == null) {
					JOptionPane.showMessageDialog(null, 
												  "El usuario asociado a ese teléfono no existe",
												  "Se ha producido un error", 
												  JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Asociar el contacto con el usuario correspondiente
				ci.setUsuario(nuevo);
				
				//Registrar contacto nuevo a nuevo
				
				boolean registrado = false;
				registrado = ControladorUsuarios.getUnicaInstancia().añadirContacto(uAct.getLogin(), ci);
				
				if (registrado) {
					JOptionPane.showMessageDialog(null, 
												  "El contacto se ha añadido correctamente",
												  "Contacto añadido", 
												  JOptionPane.INFORMATION_MESSAGE);
					principal.mostrarChats();
				} else
					JOptionPane.showMessageDialog(null, 
												  "Ya existe un contacto con el mismo nombre y teléfono", 
												  "Se ha producido un error", 
												  JOptionPane.ERROR_MESSAGE);
			}
		}));
		
		add(new JMenuItem(new AbstractAction("Mostrar Contactos") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Obtenemos una instancia del usuario actual
				Usuario uAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				// Obtenemos la lista de contactos del usuario actual
				List<Contacto> contactos 
					= ControladorUsuarios.getUnicaInstancia().obtenerContactos(uAct.getLogin());
				
				List<ContactoIndividual> individuales
					= ControladorUsuarios.getUnicaInstancia().obtenerIndividuales(contactos);
				
				if (individuales.size() == 0) {

					JOptionPane.showMessageDialog(null,
												  "Tu lista de contactos está vacía",
												  "Se ha producido un error",
												  JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Obtenemos un objeto de tipo String[][] con los campos de cada contacto
				// que se deben mostrar en la tabla
				Object[][] contactosTabla 	= ControladorUsuarios.getUnicaInstancia().contactosATabla(contactos);
				String[] nombresColumnas 	= {"Nombre", "Imagen", "Teléfono", "Grupos en común"}; 
				
				JFrame ventanaTabla = new JFrame();
				ventanaTabla.setTitle("Lista de contactos");    
				ventanaTabla.setBounds(100, 100, 500, (contactos.size() * 40));
				ventanaTabla.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				ventanaTabla.setVisible(true);
				ventanaTabla.setResizable(false);
					
				DefaultTableModel model = new DefaultTableModel(contactosTabla, nombresColumnas) {
					private static final long serialVersionUID = 1L;
					
				    @Override
				    public Class<?> getColumnClass(int column) {
				        switch (column) {
				            case 0: return String.class;
				            case 1: return ImageIcon.class;
				            case 2: return String.class;
				            case 3: return String.class;
				            default: return Object.class;
				        }
				    }
				};
				
				JTable tabla = new JTable(model) {
					// Evitar que las celdas de la tabla se puedan editar...
			        private static final long serialVersionUID = 1L;

			        public boolean isCellEditable(int row, int column) {                
			                return false;               
			        };
			    };
			    
			    // Establecer anchura de cada columna
			    TableColumn columnaNombre = tabla.getColumnModel().getColumn(0);
			    columnaNombre.setMinWidth(200);
			    columnaNombre.setMaxWidth(200);
			    columnaNombre.setPreferredWidth(200);
	            
			    TableColumn columnaFoto = tabla.getColumnModel().getColumn(1);
			    columnaFoto.setMinWidth(60);
	            columnaFoto.setMaxWidth(60);
	            columnaFoto.setPreferredWidth(60);
	            
	            TableColumn columnaTlf = tabla.getColumnModel().getColumn(2);
	            columnaTlf.setMinWidth(80);
	            columnaTlf.setMaxWidth(80);
	            columnaTlf.setPreferredWidth(80);
	            
	            // Establecer altura de cada fila
	            // La altura de una fila será igual a la altura
	            // de la imagen de perfil del usuario
	            tabla.setRowHeight(60);
				
				JScrollPane sp = new JScrollPane(tabla);
				ventanaTabla.add(sp, BorderLayout.CENTER);
				
			}
		}));
		
		add(new JMenuItem(new AbstractAction("Crear Grupo") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame crearGrupo = new JFrame();
				crearGrupo.setTitle("Crear Grupo");
				crearGrupo.setBounds(150, 150, 400, 380);
				crearGrupo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				crearGrupo.setResizable(false);
				crearGrupo.setVisible(true);
				
				JPanel contentPane = new JPanel();
				contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
				crearGrupo.setContentPane(contentPane);
				
				JLabel texto1 = new JLabel("Nombre del grupo:  ");
				contentPane.add(texto1);
				
				JTextField textField = new JTextField();
				textField.setColumns(21);
				contentPane.add(textField);
				
				List<Contacto> contactos = ControladorUsuarios.getUnicaInstancia().getUsuarioActual().getContactos();
				DefaultListModel<String> listmodel = new DefaultListModel<String>();
				for(Contacto contacto : contactos) {
					if(contacto instanceof ContactoIndividual) 
						listmodel.addElement(contacto.getNombre());
				}
				
				
				JList<String> jcontactos = new JList<String>(listmodel);
				jcontactos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				jcontactos.setLayoutOrientation(JList.VERTICAL);
				jcontactos.setVisibleRowCount(-1);
				JScrollPane listaContactos = new JScrollPane(jcontactos);
				listaContactos.setPreferredSize(new Dimension(138,250));
				
				DefaultListModel<String> contactosGrupo = new DefaultListModel<String>();
				JList<String> contGrupo = new JList<String>(contactosGrupo);
				contGrupo.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				contGrupo.setLayoutOrientation(JList.VERTICAL);
				contGrupo.setVisibleRowCount(-1);
				JScrollPane contactosEnGrupo = new JScrollPane(contGrupo);
				contactosEnGrupo.setPreferredSize(new Dimension(138,250));
				
				BotonChat addContactoGrupo = new BotonChat("icons/send.png", 30, 30);
				addContactoGrupo.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String contacto = jcontactos.getSelectedValue();
						listmodel.removeElement(contacto);
						contactosGrupo.addElement(contacto);
					}
				});
				
				BotonChat removeContactoGrupo = new BotonChat("icons/send2.png", 30, 30);
				removeContactoGrupo.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String contacto = contGrupo.getSelectedValue();
						contactosGrupo.removeElement(contacto);
						listmodel.addElement(contacto);
					}
				});
				
				JPanel botones = new JPanel();
				botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
				botones.setBounds(0, 0, 60, 30);
				botones.add(addContactoGrupo);
				botones.add(removeContactoGrupo);
				
				JButton boton1 = new JButton("Crear Grupo");
				boton1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Crear el grupo
						
						// Comprobar que nombre no es vacio
						if (textField.getText().equals("")) {
							JOptionPane.showMessageDialog(null, 
														  "El grupo no tiene nombre",
														  "Se ha producido un error", 
														  JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						// Comprobar que el grupo tiene al menos 2 usuarios
						// (sin contar el usuario que lo está creando (admin)).
						if (contGrupo.getModel().getSize() < 2) {
							JOptionPane.showMessageDialog(null,
														  "El grupo debe tener al menos 2 usuarios",
														  "Se ha producido un error",
														  JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						// Crear objeto con el contacto
						Grupo g = new Grupo(textField.getText());
						
						// Añadir contactos
						for (Contacto contacto : contactos) {
							if (contactosGrupo.contains(contacto.getNombre())) {
								if (contacto instanceof ContactoIndividual) {
									ContactoIndividual cInd = (ContactoIndividual) contacto;
									g.addMiembro(cInd);
								}
							}
						}
						
						Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
						ContactoIndividual yo = new ContactoIndividual(usuarioAct.getNombre(), usuarioAct.getTelefono());
						g.addMiembro(yo);
						
						boolean registrado = false;
						registrado = ControladorUsuarios.getUnicaInstancia().añadirContacto(usuarioAct.getLogin(), g);
						
						// Crear el grupo en los miembros del grupo
						List<ContactoIndividual> miembros = g.getMiembros();
						for(ContactoIndividual miembro : miembros) {
							String tlf = miembro.getTelefono();
							Usuario user = ControladorUsuarios.getUnicaInstancia()._buscarUsuario(tlf);
							ControladorUsuarios.getUnicaInstancia().añadirContacto(user.getLogin(), g);
						}
						
						if (registrado) {
							JOptionPane.showMessageDialog(null, 
														  "El contacto se ha añadido correctamente",
														  "Contacto añadido", 
														  JOptionPane.INFORMATION_MESSAGE);
							principal.mostrarChats();
						} else
							JOptionPane.showMessageDialog(null, 
														  "Error al crear el grupo", 
														  "Se ha producido un error", 
														  JOptionPane.ERROR_MESSAGE);
						
						crearGrupo.dispose();
					}
				});
				
				contentPane.add(listaContactos);
				contentPane.add(botones);
				contentPane.add(contactosEnGrupo);
				contentPane.add(Box.createHorizontalStrut(100));
				contentPane.add(boton1);
				
			}
		}));
		
		add(new JMenuItem(new AbstractAction("Modificar Grupo") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField txtNombre = new JTextField();
				Object[] campos = {
					    "Nombre del grupo:", txtNombre
				};
				
				JOptionPane.showConfirmDialog(null,
						  campos,
						  "Modificar grupo", 
						  JOptionPane.OK_CANCEL_OPTION);
				
				if(txtNombre.getText().equals("")) {
					JOptionPane.showMessageDialog(null, 
							  "Error, no existe el grupo", 
							  "Se ha producido un error", 
							  JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//Comprobar que el grupo existe

				Grupo grupo = null;

				List<Contacto> contactos = ControladorUsuarios.getUnicaInstancia().getUsuarioActual().getContactos();	
				
				for(Contacto contacto : contactos) {
					if(contacto instanceof Grupo) {
						Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
						if(contacto.getNombre().equals(txtNombre.getText()) && usuarioAct.equals(((Grupo) contacto).getAdmin())) {
							grupo = (Grupo) contacto;
						}
					}	
				}
				
				if(grupo == null) {
					JOptionPane.showMessageDialog(null, 
							  "Error, no existe el grupo " + txtNombre.getText() + " o no eres admin", 
							  "Se ha producido un error", 
							  JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				final Grupo grupoaux = grupo;
				
				JFrame crearGrupo = new JFrame();
				crearGrupo.setTitle("Modificar Grupo");
				crearGrupo.setBounds(150, 150, 400, 350);
				crearGrupo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				crearGrupo.setResizable(false);
				crearGrupo.setVisible(true);
				
				JPanel contentPane = new JPanel();
				contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
				crearGrupo.setContentPane(contentPane);

				DefaultListModel<String> listmodel = new DefaultListModel<String>();
				DefaultListModel<String> contactosGrupo = new DefaultListModel<String>();
				DefaultListModel<String> anadidos = new DefaultListModel<String>();
				DefaultListModel<String> eliminados = new DefaultListModel<String>();
				List<ContactoIndividual> contEnGrupo = grupo.getMiembros();
				
				for(Contacto contacto : contactos) {
					if(contacto instanceof ContactoIndividual) {
						if(contEnGrupo.contains(contacto)) {
							contactosGrupo.addElement(contacto.getNombre());
						} else {
							listmodel.addElement(contacto.getNombre());
						}
					}
				}
				
				JList<String> jcontactos = new JList<String>(listmodel);
				jcontactos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				jcontactos.setLayoutOrientation(JList.VERTICAL);
				jcontactos.setVisibleRowCount(-1);
				JScrollPane listaContactos = new JScrollPane(jcontactos);
				listaContactos.setPreferredSize(new Dimension(138,250));
				
				
				JList<String> contGrupo = new JList<String>(contactosGrupo);
				contGrupo.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				contGrupo.setLayoutOrientation(JList.VERTICAL);
				contGrupo.setVisibleRowCount(-1);
				JScrollPane contactosEnGrupo = new JScrollPane(contGrupo);
				contactosEnGrupo.setPreferredSize(new Dimension(138,250));
				
				// Añadir contacto
				BotonChat addContactoGrupo = new BotonChat("icons/send.png", 30, 30);
				addContactoGrupo.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String contacto = jcontactos.getSelectedValue();
						listmodel.removeElement(contacto);
						contactosGrupo.addElement(contacto);
						anadidos.addElement(contacto);
					}
				});
				
				// Eliminar contacto
				BotonChat removeContactoGrupo = new BotonChat("icons/send2.png", 30, 30);
				removeContactoGrupo.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String contacto = contGrupo.getSelectedValue();
						contactosGrupo.removeElement(contacto);
						listmodel.addElement(contacto);
						eliminados.addElement(contacto);
					}
				});
				
				JPanel botones = new JPanel();
				botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
				botones.setBounds(0, 0, 60, 30);
				botones.add(addContactoGrupo);
				botones.add(removeContactoGrupo);
				
				JButton boton1 = new JButton("Modificar Grupo");
				boton1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// Si las dos listas son vacias -> no hay cambios
						if(anadidos.isEmpty() && eliminados.isEmpty()) {
							JOptionPane.showMessageDialog(null, 
									  "No hay cambios",
									  "Modificar Grupo", 
									  JOptionPane.INFORMATION_MESSAGE);
						}
						
						for(Contacto contacto : contactos) {
							if (anadidos.contains(contacto.getNombre())) {
								if (contacto instanceof ContactoIndividual) {
									// TODO Cuando se añade un contacto que no estaba antes, 
									// añadirle el grupo como contacto nuevo
									grupoaux.addMiembro((ContactoIndividual) contacto);
								}
							} else if (eliminados.contains(contacto.getNombre())) {
								if (contacto instanceof ContactoIndividual) {
									// TODO Cuando se elimina un contacto, 
									// eliminarle el grupo que tenia en la lista contactos
									grupoaux.removeMiembro((ContactoIndividual) contacto);
								}
							}
						}
						
						//Modificar el grupo
						Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
						ControladorUsuarios.getUnicaInstancia().updateGrupo(grupoaux);
						ControladorUsuarios.getUnicaInstancia().updateUsuario(usuarioAct);
						
						crearGrupo.dispose();
					}
				});
				
				contentPane.add(listaContactos);
				contentPane.add(botones);
				contentPane.add(contactosEnGrupo);
				contentPane.add(Box.createHorizontalStrut(90));
				contentPane.add(boton1);
				
			}
		}));
		
		add(new JMenuItem(new AbstractAction("Ver Estadísticas") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		}));
		
		add(new JMenuItem(new AbstractAction("Hacerse Premium") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		}));
		
		add(new JMenuItem(new AbstractAction("Cerrar Sesión") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}));
	}
	
	public void setMainView(MainView principal)
	{
		this.principal = principal;
	}
	
}
