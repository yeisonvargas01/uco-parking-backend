package co.edu.uco.ucoparking.datos.dao;

import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.entidad.CiudadEntidad;
import co.edu.uco.ucoparking.transversal.excepcion.DatosUcoParkingException;

public interface CiudadDAO {

	void crear(CiudadEntidad ciudad) throws DatosUcoParkingException;

	void modificar(CiudadEntidad ciudad) throws DatosUcoParkingException;

	void eliminar(UUID id) throws DatosUcoParkingException;

	CiudadEntidad consultarPorId(UUID id) throws DatosUcoParkingException;

	List<CiudadEntidad> consultar(CiudadEntidad filtro) throws DatosUcoParkingException;
}
