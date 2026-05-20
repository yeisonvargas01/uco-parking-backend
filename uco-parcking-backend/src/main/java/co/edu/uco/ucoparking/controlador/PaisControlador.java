package co.edu.uco.ucoparking.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.ucoparking.controlador.respuesta.RespuestaExito;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.ConsultarPaisPorIdFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.ConsultarPaisesFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.EliminarPaisFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.ModificarPaisFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.RegistrarNuevoPaisFachadaImpl;

@RestController
@RequestMapping("/api/v1/paises")
public class PaisControlador {

	@PostMapping	
	public void registrarNuevoPais(PaisDTO pais) {
		system.out.println("Entre al método registrar nuevo pais");	
	}
	
	@PutMapping
	public void modificarPais(PaisDTO pais) {
		system.out.println("Entre al método modificarInformacionPaisExistente");	
	}
	
	@DeleteMapping
	public void darBajaPaisExistente() {
		system.out.println("Entre al método darBajaPaisExistente");	
	}
	
	@GetMapping("/{id}")
	public void consultarPaisPorId(@PathVariable UUID id) {
		return "Entré al método ConsultarPaisPorId ".concat(id.toString());	
	}
	
	@GetMapping
	public void consultarPaisesPorFiltro() {
		system.out.println("Entre al método consultarPaisesPorFiltro");	
	}
	
	@GetMapping
	public void consultarTodosPaises() {
		system.out.println("Entre al método consultarPaises");	
	}


}