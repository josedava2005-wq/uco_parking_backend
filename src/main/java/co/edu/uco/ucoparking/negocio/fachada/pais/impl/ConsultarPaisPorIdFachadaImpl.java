package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.ConsultarPaisPorIdCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.pais.impl.ConsultarPaisPorIdCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.negocio.fachada.pais.ConsultarPaisPorIdFachada;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.ValidacionExcepcion;

public class ConsultarPaisPorIdFachadaImpl implements ConsultarPaisPorIdFachada {

	private DAOFactory daoFactory;
	private ConsultarPaisPorIdCasoUso casoUso;

	public ConsultarPaisPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarPaisPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public PaisDTO ejecutar(PaisDTO datos) {

		if (UtilObjeto.esNulo(datos)) {
			throw new ValidacionExcepcion("Los datos del pais no pueden ser nulos");
		}

		try {

			PaisDominio dominio = new PaisDominio.Builder().id(datos.getId()).build();
			PaisEntidad entidad = casoUso.ejecutar(dominio);

			return new PaisDTO.Builder()
					.id(entidad.getId())
					.nombre(entidad.getNombre())
					.build();

		} catch (UcoParkingExcepcion excepcion) {

			throw excepcion;

		} catch (Exception excepcion) {

			throw new UcoParkingExcepcion("Ocurrio un error obteniendo la informacion del pais.", excepcion);

		} finally {

			daoFactory.cerrarConexion();

		}
	}

}
