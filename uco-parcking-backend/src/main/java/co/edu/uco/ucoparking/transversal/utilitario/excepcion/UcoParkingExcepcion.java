package co.edu.uco.ucoparking.transversal.utilitario.excepcion;

public abstract class UcoParkingExcepcion extends RuntimeException {

	private static final long serialVersionUID = -127481128908084318L;

	public UcoParkingExcepcion(String mensaje) {
		super(mensaje);
	}

	public UcoParkingExcepcion(String mensaje, Throwable excepcionRaiz) {
		super(mensaje, excepcionRaiz);
	}
}