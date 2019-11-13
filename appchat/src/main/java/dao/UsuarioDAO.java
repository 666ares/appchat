package dao;

import java.util.List;

import dominio.Usuario;

public interface UsuarioDAO {

	void create(Usuario usuario);
	boolean delete(Usuario usuario);
	void updatePerfil(Usuario usuario);
	
	Usuario get(int id);
	List<Usuario> getAll();
}
