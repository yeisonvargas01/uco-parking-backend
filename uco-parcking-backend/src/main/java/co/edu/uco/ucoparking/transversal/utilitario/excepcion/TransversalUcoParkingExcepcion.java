package co.edu.uco.ucoparking.transversal.utilitario.excepcion;

public final class TransversalUcoParkingExcepcion extends UcoParkingExcepcion {

	private static final long serialVersionUID = -127481128908084318L;

	private TransversalUcoParkingExcepcion(final String mensaje) {
		super(mensaje);
	}

	private TransversalUcoParkingExcepcion(final String mensaje, final Throwable excepcionRaiz) {
		super(mensaje, excepcionRaiz);
	}

	public static TransversalUcoParkingExcepcion crear(final String mensaje) {
		return new TransversalUcoParkingExcepcion(mensaje);
	}

	public static TransversalUcoParkingExcepcion crear(final String mensaje, final Throwable excepcionRaiz) {
		return new TransversalUcoParkingExcepcion(mensaje, excepcionRaiz);
	}
}