package co.edu.uco.ucoparking.datos.dao.sql.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import co.edu.uco.ucoparking.datos.dao.PaisDAO;
import co.edu.uco.ucoparking.datos.dao.sql.SQLDAO;
import co.edu.uco.ucoparking.entidad.PaisEntidad;
import co.edu.uco.ucoparking.transversal.utilitario.excepcion.DatosUcoParkingExcepcion;

public class PaisSQLServerDAO extends SQLDAO implements PaisDAO {

	private static final UUID UUID_DEFECTO = UUID.fromString("00000000-0000-0000-0000-000000000000");

	public PaisSQLServerDAO(final Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(final PaisEntidad entidad) {
		final String sentenciaSql = "INSERT INTO dbo.Pais (id, nombre) VALUES (?, ?)";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, entidad.getId().toString());
			sentencia.setString(2, entidad.getNombre());

			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible registrar la información del país.",
					excepcion);
		}
	}

	@Override
	public List<PaisEntidad> consultar() {
		return consultar(PaisEntidad.builder().build());
	}

	@Override
	public PaisEntidad consultarPorId(final UUID id) {
		final String sentenciaSql = "SELECT id, nombre FROM dbo.Pais WHERE id = ?";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, id.toString());

			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					return ensamblarPais(resultado);
				}
			}

			return null;

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible consultar la información del país por identificador.",
					excepcion);
		}
	}

	@Override
	public List<PaisEntidad> consultar(final PaisEntidad filtro) {
		var parametros = new ArrayList<Object>();
		var sentenciaSql = new StringBuilder();

		sentenciaSql.append("SELECT id, nombre ");
		sentenciaSql.append("FROM dbo.Pais ");
		sentenciaSql.append("WHERE 1 = 1 ");

		if (Objects.nonNull(filtro)) {

			if (esUUIDValido(filtro.getId())) {
				sentenciaSql.append("AND id = ? ");
				parametros.add(filtro.getId().toString());
			}

			if (tieneTexto(filtro.getNombre())) {
				sentenciaSql.append("AND LOWER(nombre) = LOWER(?) ");
				parametros.add(filtro.getNombre().trim());
			}
		}

		sentenciaSql.append("ORDER BY nombre ASC");

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql.toString())) {

			for (int indice = 0; indice < parametros.size(); indice++) {
				sentencia.setObject(indice + 1, parametros.get(indice));
			}

			try (ResultSet resultado = sentencia.executeQuery()) {
				var paises = new ArrayList<PaisEntidad>();

				while (resultado.next()) {
					paises.add(ensamblarPais(resultado));
				}

				return paises;
			}

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible consultar la información de los países.",
					excepcion);
		}
	}

	@Override
	public void actualizar(final PaisEntidad entidad) {
		final String sentenciaSql = "UPDATE dbo.Pais SET nombre = ? WHERE id = ?";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, entidad.getNombre());
			sentencia.setString(2, entidad.getId().toString());

			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible modificar la información del país.",
					excepcion);
		}
	}

	@Override
	public void eliminar(final UUID id) {
		final String sentenciaSql = "DELETE FROM dbo.Pais WHERE id = ?";

		try (PreparedStatement sentencia = getConexion().prepareStatement(sentenciaSql)) {
			sentencia.setString(1, id.toString());

			sentencia.executeUpdate();

		} catch (SQLException excepcion) {
			throw DatosUcoParkingExcepcion.crear(
					"No fue posible eliminar la información del país.",
					excepcion);
		}
	}

	private PaisEntidad ensamblarPais(final ResultSet resultado) throws SQLException {
		return PaisEntidad.builder()
				.id(UUID.fromString(resultado.getString("id")))
				.nombre(resultado.getString("nombre"))
				.build();
	}

	private boolean tieneTexto(final String texto) {
		return Objects.nonNull(texto) && !texto.trim().isEmpty();
	}

	private boolean esUUIDValido(final UUID id) {
		return Objects.nonNull(id) && !UUID_DEFECTO.equals(id);
	}
}