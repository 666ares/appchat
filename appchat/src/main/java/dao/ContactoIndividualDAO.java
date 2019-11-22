package dao;

import java.util.List;

import dominio.ContactoIndividual;

public interface ContactoIndividualDAO {

	void 						registrarIndividual(ContactoIndividual cInd);
	void						modificarIndividual(ContactoIndividual cInd);
	void 						borrarIndividual(ContactoIndividual cInd);
	ContactoIndividual 			recuperarIndividual(int id);
	List<ContactoIndividual> 	recuperarTodosIndividuales();
	
}
