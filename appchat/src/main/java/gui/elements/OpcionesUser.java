package gui.elements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

public class OpcionesUser extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public OpcionesUser() {
		
		super();
		setTitle("Opciones");
		setBounds(150, 150, 230, 387);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		final JPanel contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setContentPane(contentPane);
		
		// ======================
		// Botón 'Crear contacto'
		// ======================
		JButton boton1 = new JButton("Añadir contacto");
		boton1.setPreferredSize(new Dimension(430, 50));
		boton1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
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
				
				boolean registrado = false;
				registrado = ControladorUsuarios.getUnicaInstancia().añadirContacto(uAct.getLogin(), ci);
				
				if (registrado)
					JOptionPane.showMessageDialog(null, 
												  "El contacto se ha añadido correctamente",
												  "Contacto añadido", 
												  JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, 
												  "Ya existe un contacto con el mismo nombre y teléfono", 
												  "Se ha producido un error", 
												  JOptionPane.ERROR_MESSAGE);
				
			}
		});
		
		contentPane.add(boton1, BorderLayout.CENTER);
		
		// =========================
		// Botón 'Mostrar contactos'
		// =========================
		JButton boton2 = new JButton("Mostrar contactos");
		boton2.setPreferredSize(new Dimension(430, 50));
		boton2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
				// Obtenemos una instancia del usuario actual
				Usuario uAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				// Obtenemos la lista de contactos del usuario actual
				List<Contacto> contactos 
					= ControladorUsuarios.getUnicaInstancia().obtenerContactos(uAct.getLogin());
				
				if (contactos.size() == 0) {

					JOptionPane.showMessageDialog(null,
												  "Tu lista de contactos está vacía",
												  "Se ha producido un error",
												  JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Obtenemos un objeto de tipo String[][] con los campos de cada contacto
				// que se deben mostrar en la tabla
				Object[][] contactosTabla 	= ControladorUsuarios.getUnicaInstancia().contactosATabla(contactos);
				String[] nombresColumnas 	= {"Nombre", "Imagen", "Teléfono"}; 
				
				JFrame ventanaTabla = new JFrame();
				ventanaTabla.setTitle("Lista de contactos");    
				ventanaTabla.setBounds(100, 100, 375, (contactos.size() * 60) + 52);
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
		});
		
		contentPane.add(boton2, BorderLayout.CENTER);

		// ===================
		// Botón 'Crear grupo'
		// ===================
		JButton boton3 = new JButton("Crear grupo");
		boton3.setPreferredSize(new Dimension(430, 50));
		boton3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
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
						
						// Crear objeto con el contacto
						Grupo g = new Grupo(textField.getText());
						
						// Añadir contactos
						for(Contacto contacto : contactos) {
							if(contactosGrupo.contains(contacto.getNombre())) {
								if(contacto instanceof ContactoIndividual) {
									g.addMiembro((ContactoIndividual) contacto);
								}
							}
						}
						
						Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
						
						boolean registrado = false;
						registrado = ControladorUsuarios.getUnicaInstancia().añadirContacto(usuarioAct.getLogin(), g);
						
						if (registrado)
							JOptionPane.showMessageDialog(null, 
														  "El contacto se ha añadido correctamente",
														  "Contacto añadido", 
														  JOptionPane.INFORMATION_MESSAGE);
						else
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
		});
		
		contentPane.add(boton3, BorderLayout.CENTER);
		
		// =======================
		// Botón 'Modificar grupo'
		// =======================
		JButton boton4 = new JButton("Modificar grupo");
		boton4.setPreferredSize(new Dimension(430, 50));
		boton4.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				
			}
		});

		contentPane.add(boton4, BorderLayout.CENTER);
		
		// ========================
		// Botón 'Ver estadísticas'
		// ========================
		JButton boton5 = new JButton("Ver estadísticas");
		boton5.setPreferredSize(new Dimension(430, 50));
		boton5.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				
			}
		});

		contentPane.add(boton5, BorderLayout.CENTER);
		
		// =======================
		// Botón 'Hacerse premium'
		// =======================
		JButton boton6 = new JButton("Hacerse premium");
		boton6.setPreferredSize(new Dimension(430, 50));
		boton6.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				
			}
		});

		contentPane.add(boton6, BorderLayout.CENTER);
		
		// =====================
		// Botón 'Cerrar sesión'
		// =====================
		JButton boton7 = new JButton("Cerrar sesión");
		boton7.setPreferredSize(new Dimension(430, 50));
		boton7.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		contentPane.add(boton7, BorderLayout.CENTER);
		
	}
	
	public void makeVisible() 	{ setVisible(true); }
	public void makeInvisible() { setVisible(false); }

}
