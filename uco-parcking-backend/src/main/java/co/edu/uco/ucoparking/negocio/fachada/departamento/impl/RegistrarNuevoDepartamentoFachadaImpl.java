package co.edu.uco.ucoparking.negocio.fachada.departamento.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.DepartamentoDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.impl.DepartamentoDTOAssembler;
import co.edu.uco.ucoparking.negocio.casouso.departamento.RegistrarNuevoDepartamentoCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.departamento.impl.RegistrarNuevoDepartamentoCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.departamento.RegistrarNuevoDepartamentoFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public final class RegistrarNuevoDepartamentoFachadaImpl implements RegistrarNuevoDepartamentoFachada {

	private final DAOFactory daoFactory;
	private final RegistrarNuevoDepartamentoCasoUso casoUso;

	public RegistrarNuevoDepartamentoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new RegistrarNuevoDepartamentoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final DepartamentoDTO dto) {
		try {
			daoFactory.iniciarTransaccion();

			var departamentoDominio = DepartamentoDTOAssembler.getInstance().ensamblarDominio(dto);

			casoUso.ejecutar(departamentoDominio);

			daoFactory.confirmarTransaccion();

		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;

		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw NegocioUcoParkingExcepcion.crear(
					"Ocurrió un error inesperado al registrar el nuevo departamento.",
					excepcion);

		} finally {
			daoFactory.cerrarConexion();
		}
	}
}