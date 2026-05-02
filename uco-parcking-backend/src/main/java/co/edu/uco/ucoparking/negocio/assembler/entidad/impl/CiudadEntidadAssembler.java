package co.edu.uco.ucoparking.negocio.assembler.entidad.impl;

import co.edu.uco.ucoparking.entidad.CiudadEntidad;
import co.edu.uco.ucoparking.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.ucoparking.negocio.dominio.CiudadDominio;
import co.edu.uco.ucoparking.transversal.UtilObjeto;

public final class CiudadEntidadAssembler implements EntidadAssembler<CiudadDominio, CiudadEntidad> {

	private static CiudadEntidadAssembler INSTANCE = null;

	private CiudadEntidadAssembler() {
		super();
	}

	public synchronized static final CiudadEntidadAssembler getInstance() {
		if (UtilObjeto.esNula(INSTANCE)) {
			INSTANCE = new CiudadEntidadAssembler();
		}

		return INSTANCE;
	}

	@Override
	public CiudadDominio ensamblarDominio(final CiudadEntidad entidad) {
		var ciudadEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new CiudadEntidad.Builder().build());

		return new CiudadDominio.Builder()
				.id(ciudadEnsamblar.getId())
				.nombre(ciudadEnsamblar.getNombre())
				.departamento(DepartamentoEntidadAssembler.getInstance().ensamblarDominio(ciudadEnsamblar.getDepartamento()))
				.build();
	}

	@Override
	public CiudadEntidad ensamblarEntidad(final CiudadDominio dominio) {
		var ciudadEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CiudadDominio.Builder().build());

		return new CiudadEntidad.Builder()
				.id(ciudadEnsamblar.getId())
				.nombre(ciudadEnsamblar.getNombre())
				.departamento(DepartamentoEntidadAssembler.getInstance().ensamblarEntidad(ciudadEnsamblar.getDepartamento()))
				.build();
	}
}