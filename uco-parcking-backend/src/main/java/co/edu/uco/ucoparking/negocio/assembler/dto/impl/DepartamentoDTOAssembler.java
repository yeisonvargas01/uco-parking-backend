package co.edu.uco.ucoparking.negocio.assembler.dto.impl;

import co.edu.uco.ucoparking.dto.DepartamentoDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.ucoparking.negocio.dominio.DepartamentoDominio;
import co.edu.uco.ucoparking.transversal.UtilObjeto;

public final class DepartamentoDTOAssembler implements DTOAssembler<DepartamentoDominio, DepartamentoDTO> {

	private static DepartamentoDTOAssembler INSTANCE = null;

	private DepartamentoDTOAssembler() {
		super();
	}

	public synchronized static final DepartamentoDTOAssembler getInstance() {
		if (UtilObjeto.esNula(INSTANCE)) {
			INSTANCE = new DepartamentoDTOAssembler();
		}

		return INSTANCE;
	}

	@Override
	public DepartamentoDominio ensamblarDTO(final DepartamentoDTO dto) {
		var departamentoEnsamblar = UtilObjeto.obtenerValorDefecto(dto, new DepartamentoDTO.Builder().build());

		return new DepartamentoDominio.Builder()
				.id(departamentoEnsamblar.getId())
				.nombre(departamentoEnsamblar.getNombre())
				.pais(PaisDTOAssembler.getInstance().ensamblarDTO(departamentoEnsamblar.getPais()))
				.build();
	}

	@Override
	public DepartamentoDTO ensamblarDominio(final DepartamentoDominio dominio) {
		var departamentoEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new DepartamentoDominio.Builder().build());

		return new DepartamentoDTO.Builder()
				.id(departamentoEnsamblar.getId())
				.nombre(departamentoEnsamblar.getNombre())
				.pais(PaisDTOAssembler.getInstance().ensamblarDominio(departamentoEnsamblar.getPais()))
				.build();
	}

	public DepartamentoDominio ensamblarDominio(final DepartamentoDTO dto) {
		return ensamblarDTO(dto);
	}
}