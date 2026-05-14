package co.edu.uco.ucoparking.negocio.fachada.departamento.impl;

import java.util.List;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.DepartamentoDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.impl.DepartamentoDTOAssembler;
import co.edu.uco.ucoparking.negocio.casouso.departamento.ConsultarDepartamentosCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.departamento.impl.ConsultarDepartamentosCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.departamento.ConsultarDepartamentosFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public final class ConsultarDepartamentosFachadaImpl implements ConsultarDepartamentosFachada {

	private final DAOFactory daoFactory;
	private final ConsultarDepartamentosCasoUso casoUso;

	public ConsultarDepartamentosFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ConsultarDepartamentosCasoUsoImpl(daoFactory);
	}

	@Override
	public List<DepartamentoDTO> ejecutar(final DepartamentoDTO filtro) {
		try {
			daoFactory.iniciarTransaccion();

			var departamentoDominioFiltro = DepartamentoDTOAssembler.getInstance()
					.ensamblarDominio(filtro);

			var departamentosDominio = casoUso.ejecutar(departamentoDominioFiltro);

			var departamentosDTO = departamentosDominio.stream()
					.map(DepartamentoDTOAssembler.getInstance()::ensamblarDTO)
					.toList();

			daoFactory.confirmarTransaccion();

			return departamentosDTO;

		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;

		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw NegocioUcoParkingExcepcion.crear(
					"Ocurrió un error inesperado al consultar la información de los departamentos.",
					excepcion);

		} finally {
			daoFactory.cerrarConexion();
		}
	}
}
