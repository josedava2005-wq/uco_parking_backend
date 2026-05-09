package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.RegistrarNuevoPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;

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
		var paisEntidadFiltro = new PaisEntidad.Builder().nombre(nombre).build();
		var resultados = daoFactory.getPaisDAO().consultarPorFiltro(paisEntidadFiltro);

		if (!resultados.isEmpty()) {
			throw new RuntimeException("Ya existe un pais con el nombre: " + nombre);
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
			throw new RuntimeException("Los datos del pais no pueden ser nulos");
		}
		if (UtilTexto.esNula(datos.getNombre())) {
			throw new RuntimeException("El nombre del pais es obligatorio");
		}
		String nombre = datos.getNombre();
		if (nombre.length() < 1 || nombre.length() > 100) {
			throw new RuntimeException("El nombre debe tener entre 1 y 100 caracteres");
		}
	}

	private void guardarNuevoPais(PaisDominio pais) {
		var idNuevoPais = generarIdUnicoNuevoPais();

		PaisEntidad paisEntidad = new PaisEntidad.Builder().id(idNuevoPais).nombre(pais.getNombre()).build();
		daoFactory.getPaisDAO().crear(paisEntidad);

	}

}
