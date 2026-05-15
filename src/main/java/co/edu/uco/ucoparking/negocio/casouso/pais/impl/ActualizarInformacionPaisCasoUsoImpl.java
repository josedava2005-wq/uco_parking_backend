package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.ActualizarInformacionPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.ValidacionExcepcion;

public class ActualizarInformacionPaisCasoUsoImpl implements ActualizarInformacionPaisCasoUso {

	private DAOFactory daoFactory;

	public ActualizarInformacionPaisCasoUsoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(PaisDominio datos) {
		validarIntegridadDatos(datos);
		validarExistenciaPais(datos.getId());
		validarNoExistaOtroPaisConMismoNombre(datos.getId(), datos.getNombre().trim());
		actualizarPais(datos);
	}

	private void validarIntegridadDatos(PaisDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw new ValidacionExcepcion("Los datos del pais no pueden ser nulos");
		}
		if (UtilObjeto.esNulo(datos.getId())) {
			throw new ValidacionExcepcion("El ID del pais es obligatorio");
		}
		if (UtilTexto.esNula(datos.getNombre())) {
			throw new ValidacionExcepcion("El nombre del pais es obligatorio");
		}
		String nombre = datos.getNombre().trim();
		if (nombre.isEmpty()) {
			throw new ValidacionExcepcion("El nombre del pais no puede ser solo espacios");
		}
		if (nombre.length() > 100) {
			throw new ValidacionExcepcion("El nombre debe tener maximo 100 caracteres");
		}
	}

	private void validarExistenciaPais(UUID id) {
		PaisEntidad paisEntidad = daoFactory.getPaisDAO().consultarPorId(id);
		if (UtilObjeto.esNulo(paisEntidad)) {
			throw new ValidacionExcepcion("No existe un pais con el ID: " + id);
		}
	}

	private void validarNoExistaOtroPaisConMismoNombre(UUID idActual, String nombre) {
		var paisEntidadFiltro = new PaisEntidad.Builder().nombre(nombre).build();
		var resultados = daoFactory.getPaisDAO().consultarPorFiltro(paisEntidadFiltro);

		for (PaisEntidad pais : resultados) {
			if (!pais.getId().equals(idActual)) {
				throw new ValidacionExcepcion("Ya existe un pais con el nombre: " + nombre);
			}
		}
	}

	private void actualizarPais(PaisDominio pais) {
		PaisEntidad paisEntidad = new PaisEntidad.Builder().id(pais.getId()).nombre(pais.getNombre().trim()).build();
		daoFactory.getPaisDAO().actualizar(paisEntidad.getId(), paisEntidad);
	}

}