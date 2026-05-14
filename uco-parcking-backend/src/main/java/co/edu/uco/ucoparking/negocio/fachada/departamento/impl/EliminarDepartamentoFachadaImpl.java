package co.edu.uco.ucoparking.negocio.fachada.departamento.impl;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.negocio.casouso.departamento.EliminarDepartamentoCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.departamento.impl.EliminarDepartamentoCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.departamento.EliminarDepartamentoFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public final class EliminarDepartamentoFachadaImpl implements EliminarDepartamentoFachada {

	private final DAOFactory daoFactory;
	private final EliminarDepartamentoCasoUso casoUso;

	public EliminarDepartamentoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new EliminarDepartamentoCasoUsoImpl(daoFactory);
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
					"Ocurrió un error inesperado al eliminar la información del departamento.",
					excepcion);

		} finally {
			daoFactory.cerrarConexion();
		}
	}
}
