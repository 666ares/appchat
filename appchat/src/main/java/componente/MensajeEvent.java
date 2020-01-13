package componente;

import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import modelo.MensajeWhatsApp;

public class MensajeEvent extends EventObject {

	protected List<MensajeWhatsApp> listaMensajes;
	
	public MensajeEvent(Object obj, List<MensajeWhatsApp> lista) {
		super(obj);
		listaMensajes = lista;
	}
	
	public List<MensajeWhatsApp> getLista() {
		return new LinkedList<MensajeWhatsApp>(listaMensajes);
	}
}
