package co.edu.uco.ucoparking.datos.dao.sql.factoria.sqlserver;

import co.edu.uco.ucoparking.datos.dao.CiudadDAO;
import co.edu.uco.ucoparking.datos.dao.DepartamentoDAO;
import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.factoria.DAOFactory;
import co.edu.uco.ucoparking.datos.dao.sql.sqlserver.CiudadSQLServerDAO;
import co.edu.uco.ucoparking.datos.dao.sql.sqlserver.DepartamentoSQLServerDAO;
import co.edu.uco.ucoparking.datos.dao.sql.sqlserver.PaisSQLServerDAO;

public class SQLServerDAOFactory extends DAOFactory {
	
	public SQLServerDAOFactory() {
		abrirConexion();
	}
	
	
	@Override
	public void abrirConexion() {
		try {
			conexion = SQLServerConexion.getConexion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cerrarConexion() {
		conexion = null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void iniciarTransaccion() {
		conexion = null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmarTransaccion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelarTransaccion() {
		conexion = null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public PaisDAO obtenerPaisDAO() {
		conexion = null;
		// TODO Auto-generated method stub
		return new PaisSQLServerDAO(conexion);
	}
	
	@Override
	public DepartamentoDAO obtenerDepartamentoDAO() {
		return new DepartamentoSQLServerDAO(conexion);
	}

	@Override
	public CiudadDAO obtenerCiudadDAO() {
		return new CiudadSQLServerDAO(conexion);
	}

}
