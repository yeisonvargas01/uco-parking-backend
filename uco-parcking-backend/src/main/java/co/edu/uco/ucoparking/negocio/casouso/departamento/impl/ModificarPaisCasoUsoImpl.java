package co.edu.uco.ucoparking.negocio.casouso.departamento.impl;

import java.util.List;
import java.util.Objects;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.ModificarPaisCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class ModificarPaisCasoUsoImpl implements ModificarPaisCasoUso {

	private final DAOFactory daoFactory;

	public ModificarPaisCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final PaisDominio datos) {
		validarIntegridadDatosPais(datos);
		validarExistePaisConId(datos);
		validarNoExisteOtroPaisConMismoNombre(datos);
		modificar(datos);
	}

	// 1. Validación de datos consistentes:
	// tipo de dato, longitud, obligatoriedad, formato y rango.
	private void validarIntegridadDatosPais(final PaisDominio datos) {
		if (Objects.isNull(datos)) {
			throw NegocioUcoParkingExcepcion.crear("Los datos del país son obligatorios.");
		}

		if (Objects.isNull(datos.getId())) {
			throw NegocioUcoParkingExcepcion.crear("El identificador del país es obligatorio.");
		}

		if (Objects.isNull(datos.getNombre()) || datos.getNombre().trim().isEmpty()) {
			throw NegocioUcoParkingExcepcion.crear("El nombre del país es obligatorio.");
		}
	}

	// 2. El país que se desea modificar debe existir.
	private void validarExistePaisConId(final PaisDominio datos) {
		var paisExistente = daoFactory.obtenerPaisDAO().consultarPorId(datos.getId());

		if (Objects.isNull(paisExistente)) {
			throw NegocioUcoParkingExcepcion.crear("No existe un país registrado con el identificador indicado.");
		}
	}

	// 3. No debe existir otro país con el mismo nombre..
	private void validarNoExisteOtroPaisConMismoNombre(final PaisDominio datos) {
		var paisEntidadFiltro = PaisEntidad.builder()
				.nombre(datos.getNombre().trim())
				.build();

		List<PaisEntidad> resultados = daoFactory.obtenerPaisDAO().consultar(paisEntidadFiltro);

		if (Objects.nonNull(resultados)) {
			for (PaisEntidad pais : resultados) {
				var esOtroPais = !pais.getId().equals(datos.getId());

				if (esOtroPais) {
					throw NegocioUcoParkingExcepcion.crear(
							"Ya existe otro país registrado con el mismo nombre.");
				}
			}
		}
	}

	// 4. Modificar la información del país.
	private void modificar(final PaisDominio datos) {
		var paisEntidad = PaisEntidad.builder()
				.id(datos.getId())
				.nombre(datos.getNombre().trim())
				.build();

		daoFactory.obtenerPaisDAO().actualizar(paisEntidad);
	}
}