package co.edu.uco.ucoparking.negocio.fachada.departamento.impl;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.DepartamentoDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.impl.DepartamentoDTOAssembler;
import co.edu.uco.ucoparking.negocio.casouso.departamento.ConsultarDepartamentoPorIdCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.departamento.impl.ConsultarDepartamentoPorIdCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.departamento.ConsultarDepartamentoPorIdFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public final class ConsultarDepartamentoPorIdFachadaImpl implements ConsultarDepartamentoPorIdFachada {

	private final DAOFactory daoFactory;
	private final ConsultarDepartamentoPorIdCasoUso casoUso;

	public ConsultarDepartamentoPorIdFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarDepartamentoPorIdCasoUsoImpl(daoFactory);
	}

	@Override
	public DepartamentoDTO ejecutar(final UUID id) {
		try {
			daoFactory.iniciarTransaccion();

			var departamentoDominio = casoUso.ejecutar(id);

			var departamentoDTO = DepartamentoDTOAssembler.getInstance()
					.ensamblarDTO(departamentoDominio);

			daoFactory.confirmarTransaccion();

			return departamentoDTO;

		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;

		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw NegocioUcoParkingExcepcion.crear(
					"Ocurrió un error inesperado al consultar el departamento por identificador.",
					excepcion);

		} finally {
			daoFactory.cerrarConexion();
		}
	}
}
