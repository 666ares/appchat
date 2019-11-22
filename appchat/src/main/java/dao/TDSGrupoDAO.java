package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Mensaje;
import dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public final class TDSGrupoDAO implements GrupoDAO {

	private ServicioPersistencia servPersistencia;
	private static TDSGrupoDAO unicaInstancia = null;
	
	public static TDSGrupoDAO getUnicaInstancia() {
		if (unicaInstancia == null)
			return new TDSGrupoDAO();
		else
			return unicaInstancia;
	}
	
	public TDSGrupoDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public void registrarGrupo(Grupo grupo) {
		
		Entidad eGrupo;
		boolean existe = true;
		
		// Si la entidad está registrada no la registra de nuevo
		try {
			eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		
		if (existe)
			return;
		
		// Registrar primero los atributos que son objetos
		// Usuario Administrador
		TDSUsuarioDAO adaptadorU = TDSUsuarioDAO.getUnicaInstancia();
		adaptadorU.registrarUsuario(grupo.getAdmin());
		
		// Miembros del grupo
		TDSContactoIndividualDAO adaptadorC = TDSContactoIndividualDAO.getUnicaInstancia();
		for (ContactoIndividual c : grupo.getMiembros())
			adaptadorC.registrarIndividual(c);
		
		// Crear entidad Grupo
		eGrupo = new Entidad();
		eGrupo.setNombre("Grupo");
		eGrupo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
										new Propiedad("nombre", grupo.getNombre()),
										new Propiedad("admin", String.valueOf(grupo.getId())),
										new Propiedad("miembros", obtenerCodigosMiembros(grupo.getMiembros())),
										new Propiedad("mensajes", obtenerCodigosMensajes(grupo.getMensajes()))
										))
				);
		
		// Registrar entidad Grupo
		eGrupo = servPersistencia.registrarEntidad(eGrupo);
		// Asignar identificador único
		grupo.setId(eGrupo.getId());
		
	}

	public Grupo recuperarGrupo(int codigo) {
		
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Grupo) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		// Si no, la recupera de la base de datos
		Entidad eGrupo;
		String nombre;
		Usuario admin;
		String admin_id;
		List<ContactoIndividual> miembros;
		List<Mensaje> m;
		
		// Recuperar entidad
		eGrupo = servPersistencia.recuperarEntidad(codigo);
		
		// Recuperar propiedades que no son objetos
		nombre 		= servPersistencia.recuperarPropiedadEntidad(eGrupo, "nombre");
		admin_id 	= servPersistencia.recuperarPropiedadEntidad(eGrupo, "admin");
		
		Grupo grupo = new Grupo(nombre);
		grupo.setId(codigo);
		
		// Añadir el grupo al pool antes de llamar a otros adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, grupo);
		
		// Recuperar propiedades que son objetos llamando a adaptadores
		/* Administrador */
		TDSUsuarioDAO adaptadorU = TDSUsuarioDAO.getUnicaInstancia();
		admin = adaptadorU.recuperarUsuario(Integer.parseInt(admin_id));
		
		grupo.setAdmin(admin);
		
		/* Miembros */
		miembros = obtenerMiembrosDesdeCodigos(
				servPersistencia.recuperarPropiedadEntidad(eGrupo, "miembros"));
		
		for (ContactoIndividual ci : miembros)
			grupo.addMiembro(ci);
		
		/* Grupos */
		m = obtenerMensajesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eGrupo, "mensajes"));
		for(Mensaje mensaje : m) {
			grupo.addMensaje(mensaje);
		}
		
		return grupo;
	}
	
	// ====================
	// FUNCIONES AUXILIARES
	// ====================

	public void borrarGrupo(Grupo grupo) {
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		servPersistencia.borrarEntidad(eGrupo);
	}

	public void modificarGrupo(Grupo grupo) {
		
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		
		String miembros = obtenerCodigosMiembros(grupo.getMiembros());
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "miembros");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "miembros", miembros);
		
		String mensajes = obtenerCodigosMensajes(grupo.getMensajes());
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "mensajes");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "mensajes", mensajes);
	}

	public List<Grupo> recuperarTodosGrupos() {
		
		List<Entidad> eGrupos = servPersistencia.recuperarEntidades("Grupo");
		List<Grupo> grupos = new LinkedList<Grupo>();
		
		for (Entidad eGrupo : eGrupos)
			grupos.add(recuperarGrupo(eGrupo.getId()));
		
		return grupos;
	}
	
	public List<ContactoIndividual> obtenerMiembrosDesdeCodigos(String miembros) {
		List<ContactoIndividual> listaMiembros = new LinkedList<ContactoIndividual>();
		
		if (miembros == null || miembros.equals(""))
			return listaMiembros;
		
		StringTokenizer st = new StringTokenizer(miembros, " ");
		
		TDSContactoIndividualDAO adaptadorC = TDSContactoIndividualDAO.getUnicaInstancia();
		
		while (st.hasMoreTokens())
			listaMiembros.add(adaptadorC.recuperarIndividual(Integer.valueOf((String) st.nextElement())));
		
		return listaMiembros;
	}
	
	public String obtenerCodigosMiembros(List<ContactoIndividual> miembros) {
		String aux = "";
		for (ContactoIndividual c : miembros) 
			aux += c.getId() +  " ";
		
		return aux.trim();
	}
	
	private String obtenerCodigosMensajes(List<Mensaje> mensajes) {
		String aux = "";
		for (Mensaje m : mensajes) {
			aux += m.getId() + " ";
		}
		return aux.trim();
	}
	
	private List<Mensaje> obtenerMensajesDesdeCodigos(String cMensajes) {
		List<Mensaje> listaMensajes = new LinkedList<Mensaje>();
		
		if (cMensajes == null || cMensajes.equals(""))
			return listaMensajes;
		
		StringTokenizer strTok = new StringTokenizer(cMensajes, " ");
		
		TDSMensajeDAO adaptadorM = TDSMensajeDAO.getUnicaInstancia();
		
		while (strTok.hasMoreTokens()) {
			listaMensajes.add(
					adaptadorM.recuperarMensaje(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaMensajes;
	}
}
