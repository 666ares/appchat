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
