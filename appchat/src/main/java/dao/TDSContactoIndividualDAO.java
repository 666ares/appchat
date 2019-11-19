package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import dominio.CatalogoUsuarios;
import dominio.ContactoIndividual;
import dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public final class TDSContactoIndividualDAO implements ContactoIndividualDAO {

private ServicioPersistencia servPersistencia;
	
	public TDSContactoIndividualDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	private ContactoIndividual entidadToContactoIndividual(Entidad eContInd) {
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContInd, "nombre");
		String telefono = servPersistencia.recuperarPropiedadEntidad(eContInd, "telefono");
		String usuario = servPersistencia.recuperarPropiedadEntidad(eContInd, "usuario");
		
		int id = Integer.parseInt(usuario);
		
		Usuario u = CatalogoUsuarios.getUnicaInstancia().getUsuario(id);

		ContactoIndividual ci = new ContactoIndividual(nombre, u, telefono);
		
		return ci;	
	}
	
	private Entidad ContactoIndividualToEntidad(ContactoIndividual contInd) {
		Entidad eContInd = new Entidad();
		eContInd.setNombre("ContactoIndividual");
	
		eContInd.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("nombre", contInd.getNombre()),
						new Propiedad("telefono", contInd.getTelefono()),
						new Propiedad("usuario", String.valueOf(contInd.getUsuario().getId()))
						))
				);
		
		return eContInd;
	}
	
	public ContactoIndividual get(int id) {
		Entidad eContInd = servPersistencia.recuperarEntidad(id);
		return entidadToContactoIndividual(eContInd);
	}
	
	public List<ContactoIndividual> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("ContactoIndividual");
		
		List<ContactoIndividual> contInd = new LinkedList<ContactoIndividual>();
		
		for (Entidad eContInd : entidades)
			contInd.add(get(eContInd.getId()));
		
		return contInd;
	}
	
	public void create(ContactoIndividual cInd) {
		Entidad eContInd = this.ContactoIndividualToEntidad(cInd);
		eContInd = servPersistencia.registrarEntidad(eContInd);
		cInd.setId(eContInd.getId());
	}
	
	public boolean delete(ContactoIndividual cInd) {
		Entidad eContInd;
		eContInd = servPersistencia.recuperarEntidad(cInd.getId());
		return servPersistencia.borrarEntidad(eContInd);
	}
	
}
