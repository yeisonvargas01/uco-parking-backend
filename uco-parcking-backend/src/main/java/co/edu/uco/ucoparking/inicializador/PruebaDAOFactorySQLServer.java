package co.edu.uco.ucoparking.inicializador;

import java.sql.SQLException;

import co.edu.uco.ucoparking.datos.dao.factory.DAOFactory;
import co.edu.uco.ucoparking.datos.dao.factory.SQLServerDAOFactory;
import co.edu.uco.ucoparking.entidad.CiudadEntidad;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;

public class PruebaDAOFactorySQLServer {

	public static void main(String[] args) {

		DAOFactory daoFactory = new SQLServerDAOFactory();

		try {
			daoFactory.abrirConexion();

			System.out.println("===== CONSULTANDO PAISES =====");

			for (PaisEntidad pais : daoFactory.obtenerPaisDAO().consultar(new PaisEntidad.Builder().build())) {
				System.out.println("Pais: " + pais.getId() + " - " + pais.getNombre());
			}

			System.out.println();
			System.out.println("===== CONSULTANDO DEPARTAMENTOS CON PAIS =====");

			for (DepartamentoEntidad departamento : daoFactory.obtenerDepartamentoDAO()
					.consultar(new DepartamentoEntidad.Builder().build())) {

				System.out.println("Departamento: " + departamento.getId() + " - " + departamento.getNombre());
				System.out.println("Pais: " + departamento.getPais().getId() + " - "
						+ departamento.getPais().getNombre());
				System.out.println("-----------------------------------");
			}

			System.out.println();
			System.out.println("===== CONSULTANDO CIUDADES CON DEPARTAMENTO Y PAIS =====");

			for (CiudadEntidad ciudad : daoFactory.obtenerCiudadDAO().consultar(new CiudadEntidad.Builder().build())) {

				System.out.println("Ciudad: " + ciudad.getId() + " - " + ciudad.getNombre());
				System.out.println("Departamento: " + ciudad.getDepartamento().getId() + " - "
						+ ciudad.getDepartamento().getNombre());
				System.out.println("Pais: " + ciudad.getDepartamento().getPais().getId() + " - "
						+ ciudad.getDepartamento().getPais().getNombre());
				System.out.println("-----------------------------------");
			}

		} catch (SQLException excepcion) {
			excepcion.printStackTrace();

		} finally {
			try {
				daoFactory.cerrarConexion();
			} catch (SQLException excepcion) {
				excepcion.printStackTrace();
			}
		}
	}
}