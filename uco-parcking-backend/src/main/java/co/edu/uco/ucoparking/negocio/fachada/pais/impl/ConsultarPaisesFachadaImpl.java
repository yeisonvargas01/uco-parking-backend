package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import java.util.List;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.impl.PaisDTOAssembler;
import co.edu.uco.ucoparking.negocio.casouso.pais.ConsultarPaisesCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.pais.impl.ConsultarPaisesCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.ConsultarPaisesFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public final class ConsultarPaisesFachadaImpl implements ConsultarPaisesFachada {

	private final DAOFactory daoFactory;
	private final ConsultarPaisesCasoUso casoUso;

	public ConsultarPaisesFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarPaisesCasoUsoImpl(daoFactory);
	}

	@Override
	public List<PaisDTO> ejecutar(final PaisDTO filtro) {
		try {
			daoFactory.iniciarTransaccion();

			var paisDominioFiltro = PaisDTOAssembler.getInstance().ensamblarDominio(filtro);

			var paisesDominio = casoUso.ejecutar(paisDominioFiltro);

			var paisesDTO = paisesDominio.stream()
					.map(PaisDTOAssembler.getInstance()::ensamblarDTO)
					.toList();

			daoFactory.confirmarTransaccion();

			return paisesDTO;

		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;

		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw NegocioUcoParkingExcepcion.crear(
					"Ocurrió un error inesperado al consultar la información de los países.",
					excepcion);

		} finally {
			daoFactory.cerrarConexion();
		}
	}
}
