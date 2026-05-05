package co.edu.uco.ucoparking.datos.dao.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.transversal.excepcion.DatosUcoParkingException;

public class PaisSQLServerDAO implements PaisDAO {

	private final Connection conexion;

	public PaisSQLServerDAO(final Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public void crear(final PaisEntidad pais) throws DatosUcoParkingException {
		final String sql = "INSERT INTO Pais (id, nombre) VALUES (?, ?)";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, pais.getId().toString());
			sentencia.setString(2, pais.getNombre());
			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingException.crear(
					"No fue posible registrar el país.",
					"Error técnico al ejecutar INSERT sobre la tabla Pais.",
					excepcion);
		}
	}

	@Override
	public void modificar(final PaisEntidad pais) throws DatosUcoParkingException {
		final String sql = "UPDATE Pais SET nombre = ? WHERE id = ?";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, pais.getNombre());
			sentencia.setString(2, pais.getId().toString());
			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingException.crear(
					"No fue posible modificar el país.",
					"Error técnico al ejecutar UPDATE sobre la tabla Pais.",
					excepcion);
		}
	}

	@Override
	public void eliminar(final UUID id) throws DatosUcoParkingException {
		final String sql = "DELETE FROM Pais WHERE id = ?";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, id.toString());
			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingException.crear(
					"No fue posible eliminar el país.",
					"Error técnico al ejecutar DELETE sobre la tabla Pais.",
					excepcion);
		}
	}

	@Override
	public PaisEntidad consultarPorId(final UUID id) throws DatosUcoParkingException {
		final String sql = "SELECT id, nombre FROM Pais WHERE id = ?";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, id.toString());

			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					return ensamblarPais(resultado);
				}
			}

		} catch (SQLException excepcion) {
			throw DatosUcoParkingException.crear(
					"No fue posible consultar el país por identificador.",
					"Error técnico al ejecutar SELECT por id sobre la tabla Pais.",
					excepcion);
		}

		return new PaisEntidad.Builder().build();
	}

	@Override
	public List<PaisEntidad> consultar(final PaisEntidad filtro) throws DatosUcoParkingException {
		final String sql = "SELECT id, nombre FROM Pais";

		List<PaisEntidad> paises = new ArrayList<>();

		try (
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				ResultSet resultado = sentencia.executeQuery()
		) {
			while (resultado.next()) {
				paises.add(ensamblarPais(resultado));
			}

		} catch (SQLException excepcion) {
			throw DatosUcoParkingException.crear(
					"No fue posible consultar los países.",
					"Error técnico al ejecutar SELECT sobre la tabla Pais.",
					excepcion);
		}

		return paises;
	}

	private PaisEntidad ensamblarPais(final ResultSet resultado) throws SQLException {
		return new PaisEntidad.Builder()
				.id(UUID.fromString(resultado.getString("id")))
				.nombre(resultado.getString("nombre"))
				.build();
	}
}