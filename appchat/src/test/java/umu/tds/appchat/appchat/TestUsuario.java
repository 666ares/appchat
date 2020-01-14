package umu.tds.appchat.appchat;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUsuario {
	
	Usuario usuario;
	Contacto contacto;
	
	@Before
	public void inicializarTestUsuario() {
		usuario = new Usuario("Pepe", LocalDate.now().toString(), "pepe@um.es", "695214568", "Pepe", "contra", "", "");
		contacto = new ContactoIndividual("Paco", "605896269");
	}

	@Test
	public void testAddContacto() {
		assertTrue(usuario.addContacto(contacto));
	}

	@Test
	public void testRemoveContacto() {
		usuario.addContacto(contacto);
		assertTrue(usuario.removeContacto(contacto));
	}

	@Test
	public void testBuscarContacto() {
		usuario.addContacto(contacto);
		assertNotNull(usuario.buscarContacto("605896269"));
	}

}
