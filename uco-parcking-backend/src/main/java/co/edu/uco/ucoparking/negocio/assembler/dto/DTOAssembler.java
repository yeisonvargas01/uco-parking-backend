package co.edu.uco.ucoparking.negocio.assembler.dto;

public interface DTOAssembler<D, T> {
	
	D ensamblarDTO(T dominio);
	
	T ensamblarDominio(D dto);

}
