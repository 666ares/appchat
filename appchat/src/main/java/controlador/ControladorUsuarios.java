package controlador;

import java.awt.Image;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;

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

public class ControladorUsuarios {

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
	
	public Object[][] contactosATabla(List<Contacto> contactos) {
		
		Object[][] datos = new Object[contactos.size()][3];
		
		Comparator<Contacto> ordenarPorNombre 
			= (Contacto c1, Contacto c2) -> c1.getNombre().compareTo(c2.getNombre());
			
		Collections.sort(contactos, ordenarPorNombre);
		
		int index = 0;
		for (Contacto c : contactos) {
			
			if (c instanceof ContactoIndividual) {
				ContactoIndividual ci = (ContactoIndividual) c;
				datos[index][0] = ci.getNombre();
				
				ImageIcon icon = new ImageIcon(ci.getUsuario().getImagenPerfil());
				Image imageIcon = icon.getImage();
				Image newImage = imageIcon.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
				ImageIcon icon2 = new ImageIcon(newImage);
				
				datos[index][1] = icon2;
				datos[index][2] = " " + ci.getTelefono();
				index++;
			} else {
				Grupo g = (Grupo) c;
				datos[index][0] = g.getNombre();
				
				ImageIcon icon = new ImageIcon("icons/group.png");
				Image imageIcon = icon.getImage();
				Image newImage = imageIcon.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
				ImageIcon icon2 = new ImageIcon(newImage);
				
				datos[index][1] = icon2;
				datos[index][2] = " ...";
				index++;
			}
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
			updateUsuario(usuario);
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
	
	public void recibirMensaje(Mensaje mensaje, Contacto contacto) {
		if (contacto instanceof ContactoIndividual) {
			ContactoIndividual cInd = (ContactoIndividual) contacto;
			cInd.addMensaje(mensaje);
			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorIndividual.modificarIndividual(cInd);
		} else if (contacto instanceof Grupo) {
			Grupo g = (Grupo) contacto;
			g.addMensaje(mensaje);
			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorGrupo.modificarGrupo(g);
		}
		
	}
	
	public boolean updateUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getLogin()))
			return false;
		
		adaptadorUsuario.modificarUsuario(usuario);
		catalogoUsuarios.updateUsuario(usuario);
		return true;
	}	
	
	public boolean updateIndividual(ContactoIndividual contacto) {
		adaptadorIndividual.modificarIndividual(contacto);
		return true;
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
}
