package co.edu.uco.ucoparking.negocio.fachada.ciudad.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.CiudadDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.impl.CiudadDTOAssembler;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.RegistrarNuevaCiudadCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.impl.RegistrarNuevaCiudadCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.ciudad.RegistrarNuevaCiudadFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public final class RegistrarNuevaCiudadFachadaImpl implements RegistrarNuevaCiudadFachada {

	private final DAOFactory daoFactory;
	private final RegistrarNuevaCiudadCasoUso casoUso;

	public RegistrarNuevaCiudadFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarNuevaCiudadCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final CiudadDTO dto) {
		try {
			daoFactory.iniciarTransaccion();

			var ciudadDominio = CiudadDTOAssembler.getInstance()
					.ensamblarDominio(dto);

			casoUso.ejecutar(ciudadDominio);

			daoFactory.confirmarTransaccion();

		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;

		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw NegocioUcoParkingExcepcion.crear(
					"Ocurrió un error inesperado al registrar la nueva ciudad.",
					excepcion);

		} finally {
			daoFactory.cerrarConexion();
		}
	}
}