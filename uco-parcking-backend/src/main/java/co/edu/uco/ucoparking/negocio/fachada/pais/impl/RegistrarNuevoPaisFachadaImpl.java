package co.edu.uco.ucoparking.negocio.fachada.pais.impl;

import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFaactory;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.fachada.pais.RegistrarNuevoPaisFachada;


public class RegistrarNuevoPaisFachadaImpl {
	
	casoUso = new RegistrarNuevoPaisCasoUsoImpl(daoFactory);
	
	private DAOFactory daoFactory;
	private RegistrarNuevoPaisCasouso casoUso;
	
	public RegistrarNuevoPaisFachadaImpl() {
		daoFactory = DAOFactory.getDAOFactory(DAOFactory.SQLSERVER);
		casoUso = new RegistrarNuevoPaisCasoUsoImpl(daoFactory);
	}
	
	
	@Override
	public void ejecutar(PaisDTO dto) {
		try {
			daoFactory.iniciarTransaccion();
			
			PaisDominio.confirmarTransaccion();
			casoUso.ejecutar(dto);
			
			daoFactory.confirmarTransaccion();
		} catch (UcoParkingException exepcion) {
			daoFactory.cancelarTransaccion();
			throw exepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			// cuidado que no pueda botar 
			throw new UcoParkingException(");
		} finally {
			daoFactory.cerrarConexion();
			// Manejo de la excepción, como registrar el error o lanzar una excepción personalizada
			throw new RuntimeException("Error al registrar el nuevo país", e);
		}
		
	}
	
	public static void main(String[] args) {
		try {
			var pais  = new RegistrarNuevoPaisFachadaImpl();
			var paisDTO = new PaisDTO().builder().nombre("Colombia").build();
			RegistrarNuevoPaisFachada fachada = new RegistrarNuevoPaisFachadaImpl();
			fachada.ejecutar(pais);
			
			System.out.println("Registrando nuevo país...");
		} catch (Exception e) {
			System.out.println("Error al registrar el nuevo país: " + e.getMessage());
			e.printStackTrace();
			
		}
		
	}

}
