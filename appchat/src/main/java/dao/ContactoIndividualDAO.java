package dao;

import java.util.List;

import dominio.ContactoIndividual;

public interface ContactoIndividualDAO {

	void 						create(ContactoIndividual cInd);
	boolean 					delete(ContactoIndividual cInd);
	ContactoIndividual 			get(int id);
	List<ContactoIndividual> 	getAll();

}
