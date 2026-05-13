package co.edu.uco.ucoparking.negocio.fachada;

public interface FachadaConRetorno<E, S> {

	S ejecutar(E datos);
}
