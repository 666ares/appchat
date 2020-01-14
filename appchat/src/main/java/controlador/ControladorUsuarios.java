package controlador;

import java.awt.Image;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import com.itextpdf.text.Document;

import componente.CargadorMensajes;
import componente.MensajeEvent;
import componente.MensajeListener;
import dao.ContactoIndividualDAO;
import dao.DAOException;
import dao.FactoriaDAO;
import dao.GrupoDAO;
import dao.MensajeDAO;
import dao.UsuarioDAO;
import dominio.CatalogoUsuarios;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Mensaje;
import dominio.Usuario;
import modelo.MensajeWhatsApp;

public class ControladorUsuarios implements MensajeListener {

	private static ControladorUsuarios unicaInstancia;
	
	private UsuarioDAO 				adaptadorUsuario;
	private ContactoIndividualDAO 	adaptadorIndividual;
	private GrupoDAO 				adaptadorGrupo;
	private CatalogoUsuarios 		catalogoUsuarios;
	private MensajeDAO				adaptadorMensaje;
	private Usuario 				usuarioActual;
	
	private ControladorUsuarios() {
		inicializarAdaptadores();
		inicializarCatalogo();
	}
	
	public static ControladorUsuarios getUnicaInstancia() {
		if (unicaInstancia == null) 
			unicaInstancia = new ControladorUsuarios();
		return unicaInstancia;
	}
	
	public boolean registrarUsuario(String nombre, String fechaNacimiento,
		     						String email, String telefono, 
		     						String login, String password,
		     						String imagenPerfil, String saludo) {
		
		if (esUsuarioRegistrado(login)) 
			return false;

		Usuario usuario = new Usuario(nombre, fechaNacimiento, email,
									  telefono, login, password,
									  imagenPerfil, saludo);
		
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		return true;
	}
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public boolean esUsuarioRegistrado(String login) {
		return catalogoUsuarios.getUsuario(login) != null;
	}
	
	public boolean loginUsuario(String login, String password) {
		Usuario usuario = catalogoUsuarios.getUsuario(login);
		
		if (usuario != null && usuario.getPassword().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}
	
	
	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getLogin())) 
			return false;
		
		adaptadorUsuario.borrarUsuario(usuario);
		catalogoUsuarios.removeUsuario(usuario);
		return true;
	}
	
	public Usuario buscarUsuario(String login) {
		if (!esUsuarioRegistrado(login))
			return null;
		
		return catalogoUsuarios.getUsuario(login);
	}
	
	public Usuario _buscarUsuario(String telefono) {
		return catalogoUsuarios._getUsuario(telefono);
	}
	
	public List<Contacto> obtenerContactos(String login) {
		Usuario usuario = catalogoUsuarios.getUsuario(login);
		
		List<Contacto> contactos = usuario.getContactos();
		
		return contactos;
	}
	
	public List<ContactoIndividual> obtenerIndividuales(List<Contacto> contactos) {
		
		List<ContactoIndividual> individuales = new LinkedList<ContactoIndividual>();
		
		for (Contacto c : contactos)
			if (c instanceof ContactoIndividual) {
				ContactoIndividual ci = (ContactoIndividual) c;
				individuales.add(ci);
			}
		return individuales;
	}
	
	public List<Grupo> obtenerGrupos(List<Contacto> contactos) {
		
		List<Grupo> grupos = new LinkedList<Grupo>();
		
		for (Contacto c : contactos)
			if (c instanceof Grupo) {
				Grupo g = (Grupo) c;
				grupos.add(g);
			}
		
		return grupos;
	}
	
	public Object[][] contactosATabla(List<Contacto> contactos) {

		List<ContactoIndividual> individuales = obtenerIndividuales(contactos);
		// List<Grupo> grupos = obtenerGrupos(contactos);
		
		Object[][] datos = new Object[individuales.size()][4];
		
		Comparator<Contacto> ordenarPorNombre 
			= (Contacto c1, Contacto c2) -> c1.getNombre().compareTo(c2.getNombre());
			
		Collections.sort(contactos, ordenarPorNombre);
		
		int index = 0;

		for (ContactoIndividual ci : individuales) {
			
			datos[index][0] = ci.getNombre();
				
			ImageIcon icon = new ImageIcon(ci.getUsuario().getImagenPerfil());
			Image imageIcon = icon.getImage();
			Image newImage = imageIcon.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
			ImageIcon icon2 = new ImageIcon(newImage);
				
			datos[index][1] = icon2;
			datos[index][2] = " " + ci.getTelefono();
				
			index++;
		}
		
		return datos;
	}
	
	public boolean añadirContacto(String login, Contacto c) {
		
		Usuario usuario = catalogoUsuarios.getUsuario(login);
		
		if (usuario.addContacto(c)) {
			
			if (c instanceof ContactoIndividual) {
				ContactoIndividual cInd = (ContactoIndividual) c;
				adaptadorIndividual.registrarIndividual(cInd);
			}
			else if (c instanceof Grupo) {
				Grupo g = (Grupo) c;
				adaptadorGrupo.registrarGrupo(g);
			}
			adaptadorUsuario.modificarUsuario(usuario);
			catalogoUsuarios.updateUsuario(usuario);
			return true;
		}
		return false;
	}
	
	public void enviarMensaje(Mensaje mensaje) {
		Contacto contacto = mensaje.getReceptor();
		
		if (contacto instanceof ContactoIndividual) {
			ContactoIndividual cInd = (ContactoIndividual) contacto;
			cInd.addMensaje(mensaje);
			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorIndividual.modificarIndividual(cInd);
		}
		else if (contacto instanceof Grupo) {
			Grupo g = (Grupo) contacto;
			g.addMensaje(mensaje);
			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorGrupo.modificarGrupo(g);
		}
	}
	
	public boolean eliminarContacto(String login, Contacto contacto) {
		
		Usuario usuario = catalogoUsuarios.getUsuario(login);
		
		if (usuario.getContactos().contains(contacto)) {
			
			// Si el contacto es un grupo del que el usuario es administrador,
			// el grupo se queda sin administrador
			if (contacto instanceof Grupo) {
				Grupo g = (Grupo) contacto;
				g.setAdmin(null);
				adaptadorGrupo.modificarGrupo(g);
			}
			
			usuario.removeContacto(contacto);
			adaptadorUsuario.modificarUsuario(usuario);
			catalogoUsuarios.updateUsuario(usuario);
			return true;
		} 
		return false;
	}
	
	public void recibirMensaje(Mensaje mensaje, Contacto contacto) {
		
		if (contacto instanceof ContactoIndividual) {
			ContactoIndividual cInd = (ContactoIndividual) contacto;
			cInd.addMensaje(mensaje);
			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorIndividual.modificarIndividual(cInd);
		} 
		else if (contacto instanceof Grupo) {
			Grupo g = (Grupo) contacto;
			g.addMensaje(mensaje);
			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorGrupo.modificarGrupo(g);
		}
		
	}
	
	public void mandarMensajeEmoji(int numEmoji, Contacto contacto) {
		Mensaje mensaje = new Mensaje(Integer.toString(numEmoji), usuarioActual, contacto);

		String tlf = "";
		if (contacto instanceof ContactoIndividual) {
			tlf = ((ContactoIndividual) contacto).getTelefono();

			Usuario receptor = _buscarUsuario(tlf);

			ContactoIndividual ci2 = null;
			
			ci2 = receptor.buscarContacto(usuarioActual.getTelefono());
			// Si no lo tiene crear un contacto con el numero de telefono de usuario Act
			if (ci2 == null) {
				ci2 = new ContactoIndividual(usuarioActual.getTelefono(), usuarioActual.getTelefono());
				añadirContacto(receptor.getLogin(), ci2);
			}

			// Añadir mensaje a la base de datos
			enviarMensaje(mensaje);
			recibirMensaje(mensaje, ci2);
		} else if (contacto instanceof Grupo) {
			enviarMensaje(mensaje);
		}
	}
	
	public void mandarMensajeTexto(String texto, Contacto contacto) {
		Mensaje mensaje = new Mensaje(usuarioActual, contacto, texto);

		String tlf = "";
		
		if (contacto instanceof ContactoIndividual) {
			tlf = ((ContactoIndividual) contacto).getTelefono();

			Usuario receptor = _buscarUsuario(tlf);

			ContactoIndividual ci2 = null;
			
			ci2 = receptor.buscarContacto(usuarioActual.getTelefono());
			// Si no lo tiene crear un contacto con el numero de telefono de usuario Act
			if (ci2 == null) {
				ci2 = new ContactoIndividual(usuarioActual.getTelefono(), usuarioActual.getTelefono());
				añadirContacto(receptor.getLogin(), ci2);
			}

			// Añadir mensaje a la base de datos
			enviarMensaje(mensaje);
			recibirMensaje(mensaje, ci2);
		} 
		else if (contacto instanceof Grupo)
			enviarMensaje(mensaje);
	}
	
	public boolean updateIndividual(ContactoIndividual contacto) {
		adaptadorIndividual.modificarIndividual(contacto);
		return true;
	}
	
	public boolean updateGrupo(Grupo grupo) {
		adaptadorGrupo.modificarGrupo(grupo);
		return true;
	}
	
	public boolean updateUsuario(Usuario usuario) {
		adaptadorUsuario.modificarUsuario(usuario);
		catalogoUsuarios.updateUsuario(usuario);
		return true;
	}
	
	public void importarMensajes(String ruta, String formato) {
		CargadorMensajes cargador = new CargadorMensajes();
		cargador.addMensajesListener(ControladorUsuarios.getUnicaInstancia());
		cargador.setFichero(ruta, formato);	
	}
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorIndividual = factoria.getContactoIndividualDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
	}
	
	private void inicializarCatalogo() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
	}
	
	public DefaultListModel<String> obtenerNombreContactos() {
		return usuarioActual.obtenerNombreContactos();
	}
	
	public boolean crearGrupo(String nombre, DefaultListModel<String> contactosGrupo) {
		Grupo grupo = new Grupo(nombre);
		usuarioActual.addContactosGrupo(contactosGrupo, grupo);
		boolean registro = añadirContacto(usuarioActual.getLogin(), grupo);
		if(registro) {
			for(ContactoIndividual miembro : grupo.getMiembros()) {
				String tlf = miembro.getTelefono();
				Usuario user = _buscarUsuario(tlf);
				añadirContacto(user.getLogin(), grupo);
			}
		}
		return registro;
	}
	
	public Grupo comprobarGrupo(String nombre) {
		return usuarioActual.comprobarGrupo(nombre);
	}
	
	public void actualizarDatosGrupo(List<ContactoIndividual> contEnGrupo, 
			 						 DefaultListModel<String> contactosGrupo, 
			 						 DefaultListModel<String> listmodel) {
		usuarioActual.actualizarDatosGrupo(contEnGrupo, contactosGrupo, listmodel);
	}
	
	public void modificarGrupo(Grupo g, DefaultListModel<String> anadidos, 
            				   DefaultListModel<String> eliminados) {
		usuarioActual.modificarGrupo(g, anadidos, eliminados);
	}
	
	public void crearDocumento(Document documento) {
		usuarioActual.crearDocumento(documento);
	}
	
	@Override
	public void nuevosMensajes(MensajeEvent mensajeEvent) {
		List<MensajeWhatsApp> listaMensajes = mensajeEvent.getLista();
		
		// Transformar mensajes
		String contacto = listaMensajes.stream()
									   .map(MensajeWhatsApp::getAutor)
									   .distinct()
									   .filter(s -> !s.equals(usuarioActual.getNombre()))
									   .findAny()
									   .orElse("");
		
		Contacto con = usuarioActual.getContactos().stream()
                							       .filter(s -> s.getNombre().equals(contacto))
                							       .findAny()
                							       .orElse(null);
		
		System.out.println(contacto);
		if(con != null) {
			for(MensajeWhatsApp msj : listaMensajes) {
				String autor = msj.getAutor();
				String nombreUsuarioAct = usuarioActual.getNombre();
				
				if(autor.equals(nombreUsuarioAct)) {
					Mensaje mensaje = new Mensaje(usuarioActual, con, msj.getTexto());
					LocalDate fecha = msj.getFecha().toLocalDate();
					mensaje.setHora(fecha);
					
					enviarMensaje(mensaje);
				} else {
					Usuario aux = null;
					Contacto aux_con = new ContactoIndividual(usuarioActual.getNombre(), usuarioActual.getTelefono());
					if(con instanceof ContactoIndividual) {
						aux = _buscarUsuario(((ContactoIndividual) con).getTelefono());
					}
						
					//System.out.println("prueba: " +  aux.getNombre() );
					Mensaje mensaje = new Mensaje(aux, aux_con, msj.getTexto());
					LocalDate fecha = msj.getFecha().toLocalDate();
					mensaje.setHora(fecha);
					
					recibirMensaje(mensaje, con);
				}
			}
		}
	}
}
