package co.edu.uco.ucoparking.negocio.casouso.ciudad.impl;

import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.CiudadEntidad;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.ConsultarCiudadPorIdCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.CiudadDominio;
import co.edu.uco.ucoparking.negocio.dominio.DepartamentoDominio;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class ConsultarCiudadPorIdCasoUsoImpl implements ConsultarCiudadPorIdCasoUso {

	private final DAOFactory daoFactory;

	public ConsultarCiudadPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public CiudadDominio ejecutar(final UUID id) {
		validarIdCiudad(id);

		var ciudadEntidad = consultarCiudadPorId(id);

		validarExisteCiudad(ciudadEntidad);

		return convertirEntidadADominio(ciudadEntidad);
	}

	// 1. El identificador de la ciudad es obligatorio.
	private void validarIdCiudad(final UUID id) {
		if (Objects.isNull(id)) {
			throw NegocioUcoParkingExcepcion.crear("El identificador de la ciudad es obligatorio.");
		}
	}

	// 2. Consultar la ciudad por su identificador.
	private CiudadEntidad consultarCiudadPorId(final UUID id) {
		return daoFactory.obtenerCiudadDAO().consultarPorId(id);
	}

	// 3. Debe existir una ciudad con el identificador recibido.
	private void validarExisteCiudad(final CiudadEntidad ciudadEntidad) {
		if (Objects.isNull(ciudadEntidad)) {
			throw NegocioUcoParkingExcepcion.crear(
					"No existe una ciudad registrada con el identificador indicado.");
		}
	}

	// 4. Convertir la entidad consultada en objeto de dominio.
	private CiudadDominio convertirEntidadADominio(final CiudadEntidad ciudadEntidad) {
		return CiudadDominio.builder()
				.id(ciudadEntidad.getId())
				.nombre(ciudadEntidad.getNombre())
				.departamento(convertirDepartamentoEntidadADominio(ciudadEntidad.getDepartamento()))
				.build();
	}

	// 5. Convertir el departamento asociado de entidad a dominio.
	private DepartamentoDominio convertirDepartamentoEntidadADominio(final DepartamentoEntidad departamentoEntidad) {
		if (Objects.isNull(departamentoEntidad)) {
			return DepartamentoDominio.builder().build();
		}

		return DepartamentoDominio.builder()
				.id(departamentoEntidad.getId())
				.nombre(departamentoEntidad.getNombre())
				.pais(convertirPaisEntidadADominio(departamentoEntidad.getPais()))
				.build();
	}

	// 6. Convertir el país asociado de entidad a dominio.
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