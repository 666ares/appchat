package dominio;

public class DescuentoEstudiante implements Descuento {

	@Override
	public double calcDescuento(Usuario usuario) {
		return 10*0.3;
	}

}
