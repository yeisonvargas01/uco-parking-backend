package co.edu.uco.ucoparking.negocio.casouso.ciudad.impl;

import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.EliminarCiudadCasoUso;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class EliminarCiudadCasoUsoImpl implements EliminarCiudadCasoUso {

	private final DAOFactory daoFactory;

	public EliminarCiudadCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final UUID id) {
		validarIdCiudad(id);
		validarExisteCiudadConId(id);
		eliminar(id);
	}

	// 1. El identificador de la ciudad es obligatorio.
	private void validarIdCiudad(final UUID id) {
		if (Objects.isNull(id)) {
			throw NegocioUcoParkingExcepcion.crear("El identificador de la ciudad es obligatorio.");
		}
	}

	// 2. Debe existir una ciudad con el identificador recibido.
	private void validarExisteCiudadConId(final UUID id) {
		var ciudadExistente = daoFactory.obtenerCiudadDAO().consultarPorId(id);

		if (Objects.isNull(ciudadExistente)) {
			throw NegocioUcoParkingExcepcion.crear(
					"No existe una ciudad registrada con el identificador indicado.");
		}
	}

	// 3. Eliminar la ciudad.
	private void eliminar(final UUID id) {
		daoFactory.obtenerCiudadDAO().eliminar(id);
	}
}