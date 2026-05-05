package co.edu.uco.ucoparking.datos.dao.factory;

import co.edu.uco.ucoparking.datos.dao.CiudadDAO;
import co.edu.uco.ucoparking.datos.dao.DepartamentoDAO;
import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.transversal.excepcion.DatosUcoParkingException;

public abstract class DAOFactory {

	public abstract void abrirConexion() throws DatosUcoParkingException;

	public abstract void cerrarConexion() throws DatosUcoParkingException;

	public abstract void iniciarTransaccion() throws DatosUcoParkingException;

	public abstract void confirmarTransaccion() throws DatosUcoParkingException;

	public abstract void cancelarTransaccion() throws DatosUcoParkingException;

	public abstract PaisDAO obtenerPaisDAO() throws DatosUcoParkingException;

	public abstract DepartamentoDAO obtenerDepartamentoDAO() throws DatosUcoParkingException;

	public abstract CiudadDAO obtenerCiudadDAO() throws DatosUcoParkingException;
}