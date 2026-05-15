package co.edu.uco.ucoparking.datos.dao.sql.sqlserver;

import java.util.List;
import java.util.UUID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.SQLDAO;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.TransaccionExcepcion;

public class PaisSQLServerDAO extends SQLDAO implements PaisDAO {

	public PaisSQLServerDAO(Connection conexion) {
		super(conexion);

	}

	@Override
	public void crear(PaisEntidad entidad) {
		String sql = "INSERT INTO Pais (id, nombre) VALUES (?, ?)";

		try (PreparedStatement stmt = getConexion().prepareStatement(sql);) {
			stmt.setObject(1, entidad.getId());
			stmt.setObject(2, entidad.getNombre());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new TransaccionExcepcion("Ocurrio un error al crear el pais.", e);
		}

	}

	@Override
	public List<PaisEntidad> consultarTodos() {
		String sql = "SELECT id, nombre FROM Pais";
		List<PaisEntidad> resultados = new ArrayList<>();

		try (PreparedStatement stmt = getConexion().prepareStatement(sql);) {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				resultados.add(mapearResultado(rs));
			}
		} catch (SQLException e) {
			throw new TransaccionExcepcion("Ocurrio un error al consultar los paises.", e);
		}

		return resultados;
	}

	@Override
	public PaisEntidad consultarPorId(UUID id) {
		String sql = "SELECT id, nombre FROM Pais WHERE id = ?";

		try (PreparedStatement stmt = getConexion().prepareStatement(sql);) {

			stmt.setObject(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return mapearResultado(rs);
			}
		} catch (SQLException e) {
			throw new TransaccionExcepcion("Ocurrio un error al consultar el pais por id.", e);
		}

		return null;
	}

	@Override
	public List<PaisEntidad> consultarPorFiltro(PaisEntidad filtro) {
		String sql = "SELECT id, nombre FROM Pais WHERE 1=1";
		List<Object> parametros = new ArrayList<>();

		if (filtro.getNombre() != null && !filtro.getNombre().isEmpty()) {
			sql += " AND nombre = ?";
			parametros.add(filtro.getNombre());
		}

		List<PaisEntidad> resultados = new ArrayList<>();

		try (PreparedStatement stmt = getConexion().prepareStatement(sql);) {

			for (int i = 0; i < parametros.size(); i++) {
				stmt.setString(i + 1, (String) parametros.get(i));
			}

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				resultados.add(mapearResultado(rs));
			}
		} catch (SQLException e) {
			throw new TransaccionExcepcion("Ocurrio un error al consultar paises por filtro.", e);
		}

		return resultados;
	}

	@Override
	public void actualizar(UUID id, PaisEntidad entidad) {
		String sql = "UPDATE Pais SET nombre = ? WHERE id = ?";

		try (PreparedStatement stmt = getConexion().prepareStatement(sql);) {
			stmt.setString(1, entidad.getNombre());
			stmt.setObject(2, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new TransaccionExcepcion("Ocurrio un error al actualizar el pais.", e);
		}
	}

	@Override
	public void eliminar(UUID id) {
		String sql = "DELETE FROM Pais WHERE id = ?";

		try (PreparedStatement stmt = getConexion().prepareStatement(sql);) {
			stmt.setObject(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new TransaccionExcepcion("Ocurrio un error al eliminar el pais.", e);
		}
	}

	private PaisEntidad mapearResultado(ResultSet rs) throws SQLException {
		return new PaisEntidad.Builder().id(rs.getObject("id", UUID.class)).nombre(rs.getString("nombre")).build();
	}

}
