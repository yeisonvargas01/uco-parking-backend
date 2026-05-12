package co.edu.uco.ucoparking.negocio.casouso.departamento.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.negocio.casouso.pais.ConsultarPaisesCasoUso;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;

public final class ConsultarPaisesCasoUsoImpl implements ConsultarPaisesCasoUso {

	private final DAOFactory daoFactory;

	public ConsultarPaisesCasoUsoImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public List<PaisDominio> ejecutar(final PaisDominio filtro) {
		var paisEntidadFiltro = convertirDominioAEntidad(filtro);

		var paisesEntidad = daoFactory.obtenerPaisDAO().consultar(paisEntidadFiltro);

		return convertirEntidadesADominios(paisesEntidad);
	}

	// 1. Preparar filtro de consulta.
	private PaisEntidad convertirDominioAEntidad(final PaisDominio filtro) {
		if (Objects.isNull(filtro)) {
			return PaisEntidad.builder().build();
		}

		return PaisEntidad.builder()
				.id(filtro.getId())
				.nombre(filtro.getNombre())
				.build();
	}

	// 2. Convertir la lista de entidades a lista de dominio.
	private List<PaisDominio> convertirEntidadesADominios(final List<PaisEntidad> paisesEntidad) {
		if (Objects.isNull(paisesEntidad) || paisesEntidad.isEmpty()) {
			return Collections.emptyList();
		}

		return paisesEntidad.stream()
				.map(this::convertirEntidadADominio)
				.toList();
	}

	// 3. Convertir cada PaísEntidad a PaísDominio.
	private PaisDominio convertirEntidadADominio(final PaisEntidad paisEntidad) {
		return PaisDominio.builder()
				.id(paisEntidad.getId())
				.nombre(paisEntidad.getNombre())
				.build();
	}
}