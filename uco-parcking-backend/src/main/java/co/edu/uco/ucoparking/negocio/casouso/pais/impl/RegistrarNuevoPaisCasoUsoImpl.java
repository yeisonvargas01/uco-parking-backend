package co.edu.uco.ucoparking.negocio.casouso.pais.impl;

import co.edu.co.uco.ucoparking.datos.CasoUsoConRetorno;
import co.edu.uco.ucoparking.dominio.PaisDominio;
import co.edu.uco.ucoparking.negocio.casouso.pais.RegistrarNuevoPaisCasoUso;

public class RegistrarNuevoPaisCasoUsoImpl implements RegistrarNuevoPaisCasoUso {
	
	private DAOFactory daoFactory;
	
	protected RegistrarNuevoPaisCasoUsoImpl(DAOFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void ejecutar(PaisDominio datos) {
		validarIntegridadDatosPais(datos);
		validarNoExistaOtroPaisConMismoNombre(datos.getNombre());
		var idNuevoPais = generarIdUnicoNuevoPais();
		guardar(datos);
	}
	
	
	// 1. Validación de datos consistentes: tipo de datos, longitud, obligatoriedad,
	//formato, rango
	private void validarIntegridadDatosPais(PaisDominio datos) {
		
	}
	
	// 2. No debe existir un país con el mismo nombre
	private void validarNoExistaOtroPaisConMismoNombre(String nombre) {
		var paisEntidadFiltro = new PaisEntidad.builder().nombre(nombre).build();
		var resultados = daoFactory.getPaisDAO().consultarPorFiltro(paisEntidadFiltro);
		
		if(UtilObjeto.esNulo(resultados) && !resultados.isEmpty()) {
			// Existe otro pais con el mismo nombre
			// Lanzar una excepción customizada
			// ExistePaisConMismoNombreExcepcion
		}
		
	}
	
	// 3. El id del pais debe ser unico
	private UUID generarIdUnicoNuevoPais() {
		// Aquí la lógica para garantizar que se genere un Id que no existe
		return UUID.randomUUID();
	}
	
	
	private void guardar(PaisDominio pais, UUID id) {
		// Lógica para guardar el nuevo pais
	}
	

	
}