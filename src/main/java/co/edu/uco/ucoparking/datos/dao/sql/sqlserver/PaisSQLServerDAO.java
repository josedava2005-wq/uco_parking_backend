package co.edu.uco.ucoparking.datos.dao.sql.sqlserver;

import java.util.List;
import java.util.UUID;

import java.sql.Connection;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.SQLDAO;
import co.edu.uco.ucoparking.entidad.PaisEntidad;

public class PaisSQLServerDAO extends SQLDAO implements PaisDAO {

	public PaisSQLServerDAO(Connection conexion) {
		super(conexion);

	}

	@Override
	public void crear(PaisEntidad entidad) {
		getConexion();

	}

	@Override
	public List<PaisEntidad> consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaisEntidad consultarPorId(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaisEntidad> consultarPorFiltro(PaisEntidad filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(UUID id, PaisEntidad entidad) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminar(UUID id) {
		// TODO Auto-generated method stub

	}

}
