package co.edu.uco.ucoparking.negocio.casouso;

public interface CasoUsoConRetorno<D, R> {
	
	R ejecutar(D datos);
	
}
