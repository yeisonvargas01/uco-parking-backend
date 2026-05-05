package co.edu.uco.ucoparking.datos.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;

public interface DepartamentoDAO {

	void crear(DepartamentoEntidad departamento) throws SQLException;

	void modificar(DepartamentoEntidad departamento) throws SQLException;

	void eliminar(UUID id) throws SQLException;

	DepartamentoEntidad consultarPorId(UUID id) throws SQLException;

	List<DepartamentoEntidad> consultar(DepartamentoEntidad filtro) throws SQLException;
}
