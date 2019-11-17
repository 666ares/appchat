package gui.elements;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
		setBounds(100, 100, 430, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton boton1 = new JButton("Crear Contacto");
		botones.add(boton1, BorderLayout.CENTER);
		
		JButton boton2 = new JButton("Crear Grupo");
		botones.add(boton2, BorderLayout.CENTER);
		
		JButton boton3 = new JButton("Modificar Grupo");
		botones.add(boton3, BorderLayout.CENTER);
		
		JButton boton4 = new JButton("Mostrar Contactos");
		botones.add(boton4, BorderLayout.CENTER);
		
		JButton boton5 = new JButton("Ver Estadisticas");
		botones.add(boton5, BorderLayout.CENTER);
		
		JButton boton6 = new JButton("Convertirse Premium");
		botones.add(boton6, BorderLayout.CENTER);
		
		JButton boton7 = new JButton("Cerrar Sesión");
		botones.add(boton7, BorderLayout.CENTER);
		
		getContentPane().add(botones);
	}
	
	public void makeVisible() {
		setVisible(true);
	}
	
	public void makeInvisible() {
		setVisible(false);
	}
	

}
