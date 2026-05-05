package co.edu.uco.ucoparking.transversal.excepcion;

public final class DatosUcoParkingException extends UcoParkingException {

	private static final long serialVersionUID = 1L;

	private DatosUcoParkingException(final String mensajeUsuario, final String mensajeTecnico,
			final Throwable excepcionRaiz) {
		super(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}

	public static DatosUcoParkingException crear(final String mensajeUsuario, final String mensajeTecnico) {
		return new DatosUcoParkingException(mensajeUsuario, mensajeTecnico, null);
	}

	public static DatosUcoParkingException crear(final String mensajeUsuario, final String mensajeTecnico,
			final Throwable excepcionRaiz) {
		return new DatosUcoParkingException(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
