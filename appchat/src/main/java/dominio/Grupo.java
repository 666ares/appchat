package dominio;

import java.util.LinkedList;
import java.util.List;

import controlador.ControladorUsuarios;

public class Grupo extends Contacto {

	private Usuario admin;
	private List<ContactoIndividual> miembros;
	
	public Grupo(String nombre) {
		super(nombre);
		this.miembros = new LinkedList<ContactoIndividual>();
		this.admin = ControladorUsuarios.getUnicaInstancia().getUsuarioActual();
	}
	
	public Usuario getAdmin() { return admin; }
	
	public List<ContactoIndividual> getMiembros() {
		return new LinkedList<ContactoIndividual>(miembros);
	}

}
