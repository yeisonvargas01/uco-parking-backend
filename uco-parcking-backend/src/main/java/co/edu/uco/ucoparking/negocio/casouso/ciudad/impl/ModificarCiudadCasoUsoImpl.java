package co.edu.uco.ucoparking.negocio.casouso.ciudad.impl;

import java.util.List;
import java.util.Objects;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.CiudadEntidad;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.ModificarCiudadCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.CiudadDominio;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class ModificarCiudadCasoUsoImpl implements ModificarCiudadCasoUso {

	private final DAOFactory daoFactory;

	public ModificarCiudadCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final CiudadDominio datos) {
		validarIntegridadDatosCiudad(datos);
		validarExisteCiudadConId(datos);
		validarExisteDepartamentoAsociado(datos);
		validarNoExisteOtraCiudadConMismoNombreEnElMismoDepartamento(datos);
		modificar(datos);
	}

	// 1. Validación de datos consistentes:
	// tipo de dato, longitud, obligatoriedad, formato y rango.
	private void validarIntegridadDatosCiudad(final CiudadDominio datos) {
		if (Objects.isNull(datos)) {
			throw NegocioUcoParkingExcepcion.crear("Los datos de la ciudad son obligatorios.");
		}

		if (Objects.isNull(datos.getId())) {
			throw NegocioUcoParkingExcepcion.crear("El identificador de la ciudad es obligatorio.");
		}

		if (Objects.isNull(datos.getNombre()) || datos.getNombre().trim().isEmpty()) {
			throw NegocioUcoParkingExcepcion.crear("El nombre de la ciudad es obligatorio.");
		}

		if (Objects.isNull(datos.getDepartamento())) {
			throw NegocioUcoParkingExcepcion.crear("El departamento de la ciudad es obligatorio.");
		}

		if (Objects.isNull(datos.getDepartamento().getId())) {
			throw NegocioUcoParkingExcepcion.crear("El identificador del departamento es obligatorio.");
		}
	}

	// 2. La ciudad que se desea modificar debe existir.
	private void validarExisteCiudadConId(final CiudadDominio datos) {
		var ciudadExistente = daoFactory.obtenerCiudadDAO().consultarPorId(datos.getId());

		if (Objects.isNull(ciudadExistente)) {
			throw NegocioUcoParkingExcepcion.crear(
					"No existe una ciudad registrada con el identificador indicado.");
		}
	}

	// 3. El departamento asociado a la ciudad debe existir.
	private void validarExisteDepartamentoAsociado(final CiudadDominio datos) {
		var departamentoExistente = daoFactory.obtenerDepartamentoDAO()
				.consultarPorId(datos.getDepartamento().getId());

		if (Objects.isNull(departamentoExistente)) {
			throw NegocioUcoParkingExcepcion.crear(
					"No existe un departamento registrado con el identificador indicado.");
		}
	}

	// 4. No debe existir otra ciudad con el mismo nombre en el mismo departamento.
	private void validarNoExisteOtraCiudadConMismoNombreEnElMismoDepartamento(final CiudadDominio datos) {
		var departamentoFiltro = DepartamentoEntidad.builder()
				.id(datos.getDepartamento().getId())
				.build();

		var ciudadEntidadFiltro = CiudadEntidad.builder()
				.nombre(datos.getNombre().trim())
				.departamento(departamentoFiltro)
				.build();

		List<CiudadEntidad> resultados = daoFactory.obtenerCiudadDAO().consultar(ciudadEntidadFiltro);

		if (Objects.nonNull(resultados)) {
			for (CiudadEntidad ciudad : resultados) {
				var esOtraCiudad = !ciudad.getId().equals(datos.getId());

				if (esOtraCiudad) {
					throw NegocioUcoParkingExcepcion.crear(
							"Ya existe otra ciudad registrada con el mismo nombre en el departamento indicado.");
				}
			}
		}
	}

	// 5. Modificar la información de la ciudad.
	private void modificar(final CiudadDominio datos) {
		var departamentoEntidad = DepartamentoEntidad.builder()
				.id(datos.getDepartamento().getId())
				.build();

		var ciudadEntidad = CiudadEntidad.builder()
				.id(datos.getId())
				.nombre(datos.getNombre().trim())
				.departamento(departamentoEntidad)
				.build();

		daoFactory.obtenerCiudadDAO().actualizar(ciudadEntidad);
	}
}
