package co.edu.uco.ucoparking.datos.dao.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.CiudadDAO;
import co.edu.uco.ucoparking.entidad.CiudadEntidad;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;

public class CiudadSQLServerDAO implements CiudadDAO {

	private final Connection conexion;

	public CiudadSQLServerDAO(final Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public void crear(final CiudadEntidad ciudad) throws SQLException {
		final String sql = "INSERT INTO Ciudad (id, nombre, departamento_id) VALUES (?, ?, ?)";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, ciudad.getId().toString());
			sentencia.setString(2, ciudad.getNombre());
			sentencia.setString(3, ciudad.getDepartamento().getId().toString());
			sentencia.executeUpdate();
		}
	}

	@Override
	public void modificar(final CiudadEntidad ciudad) throws SQLException {
		final String sql = "UPDATE Ciudad SET nombre = ?, departamento_id = ? WHERE id = ?";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, ciudad.getNombre());
			sentencia.setString(2, ciudad.getDepartamento().getId().toString());
			sentencia.setString(3, ciudad.getId().toString());
			sentencia.executeUpdate();
		}
	}

	@Override
	public void eliminar(final UUID id) throws SQLException {
		final String sql = "DELETE FROM Ciudad WHERE id = ?";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, id.toString());
			sentencia.executeUpdate();
		}
	}

	@Override
	public CiudadEntidad consultarPorId(final UUID id) throws SQLException {
		final String sql = """
				SELECT 
					c.id AS ciudad_id,
					c.nombre AS ciudad_nombre,
					d.id AS departamento_id,
					d.nombre AS departamento_nombre,
					p.id AS pais_id,
					p.nombre AS pais_nombre
				FROM Ciudad c
				INNER JOIN Departamento d ON c.departamento_id = d.id
				INNER JOIN Pais p ON d.pais_id = p.id
				WHERE c.id = ?
				""";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, id.toString());

			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					return ensamblarCiudad(resultado);
				}
			}
		}

		return new CiudadEntidad.Builder().build();
	}

	@Override
	public List<CiudadEntidad> consultar(final CiudadEntidad filtro) throws SQLException {
		final String sql = """
				SELECT 
					c.id AS ciudad_id,
					c.nombre AS ciudad_nombre,
					d.id AS departamento_id,
					d.nombre AS departamento_nombre,
					p.id AS pais_id,
					p.nombre AS pais_nombre
				FROM Ciudad c
				INNER JOIN Departamento d ON c.departamento_id = d.id
				INNER JOIN Pais p ON d.pais_id = p.id
				""";

		List<CiudadEntidad> ciudades = new ArrayList<>();

		try (
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				ResultSet resultado = sentencia.executeQuery()
		) {
			while (resultado.next()) {
				ciudades.add(ensamblarCiudad(resultado));
			}
		}

		return ciudades;
	}

	private CiudadEntidad ensamblarCiudad(final ResultSet resultado) throws SQLException {
		PaisEntidad pais = new PaisEntidad.Builder()
				.id(UUID.fromString(resultado.getString("pais_id")))
				.nombre(resultado.getString("pais_nombre"))
				.build();

		DepartamentoEntidad departamento = new DepartamentoEntidad.Builder()
				.id(UUID.fromString(resultado.getString("departamento_id")))
				.nombre(resultado.getString("departamento_nombre"))
				.pais(pais)
				.build();

		return new CiudadEntidad.Builder()
				.id(UUID.fromString(resultado.getString("ciudad_id")))
				.nombre(resultado.getString("ciudad_nombre"))
				.departamento(departamento)
				.build();
	}
}
