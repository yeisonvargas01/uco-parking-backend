package co.edu.uco.ucoparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.CiudadDAO;
import co.edu.uco.ucoparking.datos.dao.sql.SQLDAO;
import co.edu.uco.ucoparking.entidad.CiudadEntidad;

public class CiudadSQLServerDAO extends SQLDAO implements CiudadDAO {

	public CiudadSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(CiudadEntidad entidad) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<CiudadEntidad> consultar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CiudadEntidad consultarPorId(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CiudadEntidad> consultar(CiudadEntidad filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CiudadEntidad entidad) {
		// TODO Auto-generated method stub
	}

	@Override
	public void eliminar(UUID id) {
		// TODO Auto-generated method stub
	}
}