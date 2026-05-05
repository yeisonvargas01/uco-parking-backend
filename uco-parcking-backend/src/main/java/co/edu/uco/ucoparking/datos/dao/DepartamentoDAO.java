package co.edu.uco.ucoparking.datos.dao;

import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.transversal.excepcion.DatosUcoParkingException;

public interface DepartamentoDAO {

	void crear(DepartamentoEntidad departamento) throws DatosUcoParkingException;

	void modificar(DepartamentoEntidad departamento) throws DatosUcoParkingException;

	void eliminar(UUID id) throws DatosUcoParkingException;

	DepartamentoEntidad consultarPorId(UUID id) throws DatosUcoParkingException;

	List<DepartamentoEntidad> consultar(DepartamentoEntidad filtro) throws DatosUcoParkingException;
}