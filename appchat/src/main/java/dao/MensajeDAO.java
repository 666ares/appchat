package dao;

import java.util.List;

import dominio.Mensaje;

public interface MensajeDAO {

	void 			registrarMensaje(Mensaje mensaje);
	Mensaje 		recuperarMensaje(int id);
	List<Mensaje> 	recuperarTodosMensajes();
}
