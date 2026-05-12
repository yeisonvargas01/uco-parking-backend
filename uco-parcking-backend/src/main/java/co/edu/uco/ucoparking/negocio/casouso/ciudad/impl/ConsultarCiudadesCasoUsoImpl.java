
package co.edu.uco.ucoparking.negocio.casouso.ciudad.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.CiudadEntidad;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.ciudad.ConsultarCiudadesCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.CiudadDominio;
import co.edu.uco.ucoparking.negocio.dominio.DepartamentoDominio;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;

public final class ConsultarCiudadesCasoUsoImpl implements ConsultarCiudadesCasoUso {

	private final DAOFactory daoFactory;

	public ConsultarCiudadesCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public List<CiudadDominio> ejecutar(final CiudadDominio filtro) {
		var ciudadEntidadFiltro = convertirDominioAEntidad(filtro);

		var ciudadesEntidad = daoFactory.obtenerCiudadDAO().consultar(ciudadEntidadFiltro);

		return convertirEntidadesADominios(ciudadesEntidad);
	}

	// 1. Preparar filtro de consulta.
	private CiudadEntidad convertirDominioAEntidad(final CiudadDominio filtro) {
		if (Objects.isNull(filtro)) {
			return CiudadEntidad.builder().build();
		}

		return CiudadEntidad.builder()
				.id(filtro.getId())
				.nombre(filtro.getNombre())
				.departamento(convertirDepartamentoDominioAEntidad(filtro.getDepartamento()))
				.build();
	}

	// 2. Convertir el departamento del filtro de dominio a entidad.
	// Ciudad depende de Departamento, por eso cuando se consulta por filtro
	// también se debe poder filtrar por el departamento asociado.
	private DepartamentoEntidad convertirDepartamentoDominioAEntidad(final DepartamentoDominio departamentoDominio) {
		if (Objects.isNull(departamentoDominio)) {
			return DepartamentoEntidad.builder().build();
		}

		return DepartamentoEntidad.builder()
				.id(departamentoDominio.getId())
				.nombre(departamentoDominio.getNombre())
				.pais(convertirPaisDominioAEntidad(departamentoDominio.getPais()))
				.build();
	}

	// 3. Convertir el país del filtro de dominio a entidad.
	// Departamento depende de País, por eso también se conserva esa relación
	// dentro del filtro cuando sea necesario.
	private PaisEntidad convertirPaisDominioAEntidad(final PaisDominio paisDominio) {
		if (Objects.isNull(paisDominio)) {
			return PaisEntidad.builder().build();
		}

		return PaisEntidad.builder()
				.id(paisDominio.getId())
				.nombre(paisDominio.getNombre())
				.build();
	}

	// 4. Convertir la lista de entidades a lista de dominio.
	private List<CiudadDominio> convertirEntidadesADominios(final List<CiudadEntidad> ciudadesEntidad) {
		if (Objects.isNull(ciudadesEntidad) || ciudadesEntidad.isEmpty()) {
			return Collections.emptyList();
		}

		return ciudadesEntidad.stream()
				.map(this::convertirEntidadADominio)
				.toList();
	}

	// 5. Convertir cada CiudadEntidad a CiudadDominio.
	private CiudadDominio convertirEntidadADominio(final CiudadEntidad ciudadEntidad) {
		return CiudadDominio.builder()
				.id(ciudadEntidad.getId())
				.nombre(ciudadEntidad.getNombre())
				.departamento(convertirDepartamentoEntidadADominio(ciudadEntidad.getDepartamento()))
				.build();
	}

	// 6. Convertir el departamento asociado de entidad a dominio.
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

	// 7. Convertir el país asociado de entidad a dominio.
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