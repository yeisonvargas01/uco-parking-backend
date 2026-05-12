package co.edu.uco.ucoparking.transversal.utilitario.excepcion;

public final class DatosUcoParkingExcepcion extends UcoParkingExcepcion {

	private static final long serialVersionUID = -127481128908084318L;

	private DatosUcoParkingExcepcion(final String mensaje) {
		super(mensaje);
	}

	private DatosUcoParkingExcepcion(final String mensaje, final Throwable excepcionRaiz) {
		super(mensaje, excepcionRaiz);
	}

	public static DatosUcoParkingExcepcion crear(final String mensaje) {
		return new DatosUcoParkingExcepcion(mensaje);
	}

	public static DatosUcoParkingExcepcion crear(final String mensaje, final Throwable excepcionRaiz) {
		return new DatosUcoParkingExcepcion(mensaje, excepcionRaiz);
	}
}
