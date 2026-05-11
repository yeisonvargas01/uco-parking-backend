package co.edu.uco.ucoparking.datos.dao.sql.factoria;

import java.sql.Connection;

import co.edu.uco.ucoparking.datos.dao.CiudadDAO;
import co.edu.uco.ucoparking.datos.dao.DepartamentoDAO;
import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.factoria.sqlserver.SQLServerDAOFactory;

public abstract class DAOFactory {

	protected Connection conexion;

	private static TipoFactoriaEnum FACTORIA_ACTUAL = TipoFactoriaEnum.SQLSERVER;

	public static DAOFactory getFactory() {
		switch (FACTORIA_ACTUAL) {
		case SQLSERVER:
			return new SQLServerDAOFactory();

		default:
			// No dejar esta. Debe ser excepción personalizada.
			throw new IllegalArgumentException("Unexpected value: " + FACTORIA_ACTUAL);
		}
	}

	public abstract void abrirConexion();

	public abstract void cerrarConexion();

	public abstract void iniciarTransaccion();

	public abstract void confirmarTransaccion();

	public abstract void cancelarTransaccion();

	public abstract PaisDAO obtenerPaisDAO();

	public abstract DepartamentoDAO obtenerDepartamentoDAO();

	public abstract CiudadDAO obtenerCiudadDAO();
}
