package co.edu.uco.ucoparking.entidad;

import java.util.UUID;

import co.edu.uco.ucoparking.transversal.utilitario.UtilObjeto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilTexto;
import co.edu.uco.ucoparking.transversal.utilitario.UtilUUID;

public class CiudadEntidad {

	private UUID id;
	private String nombre;
	private DepartamentoEntidad departamento;

	private CiudadEntidad(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
		setDepartamento(builder.departamento);
	}

	public static Builder builder() {
		return new Builder();
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

	public DepartamentoEntidad getDepartamento() {
		return departamento;
	}

	private void setDepartamento(final DepartamentoEntidad departamento) {
		this.departamento = UtilObjeto.obtenerValorDefecto(departamento, DepartamentoEntidad.builder().build());
	}

	public static class Builder {

		private UUID id;
		private String nombre;
		private DepartamentoEntidad departamento;

		private Builder() {
			super();
		}

		public Builder id(final UUID id) {
			this.id = id;
			return this;
		}

		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}

		public Builder departamento(final DepartamentoEntidad departamento) {
			this.departamento = UtilObjeto.obtenerValorDefecto(departamento, DepartamentoEntidad.builder().build());
			return this;
		}

		public CiudadEntidad build() {
			return new CiudadEntidad(this);
		}
	}
}