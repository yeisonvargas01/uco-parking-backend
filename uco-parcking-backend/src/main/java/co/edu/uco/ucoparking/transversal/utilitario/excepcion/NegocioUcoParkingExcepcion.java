package co.edu.uco.ucoparking.transversal.utilitario.excepcion;

public final class NegocioUcoParkingExcepcion extends UcoParkingExcepcion {

	private static final long serialVersionUID = -127481128908084318L;

	private NegocioUcoParkingExcepcion(final String mensaje) {
		super(mensaje);
	}

	private NegocioUcoParkingExcepcion(final String mensaje, final Throwable excepcionRaiz) {
		super(mensaje, excepcionRaiz);
	}

	public static NegocioUcoParkingExcepcion crear(final String mensaje) {
		return new NegocioUcoParkingExcepcion(mensaje);
	}

	public static NegocioUcoParkingExcepcion crear(final String mensaje, final Throwable excepcionRaiz) {
		return new NegocioUcoParkingExcepcion(mensaje, excepcionRaiz);
	}
}
