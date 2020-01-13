package gui.elements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import controlador.ControladorUsuarios;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Usuario;
import graficos.HistogramaMensajes;
import graficos.PieChartGrupos;
import gui.MainView;

public class MenuOpciones extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	
	MainView principal;
	
	@SuppressWarnings("serial")
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
				ventanaTabla.setBounds(100, 100, 500, 500);
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
				
				// Obtener lista de nombres de contactos
				DefaultListModel<String> listmodel = ControladorUsuarios.getUnicaInstancia().obtenerNombreContactos();
				
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
						// Crear el grupo
						
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
						ControladorUsuarios.getUnicaInstancia().addContactosGrupo(contactosGrupo, g);
						
						Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
						
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
				grupo = ControladorUsuarios.getUnicaInstancia().comprobarGrupo(txtNombre.getText());
				
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
				
				ControladorUsuarios.getUnicaInstancia().actualizarDatosGrupo(contEnGrupo, contactosGrupo, listmodel);
				
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
						
						ControladorUsuarios.getUnicaInstancia().modificarGrupo(grupoaux, anadidos, eliminados);
						
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
				// Ventana de estadisticas
				Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				if(usuarioAct.getPremium()) {
					JFrame estadisticas = new JFrame();
					estadisticas.setTitle("Estadisticas");
					estadisticas.setBounds(400, 200, 1300, 700);
					estadisticas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					estadisticas.setResizable(false);
					estadisticas.setVisible(true);
					estadisticas.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
					
					// Estadistica en forma de histograma
					HistogramaMensajes menEstadisticas = new HistogramaMensajes();
					
					// Obtener numero de mensajes del usuario
					Usuario usuarioAct2 = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
					
					Integer[] valores = usuarioAct2.getNumeroDeMensajesEnMeses();
					menEstadisticas.addElemento(valores);
					
					CategoryChart histogram = menEstadisticas.getChart();
					try {
						BitmapEncoder.saveBitmap(histogram, "icons/histogram.png", BitmapFormat.PNG);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					ImageIcon chartFoto2 = new ImageIcon("icons/histogram.png");
					Image imageIcon2 = chartFoto2.getImage();
					Image newImage2 = imageIcon2.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH);
					
					JLabel chart2 = new JLabel(new ImageIcon(newImage2));
					estadisticas.add(chart2);
					
					// Estadistica en forma de tarta
					PieChartGrupos grupoEstadistico = new PieChartGrupos();
					usuarioAct2.getGruposMasActivos(grupoEstadistico);
					
					PieChart grupoChart = grupoEstadistico.getChart();
					try {
						BitmapEncoder.saveBitmap(grupoChart, "icons/piechart.png", BitmapFormat.PNG);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					// Mostrar la estadistica de grupos
					ImageIcon chartFoto = new ImageIcon("icons/piechart.png");
					Image imageIcon = chartFoto.getImage();
					Image newImage = imageIcon.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH);
					
					JLabel chart1 = new JLabel(new ImageIcon(newImage));
					estadisticas.add(chart1);
					
					// Botones para exportar las imagenes 
					JButton exportar1 = new JButton("Exportar Histograma");
					JButton exportar2 = new JButton("Exportar PieChart");
					exportar1.setPreferredSize(new Dimension(600,30));
					exportar2.setPreferredSize(new Dimension(600,30));
					estadisticas.add(exportar1);
					estadisticas.add(exportar2);
					
					exportar1.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

							int returnValue = jfc.showOpenDialog(null);

							File selectedFile = null;
							
							if (returnValue == JFileChooser.APPROVE_OPTION)
								selectedFile = jfc.getSelectedFile();
							else if (returnValue == JFileChooser.CANCEL_OPTION
									|| returnValue == JFileChooser.ERROR_OPTION)
								return;
							
							String filePath = selectedFile.getAbsolutePath();
							if(!filePath.endsWith(".png"))
								selectedFile = new File(filePath + ".png");
							
							// Exportar el grafico
							CategoryChart histogram = menEstadisticas.getChart();
							try {
								BitmapEncoder.saveBitmap(histogram, filePath, BitmapFormat.PNG);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					});
					
					exportar2.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

							int returnValue = jfc.showOpenDialog(null);

							File selectedFile = null;
							
							if (returnValue == JFileChooser.APPROVE_OPTION)
								selectedFile = jfc.getSelectedFile();
							else if (returnValue == JFileChooser.CANCEL_OPTION
									|| returnValue == JFileChooser.ERROR_OPTION)
								return;
							
							String filePath = selectedFile.getAbsolutePath();
							if(!filePath.endsWith(".png"))
								selectedFile = new File(filePath + ".png");
							
							// Exportar el grafico
							PieChart grupoChart = grupoEstadistico.getChart();
							try {
								BitmapEncoder.saveBitmap(grupoChart, filePath, BitmapFormat.PNG);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					});
					
				} else {
					JOptionPane.showMessageDialog(null, 
							  "Esta operacion solo esta disponible si eres usuario premium", 
							  "Error", 
							  JOptionPane.ERROR_MESSAGE);
				}
			}
		}));
		
		add(new JMenuItem(new AbstractAction("Hacerse Premium") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ventana premium
				JFrame premium = new JFrame();
				premium.setTitle("¡Premium!");
				premium.setBounds(400, 200, 380, 300);
				premium.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				premium.setResizable(false);
				premium.setVisible(true);
				premium.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
				
				JLabel intro = new JLabel("Hazte premium solo con un click!!");
				premium.add(intro);
				
				JButton aceptar = new JButton("Convertirse en premium");
				JButton cancelar = new JButton("Cancelar Premium");
				
				JButton salir = new JButton("Volver");
				salir.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						premium.dispose();
					}
				});
				
				Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				if (usuarioAct.getPremium()) {
					cancelar.setPreferredSize(new Dimension(200, 30));
					premium.add(cancelar);
					cancelar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// Modificar el usuario para que no sea premium
							usuarioAct.setPremium(false);
							if(ControladorUsuarios.getUnicaInstancia().updateUsuario(usuarioAct)) {
								// Cerrar la ventana
								premium.dispose();
								// Mostrar ventana de proceso terminado
								JOptionPane.showMessageDialog(null, 
										  "A partir de ahora ya no serás premium.",
										  "Gracias!", 
										  JOptionPane.INFORMATION_MESSAGE);
							} else {
								// Cerrar la ventana
								premium.dispose();
								// Mostrar ventana de error
								JOptionPane.showMessageDialog(null, 
										  "Se ha producido un error", 
										  "Error", 
										  JOptionPane.ERROR_MESSAGE);
							}
						}
					});
					
					JLabel opPremium = new JLabel("Operaciones con premium:");
					premium.add(opPremium);
					//Operacion Contactos -> PDF
					JButton pdf = new JButton("Exportar contactos a pdf");
					pdf.setPreferredSize(new Dimension(200, 30));
					pdf.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// Empezar proceso de generacion de pdf
							// Pregutar donde almacenar el archivo pdf
							JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

							int returnValue = jfc.showOpenDialog(null);

							File selectedFile = null;
							
							if (returnValue == JFileChooser.APPROVE_OPTION)
								selectedFile = jfc.getSelectedFile();
							else if (returnValue == JFileChooser.CANCEL_OPTION
									|| returnValue == JFileChooser.ERROR_OPTION)
								return;
							
							String filePath = selectedFile.getAbsolutePath();
							if(!filePath.endsWith(".pdf"))
								selectedFile = new File(filePath + ".pdf");
							
							// Crear archivo pdf
							try {
								FileOutputStream archivo = new FileOutputStream(selectedFile.getAbsoluteFile());
								Document documento = new Document();
								PdfWriter.getInstance(documento, archivo);
								documento.open();
								documento.add(new Paragraph("LISTA DE CONTACTOS"));
								documento.add(new Paragraph("___________________"));
								
								// Obtener todos los contactos del usuario actual
								ControladorUsuarios.getUnicaInstancia().crearDocumento(documento);
								documento.close();
								
								// Cerrar la ventana
								premium.dispose();
								// Mostrar ventana de proceso terminado
								JOptionPane.showMessageDialog(null, 
										  "El pdf ha sido creado",
										  "Proceso terminado", 
										  JOptionPane.INFORMATION_MESSAGE);
							} catch (Exception e1) {
								e1.printStackTrace();
							}	
						}
					});
					premium.add(pdf);
					
					salir.setPreferredSize(new Dimension(200, 30));
					premium.add(salir);
					
				} else {
					premium.add(aceptar);
					
					// Añadir descuento disponible
					double descuento = usuarioAct.getDescuento();
					JLabel precio = new JLabel("Precio: 10€/mes " + ", con un descuento de: " + descuento + "€.");
					premium.add(precio);
					int precioFinal = (int) (10 - descuento);
					JLabel pfinal = new JLabel("Precio final: " + precioFinal + "€.");
					Font font = pfinal.getFont();
					pfinal.setFont(new Font(font.getFontName(), Font.BOLD, 25));
					premium.add(pfinal);
					
					salir.setPreferredSize(new Dimension(150, 30));
					
					aceptar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// Modificar el usuario para que sea premium
							usuarioAct.setPremium(true);
							if(ControladorUsuarios.getUnicaInstancia().updateUsuario(usuarioAct)) {
								// Cerrar la ventana
								premium.dispose();
								// Mostrar ventana de proceso terminado
								JOptionPane.showMessageDialog(null, 
										  "Ya eres premium!! Gracias por apoyar la aplicación.",
										  "Gracias!", 
										  JOptionPane.INFORMATION_MESSAGE);
							} else {
								// Cerrar la ventana
								premium.dispose();
								// Mostrar ventana de error
								JOptionPane.showMessageDialog(null, 
										  "Se ha producido un error", 
										  "Error", 
										  JOptionPane.ERROR_MESSAGE);
							}
							
						}
					});
					premium.add(salir);
				}
				
				
				
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
