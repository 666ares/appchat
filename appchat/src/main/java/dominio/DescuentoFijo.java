package dominio;

public class DescuentoFijo implements Descuento {

	@Override
	public double calcDescuento(Usuario usuario) {
		int nMensajes = usuario.getNumMensajes();
		if(nMensajes > 10 && nMensajes <= 100) {
			return 10*0.2;
		} else if(nMensajes > 100 && nMensajes <= 1000) {
			return 10*0.4;
		}
		return 0;
	}

}
