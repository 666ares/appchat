package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public final class TDSUsuarioDAO implements UsuarioDAO {

	private ServicioPersistencia servPersistencia;
	
	public TDSUsuarioDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	private Usuario entidadToUsuario(Entidad eUsuario) {
		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		String fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento");
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		String telefono = servPersistencia.recuperarPropiedadEntidad(eUsuario, "telefono");
		String usuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, "usuario");
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
		
		Usuario u = new Usuario(nombre, fechaNacimiento, email, telefono, usuario, password);
		u.setId(eUsuario.getId());
		return u;	
	}
	
	private Entidad usuarioToEntidad(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		eUsuario.setNombre("Usuario");
		
		eUsuario.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("nombre", usuario.getNombre()),
						new Propiedad("fechaNacimiento", usuario.getFechaNacimiento()),
						new Propiedad("email", usuario.getEmail()),
						new Propiedad("telefono", usuario.getTelefono()),
						new Propiedad("usuario", usuario.getUsuario()),
						new Propiedad("password", usuario.getPassword())
						))
				);
		
		return eUsuario;
	}
	
	public void create(Usuario usuario) {
		Entidad eUsuario = this.usuarioToEntidad(usuario);
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setId(eUsuario.getId());
	}
	
	public boolean delete(Usuario usuario) {
		Entidad eUsuario;
		eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		return servPersistencia.borrarEntidad(eUsuario);
	}
	
	public void updatePerfil(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "password");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "password", usuario.getPassword());
	}
	
	public Usuario get(int id) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		return entidadToUsuario(eUsuario);
	}
	
	public List<Usuario> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Usuario");
		
		List<Usuario> usuarios = new LinkedList<Usuario>();
		
		for (Entidad eUsuario : entidades)
			usuarios.add(get(eUsuario.getId()));
		
		return usuarios;
	}
	
	
	
}
