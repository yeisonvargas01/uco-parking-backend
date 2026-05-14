package co.edu.uco.ucoparking.negocio.fachada.departamento.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.dto.DepartamentoDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.impl.DepartamentoDTOAssembler;
import co.edu.uco.ucoparking.negocio.casouso.departamento.ModificarDepartamentoCasoUso;
import co.edu.uco.ucoparking.negocio.casouso.departamento.impl.ModificarDepartamentoCasoUsoImpl;
import co.edu.uco.ucoparking.negocio.fachada.departamento.ModificarDepartamentoFachada;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

public final class ModificarDepartamentoFachadaImpl implements ModificarDepartamentoFachada {

	private final DAOFactory daoFactory;
	private final ModificarDepartamentoCasoUso casoUso;

	public ModificarDepartamentoFachadaImpl() {
		daoFactory = DAOFactory.getFactory();
		casoUso = new ModificarDepartamentoCasoUsoImpl(daoFactory);
	}

	@Override
	public void ejecutar(final DepartamentoDTO dto) {
		try {
			daoFactory.iniciarTransaccion();

			var departamentoDominio = DepartamentoDTOAssembler.getInstance()
					.ensamblarDominio(dto);

			casoUso.ejecutar(departamentoDominio);

			daoFactory.confirmarTransaccion();

		} catch (UcoParkingExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;

		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			throw NegocioUcoParkingExcepcion.crear(
					"Ocurrió un error inesperado al modificar la información del departamento.",
					excepcion);

		} finally {
			daoFactory.cerrarConexion();
		}
	}
}
