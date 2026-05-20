package co.edu.uco.ucoparking.controlador.respuesta;

import java.time.LocalDateTime;

import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;

public record RespuestaExito<T>(String mensaje, LocalDateTime fecha, T datos) {

	public static <T> RespuestaExito<T> crear(final String mensaje, final T datos) {
		return new RespuestaExito<>(UtilTexto.aplicarTrim(mensaje), LocalDateTime.now(), datos);
	}

	public static RespuestaExito<Void> crear(final String mensaje) {
		return new RespuestaExito<>(UtilTexto.aplicarTrim(mensaje), LocalDateTime.now(), null);
	}
}