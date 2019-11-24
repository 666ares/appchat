package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public final class TDSUsuarioDAO implements UsuarioDAO {

	private static ServicioPersistencia servPersistencia;
	private static TDSUsuarioDAO unicaInstancia = null;
	
	// Patrón Singleton
	public static TDSUsuarioDAO getUnicaInstancia() {
		return (unicaInstancia == null) ? new TDSUsuarioDAO() : unicaInstancia;
	}
	
	public TDSUsuarioDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public Usuario recuperarUsuario(int codigo) {
		
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		// Si no, la recupera de la base de datos
		Entidad eUsuario;
		String nombre;
		String fechaNacimiento;
		String email;
		String telefono;
		String login;
		String password;
		String imagenPerfil;
		String saludo;
		List<Contacto> indiv = new LinkedList<Contacto>();
		List<Contacto> grupos = new LinkedList<Contacto>();
		
		// Recuperar entidad
		eUsuario = servPersistencia.recuperarEntidad(codigo);
		
		// Recuperar propiedades que no son objetos
		nombre 			= servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento");
		email 			= servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		telefono 		= servPersistencia.recuperarPropiedadEntidad(eUsuario, "telefono");
		login 			= servPersistencia.recuperarPropiedadEntidad(eUsuario, "login");
		password 		= servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
		imagenPerfil 	= servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagenPerfil");
		saludo 			= servPersistencia.recuperarPropiedadEntidad(eUsuario, "saludo");

		Usuario u = new Usuario(nombre, fechaNacimiento, email, telefono, login,
								password, imagenPerfil, saludo);
		u.setId(codigo);
		
		// Añadir el usuario al pool antes de llamar a otros adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, u);
		
		// Recuperar propiedades que son objetos
		/* Contactos individuales */
		indiv = obtenerContactosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"));

		for (Contacto ind : indiv)
			u.addContacto(ind);
		
		/* Grupos */
		grupos = obtenerGruposDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "grupos"));

		for (Contacto gr : grupos)
			u.addContacto(gr);

		return u;	
	}

	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		boolean existe = true;
		
		// Si la entidad está registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		
		if (existe) 
			return;
		
		// Registrar primero los atributos que son objetos
		TDSContactoIndividualDAO adaptadorCInd = TDSContactoIndividualDAO.getUnicaInstancia();
		TDSGrupoDAO adaptadorGrupo = TDSGrupoDAO.getUnicaInstancia();
		
		for (Contacto c : usuario.getContactos()) {
			if (c instanceof ContactoIndividual) {
				ContactoIndividual ci = (ContactoIndividual) c;
				adaptadorCInd.registrarIndividual(ci);
			}
			else if (c instanceof Grupo) {
				Grupo gr = (Grupo) c;
				adaptadorGrupo.registrarGrupo(gr);
			}
		}
		
		eUsuario = new Entidad();
		eUsuario.setNombre("Usuario");
		
		eUsuario.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("nombre", 			usuario.getNombre()),
						new Propiedad("fechaNacimiento", 	usuario.getFechaNacimiento()),
						new Propiedad("email",	 			usuario.getEmail()),
						new Propiedad("telefono", 			usuario.getTelefono()),
						new Propiedad("login", 				usuario.getLogin()),
						new Propiedad("password",	 		usuario.getPassword()),
						new Propiedad("imagenPerfil", 		usuario.getImagenPerfil()),
						new Propiedad("saludo", 			usuario.getSaludo()),
						new Propiedad("contactos", 			obtenerCodigosContactos(usuario.getContactos())),
						new Propiedad("grupos", 			obtenerCodigosGrupos(usuario.getContactos()))
						))
				);
		
		// Registrar entidad Usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// Asignar identificador único
		usuario.setId(eUsuario.getId());
	}
	
	public void borrarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		servPersistencia.borrarEntidad(eUsuario);
	}
	
	public void modificarUsuario(Usuario usuario) {
		
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "imagenPerfil");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "imagenPerfil", usuario.getImagenPerfil());

		servPersistencia.eliminarPropiedadEntidad(eUsuario, "saludo");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "saludo", usuario.getSaludo());
		
		String contactos = obtenerCodigosContactos(usuario.getContactos());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contactos");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contactos", contactos);
		
		String grupos = obtenerCodigosGrupos(usuario.getContactos());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "grupos");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "grupos", grupos);
	}
	
	public List<Usuario> recuperarTodosUsuarios() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();
		
		for (Entidad eUsuario : entidades)
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		
		return usuarios;
	}
	
	// ====================
	// FUNCIONES AUXILIARES
	// ====================
	private String obtenerCodigosGrupos(List<Contacto> contactos) {
		String aux = "";
		for (Contacto c : contactos) {
			if (c instanceof Grupo) {
				Grupo gr = (Grupo) c;
				aux += gr.getId() + " ";
			}
		}
		return aux.trim();
	}

	private String obtenerCodigosContactos(List<Contacto> contactos) {
		String aux = "";
		for (Contacto c : contactos) {
			if (c instanceof ContactoIndividual) {
				ContactoIndividual ci = (ContactoIndividual) c;
				aux += ci.getId() + " ";
			}
		}
		return aux.trim();
	}
	
	private List<Contacto> obtenerGruposDesdeCodigos(String cGrupos) {
		List<Contacto> listaGrupos = new LinkedList<Contacto>();
		
		if (cGrupos == null || cGrupos.equals(""))
			return listaGrupos;
		
		StringTokenizer strTok = new StringTokenizer(cGrupos, " ");
		
		TDSGrupoDAO adaptadorG = TDSGrupoDAO.getUnicaInstancia();
		
		while (strTok.hasMoreTokens())
			listaGrupos.add(
					adaptadorG.recuperarGrupo(Integer.valueOf((String) strTok.nextElement())));
		return listaGrupos;
	}

	private List<Contacto> obtenerContactosDesdeCodigos(String cIndividuales) {
		
		List<Contacto> listaIndividuales = new LinkedList<Contacto>();
		
		if (cIndividuales == null || cIndividuales.equals(""))
			return listaIndividuales;

		StringTokenizer strTok = new StringTokenizer(cIndividuales, " ");
		
		TDSContactoIndividualDAO adaptadorC = TDSContactoIndividualDAO.getUnicaInstancia();
		
		while (strTok.hasMoreTokens())
			listaIndividuales.add(
					adaptadorC.recuperarIndividual(Integer.valueOf((String) strTok.nextElement())));
		return listaIndividuales;
	}
	
}
