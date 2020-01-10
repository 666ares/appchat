package dominio;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	
	public boolean removeContacto(Contacto c) {
		return contactos.remove(c);
	}

	// Funciones
	public Integer[] getNumeroDeMensajesEnMeses() {
		
		Integer[] mensajes = new Integer[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		contactos.stream()
				 .flatMap(mset -> mset.getMensajes().stream())
				 .forEach(m -> {
					 int mes = m.getHora().getMonthValue();
					 mensajes[mes - 1]++;
				 });
		
		return mensajes;
	}
	
	// Grupos a los que más mensajes ha mandado el usuario
	public void getGruposMasActivos(PieChartGrupos chart) {
		
		HashMap<Grupo, Long> valoresGrupos = new HashMap<Grupo, Long>();
		
		// Obtenemos en el mapa cada uno de los grupos y el número de mensajes
		// que el usuario ha enviado a este
		contactos.stream()
				 .filter(c -> c instanceof Grupo)
				 .forEach(g -> { 
					 long sum = g.contarMisMensajes(nombre);
					 valoresGrupos.put((Grupo)g, sum);
				 });
		
		// Ordenamos el mapa según el número de mensajes enviado al grupo
		valoresGrupos.entrySet().stream()
								.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
								.collect(Collectors.toMap(Map.Entry::getKey,
														  Map.Entry::getValue,
														  (e1, e2) -> e2));
		
		// Nos quedamos con los 6 grupos a los que más mensajes ha enviado
		valoresGrupos.keySet().stream()
							  .limit(6)
							  .forEach(c -> chart.setSerie(c.getNombre(),
														   valoresGrupos.get(c)));
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
	public boolean  getPremium()			{ return premium; }
	
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
