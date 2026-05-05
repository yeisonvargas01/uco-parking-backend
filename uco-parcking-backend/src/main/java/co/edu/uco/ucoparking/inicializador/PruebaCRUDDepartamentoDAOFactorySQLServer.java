package co.edu.uco.ucoparking.inicializador;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.factory.DAOFactory;
import co.edu.uco.ucoparking.datos.dao.factory.SQLServerDAOFactory;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.transversal.excepcion.DatosUcoParkingException;

public class PruebaCRUDDepartamentoDAOFactorySQLServer {

	public static void main(String[] args) {

		DAOFactory daoFactory = new SQLServerDAOFactory();

		UUID idPaisPrueba = UUID.randomUUID();
		UUID idDepartamentoPrueba = UUID.randomUUID();

		PaisEntidad paisCrear = new PaisEntidad.Builder()
				.id(idPaisPrueba)
				.nombre("Ecuador")
				.build();

		DepartamentoEntidad departamentoCrear = new DepartamentoEntidad.Builder()
				.id(idDepartamentoPrueba)
				.nombre("Imbabura")
				.pais(paisCrear)
				.build();

		DepartamentoEntidad departamentoModificar = new DepartamentoEntidad.Builder()
				.id(idDepartamentoPrueba)
				.nombre("Imbabura Actualizado")
				.pais(paisCrear)
				.build();

		try {
			daoFactory.abrirConexion();

			System.out.println("===== LISTA INICIAL DE DEPARTAMENTOS =====");
			consultarDepartamentos(daoFactory);

			System.out.println();
			System.out.println("===== INSERTAR PAIS NECESARIO PARA EL DEPARTAMENTO =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerPaisDAO().crear(paisCrear);
			daoFactory.confirmarTransaccion();

			System.out.println("Pais creado correctamente: " + paisCrear.getId() + " - " + paisCrear.getNombre());

			System.out.println();
			System.out.println("===== INSERTAR DEPARTAMENTO =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerDepartamentoDAO().crear(departamentoCrear);
			daoFactory.confirmarTransaccion();

			System.out.println("Departamento creado correctamente: " + departamentoCrear.getId() + " - "
					+ departamentoCrear.getNombre());

			System.out.println();
			System.out.println("===== CONSULTAR DEPARTAMENTO POR ID =====");

			DepartamentoEntidad departamentoConsultado = daoFactory.obtenerDepartamentoDAO()
					.consultarPorId(idDepartamentoPrueba);

			System.out.println("Departamento: " + departamentoConsultado.getId() + " - "
					+ departamentoConsultado.getNombre());
			System.out.println("Pais: " + departamentoConsultado.getPais().getId() + " - "
					+ departamentoConsultado.getPais().getNombre());

			System.out.println();
			System.out.println("===== MODIFICAR DEPARTAMENTO =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerDepartamentoDAO().modificar(departamentoModificar);
			daoFactory.confirmarTransaccion();

			System.out.println("Departamento modificado correctamente.");

			System.out.println();
			System.out.println("===== CONSULTAR DEPARTAMENTO MODIFICADO =====");

			DepartamentoEntidad departamentoModificadoConsultado = daoFactory.obtenerDepartamentoDAO()
					.consultarPorId(idDepartamentoPrueba);

			System.out.println("Departamento: " + departamentoModificadoConsultado.getId() + " - "
					+ departamentoModificadoConsultado.getNombre());
			System.out.println("Pais: " + departamentoModificadoConsultado.getPais().getId() + " - "
					+ departamentoModificadoConsultado.getPais().getNombre());

			System.out.println();
			System.out.println("===== LISTA DE DEPARTAMENTOS DESPUES DEL UPDATE =====");
			consultarDepartamentos(daoFactory);

			System.out.println();
			System.out.println("===== ELIMINAR DEPARTAMENTO =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerDepartamentoDAO().eliminar(idDepartamentoPrueba);
			daoFactory.confirmarTransaccion();

			System.out.println("Departamento eliminado correctamente.");

			System.out.println();
			System.out.println("===== ELIMINAR PAIS DE PRUEBA =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerPaisDAO().eliminar(idPaisPrueba);
			daoFactory.confirmarTransaccion();

			System.out.println("Pais de prueba eliminado correctamente.");

			System.out.println();
			System.out.println("===== LISTA FINAL DE DEPARTAMENTOS =====");
			consultarDepartamentos(daoFactory);

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

	private static void consultarDepartamentos(final DAOFactory daoFactory) throws DatosUcoParkingException {
		for (DepartamentoEntidad departamento : daoFactory.obtenerDepartamentoDAO()
				.consultar(new DepartamentoEntidad.Builder().build())) {

			System.out.println("Departamento: " + departamento.getId() + " - " + departamento.getNombre());
			System.out.println("Pais: " + departamento.getPais().getId() + " - "
					+ departamento.getPais().getNombre());
			System.out.println("-----------------------------------");
		}
	}
}
