package controlador;

import java.util.List;

import dao.ContactoIndividualDAO;
import dao.DAOException;
import dao.FactoriaDAO;
import dao.GrupoDAO;
import dao.UsuarioDAO;
import dominio.CatalogoUsuarios;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
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
		if (unicaInstancia == null) 
			unicaInstancia = new ControladorUsuarios();
		return unicaInstancia;
	}
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public boolean esUsuarioRegistrado(String login) {
		return CatalogoUsuarios.getUnicaInstancia().getUsuario(login) != null;
	}
	
	public boolean loginUsuario(String login, String password) {
		Usuario usuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(login);
		if (usuario != null && usuario.getPassword().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}
	
	public boolean registrarUsuario(String nombre, String fechaNacimiento,
								     String email, String telefono, 
									 String login, String password,
									 String imagenPerfil, String saludo) {
		if (esUsuarioRegistrado(login)) 
			return false;
		
		Usuario usuario = new Usuario(nombre, fechaNacimiento, email,
									  telefono, login, password,
									  imagenPerfil, saludo);
		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.create(usuario);
		
		CatalogoUsuarios.getUnicaInstancia().addUsuario(usuario);
		return true;
	}
	
	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getLogin())) 
			return false;
		
		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.delete(usuario);
		
		CatalogoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		return true;
	}
	
	public Usuario buscarUsuario(String login) {
		if (!esUsuarioRegistrado(login))
			return null;
		
		return CatalogoUsuarios.getUnicaInstancia().getUsuario(login);
	}
	
	public List<Contacto> mostrarContactos(String login) {
		Usuario usuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(login);
		
		List<Contacto> contactos = usuario.getContactos();
		
		return contactos;
	}
	
	public boolean añadirContacto(String login, Contacto c) {
		Usuario usuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(login);
		
		if (usuario.añadirUsuario(c)) {
			UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
			usuarioDAO.updatePerfil(usuario);
			
			if (c instanceof ContactoIndividualDAO) {
				ContactoIndividual cInd = (ContactoIndividual) c;
				ContactoIndividualDAO cIndDAO = factoria.getContactoIndividualDAO();
				cIndDAO.create(cInd);
			}
			else if (c instanceof GrupoDAO) {
				Grupo g = (Grupo) c;
				GrupoDAO grupoDAO = factoria.getGrupoDAO();
				grupoDAO.create(g);
			}
			CatalogoUsuarios.getUnicaInstancia().updateUsuario(usuario);
			return true;
		}
		return false;
	}
	
	public boolean updateUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getLogin()))
			return false;
		
		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		usuarioDAO.updatePerfil(usuario);
		
		CatalogoUsuarios.getUnicaInstancia().updateUsuario(usuario);
		return true;
	}	
}
