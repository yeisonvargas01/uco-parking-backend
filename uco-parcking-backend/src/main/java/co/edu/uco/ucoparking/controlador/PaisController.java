package co.edu.uco.ucoparking.controlador;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.ConsultarPaisPorIdFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.ConsultarPaisesFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.EliminarPaisFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.ModificarPaisFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.RegistrarNuevoPaisFachadaImpl;

@RestController
@RequestMapping("/api/v1/paises")
public class PaisController {

	@PostMapping
	public ResponseEntity<String> registrar(@RequestBody PaisRequest request) {
		var pais = PaisDTO.builder()
				.nombre(request.getNombre())
				.build();

		new RegistrarNuevoPaisFachadaImpl().ejecutar(pais);

		return ResponseEntity.ok("País registrado correctamente.");
	}

	@GetMapping
	public ResponseEntity<List<PaisDTO>> consultarTodos() {
		var paises = new ConsultarPaisesFachadaImpl()
				.ejecutar(PaisDTO.builder().build());

		return ResponseEntity.ok(paises);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaisDTO> consultarPorId(@PathVariable UUID id) {
		var pais = new ConsultarPaisPorIdFachadaImpl().ejecutar(id);

		return ResponseEntity.ok(pais);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> modificar(@PathVariable UUID id, @RequestBody PaisRequest request) {
		var pais = PaisDTO.builder()
				.id(id)
				.nombre(request.getNombre())
				.build();

		new ModificarPaisFachadaImpl().ejecutar(pais);

		return ResponseEntity.ok("País modificado correctamente.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(@PathVariable UUID id) {
		new EliminarPaisFachadaImpl().ejecutar(id);

		return ResponseEntity.ok("País eliminado correctamente.");
	}

	public static class PaisRequest {

		private String nombre;

		public PaisRequest() {
			super();
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(final String nombre) {
			this.nombre = nombre;
		}
	}
}