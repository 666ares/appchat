package gui.elements;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OpcionesChat extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final int ANCHOW = 430;
	static final int ALTOW = 300;
	
	public OpcionesChat() {
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
		
		JButton boton1 = new JButton("Eliminar mensajes");
		botones.add(boton1, BorderLayout.CENTER);
		
		JButton boton2 = new JButton("Eliminar Contacto");
		botones.add(boton2, BorderLayout.CENTER);
		
		getContentPane().add(botones);
		
	}
	
	public void makeVisible() {
		setVisible(true);
	}
	
	public void makeInvisible() {
		setVisible(false);
	}
	

}
