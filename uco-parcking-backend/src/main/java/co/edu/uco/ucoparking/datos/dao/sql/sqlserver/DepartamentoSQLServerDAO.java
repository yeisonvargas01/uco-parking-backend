package co.edu.uco.ucoparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.DepartamentoDAO;
import co.edu.uco.ucoparking.datos.dao.sql.SQLDAO;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.DatosUcoParkingExcepcion;

public class DepartamentoSQLServerDAO extends SQLDAO implements DepartamentoDAO {

	private static final UUID UUID_DEFECTO = UUID.fromString("00000000-0000-0000-0000-000000000000");

	public DepartamentoSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final DepartamentoEntidad entidad) {
		final String sentenciaSql = "INSERT INTO dbo.Departamento (id, nombre, pais_id) VALUES (?, ?, ?)";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, entidad.getId().toString());
			sentencia.setString(2, entidad.getNombre());
			sentencia.setString(3, entidad.getPais().getId().toString());

			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible registrar la información del departamento.",
					excepcion);
		}
	}

	@Override
	public List<DepartamentoEntidad> consultar() {
		return consultar(DepartamentoEntidad.builder().build());
	}

	@Override
	public DepartamentoEntidad consultarPorId(final UUID id) {
		final String sentenciaSql = """
				SELECT 
					d.id AS departamento_id,
					d.nombre AS departamento_nombre,
					p.id AS pais_id,
					p.nombre AS pais_nombre
				FROM dbo.Departamento d
				INNER JOIN dbo.Pais p ON d.pais_id = p.id
				WHERE d.id = ?
				""";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, id.toString());

			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					return ensamblarDepartamento(resultado);
				}
			}

			return null;

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible consultar la información del departamento por identificador.",
					excepcion);
		}
	}

	@Override
	public List<DepartamentoEntidad> consultar(final DepartamentoEntidad filtro) {
		var parametros = new ArrayList<Object>();
		var sentenciaSql = new StringBuilder();

		sentenciaSql.append("SELECT ");
		sentenciaSql.append("d.id AS departamento_id, ");
		sentenciaSql.append("d.nombre AS departamento_nombre, ");
		sentenciaSql.append("p.id AS pais_id, ");
		sentenciaSql.append("p.nombre AS pais_nombre ");
		sentenciaSql.append("FROM dbo.Departamento d ");
		sentenciaSql.append("INNER JOIN dbo.Pais p ON d.pais_id = p.id ");
		sentenciaSql.append("WHERE 1 = 1 ");

		if (Objects.nonNull(filtro)) {

			if (esUUIDValido(filtro.getId())) {
				sentenciaSql.append("AND d.id = ? ");
				parametros.add(filtro.getId().toString());
			}

			if (tieneTexto(filtro.getNombre())) {
				sentenciaSql.append("AND LOWER(d.nombre) = LOWER(?) ");
				parametros.add(filtro.getNombre().trim());
			}

			if (Objects.nonNull(filtro.getPais())) {

				if (esUUIDValido(filtro.getPais().getId())) {
					sentenciaSql.append("AND p.id = ? ");
					parametros.add(filtro.getPais().getId().toString());
				}

				if (tieneTexto(filtro.getPais().getNombre())) {
					sentenciaSql.append("AND LOWER(p.nombre) = LOWER(?) ");
					parametros.add(filtro.getPais().getNombre().trim());
				}
			}
		}

		sentenciaSql.append("ORDER BY p.nombre ASC, d.nombre ASC");

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql.toString())) {

			for (int indice = 0; indice < parametros.size(); indice++) {
				sentencia.setObject(indice + 1, parametros.get(indice));
			}

			try (ResultSet resultado = sentencia.executeQuery()) {
				var departamentos = new ArrayList<DepartamentoEntidad>();

				while (resultado.next()) {
					departamentos.add(ensamblarDepartamento(resultado));
				}

				return departamentos;
			}

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible consultar la información de los departamentos.",
					excepcion);
		}
	}

	@Override
	public void actualizar(final DepartamentoEntidad entidad) {
		final String sentenciaSql = "UPDATE dbo.Departamento SET nombre = ?, pais_id = ? WHERE id = ?";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, entidad.getNombre());
			sentencia.setString(2, entidad.getPais().getId().toString());
			sentencia.setString(3, entidad.getId().toString());

			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible modificar la información del departamento.",
					excepcion);
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sentenciaSql = "DELETE FROM dbo.Departamento WHERE id = ?";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, id.toString());

			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible eliminar la información del departamento.",
					excepcion);
		}
	}

	private DepartamentoEntidad ensamblarDepartamento(final ResultSet resultado) throws SQLException {
		var pais = PaisEntidad.builder()
				.id(UUID.fromString(resultado.getString("pais_id")))
				.nombre(resultado.getString("pais_nombre"))
				.build();

		return DepartamentoEntidad.builder()
				.id(UUID.fromString(resultado.getString("departamento_id")))
				.nombre(resultado.getString("departamento_nombre"))
				.pais(pais)
				.build();
	}

	private boolean tieneTexto(final String texto) {
		return Objects.nonNull(texto) && !texto.trim().isEmpty();
	}

	private boolean esUUIDValido(final UUID id) {
		return Objects.nonNull(id) && !UUID_DEFECTO.equals(id);
	}
}