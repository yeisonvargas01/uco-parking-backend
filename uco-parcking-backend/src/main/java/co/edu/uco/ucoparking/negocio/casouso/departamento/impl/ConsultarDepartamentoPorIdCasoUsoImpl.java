package co.edu.uco.ucoparking.negocio.casouso.departamento.impl;

import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.departamento.ConsultarDepartamentoPorIdCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.DepartamentoDominio;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class ConsultarDepartamentoPorIdCasoUsoImpl implements ConsultarDepartamentoPorIdCasoUso {

	private final DAOFactory daoFactory;

	public ConsultarDepartamentoPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public DepartamentoDominio ejecutar(final UUID id) {
		validarIdDepartamento(id);

		var departamentoEntidad = consultarDepartamentoPorId(id);

		validarExisteDepartamento(departamentoEntidad);

		return convertirEntidadADominio(departamentoEntidad);
	}

	// 1. El identificador del departamento es obligatorio.
	// Para consultar un departamento específico, el sistema necesita recibir
	// el identificador del registro que se desea buscar.
	private void validarIdDepartamento(final UUID id) {
		if (Objects.isNull(id)) {
			throw NegocioUcoParkingExcepcion.crear("El identificador del departamento es obligatorio.");
		}
	}

	// 2. Consultar el departamento por su identificador.
	private DepartamentoEntidad consultarDepartamentoPorId(final UUID id) {
		return daoFactory.obtenerDepartamentoDAO().consultarPorId(id);
	}

	// 3. Debe existir un departamento con el identificador recibido.
	private void validarExisteDepartamento(final DepartamentoEntidad departamentoEntidad) {
		if (Objects.isNull(departamentoEntidad)) {
			throw NegocioUcoParkingExcepcion.crear(
					"No existe un departamento registrado con el identificador indicado.");
		}
	}

	// 4. Convertir la entidad consultada en objeto de dominio.
	private DepartamentoDominio convertirEntidadADominio(final DepartamentoEntidad departamentoEntidad) {
		return DepartamentoDominio.builder()
				.id(departamentoEntidad.getId())
				.nombre(departamentoEntidad.getNombre())
				.pais(convertirPaisEntidadADominio(departamentoEntidad.getPais()))
				.build();
	}

	// 5. Convertir el país asociado de entidad a dominio.
	private PaisDominio convertirPaisEntidadADominio(final PaisEntidad paisEntidad) {
		if (Objects.isNull(paisEntidad)) {
			return PaisDominio.builder().build();
		}

		return PaisDominio.builder()
				.id(paisEntidad.getId())
				.nombre(paisEntidad.getNombre())
				.build();
	}
}
