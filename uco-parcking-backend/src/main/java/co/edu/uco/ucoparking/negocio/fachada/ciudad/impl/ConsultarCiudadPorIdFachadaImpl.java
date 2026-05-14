package co.edu.uco.ucoparking.negocio.fachada.ciudad.impl;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.CiudadDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.impl.CiudadDTOAssembler;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.ConsultarCiudadPorIdCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.impl.ConsultarCiudadPorIdCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.ciudad.ConsultarCiudadPorIdFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public final class ConsultarCiudadPorIdFachadaImpl implements ConsultarCiudadPorIdFachada {

	private final DAOFactory daoFactory;
	private final ConsultarCiudadPorIdCasoUso casoUso;

	public ConsultarCiudadPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarCiudadPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public CiudadDTO ejecutar(final UUID id) {
		try {
			daoFactory.iniciarTransaccion();

			var ciudadDominio = casoUso.ejecutar(id);

			var ciudadDTO = CiudadDTOAssembler.getInstance()
					.ensamblarDTO(ciudadDominio);

			daoFactory.confirmarTransaccion();

			return ciudadDTO;

		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;

		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw NegocioUcoParkingExcepcion.crear(
					"Ocurrió un error inesperado al consultar la ciudad por identificador.",
					excepcion);

		} finally {
			daoFactory.cerrarConexion();
		}
	}
}
