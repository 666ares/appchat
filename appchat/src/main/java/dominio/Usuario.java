package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import graficos.PieChartGrupos;

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
	private boolean premium;
	private List<Contacto> contactos;
	
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
	}
	
	// Permite añadir un contacto a la lista de contactos del usuario
	public boolean addContacto(Contacto c) {
		if (!contactos.contains(c)) {
			contactos.add(c);
			return true;
		}
		return false;
	}
	
	// Funciones
	public Integer[] getNumeroDeMensajesEnMeses() {
		Integer[] mensajes = new Integer[] {0,0,0,0,0,0,0,0,0,0,0,0};
		// TODO Cambiar a Java8
		for(Contacto contacto : contactos) {
			List<Mensaje> listaMsj = contacto.getMensajes();
			for(Mensaje msj : listaMsj) {
				int mes = msj.getHora().getMonthValue();
				mensajes[mes-1]++;
			}
		}
		return mensajes;
	}
	
	public void getGruposMasActivos(PieChartGrupos chart) {
		HashMap<Grupo, Integer> valoresGrupos = new HashMap<Grupo, Integer>();
		for(Contacto contacto : contactos) {
			if(contacto instanceof Grupo) {
				int nMen = contacto.contarMisMensajes(nombre);
				valoresGrupos.put((Grupo)contacto, nMen);
			}
		}
		
		int index = 0;
		for(Grupo g : valoresGrupos.keySet()) {
			if(index == 6) break;
			chart.setSerie(g.getNombre(), valoresGrupos.get(g));
		}
	}
	
	// Getters
	public int 		getId() 				{ return id; }
	public String 	getSaludo() 			{ return saludo; }
	public String 	getImagenPerfil() 		{ return imagenPerfil; }
	public String 	getNombre() 			{ return nombre; }
	public String 	getFechaNacimiento() 	{ return fechaNacimiento; }	
	public String 	getEmail() 				{ return email; }
	public String 	getTelefono() 			{ return telefono; }
	public String 	getLogin() 				{ return login; }
	public String 	getPassword() 			{ return password; }
	public Boolean  getPremium()			{ return premium; }
	
	public List<Contacto> getContactos() { 
		return new LinkedList<Contacto>(contactos); 
	}
	
	// Setters
	public void setSaludo(String saludo) 		{ this.saludo = saludo; }
	public void setImagenPerfil(String im) 		{ this.imagenPerfil = im; }
	public void setId(int id) 				 	{ this.id = id; }
	public void setNombre(String nombre) 	 	{ this.nombre = nombre; }
	public void setFechaNacimiento(String fn)	{ this.fechaNacimiento = fn; }
	public void setEmail(String email) 			{ this.email = email; }
	public void setTelefono(String telefono) 	{ this.telefono = telefono; }
	public void setLogin(String login) 	 		{ this.login = login; }
	public void setPassword(String password) 	{ this.password = password; }
	public void setPremium(Boolean premium)		{ this.premium = premium; }
}
