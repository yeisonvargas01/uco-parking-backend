package co.edu.uco.ucoparking.datos.dao.sql.factoria.sqlserver;

import java.sql.SQLException;

import co.edu.uco.ucoparking.datos.dao.CiudadDAO;
import co.edu.uco.ucoparking.datos.dao.DepartamentoDAO;
import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.datos.dao.sql.sqlserver.CiudadSQLServerDAO;
import co.edu.uco.ucoparking.datos.dao.sql.sqlserver.DepartamentoSQLServerDAO;
import co.edu.uco.ucoparking.datos.dao.sql.sqlserver.PaisSQLServerDAO;

public class SQLServerDAOFactory extends DAOFactory {

	public SQLServerDAOFactory() {
		abrirConexion();
	}

	@Override
	public void abrirConexion() {
		try {
			if (conexion == null || conexion.isClosed()) {
				conexion = SQLServerConexion.getConexion();
			}
		} catch (SQLException exception) {
			throw new RuntimeException("No fue posible abrir la conexión con SQL Server.", exception);
		}
	}

	@Override
	public void cerrarConexion() {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
			}
		} catch (SQLException exception) {
			throw new RuntimeException("No fue posible cerrar la conexión con SQL Server.", exception);
		}
	}

	@Override
	public void iniciarTransaccion() {
		try {
			validarConexionActiva();
			conexion.setAutoCommit(false);
		} catch (SQLException exception) {
			throw new RuntimeException("No fue posible iniciar la transacción en SQL Server.", exception);
		}
	}

	@Override
	public void confirmarTransaccion() {
		try {
			validarConexionActiva();
			conexion.commit();
			conexion.setAutoCommit(true);
		} catch (SQLException exception) {
			throw new RuntimeException("No fue posible confirmar la transacción en SQL Server.", exception);
		}
	}

	@Override
	public void cancelarTransaccion() {
		try {
			validarConexionActiva();
			conexion.rollback();
			conexion.setAutoCommit(true);
		} catch (SQLException exception) {
			throw new RuntimeException("No fue posible cancelar la transacción en SQL Server.", exception);
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
				throw new RuntimeException("No existe una conexión activa con SQL Server.");
			}
		} catch (SQLException exception) {
			throw new RuntimeException("No fue posible validar el estado de la conexión con SQL Server.", exception);
		}
	}
}