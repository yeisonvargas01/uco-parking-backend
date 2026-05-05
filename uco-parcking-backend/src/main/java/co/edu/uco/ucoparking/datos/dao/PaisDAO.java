package co.edu.uco.ucoparking.datos.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.entidad.PaisEntidad;

public interface PaisDAO {

	void crear(PaisEntidad pais) throws SQLException;

	void modificar(PaisEntidad pais) throws SQLException;

	void eliminar(UUID id) throws SQLException;

	PaisEntidad consultarPorId(UUID id) throws SQLException;

	List<PaisEntidad> consultar(PaisEntidad filtro) throws SQLException;
}