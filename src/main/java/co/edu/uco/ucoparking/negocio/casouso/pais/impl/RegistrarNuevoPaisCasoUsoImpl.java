package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.RegistrarNuevoPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.ValidacionExcepcion;

public class RegistrarNuevoPaisCasoUsoImpl implements RegistrarNuevoPaisCasoUso {

	private DAOFactory daoFactory;

	public RegistrarNuevoPaisCasoUsoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(PaisDominio datos) {

		validarIntegridadDatosPais(datos);

		validarNoExistaOtroPaisConMismoNombre(datos.getNombre());

		guardarNuevoPais(datos);

	}

	private void validarNoExistaOtroPaisConMismoNombre(String nombre) {
		var paisEntidadFiltro = new PaisEntidad.Builder().nombre(nombre.trim()).build();
		var resultados = daoFactory.getPaisDAO().consultarPorFiltro(paisEntidadFiltro);

		if (!resultados.isEmpty()) {
			throw new ValidacionExcepcion("Ya existe un pais con el nombre: " + nombre);
		}
	}

	private UUID generarIdUnicoNuevoPais() {
		
		UUID uuidGenerado;
		PaisEntidad paisConsultado;
		
		do {
			uuidGenerado = UUID.randomUUID();
			paisConsultado = daoFactory
					.getPaisDAO()
					.consultarPorId(uuidGenerado);
		} while (paisConsultado != null);

		return uuidGenerado;
	}

	private void validarIntegridadDatosPais(PaisDominio datos) {
		if (UtilObjeto.esNulo(datos)) {
			throw new ValidacionExcepcion("Los datos del pais no pueden ser nulos");
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

	private void guardarNuevoPais(PaisDominio pais) {
		var idNuevoPais = generarIdUnicoNuevoPais();

		PaisEntidad paisEntidad = new PaisEntidad.Builder().id(idNuevoPais).nombre(pais.getNombre().trim()).build();
		daoFactory.getPaisDAO().crear(paisEntidad);

	}

}
