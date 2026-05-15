package co.edu.uco.ucoparking.transversal.utilitario.excepcion;

public class TransaccionExcepcion extends UcoParkingExcepcion {

	private static final long serialVersionUID = 5798022889923045952L;

	public TransaccionExcepcion(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

}
