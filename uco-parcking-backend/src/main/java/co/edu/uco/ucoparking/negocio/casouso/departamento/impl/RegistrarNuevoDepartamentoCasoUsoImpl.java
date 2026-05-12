package co.edu.uco.ucoparking.negocio.casouso.departamento.impl;

import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.departamento.RegistrarNuevoDepartamentoCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.DepartamentoDominio;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class RegistrarNuevoDepartamentoCasoUsoImpl implements RegistrarNuevoDepartamentoCasoUso {

	private final DAOFactory daoFactory;

	public RegistrarNuevoDepartamentoCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final DepartamentoDominio datos) {
		validarIntegridadDatosDepartamento(datos);
		validarExistePaisAsociado(datos);
		validarNoExisteOtroDepartamentoConMismoNombreEnElMismoPais(datos);

		var idNuevoDepartamento = generarIdUnicoNuevoDepartamento();

		guardar(datos, idNuevoDepartamento);
	}

	// 1. Validación de datos consistentes:
	// tipo de dato, longitud, obligatoriedad, formato y rango.
	private void validarIntegridadDatosDepartamento(final DepartamentoDominio datos) {
		if (Objects.isNull(datos)) {
			throw NegocioUcoParkingExcepcion.crear("Los datos del departamento son obligatorios.");
		}

		if (Objects.isNull(datos.getNombre()) || datos.getNombre().trim().isEmpty()) {
			throw NegocioUcoParkingExcepcion.crear("El nombre del departamento es obligatorio.");
		}

		if (Objects.isNull(datos.getPais())) {
			throw NegocioUcoParkingExcepcion.crear("El país del departamento es obligatorio.");
		}

		if (Objects.isNull(datos.getPais().getId())) {
			throw NegocioUcoParkingExcepcion.crear("El identificador del país es obligatorio.");
		}
	}

	// 2. El país asociado al departamento debe existir.
	private void validarExistePaisAsociado(final DepartamentoDominio datos) {
		var paisExistente = daoFactory.obtenerPaisDAO().consultarPorId(datos.getPais().getId());

		if (Objects.isNull(paisExistente)) {
			throw NegocioUcoParkingExcepcion.crear("No existe un país registrado con el identificador indicado.");
		}
	}

	// 3. No debe existir otro departamento con el mismo nombre en el mismo país.
	private void validarNoExisteOtroDepartamentoConMismoNombreEnElMismoPais(final DepartamentoDominio datos) {
		var paisFiltro = PaisEntidad.builder()
				.id(datos.getPais().getId())
				.build();

		var departamentoEntidadFiltro = DepartamentoEntidad.builder()
				.nombre(datos.getNombre().trim())
				.pais(paisFiltro)
				.build();

		var resultados = daoFactory.obtenerDepartamentoDAO().consultar(departamentoEntidadFiltro);

		if (Objects.nonNull(resultados) && !resultados.isEmpty()) {
			throw NegocioUcoParkingExcepcion.crear(
					"Ya existe un departamento registrado con el mismo nombre en el país indicado.");
		}
	}

	// 4. El id del departamento debe ser único.
	private UUID generarIdUnicoNuevoDepartamento() {
		UUID idNuevoDepartamento;
		DepartamentoEntidad departamentoExistente;

		do {
			idNuevoDepartamento = UUID.randomUUID();
			departamentoExistente = daoFactory.obtenerDepartamentoDAO().consultarPorId(idNuevoDepartamento);
		} while (Objects.nonNull(departamentoExistente));

		return idNuevoDepartamento;
	}

	// 5. Guardar el nuevo departamento.
	private void guardar(final DepartamentoDominio departamento, final UUID id) {
		var paisEntidad = PaisEntidad.builder()
				.id(departamento.getPais().getId())
				.build();

		var departamentoEntidad = DepartamentoEntidad.builder()
				.id(id)
				.nombre(departamento.getNombre().trim())
				.pais(paisEntidad)
				.build();

		daoFactory.obtenerDepartamentoDAO().crear(departamentoEntidad);
	}
}
