package co.edu.uco.ucoparking.negocio.assembler.entidad;

public interface EntidadAssembler<D, E> {

    D ensamblarDominio(E entidad);

    E ensamblarEntidad(D dominio);
    
}