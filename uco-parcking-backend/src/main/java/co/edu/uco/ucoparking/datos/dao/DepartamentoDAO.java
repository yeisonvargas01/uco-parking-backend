package co.edu.uco.ucoparking.datos.dao;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.ActualizarDAO;
import co.edu.uco.ucoparking.datos.ConsultarPorFiltroDAO;
import co.edu.uco.ucoparking.datos.ConsultarPorIdDAO;
import co.edu.uco.ucoparking.datos.ConsultarTodosDAO;
import co.edu.uco.ucoparking.datos.CrearDAO;
import co.edu.uco.ucoparking.datos.EliminarDAO;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;

public interface DepartamentoDAO extends CrearDAO<DepartamentoEntidad>, ConsultarTodosDAO<DepartamentoEntidad>,
		ConsultarPorIdDAO<DepartamentoEntidad, UUID>, ConsultarPorFiltroDAO<DepartamentoEntidad>,
		ActualizarDAO<DepartamentoEntidad>, EliminarDAO<UUID> {

}