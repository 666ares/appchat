package dominio;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

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
		
		List<Mensaje> lista = mensajes.stream()
									  .filter(m -> m.estaEntreFechas(inf, sup))
									  .collect(Collectors.toList());
		return lista;
	}
	
	// Devuelve una lista con los mensajes que contienen la cadena 'texto'
	public List<Mensaje> mensajesPorTexto(String texto) {
		
		List<Mensaje> lista = mensajes.stream()
									  .filter(m -> m.contieneTexto(texto))
									  .collect(Collectors.toList());

		return lista;
	}
	
	// Devuelve una lista con los mensajes cuyo emisor tiene como 
	// nombre 'nombre'
	public List<Mensaje> mensajesPorUsuario(String nombre) {
		
		List<Mensaje> lista = mensajes.stream()
									  .filter(m -> m.buscarPorNombre(nombre))
									  .collect(Collectors.toList());
		
		return lista;
	}
	
	// Permite añadir un nuevo mensaje a la colección
	public void addMensaje(Mensaje mensaje) {
		this.mensajes.add(mensaje);
	}
	
	public long contarMisMensajes(String nombre) {
		
		long num = mensajes.stream()
					   	   .filter(m -> m.getEmisor().getNombre().equals(nombre))
					   	   .count();
		
		return num;
	}
	
	public void resetearChat() {
		this.mensajes = new LinkedList<Mensaje>();
	}
	
	public List<Mensaje> buscadorMensajes(String nombre, JDateChooser fecha1, 
			                              JDateChooser fecha2, JTextField busqueda) {
		List<Mensaje> resultadoBusqueda = new LinkedList<Mensaje>();
		List<Mensaje> resultadoBusqueda2 = new LinkedList<Mensaje>();
		List<Mensaje> resultadoBusqueda3 = new LinkedList<Mensaje>();
		
		if(this instanceof Grupo && !nombre.equals("")) {
			for (Mensaje msj : mensajes)
				if (msj.getEmisor().getNombre().equals(nombre))
					resultadoBusqueda3.add(msj);
		} else resultadoBusqueda3 = mensajes;
		
		if (fecha1.getDate() != null && fecha2.getDate() != null) {
			Date date1 = fecha1.getDate();
			Date date2 = fecha2.getDate();
			for (Mensaje msj : resultadoBusqueda3) {
				// Trasnformamos LocalDate a Date
				Date dateMsj = Date.from(msj.getHora().atStartOfDay(ZoneId.systemDefault()).toInstant());
				// Comprobamos que el mensaje se encuentre entre la fecha1 y la fecha2
				if(dateMsj.after(date1) && dateMsj.before(date2)) {
					resultadoBusqueda.add(msj);
				}
			}
		} else // Copiar la lista entera
			resultadoBusqueda = mensajes;
			
		String textBusqueda = busqueda.getText();
		busqueda.setText("");
		if(!textBusqueda.equals("")) {
			//Obtener lista de mensajes con el contacto actual
			for(Mensaje mensaje : resultadoBusqueda)
			{
				String msj = mensaje.getTexto();
				if(!msj.equals("") && msj.contains(textBusqueda)) {
					resultadoBusqueda2.add(mensaje);
				}
			}
			resultadoBusqueda = resultadoBusqueda2;
		} 
		
		return resultadoBusqueda;
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
