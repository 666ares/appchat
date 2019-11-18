package gui.elements;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import controlador.ControladorUsuarios;
import dominio.Usuario;

public class InfoUsuario extends JFrame{

	private static final long serialVersionUID = 1L;
	
	static final int ANCHOW = 260;
	static final int ALTOW = 300;
	
	public InfoUsuario() {
		super();
		
		Usuario uAct = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
		
		String nombreUser = uAct.getNombre();			/* nombre del usuario */	
		String saludo = uAct.getSaludo();				/* saludo del usuario */
		String ruta_imagen = uAct.getImagenPerfil();	/* ruta relativa a imagen perfil */
		
		setTitle("Mi información");
		setBounds(100, 100, ANCHOW, ALTOW);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		getContentPane().add(Box.createVerticalStrut(10));
		
		/* Imagen del usuario */
		JPanel icono = new JPanel();
	    ImageIcon icon = new ImageIcon(ruta_imagen);
		Image imageIcon = icon.getImage();
		Image newImage = imageIcon.getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
		ImageIcon icon2 = new ImageIcon(newImage);
		final JLabel iconoUser = new JLabel();
		iconoUser.setIcon(icon2);
		
		iconoUser.addMouseListener(new MouseListener() {
			
			public void mouseClicked(MouseEvent e) {
				
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(null);

				File selectedFile = null;
				
				if (returnValue == JFileChooser.APPROVE_OPTION)
					selectedFile = jfc.getSelectedFile();
				else if (returnValue == JFileChooser.CANCEL_OPTION
						|| returnValue == JFileChooser.ERROR_OPTION)
					return;
				
				Usuario act = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
				act.setImagenPerfil(selectedFile.getAbsolutePath());
				
				if (ControladorUsuarios.getUnicaInstancia().updateUsuario(act)) {
					ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
					Image imageIcon = icon.getImage();
					Image newImage = imageIcon.getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
					ImageIcon icon2 = new ImageIcon(newImage);
					iconoUser.setIcon(icon2);
				}
				
			}
			
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			
		});
		
		icono.add(iconoUser);
		getContentPane().add(icono);
	    
		/* Nombre del usuario */
	    JPanel nombre = new JPanel();
	    nombre.setLayout(new GridLayout(0, 1, 0, 0));
	    final JLabel nombreUsuario = new JLabel("      Nombre: " + nombreUser);
	    nombre.add(nombreUsuario);
	    
	    /* Saludo del usuario */
	    final JLabel saludoUser = new JLabel("      Saludo: " + saludo);
	    nombre.add(saludoUser);
	    
	    getContentPane().add(nombre);
	    
	    /* Botones para cambiar nombre y saludo */
	    JPanel botones = new JPanel();
	    botones.setLayout(new FlowLayout());
	    
	    /*
	    JButton cambiarNombre = new JButton("Cambiar nombre");
	    cambiarNombre.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String nuevoNombre = JOptionPane.showInputDialog(
						"Introduce el nuevo nombre:");
				
				if (!nuevoNombre.equals("")) {
					Usuario act = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
					act.setNombre(nuevoNombre);
				
					if (ControladorUsuarios.getUnicaInstancia().updateUsuario(act))
						nombreUsuario.setText("      Nombre: " + nuevoNombre);
				}
				
			}
		});
	    
	    botones.add(cambiarNombre);
	    */
	    
	    JButton cambiarSaludo = new JButton("Cambiar saludo  ");
	    cambiarSaludo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String nuevoSaludo = JOptionPane.showInputDialog(
						"Introduce el nuevo saludo:");
				
				if (!nuevoSaludo.equals("")) {
					Usuario act = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
					act.setSaludo(nuevoSaludo);
				
					if (ControladorUsuarios.getUnicaInstancia().updateUsuario(act))
						saludoUser.setText("      Saludo: " + nuevoSaludo);
				}

			}
		});
	    botones.add(cambiarSaludo);
	    
	    getContentPane().add(botones);
	    
		
		
	}
	
	public void makeVisible() {
		setVisible(true);
	}
	
	public void makeInvisible() {
		setVisible(false);
	}
	

}
