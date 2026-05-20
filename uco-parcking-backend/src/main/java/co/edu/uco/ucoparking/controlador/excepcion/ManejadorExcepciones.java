package co.edu.uco.ucoparking.controlador.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.edu.uco.ucoparking.controlador.respuesta.RespuestaError;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.UcoParkingExcepcion;

@RestControllerAdvice
public class ManejadorExcepciones {

	@ExceptionHandler(UcoParkingExcepcion.class)
	public ResponseEntity<RespuestaError> gestionarUcoParkingExcepcion(final UcoParkingExcepcion excepcion) {

		System.err.println(excepcion.getMensajeTecnico());

		if (excepcion.getExcepcionRaiz() != null) {
			excepcion.getExcepcionRaiz().printStackTrace();
		}

		return new ResponseEntity<>(
				RespuestaError.crear(excepcion.getMensajeUsuario()),
				HttpStatus.BAD_REQUEST
		);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RespuestaError> gestionarExcepcionGenerica(final Exception excepcion) {

		System.err.println("Excepción no controlada...");
		excepcion.printStackTrace();

		return new ResponseEntity<>(
				RespuestaError.crear("Ocurrió un error inesperado. Por favor, inténtelo nuevamente."),
				HttpStatus.INTERNAL_SERVER_ERROR
		);
	}
}