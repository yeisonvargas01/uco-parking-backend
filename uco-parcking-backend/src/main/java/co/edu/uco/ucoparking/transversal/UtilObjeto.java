package co.edu.uco.ucoparking.transversal;

public class UtilObjeto {
	
	private UtilObjeto() {
		super();
	}
	
	public static <O> boolean esNula(final O objeto) {
		return objeto == null;
	}
	
	public static <O> O obtenerValorDefecto(final O objeto, final O valorDefecto) {
		return esNula(objeto) ? valorDefecto : objeto;
		
	}

}





