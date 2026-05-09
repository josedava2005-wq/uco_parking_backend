package co.edu.uco.ucoparking.negocio.fachada;

public interface FachadaConRetorno<D, R> {

	R ejecutar(D datos);

}
