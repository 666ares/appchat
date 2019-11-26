/*
 * El usuario puede ser Francisco Lopez Martinez, 
 * pero yo puedo tenerlo en mis contactos como "Paco".
 * Otra persona podría tener también como contacto
 * a la misma persona pero con otro nombre de contacto:
 * "Francisco" o "Paquillo", otro como "Fontanero", etc..
 * 
 * Cuando se registra el usuario lo hace con su nombre real.
 * Un mismo usuario puede aparecer como distintos contactos
 * en otros usuarios del AppChat
 */

package dominio;

import controlador.ControladorUsuarios;

public class ContactoIndividual extends Contacto {

	private int id;				/* Identificador único para BBDD */
	private Usuario usuario;	/* Usuario asociado al número y teléfono */
	private String telefono;	/* teléfono del usuario */
	
	public ContactoIndividual(String nombre, String telefono) {
		super(nombre);
		this.telefono = telefono;
		this.usuario = ControladorUsuarios.getUnicaInstancia()._buscarUsuario(telefono);
	}

	// Getters
	public int 		getId()			{ return id; }
	public Usuario 	getUsuario() 	{ return usuario; }
	public String 	getTelefono() 	{ return telefono; }
	
	// Setters
	public void 	setId(int id) 			{ this.id = id; }
	public void		setUsuario(Usuario u) 	{ this.usuario = u; }
}
