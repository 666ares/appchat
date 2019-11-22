package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Mensaje;
import dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public final class TDSMensajeDAO implements MensajeDAO {

	private static ServicioPersistencia servPersistencia;
	private static TDSMensajeDAO unicaInstancia = null;
	
	// Patrón Singleton
	public static TDSMensajeDAO getUnicaInstancia() {
		if (unicaInstancia == null)
			return new TDSMensajeDAO();
		else
			return unicaInstancia;
	}
	
	public TDSMensajeDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	

	public void registrarMensaje(Mensaje mensaje) {
		
		Entidad eMensaje;
		boolean existe = true;
		
		// Si la entidad está registrada no la registra de nuevo
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		} catch (NullPointerException e) {
			existe= false;
		}
		
		if (existe)
			return;
		
		// Registrar primero los atributos que son objetos
		// Receptor (tipo Contacto)
		Contacto c = mensaje.getReceptor();
		if (c instanceof ContactoIndividual) {
			TDSContactoIndividualDAO adaptadorC = TDSContactoIndividualDAO.getUnicaInstancia();
			ContactoIndividual ci = (ContactoIndividual) c;
			adaptadorC.registrarIndividual(ci);
		}
		else if (c instanceof Grupo) {
			TDSGrupoDAO adaptadorG = TDSGrupoDAO.getUnicaInstancia();
			Grupo g = (Grupo) c;
			adaptadorG.registrarGrupo(g);
		}
		
		// Emisor (tipo Usuario)
		TDSUsuarioDAO adaptadorU = TDSUsuarioDAO.getUnicaInstancia();
		adaptadorU.registrarUsuario(mensaje.getEmisor());
		
		eMensaje = new Entidad();
		eMensaje.setNombre("Mensaje");
		
		int id = 0;
		String tipo = "";
		Contacto contacto = mensaje.getReceptor();
		if(contacto instanceof ContactoIndividual) {
			id = ((ContactoIndividual) contacto).getId();
			tipo = "ContactoIndividual";
		} else if(contacto instanceof Grupo) {
			id = ((Grupo) contacto).getId();
			tipo = "Grupo";
		}
		
		eMensaje.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("texto", 		mensaje.getTexto()),
						new Propiedad("emoticono", 	mensaje.getEmoticono()),
						new Propiedad("receptor", 	String.valueOf(id)),
						new Propiedad("tipo",       tipo),
						new Propiedad("emisor", 	String.valueOf(mensaje.getEmisor().getId())),
						new Propiedad("hora", 		mensaje.getHora().toString()) )
						)
				);
		
		// Registrar entidad Menskae
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		// Asignar identificador único
		mensaje.setId(eMensaje.getId());
	}

	public Mensaje recuperarMensaje(int codigo) {
		
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Mensaje) PoolDAO.getUnicaInstancia().getObjeto(codigo);
			
		Entidad eMensaje;
		String texto;
		String emoticono;
		String tipo;
		String hora;
		
		eMensaje = servPersistencia.recuperarEntidad(codigo);
		
		texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		emoticono = servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono");
		tipo = servPersistencia.recuperarPropiedadEntidad(eMensaje, "tipo");
		hora = servPersistencia.recuperarPropiedadEntidad(eMensaje, "hora");
		
		Mensaje mensaje;
		if(texto == "") {
			mensaje = new Mensaje(emoticono, null, null);
		} else {
			mensaje = new Mensaje(texto, null, null);
		}
		mensaje.setId(codigo);
		
		PoolDAO.getUnicaInstancia().addObjeto(codigo, mensaje);
		
		String receptorID = servPersistencia.recuperarPropiedadEntidad(eMensaje, "receptor");
		String emisorID = servPersistencia.recuperarPropiedadEntidad(eMensaje, "emisor");
		
		if(tipo.equals("ContactoIndividual")) {
			TDSContactoIndividualDAO  adaptadorC = TDSContactoIndividualDAO.getUnicaInstancia();
			ContactoIndividual ci = adaptadorC.recuperarIndividual(Integer.parseInt(receptorID));
			mensaje.setReceptor(ci);
		} else if(tipo.equals("Grupo")) {
			TDSGrupoDAO adapatadorG = TDSGrupoDAO.getUnicaInstancia();
			Grupo grupo = adapatadorG.recuperarGrupo(Integer.parseInt(receptorID));
			mensaje.setReceptor(grupo);
		}
		
		TDSUsuarioDAO adaptadorU = TDSUsuarioDAO.getUnicaInstancia();
		Usuario u = adaptadorU.recuperarUsuario(Integer.parseInt(emisorID));
		mensaje.setEmisor(u);
		
		mensaje.setHora(LocalDate.parse(hora));
		
		return mensaje;
		
	}

	public List<Mensaje> recuperarTodosMensajes() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Mensaje");
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		
		for (Entidad eMensaje : entidades)
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		
		return mensajes;
	}

}
