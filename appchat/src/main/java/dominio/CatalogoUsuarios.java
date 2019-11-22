package dominio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import dao.DAOException;
import dao.FactoriaDAO;
import dao.UsuarioDAO;

public class CatalogoUsuarios {
	
	private HashMap<Integer, Usuario> 	usuariosPorID;
	private HashMap<String, Usuario> 	usuariosPorLogin;
	private HashMap<String, Usuario>	usuariosPorTelefono;
	
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
			
			usuariosPorID 		= new HashMap<Integer, Usuario>();
			usuariosPorLogin 	= new HashMap<String, Usuario>();
			usuariosPorTelefono = new HashMap<String, Usuario>();
			
			List<Usuario> listaUsuarios = adaptadorUsuario.recuperarTodosUsuarios();

			for (Usuario usuario : listaUsuarios) {
				usuariosPorID.put(usuario.getId(), usuario);
				usuariosPorLogin.put(usuario.getLogin(), usuario);
				usuariosPorTelefono.put(usuario.getTelefono(), usuario);
			}
		} catch (DAOException eDAO) { 
			eDAO.printStackTrace(); 
		}
	}
	
	public List<Usuario> getUsuarios() throws DAOException {
		return new LinkedList<Usuario>(usuariosPorLogin.values());
	}
	
	public Usuario _getUsuario(String telefono) {
		return usuariosPorTelefono.get(telefono);
	}
	
	public Usuario getUsuario(String login) {
		return usuariosPorLogin.get(login);
	}
	
	public Usuario getUsuario(int id) {
		return usuariosPorID.get(id);
	}
	
	public void addUsuario(Usuario usuario) {
		usuariosPorID.put(usuario.getId(), usuario);
		usuariosPorLogin.put(usuario.getLogin(), usuario);
	}
	
	public void removeUsuario(Usuario usuario) {
		usuariosPorID.remove(usuario.getId());
		usuariosPorLogin.remove(usuario.getLogin());
	}

	public void updateUsuario(Usuario usuario) {
		removeUsuario(usuario);
		addUsuario(usuario);
		
	}
}
