package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import dominio.ContactoIndividual;
import dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public final class TDSContactoIndividualDAO implements ContactoIndividualDAO {

	private ServicioPersistencia servPersistencia;
	private static TDSContactoIndividualDAO unicaInstancia = null;
	
	public static TDSContactoIndividualDAO getUnicaInstancia() {
		if (unicaInstancia == null)
			return new TDSContactoIndividualDAO();
		else
			return unicaInstancia;
	}
	
	public TDSContactoIndividualDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public ContactoIndividual recuperarIndividual(int codigo) {
		
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (ContactoIndividual) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		// Si no, la recupera de la base de datos
		Entidad eIndividual;
		String nombre;
		String telefono;
		String usuario_id;
		Usuario u;

		eIndividual = servPersistencia.recuperarEntidad(codigo);

		// Recuperar propiedad que no son objetos
		nombre = servPersistencia.recuperarPropiedadEntidad(eIndividual, "nombre");
		telefono = servPersistencia.recuperarPropiedadEntidad(eIndividual, "telefono");
		usuario_id = servPersistencia.recuperarPropiedadEntidad(eIndividual, "usuario");
		
		ContactoIndividual ci = new ContactoIndividual(nombre, telefono);
		ci.setId(codigo);
		
		// Añadir ContactoIndividual al pool antes de llamar a otros adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, ci);
		
		// Recuperar objeto Usuario llamando a su adaptador
		TDSUsuarioDAO adaptadorU = TDSUsuarioDAO.getUnicaInstancia();
		u = adaptadorU.recuperarUsuario(Integer.parseInt(usuario_id));
		
		ci.setUsuario(u);
		
		return ci;	
	}
	
	public void registrarIndividual(ContactoIndividual contInd) {
		Entidad eContInd;
		boolean existe = true;
		
		// Si la entidad está registrada no la registra de nuevo
		try {
			eContInd = servPersistencia.recuperarEntidad(contInd.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		
		if (existe)
			return;
		
		
		// Registrar primero los atributos que son objetos
		TDSUsuarioDAO adaptadorU = TDSUsuarioDAO.getUnicaInstancia();
		adaptadorU.registrarUsuario(contInd.getUsuario());
		
		eContInd = new Entidad();
		eContInd.setNombre("ContactoIndividual");
		eContInd.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("nombre", contInd.getNombre()),
						new Propiedad("telefono", contInd.getTelefono()),
						new Propiedad("usuario", String.valueOf(contInd.getUsuario().getId()))
						))
				);
		
		// Registrar entidad ContactoIndividual
		eContInd = servPersistencia.registrarEntidad(eContInd);
		// Asignar identificador único
		contInd.setId(eContInd.getId());
	}
	
	
	public void borrarIndividual(ContactoIndividual cInd) {
		Entidad eContInd = servPersistencia.recuperarEntidad(cInd.getId());
		servPersistencia.borrarEntidad(eContInd);
	}

	public List<ContactoIndividual> recuperarTodosIndividuales() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("ContactoIndividual");
		
		List<ContactoIndividual> contInd = new LinkedList<ContactoIndividual>();
		
		for (Entidad eContInd : entidades)
			contInd.add(recuperarIndividual(eContInd.getId()));
		
		return contInd;
	}
	
}
