package dominio;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import controlador.ControladorUsuarios;

public class Grupo extends Contacto {

	private int id;
	private Usuario admin;
	private List<ContactoIndividual> miembros;
	
	public Grupo(String nombre) {
		super(nombre);
		this.miembros = new LinkedList<ContactoIndividual>();
		this.admin = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
	}
	
	public boolean addMiembro(ContactoIndividual ci) { 
		if (!miembros.contains(ci)) {
			miembros.add(ci);
			return true;
		}
		return false;
	}
	
	public boolean removeMiembro(ContactoIndividual ci) {
		return miembros.remove(ci);
	}
	
	public int 		getId()		{ return id; }
	public Usuario 	getAdmin() 	{ return admin; }
	
	public List<ContactoIndividual> getMiembros() 	{ 
		return new LinkedList<ContactoIndividual>(miembros); 
	}
	
	public List<String> getNombresMiembros() {
		List<String> lista;
		
		lista = miembros.stream()
						.map(ContactoIndividual::getNombre)
						.collect(Collectors.toList());
		
		if (admin != null)
			lista.add(admin.getNombre());
		return lista;
	}
	
	// Setters
	public void setId(int id)		{ this.id = id; }
	public void	setAdmin(Usuario u)	{ this.admin = u; }

}
