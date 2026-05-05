package co.edu.uco.ucoparking.transversal.excepcion;

public final class NegocioUcoParkingException extends UcoParkingException {

	private static final long serialVersionUID = 1L;

	private NegocioUcoParkingException(final String mensajeUsuario, final String mensajeTecnico,
			final Throwable excepcionRaiz) {
		super(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}

	public static NegocioUcoParkingException crear(final String mensajeUsuario, final String mensajeTecnico) {
		return new NegocioUcoParkingException(mensajeUsuario, mensajeTecnico, null);
	}

	public static NegocioUcoParkingException crear(final String mensajeUsuario, final String mensajeTecnico,
			final Throwable excepcionRaiz) {
		return new NegocioUcoParkingException(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
