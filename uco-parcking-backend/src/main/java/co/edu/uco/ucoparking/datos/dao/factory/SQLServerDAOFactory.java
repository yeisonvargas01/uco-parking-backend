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
import co.edu.uco.ucoparking.transversal.excepcion.DatosUcoParkingException;

public final class SQLServerDAOFactory extends DAOFactory {

	private Connection conexion;

	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=UcoParking;encrypt=true;trustServerCertificate=true";
	private static final String USUARIO = "sa";
	private static final String CLAVE = "UcoParking123*";

	public SQLServerDAOFactory() {
		super();
	}

	@Override
	public void abrirConexion() throws DatosUcoParkingException {
		try {
			if (conexion == null || conexion.isClosed()) {
				conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
			}
		} catch (SQLException excepcion) {
			throw DatosUcoParkingException.crear(
					"No fue posible abrir la conexión con la base de datos.",
					"Error técnico al abrir la conexión con SQL Server.",
					excepcion);
		}
	}

	@Override
	public void cerrarConexion() throws DatosUcoParkingException {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
			}
		} catch (SQLException excepcion) {
			throw DatosUcoParkingException.crear(
					"No fue posible cerrar la conexión con la base de datos.",
					"Error técnico al cerrar la conexión con SQL Server.",
					excepcion);
		}
	}

	@Override
	public void iniciarTransaccion() throws DatosUcoParkingException {
		try {
			abrirConexion();
			conexion.setAutoCommit(false);
		} catch (SQLException excepcion) {
			throw DatosUcoParkingException.crear(
					"No fue posible iniciar la transacción.",
					"Error técnico al iniciar la transacción en SQL Server.",
					excepcion);
		}
	}

	@Override
	public void confirmarTransaccion() throws DatosUcoParkingException {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.commit();
				conexion.setAutoCommit(true);
			}
		} catch (SQLException excepcion) {
			throw DatosUcoParkingException.crear(
					"No fue posible confirmar la transacción.",
					"Error técnico al confirmar la transacción en SQL Server.",
					excepcion);
		}
	}

	@Override
	public void cancelarTransaccion() throws DatosUcoParkingException {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.rollback();
				conexion.setAutoCommit(true);
			}
		} catch (SQLException excepcion) {
			throw DatosUcoParkingException.crear(
					"No fue posible cancelar la transacción.",
					"Error técnico al cancelar la transacción en SQL Server.",
					excepcion);
		}
	}

	@Override
	public PaisDAO obtenerPaisDAO() throws DatosUcoParkingException {
		validarConexionActiva();
		return new PaisSQLServerDAO(conexion);
	}

	@Override
	public DepartamentoDAO obtenerDepartamentoDAO() throws DatosUcoParkingException {
		validarConexionActiva();
		return new DepartamentoSQLServerDAO(conexion);
	}

	@Override
	public CiudadDAO obtenerCiudadDAO() throws DatosUcoParkingException {
		validarConexionActiva();
		return new CiudadSQLServerDAO(conexion);
	}

	private void validarConexionActiva() throws DatosUcoParkingException {
		try {
			if (conexion == null || conexion.isClosed()) {
				throw DatosUcoParkingException.crear(
						"No existe una conexión activa con la base de datos.",
						"Se intentó obtener un DAO sin abrir previamente la conexión con SQL Server.");
			}
		} catch (SQLException excepcion) {
			throw DatosUcoParkingException.crear(
					"No fue posible validar la conexión con la base de datos.",
					"Error técnico al validar el estado de la conexión con SQL Server.",
					excepcion);
		}
	}
}
