package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Mensaje;
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
		
		eMensaje.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("texto", 		mensaje.getTexto()),
						new Propiedad("emoticono", 	mensaje.getEmoticono()),
						// new Propiedad("receptor", 	String.valueOf(mensaje.getReceptor().getId())),
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
		return null;
		
	}

	public List<Mensaje> recuperarTodosMensajes() {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

}
