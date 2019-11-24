package gui.elements;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dominio.Contacto;
import dominio.ContactoIndividual;

public class InfoUChat extends JFrame{

	private static final long serialVersionUID = 1L;
	
	static final int ANCHOW = 260;
	static final int ALTOW = 250;
	
	public InfoUChat(Contacto c) {
		
		super();
		
		setTitle(c.getNombre());
		setBounds(100, 100, ANCHOW, ALTOW);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		getContentPane().add(Box.createVerticalStrut(10));
		
		JPanel panelImagen = new JPanel();
	    
		ContactoIndividual ci = (ContactoIndividual) c;
		
	    ImageIcon imageIcon = new ImageIcon(ci.getUsuario().getImagenPerfil());
		Image image = imageIcon.getImage();
		Image newImage = image.getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
		ImageIcon imageIcon2 = new ImageIcon(newImage);
		JLabel iconoUser = new JLabel();
		iconoUser.setIcon(imageIcon2);
		panelImagen.add(iconoUser);
		
		getContentPane().add(panelImagen);
	    
	    JPanel panelNombreTelefono = new JPanel();
	    panelNombreTelefono.setLayout(new GridLayout(0, 1, 0, 0));
	    
	    // ==============
	    // Label 'Nombre'
	    // ==============
	    JLabel nombreUsuario = new JLabel("      Nombre: " + ci.getNombre());
	    panelNombreTelefono.add(nombreUsuario);
	    
	    // ================
	    // Label 'Telefono'
	    // ================
	    JLabel telefonoUser = new JLabel("      Teléfono: " + ci.getTelefono());
	    panelNombreTelefono.add(telefonoUser);
	    
	    getContentPane().add(panelNombreTelefono);
	    getContentPane().add(Box.createVerticalStrut(10));
		
	}
	
	public void makeVisible() 	{ setVisible(true); }
	public void makeInvisible() { setVisible(false); }
}
