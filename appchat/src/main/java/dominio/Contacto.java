package dominio;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public abstract class Contacto {

	private String nombre;
	private List<Mensaje> mensajes;

	public Contacto(String nombre) {
		this.nombre = nombre;
		this.mensajes = new LinkedList<Mensaje>();
	}
	
	// Devuelve una lista con los mensajes comprendidos entre las fechas
	// 'inf' y 'sup'
	public List<Mensaje> mensajesEntreFechas(LocalDate inf, LocalDate sup) {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		
		for (Mensaje m : this.mensajes)
			if (m.estaEntreFechas(inf, sup))
				mensajes.add(m);
		
		return mensajes;
	}
	
	// Devuelve una lista con los mensajes que contienen la cadena 'texto'
	public List<Mensaje> mensajesPorTexto(String texto) {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		
		for (Mensaje m : this.mensajes)
			if (m.contieneTexto(texto))
				mensajes.add(m);
		
		return mensajes;
	}
	
	// Devuelve una lista con los mensajes cuyo emisor tiene como 
	// nombre 'nombre'
	public List<Mensaje> mensajesPorUsuario(String nombre) {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		
		for (Mensaje m : this.mensajes)
			if (m.buscarPorNombre(nombre))
				mensajes.add(m);
		
		return mensajes;
	}
	
	// Permite añadir un nuevo mensaje a la colección
	public void addMensaje(Mensaje mensaje) {
		this.mensajes.add(mensaje);
	}
	
	public int contarMisMensajes(String nombre) {
		int cuenta = 0;
		for(Mensaje mensaje : mensajes) {
			if(mensaje.getEmisor().getNombre().equals(nombre)) {
				cuenta++;
			}
		}
		return cuenta;
	}
	
	public void resetearChat() {
		this.mensajes = new LinkedList<Mensaje>();
	}
	
	// Setters
	public void setNombre(String nombre) { this.nombre = nombre; }
	
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
