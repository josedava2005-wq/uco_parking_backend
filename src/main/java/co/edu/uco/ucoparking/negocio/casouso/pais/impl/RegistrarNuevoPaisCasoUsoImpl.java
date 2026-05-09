package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.RegistrarNuevoPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;

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
		
		
		if(UtilObjeto.esNulo(resultados) || !resultados.isEmpty()) {
			// Si no se cumple, lanzar una excepción customizada
			// Ejemplo: ExistePaisConMismoNombreException
		}

	}

	private UUID generarIdUnicoNuevoPais() {

		// Logica para generar un id que no existe

		return UUID.randomUUID();
	}

	private void validarIntegridadDatosPais(PaisDominio datos) {
		// Validación de datos consistentes: tipos de dato, longitud, obligatoriedad,
		// formato y rango.

	}

	private void guardarNuevoPais(PaisDominio pais) {
		// logica para guardar el nuevo pais

		var idNuevoPais = generarIdUnicoNuevoPais();

		PaisEntidad paisEntidad = null;
		daoFactory.getPaisDAO().crear(paisEntidad);

	}

}
