package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.ConsultarPaisPorIdCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.ValidacionExcepcion;

public class ConsultarPaisPorIdCasoUsoImpl implements ConsultarPaisPorIdCasoUso {

	private DAOFactory daoFactory;

	public ConsultarPaisPorIdCasoUsoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public PaisEntidad ejecutar(PaisDominio datos) {
		ValidarObligatoriedadIDPais(datos);

		return consultarPais(datos);
	}

	private void ValidarObligatoriedadIDPais(PaisDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw new ValidacionExcepcion("El pais a consultar no es valido.");
		}

		if (UtilObjeto.esNulo(datos.getId())) {
			throw new ValidacionExcepcion("El ID del pais es obligatorio.");
		}
	}

	private PaisEntidad consultarPais(PaisDominio datos) {
		PaisEntidad paisEntidad = daoFactory.getPaisDAO().consultarPorId(datos.getId());

		if (UtilObjeto.esNulo(paisEntidad)) {
			throw new ValidacionExcepcion("No existe un pais con el ID: " + datos.getId());
		}

		return paisEntidad;
	}

}
