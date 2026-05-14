package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.impl.PaisDTOAssembler;
import co.edu.uco.ucoparking.negocio.casouso.pais.ModificarPaisCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.pais.impl.ModificarPaisCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.ModificarPaisFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public final class ModificarPaisFachadaImpl implements ModificarPaisFachada {

	private final DAOFactory daoFactory;
	private final ModificarPaisCasoUso casoUso;

	public ModificarPaisFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ModificarPaisCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final PaisDTO dto) {
		try {
			daoFactory.iniciarTransaccion();

			var paisDominio = PaisDTOAssembler.getInstance().ensamblarDominio(dto);

			casoUso.ejecutar(paisDominio);

			daoFactory.confirmarTransaccion();

		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;

		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw NegocioUcoParkingExcepcion.crear(
					"Ocurrió un error inesperado al modificar la información del país.",
					excepcion);

		} finally {
			daoFactory.cerrarConexion();
		}
	}
}
