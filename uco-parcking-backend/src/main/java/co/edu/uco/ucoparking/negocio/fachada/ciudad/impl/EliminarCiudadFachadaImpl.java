package co.edu.uco.ucoparking.negocio.fachada.ciudad.impl;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.EliminarCiudadCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.impl.EliminarCiudadCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.ciudad.EliminarCiudadFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public final class EliminarCiudadFachadaImpl implements EliminarCiudadFachada {

	private final DAOFactory daoFactory;
	private final EliminarCiudadCasoUso casoUso;

	public EliminarCiudadFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new EliminarCiudadCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final UUID id) {
		try {
			daoFactory.iniciarTransaccion();

			casoUso.ejecutar(id);

			daoFactory.confirmarTransaccion();

		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;

		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw NegocioUcoParkingExcepcion.crear(
					"Ocurrió un error inesperado al eliminar la información de la ciudad.",
					excepcion);

		} finally {
			daoFactory.cerrarConexion();
		}
	}
}
