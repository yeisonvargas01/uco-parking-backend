package co.edu.uco.ucoparking.negocio.fachada.departamento;

import java.util.UUID;

import co.edu.uco.ucoparking.dto.DepartamentoDTO;
import co.edu.uco.ucoparking.negocio.fachada.FachadaConRetorno;

public interface ConsultarDepartamentoPorIdFachada extends FachadaConRetorno<UUID, DepartamentoDTO> {

}