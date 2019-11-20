package dominio;

import java.util.LinkedList;
import java.util.List;

public class Usuario {

	private int id;
	private String nombre;
	private String fechaNacimiento;
	private String email;
	private String telefono;
	private String login;
	private String password;
	private String imagenPerfil;
	private String saludo;
	private List<Contacto> contactos;
	private List<Grupo> gruposAdmin;
	
	// Constructor
	public Usuario(String nombre, String fechaNacimiento, String email, 
					String telefono, String login, String password,
					String imagenPerfil, String saludo) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.telefono = telefono;
		this.login = login;
		this.password = password;
		this.imagenPerfil = imagenPerfil;
		this.saludo = saludo;
		this.contactos = new LinkedList<Contacto>();
		this.gruposAdmin = new LinkedList<Grupo>();
	}
	
	// Permite añadir un contacto a la lista de contactos del usuario
	public boolean añadirUsuario(Contacto c) {
		if (!contactos.contains(c)) {
			contactos.add(c);
			return true;
		}
		return false;
	}
	
	// Getters
	public int 			getId() 				{ return id; }
	public String 			getSaludo() 			{ return saludo; }
	public String 			getImagenPerfil() 		{ return imagenPerfil; }
	public String 			getNombre() 			{ return nombre; }
	public String 			getFechaNacimiento() 	{ return fechaNacimiento; }	
	public String 			getEmail() 				{ return email; }
	public String 			getTelefono() 			{ return telefono; }
	public String 			getLogin() 				{ return login; }
	public String 			getPassword() 			{ return password; }
	public List<Contacto>	getContactos()			{ return new LinkedList<Contacto>(contactos); }
	public List<Grupo> 		getGruposAdmin() 		{ return new LinkedList<Grupo>(gruposAdmin); }
	
	// Setters
	public void setSaludo(String saludo) 		{ this.saludo = saludo; }
	public void setImagenPerfil(String im) 	{ this.imagenPerfil = im; }
	public void setId(int id) 				 	{ this.id = id; }
	public void setNombre(String nombre) 	 	{ this.nombre = nombre; }
	public void setFechaNacimiento(String fn)	{ this.fechaNacimiento = fn; }
	public void setEmail(String email) 		{ this.email = email; }
	public void setTelefono(String telefono) 	{ this.telefono = telefono; }
	public void setLogin(String login) 	 	{ this.login = login; }
	public void setPassword(String password) 	{ this.password = password; }
}
