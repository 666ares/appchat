package dominio;

import java.time.LocalDate;

public class Mensaje {

	private int id;
	private Usuario emisor;
	private Contacto receptor;
	private String texto;
	private LocalDate hora;
	private String emoticono;
	
	public Mensaje(Usuario emisor, Contacto receptor, String texto) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.texto = texto;
		this.emoticono = "";
		this.hora = LocalDate.now();
	}
	
	public Mensaje(String emoticono, Usuario emisor, Contacto receptor) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.emoticono = emoticono;
		this.texto = "";
		this.hora = LocalDate.now();
	}

	// Devuelve 'true' si el nombre del emisor del mensaje coincide
	// con el nombre pasado como parámetro
	public boolean buscarPorNombre(String nombre) {
		return this.getEmisor().getNombre().equals(nombre);
	}
	
	// Devuelve 'true' si el mensaje sobre el que se llama el método contiene
	// el texto pasado como parámetro
	public boolean contieneTexto(String texto) {
		return this.texto.contains(texto);
	}
	
	// Devuelve 'true' si la fecha del mensaje coincide con el límite superior
	// o inferior, o si la fecha se encuentra entre el rango [inferior, superior]
	public boolean estaEntreFechas(LocalDate inferior, LocalDate superior) {
		if (this.hora.isAfter(inferior) && this.hora.isBefore(superior)) 
			return true;
		
		if (this.hora.equals(inferior) || this.hora.equals(superior)) 
			return true;
		
		return false;
	}
	
	// Getters
	public int			getId()			{ return id; }
	public Usuario 		getEmisor()		{ return emisor; }
	public Contacto 	getReceptor()	{ return receptor; }
	public String 		getTexto() 		{ return texto; }
	public LocalDate 	getHora() 		{ return hora; }
	public String 		getEmoticono() 	{ return emoticono; }
	
	// Setters
	public void	setId(int id)			       { this.id = id; }
	public void	setEmisor(Usuario usuario)     { this.emisor = usuario; }
	public void setReceptor(Contacto contacto) { this.receptor = contacto; }
	public void setHora(LocalDate hora)		   { this.hora = hora; }
	
}
