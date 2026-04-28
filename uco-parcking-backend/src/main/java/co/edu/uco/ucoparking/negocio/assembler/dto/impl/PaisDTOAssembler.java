package co.edu.uco.ucoparking.negocio.assembler.dto.impl;

import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.assembler.dto.DTOAssembler;
import co.edu.uco.ucoparking.negocio.dominio.PaisDominio;
import co.edu.uco.ucoparking.transversal.UtilObjeto;


public final class PaisDTOAssembler implements DTOAssembler<PaisDominio, PaisDTO> {
	
	private static PaisDTOAssembler INSTANCE = null;
	
	private PaisDTOAssembler() {
		super();
	}
	
	public synchronized static final PaisDTOAssembler getInstance() {
		
		if(UtilObjeto.esNula(INSTANCE)) {
			INSTANCE = new PaisDTOAssembler();
		}
		
		return INSTANCE;
	
	}
	
	@Override
	public PaisDominio ensamblarDTO(final PaisDTO dominio) {
		var paisAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PaisDTO.Builder().build());
		return new PaisDominio.Builder().id(paisAEnsamblar.getId()).nombre(paisAEnsamblar.getNombre()).build();				 
	}

	@Override
	public PaisDTO ensamblarDominio(final PaisDominio dominio) {
		var paisAEnsamblar = UtilObjeto.obtenerValorDefecto(dominio, new PaisDominio.Builder().build());
		return new PaisDTO.Builder().id(paisAEnsamblar.getId()).nombre(paisAEnsamblar.getNombre()).build();
	}
	
	public PaisDominio ensamblarDominio(final PaisDTO dto) {
		return ensamblarDTO(dto);
	}
	
	public static void main (String[] args) {
		var miPaisDTO = new PaisDTO.Builder().id(null).build();
		var miPaisDominio = PaisDTOAssembler.getInstance().ensamblarDominio(miPaisDTO);
		
	}
	
}