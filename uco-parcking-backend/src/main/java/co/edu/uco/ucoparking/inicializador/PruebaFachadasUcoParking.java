package co.edu.uco.ucoparking.inicializador;

import java.util.List;
import java.util.UUID;

import co.edu.uco.ucoparking.dto.CiudadDTO;
import co.edu.uco.ucoparking.dto.DepartamentoDTO;
import co.edu.uco.ucoparking.dto.PaisDTO;
import co.edu.uco.ucoparking.negocio.fachada.ciudad.impl.ConsultarCiudadPorIdFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.ciudad.impl.ConsultarCiudadesFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.ciudad.impl.EliminarCiudadFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.ciudad.impl.RegistrarNuevaCiudadFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.departamento.impl.ConsultarDepartamentoPorIdFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.departamento.impl.ConsultarDepartamentosFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.departamento.impl.EliminarDepartamentoFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.departamento.impl.RegistrarNuevoDepartamentoFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.ConsultarPaisPorIdFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.ConsultarPaisesFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.EliminarPaisFachadaImpl;
import co.edu.uco.ucoparking.negocio.fachada.pais.impl.RegistrarNuevoPaisFachadaImpl;

public class PruebaFachadasUcoParking {

	public static void main(String[] args) {
		try {
			System.out.println("==============================================");
			System.out.println(" PRUEBA COMPLETA UCO PARKING");
			System.out.println("==============================================");

			// 1. Registrar o consultar países reales
			var colombia = registrarPaisSiNoExiste("Colombia");
			var argentina = registrarPaisSiNoExiste("Argentina");
			var brasil = registrarPaisSiNoExiste("Brasil");
			var espana = registrarPaisSiNoExiste("España");
			var estadosUnidos = registrarPaisSiNoExiste("Estados Unidos");

			// 2. Registrar o consultar departamentos reales
			var antioquia = registrarDepartamentoSiNoExiste("Antioquia", colombia);
			var buenosAiresDepartamento = registrarDepartamentoSiNoExiste("Buenos Aires", argentina);
			var saoPauloDepartamento = registrarDepartamentoSiNoExiste("São Paulo", brasil);
			var madridDepartamento = registrarDepartamentoSiNoExiste("Madrid", espana);
			var california = registrarDepartamentoSiNoExiste("California", estadosUnidos);

			// 3. Registrar o consultar ciudades reales
			var medellin = registrarCiudadSiNoExiste("Medellín", antioquia);
			registrarCiudadSiNoExiste("Buenos Aires", buenosAiresDepartamento);
			registrarCiudadSiNoExiste("São Paulo", saoPauloDepartamento);
			registrarCiudadSiNoExiste("Madrid", madridDepartamento);
			registrarCiudadSiNoExiste("Los Ángeles", california);

			// 4. Consultar por id
			System.out.println("\n=== CONSULTAR PAÍS POR ID ===");
			var paisConsultado = new ConsultarPaisPorIdFachadaImpl().ejecutar(colombia.getId());
			imprimirPais(paisConsultado);

			System.out.println("\n=== CONSULTAR DEPARTAMENTO POR ID ===");
			var departamentoConsultado = new ConsultarDepartamentoPorIdFachadaImpl().ejecutar(antioquia.getId());
			imprimirDepartamento(departamentoConsultado);

			System.out.println("\n=== CONSULTAR CIUDAD POR ID ===");
			var ciudadConsultada = new ConsultarCiudadPorIdFachadaImpl().ejecutar(medellin.getId());
			imprimirCiudad(ciudadConsultada);

			// 5. Consultar todos
			System.out.println("\n=== CONSULTAR TODOS LOS PAÍSES ===");
			var paises = new ConsultarPaisesFachadaImpl().ejecutar(PaisDTO.builder().build());
			paises.forEach(PruebaFachadasUcoParking::imprimirPaisResumen);

			System.out.println("\n=== CONSULTAR TODOS LOS DEPARTAMENTOS ===");
			var departamentos = new ConsultarDepartamentosFachadaImpl().ejecutar(DepartamentoDTO.builder().build());
			departamentos.forEach(PruebaFachadasUcoParking::imprimirDepartamentoResumen);

			System.out.println("\n=== CONSULTAR TODAS LAS CIUDADES ===");
			var ciudades = new ConsultarCiudadesFachadaImpl().ejecutar(CiudadDTO.builder().build());
			ciudades.forEach(PruebaFachadasUcoParking::imprimirCiudadResumen);

			// 6. Prueba de eliminación con datos reales temporales
			System.out.println("\n=== PRUEBA DE ELIMINACIÓN ===");

			var chile = registrarPaisSiNoExiste("Chile");
			var valparaisoDepartamento = registrarDepartamentoSiNoExiste("Valparaíso", chile);
			var vinaDelMar = registrarCiudadSiNoExiste("Viña del Mar", valparaisoDepartamento);

			System.out.println("Antes de eliminar:");
			imprimirCiudad(vinaDelMar);

			new EliminarCiudadFachadaImpl().ejecutar(vinaDelMar.getId());
			System.out.println("Ciudad eliminada: Viña del Mar");

			new EliminarDepartamentoFachadaImpl().ejecutar(valparaisoDepartamento.getId());
			System.out.println("Departamento eliminado: Valparaíso");

			new EliminarPaisFachadaImpl().ejecutar(chile.getId());
			System.out.println("País eliminado: Chile");

			System.out.println("\nPRUEBA FINALIZADA CORRECTAMENTE.");

		} catch (Exception excepcion) {
			System.out.println("ERROR EN LA PRUEBA: " + excepcion.getMessage());
			excepcion.printStackTrace();
		}
	}

	private static PaisDTO registrarPaisSiNoExiste(final String nombre) {
		var encontrados = buscarPaisesPorNombre(nombre);

		if (!encontrados.isEmpty()) {
			return encontrados.get(0);
		}

		new RegistrarNuevoPaisFachadaImpl().ejecutar(
				PaisDTO.builder()
						.nombre(nombre)
						.build());

		return buscarPaisesPorNombre(nombre).get(0);
	}

	private static DepartamentoDTO registrarDepartamentoSiNoExiste(final String nombre, final PaisDTO pais) {
		var encontrados = buscarDepartamentosPorNombreYPais(nombre, pais.getId());

		if (!encontrados.isEmpty()) {
			return encontrados.get(0);
		}

		new RegistrarNuevoDepartamentoFachadaImpl().ejecutar(
				DepartamentoDTO.builder()
						.nombre(nombre)
						.pais(PaisDTO.builder().id(pais.getId()).build())
						.build());

		return buscarDepartamentosPorNombreYPais(nombre, pais.getId()).get(0);
	}

	private static CiudadDTO registrarCiudadSiNoExiste(final String nombre, final DepartamentoDTO departamento) {
		var encontrados = buscarCiudadesPorNombreYDepartamento(nombre, departamento.getId());

		if (!encontrados.isEmpty()) {
			return encontrados.get(0);
		}

		new RegistrarNuevaCiudadFachadaImpl().ejecutar(
				CiudadDTO.builder()
						.nombre(nombre)
						.departamento(DepartamentoDTO.builder().id(departamento.getId()).build())
						.build());

		return buscarCiudadesPorNombreYDepartamento(nombre, departamento.getId()).get(0);
	}

	private static List<PaisDTO> buscarPaisesPorNombre(final String nombre) {
		return new ConsultarPaisesFachadaImpl().ejecutar(
				PaisDTO.builder()
						.nombre(nombre)
						.build());
	}

	private static List<DepartamentoDTO> buscarDepartamentosPorNombreYPais(final String nombre, final UUID idPais) {
		return new ConsultarDepartamentosFachadaImpl().ejecutar(
				DepartamentoDTO.builder()
						.nombre(nombre)
						.pais(PaisDTO.builder().id(idPais).build())
						.build());
	}

	private static List<CiudadDTO> buscarCiudadesPorNombreYDepartamento(final String nombre, final UUID idDepartamento) {
		return new ConsultarCiudadesFachadaImpl().ejecutar(
				CiudadDTO.builder()
						.nombre(nombre)
						.departamento(DepartamentoDTO.builder().id(idDepartamento).build())
						.build());
	}

	private static void imprimirPais(final PaisDTO pais) {
		System.out.println("País:");
		System.out.println("ID: " + pais.getId());
		System.out.println("Nombre: " + pais.getNombre());
	}

	private static void imprimirDepartamento(final DepartamentoDTO departamento) {
		System.out.println("Departamento:");
		System.out.println("ID: " + departamento.getId());
		System.out.println("Nombre: " + departamento.getNombre());
		System.out.println("País ID: " + departamento.getPais().getId());
		System.out.println("País Nombre: " + departamento.getPais().getNombre());
	}

	private static void imprimirCiudad(final CiudadDTO ciudad) {
		System.out.println("Ciudad:");
		System.out.println("ID: " + ciudad.getId());
		System.out.println("Nombre: " + ciudad.getNombre());
		System.out.println("Departamento ID: " + ciudad.getDepartamento().getId());
		System.out.println("Departamento Nombre: " + ciudad.getDepartamento().getNombre());
		System.out.println("País ID: " + ciudad.getDepartamento().getPais().getId());
		System.out.println("País Nombre: " + ciudad.getDepartamento().getPais().getNombre());
	}

	private static void imprimirPaisResumen(final PaisDTO pais) {
		System.out.println(pais.getId() + " | " + pais.getNombre());
	}

	private static void imprimirDepartamentoResumen(final DepartamentoDTO departamento) {
		System.out.println(departamento.getId() + " | " + departamento.getNombre()
				+ " | País: " + departamento.getPais().getNombre());
	}

	private static void imprimirCiudadResumen(final CiudadDTO ciudad) {
		System.out.println(ciudad.getId() + " | " + ciudad.getNombre()
				+ " | Departamento: " + ciudad.getDepartamento().getNombre()
				+ " | País: " + ciudad.getDepartamento().getPais().getNombre());
	}
}