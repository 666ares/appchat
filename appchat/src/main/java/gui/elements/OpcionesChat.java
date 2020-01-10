package gui.elements;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import controlador.ControladorUsuarios;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Mensaje;
import dominio.Usuario;
import gui.MainView;

public class OpcionesChat extends JPopupMenu{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainView principal;
	
	public OpcionesChat() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		setBounds(350, 150, 130, 70);
		setPreferredSize(new Dimension(130, 50));
		
		add(new JMenuItem(new AbstractAction("Eliminar Mensajes") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Mostrar mensaje para confirmar la eliminacion de los mensajes
				JTextField txtNombre = new JTextField();
				
				Object[] campos = {
						"Introduce la palabra 'borrar' para confirmar:", txtNombre
				};
				
				JOptionPane.showConfirmDialog(null, 
											  campos, 
											  "Eliminar mensajes", 
											  JOptionPane.OK_CANCEL_OPTION);
				
				String nombre = txtNombre.getText();
				if(!nombre.equals("borrar"))
				{
					JOptionPane.showMessageDialog(null, 
												  "La confirmación no ha salido con exito", 
												  "Se ha producido un error", 
												  JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//Eliminar los mensajes del contacto actual
				Contacto contacto = principal.getContactoActivo();
				Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				
				String tlf = "";
				if(contacto instanceof ContactoIndividual) {
					tlf = ((ContactoIndividual) contacto).getTelefono();
				}
				
				//	Eliminar los mensajes del contacto del usuario
				for(Contacto con : usuarioAct.getContactos()) {
					if(con instanceof ContactoIndividual && ((ContactoIndividual) con).getTelefono().equals(tlf)) {
						con.resetearChat();
						contacto = con;
					}	
				}
				
				if(contacto instanceof ContactoIndividual) {
					ControladorUsuarios.getUnicaInstancia().updateIndividual((ContactoIndividual)contacto);
				}
				ControladorUsuarios.getUnicaInstancia().updateUsuario(usuarioAct);
				
				// TODO actualizar pantalla principal
			}
		}));
		
		add(new JMenuItem(new AbstractAction("Eliminar Contacto") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Mostrar mensaje para confirmar la eliminacion del contacto
				JTextField txtNombre = new JTextField();
				
				Object[] campos = {"Nombre del contacto a eliminar:", txtNombre};
				
				JOptionPane.showConfirmDialog(null, 
											  campos, 
											  "Eliminar contacto", 
											  JOptionPane.OK_CANCEL_OPTION);
				
				String nombre = txtNombre.getText();
				if (!nombre.equals(principal.getContactoActivo().getNombre()))
				{
					JOptionPane.showMessageDialog(null, 
												  "El nombre no coincide con el contacto actual", 
												  "Se ha producido un error", 
												  JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Borrar el contacto
				Contacto contacto = principal.getContactoActivo();
				Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				
				boolean borrar 
					= ControladorUsuarios.getUnicaInstancia().eliminarContacto(usuarioAct.getLogin(),
																			   contacto);
				
				if (borrar) {
					
					JOptionPane.showMessageDialog(null, 
							  					  "El contacto se ha eliminado correctamente",
							  					  "Contacto eliminado", 
							  					  JOptionPane.INFORMATION_MESSAGE);
					principal.vaciarChat();
					principal.mostrarChats();
				} 
				else
					JOptionPane.showMessageDialog(null, 
							  					  "No se ha podido eliminar al contacto", 
							  					  "Se ha producido un error", 
							  					  JOptionPane.ERROR_MESSAGE);
				
			}
		}));
	}
	
	public void setPrincipal(MainView nPrincipal)
	{
		this.principal = nPrincipal;
	}
}
