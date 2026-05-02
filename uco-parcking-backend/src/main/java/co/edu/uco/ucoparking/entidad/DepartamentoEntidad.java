package co.edu.uco.ucoparking.entidad;

import java.util.UUID;

import co.edu.uco.ucoparking.transversal.UtilObjeto;
import co.edu.uco.ucoparking.transversal.UtilTexto;
import co.edu.uco.ucoparking.transversal.UtilUUID;

public class DepartamentoEntidad {

	private UUID id;
	private String nombre;
	private PaisEntidad pais;

	private DepartamentoEntidad(final Builder builder) {
		setId(builder.id);
		setNombre(builder.nombre);
		setPais(builder.pais);
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

	public PaisEntidad getPais() {
		return pais;
	}

	private void setPais(final PaisEntidad pais) {
		this.pais = UtilObjeto.obtenerValorDefecto(pais, new PaisEntidad.Builder().build());
	}

	public static class Builder {

		private UUID id;
		private String nombre;
		private PaisEntidad pais;

		public Builder id(final UUID id) {
			this.id = id;
			return this;
		}

		public Builder nombre(final String nombre) {
			this.nombre = UtilTexto.aplicarTrim(nombre);
			return this;
		}

		public Builder pais(final PaisEntidad pais) {
			this.pais = UtilObjeto.obtenerValorDefecto(pais, new PaisEntidad.Builder().build());
			return this;
		}

		public DepartamentoEntidad build() {
			return new DepartamentoEntidad(this);
		}
	}
}
