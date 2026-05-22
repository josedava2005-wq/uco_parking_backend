package co.edu.uco.ucoparking.datos.dao.sql.factoria.sqlserver;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.sqlserver.PaisSQLServerDAO;
import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.TransaccionExcepcion;

public class SQLServerDAOFactory extends DAOFactory {

	public SQLServerDAOFactory() {
		abrirConexion();
	}

	@Override
	protected void abrirConexion() {
		try {
			Properties props = new Properties();
			try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
				props.load(input);
			}

			String urlConexion = props.getProperty("db.url");
			String driver     = props.getProperty("db.driver");
			String usuario    = props.getProperty("db.user",     "").trim();
			String contrasena = props.getProperty("db.password", "").trim();

			Class.forName(driver);

			if (!usuario.isEmpty()) {
				conexion = DriverManager.getConnection(urlConexion, usuario, contrasena);
			} else {
				conexion = DriverManager.getConnection(urlConexion);
			}

		} catch (SQLException e) {
			conexion = null;
			throw new TransaccionExcepcion(
				"Error SQL al conectar con la base de datos: " + e.getMessage(), e);
		} catch (Exception e) {
			conexion = null;
			throw new TransaccionExcepcion(
				"Error al configurar la conexion [" + e.getClass().getSimpleName() + "]: " + e.getMessage(), e);
		}
	}

	@Override
	public void cerrarConexion() {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();

			}
		} catch (SQLException e) {
			throw new TransaccionExcepcion("Error al cerrar la conexión de la base de datos SQLServer", e);
		} finally {
			conexion = null;
		}

	}

	@Override
	public void iniciarTransaccion() {
		try {
			if (esConexionValida()) {
				conexion.setAutoCommit(false);
			}
		} catch (SQLException e) {
			throw new TransaccionExcepcion("Error al iniciar la transaccion de la base de datos SQLServer", e);
		}
	}

	@Override
	public void confirmarTransaccion() {
		try {
			if (esConexionValida()) {
				conexion.commit();
				conexion.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new TransaccionExcepcion("Error al confirmar la transaccion de la base de datos SQLServer", e);
		}
	}

	@Override
	public void cancelarTransaccion() {
		try {
			if (esConexionValida()) {
				conexion.rollback();
				conexion.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new TransaccionExcepcion("Error al cancelar la transaccion de la base de datos SQLServer", e);
		}
	}

	@Override
	public PaisDAO getPaisDAO() {
		return new PaisSQLServerDAO(conexion);
	}

	private boolean esConexionValida() {
		try {
			return conexion != null && !conexion.isClosed();
		} catch (SQLException ignorada) {
			return false;
		}
	}
}
