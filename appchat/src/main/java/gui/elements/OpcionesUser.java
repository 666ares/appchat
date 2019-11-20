package gui.elements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OpcionesUser extends JFrame {

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
		
		// ======================
		// Botón 'Crear contacto'
		// ======================
		JButton boton1 = new JButton("Añadir contacto");
		boton1.setPreferredSize(new Dimension(430, 50));
		boton1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
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
				
				// String nombre = txtNombre.getText();
				// String telefono = txtTelefono.getText();
				
			}
		});
		
		contentPane.add(boton1, BorderLayout.CENTER);
		
		// =========================
		// Botón 'Mostrar contactos'
		// =========================
		JButton boton2 = new JButton("Mostrar contactos");
		boton2.setPreferredSize(new Dimension(430, 50));
		boton2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				
			}
		});
		
		contentPane.add(boton2, BorderLayout.CENTER);

		// ===================
		// Botón 'Crear grupo'
		// ===================
		JButton boton3 = new JButton("Crear grupo");
		boton3.setPreferredSize(new Dimension(430, 50));
		boton3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				
			}
		});
		
		contentPane.add(boton3, BorderLayout.CENTER);
		
		// =======================
		// Botón 'Modificar grupo'
		// =======================
		JButton boton4 = new JButton("Modificar grupo");
		boton4.setPreferredSize(new Dimension(430, 50));
		boton4.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				
			}
		});

		contentPane.add(boton4, BorderLayout.CENTER);
		
		// ========================
		// Botón 'Ver estadísticas'
		// ========================
		JButton boton5 = new JButton("Ver estadísticas");
		boton5.setPreferredSize(new Dimension(430, 50));
		boton5.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				
			}
		});

		contentPane.add(boton5, BorderLayout.CENTER);
		
		// =======================
		// Botón 'Hacerse premium'
		// =======================
		JButton boton6 = new JButton("Hacerse premium");
		boton6.setPreferredSize(new Dimension(430, 50));
		boton6.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				
			}
		});

		contentPane.add(boton6, BorderLayout.CENTER);
		
		// =====================
		// Botón 'Cerrar sesión'
		// =====================
		JButton boton7 = new JButton("Cerrar sesión");
		boton7.setPreferredSize(new Dimension(430, 50));
		boton7.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		contentPane.add(boton7, BorderLayout.CENTER);
		
	}
	
	public void makeVisible() 	{ setVisible(true); }
	public void makeInvisible() { setVisible(false); }

}
