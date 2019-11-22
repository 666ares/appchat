package dao;

import java.util.List;

import dominio.Grupo;

public interface GrupoDAO {

	void 			registrarGrupo(Grupo grupo);
	void 			borrarGrupo(Grupo grupo);
	void			modificarGrupo(Grupo grupo);
	Grupo 			recuperarGrupo(int id);
	List<Grupo> 	recuperarTodosGrupos();
}

