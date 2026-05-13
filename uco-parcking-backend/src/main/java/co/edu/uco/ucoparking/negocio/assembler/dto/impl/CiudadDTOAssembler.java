package co.edu.uco.ucoparking.negocio.assembler.dto.impl;

import co.edu.uco.ucoparking.dto.CiudadDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.ucoparking.negocio.dominio.CiudadDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;

public final class CiudadDTOAssembler implements DTOAssembler<CiudadDominio, CiudadDTO> {

	private static CiudadDTOAssembler INSTANCE = null;

	private CiudadDTOAssembler() {
		super();
	}

	public static synchronized CiudadDTOAssembler getInstance() {
		if (UtilObjeto.esNula(INSTANCE)) {
			INSTANCE = new CiudadDTOAssembler();
		}

		return INSTANCE;
	}

	@Override
	public CiudadDTO ensamblarDTO(final CiudadDominio dominio) {
		var ciudadEnsamblar = UtilObjeto.obtenerValorDefecto(
				dominio, CiudadDominio.builder().build());

		return CiudadDTO.builder()
				.id(ciudadEnsamblar.getId())
				.nombre(ciudadEnsamblar.getNombre())
				.departamento(DepartamentoDTOAssembler.getInstance().ensamblarDTO(ciudadEnsamblar.getDepartamento()))
				.build();
	}

	@Override
	public CiudadDominio ensamblarDominio(final CiudadDTO dto) {
		var ciudadEnsamblar = UtilObjeto.obtenerValorDefecto(
				dto, CiudadDTO.builder().build());

		return CiudadDominio.builder()
				.id(ciudadEnsamblar.getId())
				.nombre(ciudadEnsamblar.getNombre())
				.departamento(DepartamentoDTOAssembler.getInstance().ensamblarDominio(ciudadEnsamblar.getDepartamento()))
				.build();
	}
}