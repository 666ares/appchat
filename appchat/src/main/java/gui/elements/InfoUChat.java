package gui.elements;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoUChat extends JFrame{

	private static final long serialVersionUID = 1L;
	
	static final int ANCHOW = 260;
	static final int ALTOW = 250;
	
	public InfoUChat(String nombreUser, String telefono) {
		super();
		setTitle(nombreUser);
		setBounds(100, 100, ANCHOW, ALTOW);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		getContentPane().add(Box.createVerticalStrut(10));
		
		JPanel icono = new JPanel();
	    
	    ImageIcon icon = new ImageIcon("icons/profile_picture.png");
		Image imageIcon = icon.getImage();
		Image newImage = imageIcon.getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
		ImageIcon icon2 = new ImageIcon(newImage);
		JLabel iconoUser = new JLabel();
		iconoUser.setIcon(icon2);
		icono.add(iconoUser);
		
		getContentPane().add(icono);
	    
	    JPanel nombre = new JPanel();
	    nombre.setLayout(new GridLayout(0, 1, 0, 0));
	    
	    JLabel nombreUsuario = new JLabel("      Nombre: " + nombreUser);
	    nombre.add(nombreUsuario);
	    
	    JLabel telefonoUser = new JLabel("      Teléfono: " + telefono);
	    nombre.add(telefonoUser);
	    
	    getContentPane().add(nombre);
	    getContentPane().add(Box.createVerticalStrut(10));
		
	}
	
	public void makeVisible() {
		setVisible(true);
	}
	
	public void makeInvisible() {
		setVisible(false);
	}
	

}
