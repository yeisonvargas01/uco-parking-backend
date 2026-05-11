package co.edu.uco.ucoparking.datos.dao.sql.factoria.sqlserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SQLServerConexion {

	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=UcoParking;encrypt=true;trustServerCertificate=true;";
	private static final String USUARIO = "sa";
	private static final String CLAVE = "UcoParking123*";

	private SQLServerConexion() {
		super();
	}

	public static Connection getConexion() throws SQLException {
		return DriverManager.getConnection(URL, USUARIO, CLAVE);
	}
}
