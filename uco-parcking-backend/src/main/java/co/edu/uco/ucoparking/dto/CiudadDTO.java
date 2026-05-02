package co.edu.uco.ucoparking.dto;

import java.util.UUID;

import co.edu.uco.ucoparking.transversal.UtilObjeto;
import co.edu.uco.ucoparking.transversal.UtilTexto;
import co.edu.uco.ucoparking.transversal.UtilUUID;

public class CiudadDTO {

	private UUID id;
	private String nombre;
	private DepartamentoDTO departamento;

	private CiudadDTO(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
		setDepartamento(builder.departamento);
	}

	public UUID getId() {
		return id;
	}

	private void setId(final UUID id) {
	    this.id = UtilUUID.obtenerValorDefecto(id);
	}
	
	public String getNombre() {
		return nombre;
	}

	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.aplicarTrim(nombre);
	}

	public DepartamentoDTO getDepartamento() {
		return departamento;
	}

	private void setDepartamento(final DepartamentoDTO departamento) {
		this.departamento = UtilObjeto.obtenerValorDefecto(departamento, new DepartamentoDTO.Builder().build());
	}

	public static class Builder {

		private UUID id;
		private String nombre;
		private DepartamentoDTO departamento;

		public Builder id(final UUID id) {
			this.id = id;
			return this;
		}

		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}

		public Builder departamento(final DepartamentoDTO departamento) {
			this.departamento = UtilObjeto.obtenerValorDefecto(departamento, new DepartamentoDTO.Builder().build());
			return this;
		}

		public CiudadDTO build() {
			return new CiudadDTO(this);
		}
	}
}