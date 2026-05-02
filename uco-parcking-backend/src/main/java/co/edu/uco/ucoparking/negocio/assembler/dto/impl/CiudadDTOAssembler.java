package co.edu.uco.ucoparking.negocio.assembler.dto.impl;

import co.edu.uco.ucoparking.dto.CiudadDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.ucoparking.negocio.dominio.CiudadDominio;
import co.edu.uco.ucoparking.transversal.UtilObjeto;

public final class CiudadDTOAssembler implements DTOAssembler<CiudadDominio, CiudadDTO> {

	private static CiudadDTOAssembler INSTANCE = null;

	private CiudadDTOAssembler() {
		super();
	}

	public synchronized static final CiudadDTOAssembler getInstance() {
		if (UtilObjeto.esNula(INSTANCE)) {
			INSTANCE = new CiudadDTOAssembler();
		}

		return INSTANCE;
	}

	@Override
	public CiudadDominio ensamblarDTO(final CiudadDTO dto) {
		var ciudadEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new CiudadDTO.Builder().build());

		return new CiudadDominio.Builder()
				.id(ciudadEnsamblar.getId())
				.nombre(ciudadEnsamblar.getNombre())
				.departamento(DepartamentoDTOAssembler.getInstance().ensamblarDTO(ciudadEnsamblar.getDepartamento()))
				.build();
	}

	@Override
	public CiudadDTO ensamblarDominio(final CiudadDominio dominio) {
		var ciudadEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new CiudadDominio.Builder().build());

		return new CiudadDTO.Builder()
				.id(ciudadEnsamblar.getId())
				.nombre(ciudadEnsamblar.getNombre())
				.departamento(DepartamentoDTOAssembler.getInstance().ensamblarDominio(ciudadEnsamblar.getDepartamento()))
				.build();
	}

	public CiudadDominio ensamblarDominio(final CiudadDTO dto) {
		return ensamblarDTO(dto);
	}
}
