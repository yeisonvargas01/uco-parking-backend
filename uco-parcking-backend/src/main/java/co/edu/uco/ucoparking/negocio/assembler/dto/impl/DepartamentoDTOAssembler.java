package co.edu.uco.ucoparking.negocio.assembler.dto.impl;

import co.edu.uco.ucoparking.dto.DepartamentoDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.ucoparking.negocio.dominio.DepartamentoDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;

public final class DepartamentoDTOAssembler implements DTOAssembler<DepartamentoDominio, DepartamentoDTO> {

	private static DepartamentoDTOAssembler INSTANCE = null;

	private DepartamentoDTOAssembler() {
		super();
	}

	public static synchronized DepartamentoDTOAssembler getInstance() {
		if (UtilObjeto.esNula(INSTANCE)) {
			INSTANCE = new DepartamentoDTOAssembler();
		}

		return INSTANCE;
	}

	@Override
	public DepartamentoDTO ensamblarDTO(final DepartamentoDominio dominio) {
		var departamentoEnsamblar = UtilObjeto.obtenerValorDefecto(
				dominio, DepartamentoDominio.builder().build());

		return DepartamentoDTO.builder()
				.id(departamentoEnsamblar.getId())
				.nombre(departamentoEnsamblar.getNombre())
				.pais(PaisDTOAssembler.getInstance().ensamblarDTO(departamentoEnsamblar.getPais()))
				.build();
	}

	@Override
	public DepartamentoDominio ensamblarDominio(final DepartamentoDTO dto) {
		var departamentoEnsamblar = UtilObjeto.obtenerValorDefecto(
				dto, DepartamentoDTO.builder().build());

		return DepartamentoDominio.builder()
				.id(departamentoEnsamblar.getId())
				.nombre(departamentoEnsamblar.getNombre())
				.pais(PaisDTOAssembler.getInstance().ensamblarDominio(departamentoEnsamblar.getPais()))
				.build();
	}
}