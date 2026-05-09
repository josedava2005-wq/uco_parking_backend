package co.edu.uco.ucoparking.datos.dao.sql.factoria.sqlserver;

import java.sql.DriverManager;
import java.sql.SQLException;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.sqlserver.PaisSQLServerDAO;
import io.github.cdimascio.dotenv.Dotenv;
import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;

public class SQLServerDAOFactory extends DAOFactory {

	public SQLServerDAOFactory() {
		abrirConexion();
	}

	@Override
	protected void abrirConexion() {
		try {

			Dotenv env = Dotenv.load();

			String servidor = env.get("BD_HOST");
			String puerto = env.get("BD_PORT");
			String baseDatos = env.get("BD_NAME");
			String usuario = env.get("BD_USR");
			String contrasena = env.get("BD_PWD");

			String urlConexion = "jdbc:sqlserver://" + servidor + ":" + puerto + ";" + "database=" + baseDatos + ";"
					+ "user=" + usuario + ";" + "password=" + contrasena + ";applicationName=UCOParking;"
					+ "encrypt=false;" + "trustServerCertificate=false;" + "loginTimeout=30;";

			conexion = DriverManager.getConnection(urlConexion);
			System.out.println("Conexión a SQL Server establecida exitosamente.");
		} catch (Exception e) {

			conexion = null;

			throw new RuntimeException("Error al abrir la conexión a la base de datos SQLServer", e);
		}

	}

	@Override
	public void cerrarConexion() {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
				System.out.println("La conexion fue cerrada correctamente.");

			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al cerrar la conexión de la base de datos SQLServer", e);
		} finally {
			conexion = null;
		}

	}

	@Override
	public void iniciarTransaccion() {
		try {
			if (esConexionValida()) {
				conexion.setAutoCommit(false);
				System.out.println("Transaccion Iniciada.");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al iniciar la transaccion de la base de datos SQLServer", e);
		}
	}

	@Override
	public void confirmarTransaccion() {
		try {
			if (esConexionValida()) {
				conexion.commit();
				conexion.setAutoCommit(true);
				System.out.println("Transaccion Confirmada.");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al confirmar la transaccion de la base de datos SQLServer", e);
		}
	}

	@Override
	public void cancelarTransaccion() {
		try {
			if (esConexionValida()) {
				conexion.rollback();
				conexion.setAutoCommit(true);
				System.out.println("Transaccion Cancelada.");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al cancelar la transaccion de la base de datos SQLServer", e);
		}
	}

	@Override
	public PaisDAO getPaisDAO() {
		return new PaisSQLServerDAO(conexion);
	}
	
	private boolean esConexionValida() {
		try {
			return conexion != null && !conexion.isClosed();
		} catch (SQLException e) {
			return false;
		}
	}
}
