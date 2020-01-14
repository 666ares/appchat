package umu.tds.appchat.appchat;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import dominio.Mensaje;
import dominio.Usuario;

public class TestMensaje {
	
	Usuario usuario;
	Mensaje mensaje;
	
	@Before
	public void inicializarTestMensaje() {
		usuario = new Usuario("Pepe", LocalDate.now().toString(), "pepe@um.es", "695214568", "Pepe", "contra", "", "");
		mensaje = new Mensaje(usuario, null, "prueba");
	}
	
	@Test
	public void testBuscarPorNombre() {
		assertTrue(mensaje.buscarPorNombre(usuario.getNombre()));
	}

	@Test
	public void testContieneTexto() {	
		assertFalse(mensaje.contieneTexto("otracosa"));
	}

	@Test
	public void testEstaEntreFechas() {
		LocalDate fecha1 = LocalDate.of(1998, 5, 6);
		LocalDate fecha2 = LocalDate.now();
		assertTrue(mensaje.estaEntreFechas(fecha1, fecha2));
	}

}
