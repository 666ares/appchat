package dao;

import java.util.List;

import dominio.Usuario;

public interface UsuarioDAO {

	void 			registrarUsuario(Usuario usuario);
	void 			borrarUsuario(Usuario usuario);
	void 			modificarUsuario(Usuario usuario);
	Usuario 		recuperarUsuario(int id);
	List<Usuario> 	recuperarTodosUsuarios();
}
