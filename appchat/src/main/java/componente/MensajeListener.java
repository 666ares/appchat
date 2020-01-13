package componente;

import java.util.EventListener;

public interface MensajeListener extends EventListener {

	public void nuevosMensajes(MensajeEvent mensajeEvent);
	
}
