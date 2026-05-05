package co.edu.uco.ucoparking.inicializador;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.factory.DAOFactory;
import co.edu.uco.ucoparking.datos.dao.factory.SQLServerDAOFactory;
import co.edu.uco.ucoparking.entidad.CiudadEntidad;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.transversal.excepcion.DatosUcoParkingException;

public class PruebaCRUDCiudadDAOFactorySQLServer {

	public static void main(String[] args) {

		DAOFactory daoFactory = new SQLServerDAOFactory();

		UUID idPaisPrueba = UUID.randomUUID();
		UUID idDepartamentoPrueba = UUID.randomUUID();
		UUID idCiudadPrueba = UUID.randomUUID();

		PaisEntidad paisCrear = new PaisEntidad.Builder()
				.id(idPaisPrueba)
				.nombre("Colombia")
				.build();

		DepartamentoEntidad departamentoCrear = new DepartamentoEntidad.Builder()
				.id(idDepartamentoPrueba)
				.nombre("Antioquia")
				.pais(paisCrear)
				.build();

		CiudadEntidad ciudadCrear = new CiudadEntidad.Builder()
				.id(idCiudadPrueba)
				.nombre("Rionegro")
				.departamento(departamentoCrear)
				.build();

		CiudadEntidad ciudadModificar = new CiudadEntidad.Builder()
				.id(idCiudadPrueba)
				.nombre("Rionegro Actualizado")
				.departamento(departamentoCrear)
				.build();

		try {
			daoFactory.abrirConexion();

			System.out.println("===== LISTA INICIAL DE CIUDADES =====");
			consultarCiudades(daoFactory);

			System.out.println();
			System.out.println("===== INSERTAR PAIS NECESARIO =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerPaisDAO().crear(paisCrear);
			daoFactory.confirmarTransaccion();

			System.out.println("Pais creado correctamente: " + paisCrear.getId() + " - " + paisCrear.getNombre());

			System.out.println();
			System.out.println("===== INSERTAR DEPARTAMENTO NECESARIO =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerDepartamentoDAO().crear(departamentoCrear);
			daoFactory.confirmarTransaccion();

			System.out.println("Departamento creado correctamente: " + departamentoCrear.getId() + " - "
					+ departamentoCrear.getNombre());

			System.out.println();
			System.out.println("===== INSERTAR CIUDAD =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerCiudadDAO().crear(ciudadCrear);
			daoFactory.confirmarTransaccion();

			System.out.println("Ciudad creada correctamente: " + ciudadCrear.getId() + " - " + ciudadCrear.getNombre());

			System.out.println();
			System.out.println("===== CONSULTAR CIUDAD POR ID =====");

			CiudadEntidad ciudadConsultada = daoFactory.obtenerCiudadDAO().consultarPorId(idCiudadPrueba);

			imprimirCiudad(ciudadConsultada);

			System.out.println();
			System.out.println("===== MODIFICAR CIUDAD =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerCiudadDAO().modificar(ciudadModificar);
			daoFactory.confirmarTransaccion();

			System.out.println("Ciudad modificada correctamente.");

			System.out.println();
			System.out.println("===== CONSULTAR CIUDAD MODIFICADA =====");

			CiudadEntidad ciudadModificadaConsultada = daoFactory.obtenerCiudadDAO().consultarPorId(idCiudadPrueba);

			imprimirCiudad(ciudadModificadaConsultada);

			System.out.println();
			System.out.println("===== LISTA DE CIUDADES DESPUES DEL UPDATE =====");
			consultarCiudades(daoFactory);

			System.out.println();
			System.out.println("===== ELIMINAR CIUDAD =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerCiudadDAO().eliminar(idCiudadPrueba);
			daoFactory.confirmarTransaccion();

			System.out.println("Ciudad eliminada correctamente.");

			System.out.println();
			System.out.println("===== ELIMINAR DEPARTAMENTO DE PRUEBA =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerDepartamentoDAO().eliminar(idDepartamentoPrueba);
			daoFactory.confirmarTransaccion();

			System.out.println("Departamento de prueba eliminado correctamente.");

			System.out.println();
			System.out.println("===== ELIMINAR PAIS DE PRUEBA =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerPaisDAO().eliminar(idPaisPrueba);
			daoFactory.confirmarTransaccion();

			System.out.println("Pais de prueba eliminado correctamente.");

			System.out.println();
			System.out.println("===== LISTA FINAL DE CIUDADES =====");
			consultarCiudades(daoFactory);

		} catch (DatosUcoParkingException excepcion) {

			try {
				daoFactory.cancelarTransaccion();

			} catch (DatosUcoParkingException excepcionRollback) {
				System.out.println("No fue posible cancelar la transacción.");
				System.out.println("Mensaje técnico: " + excepcionRollback.getMensajeTecnico());
			}

			System.out.println("Mensaje usuario: " + excepcion.getMensajeUsuario());
			System.out.println("Mensaje técnico: " + excepcion.getMensajeTecnico());
			excepcion.printStackTrace();

		} finally {
			try {
				daoFactory.cerrarConexion();

			} catch (DatosUcoParkingException excepcion) {
				System.out.println("No fue posible cerrar la conexión.");
				System.out.println("Mensaje técnico: " + excepcion.getMensajeTecnico());
			}
		}
	}

	private static void consultarCiudades(final DAOFactory daoFactory) throws DatosUcoParkingException {
		for (CiudadEntidad ciudad : daoFactory.obtenerCiudadDAO().consultar(new CiudadEntidad.Builder().build())) {
			imprimirCiudad(ciudad);
			System.out.println("-----------------------------------");
		}
	}

	private static void imprimirCiudad(final CiudadEntidad ciudad) {
		System.out.println("Ciudad: " + ciudad.getId() + " - " + ciudad.getNombre());
		System.out.println("Departamento: " + ciudad.getDepartamento().getId() + " - "
				+ ciudad.getDepartamento().getNombre());
		System.out.println("Pais: " + ciudad.getDepartamento().getPais().getId() + " - "
				+ ciudad.getDepartamento().getPais().getNombre());
	}
}
