package co.edu.uco.ucoparking.transversal.excepcion;

public final class TransversalUcoParkingException extends UcoParkingException {

	private static final long serialVersionUID = 1L;

	private TransversalUcoParkingException(final String mensajeUsuario, final String mensajeTecnico,
			final Throwable excepcionRaiz) {
		super(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}

	public static TransversalUcoParkingException crear(final String mensajeUsuario, final String mensajeTecnico) {
		return new TransversalUcoParkingException(mensajeUsuario, mensajeTecnico, null);
	}

	public static TransversalUcoParkingException crear(final String mensajeUsuario, final String mensajeTecnico,
			final Throwable excepcionRaiz) {
		return new TransversalUcoParkingException(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}