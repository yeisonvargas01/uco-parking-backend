package co.edu.uco.ucoparking.negocio.casouso.departamento.impl;

import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.CiudadEntidad;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.negocio.casouso.departamento.EliminarDepartamentoCasoUso;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class EliminarDepartamentoCasoUsoImpl implements EliminarDepartamentoCasoUso {

	private final DAOFactory daoFactory;

	public EliminarDepartamentoCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final UUID id) {
		validarIdDepartamento(id);
		validarExisteDepartamentoConId(id);
		validarDepartamentoNoEsteAsociadoACiudades(id);
		eliminar(id);
	}

	// 1. El identificador del departamento es obligatorio.
	private void validarIdDepartamento(final UUID id) {
		if (Objects.isNull(id)) {
			throw NegocioUcoParkingExcepcion.crear("El identificador del departamento es obligatorio.");
		}
	}

	// 2. Debe existir un departamento con el identificador recibido.
	private void validarExisteDepartamentoConId(final UUID id) {
		var departamentoExistente = daoFactory.obtenerDepartamentoDAO().consultarPorId(id);

		if (Objects.isNull(departamentoExistente)) {
			throw NegocioUcoParkingExcepcion.crear(
					"No existe un departamento registrado con el identificador indicado.");
		}
	}

	// 3. El departamento no debe estar siendo usado por otros objetos del dominio.
	private void validarDepartamentoNoEsteAsociadoACiudades(final UUID id) {
		var departamentoFiltro = DepartamentoEntidad.builder()
				.id(id)
				.build();

		var ciudadFiltro = CiudadEntidad.builder()
				.departamento(departamentoFiltro)
				.build();

		var ciudades = daoFactory.obtenerCiudadDAO().consultar(ciudadFiltro);

		if (Objects.nonNull(ciudades) && !ciudades.isEmpty()) {
			throw NegocioUcoParkingExcepcion.crear(
					"No es posible eliminar el departamento porque tiene ciudades asociadas.");
		}
	}

	// 4. Eliminar el departamento.
	private void eliminar(final UUID id) {
		daoFactory.obtenerDepartamentoDAO().eliminar(id);
	}
}