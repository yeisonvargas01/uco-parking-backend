package co.edu.uco.ucoparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.CiudadDAO;
import co.edu.uco.ucoparking.datos.dao.sql.SQLDAO;
import co.edu.uco.ucoparking.entidad.CiudadEntidad;
import co.edu.uco.ucoparking.entidad.DepartamentoEntidad;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.DatosUcoParkingExcepcion;

public class CiudadSQLServerDAO extends SQLDAO implements CiudadDAO {

	private static final UUID UUID_DEFECTO = UUID.fromString("00000000-0000-0000-0000-000000000000");

	public CiudadSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final CiudadEntidad entidad) {
		final String sentenciaSql = "INSERT INTO dbo.Ciudad (id, nombre, departamento_id) VALUES (?, ?, ?)";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, entidad.getId().toString());
			sentencia.setString(2, entidad.getNombre());
			sentencia.setString(3, entidad.getDepartamento().getId().toString());

			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible registrar la información de la ciudad.",
					excepcion);
		}
	}

	@Override
	public List<CiudadEntidad> consultar() {
		return consultar(CiudadEntidad.builder().build());
	}

	@Override
	public CiudadEntidad consultarPorId(final UUID id) {
		final String sentenciaSql = """
				SELECT 
					c.id AS ciudad_id,
					c.nombre AS ciudad_nombre,
					d.id AS departamento_id,
					d.nombre AS departamento_nombre,
					p.id AS pais_id,
					p.nombre AS pais_nombre
				FROM dbo.Ciudad c
				INNER JOIN dbo.Departamento d ON c.departamento_id = d.id
				INNER JOIN dbo.Pais p ON d.pais_id = p.id
				WHERE c.id = ?
				""";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, id.toString());

			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					return ensamblarCiudad(resultado);
				}
			}

			return null;

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible consultar la información de la ciudad por identificador.",
					excepcion);
		}
	}

	@Override
	public List<CiudadEntidad> consultar(final CiudadEntidad filtro) {
		var parametros = new ArrayList<Object>();
		var sentenciaSql = new StringBuilder();

		sentenciaSql.append("SELECT ");
		sentenciaSql.append("c.id AS ciudad_id, ");
		sentenciaSql.append("c.nombre AS ciudad_nombre, ");
		sentenciaSql.append("d.id AS departamento_id, ");
		sentenciaSql.append("d.nombre AS departamento_nombre, ");
		sentenciaSql.append("p.id AS pais_id, ");
		sentenciaSql.append("p.nombre AS pais_nombre ");
		sentenciaSql.append("FROM dbo.Ciudad c ");
		sentenciaSql.append("INNER JOIN dbo.Departamento d ON c.departamento_id = d.id ");
		sentenciaSql.append("INNER JOIN dbo.Pais p ON d.pais_id = p.id ");
		sentenciaSql.append("WHERE 1 = 1 ");

		if (Objects.nonNull(filtro)) {

			if (esUUIDValido(filtro.getId())) {
				sentenciaSql.append("AND c.id = ? ");
				parametros.add(filtro.getId().toString());
			}

			if (tieneTexto(filtro.getNombre())) {
				sentenciaSql.append("AND LOWER(c.nombre) = LOWER(?) ");
				parametros.add(filtro.getNombre().trim());
			}

			if (Objects.nonNull(filtro.getDepartamento())) {

				if (esUUIDValido(filtro.getDepartamento().getId())) {
					sentenciaSql.append("AND d.id = ? ");
					parametros.add(filtro.getDepartamento().getId().toString());
				}

				if (tieneTexto(filtro.getDepartamento().getNombre())) {
					sentenciaSql.append("AND LOWER(d.nombre) = LOWER(?) ");
					parametros.add(filtro.getDepartamento().getNombre().trim());
				}

				if (Objects.nonNull(filtro.getDepartamento().getPais())) {

					if (esUUIDValido(filtro.getDepartamento().getPais().getId())) {
						sentenciaSql.append("AND p.id = ? ");
						parametros.add(filtro.getDepartamento().getPais().getId().toString());
					}

					if (tieneTexto(filtro.getDepartamento().getPais().getNombre())) {
						sentenciaSql.append("AND LOWER(p.nombre) = LOWER(?) ");
						parametros.add(filtro.getDepartamento().getPais().getNombre().trim());
					}
				}
			}
		}

		sentenciaSql.append("ORDER BY p.nombre ASC, d.nombre ASC, c.nombre ASC");

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql.toString())) {

			for (int indice = 0; indice < parametros.size(); indice++) {
				sentencia.setObject(indice + 1, parametros.get(indice));
			}

			try (ResultSet resultado = sentencia.executeQuery()) {
				var ciudades = new ArrayList<CiudadEntidad>();

				while (resultado.next()) {
					ciudades.add(ensamblarCiudad(resultado));
				}

				return ciudades;
			}

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible consultar la información de las ciudades.",
					excepcion);
		}
	}

	@Override
	public void actualizar(final CiudadEntidad entidad) {
		final String sentenciaSql = "UPDATE dbo.Ciudad SET nombre = ?, departamento_id = ? WHERE id = ?";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, entidad.getNombre());
			sentencia.setString(2, entidad.getDepartamento().getId().toString());
			sentencia.setString(3, entidad.getId().toString());

			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible modificar la información de la ciudad.",
					excepcion);
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sentenciaSql = "DELETE FROM dbo.Ciudad WHERE id = ?";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, id.toString());

			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible eliminar la información de la ciudad.",
					excepcion);
		}
	}

	private CiudadEntidad ensamblarCiudad(final ResultSet resultado) throws SQLException {
		var pais = PaisEntidad.builder()
				.id(UUID.fromString(resultado.getString("pais_id")))
				.nombre(resultado.getString("pais_nombre"))
				.build();

		var departamento = DepartamentoEntidad.builder()
				.id(UUID.fromString(resultado.getString("departamento_id")))
				.nombre(resultado.getString("departamento_nombre"))
				.pais(pais)
				.build();

		return CiudadEntidad.builder()
				.id(UUID.fromString(resultado.getString("ciudad_id")))
				.nombre(resultado.getString("ciudad_nombre"))
				.departamento(departamento)
				.build();
	}

	private boolean tieneTexto(final String texto) {
		return Objects.nonNull(texto) && !texto.trim().isEmpty();
	}

	private boolean esUUIDValido(final UUID id) {
		return Objects.nonNull(id) && !UUID_DEFECTO.equals(id);
	}
}