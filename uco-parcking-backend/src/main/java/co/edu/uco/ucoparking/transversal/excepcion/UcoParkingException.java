package co.edu.uco.ucoparking.transversal.excepcion;

public abstract class UcoParkingException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String mensajeUsuario;
	private final String mensajeTecnico;

	protected UcoParkingException(final String mensajeUsuario, final String mensajeTecnico, final Throwable excepcionRaiz) {
		super(mensajeTecnico, excepcionRaiz);
		this.mensajeUsuario = mensajeUsuario;
		this.mensajeTecnico = mensajeTecnico;
	}

	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

	public String getMensajeTecnico() {
		return mensajeTecnico;
	}
}