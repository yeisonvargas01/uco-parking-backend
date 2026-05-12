package co.edu.uco.ucoparking.negocio.casouso.ciudad.impl;

import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.CiudadEntidad;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.RegistrarNuevaCiudadCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.CiudadDominio;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class RegistrarNuevaCiudadCasoUsoImpl implements RegistrarNuevaCiudadCasoUso {

	private final DAOFactory daoFactory;

	public RegistrarNuevaCiudadCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final CiudadDominio datos) {
		validarIntegridadDatosCiudad(datos);
		validarExisteDepartamentoAsociado(datos);
		validarNoExisteOtraCiudadConMismoNombreEnElMismoDepartamento(datos);

		var idNuevaCiudad = generarIdUnicoNuevaCiudad();

		guardar(datos, idNuevaCiudad);
	}

	// 1. Validación de datos consistentes:
	// tipo de dato, longitud, obligatoriedad, formato y rango.
	private void validarIntegridadDatosCiudad(final CiudadDominio datos) {
		if (Objects.isNull(datos)) {
			throw NegocioUcoParkingExcepcion.crear("Los datos de la ciudad son obligatorios.");
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

	// 2. El departamento asociado a la ciudad debe existir.
	private void validarExisteDepartamentoAsociado(final CiudadDominio datos) {
		var departamentoExistente = daoFactory.obtenerDepartamentoDAO()
				.consultarPorId(datos.getDepartamento().getId());

		if (Objects.isNull(departamentoExistente)) {
			throw NegocioUcoParkingExcepcion.crear(
					"No existe un departamento registrado con el identificador indicado.");
		}
	}

	// 3. No debe existir otra ciudad con el mismo nombre en el mismo departamento.
	private void validarNoExisteOtraCiudadConMismoNombreEnElMismoDepartamento(final CiudadDominio datos) {
		var departamentoFiltro = DepartamentoEntidad.builder()
				.id(datos.getDepartamento().getId())
				.build();

		var ciudadEntidadFiltro = CiudadEntidad.builder()
				.nombre(datos.getNombre().trim())
				.departamento(departamentoFiltro)
				.build();

		var resultados = daoFactory.obtenerCiudadDAO().consultar(ciudadEntidadFiltro);

		if (Objects.nonNull(resultados) && !resultados.isEmpty()) {
			throw NegocioUcoParkingExcepcion.crear(
					"Ya existe una ciudad registrada con el mismo nombre en el departamento indicado.");
		}
	}

	// 4. El id de la ciudad debe ser único.
	private UUID generarIdUnicoNuevaCiudad() {
		UUID idNuevaCiudad;
		CiudadEntidad ciudadExistente;

		do {
			idNuevaCiudad = UUID.randomUUID();
			ciudadExistente = daoFactory.obtenerCiudadDAO().consultarPorId(idNuevaCiudad);
		} while (Objects.nonNull(ciudadExistente));

		return idNuevaCiudad;
	}

	// 5. Guardar la nueva ciudad.
	private void guardar(final CiudadDominio ciudad, final UUID id) {
		var departamentoEntidad = DepartamentoEntidad.builder()
				.id(ciudad.getDepartamento().getId())
				.build();

		var ciudadEntidad = CiudadEntidad.builder()
				.id(id)
				.nombre(ciudad.getNombre().trim())
				.departamento(departamentoEntidad)
				.build();

		daoFactory.obtenerCiudadDAO().crear(ciudadEntidad);
	}
}
