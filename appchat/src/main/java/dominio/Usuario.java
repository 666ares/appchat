package dominio;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

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
	private Descuento descuento;
	
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
		
		Integer[] mensajes = new Integer[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		contactos.stream()
				 .flatMap(mset -> mset.getMensajes().stream())
				 .forEach(m -> {
					 int mes = m.getHora().getMonthValue();
					 mensajes[mes - 1]++;
				 });
		
		return mensajes;
	}
	
	// Número de mensajes que ha mandado el usuario
	public int getNumMensajes() {
		
		long numMensajes 
			= contactos.stream()
					   .flatMap(mset -> mset.getMensajes().stream())
					   .filter(m -> m.getEmisor().getNombre().equals(nombre))
					   .count();
		
		return (int) numMensajes;

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
	
	public double getDescuento() {
		
		// Comprobar que cuando el usuario se registró introdujo una fecha
		// y no dejo el campo en blanco
		if (!fechaNacimiento.equals("")) {
			
			String[] fecha = fechaNacimiento.split(" ");
			String age = fecha[fecha.length - 1];
			
			int age2 = Integer.parseInt(age);
			int ahora = LocalDate.now().getYear();
			int rangoInf = ahora - 22;
			int rangoSup = ahora - 12;
			
			// Si el usuario tiene entre 12 y 22 se aplica descuento de estudiante
			if (age2 >= rangoInf && age2 <= rangoSup)
				descuento = new DescuentoEstudiante();
			
		}
		else
			descuento = new DescuentoFijo();
		
		return descuento.calcDescuento(this);
	}
	
	public DefaultListModel<String> obtenerNombreContactos() {
		DefaultListModel<String> listmodel = new DefaultListModel<String>();
		for(Contacto contacto : contactos) {
			if(contacto instanceof ContactoIndividual)
				listmodel.addElement(contacto.getNombre());
		}
		return listmodel;
	}
	
	public void addContactosGrupo(DefaultListModel<String> contactosGrupo, Grupo g) {
		for(Contacto contacto : contactos) {
			if(contactosGrupo.contains(contacto.getNombre())) {
				if(contacto instanceof ContactoIndividual) {
					ContactoIndividual cInd = (ContactoIndividual)contacto;
					g.addMiembro(cInd);
				}
			}
		}
	}
	
	public Grupo comprobarGrupo(String nombre) {
		for(Contacto contacto : contactos) {
			if(contacto instanceof Grupo) {
				if(contacto.getNombre().equals(nombre) 
				   && ((Grupo)contacto).getAdmin().equals(this)) {
					  return (Grupo) contacto; 
				   }
			}
		}
		return null;
	}
	
	public void actualizarDatosGrupo(List<ContactoIndividual> contEnGrupo, 
									 DefaultListModel<String> contactosGrupo, 
			                         DefaultListModel<String> listmodel) {
		for(Contacto contacto : contactos) {
			if(contacto instanceof ContactoIndividual) {
				if(contEnGrupo.contains(contacto))
					contactosGrupo.addElement(contacto.getNombre());
				else
					listmodel.addElement(contacto.getNombre());
			}
		}
	}
	
	public void modificarGrupo(Grupo g, DefaultListModel<String> anadidos, 
			                   DefaultListModel<String> eliminados) {
		for(Contacto contacto : contactos) {
			if (anadidos.contains(contacto.getNombre())) {
				if (contacto instanceof ContactoIndividual) {
					g.addMiembro((ContactoIndividual) contacto);
				}
			} else if (eliminados.contains(contacto.getNombre())) {
				if (contacto instanceof ContactoIndividual) {
					g.removeMiembro((ContactoIndividual) contacto);
				}
			}
		}
	}
	
	public void crearDocumento(Document documento) {
		for(Contacto contacto : contactos) {
			if(contacto instanceof ContactoIndividual) {
				//Guardar el nombre y telefono en el pdf
				String info = "<Contacto: " 
				              + contacto.getNombre() 
							  + ", Telefono: " 
				              + ((ContactoIndividual) contacto).getTelefono()
				              + ">";
				try {
					documento.add(new Paragraph(info));
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			} else if(contacto instanceof Grupo) {
				String grupo = "<Grupo: " + contacto.getNombre();
				try {
					documento.add(new Paragraph(grupo));
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				List<ContactoIndividual> contactosG = ((Grupo) contacto).getMiembros();
				String ultimo = contactosG.get(contactosG.size()-1).getNombre();
				for(ContactoIndividual miembro : contactosG) {
					String info = "* Miembro: "
								  + miembro.getNombre()
								  + " , Telefono: "
								  + miembro.getTelefono();
					if(miembro.getNombre().equals(ultimo)) info = info + ">";
					try {
						documento.add(new Paragraph(info));
					} catch (DocumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public ContactoIndividual buscarContacto(String tlf) {
		for(Contacto contacto : contactos) {
			if(contacto instanceof ContactoIndividual) {
				if(((ContactoIndividual) contacto).getTelefono().equals(tlf)) {
					return (ContactoIndividual)contacto;
				}
			}
		}
		return null;
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
	public void setPremium(boolean premium)		{ this.premium = premium; }
}
