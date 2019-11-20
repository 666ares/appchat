package dominio;

public class ContactoIndividual extends Contacto {

	private int id;
	private Usuario usuario;
	private String telefono;
	
	public ContactoIndividual(String nombre, Usuario usuario,
							  String telefono) {
		super(nombre);
		this.telefono = telefono;
		this.usuario = usuario;
	}

	// Getters
	public int 		getId()			{ return id; }
	public Usuario 	getUsuario() 	{ return usuario; }
	public String 	getTelefono() 	{ return telefono; }
	
	// Setters
	public void 	setId(int id) 	{ this.id = id; }
}
