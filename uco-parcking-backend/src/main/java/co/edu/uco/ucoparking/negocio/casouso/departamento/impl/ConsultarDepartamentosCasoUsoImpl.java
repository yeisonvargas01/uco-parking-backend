package co.edu.uco.ucoparking.negocio.casouso.departamento.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.departamento.ConsultarDepartamentosCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.DepartamentoDominio;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;

public final class ConsultarDepartamentosCasoUsoImpl implements ConsultarDepartamentosCasoUso {

	private final DAOFactory daoFactory;

	public ConsultarDepartamentosCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public List<DepartamentoDominio> ejecutar(final DepartamentoDominio filtro) {
		var departamentoEntidadFiltro = convertirDominioAEntidad(filtro);

		var departamentosEntidad = daoFactory.obtenerDepartamentoDAO().consultar(departamentoEntidadFiltro);

		return convertirEntidadesADominios(departamentosEntidad);
	}

	// 1. Preparar filtro de consulta.
	// Si el filtro viene nulo, se crea un filtro vacío.
	// Esto permite consultar todos los departamentos o consultar por campos específicos:
	// - id
	// - nombre
	// - país asociado
	private DepartamentoEntidad convertirDominioAEntidad(final DepartamentoDominio filtro) {
		if (Objects.isNull(filtro)) {
			return DepartamentoEntidad.builder().build();
		}

		return DepartamentoEntidad.builder()
				.id(filtro.getId())
				.nombre(filtro.getNombre())
				.pais(convertirPaisDominioAEntidad(filtro.getPais()))
				.build();
	}

	// 2. Convertir el país del filtro de dominio a entidad.
	private PaisEntidad convertirPaisDominioAEntidad(final PaisDominio paisDominio) {
		if (Objects.isNull(paisDominio)) {
			return PaisEntidad.builder().build();
		}

		return PaisEntidad.builder()
				.id(paisDominio.getId())
				.nombre(paisDominio.getNombre())
				.build();
	}

	// 3. Convertir la lista de entidades a lista de dominio.
	private List<DepartamentoDominio> convertirEntidadesADominios(final List<DepartamentoEntidad> departamentosEntidad) {
		if (Objects.isNull(departamentosEntidad) || departamentosEntidad.isEmpty()) {
			return Collections.emptyList();
		}

		return departamentosEntidad.stream()
				.map(this::convertirEntidadADominio)
				.toList();
	}

	// 4. Convertir cada DepartamentoEntidad a DepartamentoDominio.
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
