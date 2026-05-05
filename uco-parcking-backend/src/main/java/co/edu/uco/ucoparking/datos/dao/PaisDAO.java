package co.edu.uco.ucoparking.datos.dao;

import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.transversal.excepcion.DatosUcoParkingException;

public interface PaisDAO {

	void crear(PaisEntidad pais) throws DatosUcoParkingException;

	void modificar(PaisEntidad pais) throws DatosUcoParkingException;

	void eliminar(UUID id) throws DatosUcoParkingException;

	PaisEntidad consultarPorId(UUID id) throws DatosUcoParkingException;

	List<PaisEntidad> consultar(PaisEntidad filtro) throws DatosUcoParkingException;
}