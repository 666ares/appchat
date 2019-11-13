package controlador;

import dao.DAOException;
import dao.FactoriaDAO;
import dao.UsuarioDAO;
import dominio.CatalogoUsuarios;
import dominio.Usuario;

public class ControladorUsuarios {

	private Usuario usuarioActual;
	private static ControladorUsuarios unicaInstancia;
	private FactoriaDAO factoria;
	
	private ControladorUsuarios() {
		usuarioActual = null;
		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}
	
	public static ControladorUsuarios getUnicaInstancia() {
		if (unicaInstancia == null) unicaInstancia = new ControladorUsuarios();
		return unicaInstancia;
	}
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public boolean esUsuarioRegistrado(String login) {
		return CatalogoUsuarios.getUnicaInstancia().getUsuario(login) != null;
	}
	
	public boolean loginUsuario(String nombre, String password) {
		Usuario usuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(nombre);
		if (usuario != null && usuario.getPassword().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}
	
	public boolean registrarUsuario(String nombre, String fechaNacimiento, String email,
			String telefono, String login, String password) {
		if (esUsuarioRegistrado(login)) return false;
		
		Usuario usuario = new Usuario(nombre, fechaNacimiento, email, telefono, login, password);
		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.create(usuario);
		
		CatalogoUsuarios.getUnicaInstancia().addUsuario(usuario);
		return true;
	}
	
	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getUsuario())) return false;
		
		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.delete(usuario);
		
		CatalogoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		return true;
	}
	
	
		
}
