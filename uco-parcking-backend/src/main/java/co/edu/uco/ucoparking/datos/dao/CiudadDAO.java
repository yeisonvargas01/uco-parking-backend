package co.edu.uco.ucoparking.datos.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.entidad.CiudadEntidad;

public interface CiudadDAO {

	void crear(CiudadEntidad ciudad) throws SQLException;

	void modificar(CiudadEntidad ciudad) throws SQLException;

	void eliminar(UUID id) throws SQLException;

	CiudadEntidad consultarPorId(UUID id) throws SQLException;

	List<CiudadEntidad> consultar(CiudadEntidad filtro) throws SQLException;
}
