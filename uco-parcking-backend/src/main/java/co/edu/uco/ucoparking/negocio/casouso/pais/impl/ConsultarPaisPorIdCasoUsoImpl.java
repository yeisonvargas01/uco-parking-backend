package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.ConsultarPaisPorIdCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class ConsultarPaisPorIdCasoUsoImpl implements ConsultarPaisPorIdCasoUso {

	private final DAOFactory daoFactory;

	public ConsultarPaisPorIdCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public PaisDominio ejecutar(final UUID id) {
		validarIdPais(id);

		var paisEntidad = consultarPaisPorId(id);

		validarExistePais(paisEntidad);

		return convertirEntidadADominio(paisEntidad);
	}

	// 1. El identificador del país es obligatorio.
	private void validarIdPais(final UUID id) {
		if (Objects.isNull(id)) {
			throw NegocioUcoParkingExcepcion.crear("El identificador del país es obligatorio.");
		}
	}

	// 2. Consultar el país por su identificador.
	private PaisEntidad consultarPaisPorId(final UUID id) {
		return daoFactory.obtenerPaisDAO().consultarPorId(id);
	}

	// 3. Debe existir un país con el identificador recibido.
	private void validarExistePais(final PaisEntidad paisEntidad) {
		if (Objects.isNull(paisEntidad)) {
			throw NegocioUcoParkingExcepcion.crear("No existe un país registrado con el identificador indicado.");
		}
	}

	// 4. Convertir la entidad consultada en objeto de dominio.
	private PaisDominio convertirEntidadADominio(final PaisEntidad paisEntidad) {
		return PaisDominio.builder()
				.id(paisEntidad.getId())
				.nombre(paisEntidad.getNombre())
				.build();
	}
}
