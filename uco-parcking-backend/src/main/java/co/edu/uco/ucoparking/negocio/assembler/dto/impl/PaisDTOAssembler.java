package co.edu.uco.ucoparking.negocio.assembler.dto.impl;

import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;

public final class PaisDTOAssembler implements DTOAssembler<PaisDominio, PaisDTO> {

	private static PaisDTOAssembler INSTANCE = null;

	private PaisDTOAssembler() {
		super();
	}

	public static synchronized PaisDTOAssembler getInstance() {
		if (UtilObjeto.esNula(INSTANCE)) {
			INSTANCE = new PaisDTOAssembler();
		}

		return INSTANCE;
	}

	@Override
	public PaisDTO ensamblarDTO(final PaisDominio dominio) {
		var paisEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, PaisDominio.builder().build());

		return PaisDTO.builder()
				.id(paisEnsamblar.getId())
				.nombre(paisEnsamblar.getNombre())
				.build();
	}

	@Override
	public PaisDominio ensamblarDominio(final PaisDTO dto) {
		var paisEnsamblar = UtilObjeto.obtenerValorDefecto(dto, PaisDTO.builder().build());

		return PaisDominio.builder()
				.id(paisEnsamblar.getId())
				.nombre(paisEnsamblar.getNombre())
				.build();
	}
}