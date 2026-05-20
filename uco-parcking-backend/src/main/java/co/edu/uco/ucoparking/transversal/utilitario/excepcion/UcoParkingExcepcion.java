package co.edu.uco.ucoparking.transversal.utilitario.excepcion;

public abstract class UcoParkingExcepcion extends RuntimeException {

	private static final long serialVersionUID = -127481128908084318L;

	private final String mensajeUsuario;
	private final String mensajeTecnico;
	private final Throwable excepcionRaiz;

	protected UcoParkingExcepcion(final String mensajeUsuario, final String mensajeTecnico,
			final Throwable excepcionRaiz) {
		super(mensajeUsuario, excepcionRaiz);
		this.mensajeUsuario = mensajeUsuario;
		this.mensajeTecnico = mensajeTecnico;
		this.excepcionRaiz = excepcionRaiz;
	}

	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

	public String getMensajeTecnico() {
		return mensajeTecnico;
	}

	public Throwable getExcepcionRaiz() {
		return excepcionRaiz;
	}
}
