package co.edu.uco.ucoparking.datos.dao.factory;

import java.sql.SQLException;

import co.edu.uco.ucoparking.datos.dao.CiudadDAO;
import co.edu.uco.ucoparking.datos.dao.DepartamentoDAO;
import co.edu.uco.ucoparking.datos.dao.PaisDAO;

public abstract class DAOFactory {

	public abstract void abrirConexion() throws SQLException;

	public abstract void cerrarConexion() throws SQLException;

	public abstract void iniciarTransaccion() throws SQLException;

	public abstract void confirmarTransaccion() throws SQLException;

	public abstract void cancelarTransaccion() throws SQLException;

	public abstract PaisDAO obtenerPaisDAO();

	public abstract DepartamentoDAO obtenerDepartamentoDAO();

	public abstract CiudadDAO obtenerCiudadDAO();
}