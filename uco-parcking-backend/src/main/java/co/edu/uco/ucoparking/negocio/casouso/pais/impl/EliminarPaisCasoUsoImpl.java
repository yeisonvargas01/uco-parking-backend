package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.EliminarPaisCasoUso;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class EliminarPaisCasoUsoImpl implements EliminarPaisCasoUso {

	private final DAOFactory daoFactory;

	public EliminarPaisCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final UUID id) {
		validarIdPais(id);
		validarExistePaisConId(id);
		validarPaisNoEsteAsociadoADepartamentos(id);
		eliminar(id);
	}

	// 1. El identificador del país es obligatorio.
	private void validarIdPais(final UUID id) {
		if (Objects.isNull(id)) {
			throw NegocioUcoParkingExcepcion.crear("El identificador del país es obligatorio.");
		}
	}

	// 2. Debe existir un país con el identificador recibido.
	private void validarExistePaisConId(final UUID id) {
		var paisExistente = daoFactory.obtenerPaisDAO().consultarPorId(id);

		if (Objects.isNull(paisExistente)) {
			throw NegocioUcoParkingExcepcion.crear("No existe un país registrado con el identificador indicado.");
		}
	}

	// 3. El país no debe estar siendo usado por otros objetos del dominio.
	private void validarPaisNoEsteAsociadoADepartamentos(final UUID id) {
		var paisFiltro = PaisEntidad.builder()
				.id(id)
				.build();

		var departamentoFiltro = DepartamentoEntidad.builder()
				.pais(paisFiltro)
				.build();

		var departamentos = daoFactory.obtenerDepartamentoDAO().consultar(departamentoFiltro);

		if (Objects.nonNull(departamentos) && !departamentos.isEmpty()) {
			throw NegocioUcoParkingExcepcion.crear(
					"No es posible eliminar el país porque tiene departamentos asociados.");
		}
	}

	// 4. Eliminar el país.
	private void eliminar(final UUID id) {
		daoFactory.obtenerPaisDAO().eliminar(id);
	}
}