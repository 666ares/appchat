package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import controlador.ControladorUsuarios;

@SuppressWarnings("serial")
public class RegisterView extends JPanel {
	
	static final int ANCHOW = 400;
	static final int ALTOW = 350;

	private JFrame ventana;
	private JLabel lblNombre;
	private JDateChooser fecha;
	private JLabel lblEdad;
	private JLabel lblMovil;
	private JLabel lblEmail;
	private JLabel lblUsuario;
	private JLabel lblPassword;
	private JLabel lblPasswordChk;
	private JTextField txtNombre;
	private JTextField txtMovil;
	private JTextField txtEmail;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JPasswordField txtPasswordChk;
	private JButton btnRegistrar;
	private JButton btnVolver;

	private JPanel jpanelAnterior;
	private JLabel lblNombreError;
	private JLabel lblEdadError;
	private JLabel lblMovilError;
	private JLabel lblEmailError;
	private JLabel lblUsuarioError;
	private JLabel lblPasswordError;

	public RegisterView(JFrame frame) {
		ventana = frame;
		jpanelAnterior = (JPanel) ventana.getContentPane();

		setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		add(tabbedPane, BorderLayout.CENTER);

		JPanel datosPersonales = new JPanel();
		datosPersonales.setLayout(new BoxLayout(datosPersonales, BoxLayout.Y_AXIS));
		tabbedPane.addTab("Datos del usuario", null, datosPersonales, null);

		/* Nombre y apellidos */
		JPanel linea_1 = new JPanel();
		linea_1.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixedSize(linea_1, ANCHOW, 25);
		linea_1.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		/* Fecha de nacimiento */
		JPanel linea_2 = new JPanel();
		linea_2.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixedSize(linea_2, ANCHOW, 25);
		linea_2.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		/* Email */
		JPanel linea_3 = new JPanel();
		linea_3.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixedSize(linea_3, ANCHOW, 25);
		linea_3.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		/* Número de teléfono */
		JPanel linea_4 = new JPanel();
		linea_4.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixedSize(linea_4, ANCHOW, 25);
		linea_4.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		/* Usuario */
		JPanel linea_5 = new JPanel();
		linea_5.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixedSize(linea_5, ANCHOW, 25);
		linea_5.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		/* Contraseña */
		JPanel linea_6 = new JPanel();
		linea_6.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixedSize(linea_6, ANCHOW, 25);
		linea_6.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		/* Botones */
		JPanel linea_7 = new JPanel();
		linea_7.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixedSize(linea_7, ANCHOW, 50);
		linea_7.setAlignmentX(JLabel.LEFT_ALIGNMENT);

		///////////////////////////////////////////////
		
		/* linea 1 */
		lblNombre = new JLabel("Nombre:", JLabel.RIGHT);
		fixedSize(lblNombre, 75, 20);
		txtNombre = new JTextField();
		fixedSize(txtNombre, 270, 20);
		lblNombreError = new JLabel("El nombre es obligatorio", JLabel.RIGHT);
		fixedSize(lblNombreError, 224, 15);
		lblNombreError.setForeground(Color.RED);
		linea_1.add(lblNombre);
		linea_1.add(txtNombre);

		/* linea 2 */
		fecha = new JDateChooser();
		fixedSize(fecha, 150, 20);
		lblEdad = new JLabel("Edad:", JLabel.RIGHT);
		fixedSize(lblEdad, 75, 20);
		lblEdadError = new JLabel("La edad es obligatoria", JLabel.RIGHT);
		fixedSize(lblEdadError, 210, 15);
		lblEdadError.setForeground(Color.RED);
		linea_2.add(lblEdad);
		linea_2.add(fecha);
		linea_2.add(lblEdadError);

		/* linea 3 */
		lblEmail = new JLabel("Email:", JLabel.RIGHT);
		fixedSize(lblEmail, 75, 20);
		txtEmail = new JTextField();
		fixedSize(txtEmail, 125, 20);
		lblEmailError = new JLabel("El email es obligatorio", JLabel.RIGHT);
		fixedSize(lblEmailError, 210, 15);
		lblEmailError.setForeground(Color.RED);
		linea_3.add(lblEmail);
		linea_3.add(txtEmail);
		linea_3.add(lblEmailError);

		/* linea 4 */
		lblMovil = new JLabel("Teléfono:", JLabel.RIGHT);
		fixedSize(lblMovil, 75, 20);
		txtMovil = new JTextField();
		fixedSize(txtMovil, 125, 20);
		lblMovilError = new JLabel("El teléfono es obligatorio", JLabel.RIGHT);
		fixedSize(lblMovilError, 224, 15);
		lblMovilError.setForeground(Color.RED);
		linea_4.add(lblMovil);
		linea_4.add(txtMovil);
		linea_4.add(lblMovilError);
		
		/* linea 5 */
		lblUsuario = new JLabel("Usuario:", JLabel.RIGHT);
		fixedSize(lblUsuario, 75, 20);
		txtUsuario = new JTextField();
		fixedSize(txtUsuario, 125, 20);
		lblUsuarioError = new JLabel("El usuario ya existe", JLabel.RIGHT);
		fixedSize(lblUsuarioError, 220, 15);
		lblUsuarioError.setForeground(Color.RED);
		linea_5.add(lblUsuario);
		linea_5.add(txtUsuario);
		linea_5.add(lblUsuarioError);

		/* linea 6 */
		lblPassword = new JLabel("Contraseña:", JLabel.RIGHT);
		fixedSize(lblPassword, 75, 20);
		txtPassword = new JPasswordField();
		fixedSize(txtPassword, 100, 20);
		lblPasswordChk = new JLabel("Repetir:", JLabel.RIGHT);
		fixedSize(lblPasswordChk, 75, 20);
		txtPasswordChk = new JPasswordField();
		fixedSize(txtPasswordChk, 100, 20);
		lblPasswordError = new JLabel("Error al introducir las contraseñas", JLabel.CENTER);
		fixedSize(lblPasswordError, ANCHOW, 15);
		lblPasswordError.setForeground(Color.RED);
		linea_6.add(lblPassword);
		linea_6.add(txtPassword);
		linea_6.add(lblPasswordChk);
		linea_6.add(txtPasswordChk);

		/* linea 7 */
		btnVolver = new JButton("Volver");
		fixedSize(btnVolver, 90, 30);
		btnRegistrar = new JButton("Registrar");
		fixedSize(btnRegistrar, 90, 30);
		linea_7.add(Box.createHorizontalStrut(75));
		linea_7.add(btnVolver);
		linea_7.add(Box.createHorizontalStrut(80));
		linea_7.add(btnRegistrar);

		ocultarErrores();

		datosPersonales.add(linea_1);
		datosPersonales.add(lblNombreError);
		datosPersonales.add(linea_2);
		datosPersonales.add(lblEdadError);
		datosPersonales.add(linea_3);
		datosPersonales.add(lblEmailError);
		datosPersonales.add(linea_4);
		datosPersonales.add(lblMovilError);
		datosPersonales.add(linea_5);
		datosPersonales.add(lblUsuarioError);
		datosPersonales.add(linea_6);
		datosPersonales.add(lblPasswordError);
		datosPersonales.add(linea_7);

		ventana.setContentPane(this);
		ventana.revalidate(); /* redibujar con el nuevo JPanel */

		/* Manejador botón volver */
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(jpanelAnterior);
				ventana.setTitle("Login Gestor Eventos");
				ventana.revalidate();
			}
		});

		/* Manejador botón Registrar */
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean OK = false;
				OK = checkFields();
				if (OK) {
					boolean registrado = false;
					registrado = ControladorUsuarios.getUnicaInstancia().registrarUsuario(
							txtNombre.getText(), fecha.getDate().toString(), txtEmail.getText(), txtMovil.getText(),
							txtUsuario.getText(), new String(txtPassword.getPassword()));
							
					if (registrado) {
						JOptionPane.showMessageDialog(ventana, "Usuario registrado correctamente.", 
															   "Registro",
															   JOptionPane.INFORMATION_MESSAGE);
						ventana.setContentPane(jpanelAnterior);
						ventana.revalidate();
					} else
						JOptionPane.showMessageDialog(ventana, "No se ha podido llevar a cabo el registro.\n",
															   "Registro", 
															   JOptionPane.ERROR_MESSAGE);
					ventana.setTitle("Login Gestor Eventos");
				}
			}
		});

	} /* constructor */

	/**
	 * Comprueba que los campos de registro estén bien
	 */
	private boolean checkFields() {
		boolean salida = true;
		/* borrar todos los errores en pantalla */
		ocultarErrores();
		if (txtNombre.getText().trim().isEmpty()) {
			lblNombreError.setVisible(true);
			salida = false;
		}
		if (fecha.getDate() == null) {
			lblEdadError.setVisible(true);
			salida = false;
		}
		if (txtEmail.getText().trim().isEmpty()) {
			lblEmailError.setVisible(true);
			salida = false;
		}
		if (txtMovil.getText().trim().isEmpty()) {
			lblMovilError.setVisible(true);
			salida = false;
		}
		if (txtUsuario.getText().trim().isEmpty()) {
			lblUsuarioError.setText("El usuario es obligatorio");
			lblUsuarioError.setVisible(true);
			salida = false;
		}
		String password = new String(txtPassword.getPassword());
		String password2 = new String(txtPasswordChk.getPassword());
		if (password.equals("")) {
			lblPasswordError.setText("La contraseña no puede estar en blanco");
			lblPasswordError.setVisible(true);
			salida = false;
		} else if (!password.equals(password2)) {
			lblPasswordError.setText("Las contraseñas no coinciden");
			lblPasswordError.setVisible(true);
			salida = false;
		}
		if (ControladorUsuarios.getUnicaInstancia().esUsuarioRegistrado(txtUsuario.getText())) {
			lblUsuarioError.setText("Ya existe ese usuario.");
			lblUsuarioError.setVisible(true);
			salida = false;
		}
		return salida;
	}

	/**
	 * Oculta todos los errores que pueda haber en la pantalla
	 */
	private void ocultarErrores() {
		lblNombreError.setVisible(false);
		lblEdadError.setVisible(false);
		lblEmailError.setVisible(false);
		lblMovilError.setVisible(false);
		lblUsuarioError.setVisible(false);
		lblPasswordError.setVisible(false);
	}

	/**
	 * Fija el tamaño de un componente
	 */
	private void fixedSize(JComponent o, int x, int y) {
		Dimension d = new Dimension(x, y);
		o.setMinimumSize(d);
		o.setMaximumSize(d);
		o.setPreferredSize(d);
	}
}
