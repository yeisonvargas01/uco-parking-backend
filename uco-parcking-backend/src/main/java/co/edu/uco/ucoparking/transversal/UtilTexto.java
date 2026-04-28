package co.edu.uco.ucoparking.transversal;

public class UtilTexto {
	
	public static final String TEXTO_VACIO = "";
	
	private UtilTexto() {
		super();
	}
	
	public static boolean esNula(final String texto) {
		return UtilObjeto.esNula(texto);
	}
	
	public static String obtenerValorDefecto(final String texto, final String valorDefecto) {
		return UtilObjeto.obtenerValorDefecto(texto, valorDefecto);
	}
	
	public static String obtenerValorDefecto(final String texto) {
		return UtilObjeto.obtenerValorDefecto(texto, "");
	}
	
	public static String aplicarTrim(final String texto) {
		return obtenerValorDefecto(texto).trim();
	}
		public static String aplicarTrimYConvertirAMayusculas(final String texto) {
			return aplicarTrim(texto).toUpperCase();
		}
}
