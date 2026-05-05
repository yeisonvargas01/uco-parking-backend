package co.edu.uco.ucoparking.datos.dao.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.DepartamentoDAO;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;

public class DepartamentoSQLServerDAO implements DepartamentoDAO {

	private final Connection conexion;

	public DepartamentoSQLServerDAO(final Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public void crear(final DepartamentoEntidad departamento) throws SQLException {
		final String sql = "INSERT INTO Departamento (id, nombre, pais_id) VALUES (?, ?, ?)";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, departamento.getId().toString());
			sentencia.setString(2, departamento.getNombre());
			sentencia.setString(3, departamento.getPais().getId().toString());
			sentencia.executeUpdate();
		}
	}

	@Override
	public void modificar(final DepartamentoEntidad departamento) throws SQLException {
		final String sql = "UPDATE Departamento SET nombre = ?, pais_id = ? WHERE id = ?";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, departamento.getNombre());
			sentencia.setString(2, departamento.getPais().getId().toString());
			sentencia.setString(3, departamento.getId().toString());
			sentencia.executeUpdate();
		}
	}

	@Override
	public void eliminar(final UUID id) throws SQLException {
		final String sql = "DELETE FROM Departamento WHERE id = ?";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, id.toString());
			sentencia.executeUpdate();
		}
	}

	@Override
	public DepartamentoEntidad consultarPorId(final UUID id) throws SQLException {
		final String sql = """
				SELECT 
					d.id AS departamento_id,
					d.nombre AS departamento_nombre,
					p.id AS pais_id,
					p.nombre AS pais_nombre
				FROM Departamento d
				INNER JOIN Pais p ON d.pais_id = p.id
				WHERE d.id = ?
				""";

		try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, id.toString());

			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					return ensamblarDepartamento(resultado);
				}
			}
		}

		return new DepartamentoEntidad.Builder().build();
	}

	@Override
	public List<DepartamentoEntidad> consultar(final DepartamentoEntidad filtro) throws SQLException {
		final String sql = """
				SELECT 
					d.id AS departamento_id,
					d.nombre AS departamento_nombre,
					p.id AS pais_id,
					p.nombre AS pais_nombre
				FROM Departamento d
				INNER JOIN Pais p ON d.pais_id = p.id
				""";

		List<DepartamentoEntidad> departamentos = new ArrayList<>();

		try (
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				ResultSet resultado = sentencia.executeQuery()
		) {
			while (resultado.next()) {
				departamentos.add(ensamblarDepartamento(resultado));
			}
		}

		return departamentos;
	}

	private DepartamentoEntidad ensamblarDepartamento(final ResultSet resultado) throws SQLException {
		PaisEntidad pais = new PaisEntidad.Builder()
				.id(UUID.fromString(resultado.getString("pais_id")))
				.nombre(resultado.getString("pais_nombre"))
				.build();

		return new DepartamentoEntidad.Builder()
				.id(UUID.fromString(resultado.getString("departamento_id")))
				.nombre(resultado.getString("departamento_nombre"))
				.pais(pais)
				.build();
	}
}
