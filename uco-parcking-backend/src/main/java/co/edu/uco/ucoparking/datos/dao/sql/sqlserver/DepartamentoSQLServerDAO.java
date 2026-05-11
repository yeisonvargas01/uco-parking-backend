package co.edu.uco.ucoparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.DepartamentoDAO;
import co.edu.uco.ucoparking.datos.dao.sql.SQLDAO;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;

public class DepartamentoSQLServerDAO extends SQLDAO implements DepartamentoDAO {

	public DepartamentoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(DepartamentoEntidad entidad) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<DepartamentoEntidad> consultar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DepartamentoEntidad consultarPorId(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DepartamentoEntidad> consultar(DepartamentoEntidad filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(DepartamentoEntidad entidad) {
		// TODO Auto-generated method stub
	}

	@Override
	public void eliminar(UUID id) {
		// TODO Auto-generated method stub
	}
}