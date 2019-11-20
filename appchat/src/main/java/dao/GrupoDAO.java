package dao;

import java.util.List;

import dominio.Grupo;

public interface GrupoDAO {

	void 			create(Grupo g);
	boolean 		delete(Grupo g);
	Grupo 			get(int id);
	List<Grupo> 	getAll();
}

