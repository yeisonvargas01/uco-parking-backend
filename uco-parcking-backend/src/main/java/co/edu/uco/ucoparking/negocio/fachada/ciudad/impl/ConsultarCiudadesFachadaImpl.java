package co.edu.uco.ucoparking.negocio.fachada.ciudad.impl;

import java.util.List;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.CiudadDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.impl.CiudadDTOAssembler;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.ConsultarCiudadesCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.impl.ConsultarCiudadesCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.ciudad.ConsultarCiudadesFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public final class ConsultarCiudadesFachadaImpl implements ConsultarCiudadesFachada {

	private final DAOFactory daoFactory;
	private final ConsultarCiudadesCasoUso casoUso;

	public ConsultarCiudadesFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarCiudadesCasoUsoImpl(daoFactory);
	}

	@Override
	public List<CiudadDTO> ejecutar(final CiudadDTO filtro) {
		try {
			daoFactory.iniciarTransaccion();

			var ciudadDominioFiltro = CiudadDTOAssembler.getInstance()
					.ensamblarDominio(filtro);

			var ciudadesDominio = casoUso.ejecutar(ciudadDominioFiltro);

			var ciudadesDTO = ciudadesDominio.stream()
					.map(CiudadDTOAssembler.getInstance()::ensamblarDTO)
					.toList();

			daoFactory.confirmarTransaccion();

			return ciudadesDTO;

		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;

		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw NegocioUcoParkingExcepcion.crear(
					"Ocurrió un error inesperado al consultar la información de las ciudades.",
					excepcion);

		} finally {
			daoFactory.cerrarConexion();
		}
	}
}
