package dominio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import dao.DAOException;
import dao.FactoriaDAO;
import dao.UsuarioDAO;

public class CatalogoUsuarios {
	
	private HashMap<String, Usuario>	usuariosPorTelefono;
	private HashMap<String, Usuario>	usuariosPorLogin;
	private static CatalogoUsuarios 	unicaInstancia;	
	private FactoriaDAO 				factoria;	
	private UsuarioDAO 					adaptadorUsuario;

	public static CatalogoUsuarios getUnicaInstancia() {
		if (unicaInstancia == null)
			return new CatalogoUsuarios();
		return unicaInstancia;
	}
	
	private CatalogoUsuarios() {
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);			
			adaptadorUsuario = factoria.getUsuarioDAO();			
			usuariosPorLogin 	= new HashMap<String, Usuario>();
			usuariosPorTelefono = new HashMap<String, Usuario>();
			
			List<Usuario> listaUsuarios = adaptadorUsuario.recuperarTodosUsuarios();

			for (Usuario usuario : listaUsuarios) {
				usuariosPorLogin.put(usuario.getLogin(), usuario);
				usuariosPorTelefono.put(usuario.getTelefono(), usuario);
			}
			
		} catch (DAOException eDAO) { 
			eDAO.printStackTrace(); 
		}
	}
	
	// Retorna una lista con todos los usuarios del catálogo
	public List<Usuario> getUsuarios() throws DAOException {
		return new LinkedList<Usuario>(usuariosPorLogin.values());
	}
	
	// Retorna el usuario asociado a un número de teléfono
	public Usuario _getUsuario(String telefono) {
		return usuariosPorTelefono.get(telefono);
	}
	
	// Retorna el usuario asociado a un login
	public Usuario getUsuario(String login) {
		return usuariosPorLogin.get(login);
	}

	// Añade un usuario al catálogo
	public void addUsuario(Usuario usuario) {
		usuariosPorLogin.put(usuario.getLogin(), usuario);
		usuariosPorTelefono.put(usuario.getTelefono(), usuario);
	}
	
	// Elimina un usuario del catálogo
	public void removeUsuario(Usuario usuario) {
		usuariosPorLogin.remove(usuario.getLogin());
		usuariosPorTelefono.remove(usuario.getTelefono());
	}

	// Actualiza la información de un usuario del catálogo
	public void updateUsuario(Usuario usuario) {
		removeUsuario(usuario);
		addUsuario(usuario);		
	}
}
