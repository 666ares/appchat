package gui.elements;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import controlador.ControladorUsuarios;
import dominio.Contacto;
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
		setPreferredSize(new Dimension(130, 70));
		
		add(new JMenuItem(new AbstractAction("Eliminar Mensajes") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
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
				
				
			}
		}));
		
		add(new JMenuItem(new AbstractAction("Eliminar Contacto") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//Mostrar mensaje para confirmar la eliminacion del contacto
				JTextField txtNombre = new JTextField();
				
				Object[] campos = {
						"Nombre del contacto a eliminar:", txtNombre
				};
				
				JOptionPane.showConfirmDialog(null, 
											  campos, 
											  "Eliminar contacto", 
											  JOptionPane.OK_CANCEL_OPTION);
				
				String nombre = txtNombre.getText();
				if(!nombre.equals(principal.getContactoActivo().getNombre()))
				{
					JOptionPane.showMessageDialog(null, 
												  "El nombre no coincide con el contacto actual", 
												  "Se ha producido un error", 
												  JOptionPane.ERROR_MESSAGE);
					return;
				}
				//Borrar el contacto
				Contacto contacto = principal.getContactoActivo();
				Usuario usuarioAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				boolean borrar = ControladorUsuarios.getUnicaInstancia().eliminarContacto(usuarioAct.getLogin(), contacto);
				if(borrar) {
					JOptionPane.showMessageDialog(null, 
							  "El contacto se ha eliminado correctamente",
							  "Contacto eliminado", 
							  JOptionPane.INFORMATION_MESSAGE);
					principal.mostrarChats();
				} else
					JOptionPane.showMessageDialog(null, 
							  "No se ha podido eliminar al contacto", 
							  "Se ha producido un error", 
							  JOptionPane.ERROR_MESSAGE);
				
			}
		}));
		
		add(new JMenuItem(new AbstractAction("Buscar Mensajes") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Ventana para buscar mensajes
				JFrame buscar = new JFrame();
				buscar.setTitle("Buscar Mensajes");
				buscar.setBounds(400, 200, 400, 380);
				buscar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				buscar.setResizable(false);
				buscar.setVisible(true);
			}
		}));
	}
	
	public void setPrincipal(MainView nPrincipal)
	{
		this.principal = nPrincipal;
	}
}
