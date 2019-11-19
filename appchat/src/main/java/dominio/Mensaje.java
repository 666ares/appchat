package dominio;

import java.time.LocalDate;

import javax.swing.ImageIcon;

public class Mensaje {

	private Usuario emisor;
	private Contacto receptor;
	private String texto;
	private LocalDate hora;
	private ImageIcon emoticono;
	
	public Mensaje(Usuario emisor, Contacto receptor, String texto) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.texto = texto;
		this.hora = LocalDate.now();
	}
	
	public Mensaje(Usuario emisor, Contacto receptor, ImageIcon emoticono) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.emoticono = emoticono;
		this.hora = LocalDate.now();
	}

	public Usuario 		getEmisor()		{ return emisor; }
	public Contacto 	getReceptor()	{ return receptor; }
	public String 		getTexto() 		{ return texto; }
	public LocalDate 	getHora() 		{ return hora; }
	public ImageIcon 	getEmoticono() 	{ return emoticono; }
	
}
