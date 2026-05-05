package co.edu.uco.ucoparking.inicializador;

import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.factory.DAOFactory;
import co.edu.uco.ucoparking.datos.dao.factory.SQLServerDAOFactory;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.transversal.excepcion.DatosUcoParkingException;

public class PruebaCRUDPaisDAOFactorySQLServer {

	public static void main(String[] args) {

		DAOFactory daoFactory = new SQLServerDAOFactory();

		UUID idPaisPrueba = UUID.randomUUID();

		PaisEntidad paisCrear = new PaisEntidad.Builder()
				.id(idPaisPrueba)
				.nombre("Peru")
				.build();

		PaisEntidad paisModificar = new PaisEntidad.Builder()
				.id(idPaisPrueba)
				.nombre("Peru Actualizado")
				.build();

		try {
			daoFactory.abrirConexion();

			System.out.println("===== LISTA INICIAL DE PAISES =====");
			consultarPaises(daoFactory);

			System.out.println();
			System.out.println("===== INSERTAR PAIS =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerPaisDAO().crear(paisCrear);
			daoFactory.confirmarTransaccion();

			System.out.println("Pais creado correctamente: " + paisCrear.getId() + " - " + paisCrear.getNombre());

			System.out.println();
			System.out.println("===== CONSULTAR PAIS POR ID =====");

			PaisEntidad paisConsultado = daoFactory.obtenerPaisDAO().consultarPorId(idPaisPrueba);
			System.out.println("Pais consultado: " + paisConsultado.getId() + " - " + paisConsultado.getNombre());

			System.out.println();
			System.out.println("===== MODIFICAR PAIS =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerPaisDAO().modificar(paisModificar);
			daoFactory.confirmarTransaccion();

			System.out.println("Pais modificado correctamente.");

			System.out.println();
			System.out.println("===== CONSULTAR PAIS MODIFICADO =====");

			PaisEntidad paisModificadoConsultado = daoFactory.obtenerPaisDAO().consultarPorId(idPaisPrueba);
			System.out.println("Pais consultado: " + paisModificadoConsultado.getId() + " - "
					+ paisModificadoConsultado.getNombre());

			System.out.println();
			System.out.println("===== LISTA DE PAISES DESPUES DEL UPDATE =====");
			consultarPaises(daoFactory);

			System.out.println();
			System.out.println("===== ELIMINAR PAIS =====");

			daoFactory.iniciarTransaccion();
			daoFactory.obtenerPaisDAO().eliminar(idPaisPrueba);
			daoFactory.confirmarTransaccion();

			System.out.println("Pais eliminado correctamente.");

			System.out.println();
			System.out.println("===== LISTA FINAL DE PAISES =====");
			consultarPaises(daoFactory);

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

	private static void consultarPaises(final DAOFactory daoFactory) throws DatosUcoParkingException {
		for (PaisEntidad pais : daoFactory.obtenerPaisDAO().consultar(new PaisEntidad.Builder().build())) {
			System.out.println("Pais: " + pais.getId() + " - " + pais.getNombre());
		}
	}
}
