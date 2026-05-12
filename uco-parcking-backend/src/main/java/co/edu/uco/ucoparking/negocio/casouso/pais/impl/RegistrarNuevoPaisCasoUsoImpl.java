package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.RegistrarNuevoPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class RegistrarNuevoPaisCasoUsoImpl implements RegistrarNuevoPaisCasoUso {

	private final DAOFactory daoFactory;

	public RegistrarNuevoPaisCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final PaisDominio datos) {
		validarIntegridadDatosPais(datos);
		validarNoExisteOtroPaisConMismoNombre(datos.getNombre());

		var idNuevoPais = generarIdUnicoNuevoPais();

		guardar(datos, idNuevoPais);
	}
	
	//1. Validación de datos consistentes:
	// tipo de dato, longitud, obligatoriedad, formato y rango.
	private void validarIntegridadDatosPais(final PaisDominio datos) {
		if (Objects.isNull(datos)) {
			throw NegocioUcoParkingExcepcion.crear("Los datos del país son obligatorios.");
		}

		if (Objects.isNull(datos.getNombre()) || datos.getNombre().trim().isEmpty()) {
			throw NegocioUcoParkingExcepcion.crear("El nombre del país es obligatorio.");
		}
	}
	
	//2. No debe existir un país con el mismo nombre.
	private void validarNoExisteOtroPaisConMismoNombre(final String nombre) {
		var paisEntidadFiltro = PaisEntidad.builder()
				.nombre(nombre.trim())
				.build();

		var resultados = daoFactory.obtenerPaisDAO().consultar(paisEntidadFiltro);

		if (Objects.nonNull(resultados) && !resultados.isEmpty()) {
			throw NegocioUcoParkingExcepcion.crear("Ya existe un país registrado con el mismo nombre.");
		}
	}

	//3. El id del país debe ser único.
	private UUID generarIdUnicoNuevoPais() {
		UUID idNuevoPais;
		PaisEntidad paisExistente;

		do {
			idNuevoPais = UUID.randomUUID();
			paisExistente = daoFactory.obtenerPaisDAO().consultarPorId(idNuevoPais);
		} while (Objects.nonNull(paisExistente));

		return idNuevoPais;
	}

	private void guardar(final PaisDominio pais, final UUID id) {
		var paisEntidad = PaisEntidad.builder()
				.id(id)
				.nombre(pais.getNombre().trim())
				.build();

		daoFactory.obtenerPaisDAO().crear(paisEntidad);
	}
}