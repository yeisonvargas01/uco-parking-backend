package co.edu.uco.ucoparking.negocio.casouso.departamento.impl;

import java.util.List;
import java.util.Objects;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.departamento.ModificarDepartamentoCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.DepartamentoDominio;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.NegocioUcoParkingExcepcion;

public final class ModificarDepartamentoCasoUsoImpl implements ModificarDepartamentoCasoUso {

	private final DAOFactory daoFactory;

	public ModificarDepartamentoCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ejecutar(final DepartamentoDominio datos) {
		validarIntegridadDatosDepartamento(datos);
		validarExisteDepartamentoConId(datos);
		validarExistePaisAsociado(datos);
		validarNoExisteOtroDepartamentoConMismoNombreEnElMismoPais(datos);
		modificar(datos);
	}

	// 1. Validación de datos consistentes:
	// tipo de dato, longitud, obligatoriedad, formato y rango.
	private void validarIntegridadDatosDepartamento(final DepartamentoDominio datos) {
		if (Objects.isNull(datos)) {
			throw NegocioUcoParkingExcepcion.crear("Los datos del departamento son obligatorios.");
		}

		if (Objects.isNull(datos.getId())) {
			throw NegocioUcoParkingExcepcion.crear("El identificador del departamento es obligatorio.");
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

	// 2. El departamento que se desea modificar debe existir.
	private void validarExisteDepartamentoConId(final DepartamentoDominio datos) {
		var departamentoExistente = daoFactory.obtenerDepartamentoDAO().consultarPorId(datos.getId());

		if (Objects.isNull(departamentoExistente)) {
			throw NegocioUcoParkingExcepcion.crear(
					"No existe un departamento registrado con el identificador indicado.");
		}
	}

	// 3. El país asociado al departamento debe existir.
	private void validarExistePaisAsociado(final DepartamentoDominio datos) {
		var paisExistente = daoFactory.obtenerPaisDAO().consultarPorId(datos.getPais().getId());

		if (Objects.isNull(paisExistente)) {
			throw NegocioUcoParkingExcepcion.crear(
					"No existe un país registrado con el identificador indicado.");
		}
	}

	// 4. No debe existir otro departamento con el mismo nombre en el mismo país.
	private void validarNoExisteOtroDepartamentoConMismoNombreEnElMismoPais(final DepartamentoDominio datos) {
		var paisFiltro = PaisEntidad.builder()
				.id(datos.getPais().getId())
				.build();

		var departamentoEntidadFiltro = DepartamentoEntidad.builder()
				.nombre(datos.getNombre().trim())
				.pais(paisFiltro)
				.build();

		List<DepartamentoEntidad> resultados = daoFactory.obtenerDepartamentoDAO().consultar(departamentoEntidadFiltro);

		if (Objects.nonNull(resultados)) {
			for (DepartamentoEntidad departamento : resultados) {
				var esOtroDepartamento = !departamento.getId().equals(datos.getId());

				if (esOtroDepartamento) {
					throw NegocioUcoParkingExcepcion.crear(
							"Ya existe otro departamento registrado con el mismo nombre en el país indicado.");
				}
			}
		}
	}

	// 5. Modificar la información del departamento.
	private void modificar(final DepartamentoDominio datos) {
		var paisEntidad = PaisEntidad.builder()
				.id(datos.getPais().getId())
				.build();

		var departamentoEntidad = DepartamentoEntidad.builder()
				.id(datos.getId())
				.nombre(datos.getNombre().trim())
				.pais(paisEntidad)
				.build();

		daoFactory.obtenerDepartamentoDAO().actualizar(departamentoEntidad);
	}
}
