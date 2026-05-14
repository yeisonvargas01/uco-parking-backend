package co.edu.uco.ucoparking.negocio.fachada.ciudad;

import java.util.UUID;

import co.edu.uco.ucoparking.dto.CiudadDTO;
import co.edu.uco.ucoparking.negocio.fachada.FachadaConRetorno;

public interface ConsultarCiudadPorIdFachada extends FachadaConRetorno<UUID, CiudadDTO> {

}