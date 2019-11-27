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
	
	public void addMensaje(Mensaje mensaje) {
		this.mensajes.add(mensaje);
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	// Getters
	public String 			getNombre() 	{ return nombre; }
	public List<Mensaje> 	getMensajes() 	{ return new LinkedList<Mensaje>(mensajes);  }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mensajes == null) ? 0 : mensajes.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacto other = (Contacto) obj;
		if (mensajes == null) {
			if (other.mensajes != null)
				return false;
		} else if (!mensajes.equals(other.mensajes))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	
	
}
