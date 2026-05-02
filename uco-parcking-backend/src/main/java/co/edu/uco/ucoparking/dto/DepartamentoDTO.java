package co.edu.uco.ucoparking.dto;

import java.util.UUID;

import co.edu.uco.ucoparking.transversal.UtilObjeto;
import co.edu.uco.ucoparking.transversal.UtilTexto;

public class DepartamentoDTO {

	private UUID id;
	private String nombre;
	private PaisDTO pais;

	private DepartamentoDTO(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
		setPais(builder.pais);
	}

	public UUID getId() {
		return id;
	}

	private void setId(final UUID id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.aplicarTrim(nombre);
	}

	public PaisDTO getPais() {
		return pais;
	}

	private void setPais(final PaisDTO pais) {
		this.pais = UtilObjeto.obtenerValorDefecto(pais, new PaisDTO.Builder().build());
	}

	public static class Builder {

		private UUID id;
		private String nombre;
		private PaisDTO pais;

		public Builder id(final UUID id) {
			this.id = id;
			return this;
		}

		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}

		public Builder pais(final PaisDTO pais) {
			this.pais = UtilObjeto.obtenerValorDefecto(pais, new PaisDTO.Builder().build());
			return this;
		}

		public DepartamentoDTO build() {
			return new DepartamentoDTO(this);
		}
	}
}
