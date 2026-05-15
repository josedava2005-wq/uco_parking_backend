package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.EliminarPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.ValidacionExcepcion;

public class EliminarPaisCasoUsoImpl implements EliminarPaisCasoUso {

	private DAOFactory daoFactory;

	public EliminarPaisCasoUsoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(PaisDominio datos) {
		validarIntegridadDatos(datos);
		validarExistenciaPais(datos.getId());
		eliminarPais(datos.getId());
	}

	private void validarIntegridadDatos(PaisDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw new ValidacionExcepcion("Los datos del pais no pueden ser nulos");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw new ValidacionExcepcion("El ID del pais es obligatorio");
		}
	}

	private void validarExistenciaPais(UUID id) {
		PaisEntidad paisEntidad = daoFactory.getPaisDAO().consultarPorId(id);
		if (UtilObjeto.esNulo(paisEntidad)) {
			throw new ValidacionExcepcion("No existe un pais con el ID: " + id);
		}
	}

	private void eliminarPais(UUID id) {
		daoFactory.getPaisDAO().eliminar(id);
	}

}