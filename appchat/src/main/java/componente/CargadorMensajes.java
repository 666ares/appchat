package componente;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;

import modelo.MensajeWhatsApp;
import modelo.Plataforma;
import parser.SimpleTextParser;

public class CargadorMensajes {
	
	List<MensajeWhatsApp> listaMensajes;
	String fichero;
	String formato;
	Plataforma plataforma;
	
	private Vector mensajeListeners = new Vector();
	
	public CargadorMensajes() {}
	
	public void setFichero(String fichero, String formato) {
		this.fichero = fichero;
		if(formato.equals("IOS")) {
			this.formato = "d/M/yy H:mm:ss";
			this.plataforma = Plataforma.IOS;
		} else if(formato.equals("Android1")) {
			this.formato = "d/M/yy H:mm";
			this.plataforma = Plataforma.ANDROID;
		} else if(formato.equals("Android2")) {
			this.formato = "d/M/yyyy H:mm";
			this.plataforma = Plataforma.ANDROID;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.formato);
		try {
			listaMensajes = SimpleTextParser.parse(this.fichero, this.formato, plataforma);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!listaMensajes.isEmpty()) {
			MensajeEvent evento = new MensajeEvent(this, listaMensajes);
			notificarNuevosMensajes(evento);
		}
	}
	
	public synchronized void addMensajesListener(MensajeListener listener){
		mensajeListeners.addElement(listener);
	}
	public synchronized void removeMensajesListener(MensajeListener listener){
		mensajeListeners.removeElement(listener);
	}
	private void notificarNuevosMensajes(MensajeEvent evento){
		Vector lista;
		synchronized(this){
			lista=(Vector) mensajeListeners.clone();
		}
		for(int i=0; i<lista.size(); i++){
			MensajeListener listener=(MensajeListener)lista.elementAt(i);
			listener.nuevosMensajes(evento);
		}
	}

}
