package co.edu.uco.ucoparking.datos.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import co.edu.uco.ucoparking.datos.dao.CiudadDAO;
import co.edu.uco.ucoparking.datos.dao.DepartamentoDAO;
import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sqlserver.CiudadSQLServerDAO;
import co.edu.uco.ucoparking.datos.dao.sqlserver.DepartamentoSQLServerDAO;
import co.edu.uco.ucoparking.datos.dao.sqlserver.PaisSQLServerDAO;

public final class SQLServerDAOFactory extends DAOFactory {

	private Connection conexion;

	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=UcoParking;encrypt=true;trustServerCertificate=true";
	private static final String USUARIO = "sa";
	private static final String CLAVE = "UcoParking123*";

	public SQLServerDAOFactory() {
		super();
	}

	@Override
	public void abrirConexion() throws SQLException {
		if (conexion == null || conexion.isClosed()) {
			conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
		}
	}

	@Override
	public void cerrarConexion() throws SQLException {
		if (conexion != null && !conexion.isClosed()) {
			conexion.close();
		}
	}

	@Override
	public void iniciarTransaccion() throws SQLException {
		abrirConexion();
		conexion.setAutoCommit(false);
	}

	@Override
	public void confirmarTransaccion() throws SQLException {
		if (conexion != null && !conexion.isClosed()) {
			conexion.commit();
			conexion.setAutoCommit(true);
		}
	}

	@Override
	public void cancelarTransaccion() throws SQLException {
		if (conexion != null && !conexion.isClosed()) {
			conexion.rollback();
			conexion.setAutoCommit(true);
		}
	}

	@Override
	public PaisDAO obtenerPaisDAO() {
		validarConexionActiva();
		return new PaisSQLServerDAO(conexion);
	}

	@Override
	public DepartamentoDAO obtenerDepartamentoDAO() {
		validarConexionActiva();
		return new DepartamentoSQLServerDAO(conexion);
	}

	@Override
	public CiudadDAO obtenerCiudadDAO() {
		validarConexionActiva();
		return new CiudadSQLServerDAO(conexion);
	}

	private void validarConexionActiva() {
		try {
			if (conexion == null || conexion.isClosed()) {
				throw new IllegalStateException("No existe una conexión activa con SQL Server.");
			}
		} catch (SQLException excepcion) {
			throw new IllegalStateException("No fue posible validar la conexión con SQL Server.", excepcion);
		}
	}
}
