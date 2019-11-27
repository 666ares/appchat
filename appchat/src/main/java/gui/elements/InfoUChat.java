package gui.elements;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;

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
		
		
	    
	    if(c instanceof ContactoIndividual) {
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
	    } else {
	    	JPanel panelImagen = new JPanel();
	    	panelImagen.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		    
			Grupo grupo = (Grupo) c;
			
		    ImageIcon imageIcon = new ImageIcon("icons/group.png");
			Image image = imageIcon.getImage();
			Image newImage = image.getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
			ImageIcon imageIcon2 = new ImageIcon(newImage);
			JLabel iconoUser = new JLabel();
			iconoUser.setIcon(imageIcon2);
			panelImagen.add(iconoUser);
			
			// ==============
		    // Label 'Nombre'
		    // ==============
		    JLabel nombreUsuario = new JLabel("      Nombre: " + grupo.getNombre());
		    panelImagen.add(nombreUsuario);
		    
		    // ==============
		    // Label 'Miembros'
		    // ==============
		    JLabel texto1 = new JLabel("Miembros:");
		    panelImagen.add(texto1);
		    
		    DefaultListModel<String> nombreMiembros= new DefaultListModel<String>();
		    List<ContactoIndividual> contactosGrupo = grupo.getMiembros();
		    for(ContactoIndividual contacto : contactosGrupo) {
		    	nombreMiembros.addElement(contacto.getNombre());
		    }
		    JList<String> miembros = new JList<String>(nombreMiembros);
		    miembros.setLayoutOrientation(JList.VERTICAL);
		    JScrollPane listaMiembros = new JScrollPane(miembros);
		    listaMiembros.setPreferredSize(new Dimension(200, 80));
		    panelImagen.add(listaMiembros);
		    
		    
		    getContentPane().add(panelImagen);
		    	
	    }
	}
	
	public void makeVisible() 	{ setVisible(true); }
	public void makeInvisible() { setVisible(false); }
}
