package co.edu.uco.ucoparking.inicializador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class PruebaConexionSqlServer {

	public static void main(String[] args) {

		String url = "jdbc:sqlserver://localhost:1433;databaseName=UcoParking;encrypt=true;trustServerCertificate=true";
		String usuario = "sa";
		String clave = "UcoParking123*";

		String sql = "SELECT id, nombre FROM Pais";

		try (
				Connection conexion = DriverManager.getConnection(url, usuario, clave);
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				ResultSet resultado = sentencia.executeQuery()
		) {

			System.out.println("Conexión exitosa a SQL Server");

			while (resultado.next()) {
				System.out.println(resultado.getString("id") + " - " + resultado.getString("nombre"));
			}

		} catch (Exception excepcion) {
			excepcion.printStackTrace();
		}
	}
}

