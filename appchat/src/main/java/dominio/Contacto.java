package dominio;

import java.util.LinkedList;
import java.util.List;

public abstract class Contacto {

	private String nombre;
	private List<Mensaje> mensajes;

	public Contacto(String nombre) {
		this.nombre = nombre;
		this.mensajes = new LinkedList<Mensaje>();
	}
	
	public String getNombre() { 
		return nombre; 
	}
	
	public List<Mensaje> getMensajes() { 
		return new LinkedList<Mensaje>(mensajes); 
	}
	
	
}
