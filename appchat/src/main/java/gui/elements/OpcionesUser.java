package gui.elements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.FlowView.FlowStrategy;

public class OpcionesUser extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final int ANCHOW = 430;
	static final int ALTOW = 300;
	
	public OpcionesUser() {
		super();
		setTitle("Opciones");
		setBounds(150, 150, 430, 387);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		final JPanel contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setContentPane(contentPane);
		
		//Boton Crear Contacto
		JButton boton1 = new JButton("Crear Contacto");
		boton1.setPreferredSize(new Dimension(430, 50));
		boton1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JTextField username = new JTextField();
				JTextField password = new JPasswordField();
				Object[] message = {
				    "Nombre Contacto:", username,
				    "Teléfono:", password
				};
				
				JOptionPane.showConfirmDialog(null, message, "Añadir Contacto", JOptionPane.OK_CANCEL_OPTION);
				//Comprobar si el contacto ya existe
				//Si no existe se añade a la lista de contactos
				
			}
		});
		contentPane.add(boton1, BorderLayout.CENTER);
		
		//Boton Modificar Contacto
		JButton boton4 = new JButton("Mostrar Contactos");
		boton4.setPreferredSize(new Dimension(430, 50));
		contentPane.add(boton4, BorderLayout.CENTER);
		
		//Boton Crear Grupo
		JButton boton2 = new JButton("Crear Grupo");
		boton2.setPreferredSize(new Dimension(430, 50));
		contentPane.add(boton2, BorderLayout.CENTER);
		
		//Boton Modificar Grupo
		JButton boton3 = new JButton("Modificar Grupo");
		boton3.setPreferredSize(new Dimension(430, 50));
		contentPane.add(boton3, BorderLayout.CENTER);
		
		//Boton Ver Estadisticas
		JButton boton5 = new JButton("Ver Estadisticas");
		boton5.setPreferredSize(new Dimension(430, 50));
		contentPane.add(boton5, BorderLayout.CENTER);
		
		//Boton Premium
		JButton boton6 = new JButton("Convertirse Premium");
		boton6.setPreferredSize(new Dimension(430, 50));
		contentPane.add(boton6, BorderLayout.CENTER);
		
		//Boton Cerrar Sesion
		JButton boton7 = new JButton("Cerrar Sesión");
		boton7.setPreferredSize(new Dimension(430, 50));
		contentPane.add(boton7, BorderLayout.CENTER);
		
	}
	
	public void makeVisible() {
		setVisible(true);
	}
	
	public void makeInvisible() {
		setVisible(false);
	}
	

}
