package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.casouso.pais.EliminarPaisCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.pais.impl.EliminarPaisCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.negocio.fachada.pais.EliminarPaisFachada;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.TransaccionExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.ValidacionExcepcion;

public class EliminarPaisFachadaImpl implements EliminarPaisFachada {

	private DAOFactory daoFactory;
	private EliminarPaisCasoUso casoUso;

	public EliminarPaisFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new EliminarPaisCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(PaisDTO datos) {

		if (UtilObjeto.esNulo(datos)) {
			throw new ValidacionExcepcion("Los datos del pais no pueden ser nulos");
		}

		try {

			daoFactory.iniciarTransaccion();

			PaisDominio dominio = new PaisDominio.Builder()
					.id(datos.getId())
					.build();
			casoUso.ejecutar(dominio);

			daoFactory.confirmarTransaccion();

		} catch (UcoParkingExcepcion excepcion) {

			daoFactory.cancelarTransaccion();
			throw excepcion;

		} catch (Exception excepcion) {

			daoFactory.cancelarTransaccion();
			throw new TransaccionExcepcion("La transaccion fue cancelada.", excepcion);

		} finally {

			daoFactory.cerrarConexion();

		}
	}

}