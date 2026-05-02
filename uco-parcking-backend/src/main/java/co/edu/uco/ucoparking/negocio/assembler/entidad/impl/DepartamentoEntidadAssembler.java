package co.edu.uco.ucoparking.negocio.assembler.entidad.impl;

import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.negocio.assembler.entidad.EntidadAssembler;
import co.edu.uco.ucoparking.negocio.dominio.DepartamentoDominio;
import co.edu.uco.ucoparking.transversal.UtilObjeto;

public final class DepartamentoEntidadAssembler implements EntidadAssembler<DepartamentoDominio, DepartamentoEntidad> {

	private static DepartamentoEntidadAssembler INSTANCE = null;

	private DepartamentoEntidadAssembler() {
		super();
	}

	public synchronized static final DepartamentoEntidadAssembler getInstance() {
		if (UtilObjeto.esNula(INSTANCE)) {
			INSTANCE = new DepartamentoEntidadAssembler();
		}

		return INSTANCE;
	}

	@Override
	public DepartamentoDominio ensamblarDominio(final DepartamentoEntidad entidad) {
		var departamentoEnsamblar = UtilObjeto.obtenerValorDefecto(entidad, new DepartamentoEntidad.Builder().build());

		return new DepartamentoDominio.Builder()
				.id(departamentoEnsamblar.getId())
				.nombre(departamentoEnsamblar.getNombre())
				.pais(PaisEntidadAssembler.getInstance().ensamblarDominio(departamentoEnsamblar.getPais()))
				.build();
	}

	@Override
	public DepartamentoEntidad ensamblarEntidad(final DepartamentoDominio dominio) {
		var departamentoEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new DepartamentoDominio.Builder().build());

		return new DepartamentoEntidad.Builder()
				.id(departamentoEnsamblar.getId())
				.nombre(departamentoEnsamblar.getNombre())
				.pais(PaisEntidadAssembler.getInstance().ensamblarEntidad(departamentoEnsamblar.getPais()))
				.build();
	}
}
